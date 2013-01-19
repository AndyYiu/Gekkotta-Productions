/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OverClocked;

import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.entity.Entity;
import javax.annotation.Resource;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *
 * @author admin
 */
public class MainGamePlayer extends Entity implements Health{

    private double speed = -1, maxHeight = 170, height = 0;
    public static double gravity = 0.5;
    private static String TYPE = "MAINGAMEPLAYER";
    private Animation sprite, jUpRight, jDownRight, left, right, stand, hitRight, shield, prone, proneLeft, proneHit, proneLeftHit, wings, wingsLeft;
    private int choice, pSpeed = 5, facing = 0, uTime = 0, dTime = 0;//0 = left, 1 = right;
    private int jumping; // 1= rising, 2 = readjusting,0 = not jumping
    private boolean onGround, readjusting;
    private Animation jUpLeft;
    private Animation jDownLeft;
    private Animation hitLeft;
    public float pMapX, pMapY, healthPercent;//The player coordinates that will be used for collision detection when the map moves. They are relative to the map even though the player stays in one spot.
    public int ammo = 40;
    public int shotsFired = 0;
    public int reloadTime = 1500;
    public int account;
    public boolean invincible, noClip, fly, shields, teleport, flying, powerGuard, freeze = false, vac, battleGrieves, stance, eyes, onSlope;
    private Image healthGUI, hacksGUI, gunScoreGUI, dH, uH;
    private boolean uJustUsed, dJustUsed, uWait, dWait, stageComplete, lyingDown, hit, dHack, uHack;
    public String defense, utility, passive;
    private ReadSaveSlotsXML readSave = new ReadSaveSlotsXML();
 
