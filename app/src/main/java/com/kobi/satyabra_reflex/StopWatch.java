/********************************************************************************
 Copyright 2015 Ryan Satyabrata

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 *********************************************************************************/

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
