package com.kobi.satyabra_reflex;

import android.app.Activity;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by kobitoko on 29/09/15.
 */
public class FileManager extends Activity {

    protected StatsManager stats;

    public void saveStats() {
        try {
            FileOutputStream fos = openFileOutput(StatsManager.FILENAME, MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(stats, writer);
            writer.flush();
            fos.close();
        } catch(FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadStats() {
        try {
            FileInputStream fis = openFileInput(StatsManager.FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            stats = gson.fromJson(in, StatsManager.class);
            fis.close();
            in.close();
        } catch(FileNotFoundException e) {
            // nothing to do
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}
