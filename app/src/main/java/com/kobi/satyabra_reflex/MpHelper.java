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
