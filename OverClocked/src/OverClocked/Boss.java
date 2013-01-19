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

/**
 *
 * @author fungs4024
 */
public class Boss extends Entity {

    public static String TYPE = "MAINGAMEBOSS";
    public Animation sprite, laser, turret, stand, missile, death;
    public int time, attackPattern;//0 = left, 1 = right;
    private boolean  inRange, onGround, onScreen, justShot, gone;
    private float pSpeed = 0.4f, bulletAngle;
    private Image[] turrets, standing, missles, deaths, lasers;
    private int[] dLasers, dTurret, dStanding, dDeath, dMissiles;
    private int level;//Level 1 = NoBoss Level 2 = 
    private int jumping; // 1= rising, 2 = readjusting,0 = not jumping
    public float mapX, mapY, health;
    public boolean dead;
    
    /*Constructor
     *
     *pre: takes in the coordinates of the screen and coordinates of the map and the level it's on
     *post: makes a boss
     */
    public Boss(float x, float y, float mapx, float mapy, int level) {
	super(x, y);
	this.level = level;
	addType(TYPE);
	switch (level) {
	    case 1:
		health = 1f;
		lasers = new Image[20];//Flamethrower
		for (int i = 0; i < lasers.length; i++) {
		    lasers[i] = ResourceManager.getImage("dragFlame" + (i));
		}
		turrets = new Image[10];//Dragon bullets
		for (int i = 0; i < turrets.length; i++) {
		    turrets[i] = ResourceManager.getImage("dragMissile" + (i + 1));
		}
		standing = new Image[6];
		for (int i = 0; i < standing.length; i++) {
		    standing[i] = ResourceManager.getImage("dragStand" + (i + 1));
		}
		missles = new Image[19];//Melee attack
		for (int i = 0; i < missles.length; i++) {
		    missles[i] = ResourceManager.getImage("dragRush" + (i));
		}
		deaths = new Image[7];
		for (int i = 0; i < deaths.length; i++) {
		    deaths[i] = ResourceManager.getImage("dragDie" + (i));
		}
		dLasers = new int[lasers.length];
		for (int i = 0; i < dLasers.length; i++) {
		    dLasers[i] = 150;
		}
		dTurret = new int[turrets.length];
		for (int i = 0; i < dTurret.length; i++) {
		    dTurret[i] = 120;
		}
		dStanding = new int[standing.length];
		for (int i = 0; i < dStanding.length; i++) {
		    dStanding[i] = 120;
		}
		dMissiles = new int[missles.length];
		for (int i = 0; i < dMissiles.length; i++) {
		    dMissiles[i] = 120;
		}
		dDeath = new int[deaths.length];
		for (int i = 0; i < dDeath.length; i++) {
		    dDeath[i] = 150;
		}

		laser = new Animation(lasers, dLasers, true);
		turret = new Animation(turrets, dTurret, true);
		stand = new Animation(standing, dStanding, false);
		missile = new Animation(missles, dMissiles, true);
		death = new Animation(deaths, dDeath, true);
		sprite = stand;
		attackPattern = 0;
		setHitBox(124, 40, 77, 104);
		break;
	    case 2:
		health = 1f;
		lasers = new Image[38];
		for (int i = 0; i < lasers.length; i++) {
		    lasers[i] = ResourceManager.getImage("bergLaser" + (i + 1));
		}
		turrets = new Image[18];
		for (int i = 0; i < turrets.length; i++) {
		    turrets[i] = ResourceManager.getImage("bergTurret" + (i + 1));
		}
		standing = new Image[15];
		for (int i = 0; i < standing.length; i++) {
		    standing[i] = ResourceManager.getImage("bergStand" + (i + 1));
		}
		missles = new Image[14];
		for (int i = 0; i < missles.length; i++) {
		    missles[i] = ResourceManager.getImage("bergMissile" + (i + 1));
		}
		deaths = new Image[11];
		for (int i = 0; i < deaths.length; i++) {
		    deaths[i] = ResourceManager.getImage("bergDie" + (i + 1));
		}
		dLasers = new int[38];
		for (int i = 0; i < dLasers.length; i++) {
		    dLasers[i] = 150;
		}
		dTurret = new int[18];
		for (int i = 0; i < dTurret.length; i++) {
		    dTurret[i] = 150;
		}
		dStanding = new int[15];
		for (int i = 0; i < dStanding.length; i++) {
		    dStanding[i] = 250;
		}
		dMissiles = new int[14];
		for (int i = 0; i < dMissiles.length; i++) {
		    dMissiles[i] = 120;
		}
		dDeath = new int[11];
		for (int i = 0; i < dDeath.length; i++) {
		    dDeath[i] = 120;
		}

		laser = new Animation(lasers, dLasers, true);
		turret = new Animation(turrets, dTurret, true);
		stand = new Animation(standing, dStanding, true);
		missile = new Animation(missles, dMissiles, true);
		death = new Animation(deaths, dDeath, true);
		sprite = stand;
		attackPattern = 0;

		setHitBox(180, 0, 365, 502);
		break;
	    case 3:
		health = 1f;
		lasers = new Image[40];
		for (int i = 0; i < lasers.length; i++) {
		    lasers[i] = ResourceManager.getImage("nibelLaser" + (i + 1));
		}
		missles = new Image[17];
		for (int i = 0; i < missles.length; i++) {
		    missles[i] = ResourceManager.getImage("nibelBomb" + (i));
		}
		standing = new Image[1];
		standing[0] = ResourceManager.getImage("nibelStand");

		turrets = new Image[42];
		for (int i = 0; i < turrets.length; i++) {
		    turrets[i] = ResourceManager.getImage("nibelBullets" + (i));
		}
		deaths = new Image[7];
		for (int i = 0; i < deaths.length; i++) {
		    deaths[i] = ResourceManager.getImage("nibelDie" + (i));
		}

		dLasers = new int[40];
		for (int i = 0; i < dLasers.length; i++) {
		    dLasers[i] = 150;
		}
		dTurret = new int[42];
		for (int i = 0; i < dTurret.length; i++) {
		    dTurret[i] = 150;
		}
		dStanding = new int[1];
		for (int i = 0; i < dStanding.length; i++) {
		    dStanding[i] = 250;
		}
		dMissiles = new int[17];
		for (int i = 0; i < dMissiles.length; i++) {
		    dMissiles[i] = 120;
		}
		dDeath = new int[7];
		for (int i = 0; i < dDeath.length; i++) {
		    dDeath[i] = 120;
		}

		laser = new Animation(lasers, dLasers, true);
		turret = new Animation(turrets, dTurret, true);
		stand = new Animation(standing, dStanding, false);
		missile = new Animation(missles, dMissiles, true);
		death = new Animation(deaths, dDeath, true);
		sprite = stand;

		setHitBox(0, 0, 557, 265);
		break;
	}
	
	justShot = false;
	dead = false;
	gone = false;
	this.y = this.y - sprite.getHeight();
	mapX = mapx;
	mapY = mapy - sprite.getHeight();
    }

