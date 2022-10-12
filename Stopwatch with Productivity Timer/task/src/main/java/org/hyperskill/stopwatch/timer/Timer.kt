package org.hyperskill.stopwatch.timer

interface Timer {
    /**
     * Start ticking
     */
    fun start()

    /**
     * Stop ticking and reset time to 0
     */
    fun reset()

    fun isRunning(): Boolean
    fun setCallback(timerCallback: TimerCallback)
}