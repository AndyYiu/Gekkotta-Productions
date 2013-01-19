/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OverClocked;

import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.entity.Entity;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
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
public class Soldier extends Entity {

    public static String TYPE = "MAINGAMEENEMY";
    private Animation sprite, attackLeft, attackRight, moveLeft, moveRight, deathLeft, deathRight;
    private int health = 100, bulletType = 1, facing = 0, time;//0 = left, 1 = right;
    private boolean dead, inRange, onGround, onScreen, justShot, gone,frozen;
    private float rangeX, rangeY;
    private float pSpeed = 0.4f;
    private int delta;
    private int jumping; // 1= rising, 2 = readjusting,0 = not jumping
    public float mapX, mapY;

    public Soldier(float x, float y, float mapx, float mapy) {
        super(x, y);

        addType(TYPE);
        Image[] attacksLeft = {ResourceManager.getImage("soldShoot2"), ResourceManager.getImage("soldShoot3"), ResourceManager.getImage("soldShoot4"), ResourceManager.getImage("soldShoot5"), ResourceManager.getImage("soldShoot6"), ResourceManager.getImage("soldShoot7"), ResourceManager.getImage("soldShoot8"), ResourceManager.getImage("soldShoot9"), ResourceManager.getImage("soldShoot10"), ResourceManager.getImage("soldShoot11"), ResourceManager.getImage("soldShoot12"), ResourceManager.getImage("soldShoot13"), ResourceManager.getImage("soldShoot14"), ResourceManager.getImage("soldShoot15"), ResourceManager.getImage("soldShoot16")};
        Image[] movementLeft = {ResourceManager.getImage("soldMove1"), ResourceManager.getImage("soldMove2"), ResourceManager.getImage("soldMove3"), ResourceManager.getImage("soldMove4"), ResourceManager.getImage("soldMove5"), ResourceManager.getImage("soldMove6")};
        Image[] dyingLeft = {ResourceManager.getImage("soldDie1"), ResourceManager.getImage("soldDie2"), ResourceManager.getImage("soldDie3"), ResourceManager.getImage("soldDie4"), ResourceManager.getImage("soldDie5"), ResourceManager.getImage("soldDie6"), ResourceManager.getImage("soldDie7"), ResourceManager.getImage("soldDie8"), ResourceManager.getImage("soldDie9")};

        Image[] attacksRight = {ResourceManager.getImage("soldShoot2").getFlippedCopy(true, false), ResourceManager.getImage("soldShoot3").getFlippedCopy(true, false), ResourceManager.getImage("soldShoot4").getFlippedCopy(true, false), ResourceManager.getImage("soldShoot5").getFlippedCopy(true, false), ResourceManager.getImage("soldShoot6").getFlippedCopy(true, false), ResourceManager.getImage("soldShoot7").getFlippedCopy(true, false), ResourceManager.getImage("soldShoot8").getFlippedCopy(true, false), ResourceManager.getImage("soldShoot9").getFlippedCopy(true, false), ResourceManager.getImage("soldShoot10").getFlippedCopy(true, false), ResourceManager.getImage("soldShoot11").getFlippedCopy(true, false), ResourceManager.getImage("soldShoot12").getFlippedCopy(true, false), ResourceManager.getImage("soldShoot13").getFlippedCopy(true, false), ResourceManager.getImage("soldShoot14").getFlippedCopy(true, false), ResourceManager.getImage("soldShoot15").getFlippedCopy(true, false), ResourceManager.getImage("soldShoot16").getFlippedCopy(true, false)};
        Image[] movementRight = {ResourceManager.getImage("soldMove1b").getFlippedCopy(true, false), ResourceManager.getImage("soldMove2b").getFlippedCopy(true, false), ResourceManager.getImage("soldMove3b").getFlippedCopy(true, false), ResourceManager.getImage("soldMove4b").getFlippedCopy(true, false), ResourceManager.getImage("soldMove5b").getFlippedCopy(true, false), ResourceManager.getImage("soldMove6b").getFlippedCopy(true, false)};
        Image[] dyingRight = {ResourceManager.getImage("soldDie1").getFlippedCopy(true, false), ResourceManager.getImage("soldDie2").getFlippedCopy(true, false), ResourceManager.getImage("soldDie3").getFlippedCopy(true, false), ResourceManager.getImage("soldDie4").getFlippedCopy(true, false), ResourceManager.getImage("soldDie5").getFlippedCopy(true, false), ResourceManager.getImage("soldDie6").getFlippedCopy(true, false), ResourceManager.getImage("soldDie7").getFlippedCopy(true, false), ResourceManager.getImage("soldDie8").getFlippedCopy(true, false), ResourceManager.getImage("soldDie9").getFlippedCopy(true, false)};


        int[] dAttack = {90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90};
        int[] dMove = {120, 120, 120, 120, 120, 120};
        int[] dDeath = {150, 150, 150, 150, 150, 150, 150, 150, 150};
        attackLeft = new Animation(attacksLeft, dAttack, true);
        moveLeft = new Animation(movementLeft, dMove, true);
        deathLeft = new Animation(dyingLeft, dDeath, true);

        attackRight = new Animation(attacksRight, dAttack, true);
        moveRight = new Animation(movementRight, dMove, true);
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
        for (int i = 0; i < movementRight.length; i++) {
            movementRight[i] = null;
        }
        for (int i = 0; i < dyingLeft.length; i++) {
            dyingRight[i] = null;
        }
        for (int i = 0; i < movementLeft.length; i++) {
            movementLeft[i] = null;
        }
        jumping = 0;
        onGround = true;
        gone = false;
        sprite = moveLeft;
        dead = false;
        setHitBox(90, 9, 63, 100);
        this.y = this.y - sprite.getHeight();
        mapX = mapx;
        mapY = mapy - sprite.getHeight();

    }

