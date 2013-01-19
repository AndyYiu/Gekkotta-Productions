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
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *
 * @author admin
 */
public class MiniGameEnemy extends Entity {

    private int miniGame;
    private Image enemy;
    public Animation object;
    private static String type = "MINIGAMEENEMY";
    public boolean right = true, left = false, up, down;
    private float speedChoice, velocX, velocY;
    private Random randNum;
    private int health;

    public MiniGameEnemy(float x, float y, int c) {
	super(x, y);
	miniGame = c;
	this.addType(type);

	switch (miniGame) {
	    case 1://Frogger Enemy
		enemy = ResourceManager.getImage("blackHead");
		setGraphic(enemy);
		newRandomSpeed();
		break;
	    case 2://Guitar hero orbs
		int duration[] = {100, 100, 100, 100};
		Image[] animations = {ResourceManager.getImage("orb1"), ResourceManager.getImage("orb2"), ResourceManager.getImage("orb3"), ResourceManager.getImage("orb4")};
		object = new Animation(animations, duration, true);
		newRandomSpeed();

		break;
	    case 3://pong paddle
		enemy = ResourceManager.getImage("paddle");
		setGraphic(enemy);
		setHitBox(0, 0, enemy.getWidth(), enemy.getHeight());
		speedChoice = (float) 0.399999999;
		break;
	    case 4:
		break;
	    case 5:
		break;
	    case 6://cannon enemies
		enemy = ResourceManager.getImage("blackHead");
		setGraphic(enemy);
		setHitBox(0, 0, enemy.getWidth(), enemy.getHeight());
		newRandomSpeed();
		break;
	    case 7:
		break;
	    case 8:
		break;
	    case 9://Zombie Survival
		randNum = new Random();
		enemy = ResourceManager.getImage("blackHead");
		setGraphic(enemy);
		setHitBox(0, 0, enemy.getWidth(), enemy.getHeight());
		break;
	    case 10:
		break;
	    case 11:
		enemy = ResourceManager.getImage("blackHead");
		setGraphic(enemy);
		setHitBox(0, 0, enemy.getWidth(), enemy.getHeight());
		break;
	    case 12:
		enemy = ResourceManager.getImage("drivingTarget");
		setGraphic(enemy);
		setHitBox(0, 0, enemy.getWidth(), enemy.getHeight());
		newRandomSpeed();
		break;
	    case 13:
		enemy = ResourceManager.getImage ("blackHead");
		setGraphic(enemy);
		setHitBox (0, 0, enemy.getWidth(), enemy.getHeight());
		newRandomSpeed();
		break;
	    case 14:
		enemy = ResourceManager.getImage ("blackHead");
		setGraphic(enemy);
		setHitBox (0, 0, enemy.getWidth(), enemy.getHeight());
		newRandomSpeed();
		speedChoice = (float)0.9;
		break;
		case 15:
		enemy = ResourceManager.getImage("terrorist");
		setGraphic(enemy);
		break;
	    case 16:
		randNum = new Random();
		enemy = ResourceManager.getImage("blackHead");
		setGraphic(enemy);
		setHitBox(0, 0, enemy.getWidth(), enemy.getHeight());
		break;
	}
    }

	//pre: none
	//post: resets the position of the enemies
	//resets the position of the enemies
    public void resetPosition() {
	switch (miniGame) {
	    case 6://cannon
		y = -50;
		break;
	    case 9: //Zombie
		boolean cycle = randNum.nextBoolean();
		if (cycle) {
		    cycle = randNum.nextBoolean();
		    x = (float) (Math.random() * 0 + 800);
		    if (cycle) {
			y = (float) (Math.random() * 750 + 900);
		    } else if (!cycle) {
			y = (float) (Math.random() * -350 + -150);
		    }
		} else if (!cycle) {
		    cycle = randNum.nextBoolean();
		    y = (float) (Math.random() * 0 + 600);
		    if (cycle) {
			x = (float) (Math.random() * -100 + -200);
		    } else if (!cycle) {
			x = (float) (Math.random() * 900 + 1000);
		    }
		}
		break;
	    case 12:
		y = -50;
		break;
	    case 13:
		y = -50;
		break;
	    case 14:
		y = -50;
		break;
		case 15:
		y = -800;
		break;
	    case 16:
		 boolean cycle1 = randNum.nextBoolean();
		 if (cycle1) {
		     cycle1 = randNum.nextBoolean();
		     x = (float) (Math.random() * 0 + 800);
		     if (cycle1) {
			 y = (float) (Math.random() * 750 + 900);
		     } else if (!cycle1) {
			 y = (float) (Math.random() * -350 + -150);
		     }
		 } else if (!cycle1) {
		     cycle1 = randNum.nextBoolean();
		     y = (float) (Math.random() * 0 + 600);
		     if (cycle1) {
			 x = (float) (Math.random() * -100 + -200);
		     } else if (!cycle1) {
			 x = (float) (Math.random() * 900 + 1000);
		     }
		 }
		 break;
	}
    }