    /*Constructor
     *
     *pre: takes in the coordinates of the screen and coordinates of the map and the level it's on
     *post: makes a player
     */
    public MainGamePlayer(float x, float y, float mapX, float mapY, int direc, int acc) {
        super(x, y);
        addType(TYPE);
        account = acc;
        healthGUI = ResourceManager.getImage("GUIHealth");
        hacksGUI = ResourceManager.getImage("GUIHacks");
        gunScoreGUI = ResourceManager.getImage("GUIGun");
        Image[] jumpUpRight = {ResourceManager.getImage("mJUP4")};
        Image[] jumpDownRight = {ResourceManager.getImage("mJDOWN1")};
        Image[] movementRight = {ResourceManager.getImage("mWalkingRight0"), ResourceManager.getImage("mWalkingRight1"), ResourceManager.getImage("mWalkingRight2"), ResourceManager.getImage("mWalkingRight3"), ResourceManager.getImage("mWalkingRight4"), ResourceManager.getImage("mWalkingRight5"), ResourceManager.getImage("mWalkingRight6"), ResourceManager.getImage("mWalkingRight7"), ResourceManager.getImage("mWalkingRight8"), ResourceManager.getImage("mWalkingRight9")};
        Image[] hitFlashRight = {ResourceManager.getImage("mHit1"), ResourceManager.getImage("mHit2")};

        Image[] jumpUpLeft = {ResourceManager.getImage("mJUP4").getFlippedCopy(true, false)};
        Image[] jumpDownLeft = {ResourceManager.getImage("mJDOWN1").getFlippedCopy(true, false)};
        Image[] movementLeft = {ResourceManager.getImage("mWalkingRight0").getFlippedCopy(true, false), ResourceManager.getImage("mWalkingRight1").getFlippedCopy(true, false), ResourceManager.getImage("mWalkingRight2").getFlippedCopy(true, false), ResourceManager.getImage("mWalkingRight3").getFlippedCopy(true, false), ResourceManager.getImage("mWalkingRight4").getFlippedCopy(true, false), ResourceManager.getImage("mWalkingRight5").getFlippedCopy(true, false), ResourceManager.getImage("mWalkingRight6").getFlippedCopy(true, false), ResourceManager.getImage("mWalkingRight7").getFlippedCopy(true, false), ResourceManager.getImage("mWalkingRight8").getFlippedCopy(true, false), ResourceManager.getImage("mWalkingRight9").getFlippedCopy(true, false)};
        Image[] hitFlashLeft = {ResourceManager.getImage("mHit1").getFlippedCopy(true, false), ResourceManager.getImage("mHit2").getFlippedCopy(true, false)};
        Image[] proningRight = {ResourceManager.getImage("mProne")};
        Image[] proningLeft = {ResourceManager.getImage("mProne").getFlippedCopy(true, false)};
        Image[] proningRightHit = {ResourceManager.getImage("mProneHit")};
        Image[] proningLeftHit = {ResourceManager.getImage("mProneHit").getFlippedCopy(true, false)};

        Image[] shields = {ResourceManager.getImage("s1"), ResourceManager.getImage("s2"), ResourceManager.getImage("s3"), ResourceManager.getImage("s4"), ResourceManager.getImage("s5"), ResourceManager.getImage("s6"), ResourceManager.getImage("s7"), ResourceManager.getImage("s8"), ResourceManager.getImage("s9"), ResourceManager.getImage("s10"), ResourceManager.getImage("s11")};
        Image[] wingLeft = {ResourceManager.getImage("mw1"), ResourceManager.getImage("mw2"), ResourceManager.getImage("mw3"), ResourceManager.getImage("mw4"), ResourceManager.getImage("mw5"), ResourceManager.getImage("mw6"), ResourceManager.getImage("mw7"), ResourceManager.getImage("mw8"), ResourceManager.getImage("mw9"), ResourceManager.getImage("mw10"), ResourceManager.getImage("mw11"), ResourceManager.getImage("mw12")};
        Image[] wingRight = {ResourceManager.getImage("mw1").getFlippedCopy(true, false), ResourceManager.getImage("mw2").getFlippedCopy(true, false), ResourceManager.getImage("mw3").getFlippedCopy(true, false), ResourceManager.getImage("mw4").getFlippedCopy(true, false), ResourceManager.getImage("mw5").getFlippedCopy(true, false), ResourceManager.getImage("mw6").getFlippedCopy(true, false), ResourceManager.getImage("mw7").getFlippedCopy(true, false), ResourceManager.getImage("mw8").getFlippedCopy(true, false), ResourceManager.getImage("mw9").getFlippedCopy(true, false), ResourceManager.getImage("mw10").getFlippedCopy(true, false), ResourceManager.getImage("mw11").getFlippedCopy(true, false), ResourceManager.getImage("mw12").getFlippedCopy(true, false),};

        int[] dJumpUp = {500};
        int[] dJumpDown = {1000};
        int[] dMove = {10, 100, 100, 100, 100, 100, 100, 100, 100, 100};
        int[] dHit = {100, 100};
        int[] dShield = {100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100};
        int[] dWing = {90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90};
        jumping = 0;
        jUpRight = new Animation(jumpUpRight, dJumpUp, false);
        jDownRight = new Animation(jumpDownRight, dJumpDown, false);
        right = new Animation(movementRight, dMove, false);
        hitRight = new Animation(hitFlashRight, dHit, false);
        jUpLeft = new Animation(jumpUpLeft, dJumpUp, false);
        jDownLeft = new Animation(jumpDownLeft, dJumpDown, false);
        left = new Animation(movementLeft, dMove, false);
        hitLeft = new Animation(hitFlashLeft, dHit, false);
        prone = new Animation(proningRight, dJumpUp);
        proneLeft = new Animation(proningLeft, dJumpUp);
        proneHit = new Animation(proningRightHit, dJumpUp);
        proneLeftHit = new Animation(proningLeftHit, dJumpUp);
        shield = new Animation(shields, dShield, true);
        wings = new Animation(wingRight, dWing, true);
        wingsLeft = new Animation(wingLeft, dWing, true);
        //Right
        for (int i = 0; i < jumpUpRight.length; i++) {
            jumpUpRight[i] = null;
        }
        for (int i = 0; i < movementRight.length; i++) {
            movementRight[i] = null;
        }
        for (int i = 0; i < hitFlashRight.length; i++) {
            hitFlashRight[i] = null;
        }
        for (int i = 0; i < jumpDownRight.length; i++) {
            jumpDownRight[i] = null;
        }
        for (int i = 0; i < proningRight.length; i++) {
            proningRight[i] = null;
        }
        for (int i = 0; i < wingRight.length; i++) {
            wingRight[i] = null;
        }
        //Left
        for (int i = 0; i < jumpUpLeft.length; i++) {
            jumpUpLeft[i] = null;
        }
        for (int i = 0; i < jumpDownLeft.length; i++) {
            jumpDownLeft[i] = null;
        }
        for (int i = 0; i < hitFlashLeft.length; i++) {
            hitFlashLeft[i] = null;
        }
        for (int i = 0; i < movementLeft.length; i++) {
            movementLeft[i] = null;
        }
        for (int i = 0; i < proningLeft.length; i++) {
            proningLeft[i] = null;
        }
        for (int i = 0; i < wingLeft.length; i++) {
            wingLeft[i] = null;
        }
        for (int i = 0; i < shields.length; i++) {
            shields[i] = null;
        }


        stageComplete = false;
        stance = false;

        defense = readSave.getDefenseHacks(account);
        utility = readSave.getUtilityHacks(account);
        passive = readSave.getBoughtHacks(account);

        if (passive.contains("HpIncrease")) {
            healthPercent = 2;
        } else {
            healthPercent = 1;
        }
        if (passive.contains("Haste")) {
            pSpeed = pSpeed + 4;
        }

        if (passive.contains("SharpEyes")) {
            eyes = true;
        }

        if (passive.contains("Stance")) {
            stance = true;
        }

        if (passive.contains("PowerGuard")) {
            powerGuard = true;
        }

        if (passive.contains("InfiniteAmmo")) {
            ammo = 2147483647;
        }
        facing = direc;
        sprite = right;
        setHitBox(17, 10, 29, 71);
        this.y = this.y - sprite.getHeight();
        pMapX = mapX;
        pMapY = mapY - sprite.getHeight();
        lyingDown = false;
        hit = false;
    }

