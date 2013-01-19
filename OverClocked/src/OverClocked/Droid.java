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
public class Droid extends Entity {

    public static String TYPE = "MAINGAMEENEMY";
    private Animation sprite, attackLeft, attackRight, moveLeft, moveRight, deathLeft, deathRight;
    private int health = 100, bulletType = 3, facing = 0;//0 = left, 1 = right;
    private boolean dead, inRange, onScreen, justShot, gone,frozen;
    private int rangeX, rangeY, time;
    public float mapX, mapY, angle;
    
    /*constructor
     *
     *pre: coordinates on screen and on map
     *post: initializes the animations for a droid
     */
    public Droid(float x, float y, float mapx, float mapy) {
	super(x, y);
	addType(TYPE);

	Image[] attacksLeft = {ResourceManager.getImage("droidShoot1"), ResourceManager.getImage("droidShoot2"), ResourceManager.getImage("droidShoot3"), ResourceManager.getImage("droidShoot4"), ResourceManager.getImage("droidShoot5"), ResourceManager.getImage("droidShoot6"), ResourceManager.getImage("droidShoot7"), ResourceManager.getImage("droidShoot8"), ResourceManager.getImage("droidShoot9"), ResourceManager.getImage("droidShoot10")};
	Image[] deathsLeft = {ResourceManager.getImage("droidDie1"), ResourceManager.getImage("droidDie2"), ResourceManager.getImage("droidDie3"), ResourceManager.getImage("droidDie4"), ResourceManager.getImage("droidDie5"), ResourceManager.getImage("droidDie6")};
	Image[] MoveLeft = {ResourceManager.getImage("droidMove1"), ResourceManager.getImage("droidMove2"), ResourceManager.getImage("droidMove3"), ResourceManager.getImage("droidMove4")};

	Image[] attacksRight = {ResourceManager.getImage("droidShoot1").getFlippedCopy(true, false), ResourceManager.getImage("droidShoot2").getFlippedCopy(true, false), ResourceManager.getImage("droidShoot3").getFlippedCopy(true, false), ResourceManager.getImage("droidShoot4").getFlippedCopy(true, false), ResourceManager.getImage("droidShoot5").getFlippedCopy(true, false), ResourceManager.getImage("droidShoot6").getFlippedCopy(true, false), ResourceManager.getImage("droidShoot7").getFlippedCopy(true, false), ResourceManager.getImage("droidShoot8").getFlippedCopy(true, false), ResourceManager.getImage("droidShoot9").getFlippedCopy(true, false), ResourceManager.getImage("droidShoot10").getFlippedCopy(true, false)};
	Image[] deathsRight = {ResourceManager.getImage("droidDie1").getFlippedCopy(true, false), ResourceManager.getImage("droidDie2").getFlippedCopy(true, false), ResourceManager.getImage("droidDie3").getFlippedCopy(true, false), ResourceManager.getImage("droidDie4").getFlippedCopy(true, false), ResourceManager.getImage("droidDie5").getFlippedCopy(true, false), ResourceManager.getImage("droidDie6").getFlippedCopy(true, false)};
	Image[] MoveRight = {ResourceManager.getImage("droidMove1").getFlippedCopy(true, false), ResourceManager.getImage("droidMove2").getFlippedCopy(true, false), ResourceManager.getImage("droidMove3").getFlippedCopy(true, false), ResourceManager.getImage("droidMove4").getFlippedCopy(true, false)};


	int[] dAttack = {180, 180, 180, 180, 180, 180, 180, 180, 180, 180};
	int[] dDeath = {150, 150, 150, 150, 150, 150};
	int[] dMove = {210, 210, 210, 210};
	attackLeft = new Animation(attacksLeft, dAttack, true);
	deathLeft = new Animation(deathsLeft, dDeath, true);
	moveLeft = new Animation(MoveLeft, dMove, true);

	attackRight = new Animation(attacksRight, dAttack, true);
	moveRight = new Animation(MoveRight, dMove, true);
	deathRight = new Animation(deathsRight, dDeath, true);
	
	 for (int i = 0; i < attacksLeft.length; i++) {
	    attacksLeft[i] = null;
	}
	for (int i = 0; i < attacksRight.length; i++) {
	    attacksRight[i] = null;
	}
	for (int i = 0; i < deathsRight.length; i++) {
	    deathsLeft[i] = null;
	}
	for (int i = 0; i < MoveRight.length; i++) {
	    MoveRight[i] = null;
	}
	for (int i = 0; i < deathsLeft.length; i++) {
	    deathsRight[i] = null;
	}
	for (int i = 0; i < MoveLeft.length; i++) {
	    MoveLeft[i] = null;
	}
	dead = false;
	gone = false;
	sprite = moveRight;
	setHitBox(7, 2, 51, 49);
	onScreen = false;
	this.y = this.y - sprite.getHeight();
	mapX = mapx;
	mapY = mapy - sprite.getHeight();
    }
    
