package com.kobi.satyabra_reflex;

import android.app.Activity;

/**
 * Created by kobitoko on 20/09/15.
 * For using Shared Preferences http://developer.android.com/guide/topics/data/data-storage.html#pref
 */
public interface DataManager {

    public static final String FILENAME = "datas";
    public static final int MODE = Activity.MODE_PRIVATE;

    public void saveData();

    public void loadData();

}