    /*reads if the boss just shot
     *
     *pre: n/a
     *post: returns a boolean value telling the program wether it shot or not
     */
    public boolean getJustShot() {
	return justShot;
    }

    /*moves the boss horizontally
     *
     *pre: get the distance it needs to move
     *post: moves the boss the distance
     */
    public void moveX(float distance) {
	x -= distance;
    }

    /*moves the boss vertically
     *
     *pre: get the distance it needs to move
     *post: moves the boss the distance
     */
    public void moveY(float distance) {
	y -= distance;
    }
    
    /*moves the map horizontally to chase boss
     *
     *pre: the same distance it needs to move
     *post: moves the map the distance
     */
    public void moveMapX(float distance) {
	mapX -= distance;
    }
    
    /*moves the map vertically to chase boss
     *
     *pre: the same distance it needs to move
     *post: moves the map the distance
     */
    public void moveMapY(float distance) {
	mapY -= distance;
    }
    
    /*spawns the boss
     *
     *pre: nothing
     *post: starts the sprite movement
     */
    public void comeAlive() {
	sprite.setAutoUpdate(true);
    }
    
    /*destroy all the animations used
     *
     *pre: nothing
     *post: destroy ALL the animations
     */
    public void destroyResources() throws SlickException {
	for (int i = 0; i < lasers.length; i++) {
           
	    lasers[i].destroy();
	}

	for (int i = 0; i < turrets.length; i++) {
	    turrets[i].destroy();;
	}

	for (int i = 0; i < standing.length; i++) {
	    standing[i].destroy();
	}

	for (int i = 0; i < missles.length; i++) {
	    missles[i].destroy();
	}

	for (int i = 0; i < deaths.length; i++) {
	    deaths[i].destroy();
	}
    }
    
