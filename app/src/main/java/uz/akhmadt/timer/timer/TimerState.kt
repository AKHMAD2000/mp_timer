 
package uz.akhmadt.timer.timer

import uz.akhmadt.timer.balls.SwingAnimation
import uz.akhmadt.timer.timer.TimerState.Configured.Paused
import uz.akhmadt.timer.timer.TimerState.Configured.Running
import uz.akhmadt.timer.timer.TimerState.NotConfigured
import uz.akhmadt.timer.timer.TimerViewModel.Companion.MAX_ANGLE
import uz.akhmadt.timer.timer.TimerViewModel.Companion.MAX_DURATION_MILLIS

sealed class TimerState(val durationMillis: Long) {
    val startAngle: Float = durationToAngle(durationMillis)
    val remainingMillis get() = (durationMillis - elapsedMillis).coerceAtLeast(0)

    abstract val elapsedMillis: Long

    class NotConfigured(durationMillis: Long = 0) : TimerState(durationMillis) {
        override val elapsedMillis = 0L

        constructor(startAngle: Float) : this(durationMillis = angleToDuration(startAngle))
    }

    sealed class Configured(durationMillis: Long) : TimerState(durationMillis) {
        val swingAnimation = SwingAnimation(startAngle)
        val isFinished get() = remainingMillis <= 0

        class Paused(
            durationMillis: Long,
            override val elapsedMillis: Long = 0
        ) : Configured(durationMillis)

        class Running(
            durationMillis: Long,
            private val alreadyElapsedMillis: Long = 0
        ) : Configured(durationMillis) {
            private val resumedAtMillis = Clock.now()
            override val elapsedMillis get() = alreadyElapsedMillis + Clock.now() - resumedAtMillis
        }
    }

    protected companion object {
        fun durationToAngle(durationMillis: Long) = durationMillis / MAX_DURATION_MILLIS.toFloat() * MAX_ANGLE
        fun angleToDuration(angle: Float): Long {
            val duration = (angle / MAX_ANGLE * MAX_DURATION_MILLIS).toLong()
            return duration - duration % 1000
        }
    }
}

val TimerState.Configured.absoluteRemainingEnergy get() = remainingMillis / MAX_DURATION_MILLIS.toFloat()
val TimerState.Configured.relativeRemainingEnergy get() = remainingMillis / durationMillis.toFloat()
fun TimerState.canBeStarted() = durationMillis > 0
fun NotConfigured.started() = Running(durationMillis = durationMillis)
fun Paused.resumed() = Running(durationMillis = durationMillis, alreadyElapsedMillis = elapsedMillis)
fun Running.paused() = Paused(durationMillis = durationMillis, elapsedMillis = elapsedMillis)
