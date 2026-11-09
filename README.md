# prevue-cmp-preview-issue

A minimal Compose Multiplatform project that reproduces a hot-reload compile failure in
[Prevue](https://github.com/kakao/prevue): a `commonMain` preview in a KMP library module renders
on open, but every save fails with `COMPILE` because the module is compiled against the **app's**
compile classpath, which does not contain the module's own `implementation` dependencies.

## Layout

```
:app                 com.android.application — implementation(project(":feature:alert")) only
:feature:alert       KMP (com.android.kotlin.multiplatform.library) — commonMain @Preview
  ├─ commonMain  implementation  :core:designsystem, compose ui-tooling-preview, foundation, ui, runtime
  └─ androidMain implementation  compose ui-tooling (ComposeViewAdapter for the renderer)
:core:designsystem   KMP — top-level Modifier.testClickable(), TestColors
```

`:feature:alert` uses `implementation` for its dependencies, as feature modules usually do.
Gradle does not leak `implementation` dependencies into a consumer's compile classpath, so
`:app`'s `debugCompileClasspath` has none of them.

Toolchain: Gradle 9.6.1 · AGP 9.3.2 · Kotlin 2.4.20 · Compose Multiplatform 1.12.0 · compileSdk 37.

## Reproduce

```sh
./gradlew :app:assembleDebug
prevue show "$PWD/feature/alert/src/commonMain/kotlin/com/citytexi/testapp/feature/alert/AlertItem.kt"
# approve the warmup it asks for; both PreviewAlertItem cards render
```

Edit `AlertItem.kt` (for example `.padding(20.dp)` → `.padding(28.dp)`), save, then:

```sh
prevue wait "$PWD/feature/alert/src/commonMain/kotlin/com/citytexi/testapp/feature/alert/AlertItem.kt"
```

### Expected

Exit 0 and the card redrawn with the new padding.

### Actual (Prevue 0.21.0, also develop `4efcb80`)

Exit 1, `failure: COMPILE`:

```
:feature:alert has no dump, so it was compiled with the compile classpath from the :app dump. ...
Run prevue warmup .../AlertItem.kt to dump that module (one Gradle run)
AlertItem.kt:3:36: error: unresolved reference 'background'.
AlertItem.kt:17:44: error: unresolved reference 'Preview'.
AlertItem.kt:22:29: error: unresolved reference 'core'.
...
```

Running the suggested `prevue warmup` does not help: a KMP module is always drawn from the app's
dump, so the next save fails the same way.

## Why the first render works

The first render loads classes Gradle already built, from the app's **runtime** classpath, where
`implementation` dependencies are present. Only the hot-reload compile uses the app's **compile**
classpath. `GradleOriginals` tries to fill the gap from the Gradle cache, but it skips in-repo
modules and cannot map transformed jars such as `jetified-ui-tooling-preview-runtime.jar`
(no version in the name) or `jetified-…-1.6.2-runtime.jar` (prefixed name).

## Workaround (not a fix)

Changing `:feature:alert`'s dependencies to `api(...)` makes them visible to `:app` and the save
compiles. That widens the module's public surface only to satisfy the preview tool.
