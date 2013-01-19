/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OverClocked;

import com.sun.org.apache.xpath.internal.axes.OneStepIterator;
import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.World;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 *
 * @author admin
 */
public class MainGameState extends World {

    private ModifySaveSlotXML modSave = new ModifySaveSlotXML();
    private ReadSaveSlotsXML readSave = new ReadSaveSlotsXML();
    private MainGamePlayer player;
    private MainGamePlayerArms arms;
    private ArrayList<MainGameBullet> pBullets = new ArrayList<MainGameBullet>();
    private ArrayList<MainGameEnemyBullet> eBullets = new ArrayList<MainGameEnemyBullet>();
    private Image healthGUI, hacksGUI, gunScoreGUI, gameOverPic;
    private int time = 0, enemyBulletCount;
    private boolean reloading = false;
    private Soldier[] soldier;
    private Sentinel[] sentinel;
    private Droid[] droid;
    private Boss boss;
    private CollisionTile[] tiles;
    private Portal[] portals;
    private ArrayList<Integer> sentinelSpawn = new ArrayList<Integer>();
    private ArrayList<Integer> soldierSpawn = new ArrayList<Integer>();
    private ArrayList<Integer> droidSpawn = new ArrayList<Integer>();
    private ArrayList<Integer> bossSpawn = new ArrayList<Integer>();
    private ArrayList<Integer> tilesSpawn = new ArrayList<Integer>();
    private ArrayList<Integer> portalSpawn = new ArrayList<Integer>();
    private int[] enemyCoor;
    private Maps collisionMap;
    private boolean gameOver, loaded, start;
    private int hitboxX;
    private int level, stage, delta;
    private float collisionMapBoundaryX;//The maximum distance the player should be able to move in the map until the map should stop scrolling
    private float collisionMapBoundaryY;
    private float scrollXCapLeft, scrollXCapRight, scrollYCapUp, scrollYCapDown;
    private MapImage map;
    public static Constants constants = new Constants();
    public Image mouse;
    private Music bgm;
    private Sound reload;
    private int saveSlot;
    /*constructor
     *
     *pre: get's the coordinates of the tile
     *post:set the collision tile made in "Tiled" to be a collision tile in the game
     */

    public MainGameState(int id, GameContainer container, int l, int s, int slot) throws SlickException {
        super(id, container);
        scrollXCapLeft = 300;
        scrollXCapRight = 500;
        scrollYCapUp = 200;
        scrollYCapDown = 400;

    }
    /*Description: Sets the level and stage for the main game to run
     * pre: User clicked Go on loadout 
     * post: MainGame recieves the info from save file
     */

    public void setLevelAndStageAndSlot(int l, int s, int slot) {
        this.saveSlot = slot;
        this.level = l;
        this.stage = s;

    }
    /*Description:Called everytime this state is entered
     * pre:n/a
     * post:n/a
     */

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        super.enter(container, game);
        if (stage != 5) {
            bgm = ResourceManager.getMusic("menuThemeShort");
        } else if (stage == 5) {
            bgm = ResourceManager.getMusic("ZeroAllies");
        }
        bgm.play();

        reload = ResourceManager.getSound("reload");
        start = false;
        time = 5000;
        loaded = false;
        gameOver = false;
        collisionMap = new Maps(0, 0, level, stage);
        map = new MapImage(0, 0, level, stage);
        collisionMap.setOriginalCameraView();

        collisionMapBoundaryX = ((collisionMap.mapWidth * collisionMap.TILESIZE) - 300);
        collisionMapBoundaryY = ((collisionMap.mapHeight * collisionMap.TILESIZE) - 200);
        add(map);
        add(collisionMap);
        player = new MainGamePlayer((collisionMap.getSpawnPointX()) - collisionMap.camX, (collisionMap.getSpawnPointY()) - collisionMap.camY, (collisionMap.getSpawnPointX()), (collisionMap.getSpawnPointY()), 1, saveSlot);
        hitboxX = 28;
        player.shotsFired = 0;
        arms = new MainGamePlayerArms(player.x, player.y, 1, player.pMapX, player.pMapY, saveSlot);
        arms.setCoordinates(player.x + 17, player.y + 14);
        arms.setMapCoor(player.pMapX + 17, player.pMapY + 14);
        add(player);
        add(arms);
        if (stage != 5) {
            sentinelSpawn = collisionMap.getSentinelCoor();
            soldierSpawn = collisionMap.getSoldierCoor();
            droidSpawn = collisionMap.getDroidCoor();
            soldier = new Soldier[collisionMap.getNumOfSoldier()];
            sentinel = new Sentinel[collisionMap.getNumOfSentinel()];
            droid = new Droid[collisionMap.getNumOfDroid()];
            spawnEnemy("soldier");
            spawnEnemy("droid");
            spawnEnemy("sentinel");
            sentinelSpawn.clear();
            sentinelSpawn.trimToSize();
            soldierSpawn.clear();
            soldierSpawn.trimToSize();
            droidSpawn.clear();
            droidSpawn.trimToSize();

        } else if (stage == 5) {
            portalSpawn = collisionMap.getPortalCoor();
            portals = new Portal[collisionMap.getNumOfPortal()];
            spawnPortal();
            portalSpawn.clear();
            portalSpawn.trimToSize();
            bossSpawn = collisionMap.getBossCoor();
            enemyCoor = toIntArray(bossSpawn);
            for (int x = 0; x < enemyCoor.length; x += 2) {
                boss = new Boss(enemyCoor[x] - collisionMap.camX, enemyCoor[x + 1] - collisionMap.camY, enemyCoor[x], enemyCoor[x + 1], level);
                add(boss);

            }
            bossSpawn.clear();
            bossSpawn.trimToSize();
        }

