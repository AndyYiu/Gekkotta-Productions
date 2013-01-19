/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OverClocked;

import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.entity.Entity;
import java.util.Random;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/** Author: Stanley Fung
 * Date:
 * Teacher:
 * Description:
 * 
 */
public class Sentinel extends Entity {

    public static String TYPE = "MAINGAMEENEMY";
    private Animation sprite, attackLeft, attackRight, moveLeft, moveRight, deathLeft, deathRight;
    private int health = 50, bulletType = 2, facing = 0;//0 = left, 1 = right;
    private boolean dead, inRange, onScreen, justShot, gone, frozen;
    private int rangeX, rangeY, time;
    public float mapX, mapY;

    /*constructor
     *
     *pre: coordinates on screen and on map
     *post: initializes the animations for a sent
     */
    public Sentinel(float x, float y, float mapx, float mapy) {
        super(x, y);

        addType(TYPE);
        Image[] attacksLeft = {ResourceManager.getImage("sentShoot2"), ResourceManager.getImage("sentShoot3"), ResourceManager.getImage("sentShoot4"), ResourceManager.getImage("sentShoot5"), ResourceManager.getImage("sentShoot6"), ResourceManager.getImage("sentShoot7"), ResourceManager.getImage("sentShoot8"), ResourceManager.getImage("sentShoot9"), ResourceManager.getImage("sentShoot10"), ResourceManager.getImage("sentShoot11"), ResourceManager.getImage("sentShoot12"), ResourceManager.getImage("sentShoot13"), ResourceManager.getImage("sentShoot14"), ResourceManager.getImage("sentShoot15"), ResourceManager.getImage("sentShoot16"), ResourceManager.getImage("sentShoot17"), ResourceManager.getImage("sentShoot18")};
        Image[] dyingLeft = {ResourceManager.getImage("sentDie1"), ResourceManager.getImage("sentDie2"), ResourceManager.getImage("sentDie3"), ResourceManager.getImage("sentDie4"), ResourceManager.getImage("sentDie5"), ResourceManager.getImage("sentDie6")};

        Image[] attacksRight = {ResourceManager.getImage("sentShoot2").getFlippedCopy(true, false), ResourceManager.getImage("sentShoot3").getFlippedCopy(true, false), ResourceManager.getImage("sentShoot4").getFlippedCopy(true, false), ResourceManager.getImage("sentShoot5").getFlippedCopy(true, false), ResourceManager.getImage("sentShoot6").getFlippedCopy(true, false), ResourceManager.getImage("sentShoot7").getFlippedCopy(true, false), ResourceManager.getImage("sentShoot8").getFlippedCopy(true, false), ResourceManager.getImage("sentShoot9").getFlippedCopy(true, false), ResourceManager.getImage("sentShoot10").getFlippedCopy(true, false), ResourceManager.getImage("sentShoot11").getFlippedCopy(true, false), ResourceManager.getImage("sentShoot12").getFlippedCopy(true, false), ResourceManager.getImage("sentShoot13").getFlippedCopy(true, false), ResourceManager.getImage("sentShoot14").getFlippedCopy(true, false), ResourceManager.getImage("sentShoot15").getFlippedCopy(true, false), ResourceManager.getImage("sentShoot16").getFlippedCopy(true, false), ResourceManager.getImage("sentShoot17").getFlippedCopy(true, false), ResourceManager.getImage("sentShoot18").getFlippedCopy(true, false)};
        Image[] dyingRight = {ResourceManager.getImage("sentDie1").getFlippedCopy(true, false), ResourceManager.getImage("sentDie2").getFlippedCopy(true, false), ResourceManager.getImage("sentDie3").getFlippedCopy(true, false), ResourceManager.getImage("sentDie4").getFlippedCopy(true, false), ResourceManager.getImage("sentDie5").getFlippedCopy(true, false), ResourceManager.getImage("sentDie6").getFlippedCopy(true, false)};


        int[] dAttack = {100, 100, 100, 100, 100, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80};

        int[] dDeath = {180, 180, 180, 100, 110, 120};

        attackLeft = new Animation(attacksLeft, dAttack, false);

        deathLeft = new Animation(dyingLeft, dDeath, true);

        attackRight = new Animation(attacksRight, dAttack, false);

        deathRight = new Animation(dyingRight, dDeath, true);
        for (int i = 0; i < attacksLeft.length; i++) {
            attacksLeft[i] = null;
        }
        for (int i = 0; i < attacksRight.length; i++) {
            attacksRight[i] = null;
        }
        for (int i = 0; i < dyingRight.length; i++) {
            dyingLeft[i] = null;
        }
        for (int i = 0; i < dyingLeft.length; i++) {
            dyingRight[i] = null;
        }

        dead = false;
        gone = false;
        sprite = attackLeft;
        setHitBox(52, 20, 29, 29);
        this.y = this.y - sprite.getHeight();
        mapX = mapx;
        mapY = mapy - sprite.getHeight();
    }

    /*take damage
     *
     *pre: takes in the damage
     *post: subtract the damage from health
     */
    public void loseHealth(int damage) {
	health -= damage;
    }

    /*get the frame on the animation
     *
     *pre: nothing
     *post: gets the frame number
     */
    public int getAnimationFrame() {
	return sprite.getFrame();
    }
    
    /*gets the size of the animation
     *
     *pre: nothing
     *post: returns the size
     */
    public int getAnimationFrameCount() {
	return sprite.getFrameCount();
    }

    /* checks if the droid is on screen
     *
     *pre: nothing
     *post: get's if it's on screen or not
     */
    public boolean getOnScreen() {
	return onScreen;
    }

