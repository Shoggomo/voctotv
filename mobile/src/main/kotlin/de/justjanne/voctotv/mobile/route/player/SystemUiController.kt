package de.justjanne.voctotv.mobile.route.player

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import de.justjanne.voctotv.common.player.PlayerState
import de.justjanne.voctotv.mobile.ui.useSystemUi

@Composable
fun SystemUiController(
    playerState: PlayerState,
    uiState: PlayerUiState,
) {
    val systemUi = useSystemUi()
    val isDarkTheme = isSystemInDarkTheme()

    DisposableEffect(uiState.isFullscreen.value, playerState.casting, isDarkTheme) {
        if (!playerState.casting) {
            if (uiState.isFullscreen.value) {
                systemUi?.hideSystemUi()
            } else {
                systemUi?.showSystemUi()
            }
        }

        onDispose {
            systemUi?.showSystemUi()
        }
    }

    val keepAwake =
        remember {
            derivedStateOf {
                when (playerState.status) {
                    PlayerState.Status.BUFFERING -> !playerState.casting
                    PlayerState.Status.PLAYING -> !playerState.casting
                    PlayerState.Status.PAUSED -> false
                    PlayerState.Status.ENDED -> false
                }
            }
        }

    DisposableEffect(keepAwake.value) {
        if (keepAwake.value) {
            systemUi?.disableSleep()
        }

        onDispose {
            systemUi?.enableSleep()
        }
    }
}
