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

import java.util.ArrayList;
import java.util.Collections;

/**
 * StatsCalculator calculates statistics for Average times, Median times, Minimum, and Maximum.
 * All for the entire ArrayList, and for the top 100 and top 10 entries in the ArrayList.
 */
public class StatsCalculator {

    //------------------------ Average Times ---------------------------------------------

    public Integer getAverageReaction(ArrayList<Integer> reactionTimes) {
        if(reactionTimes.size() == 0)
            return 0;
        Integer allValues = 0;
        for(Integer each : reactionTimes) {
            allValues += each;
        }
        return Math.round(allValues / reactionTimes.size());
    }

    public Integer getAverageReaction10(ArrayList<Integer> reactionTimes) {
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

    public Integer getAverageReaction100(ArrayList<Integer> reactionTimes) {
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

    //------------------------ Median Times ---------------------------------------------

    public Integer getMedianReaction(ArrayList<Integer> reactionTimes) {
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

    public Integer getMedianReaction10(ArrayList<Integer> reactionTimes) {
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

    public Integer getMedianReaction100(ArrayList<Integer> reactionTimes) {
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

    //------------------------ Minimum times ---------------------------------------------

    public Integer getFastestReaction(ArrayList<Integer> reactionTimes) {
        if(reactionTimes.size() == 0)
            return 0;
        Integer lowestVal = 999999;
        for(Integer each : reactionTimes) {
            if(lowestVal > each)
                lowestVal = each;
        }
        return lowestVal;
    }

    public Integer getFastestReaction10(ArrayList<Integer> reactionTimes) {
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

    public Integer getFastestReaction100(ArrayList<Integer> reactionTimes) {
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

    //------------------------ Maximum times ---------------------------------------------

    public Integer getSlowestReaction(ArrayList<Integer> reactionTimes) {
        if(reactionTimes.size() == 0)
            return 0;
        Integer highestVal = 0;
        for(Integer each : reactionTimes) {
            if(highestVal < each)
                highestVal = each;
        }
        return highestVal;
    }

    public Integer getSlowestReaction10(ArrayList<Integer> reactionTimes) {
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

    public Integer getSlowestReaction100(ArrayList<Integer> reactionTimes) {
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