    //checks where you are
    public void setScreenCoordinates(float x, float y) {
        this.x = x;
        this.y = y;
    }

    //resets health at beginning of level
    public void setHealth() {
        healthPercent = 0;
    }
    
    //makes complete if you walk into portal
    public void setStageComplete(boolean b){
    stageComplete=b;
    }
    
    //reads the players health
    public float getHealth() {
        return healthPercent;
    }

    //jumps
    public void setJumping(int j) {
        jumping = j;

    }

    //changes person back to standing position
    public void setDefaultAnimation(boolean v, int f) {
        if (facing == 0) {
            sprite = left;
        } else if (facing == 1) {
            sprite = right;
        }
        sprite.setCurrentFrame(f);
        sprite.setAutoUpdate(v);
    }

    //stops animation when standing
    public boolean animationStopped() {
        return sprite.isStopped();
    }

    //get way you look
    public int getFacing() {
        return facing;
    }

    //set where you look
    public void setFacing(int f) {
        facing = f;
    }

    //sets if you're on ground or not
    public void setOnGround(boolean b) {
        onGround = b;
    }

    //repositions you 1 pixel above the ground
    public void repositionOnGround() {
        y = y + 1;
    }

    //get players fatness
    public int getSpriteWidth() {
        return sprite.getWidth();
    }

    //jumping speed
    public double getSpeed() {
        return speed;
    }

    //get's the players heigt
    public int getSpriteHeight() {
        return sprite.getHeight();
    }

    //checks if jumping
    public int getJumping() {
        return jumping;
    }

    //get's the speed of player
    public int getPlayerSpeed() {
        return pSpeed;
    }

    //checks if player is on ground
    public boolean getOnGround() {
        return onGround;
    }

    //check if map is moving into place
    public boolean getReadjusting() {
        return readjusting;
    }

    //resets gravity
    public void resetGravtiyVars() {

        speed = -45;
        gravity = 3;
    }

    //move the guy in a vertical fashion
    public void moveVertical(int speed) {
        y += speed;
    }

    //moves the map
    public void setReadjusting(boolean b) {
        readjusting = b;
    }

