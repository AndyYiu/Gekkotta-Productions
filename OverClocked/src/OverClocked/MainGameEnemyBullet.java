/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OverClocked;

import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.entity.Entity;
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
public class MainGameEnemyBullet extends Entity {

    private final String type = "MAINGAMEENEMYBULLET";
    public Animation objectAnim;
    private Image object;
    private int tx = 12, ty = 30, time = 0, facing, bulletType;//0 = left, 1 = right//tx and ty set the point of rotation
    public float speedChoice, velocX, velocY, mapX, mapY, angle, targetX, targetY, distanceTravelled, storedVelocX, storedVelocY;
    private boolean launched = false, hit = false;

	 /*Constructor
     *
     *pre:Locates position and direction of enemy
     *post: Creates animation when firing
     */
    public MainGameEnemyBullet(float x, float y, float mapx, float mapy, int t, int face) {
        super(x, y);
        addType(type);

        facing = face;
        bulletType = t;

        if (t == 1) {//Soldier
            mapX = mapx;
            mapY = mapy;
            if (facing == 0) {
                Image[] bullets = {ResourceManager.getImage("soldBullet1"), ResourceManager.getImage("soldBullet2"), ResourceManager.getImage("soldBullet3"), ResourceManager.getImage("soldBullet4"), ResourceManager.getImage("soldBullet5"), ResourceManager.getImage("soldBullet6")};
                int[] duration = {100, 100, 100, 100, 100, 100};
                objectAnim = new Animation(bullets, duration, true);

                setHitBox(0, 18, 52, 23);
            } else if (facing == 1) {
                Image[] bullets = {ResourceManager.getImage("soldBullet1").getFlippedCopy(true, false), ResourceManager.getImage("soldBullet2").getFlippedCopy(true, false), ResourceManager.getImage("soldBullet3").getFlippedCopy(true, false), ResourceManager.getImage("soldBullet4").getFlippedCopy(true, false), ResourceManager.getImage("soldBullet5").getFlippedCopy(true, false), ResourceManager.getImage("soldBullet6")};
                int[] duration = {100, 100, 100, 100, 100, 100};
                objectAnim = new Animation(bullets, duration, true);
                setHitBox(46, 18, 42, 25);
            }

        } else if (t == 2) {//Sentinel

            Image[] bullets = {ResourceManager.getImage("sentBullet8"), ResourceManager.getImage("sentBullet7"), ResourceManager.getImage("sentBullet6"), ResourceManager.getImage("sentBullet5"), ResourceManager.getImage("sentBullet4"), ResourceManager.getImage("sentBullet3"), ResourceManager.getImage("sentBullet2"), ResourceManager.getImage("sentBullet1")};
            int[] duration = {80, 80, 80, 80, 80, 80, 80, 80};
            objectAnim = new Animation(bullets, duration, true);
            setHitBox(79, 26, 54, 209);

        } else if (t == 3) {//Droid
            mapX = mapx;
            mapY = mapy;
            object = ResourceManager.getImage("droidBullet");
            setHitBox(0, 0, object.getWidth(), object.getHeight());

        } else if (t == 4) {//Bergamot Missile
            Image[] bullets = new Image[7];
            for (int i = 0; i < bullets.length; i++) {
                bullets[i] = ResourceManager.getImage("bergBullet" + (i + 2));
            }
            int[] duration = {120, 120, 120, 120, 120, 120, 120};
            objectAnim = new Animation(bullets, duration, false);
            setHitBox(0, 0, 0, 0, false);
            mapX = mapx;
            mapY = mapy;
        } else if (t == 5) {//Dragon Missile
            Image[] bullets = new Image[3];
            for (int i = 0; i < bullets.length; i++) {
                bullets[i] = ResourceManager.getImage("dragBullet" + (i + 1));
            }
            int[] duration = {120, 120, 120};
            objectAnim = new Animation(bullets, duration, true);
            setHitBox(8, 14, 107, 64, false);
            mapX = mapx;
            mapY = mapy;
        }


    }
   	/*moves x
     *
     *pre:Distance
     *post: changes location according to distance on x-axis
     */
    public void moveX(float distance) {
        x -= distance;
    }
	/*moves y
     *
     *pre:Distance
     *post: changes location according to distance on y-axis
     */
    public void moveY(float distance) {
        y -= distance;
    }
	/*When freeze is active
     *
     *pre:
     *post: freezes bullet
     */
    public void freeze() {

        velocY = 0;
        velocX = 0;
    }
	/*When freeze is deactivated
     *
     *pre:
     *post: unfreezes bullet
     */
    public void unFreeze() {
        velocY = storedVelocY;
        velocX = storedVelocX;
    }

    public MainGameEnemyBullet(float x, float y) {
        super(x, y);
    }
	/*determines if bullet is on screen
     *
     *pre:
     *post:
     */
    public boolean withinScreen() {
        if (x > 800 || x < 0 || y > 600 || y < 0) {
            return false;
        } else {
            return true;
        }
    }
	/*removes bullet
     *
     *pre:
     *post: removes bullet
     */
    public void destroyAndRemove() {
        removedFromWorld();
        destroy();
    }
	/*Determines bullet information
     *
     *pre:
     *post: Determines bullet speed and collision according to type
     */
    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        super.update(container, delta);
        if (bulletType == 1 || bulletType == 3 || bulletType == 5) {
            x = x + velocX;
            y = y - velocY;
            mapX += velocX;
            mapY -= velocY;
        } else if (bulletType == 2 || bulletType == 4) {
            y -= velocY;
            mapY -= velocY;

        }
        if (bulletType != 4) {
            if (collide("MAINGAMEPLAYER", x, y) != null) {
                hit = true;
            }

        }

        if (bulletType == 1 || bulletType == 3 || bulletType == 5) {
            {
                if (collide("CollisionTile", mapX, mapY) != null) {
                    velocY = 0;
                    velocX = 0;
                    hit = true;
                }
            }
        }
        if (bulletType == 4) {
            if ((collide("CollisionTile", mapX, mapY + objectAnim.getHeight()) != null) || collide("MAINGAMEPLAYER", mapX, mapY + objectAnim.getHeight()) != null) {
                velocY = 0;
                velocX = 0;
                objectAnim.setAutoUpdate(true);
                setHitBox(17, 68, 114, 131, true);
            }
            if (objectAnim.getFrame() == objectAnim.getFrameCount() - 1) {
                objectAnim.setAutoUpdate(false);
                hit = true;

            }

        }
        if (hit) {


            time += delta;
            if (time > delta + 10) {
                time = 0;
                removedFromWorld();
                destroy();
            }
        }
    }
	/*Draws bullet
     *
     *pre:
     *post:Draws bullet at firing location
     */
    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        super.render(container, g);
        if (bulletType == 1) {
            objectAnim.draw(x, y);
        } else if (bulletType == 2) {
            objectAnim.draw(x, y);
        } else if (bulletType == 3) {
            object.draw(x, y);
        } else if (bulletType == 4) {
            objectAnim.draw(x, y);
        } else if (bulletType == 5) {
            objectAnim.draw(x, y);
        }
    }
	/*Determines velocity of bullet
     *
     *pre:
     *post: Saves how fast bullet is moving along x and y axis
     */
    public void setVelocX(float s) {
        velocX = s;
        storedVelocX = s;
    }

    public void setVelocY(float s) {
        velocY = s;
        storedVelocY = s;
    }
}