    /* set the droid
     *
     *pre: the position
     *post: set it's coordinates
     */
    public void setCoordinates(float x, float y) {
	this.x = x;
	this.y = y;

    }

    /* sets the direction the droids are facing
     *
     *pre: get's the direction
     *post: if i is 0, look left, if i is 1, look right
     */
    public void setFacing(int i) {
        facing = i;
        if (facing == 0) {
            sprite = attackLeft;
            sprite.setAutoUpdate(true);
        } else if (facing == 1) {
            sprite = attackRight;
            sprite.setAutoUpdate(true);
        }
    }

    /*takes out the droid
     *
     *pre:nothing
     *post:removes and destroys
     */
    public void destroyAndRemove() {
	this.removedFromWorld();
	this.destroy();
    }

    /*generates bullets
     *
     *pre:player position and if prone or not
     *post:makes bullet that shoots towards player
     */
    public MainGameEnemyBullet genBullet(float pX, float pY) {
        MainGameEnemyBullet bullet = null;
        float targX = (float) (Math.random() * (pX + 50) + (pX - 100));
        bullet = new MainGameEnemyBullet(targX, pY - 300, mapX, mapY, bulletType, facing);
        bullet.setVelocY(-10);
        justShot = true;
        return bullet;
    }

    /*checks if the droid just shot
     *
     *pre: nothing
     *post: returns if it just shot or not
     */
    public boolean getJustShot() {
	return justShot;
    }

    /*checks if the droid is in the range of the screen
     *
     *pre:what you see
     *post: true or false depending on if it's on screen or not
     * 
     */
    public void inScreenRange(int currentViewX, int currentViewY) {
        if ((mapX > currentViewX - 100) && (mapY > currentViewY - 100)) {
            onScreen = true;
        } else {
            onScreen = false;
        }
    }

    /*gets width
     *
     *pre: nothing
     *post: gets the width
     */
    public int getSpriteWidth() {
	return sprite.getWidth();
    }

    /*stops the animation
     *
     *pre: nothing
     *post: kills it
     */
    public void stopDrawing() {
	dead = true;
    }

    /*get the state the droid is in
     *
     *pre: nothing
     *post: returns if dead or not
     */
    public boolean dead() {
	return dead;
    }

    /*makes it go away
     *
     *pre:true or false
     *post:if true, it goes away
     */
    public void setGone(boolean g) {
	gone = g;
    }

    /*shooting animation
     *
     *pre:nothing
     *post:faces you and and changes to shooting animation
     */
    public void shoot() {
        if (facing == 0) {
            sprite = attackLeft;
            sprite.setAutoUpdate(true);
        } else if (facing == 1) {
            sprite = attackRight;
            sprite.setAutoUpdate(true);
        }
    }

    /* get height
     *
     *pre: nothing
     *post: gets the height
     */
    public int getSpriteHeight() {
	return sprite.getHeight();
    }

    /*moves the x
     *
     *pre: distance
     *post: changes the spot according to distance
     */
    public void moveX(float distance) {
	x -= distance;
    }
    
    /*moves the y
     *
     *pre: distance
     *post: changes the spot according to distance
     */
    public void moveY(float distance) {
	y -= distance;
    }

    /* freezes the enemy
     *
     *pre:nothing
     *post:freezes the enemy
     */
    public void freeze() {
	sprite.setAutoUpdate(false);
	
	frozen = true;
    }
    
    /* gets if frozen
     *
     *pre:nothing
     *post:gets true /false
     */
    public boolean getFrozen() {
	return frozen;
    }
    
    /*unfreezes everything
     *
     *pre: nothing
     *post: makes things move again
     */
    public void unFreeze() {
	sprite.setAutoUpdate(true);

	frozen = false;
    }

    /* checks if in range of char
     *
     *pre: the x and y position
     *post: sets the boolean to true or false
     */
    public void inRange(float x, float y) {
        float dist = (mapX - 70) - x;
        float dist2 = mapY - y;
        dist = Math.abs(dist);
        dist2 = Math.abs(dist2);
        if (dist <= 300 && dist >= 0 && dist2 <= 200 && dist2 >= 0) {
            inRange = true;

        } else {
            inRange = false;

        }
    }

    /*gets if inrange or not
     *
     *pre:nothing
     *post:gets if inrange
     */
    public boolean getInRange() {
        return inRange;
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        super.update(container, delta);
        if (dead == false) {
            if (collide("mBullet", x, y) != null) {
                loseHealth(10);
            }

            if (health < 0) {
                dead = true;
                setHitBox(0, 0, 0, 0, false);
                if (facing == 0) {
                    sprite = deathLeft;
                } else if (facing == 1) {
                    sprite = deathRight;
                }
                sprite.setLooping(false);
            }
            if (justShot) {
                time += delta;
                if (time > 100) {
                    justShot = false;
                    time = 0;
                }
            }
        } else if (dead == true) {
            if (getAnimationFrame() == getAnimationFrameCount() - 1) {
                setGone(true);
                destroyAndRemove();

            }
        }
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        super.render(container, g);
        if (onScreen) {
            if (dead != true) {

                sprite.draw(x, y);

            } else if (dead && gone == false) {
                sprite.draw(x, y);
            }

        }
    }

    /* if it dies, play it's death
     *
     *pre: nothing
     *post: makes it look like it's dying
     */
    public void playDeathAnimation() {
        if (facing == 0) {
            sprite = deathLeft;
        } else if (facing == 1) {
            sprite = deathRight;
        }
    }
}