    /*generates bullets
     *
     *pre:player position and if prone or not
     *post:makes bullet that shoots towards player
     */
    public MainGameEnemyBullet generateBullet() {
        MainGameEnemyBullet bullet = null;
        if (facing == 0) {//LEft
            bullet = new MainGameEnemyBullet(x + 75, y + 25, mapX + 75, mapY + 25, bulletType, facing);
            bullet.setVelocX((float) (-20));
        } else if (facing == 1) {//right
            bullet = new MainGameEnemyBullet(x + 103, y + 25, mapX + 103, mapY + 25, bulletType, facing);
            bullet.setVelocX((float) (20));
        }
        justShot = true;
        return bullet;

    }

    /* set the droid
     *
     *pre: the position
     *post: set it's coordinates
     */
    public void setCoordinates(float x, float y) {
        this.x = x;
        this.y = y;
        mapY = y;
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

    /*checks if the droid is in the range of the screen
     *
     *pre:what you see
     *post: true or false depending on if it's on screen or not
     * 
     */
    public void inScreenRange(int currentViewX, int currentViewY) {
        if ((mapX > currentViewX - 500) && (mapY > currentViewY - 500)) {
            onScreen = true;
        } else {
            onScreen = false;
        }
    }

    /* checks if the droid is on screen
     *
     *pre: nothing
     *post: get's if it's on screen or not
     */
    public boolean getOnScreen() {
        return onScreen;
    }

    /*shooting animation
     *
     *pre:nothing
     *post:faces you and and changes to shooting animation
     */
    public void shoot() {
        if (facing == 0) {
            sprite = attackLeft;
        } else if (facing == 1) {
            sprite = attackRight;
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

    /*take damage
     *
     *pre: takes in the damage
     *post: subtract the damage from health
     */
    public void loseHealth(int damage) {
        health -= damage;
    }

    /*gets width
     *
     *pre: nothing
     *post: gets the width
     */
    public int getSpriteWidth() {
        return sprite.getWidth();
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

    /*gets if inrange or not
     *
     *pre:nothing
     *post:gets if inrange
     */
    public boolean getInRange() {
        return inRange;
    }

    /*gets if onground or not
     *
     *pre:nothing
     *post:gets if onground
     */
    public boolean getOnGround() {
        return onGround;
    }

    /*sets if inrange or not
     *
     *pre:true or false
     *post:sets if inrange
     */
    public void setOnGround(boolean b) {
        onGround = b;
    }

    /*makes it go away
     *
     *pre:true or false
     *post:if true, it goes away
     */
    public void setGone(boolean g) {
        gone = g;
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
    
    /*checks if the droid just shot
     *
     *pre: nothing
     *post: returns if it just shot or not
     */
    public boolean getJustShot() {
        return justShot;
    }

    /* sets the direction the soldiers are moving
     *
     *pre: get's the direction
     *post: if i is 0, look left, if i is 1, look right
     */
    public void setDirection(int a) {

        if (a == 0) {//move right

            if (jumping == 0) {
                if (facing == 1) {
                    sprite = moveRight;
                } else if (facing == 0) {
                    sprite = moveLeft;
                }
                sprite.setAutoUpdate(true);
                x += pSpeed * delta;
                mapX += pSpeed * delta;
            } else {
                x += pSpeed * delta;
                mapX += pSpeed * delta;
            }
        } else if (a == 1) {//move left

            if (jumping == 0) {
                if (facing == 1) {
                    sprite = moveRight;

                } else if (facing == 0) {
                    sprite = moveLeft;
                }

                sprite.setAutoUpdate(true);
                x -= pSpeed * delta;
                mapX -= pSpeed * delta;
            } else {
                x -= pSpeed * delta;
                mapX -= pSpeed * delta;
            }
        }
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        super.update(container, delta);
        this.delta = delta;
        if (dead == false) {
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
            if (collide("mBullet", x, y) != null) {
                loseHealth(3);
            }
            if (justShot) {
                time += delta;
                if (time > 150) {
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

    public void inRange(float x, float y) {
        float dist = (mapX + 122) - x;
        float dist2 = mapY - y;
        dist = Math.abs(dist);
        dist2 = Math.abs(dist2);
        if (dist <= 700 && dist >= 0 && dist2 <= 100 && dist2 >= 0) {
            inRange = true;

        } else {
            inRange = false;

        }
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        super.render(container, g);
        if (onScreen) {
            if (dead != true) {
               
                if (facing == 1 && sprite == attackRight) {
                    sprite.draw(x + 90, y);
                } else {
                    sprite.draw(x, y);
                }
            } else if (dead && gone == false) {
                sprite.draw(x, y);
            }
        }
    }

    /* sets the direction the soldiers are facing
     *
     *pre: get's the direction
     *post: if i is 0, look left, if i is 1, look right
     */
    public void setFacing(int i) {
        facing = i;
    }
}
