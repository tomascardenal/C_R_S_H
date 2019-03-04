package com.example.tomascrd.c_r_s_h.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.tomascrd.c_r_s_h.R;
import com.example.tomascrd.c_r_s_h.components.VisualTimerComponent;
import com.example.tomascrd.c_r_s_h.structs.MapReference;
import com.example.tomascrd.c_r_s_h.structs.RecordReference;
import com.example.tomascrd.c_r_s_h.structs.eTimerSpeed;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Options manager for the game
 *
 * @author Tomás Cardenal López
 */
public class OptionsManager {

    /**
     * Determines if the music should be played or not
     */
    private boolean playMusic;
    /**
     * Determines if the sound effects should be played or not
     */
    private boolean playSoundEffects;
    /**
     * Determines if the phone should vibrate
     */
    private boolean doVibrate;
    /**
     * Determines if the player 1 keeps the velocity after the joystick gets deactivated or not
     */
    private boolean keepJoystickVelocityP1;
    /**
     * Determines if the player 2 keeps the velocity after the joystick gets deactivated or not
     */
    private boolean keepJoystickVelocityP2;
    /**
     * Timer Speed for game settings
     */
    private eTimerSpeed timerSpeed;
    /**
     * List containing the map references
     */
    private ArrayList<MapReference> mapReferences;
    /**
     * List containing the record references
     */
    private ArrayList<RecordReference> recordReferences;
    /**
     * SharedPreferences to store the options
     */
    private SharedPreferences preferences;
    /**
     * The application's Context
     */
    private Context context;