        portalSpawn = collisionMap.getPortalCoor();
        portals = new Portal[collisionMap.getNumOfPortal()];
        spawnPortal();
        portalSpawn.clear();
        portalSpawn.trimToSize();
        tilesSpawn = collisionMap.getCollisionCoor();
        tiles = new CollisionTile[collisionMap.getNumOfCollisionTiles()];

        spawnCollisionTiles();

        tilesSpawn.clear();
        tilesSpawn.trimToSize();

        loaded = true;
        enemyBulletCount = 0;

    }
    /*Description: called everytime this state is left
     * pre: n/a
     * post:n/a
     */

    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
        super.leave(container, game);
        bgm.stop();

        bgm = null;
    }
    /*Description: Turns all objects into null,destroys then, and removes them from the world, freeing up memory when not being used anymore
     * pre:Level is complete
     * post:All objects in this state are destroyed
     */

    public void resetLevel() {
        if (stage != 5) {
            for (int i = 0; i < sentinel.length; i++) {
                if (sentinel[i] != null) {
                    sentinel[i].removedFromWorld();
                    sentinel[i].destroy();
                    sentinel[i] = null;
                }
            }
            for (int i = 0; i < droid.length; i++) {
                if (droid[i] != null) {
                    droid[i].removedFromWorld();
                    droid[i].destroy();
                    droid[i] = null;
                }
            }
            for (int i = 0; i < soldier.length; i++) {
                if (soldier[i] != null) {
                    soldier[i].removedFromWorld();
                    soldier[i].destroy();
                    soldier[i] = null;
                }
            }

        }
        player.shotsFired = 0;
        pBullets.clear();
        pBullets.trimToSize();
        player.removedFromWorld();
        player.destroy();
        eBullets.clear();
        eBullets.trimToSize();
        enemyBulletCount = 0;

        this.clear();


    }
    /*Description: Spawns enemies based on the set coordinates from Tiled Map. Takes in the type of enemy in parameters.
     * pre:MainGame state enter is called 
     * post: Enemy objects are created on the map
     */

    public void spawnEnemy(String type) {
        if (type.equalsIgnoreCase("sentinel")) {
            int num = 0;
            enemyCoor = toIntArray(sentinelSpawn);
            for (int x = 0; x < enemyCoor.length; x += 2) {
                sentinel[num] = new Sentinel(enemyCoor[x] - collisionMap.camX, enemyCoor[x + 1] - collisionMap.camY, enemyCoor[x], enemyCoor[x + 1]);

                add(sentinel[num]);
                num += 1;
            }
        } else if (type.equalsIgnoreCase("soldier")) {
            int num = 0;
            enemyCoor = toIntArray(soldierSpawn);
            for (int x = 0; x < enemyCoor.length; x += 2) {
                soldier[num] = new Soldier(enemyCoor[x] - collisionMap.camX, enemyCoor[x + 1] - collisionMap.camY, enemyCoor[x], enemyCoor[x + 1]);

                add(soldier[num]);
                num += 1;
            }
        } else if (type.equalsIgnoreCase("droid")) {
            int num = 0;
            enemyCoor = toIntArray(droidSpawn);
            for (int x = 0; x < enemyCoor.length; x += 2) {
                droid[num] = new Droid(enemyCoor[x] - collisionMap.camX, enemyCoor[x + 1] - collisionMap.camY, enemyCoor[x], enemyCoor[x + 1]);

                add(droid[num]);
                num += 1;
            }
        }

    }
    /*Description: Creates 10 by 10 objects that regulate collision for bullets based on the collion tiles in Tiled map
     * pre:Enter state is called, level is loading
     * post: Collision tiles are created 
     */

    public void spawnCollisionTiles() {
        int num = 0;
        enemyCoor = toIntArray(tilesSpawn);
        for (int x = 0; x < enemyCoor.length; x += 2) {
            tiles[num] = new CollisionTile(enemyCoor[x], enemyCoor[x + 1]);
            add(tiles[num]);
            num += 1;
        }
    }
    /*Description: Portal is created in appropriate place based on Tiled map location
     * pre: Enter state is called, level loading
     * post: Portal is created
     */

    public void spawnPortal() {
        int num = 0;
        enemyCoor = toIntArray(portalSpawn);
        for (int x = 0; x < enemyCoor.length; x += 2) {
            portals[num] = new Portal(enemyCoor[x] - collisionMap.camX, enemyCoor[x + 1] - collisionMap.camY);
            add(portals[num]);
            num += 1;
        }
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        super.update(container, game, delta);
        this.delta = delta;

        //3-5 second timer to tell the player to get ready
        if (start == false) {
            time -= delta;
            if (time < 1000) {
                time = 5000;
                start = true;
                if (stage == 5) {
                    boss.comeAlive();
                }
            }
        }
        if (loaded) {
            if (gameOver == false) {
                Input input = container.getInput();
                player.x = (int) player.x;
                player.y = (int) player.y;
                player.pMapX = (int) player.pMapX;
                player.pMapY = (int) player.pMapY;
//Sets position and direction of player and arms relative to mouse
                if ((arms.getCurrentAngle() >= 90 && arms.getCurrentAngle() <= 270) && player.getFacing() == 1) {// face Left if facing right

                    player.setFacing(0);
                    player.changeDirection();
                } else if ((arms.getCurrentAngle() >= 85 && arms.getCurrentAngle() <= 275) && player.getFacing() == 0) {// face right if facing left

                    player.setFacing(1);
                    player.changeDirection();
                }

                arms.setDirec(player.getFacing());
                arms.changeDirection();
                //Sets position of arms based on player coordinates and what it is currently doing(jumping, running lying down)
                if (player.getJumping() == 0) {
                    if (player.getFacing() == 1) {
                        arms.setCoordinates(player.x + 17, player.y + 14);
                        arms.setMapCoor(player.pMapX + 17, player.pMapY + 14);
                    } else if (player.getFacing() == 0) {
                        arms.setCoordinates(player.x - 18, player.y + 17);
                        arms.setMapCoor(player.pMapX - 18, player.pMapY + 17);
                    }
                } else if (player.getJumping() == 1) {
                    if (player.getFacing() == 1) {
                        arms.setCoordinates(player.x + 17, player.y + 6);
                        arms.setMapCoor(player.pMapX + 17, player.pMapY + 6);
                    } else if (player.getFacing() == 0) {
                        arms.setCoordinates(player.x - 17, player.y + 6);
                        arms.setMapCoor(player.pMapX - 17, player.pMapY + 6);
                    }
                } else if (player.getJumping() == 2) {
                    if (player.getFacing() == 1) {
                        arms.setCoordinates(player.x + 15, player.y + 17);
                        arms.setMapCoor(player.pMapX + 15, player.pMapY + 17);
                    } else if (player.getFacing() == 0) {
                        arms.setCoordinates(player.x - 15, player.y + 17);
                        arms.setMapCoor(player.pMapX - 15, player.pMapY + 17);
                    }
                }

                if (player.getLyingDown()) {
                    if (player.getFacing() == 0) {
                        arms.setCoordinates(player.x - 30, player.y + 75);
                        arms.setMapCoor(player.pMapX - 30, player.pMapY + 75);
                    } else if (player.getFacing() == 1) {
                        arms.setCoordinates(player.x + 69, player.y + 74);
                        arms.setMapCoor(player.pMapX + 69, player.pMapY + 74);
                    }

                }
                if (player.getOnGround() == false && player.fly == false) {
                    player.pMapY += player.getSpeed();
                }

                //Checks to see if player is on ground or not
                if (!collisionMap.isBlocked(player.pMapX + hitboxX + 48, player.pMapY + player.getSpriteHeight() + collisionMap.TILESIZE) && !collisionMap.isBlocked(player.pMapX + hitboxX, player.pMapY + player.getSpriteHeight() + collisionMap.TILESIZE)) {
                    player.setOnGround(false);
                }
                if (collisionMap.isBlocked(player.pMapX + hitboxX + 48, player.pMapY + player.getSpriteHeight() + collisionMap.TILESIZE) || collisionMap.isBlocked(player.pMapX + hitboxX, player.pMapY + player.getSpriteHeight() + collisionMap.TILESIZE)) {
                    player.setOnGround(true);
                    player.setReadjusting(false);
                }


                //If player's head hits a collion tile, they will automatically start falling down, prevents player from jumping out of map
                if (collisionMap.isBlocked(player.pMapX + hitboxX + 48, player.pMapY + 11 - collisionMap.TILESIZE) || collisionMap.isBlocked(player.pMapX + hitboxX, player.pMapY + 11 - collisionMap.TILESIZE) && player.getJumping() == 1) {
                    player.setJumping(2);
                }
                //Checks if player has hit death spikes
                if (player.battleGrieves != true) {
                    if (collisionMap.isDeath(player.pMapX, player.pMapY + player.getSpriteHeight())) {
                        gameOver = true;
                        resetLevel();
                        game.enterState(constants.GAMEOVERSTATE);
                    }
                }
                //Check to see if player has lost all health
                if (player.getHealthPercent() <= 0) {
                    gameOver = true;
                    resetLevel();
                    game.enterState(constants.GAMEOVERSTATE);
                }
                if (start) {
                    //MOUSE SHOOTING///////////////////////////

                    if (reloading == false) {
                        if (player.shotsFired < player.ammo) {
                            if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                                if (arms.getjustShot() != true) {
                                    arms.shoot();
                                    float targetAngle = (float) arms.getTargetAngle(arms.x + 55, arms.y + 30, container.getInput().getMouseX(), container.getInput().getMouseY());
                                    //19,48 = point of rotation
                                    targetAngle += 90;
                                    pBullets.add(arms.generateBullet(targetAngle));
                                    add(pBullets.get(player.shotsFired));
                                    player.shotsFired += 1;

                                }
                            }
                        }
                    } else if (reloading) {
                        time += delta;
                        if (time >= player.reloadTime) {
                            player.shotsFired = 0;
                            time = 0;
                            reloading = false;
                            pBullets.clear();
                            pBullets.trimToSize();

                        }
                    }


                    if (input.isKeyPressed(Input.KEY_R)) {
                        reloading = true;
                        reload.play();
                    }

                    //PLAYER MOVEMENT///////////////////////////////////////////////
                    if (input.isKeyPressed(Input.KEY_W) && player.fly == false) {//Flying or jumping up
                        player.setDirection(Input.KEY_W, delta);
                    } else if (input.isKeyDown(Input.KEY_W) && player.fly) {
                        if (player.noClip != true) {
                            if (!collisionMap.isBlocked(player.pMapX + player.hitboxOffsetX, player.pMapY - collisionMap.TILESIZE)) {
                                player.setDirection(Input.KEY_W, delta);
                            }
                        } else if (player.noClip) {
                            player.setDirection(Input.KEY_W, delta);
                        }
                    }//Moves player Right
                    if (input.isKeyDown(Input.KEY_D)) {
                        if (player.pMapX + player.getSpriteWidth() < collisionMapBoundaryX + 500) {
                            if (player.noClip != true) {
                                if (!collisionMap.isBlocked(player.pMapX + 54 + collisionMap.TILESIZE, player.pMapY + player.getSpriteHeight())) {
                                    player.onSlope = false;
                                    player.setDirection(Input.KEY_D, delta);
                                }
                                if (collisionMap.isBlocked(player.pMapX + 54 + collisionMap.TILESIZE, player.pMapY + player.getSpriteHeight()) && !collisionMap.isBlocked(player.pMapX + 54 + collisionMap.TILESIZE, player.pMapY + player.getSpriteHeight() - collisionMap.TILESIZE)) {//Slope walking
                                    //Move up slope
                                    player.onSlope = true;
                                    player.setDirection(Input.KEY_D, delta);
                                }


                            } else if (player.noClip) {
                                player.setDirection(Input.KEY_D, delta);
                            }
                            if (player.x > scrollXCapRight && player.pMapX < collisionMapBoundaryX) {
                                player.x -= player.getPlayerSpeed();
                                moveEBullets(0, 1);
                                collisionMap.setOffsetX(-player.getPlayerSpeed());//Moves all collision tiles to the left   
                                moveAllEnemiesX(1);
                            }
                        }
                    }

                    //Moves player left
                    if (input.isKeyDown(Input.KEY_A)) {
                        if (player.x > 0 && player.pMapX >= 0) {
                            if (player.noClip != true) {
                                if (!collisionMap.isBlocked(player.pMapX + 7 - collisionMap.TILESIZE, player.pMapY + player.getSpriteHeight())) {
                                    player.onSlope = false;
                                    player.setDirection(Input.KEY_A, delta);
                                }
                                if (collisionMap.isBlocked(player.pMapX + 7 - collisionMap.TILESIZE, player.pMapY + player.getSpriteHeight()) && !collisionMap.isBlocked(player.pMapX + 7 - collisionMap.TILESIZE, player.pMapY + player.getSpriteHeight() - collisionMap.TILESIZE)) {
                                    player.onSlope = true;
                                    player.setDirection(Input.KEY_A, delta);
                                }


                            } else if (player.noClip) {
                                player.setDirection(Input.KEY_A, delta);
                            }
                            if (collisionMap.getOffsetX() >= 0) {
                                if (player.x < scrollXCapLeft && player.pMapX >= 0) {
                                    player.x += player.getPlayerSpeed();
                                    moveEBullets(0, -1);
                                    collisionMap.setOffsetX(player.getPlayerSpeed());//Moves all collision tiles one to the Right
                                    moveAllEnemiesX(-1);
                                }
                            }

                        }
                    }
                    if (input.isKeyDown(Input.KEY_S) && player.fly) {//Fly down
                        if (player.noClip != true) {
                            if (!collisionMap.isBlocked(player.pMapX + 20, player.pMapY + player.getSpriteHeight() + collisionMap.TILESIZE)) {
                                player.setDirection(Input.KEY_S, delta);
                            }
                        } else if (player.noClip) {
                            player.setDirection(Input.KEY_S, delta);
                        }

                    } else if (input.isKeyDown(Input.KEY_S) && player.fly != true) {
                        if (player.getOnGround()) {
                            player.setDirection(Input.KEY_S, delta);
                        }

                    }
                    if (!input.isKeyDown(Input.KEY_D) && !input.isKeyDown(Input.KEY_A) && player.getJumping() == 0 && !input.isKeyDown(Input.KEY_S)) {//Resets animation back to standing if not moving right or left
                        player.setDefaultAnimation(false, 0);
                        player.setLyingDown(false);
                    }
                    //Mouse Blink
                    if (player.teleport) {
                        if (input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
                            float oldX = player.x;
                            float oldY = player.y;
                            float mouseX = input.getMouseX();
                            float mouseY = input.getMouseY();
                            player.x = mouseX;
                            player.y = mouseY;
                            float difX = mouseX - oldX;
                            float difY = mouseY - oldY;
                            player.pMapX += difX;
                            player.pMapY += difY;
                        }
                    }
                    //Freeze Time
                    if (player.getFreezeTime()) {
                        freezeTime();
                    } else if (player.getFreezeTime() == false) {
                        unFreezeTime();
                    }

                }


                //Pan Scrolling///
                if (player.x + player.getSpriteWidth() > scrollXCapRight && player.pMapX + player.getSpriteWidth() < collisionMapBoundaryX) {
                    player.x -= player.getPlayerSpeed();
                    moveAllEnemiesX(1);
                    collisionMap.setOffsetX(-player.getPlayerSpeed());
                    moveEBullets(0, 1);

                }
                if (player.x < scrollXCapLeft && player.pMapX > 0 && collisionMap.getOffsetX() >= 0) {
                    player.x += player.getPlayerSpeed();
                    moveAllEnemiesX(-1);
                    collisionMap.setOffsetX(player.getPlayerSpeed());
                    moveEBullets(0, -1);

                }
                if (player.y < scrollYCapUp && player.pMapY > 0 && (collisionMap.y + collisionMap.getOffsetY() >= 0)) {
                    player.y += player.getPlayerSpeed();
                    moveAllEnemiesY(-1, true);
                    collisionMap.setOffsetY(player.getPlayerSpeed());
                    moveEBullets(1, -1);

                }
                if (player.y + player.getSpriteHeight() > scrollYCapDown && ((player.pMapY + player.getSpriteHeight()) < collisionMapBoundaryY) && (collisionMap.y + collisionMap.getOffsetY() <= collisionMap.mapHeight * collisionMap.TILESIZE)) {
                    player.y -= player.getPlayerSpeed();
                    moveAllEnemiesY(1, true);
                    collisionMap.setOffsetY(-player.getPlayerSpeed());
                    moveEBullets(1, 1);
                }
                if (stage != 5) {
                    if (start) {
                        //ENEMIES//
                        for (int i = 0; i < sentinel.length; i++) {
                            if (sentinel[i] != null) {
                                sentinel[i].inScreenRange(collisionMap.getCurrentViewX(), collisionMap.getCurrentViewY());
                                if (sentinel[i].getOnScreen()) {
                                    if (sentinel[i].dead() == false) {
                                        if (!sentinel[i].getFrozen()) {
                                            if ((sentinel[i].mapX - player.pMapX) > 0) {
                                                sentinel[i].setFacing(0);
                                            } else if ((sentinel[i].mapX - player.pMapX) < 0) {
                                                sentinel[i].setFacing(1);
                                            }
                                            sentinel[i].inRange(player.pMapX, player.pMapY);
                                            if (sentinel[i].getInRange()) {
                                                sentinel[i].shoot();
                                                if (sentinel[i].getJustShot() == false && (sentinel[i].getAnimationFrame() == 10 || sentinel[i].getAnimationFrame() == 11)) {
                                                    eBullets.add(sentinel[i].genBullet(player.x, player.y));

                                                    add(eBullets.get(enemyBulletCount));
                                                    enemyBulletCount += 1;
                                                }
                                            }
                                        }


                                    }
                                }
                            }
                        }
                        for (int i = 0; i < droid.length; i++) {
                            if (droid[i] != null) {
                                droid[i].inScreenRange(collisionMap.getCurrentViewX(), collisionMap.getCurrentViewY());
                                if (droid[i].getOnScreen() == true) {
                                    if (droid[i].dead() == false) {
                                        if (!droid[i].getFrozen()) {
                                            //Set facing
                                            if ((droid[i].mapX - player.pMapX) > 0) {
                                                droid[i].setFacing(0);
                                            } else if ((droid[i].mapX - player.pMapX) < 0) {
                                                droid[i].setFacing(1);
                                            }
                                            droid[i].inRange(player.pMapX, player.pMapY);
                                            if (droid[i].getInRange()) {
                                                droid[i].shoot();
                                                if (droid[i].getJustShot() == false && (droid[i].getAnimationFrame() == 5 || droid[i].getAnimationFrame() == 6 || droid[i].getAnimationFrame() == 7)) {
                                                    eBullets.add(droid[i].genBullet(player.pMapX, player.pMapY, player.getLyingDown()));

                                                    add(eBullets.get(enemyBulletCount));
                                                    enemyBulletCount += 1;
                                                }
                                            }
                                        }


                                    }
                                }
                            }
                        }
                        for (int i = 0; i < soldier.length; i++) {
                            if (soldier[i] != null) {
                                soldier[i].inScreenRange(collisionMap.getCurrentViewX(), collisionMap.getCurrentViewY());
                                if (soldier[i].getOnScreen() == true) {
                                    if (soldier[i].dead() == false) {
                                        if (!soldier[i].getFrozen()) {
                                            soldier[i].inRange(player.pMapX, player.pMapY);

                                            if (!collisionMap.isBlocked(soldier[i].mapX + soldier[i].hitboxOffsetX, soldier[i].mapY + soldier[i].getSpriteHeight() + collisionMap.TILESIZE) && !collisionMap.isBlocked(soldier[i].mapX + 160, soldier[i].mapY + soldier[i].getSpriteHeight() + collisionMap.TILESIZE)) {
                                                soldier[i].setOnGround(false);
                                            } else if (collisionMap.isBlocked(soldier[i].mapX + soldier[i].hitboxOffsetX, soldier[i].mapY + soldier[i].getSpriteHeight() + collisionMap.TILESIZE) || collisionMap.isBlocked(soldier[i].mapX + soldier[i].getSpriteWidth(), soldier[i].mapY + soldier[i].getSpriteHeight() + collisionMap.TILESIZE)) {
                                                soldier[i].setOnGround(true);
                                            }



                                            //Set facing
                                            if (((soldier[i].mapX + 122) - player.pMapX) > 0) {
                                                soldier[i].setFacing(0);
                                            } else if (((soldier[i].mapX + 122) - player.pMapX) < 0) {
                                                soldier[i].setFacing(1);
                                            }

                                            if (soldier[i].getInRange() == false) {

                                                //Move to player
                                                if (soldier[i].getOnGround()) {
                                                    if ((soldier[i].mapX - player.pMapX) > 0) {//Moves left
                                                        if (!collisionMap.isBlocked(soldier[i].mapX + soldier[i].hitboxOffsetX - collisionMap.TILESIZE, soldier[i].mapY + soldier[i].getSpriteHeight())) {
                                                            if (collisionMap.isBlocked(soldier[i].mapX + 20, soldier[i].mapY + soldier[i].getSpriteHeight() + collisionMap.TILESIZE)) {
                                                                soldier[i].setDirection(1);
                                                            }
                                                        }
                                                    } else if ((soldier[i].mapX - player.pMapX) < 0) {//Moves right
                                                        if (!collisionMap.isBlocked(soldier[i].mapX + soldier[i].hitboxOffsetX + soldier[i].hitboxWidth + collisionMap.TILESIZE, soldier[i].mapY + soldier[i].getSpriteHeight())) {
                                                            if (collisionMap.isBlocked(soldier[i].mapX + soldier[i].hitboxOffsetX + soldier[i].hitboxWidth + 30, soldier[i].mapY + soldier[i].getSpriteHeight() + collisionMap.TILESIZE)) {
                                                                soldier[i].setDirection(0);
                                                            }
                                                        }
                                                    }
                                                }


                                            }
                                            if (soldier[i].getInRange()) {
                                                //Shoot
                                                soldier[i].shoot();
                                                if (soldier[i].getJustShot() == false && (soldier[i].getAnimationFrame() == 6 || soldier[i].getAnimationFrame() == 8 || soldier[i].getAnimationFrame() == 10 || soldier[i].getAnimationFrame() == 12 || soldier[i].getAnimationFrame() == 14)) {
                                                    eBullets.add(soldier[i].generateBullet());

                                                    add(eBullets.get(enemyBulletCount));
                                                    enemyBulletCount += 1;
                                                }
                                            }
                                        }

                                    }
                                }
                            }
                        }
                    }
                } else if (stage == 5) {
                    if (start) {
                        if (boss.dead == true) {
                            player.setStageComplete(true);
                        }
                        if (level == 1) {
                            switch (boss.attackPattern) {

                                case 0:
                                    break;
                                case 1://flame thrower
                                    if ((boss.mapY + 260) > player.pMapY) {
                                        boss.moveY(4);
                                        boss.moveMapY(4);
                                    } else if ((boss.mapY + 260) < player.pMapY) {
                                        boss.moveY(-4);
                                        boss.moveMapY(-4);
                                    }
                                    if (boss.sprite.getFrame() >= 8 && boss.sprite.getFrame() <= 16) {
                                        if (player.pMapY >= boss.mapY + 171 && player.pMapY <= boss.mapY + 385) {
                                            player.loseHealth(0.01f, false);
                                        }
                                    }
                                    break;
                                case 2://dragon bullets
                                    if ((boss.mapY + 195) > player.pMapY) {
                                        boss.moveY(6);
                                        boss.moveMapY(6);
                                    } else if ((boss.mapY + 195) < player.pMapY) {
                                        boss.moveY(-6);
                                        boss.moveMapY(-6);
                                    }
                                    if (boss.getJustShot() == false) {
                                        eBullets.add(boss.genBullet(player.x, player.y, player.pMapX, player.pMapY, player.getLyingDown()));
                                        add(eBullets.get(enemyBulletCount));
                                        enemyBulletCount += 1;
                                    }
                                    break;
                                case 3://rush
                                    if (boss.sprite.getFrame() >= 8 && boss.sprite.getFrame() <= 12) {
                                        if (player.pMapX >= boss.mapX - 462 && player.pMapX <= boss.mapX - 462 + 300 && player.pMapY >= boss.mapY - 9 + 120 && player.pMapY <= boss.mapY - 9 + 192) {
                                            player.loseHealth(0.30f, false);


                                        }
                                    }
                                    break;
                            }

                        } else if (level == 2) {//Bergamot
                            if (boss.attackPattern == 1) {//Laser Beam
                                if (boss.sprite.getFrame() == 14) {
                                    if ((player.pMapX >= (boss.mapX - 744 + 580)) && (player.pMapX <= (boss.mapX - 744 + 694) && (player.pMapY >= boss.mapY + 454) && (player.pMapY <= boss.mapY + 496))) {
                                        player.loseHealth(0.20f, false);
                                    }

                                }
                                if (boss.sprite.getFrame() == 15) {
                                    if ((player.pMapX >= (boss.mapX - 744 + 442)) && (player.pMapX <= (boss.mapX - 744 + 630) && (player.pMapY >= boss.mapY + 410) && (player.pMapY <= boss.mapY + 505))) {
                                        player.loseHealth(0.20f, false);
                                    }

                                }
                                if (boss.sprite.getFrame() == 16) {
                                    if ((player.pMapX >= (boss.mapX - 744 + 77)) && (player.pMapX <= (boss.mapX - 744 + 371) && (player.pMapY >= boss.mapY + 463) && (player.pMapY <= boss.mapY + 507))) {
                                        player.loseHealth(0.20f, false);;
                                    }
                                }
                                if (boss.sprite.getFrame() == 17) {
                                    if ((player.pMapX >= (boss.mapX - 744 - 28)) && (player.pMapX <= (boss.mapX - 744 + 800) && (player.pMapY >= boss.mapY + 292) && (player.pMapY <= boss.mapY + 401))) {
                                        player.loseHealth(0.20f, false);
                                    }
                                }
                                if (boss.sprite.getFrame() == 18) {
                                    if ((player.pMapX >= (boss.mapX - 744 - 28)) && (player.pMapX <= (boss.mapX - 744 + 800) && (player.pMapY >= boss.mapY + 145) && (player.pMapY <= boss.mapY + 250))) {
                                        player.loseHealth(0.20f, false);
                                    }
                                }
                                if (boss.sprite.getFrame() == 19) {
                                    if ((player.pMapX >= (boss.mapX - 744 - 28)) && (player.pMapX <= (boss.mapX - 744 + 800) && (player.pMapY >= boss.mapY + 33) && (player.pMapY <= boss.mapY + 85))) {
                                        player.loseHealth(0.20f, false);
                                    }
                                }
                                if (boss.sprite.getFrame() == 20) {
                                    if ((player.pMapX >= (boss.mapX - 744 - 28)) && (player.pMapX <= (boss.mapX - 744 + 460) && (player.pMapY >= boss.mapY) && (player.pMapY <= boss.mapY + 22))) {

                                        if ((player.pMapX >= (boss.mapX - 744 + 539)) && (player.pMapX <= (boss.mapX - 744 + 666) && (player.pMapY >= boss.mapY + 460) && (player.pMapY <= boss.mapY + 505))) {
                                            player.loseHealth(0.20f, false);
                                        }
                                    }
                                }
                                if (boss.sprite.getFrame() == 21) {
                                    if ((player.pMapX >= (boss.mapX - 744 + 245)) && (player.pMapX <= (boss.mapX - 744 + 545) && (player.pMapY >= boss.mapY) && (player.pMapY <= boss.mapY + 20))) {

                                        if ((player.pMapX >= (boss.mapX - 744 + 300)) && (player.pMapX <= (boss.mapX - 744 + 485) && (player.pMapY >= boss.mapY + 460) && (player.pMapY <= boss.mapY + 505))) {
                                            player.loseHealth(0.20f, false);
                                        }
                                    }
                                }
                                if (boss.sprite.getFrame() == 22) {
                                    if ((player.pMapX >= (boss.mapX - 744 - 28)) && (player.pMapX <= (boss.mapX - 744 + 460) && (player.pMapY >= boss.mapY) && (player.pMapY <= boss.mapY + 22))) {
                                        player.healthPercent -= 0.5;
                                        if ((player.pMapX >= (boss.mapX - 744 + 539)) && (player.pMapX <= (boss.mapX - 744 + 666) && (player.pMapY >= boss.mapY + 460) && (player.pMapY <= boss.mapY + 505))) {
                                            player.loseHealth(0.20f, false);
                                        }
                                    }
                                }
                            } else if (boss.attackPattern == 2) {//Turret Fire
                                if (boss.getJustShot() == false && (boss.sprite.getFrame() == 3 || boss.sprite.getFrame() == 5 || boss.sprite.getFrame() == 7 || boss.sprite.getFrame() == 9 || boss.sprite.getFrame() == 11)) {
                                    eBullets.add((boss.genBullet(player.pMapX, player.pMapY, player.pMapX, player.pMapY, player.getLyingDown())));

                                    add(eBullets.get(enemyBulletCount));
                                    enemyBulletCount += 1;

                                }
                            } else if (boss.attackPattern == 3) {//Missiles
                                if (boss.getJustShot() == false && (boss.sprite.getFrame() == 5 || boss.sprite.getFrame() == 7 || boss.sprite.getFrame() == 9 || boss.sprite.getFrame() == 11)) {
                                    eBullets.add(boss.genBullet(player.x, player.y, player.pMapX, player.pMapY, player.getLyingDown()));
                                    add(eBullets.get(enemyBulletCount));
                                    enemyBulletCount += 1;

                                }
                            }
                        } else if (level == 3) {
                            if (boss.attackPattern == 1) {//Laser
                                if (boss.sprite.getFrame() >= 13 && boss.sprite.getFrame() <= 35) {
                                    if ((player.pMapX >= (boss.mapX - 830 + 9)) && (player.pMapX <= (boss.mapX - 830 + 1030) && (player.pMapY >= boss.mapY + 105) && (player.pMapY <= boss.mapY + 185))) {
                                        player.loseHealth(0.20f, false);
                                    }

                                }
                                if (boss.sprite.getFrame() == 36) {
                                    if ((player.pMapX >= (boss.mapX - 830 + 9)) && (player.pMapX <= (boss.mapX - 830 + 1030) && (player.pMapY >= boss.mapY + 112) && (player.pMapY <= boss.mapY + 170))) {
                                        player.loseHealth(0.20f, false);
                                    }
                                }
                                if (boss.sprite.getFrame() == 37) {
                                    if ((player.pMapX >= (boss.mapX - 830 + 9)) && (player.pMapX <= (boss.mapX - 830 + 1030) && (player.pMapY >= boss.mapY + 119) && (player.pMapY <= boss.mapY + 166))) {
                                        player.loseHealth(0.20f, false);;
                                    }
                                }
                                if (boss.sprite.getFrame() == 38) {
                                    if ((player.pMapX >= (boss.mapX - 830 + 9)) && (player.pMapX <= (boss.mapX - 830 + 1030) && (player.pMapY >= boss.mapY + 123) && (player.pMapY <= boss.mapY + 161))) {
                                        player.loseHealth(0.20f, false);
                                    }
                                }
                                if (boss.sprite.getFrame() == 39) {
                                    if ((player.pMapX >= (boss.mapX - 830 + 9)) && (player.pMapX <= (boss.mapX - 830 + 1030) && (player.pMapY >= boss.mapY + 130) && (player.pMapY <= boss.mapY + 157))) {
                                        player.loseHealth(0.20f, false);
                                    }
                                }

                            } else if (boss.attackPattern == 2) {//Turret
                                if (boss.getJustShot() == false && (boss.sprite.getFrame() % 2 == 1)) {
                                    eBullets.add((boss.genBullet(player.pMapX, player.pMapY, player.pMapX, player.pMapY, player.getLyingDown())));

                                    add(eBullets.get(enemyBulletCount));
                                    enemyBulletCount += 1;

                                }
                            } else if (boss.attackPattern == 3) {//Missiles
                                if (boss.sprite.getFrame() == 15) {
                                    if ((player.pMapX >= (boss.mapX - 600 + 170)) && (player.pMapX <= (boss.mapX - 600 + 500) && (player.pMapY >= boss.mapY - 428 + 500) && (player.pMapY <= boss.mapY - 428 + 660))) {
                                        player.loseHealth(0.20f, false);
                                    }
                                }
                                if (boss.sprite.getFrame() == 16) {
                                    if ((player.pMapX >= (boss.mapX - 600 + 130)) && (player.pMapX <= (boss.mapX - 600 + 5366) && (player.pMapY >= boss.mapY - 428 + 484) && (player.pMapY <= boss.mapY - 428 + 664))) {
                                        player.loseHealth(0.20f, false);
                                    }
                                }
                            }
                        }
                    }

                }

                //Map Portal//Advances to next map if player reaches portal
                if (player.getStageComplete()) {

                    if (stage == 5) {
                        boss.destroyResources();//Destroys all boss images to frree up memory
                    }
                    if (stage < 5) {
                        stage += 1;//Saves game and advances level or stage forward
                        modSave.save(saveSlot, readSave.getName(saveSlot), readSave.getMoney(saveSlot), this.level, this.stage, false);
                    } else if (stage == 5) {
                        if (level < 5) {
                            level += 1;
                            modSave.save(saveSlot, readSave.getName(saveSlot), readSave.getMoney(saveSlot), level, 1, false);
                        }
                    }
                    //Goes back to story level selector
                    resetLevel();
                    StoryLevelSelector target = (StoryLevelSelector) game.getState(constants.STORYLEVELSELECTOR);
                    target.setInfo(level, stage, saveSlot, readSave.getMoney(saveSlot));
                    game.enterState(constants.STORYLEVELSELECTOR, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));

                }
            }
        }
    }

    /*Description: Freezes movement of all enemy bullets and stops enemies from moving or firing
     * pre: Freeze is activated from player
     * post:enemies and bullets stop moving
     */
    public void freezeTime() {
        for (MainGameEnemyBullet b : eBullets) {

            b.freeze();
        }
        if (stage != 5) {
            for (Sentinel e : sentinel) {
                if (e != null) {
                    e.freeze();
                }

            }
            for (Soldier e : soldier) {
                if (e != null) {
                    e.freeze();
                }
            }
            for (Droid e : droid) {
                if (e != null) {
                    e.freeze();
                }
            }
        }

    }
    /*Description: Allows movement of all enemy bullets and enemies 
     * pre: Freeze is deactivated from player
     * post:enemies and bullets start moving again
     */

    public void unFreezeTime() {
        for (MainGameEnemyBullet b : eBullets) {
            b.unFreeze();
        }
        if (stage != 5) {
            for (Sentinel e : sentinel) {
                if (e != null) {
                    e.unFreeze();
                }
            }
            for (Soldier e : soldier) {
                if (e != null) {
                    e.unFreeze();
                }
            }
            for (Droid e : droid) {
                if (e != null) {
                    e.unFreeze();
                }
            }
        }

    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        super.render(container, game, g);

        if (start == false && time != 0) {
            g.setColor(Color.white);
            g.drawString("GET READY! ", 350, 300);
            g.drawString("" + (time / 1000), 350, 320);
            if (time <= 0) {
                g.drawString("GO!", 350, 340);
            }
        }
        if (reloading) {
            g.drawString("RELOADING...", 256, 85);
        }
    }

    /**
     * Converts an array of Integer objects to an array of integer primitives
     * @return an array of integer primitives
     */
    public static int[] toIntArray(List<Integer> integerList) {
        int[] intArray = new int[integerList.size()];
        for (int i = 0; i < integerList.size(); i++) {
            intArray[i] = integerList.get(i);
        }
        return intArray;
    }
    /*Description: Moves bullets when map is scrolling 
     * pre: Map is scrolling
     * post:Bullets move in the opposite direction player is going in
     */

    public void moveEBullets(int plane, int direc) {
//plane = 0 = moveX || plane = 1 = moveY
        for (MainGameEnemyBullet b : eBullets) {

            if (plane == 0) {
                b.moveX(direc * player.getPlayerSpeed());
            } else if (plane == 1) {
                b.moveY(direc * player.getPlayerSpeed());
            }



        }
    }
    /*Description: Moves all enemies on the horizontal plane
     * pre: Map is scrolling
     * post:enemies move
     */

    public void moveAllEnemiesX(int direction) {
        float speed = player.getPlayerSpeed();
        if (stage != 5) {

            for (int i = 0; i < portals.length; i++) {
                portals[i].moveX(direction * speed);
            }
            for (int i = 0; i < sentinel.length; i++) {
                if (sentinel[i] != null) {

                    sentinel[i].moveX(direction * speed);
                }
            }
            for (int i = 0; i < soldier.length; i++) {
                if (soldier[i] != null) {
                    soldier[i].moveX(direction * speed);
                }
            }
            for (int i = 0; i < droid.length; i++) {
                if (droid[i] != null) {

                    droid[i].moveX(direction * speed);
                }
            }
        } else if (stage == 5) {
            boss.moveX(direction * speed);
        }

    }

    /*Description: Moves all enemies on vertical plane
     * pre: Map is scrolling
     * post:Enemies move up or down
     */
    public void moveAllEnemiesY(int direction, boolean scrolling) {
        float speed;
        if (player.fly || scrolling) {
            speed = player.getPlayerSpeed();
        } else {
            speed = (int) player.getSpeed();
        }
        if (stage != 5) {

            for (int i = 0; i < portals.length; i++) {
                portals[i].moveY(direction * speed);
            }
            for (int i = 0; i < sentinel.length; i++) {
                if (sentinel[i] != null) {
                    sentinel[i].moveY(direction * speed);
                }
            }
            for (int i = 0; i < soldier.length; i++) {
                if (soldier[i] != null) {
                    soldier[i].moveY(direction * speed);
                }
            }
            for (int i = 0; i < droid.length; i++) {
                if (droid[i] != null) {
                    droid[i].moveY(direction * speed);
                }
            }
        } else if (stage == 5) {
            boss.moveY(direction * speed);
        }
    }
}