    /*makes the boss take damage
     *
     *pre: the damage boss needs to take
     *post: health is reduced by damage
     */
    public void loseHealth(float damage) {
	health -= damage;
    }
    
    /*sets if the boss has dissappeared yet
     *
     *pre: takes in a boolean value
     *post: sets variable depending on parameter. when boss is fully off map, gone is true
     */
    public void setGone(boolean g) {
	gone = g;
    }
    
    /*destroys this entity and then removes
     *
     *pre: nothing
     *post: no existence of this boss
     */
    public void destroyAndRemove() {
	this.removedFromWorld();
	this.destroy();
    }
    
    /* generates a bullet
     *
     *pre: gets the position of player on screen and on map and checks if the player is prone
     *post: makes bullets according to the attack patter
     */
    public MainGameEnemyBullet genBullet(float pX, float pY, float pMapX, float pMapY, boolean prone) {
	MainGameEnemyBullet bullet = null;
	switch (level) {
	    case 1://Dragon
		switch (attackPattern) {

		    case 2:
			bullet = new MainGameEnemyBullet(x, y + 200, mapX, mapY + 200, 5, 0);//Shoots Dragon Bolts
			bullet.setVelocX(-10f);
			break;

		}
		break;
	    case 2://Berg
		if (prone != true) {
		    pX = pX;
		    pY = pY + 50;
		} else if (prone) {
		    pX = pX + 70;
		    pY = pY += 95;
		}
		switch (attackPattern) {
		    case 1://Laser(no bullet needed)
			break;
		    case 2:
			this.bulletAngle = (float) getTargetAngle(mapX + 50, mapY + 212, pX, pY);
			this.bulletAngle = (float) Math.toRadians(this.bulletAngle);
			bullet = new MainGameEnemyBullet(x + 50, y + 212, mapX + 80, mapY + 212, 3, 0);//Shoots Droid Bullets
			bullet.setVelocX((float) (23 * Math.sin(this.bulletAngle)));
			bullet.setVelocY((float) (23 * Math.cos(this.bulletAngle)));
			break;
		    case 3://Missiles 
			float targX = (float) (Math.random() * (pX + 100) + (pX - 100));
			bullet = new MainGameEnemyBullet(targX, 0, pMapX, 0, 4, 0);//Bullets dont need to move
			bullet.setVelocY(-15);
			break;

		}
		break;
	    case 3://Nibel
		if (prone != true) {
		    pX = pX;
		    pY = pY + 50;
		} else if (prone) {
		    pX = pX + 70;
		    pY = pY += 95;
		}
		switch (attackPattern) {
		    case 2:
			this.bulletAngle = (float) getTargetAngle(mapX + 80, mapY + 212, pX, pY);
			this.bulletAngle = (float) Math.toRadians(this.bulletAngle);
			bullet = new MainGameEnemyBullet(x + 726, y + 96, mapX + 726, mapY + 96, 3, 0);//Shoots Droid Bullets
			bullet.setVelocX((float) (17 * Math.sin(this.bulletAngle)));
			bullet.setVelocY((float) (17 * Math.cos(this.bulletAngle)));
			break;
		}
		break;


	}

	justShot = true;
	return bullet;
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
	super.update(container, delta);
	switch (level) {
	    case 1://DRAGON
		if (dead == false) {
		    if (collide("mBullet", x, y) != null) {
			loseHealth(0.005f);
		    }
		    if (health < 0) {
			dead = true;
			setHitBox(0, 0, 0, 0, false);
			sprite = death;
			sprite.setLooping(false);
		    }
		    if (justShot) {
			time += delta;
			if (time > 150) {
			    justShot = false;
			    time = 0;
			}
		    }
		    if (sprite.getFrame() == sprite.getFrameCount() - 1) {
			attackPattern = (int) (Math.random() * 4 + 0);
			switch (attackPattern) {
			    case 0:
				sprite = stand;
				break;
			    case 1:
				sprite = laser;
				break;
			    case 2:
				sprite = turret;
				break;
			    case 3:
				sprite = missile;
				break;

			}
		    }

		}
		break;
	    case 2://BERGAMOT
		if (dead == false) {
		    if (collide("mBullet", x, y) != null) {
			loseHealth(0.002f);
		    }
		    if (health < 0) {
			dead = true;
			setHitBox(0, 0, 0, 0, false);
			sprite = death;
			sprite.setLooping(false);
		    }
		    if (justShot) {
			time += delta;
			if (time > 50) {
			    justShot = false;
			    time = 0;
			}
		    }
		    if (sprite.getFrame() == sprite.getFrameCount() - 1) {
			attackPattern = (int) (Math.random() * 4 + 0);
			switch (attackPattern) {
			    case 0:
				sprite = stand;
				break;
			    case 1:
				sprite = laser;
				break;
			    case 2:
				sprite = turret;
				break;
			    case 3:
				sprite = missile;
				break;

			}
		    }

		}
		break;
	    case 3://Nibel
		if (dead == false) {
		    if (collide("mBullet", x, y) != null) {
			loseHealth(0.0005f);
		    }
		    if (health < 0) {
			dead = true;
			setHitBox(0, 0, 0, 0, false);
			sprite = death;
			sprite.setLooping(false);
		    }
		    if (justShot) {
			time += delta;
			if (time > 100) {
			    justShot = false;
			    time = 0;
			}
		    }
		    if (sprite.getFrame() == sprite.getFrameCount() - 1) {
			attackPattern = (int) (Math.random() * 4 + 0);
			switch (attackPattern) {
			    case 1:
				sprite = laser;
				break;
			    case 2:
				sprite = turret;
				break;
			    case 3:
				sprite = missile;
				break;
			    case 0:
				sprite = stand;
				break;
			}
		    }
		}
		break;

	}
	if (dead == true) {
	    if (sprite.getFrame() == (sprite.getFrameCount() - 1)) {
		setGone(true);
		//destroyAndRemove();

	    }
	}


    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
	super.render(container, g);
	g.drawString("AttackPattern: " + attackPattern, 100, 300);

	switch (level) {
	    case 1:

		if (dead != true) {
		    g.setColor(Color.red);
		    g.fillRect(x + 13, y + 17, 342, 20);
		    g.setColor(Color.green);
		    g.fillRect(x + 13, y + 17, 342 * health, 20);
		    if (sprite == laser) { //flame thrower
			sprite.draw(x - 841, y);
		    } else if (sprite == turret) {//bullets
			sprite.draw(x - 114, y);
		    } else if (sprite == missile) {//rush
			sprite.draw(x - 462, y - 9);
		    } else if (sprite == stand) {
			sprite.draw(x, y);
		    }
		} else if (dead && gone == false) {
		    sprite.draw(x, y);
		}

		break;
	    case 2:

		if (dead != true) {
		    g.setColor(Color.red);
		    g.fillRect(x + 10, y + 12, 432, 22);
		    g.setColor(Color.green);
		    g.fillRect(x + 10, y + 12, 432 * health, 22);
		    if (sprite == laser) {
			sprite.draw(x - 744, y - 51);
		    } else if (sprite == turret) {
			sprite.draw(x - 91, y);
		    } else if (sprite == missile) {
			sprite.draw(x - 378, y - 144);
		    } else if (sprite == stand) {
			sprite.draw(x, y);
		    }
		} else if (dead && gone == false) {
		    sprite.draw(x, y);
		}
		break;
	    case 3:

		if (dead != true) {
		    g.setColor(Color.red);
		    g.fillRect(x + 16, y - 12, 532, 22);
		    g.setColor(Color.green);
		    g.fillRect(x + 16, y - 12, 532 * health, 22);
		    if (sprite == laser) {
			sprite.draw(x - 829, y + 6);
		    } else if (sprite == turret) {
			sprite.draw(x - 407, y + 58);
		    } else if (sprite == missile) {
			sprite.draw(x - 600, y - 430);
		    } else if (sprite == stand) {
			sprite.draw(x - 829, y + 6);
		    }
		} else if (dead && gone == false) {
		    sprite.draw(x, y);
		}
		break;

	}


    }
    
    /* get's the angle that we want the bullets to shoot at
     *
     *pre: the start anf finish point of the bullets
     *post: calculates the angle using trigonometry
     */
    public double getTargetAngle(float startX, float startY, float targetX, float targetY) {
	double angle = 0;
	// ALL bosses always face left
	angle = (Math.toDegrees(Math.atan2(startY - targetY, startX - targetX)) - 90);

	if (angle < 0) {
	    angle += 360;
	}
	return angle;
    }
}