	//pre: none
	//post: enemies have new random speeds
	//gives enemies random speeds
    public void newRandomSpeed() {
	switch (miniGame) {
	    case 1:
		speedChoice = (float) (Math.random() * 0.8 + 0.5);
		break;
	    case 2:
		speedChoice = (float) (Math.random() * 0.5 + 0.2);
		break;
	    case 6:
		speedChoice = (float) (Math.random() * 0.4 + 0.1);
	    case 12:
		speedChoice = (float)0.5;
		break;
	    case 13:
		speedChoice = (float)(Math.random()*0.5+0.2);
		break;
	    case 14:
		speedChoice = (float)(Math.random()*0.9+0.1);
		break;
	}
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
	super.render(container, g);
	if (miniGame == 2) {
	    object.draw(x, y);
	}
    }

	//pre: float s
	//post: sets velocX to s
	//sets velocity X
    public void setVelocX(float s) {
	velocX = s;
    }
	//pre: float s
	//post: sets velocY to s
	//sets velocity Y
    public void setVelocY(float s) {
	velocY = s;
    }

	//pre: none
	//post: returns velocX
	//returns velocX
    public float getVelocX() {
	return velocX;
    }

	//pre: none
	//post: returns velocY
	//returns velocY
    public float getVelocY() {
	return velocY;
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
	super.update(container, delta);

	switch (miniGame) {
	    case 1:
		if (right == true) {
		    x += (speedChoice * delta);
		    if (x + enemy.getWidth() > 800) {
			right = false;
			left = true;
		    }
		} else if (left == true) {
		    x -= (speedChoice * delta);
		    if (x < 0) {
			left = false;
			right = true;
		    }
		}
		break;
	    case 2:
		y += (delta * speedChoice);
		if (y > 800) {
		    y = -50;
		    newRandomSpeed();
		}
		break;
	    case 3:
		if (up == true) {
		    y -= (speedChoice * delta);
		    if (y < 101) {
			up = false;
			down = true;
		    }
		} else if (down == true) {
		    y += (speedChoice * delta);
		    if (y + enemy.getHeight() > 599) {
			down = false;
			up = true;
		    }
		}
		break;
	    case 4:
		break;
	    case 5:
		break;
	    case 6:
		y += (delta * speedChoice);
		if (y > 800) {
		    y = -50;
		    newRandomSpeed();
		}
		if (collide("MINIGAMEOBJECT", x, y) != null) {
		    resetPosition();
		}
		break;
	    case 7:
		break;
	    case 8:
		break;
	    case 9:
		x = x + velocX;
		y = y + velocY;

		break;
	    case 10:
		break;
	    case 11:
		break;
	    case 12:
		y += (delta * speedChoice);
		if (y > 800) {
		    y = -50;
		    x = (int)(Math.random()*(800-1)+1);
		    newRandomSpeed();
		}
		break;
	    case 13:
		y += (delta * speedChoice);
		if (y > 800) {
		    y = -50;
		    newRandomSpeed();
		}
		break;
	    case 14:
		y += (delta*speedChoice);
		if (y > 800) {
		    y = -50;
                    newRandomSpeed();
		}
		break;
	    case 16:
		x = x + velocX;
		y = y + velocY;
		break;
	}
    }
}