    //makes you look other way
    public void changeDirection() {
        if (facing == 0) {
            if (lyingDown) {
                sprite = proneLeft;
            } else {
                sprite = left;
            }

        } else if (facing == 1) {
            if (lyingDown) {
                sprite = prone;
            } else {
                sprite = right;
            }
        }
    }

    /* changes health
     * 
     * pre: damage taken and if boss or not
     * post: takes damage
     */
    public void loseHealth(float damage, boolean boss) {
        if (boss) {
            healthPercent -= damage;
        } else if (invincible != true && !boss) {
            healthPercent -= damage;
        }

    }

    /* knockback
     * 
     * pre: nothing
     * post: unless using stance, moves the plaver backwards
     */
    public void knockback() {
        if (stance != true) {
            for (int i = 0; i < 10; i++) {
                if (pMapX > 30 && pMapY > 30) {
                    pMapX -= 1;
                    x -= 1;
                    pMapY -= 1;
                    y -= 1;
                }

            }
        }


    }

    //gets if the stage is done or not
    public boolean getStageComplete() {
        return stageComplete;

    }

    //makes player lie down
    public void setLyingDown(boolean b) {
        lyingDown = b;
    }

    //gets if prone or not
    public boolean getLyingDown() {
        return lyingDown;
    }

    //gets if hit
    public boolean getHit() {
        return hit;
    }

