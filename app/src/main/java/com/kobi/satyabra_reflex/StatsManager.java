package com.kobi.satyabra_reflex;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by kobitoko on 20/09/15.
 */
public class StatsManager implements Parcelable {

    private ArrayList<Integer> reactionTimes;
    private HashMap<BuzzId, Integer> buzzerCounts;

    public static final String FILENAME = "stats.dat";

    public enum BuzzId{p41,p42,p43,p44,
        p31,p32,p33,
        p21,p22}

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

    // Gets the reaction time from the reaction time records which contains 100 records.
    public Integer getReactionTime(Integer index) throws RuntimeException {
        if(index < 0) {
            String e = "StatsManager class method getReactionTime index has to be positive. Index is: " + index.toString();
            throw new RuntimeException(e);
        }
        return reactionTimes.get(index);
    }

    public Integer reactionTimeSize() {
        return reactionTimes.size();
    }

    // adds a reaction time to the reaction time records.
    public void addReactionTime(Integer newReactionTime) {
        reactionTimes.add(0, newReactionTime);
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

    public void clearStatistics() {
        reactionTimes.clear();
        buzzerCounts.clear();
    }

    // Average Times ---------------------------------------------

    public Integer getAverageReaction() {
        if(reactionTimes.size() == 0)
            return 0;
        Integer allValues = 0;
        for(Integer each : reactionTimes) {
            allValues += each;
        }
        return Math.round(allValues / reactionTimes.size());
    }

    public Integer getAverageReaction10() {
        if(reactionTimes.size() == 0)
            return 0;
        Integer allValues = 0;
        for(int i=0; i<10; ++i) {
            allValues += reactionTimes.get(i);
            if(i+1 >= reactionTimes.size())
                return Math.round(allValues / reactionTimes.size());
        }
        return Math.round(allValues / 10);
    }

    public Integer getAverageReaction100() {
        if(reactionTimes.size() == 0)
            return 0;
        Integer allValues = 0;
        for(int i=0; i<100; ++i) {
            allValues += reactionTimes.get(i);
            if(i+1 >= reactionTimes.size())
                return Math.round(allValues / reactionTimes.size());
        }
        return Math.round(allValues / 100);
    }

    // Median Times ---------------------------------------------

    public Integer getMedianReaction() {
        if(reactionTimes.size() == 0)
            return 0;
        else if(reactionTimes.size() == 1)
            return reactionTimes.get(0);
        Integer medianVal = 0;
        ArrayList<Integer> copyList = new ArrayList<Integer>(reactionTimes);
        Collections.sort(copyList);
        if(copyList.size()%2 == 1) {
            medianVal = copyList.get(copyList.size()/2);
        } else if(copyList.size()%2 == 0) {
            int indexMid = (int) Math.ceil(copyList.size()/2);
            medianVal += copyList.get(indexMid);
            medianVal += copyList.get(indexMid - 1);
            medianVal /= 2;
        }
        return medianVal;
    }

    public Integer getMedianReaction10() {
        if(reactionTimes.size() == 0)
            return 0;
        else if(reactionTimes.size() == 1)
            return reactionTimes.get(0);
        Integer medianVal = 0;
        ArrayList<Integer> copyList = new ArrayList<Integer>();
        for(int i=0; i<10; ++i) {
            copyList.add(reactionTimes.get(i));
            if(i+1 >= reactionTimes.size())
                break;
        }
        Collections.sort(copyList);
        if(copyList.size()%2 == 1) {
            medianVal = copyList.get(copyList.size()/2);
        } else if(copyList.size()%2 == 0) {
            int indexMid = (int) Math.ceil(copyList.size()/2);
            medianVal += copyList.get(indexMid);
            medianVal += copyList.get(indexMid - 1);
            medianVal /= 2;
        }
        return medianVal;
    }

    public Integer getMedianReaction100() {
        if(reactionTimes.size() == 0)
            return 0;
        else if(reactionTimes.size() == 1)
            return reactionTimes.get(0);
        Integer medianVal = 0;
        ArrayList<Integer> copyList = new ArrayList<Integer>();
        for(int i=0; i<100; ++i) {
            copyList.add(reactionTimes.get(i));
            if(i+1 >= reactionTimes.size())
                break;
        }
        Collections.sort(copyList);
        if(copyList.size()%2 == 1) {
            medianVal = copyList.get(copyList.size()/2);
        } else if(copyList.size()%2 == 0) {
            int indexMid = (int) Math.ceil(copyList.size()/2);
            medianVal += copyList.get(indexMid);
            medianVal += copyList.get(indexMid - 1);
            medianVal /= 2;
        }
        return medianVal;
    }

    // Minimum times ---------------------------------------------

    public Integer getFastestReaction() {
        if(reactionTimes.size() == 0)
            return 0;
        Integer lowestVal = 999999;
        for(Integer each : reactionTimes) {
            if(lowestVal > each)
                lowestVal = each;
        }
        return lowestVal;
    }

    public Integer getFastestReaction10() {
        if(reactionTimes.size() == 0)
            return 0;
        Integer lowestVal = 999999;
        for(int i=0; i<10; ++i) {
            if(lowestVal > reactionTimes.get(i))
                lowestVal = reactionTimes.get(i);
            if(i+1 >= reactionTimes.size())
                return lowestVal;
        }
        return lowestVal;
    }

    public Integer getFastestReaction100() {
        if(reactionTimes.size() == 0)
            return 0;
        Integer lowestVal = 999999;
        for(int i=0; i<100; ++i) {
            if(lowestVal > reactionTimes.get(i))
                lowestVal = reactionTimes.get(i);
            if(i+1 >= reactionTimes.size())
                return lowestVal;
        }
        return lowestVal;
    }

    // Maximum times ---------------------------------------------

    public Integer getSlowestReaction() {
        if(reactionTimes.size() == 0)
            return 0;
        Integer highestVal = 0;
        for(Integer each : reactionTimes) {
            if(highestVal < each)
                highestVal = each;
        }
        return highestVal;
    }

    public Integer getSlowestReaction10() {
        if(reactionTimes.size() == 0)
            return 0;
        Integer highestVal = 0;
        for(int i=0; i<10; ++i) {
            if(highestVal < reactionTimes.get(i))
                highestVal = reactionTimes.get(i);
            if(i+1 >= reactionTimes.size())
                return highestVal;
        }
        return highestVal;
    }

    public Integer getSlowestReaction100() {
        if(reactionTimes.size() == 0)
            return 0;
        Integer highestVal = 0;
        for(int i=0; i<100; ++i) {
            if(highestVal < reactionTimes.get(i))
                highestVal = reactionTimes.get(i);
            if(i+1 >= reactionTimes.size())
                return highestVal;
        }
        return highestVal;
    }
}
