package com.kobi.satyabra_reflex;

import android.os.SystemClock;

/**
 * StopWatch's purpose is to keep track of time passed since old time. It is basically a stopwatch.
 */
public class StopWatch {
    private Long oldTime;
    private Long timeDiff;
    private Long pauseTime;

    // Initialize the times.
    public StopWatch() {
        oldTime = new Long(SystemClock.elapsedRealtime());
        pauseTime = new Long(0);
        timeDiff = new Long(0);
    }

    // Start the stopwatch, sets old time to current time.
    public void start() {
        oldTime = SystemClock.elapsedRealtime();
    }

    // Pauses the stopwatch.
    public void pause() {
        pauseTime = SystemClock.elapsedRealtime();
    }

    // Resumes the stopwatch.
    public Long resume() {
        Long pauseDuration = SystemClock.elapsedRealtime() - pauseTime;
        oldTime += pauseDuration;
        return pauseDuration;
    }

    // Stops the stopwatch and returns the time taken between the call of start() and stop().
    public Long stop() {
        timeDiff = SystemClock.elapsedRealtime() - oldTime;
        return timeDiff;
    }

    // Returns the results from the most recent call of stop().
    public Long getTimeDifference() {
        return timeDiff;
    }

}
