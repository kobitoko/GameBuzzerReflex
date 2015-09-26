package com.kobi.satyabra_reflex;

import android.os.SystemClock;

/**
 * Created by Ryan on 2015-09-25.
 */
public class StopWatch {
    private Long oldTime;
    private Long timeDiff;
    private Long pauseTime;

    public StopWatch() {
        oldTime = new Long(SystemClock.elapsedRealtime());
        pauseTime = new Long(0);
        timeDiff = new Long(0);
    }

    public void start() {
        oldTime = SystemClock.elapsedRealtime();
    }

    public void pause() {
        pauseTime = SystemClock.elapsedRealtime();
    }

    public Long resume() {
        Long pauseDuration = SystemClock.elapsedRealtime() - pauseTime;
        oldTime += pauseDuration;
        return pauseDuration;
    }

    public Long stop() {
        timeDiff = SystemClock.elapsedRealtime() - oldTime;
        return timeDiff;
    }

    public Long getTimeDifference() {
        return timeDiff;
    }

}
