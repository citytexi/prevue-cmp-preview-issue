package com.citytexi.testapp.core.designsystem

import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier

/** A top-level extension — the Kotlin compiler finds it through the module's `.kotlin_module` metadata. */
fun Modifier.testClickable(onClick: () -> Unit): Modifier = clickable(onClick = onClick)
