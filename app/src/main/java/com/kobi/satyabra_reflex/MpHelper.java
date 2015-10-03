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

/**
 * MpHelper class is a singleton that helps whoese purpose is to help the MultiPlayer
 * for storing and retrieving the amount of players playing, and which player
 * was the first to press, as well to keep track of the button's state.
 */
public class MpHelper {

    private static final MpHelper instance = new MpHelper();
    private Integer players = 0;
    private Integer playerFirst = 0;
    private Boolean playerCanPress = Boolean.TRUE;

    // Prevents a new instance being created.
    private MpHelper() { }

    // Retrieve the single instance from the class.
    public static MpHelper getInstance() {
        return instance;
    }

    public void setPlayerNumbers(Integer numberOfPlayers) {
        players = numberOfPlayers;
    }

    public Integer getPlayerNumbers() {
        return players;
    }

    public Integer getPlayerFirst() {
        return playerFirst;
    }

    public void setPlayerFirst(Integer playerFirstToPress) {
        playerFirst = playerFirstToPress;
        playerCanPress = Boolean.FALSE;
    }

    public Boolean playerCanPress() {
        return playerCanPress;
    }

    public void playerCanPressReset() {
        playerFirst = 0;
        playerCanPress = Boolean.TRUE;
    }

}
