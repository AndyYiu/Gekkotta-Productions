/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OverClocked;

import it.randomtower.engine.entity.Entity;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author admin
 */
public class Maps extends Entity {

    private TiledMap map;
    private boolean[][] blocked;
    private boolean[][] death;
    private boolean[][] camera;
    private int level, stage;
    public int TILESIZE;
    public int mapWidth;
    public int mapHeight;
    public int offsetX, offsetY;
    public float camX, camY;
    /*Description:Constructor for Tiled Map
     * pre: MainGame is created or entered
     * post:creates a Tiled Map based on level and stage
     */

    public Maps(float x, float y, int level, int stage) throws SlickException {//MainGameMaps
        super(x, y);
        this.level = level;
        this.stage = stage;
        offsetX = 0;
        offsetY = 0;
        switch (level) {
            case 1://Forest
                switch (stage) {
                    case 1:
                        map = new TiledMap("data/Tiles/TMX/MGLVL1.tmx");
                        break;
                    case 2:
                        map = new TiledMap("data/Tiles/ForestMap1a.tmx");
                        break;
                    case 3:
                        map = new TiledMap("data/Tiles/ForestMap1b.tmx");
                        break;
                    case 4:
                        map = new TiledMap("data/Tiles/ForestMap3.tmx");
                        break;
                    case 5://DRAGON
                        map = new TiledMap("data/Tiles/dragTestMap.tmx");
                        break;
                }
                break;
            case 2://UnderGround
                switch (stage) {
                    case 1:
                        map = new TiledMap("data/Tiles/RockMap1a.tmx");
                        break;
                    case 2:
                        map = new TiledMap("data/Tiles/RockMap1b.tmx");
                        break;
                    case 3:
                        map = new TiledMap("data/Tiles/MinesMap.tmx");
                        break;
                    case 4:
                        map = new TiledMap("data/Tiles/MinesMap2.tmx");
                        break;
                    case 5://BERGAMOT
                        map = new TiledMap("data/Tiles/bergTestMap.tmx");
                        break;
                }
                break;
            case 3://Snow
                switch (stage) {
                    case 1:
                        map = new TiledMap("data/Tiles/SnowMap1a.tmx");
                        break;
                    case 2:
                        map = new TiledMap("data/Tiles/SnowMap1b.tmx");
                        break;
                    case 3:
                        map = new TiledMap("data/Tiles/SnowMap2.tmx");
                        break;
                    case 4:
                        map = new TiledMap("data/Tiles/IceMap3.tmx");
                        break;
                    case 5://NIBELUM
                        map = new TiledMap("data/Tiles/NibelTestMap.tmx");
                        break;
                }
                break;

        }
        collisionCheck();
        deathCheck();
        TILESIZE = map.getTileHeight();
        mapWidth = map.getWidth();
        mapHeight = map.getHeight();
        setOriginalCameraView();
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        super.render(container, g);
        for (int i = 0; i < map.getLayerCount(); i++) {
            if (i != map.getLayerIndex("SpawnEnemyDroid") && i != map.getLayerIndex("SpawnEnemySoldier") && i != map.getLayerIndex("SpawnEnemySentinel") && i != map.getLayerIndex("Camera") && i != map.getLayerIndex("SpawnPlayer") && i != map.getLayerIndex("Portal") && i != map.getLayerIndex("Death") && i != map.getLayerIndex("Collision") && i != map.getLayerIndex("Boss")) {
                map.render(0, 0, (int) ((x - offsetX) * 0.1), (int) ((y - offsetY) * 0.1), 80, 60, i, false);
            }
        }

    }
    /*Description:Resets drawing offsets to 0
     * pre: n/a
     * post:n/a
     */

    public void resetOffsets() {
        offsetY = 0;
        offsetX = 0;
    }
    /*Description:Returns the current y of the 800 by 600 screen on the larger map
     * pre: n/a
     * post:n/a
     */

    public int getCurrentViewY() {
        return (int) ((y - offsetY) * 0.1);
    }
    /*Description:Returns the current x of the 800 by 600 screen on the larger map
     * pre: n/a
     * post:n/a
     */

