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
public class MiniGameObject extends Entity {

    public static String OBJECT = "MINIGAMEOBJECT";
    public Animation objectAnim;
    private Image object;
    private int miniGame;
    private float speedChoice, velocX, velocY;

    public MiniGameObject(float x, float y, int c) {
	super(x, y);
	miniGame = c;
	addType(OBJECT);
	if (miniGame == 2) {//Catcher for guitar hero

	    int duration[] = {30, 300, 50};
	    Image[] animations = {ResourceManager.getImage("troncircle1"), ResourceManager.getImage("troncircle2"), ResourceManager.getImage("troncircle1")};
	    objectAnim = new Animation(animations, duration, false);
	    this.setHitBox(x, 40, 30, 1);

	} else if (miniGame == 3) {//pong ball
	    object = ResourceManager.getImage("pongBall");
	    setGraphic(object);
	    setHitBox(0, 0, object.getWidth(), object.getHeight());
	    velocX = (float) (Math.random() * 0.2 + 0.2);
	    velocY = (float) (Math.random() * 0.2 + 0.2);
	} else if (miniGame == 6) {
	    object = ResourceManager.getImage("laser");
	    setGraphic(object);
	    setHitBox(0, 0, object.getWidth(), object.getHeight());
	    define("right", Input.KEY_RIGHT, Input.KEY_D);
	    define("left", Input.KEY_LEFT, Input.KEY_A);
	    define("shoot", Input.KEY_SPACE);
	} else if (miniGame == 9) {
	    object = ResourceManager.getImage("bullet");
	    setGraphic(object);
	    setHitBox(0, 0, object.getWidth(), object.getHeight());
	} else if (miniGame == 11) {
	    object = ResourceManager.getImage("bullet");
	    setGraphic(object);
	    setHitBox(2, 4, object.getWidth(), object.getHeight());
	} else if (miniGame == 15){
	    object = ResourceManager.getImage("civilian");
	    setGraphic(object);
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
	//post: return velocY
	//return velcoY
    public float getVelocY() {
	return velocY;
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
	super.render(container, g);
	if (miniGame == 2) {
	    objectAnim.draw(x, y);
	} else if (miniGame == 3) {
	    object.draw(x, y);
	}
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
	super.update(container, delta);
	switch (miniGame) {
	    case 1:
		break;
	    case 3:
		x += velocX * delta;
		y += velocY * delta;
		if (y < 101 || y + object.getWidth() > 599) {
		    velocY = -velocY;
		}
		if (collide("MINIGAMEPLAYER", x, y) != null) {//Set collision detection!
		    velocX = -velocX;
		}
		if (collide("MINIGAMEENEMY", x, y) != null) {
		    velocX = -velocX;
		}
		break;
	    case 6:
		if (collide("MINIGAMEENEMY", x, y) != null) {
		    object = null;
		}
		x = x + velocX;
		y = y - velocY;
		break;
	    case 9:
		x = x + velocX;
		y = y - velocY;
		break;
		
	}
    }

	//pre: none
	//post: resets the position of an object
	//resets the position of an object
    public void resetObject() {
	switch (miniGame) {
	    case 3: //pong
		x = 290;
		y = 290;
		break;
		case 15:
		y = -800;
		break;
	}
    }
}
