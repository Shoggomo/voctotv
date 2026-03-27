package de.justjanne.voctotv.mobile.util

import android.content.pm.ActivityInfo
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope

@Composable
fun CustomRotationController() {
    val context = LocalActivity.current ?: return

    val scope = rememberCoroutineScope()
    DisposableEffect(context) {
        val listener = CustomRotationListener(context, scope)
        listener.enable()

        onDispose {
            listener.disable()
            context.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }
}
