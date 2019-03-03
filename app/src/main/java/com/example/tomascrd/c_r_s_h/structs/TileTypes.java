package com.example.tomascrd.c_r_s_h.structs;

import com.example.tomascrd.c_r_s_h.components.TileComponent;

/**
 * Represents the different types of tile and provides tools to translate the eTileType enum
 *
 * @author Tomás Cardenal López
 */
public class TileTypes {

    /**
     * Enumerates the various types of tile
     */
    public enum eTileType {
        TILE_BORDER,    //0, border tile
        TILE_PATH,      //1, path tile
        TILE_BREAKONE,  //2, one hit breakable tile
        TILE_BREAKTWO,  //3, two hit breakable tile
    }

    /**
     * Converts integers to eTileType
     *
     * @param tileType the eTileType to convert
     * @return the corresponding integer
     * @see eTileType
     */
    public static int tileTypeToInt(eTileType tileType) {
        switch (tileType) {
            case TILE_BORDER:
                return 0;
            case TILE_PATH:
                return 1;
            case TILE_BREAKONE:
                return 2;
            case TILE_BREAKTWO:
                return 3;
            default:
                return -1;
        }
    }

    /**
     * Converts TILE_TYPEs to integer
     *
     * @param tileType the integer to convert
     * @return the corresponding eTileType
     * @see eTileType
     */
    public static eTileType intToTileType(int tileType) {
        switch (tileType) {
            case 0:
                return eTileType.TILE_BORDER;
            case 1:
                return eTileType.TILE_PATH;
            case 2:
                return eTileType.TILE_BREAKONE;
            case 3:
                return eTileType.TILE_BREAKTWO;
            default:
                return null;
        }
    }

}
