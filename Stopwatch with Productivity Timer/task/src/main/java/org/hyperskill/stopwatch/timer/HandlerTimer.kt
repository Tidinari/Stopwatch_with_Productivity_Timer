package org.hyperskill.stopwatch.timer

import android.os.Handler
import android.os.Looper

class HandlerTimer : Timer {
    private var isStarted = false
    private var startTime: Long? = null
    private val handler = Handler(Looper.getMainLooper())
    private var callback: TimerCallback = TimerCallback.EmptyCallback()

    private fun timerTask() {
        if (isStarted) {
            val difference = ((System.currentTimeMillis() - startTime!!) / 1000).toInt()
            handler.post { callback.onUpdate(difference) }
            startAndRepeatTask()
        }
    }

    override fun start() {
        if (isStarted) return
        isStarted = true
        startTime = System.currentTimeMillis()
        startAndRepeatTask()
        callback.onStart()
    }

    override fun reset() {
        isStarted = false
        handler.removeCallbacks(::timerTask)
        callback.onStop()
    }

    override fun setCallback(timerCallback: TimerCallback) {
        this.callback = timerCallback
    }

    override fun isRunning(): Boolean {
        return isStarted
    }

    private fun startAndRepeatTask() {
        if (isStarted)
            handler.postDelayed(::timerTask, 1000L)
    }
}