    /*get the state the droid is in
     *
     *pre: nothing
     *post: returns if dead or not
     */
    public boolean dead() {
	return dead;
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
    
    /*take damage
     *
     *pre: takes in the damage
     *post: subtract the damage from health
     */
    public void loseHealth(int damage) {
	health -= damage;
    }
    
    /*stops the animation
     *
     *pre: nothing
     *post: kills it
     */
    public void stopDrawing() {
	dead = true;
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
    
    /* checks if the droid is on screen
     *
     *pre: nothing
     *post: get's if it's on screen or not
     */
    public boolean getOnScreen() {
	return onScreen;
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
    
    /*takes out the droid
     *
     *pre:nothing
     *post:removes and destroys
     */
    public void destroyAndRemove() {
	this.removedFromWorld();
	this.destroy();
    }
    
    /* checks if in range of char
     *
     *pre: the x and y position
     *post: sets the boolean to true or false
     */
    public void inRange(float x, float y) {
	float dist = (mapX + 40) - x;
	float dist2 = mapY - y;
	dist = Math.abs(dist);
	dist2 = Math.abs(dist2);
	if (dist <= 500 && dist >= 0 && dist2 <= 250 && dist2 >= 0) {
	    inRange = true;

	} else {
	    inRange = false;

	}
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
    
    /*generates bullets
     *
     *pre:player position and if prone or not
     *post:makes bullet that shoots towards player
     */
    public MainGameEnemyBullet genBullet(float pX, float pY, boolean prone) {
	MainGameEnemyBullet bullet = null;
	if (prone != true) {
	    pX = pX;
	    pY = pY + 50;
	} else if (prone) {
	    pX = pX + 70;
	    pY = pY += 95;
	}
	if (facing == 0) {//LEft
	    angle = (float) getTargetAngle(mapX + 23, mapY + 63, pX, pY);
	    angle = (float) Math.toRadians(angle);
	    bullet = new MainGameEnemyBullet(x + 23, y + 63, mapX + 23, mapY + 63, bulletType, facing);
	} else if (facing == 1) {//Right
	    angle = (float) getTargetAngle(mapX + 53, mapY + 63, pX, pY);
	    angle = (float) Math.toRadians(angle);
	    bullet = new MainGameEnemyBullet(x + 53, y + 63, mapX + 23, mapY + 63, bulletType, facing);
	}
	bullet.setVelocX((float) (14 * Math.sin(angle)));
	bullet.setVelocY((float) (14 * Math.cos(angle)));
	justShot = true;

	return bullet;
    }
    
    /*gets the angle to shoot at
     *
     *pre: start and end points
     *post: gets the angle using trig
     */
    public double getTargetAngle(float startX, float startY, float targetX, float targetY) {
	double angle = 0;
	if (facing == 0) {
	    angle = (Math.toDegrees(Math.atan2(startY - targetY, startX - targetX)) - 90);
	} else if (facing == 1) {
	    angle = (Math.toDegrees(Math.atan2(startY - targetY, startX - targetX)) - 90);
	}
	if (angle < 0) {
	    angle += 360;
	}
	return angle;
    }
    
    /*checks if the droid is in the range of the screen
     *
     *pre:what you see
     *post: true or false depending on if it's on screen or not
     * 
     */
    public void inScreenRange(int currentViewX, int currentViewY) {
	if ((mapX > currentViewX - 200) && (mapY > currentViewY - 200)) {
	    onScreen = true;
	} else {
	    onScreen = false;
	}
    }
    
    /*makes it go away
     *
     *pre:true or false
     *post:if true, it goes away
     */
    public void setGone(boolean g) {
	gone = g;
    }
    
    /*gets if inrange or not
     *
     *pre:nothing
     *post:gets if inrange
     */
    public boolean getInRange() {
	return inRange;
    }
    
    /*checks if the droid just shot
     *
     *pre: nothing
     *post: returns if it just shot or not
     */
    public boolean getJustShot() {
	return justShot;
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
    
    /* sets the direction the droids are facing
     *
     *pre: get's the direction
     *post: if i is 0, look left, if i is 1, look right
     */
    public void setFacing(int i) {
	facing = i;
    }
}