    //get's wether time is frozen or not
    public boolean getFreezeTime() {
        return freeze;
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        super.update(container, delta);
        Input input = container.getInput();

        if (uJustUsed || uWait) {
            uTime += delta;
            if (freeze) {
                if (uJustUsed && uTime >= 5000) {
                    uJustUsed = false;
                    freeze = false;
                    uHack = false;
                    uWait = true;
                }
            } else if (fly) {
                if (uJustUsed && uTime >= 15000) {
                    uJustUsed = false;
                    fly = false;
                    uHack = false;
                    uWait = true;
                }
            } else if (noClip) {
                if (uJustUsed && uTime >= 5000) {
                    uJustUsed = false;
                    noClip = false;
                    uHack = false;
                    uWait = true;
                }
            } else if (teleport) {
                if (uJustUsed && uTime >= 100) {
                    uJustUsed = false;
                    teleport = false;
                    uHack = false;
                    uWait = true;
                }
            }
            if (uWait && uTime >= 30000) {
                uWait = false;
                uTime = 0;
            }
        }

        if (dJustUsed || dWait) {
            dTime += delta;
            if (shields) {
                if (dJustUsed && dTime >= 5000) {
                    dJustUsed = false;
                    shields = false;
                    dHack = false;
                    dWait = true;
                }
            } else if (battleGrieves) {
                if (dJustUsed && dTime >= 5000) {
                    dJustUsed = false;
                    battleGrieves = false;
                    dHack = false;
                    dWait = true;
                }
            } else if (invincible) {
                if (dJustUsed && dTime >= 15000) {
                    dJustUsed = false;
                    invincible = false;
                    dHack = false;
                    dWait = true;
                }
            }
            if (dWait && dTime >= 30000) {
                dWait = false;
                dTime = 0;
            }
        }

        if (collide("PORTAL", x, y) != null) {

            stageComplete = true;
        }

        if (collide("MAINGAMEENEMY", x, y) != null) {//Set collision detection!
            knockback();
            loseHealth(0.01f, false);
            hit = true;

            if (facing == 0) {
                sprite = hitLeft;
                sprite.setAutoUpdate(false);
            } else if (facing == 1) {
                sprite = hitRight;
                sprite.setAutoUpdate(false);
            }

        } else if (collide("MAINGAMEBOSS", x, y) != null) {
            knockback();
            loseHealth(0.30f, true);
            hit = true;

            if (facing == 0) {
                sprite = hitLeft;
                sprite.setAutoUpdate(false);
            } else if (facing == 1) {
                sprite = hitRight;
                sprite.setAutoUpdate(false);
            }

        } else if (collide("MAINGAMEENEMYBOSSBULLET", x, y) != null) {//Set collision detection!
            knockback();
            if (powerGuard) {
                loseHealth(0.25f * 0.3f, false);
            } else if (powerGuard != true) {
                loseHealth(0.25f, false);
            }

            hit = true;

            if (facing == 0) {
                sprite = hitLeft;
            } else if (facing == 1) {
                sprite = hitRight;
            }
            sprite.setAutoUpdate(false);
        } else if (shields != true) {
            if (collide("MAINGAMEENEMYBULLET", x, y) != null) {
                knockback();
                loseHealth(0.10f, false);
                hit = true;

                if (facing == 0) {
                    if (lyingDown) {
                        sprite = proneLeftHit;
                    } else {
                        sprite = hitLeft;
                    }

                } else if (facing == 1) {
                    if (lyingDown) {
                        sprite = proneHit;
                    } else {
                        sprite = hitRight;
                    }
                }
                sprite.setAutoUpdate(false);
            }
        } else {
            hit = false;
        }
        if (sprite == prone) {
            setHitBox(33, 72, 75, 32);
        } else if (sprite != prone) {
            setHitBox(17, 10, 29, 71);
        }
        if (fly != true) {
            if (onGround == false) {
                if (readjusting == false) {
                    if (jumping == 1) {
                        speed += gravity;
                        height += -speed;
                        y += speed;
                        if (speed >= 0 || height >= maxHeight) {


                            jumping = 2;
                        }
                    } else if (jumping == 2) {
                        if (facing == 0) {
                            sprite = jDownLeft;
                        } else if (facing == 1) {
                            sprite = jDownRight;
                        }
                        sprite.setAutoUpdate(true);
                        height = 0;
                        speed = 15;
                        y += speed;
                    } else if (jumping == 0) {

                        speed = 15;
                        y += speed;


                    }
                }
            } else if (onGround == true) {
                jumping = 0;
                speed = 0;
            }
        }
        if ((input.isKeyDown(Input.KEY_W) || input.isKeyDown(Input.KEY_S)) && fly) {
            flying = true;
        } else {
            flying = false;
        }
        //HACKS/////////////////////////////////////////
        if (input.isKeyPressed(Input.KEY_E) && !dWait) {//Defense
            if (defense.equals("Shield")) {
                if (shields == false) {
                    shields = true;
                    dHack = true;
                } else if (shields == true) {
                    shields = false;
                    dHack = false;
                }
            } else if (defense.equals("BattleGrieves")) {
                if (battleGrieves == false) {
                    battleGrieves = true;
                    dHack = true;
                } else if (battleGrieves == true) {
                    battleGrieves = false;
                    dHack = false;
                }
            } else if (defense.equals("Invincible")) {
                if (invincible == false) {
                    invincible = true;
                    dHack = true;
                } else if (invincible == true) {
                    invincible = false;
                    dHack = false;
                }
            }

            if (dHack) {
                dJustUsed = true;
            }
        }

        if (input.isKeyPressed(Input.KEY_F) && !uWait) {//Utility
            if (utility.equals("Freeze")) {
                if (freeze == false) {
                    freeze = true;
                    uHack = true;
                } else if (freeze == true) {
                    freeze = false;
                    uHack = false;
                }
            } else if (utility.equals("Fly")) {
                if (fly == false) {
                    fly = true;
                    uHack = true;
                } else if (fly == true) {
                    fly = false;
                    uHack = false;
                }
            } else if (utility.equals("NoClip")) {
                if (noClip == false) {
                    noClip = true;
                    uHack = true;
                } else if (noClip == true) {
                    noClip = false;
                    uHack = false;
                }
            } else if (utility.equals("Teleport")) {
                if (teleport == false) {
                    teleport = true;
                    uHack = true;
                } else if (teleport == true) {
                    teleport = false;
                    uHack = false;
                }
            }

            if (uHack && !teleport) {
                uJustUsed = true;
            }

            if (input.isMousePressed(Input.MOUSE_RIGHT_BUTTON) && teleport) {
                uJustUsed = true;
            }
        }
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        super.render(container, g);
        sprite.draw(x, y);
     
        if (eyes) {
            healthGUI.draw(8, 12);
            g.setColor(Color.green);
            g.fillRect(14, 78, (float) 6.5, (-47 * getHealthPercent()));

            gunScoreGUI.draw(78, 71);
            g.drawString((ammo - shotsFired) + "/" + ammo, 216, 75);
            if (shotsFired >= ammo) {
                g.setColor(Color.red);
                g.drawString("RELOAD!", 256, 75);
            }
        }
        hacksGUI.draw(71, 10);
        if (!defense.equals("z")) {
            getDefensePic().draw(134, 23);
        }
        if (!utility.equals("z")) {
            getUtilityPic().draw(185, 23);
        }
        if (shields) {
            shield.draw(x - 84, y - 11);
        }
        if (fly) {

            if (facing == 1) {
                wings.draw(x - 93, y - 50);
            } else if (facing == 0) {
                wingsLeft.draw(x - 55, y - 50);
            }
        }

        g.setColor(Color.red);
    }

