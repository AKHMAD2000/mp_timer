 
package uz.akhmadt.timer.timer

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import uz.akhmadt.timer.balls.SHADOW_TOP_OFFSET
import uz.akhmadt.timer.balls.SwingingBallsContainer
import uz.akhmadt.timer.sinDegree
import uz.akhmadt.timer.timer.TimerViewModel.Companion.MAX_ANGLE
import uz.akhmadt.timer.ui.isLandscape

@Preview(widthDp = 400, heightDp = 700)
@Composable
fun TimerScreen() {
    val timerViewModel: TimerViewModel = viewModel()

    BoxWithConstraints {
        if (isLandscape) {
            TimerLandscape(timerViewModel)
        } else {
            TimerPortrait(timerViewModel)
        }
    }
}

@Composable
private fun TimerPortrait(viewModel: TimerViewModel) {
    Column {
        Column(
            modifier = Modifier
                .animateContentSize()
                .weight(0.88f)
        ) {
            val ballsOuterRatio by animateFloatAsState(
                when (viewModel.isConfigured) {
                    true -> 1.1f * sinDegree(MAX_ANGLE) + BALLS_INNER_ASPECT_RATIO_PORTRAIT
                    else -> sinDegree(MAX_ANGLE) + (BALLS_INNER_ASPECT_RATIO_PORTRAIT / 2f)
                }
            )
            SwingingBallsContainer(
                viewModel = viewModel,
                ballsInnerRatio = BALLS_INNER_ASPECT_RATIO_PORTRAIT,
                modifier = Modifier.aspectRatio(ballsOuterRatio)
            )
            Display(
                viewModel = viewModel,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            )
        }
        ButtonsBar(
            viewModel = viewModel,
            state = viewModel.state,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Spacer(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .weight(0.1f)
        )
    }
}

@Composable
private fun TimerLandscape(viewModel: TimerViewModel) {
    Row {
        Column(
            modifier = Modifier
                .animateContentSize()
                .weight(1.2f)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.weight(0.25f))
            Display(
                viewModel = viewModel,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.weight(1f))
            ButtonsBar(
                viewModel = viewModel,
                state = viewModel.state,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.weight(0.3f))
        }

        SwingingBallsContainer(
            viewModel = viewModel,
            ballsInnerRatio = BALLS_INNER_ASPECT_RATIO_LANDSCAPE,
            modifier = Modifier
                .weight(1f)
                .padding(bottom = SHADOW_TOP_OFFSET + 24.dp)
        )
    }
}

private const val BALLS_INNER_ASPECT_RATIO_PORTRAIT = 0.5f
private const val BALLS_INNER_ASPECT_RATIO_LANDSCAPE = 0.55f