    public int getCurrentViewX() {
        return (int) ((x - offsetX) * 0.1);
    }
    /*Description:Increases offsetX
     * pre: map is scrolling
     * post: view of map is shifted
     */

    public void setOffsetX(float offset) {
        offsetX += offset;
    }
    /*Description:Returns offsetX
     * pre: n/a
     * post:n/a
     */

    public int getOffsetX() {
        return offsetX * -1;
    }
    /*Description:Increases offsetY
     * pre: map is scrolling
     * post: view of map is shifted
     */

    public void setOffsetY(float offset) {
        offsetY += offset;
    }
    /*Description:Manually sets x and y
     * pre: n/a
     * post:n/a
     */

    public void setCoor(float x, float y) {
        this.x = x;
        this.y = y;
    }
    /*Description:Manually sets offsetx and offsety
     * pre: n/a
     * post:n/a
     */

    public void setOffsetCoor(int x, int y) {
        offsetX = x;
        offsetY = y;

    }
    /*Description:Returns offsetx and offsety
     * pre: n/a
     * post:n/a
     */

    public int getOffsetY() {
        return offsetY * -1;
    }
    /*Description:Checks to see whether the current coordinates in the paramteres are a collision tile on the Tiled Map
     * pre: n/a
     * post:n/a
     */

    public boolean isBlocked(float x, float y) {
        int xBlock = (int) x / TILESIZE;
        int yBlock = (int) y / TILESIZE;
        return blocked[xBlock][yBlock];
    }

    /*Description:Checks to see whether the current coordinates in the paramteres are a  death tile on the Tiled Map
     * pre: n/a
     * post:n/a
     */
    public boolean isDeath(float x, float y) {
        int xBlock = (int) x / TILESIZE;
        int yBlock = (int) y / TILESIZE;
        return death[xBlock][yBlock];
    }
    /*Description:Returns the spawn point X of the player on the tiled map
     * pre: n/a
     * post:n/a
     */

    public int getSpawnPointX() {
        int x = 0;
        for (int xAxis = 0; xAxis < map.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < map.getHeight(); yAxis++) {
                int tileID = map.getTileId(xAxis, yAxis, map.getLayerIndex("SpawnPlayer"));
                String value = map.getTileProperty(tileID, "spawn", "false");
                if ("true".equals(value)) {
                    x = xAxis * TILESIZE;
                }
            }
        }

