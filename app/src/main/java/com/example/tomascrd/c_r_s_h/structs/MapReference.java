package com.example.tomascrd.c_r_s_h.structs;

/**
 * Represents a map reference for the map lists
 *
 * @author Tomás Cardenal López
 */
public class MapReference {

    /**
     * The id of the map
     */
    public int mapId;
    /**
     * The name of the map
     */
    public String mapName;

    /**
     * Starts a map reference on this parameters
     *
     * @param mapId   the map reference's id
     * @param mapName the map reference's name
     */
    public MapReference(int mapId, String mapName) {
        this.mapId = mapId;
        this.mapName = mapName;
    }
}

