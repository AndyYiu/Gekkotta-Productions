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
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

/**
 *
 * @author admin
 */
public class MiniGamePlayer extends Entity {

    public static String PLAYER = "MINIGAMEPLAYER";
    private Image players,explosion;
    private Animation player;
    private int choice, health;
    private float gunX, gunY;
    float scale = 1.0f;

    public MiniGamePlayer(float x, float y, int choice) {
	super(x, y);
	this.choice = choice;
	addType(PLAYER);
	define("right", Input.KEY_RIGHT, Input.KEY_D);
	define("left", Input.KEY_LEFT, Input.KEY_A);
	define("up", Input.KEY_UP, Input.KEY_W);
	define("down", Input.KEY_DOWN, Input.KEY_S);
	define("space", Input.KEY_SPACE);
	switch (this.choice) {
	    case 1:
		players = ResourceManager.getImage("squidMonster");
		setGraphic(players);
		setHitBox(-20, -20, 32, 50);
		break;
	    case 2:
		break;
	    case 3://pong
		players = ResourceManager.getImage("paddle");
		setGraphic(players);
		setHitBox(0, 0, players.getWidth(), players.getHeight());
		break;
	    case 4:
		break;
	    case 5:
		break;
	    case 6://cannon
		players = ResourceManager.getImage("cannon");
		players.setCenterOfRotation(50, 100);
	    case 7:
		break;
	    case 8:
		break;
	    case 9:
		health = 100;
		Image temp = ResourceManager.getImage("zPlayer1");
		Image[] animations = {ResourceManager.getImage("zPlayer1"), ResourceManager.getImage("zPlayer2")};
		int[] duration = {50, 50};
		setHitBox(0, 0, temp.getWidth(), temp.getHeight());
		player = new Animation(animations, duration, false);
	       
		break;
	    case 10:
		break;
	    case 11:
		players = ResourceManager.getImage("player");
		setGraphic(players);
		setHitBox(2, 6, 59, 52);
		explosion = ResourceManager.getImage("blackExplosion");
		break;
	    case 12:
		players = ResourceManager.getImage ("car");
		setGraphic (players);
		setHitBox (0, 0, players.getWidth(), players.getHeight());
		break;
	    case 13:
		players = ResourceManager.getImage("player");
		setGraphic (players);
		setHitBox (2, 6, 59, 52);
		break;
	    case 14:
		players = ResourceManager.getImage ("catcher");
		setGraphic (players);
		setHitBox (2, 6, players.getWidth(), players.getHeight());
		break;
		case 15:
		break;
	    case 16:
		players = ResourceManager.getImage("squidMonster");
		setGraphic(players);
		setHitBox(-20, -20, 32, 50);
		break;
	}
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
	super.update(container, delta);
	switch (this.choice) {
	    case 1: //Frogger Stan
		if (check("right") && x + players.getWidth() < 800) {
		    x += (0.3 * delta);
		}
		if (check("left") && x > 0) {
		    x -= (0.3 * delta);
		}
		if (check("up") && y > 0) {
		    y -= (0.3 * delta);
		}
		if (check("down") && y + players.getHeight() < 600) {
		    y += (0.3 * delta);
		}
		if (collide("MINIGAMEENEMY", x, y) != null) {//Set collision detection!
		    x = 300;
		    y = 550;
		}
		break;
	    case 2:
		break;
	    case 3://pong
		if (check("up") && y > 100) {
		    y -= (0.5 * delta);
		}
		if (check("down") && y + players.getHeight() < 600) {
		    y += (0.5 * delta);
		}
		break;
	    case 4:
		break;
	    case 5:
		break;
	    case 6://cannon
		if (check("right") && (players.getRotation() < 75)) {
		    players.rotate(0.2f * delta);
		}
		if (check("left") && (players.getRotation() > -75)) {
		    players.rotate(-0.2f * delta);
		}
		break;
	    case 7:
		break;
	    case 8:
		break;
	    case 9://Zombie Survival Stan
		if (check("right") && x + player.getWidth() < 800) {
		    x += (0.2 * delta);
		}
		if (check("left") && x > 0) {
		    x -= (0.2 * delta);
		}
		if (check("up") && y > 0) {
		    y -= (0.2 * delta);
		}
		if (check("down") && y + player.getHeight() < 600) {
		    y += (0.2 * delta);
		}

		if (container.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {

		    player.setAutoUpdate(true);
		} else {
		    player.setCurrentFrame(0);
		    player.setAutoUpdate(false);
		}
		if (collide("MINIGAMEENEMY", x, y) != null) {
		    health = health - 20;
		    break;
		}
		break;
	    case 10:
		break;
	    case 11:
		if (check("right") && x + players.getWidth() < 800) {
				x += (.4*delta);
			}
			if (check("left") && x > 0) {
				x -= (.4*delta);
			}
			if (check("up") && y > 100) {
				setGraphic (players);
				y -= (.4*delta);
			}
			if (check("down") && y + players.getHeight() < 600) {
				setGraphic (players);
				y += (.4*delta);
			}
			if (collide ("MINIGAMEENEMY", x, y) != null) {
				setGraphic (explosion);
				x = 365;
				y = 400;
			}
		break;
	    case 12:
		if (check("right") && x + players.getWidth() < 800) {
			x += (.7*delta);
		}
		if (check ("left") && x > 0) {
			x -= (.7*delta);
		}
		if (check ("up") && y > 100) {
			y -= (.7*delta);
		}
		if (check ("down") && y + players.getHeight() < 600) {
			y += (.7*delta);
		}
		break;
	    case 13:
		if (check("right") && x + players.getWidth() < 800) {
				x += (.4*delta);
			}
			if (check("left") && x > 0) {
				x -= (.4*delta);
			}
			if (check("up") && y > 100) {
				y -= (.4*delta);
			}
			if (check("down") && y + players.getHeight() < 600) {
				y += (.4*delta);
			}
		break;
	    case 14:
		if (check("right") && x + players.getWidth() < 800) {
			x += (.9*delta);
		}
		if (check ("left") && x > 0) {
			x -= (.9*delta);
		}
		break;
	    case 16: 
		if (check("right") && x + players.getWidth() < 800) {
		    x += (0.3 * delta);
		}
		if (check("left") && x > 0) {
		    x -= (0.3 * delta);
		}
		if (check("up") && y > 0) {
		    y -= (0.3 * delta);
		}
		if (check("down") && y + players.getHeight() < 600) {
		    y += (0.3 * delta);
		}
		break;
	}
    }
	//pre: none
	//post: returns health
	//returns player health
    public int getHealth() {
	return health;
    }

	//pre: angle
	//post: generates bullets
	//generates bullets that can be shot at an angle
    public MiniGameObject generateBullet(float angle, int selection) {
	MiniGameObject bullet = null;
	if (selection == 6) {
	    bullet = new MiniGameObject(345, 590, 6);
	    angle = (float) Math.toRadians(angle);
	    bullet.setVelocX((float) (5 * Math.sin(angle)));
	    bullet.setVelocY((float) (5 * Math.cos(angle)));
	    return bullet;
	}
	if (selection == 9) {
	    bullet = new MiniGameObject(x + 19, y + 48, 9);
	    angle = (float) Math.toRadians(angle);
	    bullet.setVelocX((float) (30 * Math.sin(angle)));
	    bullet.setVelocY((float) (30 * Math.cos(angle)));
	    return bullet;
	}
	return bullet;
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
	super.render(container, g);
	switch (choice) {
	    case 6:
		g.drawImage(players, x, y);
		break;
	    case 9:
		float targetAng = (float) getTargetAngle(x + 19, y + 48, container.getInput().getMouseX(), container.getInput().getMouseY());
		int tx = (19);//tx and ty set the point of rotation
		int ty = (48);
		g.rotate(x + tx, y + ty, targetAng);
		g.drawAnimation(player, x, y);
		g.rotate(x + tx, y + ty, -targetAng);
		break;
	}
    }

	//pre: none
	//post: resets postion
	//resets postion of the player
    public void resetPosition() {
	switch (this.choice) {
	    case 1: //Frogger
		x = 300;
		y = 550;
		break;
	}
    }
	//pre: startX, startY, targetX, targetY
	//post: returns target angle
	//calculates and returns target angle
    public double getTargetAngle(float startX, float startY, float targetX, float targetY) {

	return (Math.toDegrees(Math.atan2(startY - targetY, startX - targetX)) - 90);
    }

	//pre: none
	//post: returns rotation
	//rotates player
    public float getRotate() {
	return (players.getRotation());
    }
}