        return x;
    }
    /*Description:Returns the spawn point Y of the player on the tiled map
     * pre: n/a
     * post:n/a
     */

    public int getSpawnPointY() {
        int y = 0;
        for (int xAxis = 0; xAxis < map.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < map.getHeight(); yAxis++) {
                int tileID = map.getTileId(xAxis, yAxis, map.getLayerIndex("SpawnPlayer"));
                String value = map.getTileProperty(tileID, "spawn", "false");
                if ("true".equals(value)) {
                    y = yAxis * TILESIZE;
                }
            }
        }

        return y;
    }
    /*Description:Resets the view of the map(the 800 by 600 square) back to its original location
     * pre: Player loads map again or map is just created
     * post: View of map is reset back to it's starting position based on the coordinates of tiled map
     */

    public void setOriginalCameraView() {
        for (int xAxis = 0; xAxis < map.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < map.getHeight(); yAxis++) {
                int tileID = map.getTileId(xAxis, yAxis, map.getLayerIndex("Camera"));
                String value = map.getTileProperty(tileID, "camera", "false");
                if ("true".equals(value)) {
                    this.x = xAxis * TILESIZE;
                    this.y = yAxis * TILESIZE - 600;
                    camX = xAxis * TILESIZE;
                    camY = yAxis * TILESIZE - 600;
                }
            }
        }

    }
    /*Description:Returns the number of sentinels
     * pre: n/a
     * post:n/a
     */

    public int getNumOfSentinel() {
        int num = 0;
        for (int xAxis = 0; xAxis < map.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < map.getHeight(); yAxis++) {
                int tileID = map.getTileId(xAxis, yAxis, map.getLayerIndex("SpawnEnemySentinel"));
                String value = map.getTileProperty(tileID, "spawn", "false");
                if ("true".equals(value)) {
                    num += 1;
                }
            }
        }
        return num;
    }
    /*Description:Returns the number of soldiers
     * post:n/a
     */

    public int getNumOfSoldier() {
        int num = 0;
        for (int xAxis = 0; xAxis < map.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < map.getHeight(); yAxis++) {
                int tileID = map.getTileId(xAxis, yAxis, map.getLayerIndex("SpawnEnemySoldier"));
                String value = map.getTileProperty(tileID, "spawn", "false");
                if ("true".equals(value)) {
                    num += 1;
                }
            }
        }
        return num;
    }
    /*Description:Returns the number of droids
     * pre: n/a
     * post:n/a
     */

    public int getNumOfDroid() {
        int num = 0;
        for (int xAxis = 0; xAxis < map.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < map.getHeight(); yAxis++) {
                int tileID = map.getTileId(xAxis, yAxis, map.getLayerIndex("SpawnEnemyDroid"));
                String value = map.getTileProperty(tileID, "spawn", "false");
                if ("true".equals(value)) {
                    num += 1;
                }
            }
        }
        return num;
    }
    /*Description:Returns the number of portals
     * pre: n/a
     * post:n/a
     */

    public int getNumOfPortal() {
        int num = 0;
        for (int xAxis = 0; xAxis < map.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < map.getHeight(); yAxis++) {
                int tileID = map.getTileId(xAxis, yAxis, map.getLayerIndex("Portal"));
                String value = map.getTileProperty(tileID, "portal", "false");
                if ("true".equals(value)) {
                    num += 1;
                }
            }
        }
        return num;
    }
    /*Description:Returns the number of collision tiles
     * pre: n/a
     * post:n/a
     */

    public int getNumOfCollisionTiles() {
        int num = 0;
        for (int xAxis = 0; xAxis < map.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < map.getHeight(); yAxis++) {
                int tileID = map.getTileId(xAxis, yAxis, map.getLayerIndex("Collision"));
                String value = map.getTileProperty(tileID, "blocked", "false");
                if ("true".equals(value)) {
                    num += 1;
                }
            }
        }
        return num;
    }
    /*Description:Returns an array list that containts the coordinates of where all collision tiles should be
     * pre: n/a
     * post:collision coordinates are returned
     */

    public ArrayList<Integer> getCollisionCoor() {
        ArrayList<Integer> collisionSpawn = new ArrayList<Integer>();
        for (int xAxis = 0; xAxis < map.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < map.getHeight(); yAxis++) {
                int tileID = map.getTileId(xAxis, yAxis, map.getLayerIndex("Collision"));
                String value = map.getTileProperty(tileID, "blocked", "false");
                if ("true".equals(value)) {
                    collisionSpawn.add(xAxis * TILESIZE);
                    collisionSpawn.add(yAxis * TILESIZE);
                }
            }
        }

        return collisionSpawn;
    }
    /*Description:Returns an array list that containts the coordinates of where the boss should be
     * pre: n/a
     * post:boss coordinates are returned
     */

    public ArrayList<Integer> getBossCoor() {
        ArrayList<Integer> bossSpawn = new ArrayList<Integer>();
        for (int xAxis = 0; xAxis < map.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < map.getHeight(); yAxis++) {
                int tileID = map.getTileId(xAxis, yAxis, map.getLayerIndex("Boss"));
                String value = map.getTileProperty(tileID, "spawn", "false");
                if ("true".equals(value)) {
                    bossSpawn.add(xAxis * TILESIZE);
                    bossSpawn.add(yAxis * TILESIZE);
                }
            }
        }

        return bossSpawn;
    }
    /*Description:Returns an array list that containts the coordinates of where Portal should be
     * pre: n/a
     * post:Portal coordinates are returned
     */

    public ArrayList<Integer> getPortalCoor() {
        ArrayList<Integer> portalSpawn = new ArrayList<Integer>();
        for (int xAxis = 0; xAxis < map.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < map.getHeight(); yAxis++) {
                int tileID = map.getTileId(xAxis, yAxis, map.getLayerIndex("Portal"));
                String value = map.getTileProperty(tileID, "portal", "false");
                if ("true".equals(value)) {
                    portalSpawn.add(xAxis * TILESIZE);
                    portalSpawn.add(yAxis * TILESIZE);
                }
            }
        }

        return portalSpawn;
    }
    /*Description:Returns an array list that containts the coordinates of where all sentinels should be
     * pre: n/a
     * post:Sentinel coordinates are returned
     */

    public ArrayList<Integer> getSentinelCoor() {
        ArrayList<Integer> sentinelSpawn = new ArrayList<Integer>();
        for (int xAxis = 0; xAxis < map.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < map.getHeight(); yAxis++) {
                int tileID = map.getTileId(xAxis, yAxis, map.getLayerIndex("SpawnEnemySentinel"));
                String value = map.getTileProperty(tileID, "spawn", "false");
                if ("true".equals(value)) {
                    sentinelSpawn.add(xAxis * TILESIZE);
                    sentinelSpawn.add(yAxis * TILESIZE);
                }
            }
        }

        return sentinelSpawn;
    }
 /*Description:Returns an array list that containts the coordinates of where all soldiers should be
     * pre: n/a
     * post:soldier coordinates are returned
     */
    public ArrayList<Integer> getSoldierCoor() {
        ArrayList<Integer> soldierSpawn = new ArrayList<Integer>();
        for (int xAxis = 0; xAxis < map.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < map.getHeight(); yAxis++) {
                int tileID = map.getTileId(xAxis, yAxis, map.getLayerIndex("SpawnEnemySoldier"));
                String value = map.getTileProperty(tileID, "spawn", "false");
                if ("true".equals(value)) {
                    soldierSpawn.add(xAxis * TILESIZE);
                    soldierSpawn.add(yAxis * TILESIZE);
                }
            }
        }

        return soldierSpawn;
    }
 /*Description:Returns an array list that containts the coordinates of where all droids should be
     * pre: n/a
     * post:droid coordinates are returned
     */
    public ArrayList<Integer> getDroidCoor() {
        ArrayList<Integer> droidSpawn = new ArrayList<Integer>();
        for (int xAxis = 0; xAxis < map.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < map.getHeight(); yAxis++) {
                int tileID = map.getTileId(xAxis, yAxis, map.getLayerIndex("SpawnEnemyDroid"));
                String value = map.getTileProperty(tileID, "spawn", "false");
                if ("true".equals(value)) {
                    droidSpawn.add(xAxis * TILESIZE);
                    droidSpawn.add(yAxis * TILESIZE);
                }
            }
        }
        return droidSpawn;
    }
 /*Description:Runs through all tiles in the tiled map and checks to see which tiles should be collision tiles
     * pre: n/a
     * post:n/a
     */
    public void collisionCheck() {

// build a collision map based on tile properties in the TileD map
        blocked = new boolean[map.getWidth()][map.getHeight()];
        for (int xAxis = 0; xAxis < map.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < map.getHeight(); yAxis++) {
                int tileID = map.getTileId(xAxis, yAxis, map.getLayerIndex("Collision"));
                String value = map.getTileProperty(tileID, "blocked", "false");
                if ("true".equals(value)) {
                    blocked[xAxis][yAxis] = true;

                }
            }
        }
    }
/*Description:Runs through all tiles in the tiled map and checks to see which tiles should be death tiles
     * pre: n/a
     * post:n/a
     */
    public void deathCheck() {

// build a collision map based on tile properties in the TileD map
        death = new boolean[map.getWidth()][map.getHeight()];
        for (int xAxis = 0; xAxis < map.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < map.getHeight(); yAxis++) {
                int tileID = map.getTileId(xAxis, yAxis, map.getLayerIndex("Death"));
                String value = map.getTileProperty(tileID, "death", "false");
                if ("true".equals(value)) {
                    death[xAxis][yAxis] = true;

                }
            }
        }
    }
}
