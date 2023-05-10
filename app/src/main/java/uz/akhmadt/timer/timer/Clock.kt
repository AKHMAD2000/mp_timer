 
package uz.akhmadt.timer.timer

import android.os.SystemClock

object Clock {

    var fakeNow: Long? = null

    fun now(): Long = fakeNow ?: SystemClock.uptimeMillis()
}
