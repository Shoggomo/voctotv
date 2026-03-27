package de.justjanne.voctotv.mobile.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.provider.Settings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

internal class CustomRotationListener(
    private val context: Activity,
    private val scope: CoroutineScope,
) : ScreenOrientationListener(context) {
    private var job: Job? = null

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onScreenOrientationChanged(orientation: Int) {
        val rotationLock =
            Settings.System.getInt(context.contentResolver, Settings.System.ACCELEROMETER_ROTATION, 0) == 0

        val lockedOrientation =
            when (context.requestedOrientation) {
                ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE -> Configuration.ORIENTATION_LANDSCAPE
                ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT -> Configuration.ORIENTATION_PORTRAIT
                else -> null
            }

        job?.cancel()
        job =
            scope.launch {
                delay(500.milliseconds)
                when (orientation) {
                    Configuration.ORIENTATION_LANDSCAPE -> {
                        if (orientation != lockedOrientation && !rotationLock) {
                            context.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
                        }
                    }

                    Configuration.ORIENTATION_PORTRAIT -> {
                        if (orientation != lockedOrientation && !rotationLock) {
                            context.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT
                        }
                    }
                }
            }
    }

    override fun disable() {
        job?.cancel()
        super.disable()
    }
}
