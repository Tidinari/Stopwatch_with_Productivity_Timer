package org.hyperskill.stopwatch.timer

interface TimerCallback {

    /**
     * called on start
     */
    fun onStart() {}

    /**
     * called on every Timer tick
     * @param difference from start time in seconds
     */
    fun onUpdate(difference: Int) {}

    /**
     * called on stop
     */
    fun onStop() {}

    /**
     * Blank implementation
     */
    class EmptyCallback: TimerCallback
}