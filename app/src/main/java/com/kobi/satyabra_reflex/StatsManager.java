package com.kobi.satyabra_reflex;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Created by kobitoko on 20/09/15.
 * Singleton Statistics manager.
 */
public class StatsManager {

    private static StatsManager statsMan;
    private Vector<Integer> reactionTimes;
    private Map<Integer, Integer> buzzerCounts;

    public static final String FILENAME = "stats.dat";

    // later can iterate through them via for(Integer a : BuzzId.values()){}
    // this doesnt quite work, cannot access enum in activity.
    public enum BuzzId{ P21, P22,
                        P31, P32, P33,
                        P41, P42, P43, P44 }

    // prohibit new creation of instances
    private StatsManager() {
        statsMan = new StatsManager();
        reactionTimes = new Vector<Integer>(100);
        buzzerCounts = new HashMap<Integer, Integer>();
    }

    // retrieve the statistic manager object using Bill Pugh's Initialization-on-demand holder idiom.
    // taken from https://en.wikipedia.org/wiki/Singleton_pattern#Initialization-on-demand_holder_idiom
    private static class singletonHolder {
        private static final StatsManager INSTANCE = new StatsManager();
    }

    public Vector<Integer> getReactionTimeData() {
        return reactionTimes;
    }

    public Map<Integer, Integer> getBuzzerCountData() {
        return buzzerCounts;
    }

    public static StatsManager getStats() {
        return singletonHolder.INSTANCE;
    }

    // Gets the reaction time from the reaction time records which contains 100 records.
    // Returns -1 if invalid index.
    public Integer getReactionTime(Integer index) {
        if(index >= 100 || index < 0)
            return -1;
        return reactionTimes.get(index);
    }

    // adds a reaction time to the reaction time records. Only can hold 100, oldest value gets dropped.
    public void addReactionTime(Integer newReactionTime) {
        reactionTimes.add(newReactionTime);
        if(reactionTimes.size() > 100)
            reactionTimes.removeElementAt(0);
    }

    // Gets the Buzzer Counts from the Buzzer Player Number (BuzzId). Returns -1 if key doesn't exist.
    public Integer getBuzzerCount(Integer BuzzId) {
        if(!buzzerCounts.containsKey(BuzzId))
            return -1;
        return buzzerCounts.get(BuzzId);
    }

    // Adds a count to a buzzer player number (BuzzId).
    public void addBuzzerCount(Integer BuzzId) {
        Integer counts = new Integer(1);
        // if an old count already exist, add it.
        if(buzzerCounts.containsKey(BuzzId))
            counts += buzzerCounts.get(BuzzId);
        buzzerCounts.put(BuzzId, counts);
    }

    public void dataClearAndLoad(Vector<Integer> oldReactionTimes, Map<Integer, Integer> oldBuzzerCounts) {
        clearStatistics();
        buzzerCounts.putAll(oldBuzzerCounts);
        reactionTimes.addAll(oldReactionTimes);
    }

    public void clearStatistics() {
        reactionTimes.clear();
        buzzerCounts.clear();
    }
}
