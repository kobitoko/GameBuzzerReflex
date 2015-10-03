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
 * FileManagager Class's purpose is to provide write and read JSON functions
 * and access to the StatsManager object.
 * Also provide Activity inheritance for it's subclasses.
 */
public class FileManager extends Activity {

    protected StatsManager stats;
    private static final String FILENAME = "stats.dat";

    // Saves StatsManager Object into a JSON file.
    public void saveStats() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, MODE_PRIVATE);
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

    // Loads from a JSON file into the local StatsManager Object.
    public void loadStats() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
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
