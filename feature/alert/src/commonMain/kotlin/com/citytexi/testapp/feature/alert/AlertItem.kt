package com.citytexi.testapp.feature.alert

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.citytexi.testapp.core.designsystem.TestColors
import com.citytexi.testapp.core.designsystem.testClickable

data class AlertInfo(val title: String, val body: String, val isNew: Boolean = true)

@Composable
fun AlertItem(alert: AlertInfo, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .testClickable(onClick = onClick)
            .background(if (alert.isNew) TestColors.surface else TestColors.accent.copy(alpha = 0f))
            .padding(20.dp),
    ) {
        Spacer(
            modifier = Modifier
                .size(40.dp)
                .background(TestColors.accent, shape = CircleShape),
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            BasicText(alert.title, style = TextStyle(color = TestColors.onSurface, fontSize = 16.sp))
            Spacer(modifier = Modifier.height(6.dp))
            BasicText(alert.body, style = TextStyle(color = TestColors.muted, fontSize = 14.sp))
        }
    }
}

private class AlertInfoProvider : PreviewParameterProvider<AlertInfo> {
    override val values = sequenceOf(
        AlertInfo(title = "Notice title", body = "Notice body. Please check the details."),
        AlertInfo(title = "Read notice", body = "Already read.", isNew = false),
    )
}

@Preview
@Composable
private fun PreviewAlertItem(@PreviewParameter(AlertInfoProvider::class) alert: AlertInfo) {
    AlertItem(alert = alert, onClick = {})
}
