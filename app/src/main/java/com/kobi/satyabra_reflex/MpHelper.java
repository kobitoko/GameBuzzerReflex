package com.kobi.satyabra_reflex;

/**
 * Created by kobitoko on 27/09/15.
 * Simple Singleton
 */
public class MpHelper {

    private static final MpHelper instance = new MpHelper();
    private Integer players = 0;
    private Integer playerFirst = 0;
    private Boolean playerCanPress = Boolean.TRUE;

    private MpHelper() {}

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
