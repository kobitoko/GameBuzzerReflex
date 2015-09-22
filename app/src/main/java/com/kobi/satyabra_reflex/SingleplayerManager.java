package com.kobi.satyabra_reflex;

/**
 * Created by kobitoko on 20/09/15.
 */
public abstract class SingleplayerManager implements DataManager {

    @Override
    abstract public void saveData();

    @Override
    abstract public void loadData();

}
