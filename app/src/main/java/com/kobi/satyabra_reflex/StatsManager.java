package com.kobi.satyabra_reflex;

import java.util.Collection;

/**
 * Created by kobitoko on 20/09/15.
 */
public interface StatsManager {

    public Integer getValue(Integer index);

    public void addValue(Integer value);

    public void eraseAllValues();

}
