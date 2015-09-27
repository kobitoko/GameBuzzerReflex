package com.kobi.satyabra_reflex;

/**
 * Created by kobitoko on 27/09/15.
 * Simple Singleton
 */
public class MpPlayers {

    private static final MpPlayers instance = new MpPlayers();
    private Integer players = 0;

    private MpPlayers() {}

    public static MpPlayers getInstance() {
        return instance;
    }

    public void setPlayerNumbers(Integer numberOfPlayers) {
        players = numberOfPlayers;
    }

    public Integer getPlayerNumbers() {
        return players;
    }

}