    /* get's the def hack pic
     * 
     * pre:nothing
     * post: set's the pic for UI
     */
    public Image getDefensePic() throws SlickException {
        if (dHack) {
            dH = ResourceManager.getImage(defense);
        } else {
            dH = ResourceManager.getImage(defense + "BW");
        }
        dH = dH.getScaledCopy(42, 40);
        return dH;
    }

    /* get's the util hack pic
     * 
     * pre: nothing
     * post: displays pic for UI
     */
    public Image getUtilityPic() throws SlickException {
        if (uHack) {
            uH = ResourceManager.getImage(utility);
        } else {
            uH = ResourceManager.getImage(utility + "BW");
        }
        uH = uH.getScaledCopy(42, 40);
        return uH;
    }

    //get's health
    public float getHealthPercent() {
        return healthPercent;
    }

    /* sets the direction
     * 
     * pre: gets keyboard input and the frame rate
     * post: changes the character to face the direction based on key
     */
    public void setDirection(int a, int delta) {
        if (lyingDown != true) {
            if (a == Input.KEY_D) {
                if (onSlope != true) {
                    if (jumping == 0) {
                        if (facing == 1) {
                            sprite = right;
                        } else if (facing == 0) {
                            sprite = left;
                        }
                        sprite.setAutoUpdate(true);
                        x += pSpeed;
                        pMapX += pSpeed;
                    } else {
                        x += pSpeed;
                        pMapX += pSpeed;
                    }
                } else if (onSlope) {
                    if (facing == 1) {
                        sprite = right;
                    } else if (facing == 0) {
                        sprite = left;
                    }
                    sprite.setAutoUpdate(true);
                    x += pSpeed;
                    pMapX += pSpeed;
                    y -= 10;
                    pMapY -= 10;
                }

            } else if (a == Input.KEY_A) {
                if (onSlope != true) {
                    if (jumping == 0) {
                        if (facing == 1) {
                            sprite = right;
                        } else if (facing == 0) {
                            sprite = left;
                        }
                        sprite.setAutoUpdate(true);
                        x -= pSpeed;
                        pMapX -= pSpeed;
                    } else {
                        x -= pSpeed;
                        pMapX -= pSpeed;
                    }
                } else if (onSlope) {
                    if (facing == 1) {
                        sprite = right;
                    } else if (facing == 0) {
                        sprite = left;
                    }
                    sprite.setAutoUpdate(true);
                    x -= pSpeed;
                    pMapX -= pSpeed;
                    y -= 10;
                    pMapY -= 10;
                }

            } else if (a == Input.KEY_W) {
                if (fly != true) {
                    if (jumping == 0) {
                        resetGravtiyVars();
                        onGround = false;
                        jumping = 1;

                    }
                } else if (fly) {

                    y -= pSpeed;
                    pMapY -= pSpeed;
                }
                sprite.setAutoUpdate(true);
            } else if (a == Input.KEY_S) {
                if (fly) {
                    y += pSpeed;
                    pMapY += pSpeed;

                } else if (fly != true) {
                    if (facing == 0) {
                        sprite = proneLeft;
                    } else if (facing == 1) {
                        sprite = prone;
                    }

                    lyingDown = true;

                }
                sprite.setAutoUpdate(true);
            }
        }

    }

}