    /**
     * Default constructor, initalizes a new OptionsManager with the given Context
     *
     * @param context The context for this OptionsManager
     */
    public OptionsManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(GameConstants.PREFERENCES_NAME, Context.MODE_PRIVATE);
        this.mapReferences = new ArrayList<>();
        this.recordReferences = new ArrayList<>();
        loadOptions();
        loadMapList();
        loadRecords();
    }

    /**
     * Loads the values of the options if they are saved, or sets them to true if they are not saved
     */
    public void loadOptions() {
        playMusic = preferences.getBoolean(GameConstants.PREFERENCES_MUSIC, true);
        playSoundEffects = preferences.getBoolean(GameConstants.PREFERENCES_SOUNDEFFECTS, true);
        doVibrate = preferences.getBoolean(GameConstants.PREFERENCES_VIBRATE, true);
        keepJoystickVelocityP1 = preferences.getBoolean(GameConstants.PREFERENCES_KEEPJOYSTICKVELOCITY_P1, true);
        keepJoystickVelocityP2 = preferences.getBoolean(GameConstants.PREFERENCES_KEEPJOYSTICKVELOCITY_P2, true);
        timerSpeed = VisualTimerComponent.intToTimerSpeed(preferences.getInt(GameConstants.PREFERENCES_TIMERSPEED, 1));
    }

    /**
     * Saves the values of the options
     */
    public void saveOptions() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(GameConstants.PREFERENCES_MUSIC, playMusic);
        editor.putBoolean(GameConstants.PREFERENCES_SOUNDEFFECTS, playSoundEffects);
        editor.putBoolean(GameConstants.PREFERENCES_VIBRATE, doVibrate);
        editor.putBoolean(GameConstants.PREFERENCES_KEEPJOYSTICKVELOCITY_P1, keepJoystickVelocityP1);
        editor.putBoolean(GameConstants.PREFERENCES_KEEPJOYSTICKVELOCITY_P1, keepJoystickVelocityP2);
        editor.putInt(GameConstants.PREFERENCES_TIMERSPEED, VisualTimerComponent.timerSpeedToInt(timerSpeed));
        editor.commit();
    }

    /**
     * Determines if the play music option is on
     *
     * @return whether the music option is on or not
     */
    public boolean isPlayMusic() {
        return playMusic;
    }

    /**
     * Sets the value of the play music option
     *
     * @param playMusic the new value of the option
     */
    public void setPlayMusic(boolean playMusic) {
        this.playMusic = playMusic;
    }

    /**
     * Determines if the sound effects option is on
     *
     * @return whether the sound effects is on or not
     */
    public boolean isPlaySoundEffects() {
        return playSoundEffects;
    }

    /**
     * Sets the value of the sound effects option
     *
     * @param playSoundEffects the new value of the option
     */
    public void setPlaySoundEffects(boolean playSoundEffects) {
        this.playSoundEffects = playSoundEffects;
    }

    /**
     * Determines if the vibrate option is on or not
     *
     * @return whether the vibrate option is on or not
     */
    public boolean isDoVibrate() {
        return doVibrate;
    }

    /**
     * Sets the value of the vibrate option
     *
     * @param doVibrate the new value of the option
     */
    public void setDoVibrate(boolean doVibrate) {
        this.doVibrate = doVibrate;
    }

    /**
     * Determines if the player one keeps the velocity after the joystick gets deactivated or not
     *
     * @return whether the option is on or not
     */
    public boolean isKeepJoystickVelocityP1() {
        return this.keepJoystickVelocityP1;
    }

    /**
     * Sets the value of the keepJoystickVelocityP1 option
     *
     * @param keepJoystickVelocityP1 the new value of the option
     */
    public void setKeepJoystickVelocityP1(boolean keepJoystickVelocityP1) {
        this.keepJoystickVelocityP1 = keepJoystickVelocityP1;
    }

    /**
     * Determines if the player two keeps the velocity after the joystick gets deactivated or not
     *
     * @return whether the option is on or not
     */
    public boolean isKeepJoystickVelocityP2() {
        return this.keepJoystickVelocityP2;
    }

    /**
     * Sets the value of the keepJoystickVelocityP1 option
     *
     * @param keepJoystickVelocityP2 the new value of the option
     */
    public void setKeepJoystickVelocityP2(boolean keepJoystickVelocityP2) {
        this.keepJoystickVelocityP2 = keepJoystickVelocityP2;
    }

    /**
     * Returns the current eTimerSpeed
     *
     * @return the eTimerSpeed
     */
    public eTimerSpeed getTimerSpeed() {
        return timerSpeed;
    }

    /**
     * Sets the current eTimerSpeed
     *
     * @param timerSpeed the eTimerSpeed to set
     */
    public void setTimerSpeed(eTimerSpeed timerSpeed) {
        this.timerSpeed = timerSpeed;
    }

    /**
     * Saves the list of maps as a pair of id's and names
     *
     * @return true if the task was done correctly, false if not
     */
    public boolean saveMapList() {
        DataOutputStream output = null;
        try (FileOutputStream fos = context.openFileOutput(GameConstants.MAPLIST_FILE_NAME, Context.MODE_PRIVATE)) {
            output = new DataOutputStream(fos);
            for (int i = 0; i < mapReferences.size(); i++) {
                MapReference currentRef = mapReferences.get(i);
                output.writeInt(currentRef.mapId);
                output.writeUTF(currentRef.mapName);
            }
        } catch (IOException e) {
            return false;
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(GameConstants.PREFERENCES_MAPCOUNT, mapReferences.size());
        editor.commit();
        return true;
    }

    /**
     * Loads the current map list from private files into memory
     *
     * @return true if the task was done correctly, false if not
     */
    public boolean loadMapList() {
        int mapCount = preferences.getInt(GameConstants.PREFERENCES_MAPCOUNT, 0);
        try (FileInputStream fis = context.openFileInput(GameConstants.MAPLIST_FILE_NAME)) {
            DataInputStream input = new DataInputStream(fis);
            MapReference currentMap;
            for (int i = 0; i < mapCount; i++) {
                currentMap = new MapReference(
                        input.readInt(),
                        input.readUTF()
                );
                boolean add = true;
                for (MapReference ref : mapReferences) {
                    if (ref.mapId == currentMap.mapId) {
                        add = false;
                    }
                }
                if (add) {
                    mapReferences.add(currentMap);
                }
            }
            return true;
        } catch (IOException e) {
            Log.i("CrshDebug", e.getMessage() + " " + e.getStackTrace());
            return false;
        }
    }

    /**
     * Loads the records into Memory
     *
     * @return true if the task was done correctly, false if not
     */
    public boolean loadRecords() {
        int recordCount = preferences.getInt(GameConstants.PREFERENCES_RECORDCOUNT, 0);
        try (FileInputStream fis = context.openFileInput(GameConstants.MAPLIST_FILE_NAME)) {
            DataInputStream input = new DataInputStream(fis);
            for (int i = 0; i < recordCount; i++) {
                RecordReference currentRecord;
                currentRecord = new RecordReference(
                        input.readInt(),
                        input.readUTF()
                );
                boolean add = true;
                for (RecordReference ref : recordReferences) {
                    if (ref.highScore == currentRecord.highScore && ref.playerName.equals(currentRecord.playerName)) {
                        add = false;
                    }
                }
                if (add) {
                    recordReferences.add(currentRecord);
                }
            }
            return true;
        } catch (IOException e) {
            Log.i("CrshDebug", e.getMessage() + " " + e.getStackTrace());
            return false;
        }
    }

    /**
     * Saves the records
     *
     * @return true if the task was done correctly, false if not
     */
    public boolean saveRecords() {
        DataOutputStream output = null;
        try (FileOutputStream fos = context.openFileOutput(GameConstants.MAPLIST_FILE_NAME, Context.MODE_PRIVATE)) {
            output = new DataOutputStream(fos);
            for (int i = 0; i < recordReferences.size(); i++) {
                RecordReference currentRef = recordReferences.get(i);
                output.writeInt(currentRef.highScore);
                output.writeUTF(currentRef.playerName);
            }
        } catch (IOException e) {
            return false;
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(GameConstants.PREFERENCES_RECORDCOUNT, recordReferences.size());
        editor.commit();
        return true;
    }

    /**
     * Empties the records
     */
    public void emptyRecords() {
        recordReferences.clear();
        context.deleteFile(GameConstants.MAPLIST_FILE_NAME);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(GameConstants.PREFERENCES_RECORDCOUNT, recordReferences.size());
        editor.commit();
    }

    /**
     * Adds a map reference to the list
     *
     * @param reference The map reference to add
     * @return an int with the map list size or -1 if the map already exists in the list
     */
    public int addMap(MapReference reference) {
        for (MapReference ref : mapReferences) {
            if (ref.mapId == reference.mapId) {
                return -1;
            }
        }
        mapReferences.add(reference);
        return mapReferences.size();
    }

    /**
     * Adds a record reference to the list, if possible
     *
     * @param reference the reference to add
     */
    public void addRecord(RecordReference reference) {
        int ranking = -1;
        boolean add = true;
        for (RecordReference ref : recordReferences) {
            if (ref.highScore == reference.highScore && ref.playerName.equals(reference.playerName)) {
                add = false;
            }
        }
        if (add) {
            if (recordReferences.size() > 1) {
                for (int i = recordReferences.size() - 1; i >= 0; i--) {
                    if (recordReferences.get(i).highScore < reference.highScore) {
                        ranking = i;
                    }
                }
            }
            if (ranking != -1) {
                recordReferences.add(ranking, reference);
            } else if (recordReferences.size() == 0) {
                recordReferences.add(reference);
            } else if (recordReferences.size() <= 10) {
                if (ranking == -1) {
                    recordReferences.add(recordReferences.size(), reference);
                } else {
                    recordReferences.add(ranking, reference);
                }
            }
        }
        Collections.sort(recordReferences);
        if (recordReferences.size() > 10) {
            Log.i("CrshDebug", "recordsSize" + recordReferences.size());
            for (int i = recordReferences.size() - 1; i >= 10; i--) {
                Log.i("CrshDebug", "removing record index" + i);
                recordReferences.remove(i);
            }
            Log.i("CrshDebug", "recordsSize after" + recordReferences.size());
        }
    }

    /**
     * Checks if a score is a record
     *
     * @param score the score to check
     * @return true if it's a record, false if not
     */
    public boolean isRecord(int score) {
        if (recordReferences.size() < 10) {
            return true;
        }
        for (RecordReference ref : recordReferences) {
            if (ref.highScore < score) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the list of record references
     *
     * @return the record references
     */
    public ArrayList<RecordReference> getRecordReferences() {
        return recordReferences;
    }

    /**
     * Gets the id of a map by it's name
     *
     * @param name The name of the map
     * @return the map id
     */
    public int getMapId(String name) {
        for (MapReference ref : mapReferences) {
            if (ref.mapName.equals(name)) {
                return ref.mapId;
            }
        }
        return -1;
    }

    /**
     * Gets a list of the map names
     *
     * @return a list of the map names
     */
    public ArrayList<String> getMapNames() {
        ArrayList<String> mapNames = new ArrayList<>();
        for (MapReference ref : mapReferences) {
            mapNames.add(ref.mapName);
        }
        return mapNames;
    }

    /**
     * Gets a the map name for the corresponding map id
     *
     * @param id the id of the map to get the name of
     * @return the name of the map if it exists
     */
    public String getMapNameByID(int id) {
        if (id == -10) {
            return context.getString(R.string.defaultMapName);
        }
        for (MapReference ref : mapReferences) {
            if (ref.mapId == id) {
                return ref.mapName;
            }
        }
        return null;
    }

    public ArrayList<MapReference> getMapReferences() {
        return mapReferences;
    }

    /**
     * Changes the name of the map reference in the list if the id already exists
     *
     * @param reference the map reference to alter
     * @return true if the name was changed, false if it wasn't
     */
    public boolean changeNameIfExists(MapReference reference) {
        for (int i = mapReferences.size() - 1; i >= 0; i--) {
            if (mapReferences.get(i).mapId == reference.mapId && !mapReferences.get(i).mapName.equals(reference.mapName)) {
                mapReferences.get(i).mapName = reference.mapName;
                return true;
            }
        }
        return false;
    }
}
