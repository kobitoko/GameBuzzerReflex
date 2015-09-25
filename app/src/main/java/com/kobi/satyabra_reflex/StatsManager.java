package com.kobi.satyabra_reflex;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kobitoko on 20/09/15.
 */
public class StatsManager implements Parcelable {

    private ArrayList reactionTimes;
    private HashMap<Integer, Integer> buzzerCounts;

    public static final String FILENAME = "stats.dat";

    // later can iterate through them via for(Integer a : BuzzId){}
    public static final Integer[] BuzzId = new Integer[] {21, 22,
        31, 32, 33,
        41, 42, 43, 44};

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // Taken from's hdort answer http://stackoverflow.com/questions/10757598/what-classloader-to-use-with-parcel-readhashmap
        Bundle bundle = new Bundle();
        bundle.putSerializable("reactionTimes", reactionTimes);
        bundle.putSerializable("buzzerCounts", buzzerCounts);
        dest.writeBundle(bundle);
    }

    // Taken from fiXedd's answer http://stackoverflow.com/questions/2139134/how-to-send-an-object-from-one-android-activity-to-another-using-intents
    public static final Parcelable.Creator<StatsManager> CREATOR = new Parcelable.Creator<StatsManager>() {
        public StatsManager createFromParcel(Parcel in) {
            return new StatsManager(in);
        }

        public StatsManager[] newArray(int size) {
            return new StatsManager[size];
        }
    };

    public StatsManager() {
        reactionTimes = new ArrayList(100);
        buzzerCounts = new HashMap<Integer, Integer>(10);
    }

    // Taken from fiXedd's answer http://stackoverflow.com/questions/2139134/how-to-send-an-object-from-one-android-activity-to-another-using-intents
    // Constructor that takes a Parcel and gives you an object populated with it's values
    private StatsManager(Parcel in) {
        // Taken from's hdort answer http://stackoverflow.com/questions/10757598/what-classloader-to-use-with-parcel-readhashmap
        Bundle bundle = in.readBundle();
        reactionTimes = (ArrayList) bundle.getSerializable("reactionTimes");
        buzzerCounts = (HashMap<Integer, Integer>) bundle.getSerializable("buzzerCounts");
    }

    // Gets the reaction time from the reaction time records which contains 100 records.
    public Integer getReactionTime(Integer index) throws RuntimeException {
        if(index >= 100 || index < 0) {
            String e = "StatsManager class method getReactionTime index has to be 0 to 99. Index is: " + index.toString();
            throw new RuntimeException(e);
        }
        return (Integer) reactionTimes.get(index);
    }

    // adds a reaction time to the reaction time records. Only can hold 100, oldest value gets dropped.
    public void addReactionTime(Integer newReactionTime) {
        reactionTimes.add(newReactionTime);
        if(reactionTimes.size() > 100)
            reactionTimes.remove(0);
    }

    // Gets the Buzzer Counts from the Buzzer Player Number (BuzzId).
    public Integer getBuzzerCount(Integer BuzzId) throws RuntimeException {
        if(!buzzerCounts.containsKey(BuzzId)) {
            String e = "StatsManager class method getBuzzerCount key doesn't exist. Key is: " + BuzzId.toString();
            throw new RuntimeException(e);
        }
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

    public void clearStatistics() {
        reactionTimes.clear();
        buzzerCounts.clear();
    }
}
