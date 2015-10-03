package com.kobi.satyabra_reflex;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * StatsManager is to hold the data of the statistics, reaction times and button presses. Also to provide manipulations to it's data.
 */
public class StatsManager implements Parcelable {

    private ArrayList<Integer> reactionTimes;
    private HashMap<BuzzId, Integer> buzzerCounts;

    // The identity for the Player Buzzers. pXY, where X is the amount of players, and Y is the player's identity.
    public enum BuzzId{p41,p42,p43,p44,
        p31,p32,p33,
        p21,p22}

    @Override
    public int describeContents() {
        return 0;
    }

    // Allows StatsManager to be passable through an intent.
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

    // Initialize the data.
    public StatsManager() {
        reactionTimes = new ArrayList<Integer>(100);
        buzzerCounts = new HashMap<BuzzId, Integer>(10);
    }

    // Taken from fiXedd's answer http://stackoverflow.com/questions/2139134/how-to-send-an-object-from-one-android-activity-to-another-using-intents
    // Constructor that takes a Parcel and gives you an object populated with it's values
    private StatsManager(Parcel in) {
        // Taken from's hdort answer http://stackoverflow.com/questions/10757598/what-classloader-to-use-with-parcel-readhashmap
        Bundle bundle = in.readBundle();
        reactionTimes = (ArrayList<Integer>) bundle.getSerializable("reactionTimes");
        buzzerCounts = (HashMap<BuzzId, Integer>) bundle.getSerializable("buzzerCounts");
    }

    // Gets the reaction time from the reaction time records. Throws when invalid index is given.
    public Integer getReactionTime(Integer index) throws RuntimeException {
        if(index < 0 || index >= reactionTimes.size()) {
            String e = "StatsManager class method getReactionTime index has to be positive, and less than container's size. Index is: " + index.toString();
            throw new RuntimeException(e);
        }
        return reactionTimes.get(index);
    }

    // Adds a reaction time to the reaction time records.
    public void addReactionTime(Integer newReactionTime) {
        reactionTimes.add(0, newReactionTime);
    }

    // Retrieve the reaction time records.
    public ArrayList<Integer> getReactionTimesList() {
        return reactionTimes;
    }

    // Gets the Buzzer Counts from the Buzzer Player Number (BuzzId), if doesn't exist it returns 0.
    public Integer getBuzzerCount(BuzzId BuzzId) {
        if(!buzzerCounts.containsKey(BuzzId))
            return 0;
        return buzzerCounts.get(BuzzId);
    }

    // Adds a count to a buzzer player number (BuzzId).
    public void addBuzzerCount(BuzzId BuzzId) {
        Integer counts = new Integer(1);
        // if an old count already exist, add it.
        if(buzzerCounts.containsKey(BuzzId))
            counts += buzzerCounts.get(BuzzId);
        buzzerCounts.put(BuzzId, counts);
    }

    // Retrieve the buzzer count presses.
    public HashMap<BuzzId, Integer> getBuzzerCountMap() {
        return buzzerCounts;
    }

    // Clear all the data.
    public void clearStatistics() {
        reactionTimes.clear();
        buzzerCounts.clear();
    }
}
