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

/**
 *
 * @author admin
 */
public class MainGameBullet extends Entity {

    public static String TYPE = "mBullet";
    public Animation bulletTravel, bulletExplode, bullet;
    private Image object;
    private int miniGame, tx = 12, ty = 30, time = 0, facing;//0 = left, 1 = right//tx and ty set the point of rotation
    private float angle;
    private boolean launched = false, hit = false, explosion;
    public float speedChoice, velocX, velocY, mapX, mapY;
    public boolean pierceE;
    public int bulletType;//Default 1, Splash 2, Shoop Da Whoop 3, Nuke 4
    public String[] enemTypes = {"MAINGAMEENEMY", "MAINGAMEBOSS", "CollisionTile"};
    /*Description:Constructor for main game bullet
     * pre: Player has fired a bullet
     * post:Main game bullet is created with base parameters
     */

    public MainGameBullet(float x, float y, float mapx, float mapy, int face, boolean pierceShot, int bType) {
        super(x, y);
        addType(TYPE);
        pierceE = pierceShot;
        facing = face;
        mapX = mapx;
        mapY = mapy;
        bulletType = bType;
        explosion = false;
        switch (bulletType) {
            case 1:

                object = ResourceManager.getImage("bullet");
                setHitBox(0, 0, object.getWidth(), object.getHeight());
                break;
            case 2://Splash

                this.x -= 38;
                this.y -= 60;
                mapX -= 38;
                mapY -= 60;
                Image[] bTravel2 = {ResourceManager.getImage("sb1"), ResourceManager.getImage("sb2"), ResourceManager.getImage("sb3"), ResourceManager.getImage("sb4"), ResourceManager.getImage("sb5"), ResourceManager.getImage("sb6"), ResourceManager.getImage("sb7"), ResourceManager.getImage("sb8"), ResourceManager.getImage("sb9")};
                Image[] bExplode2 = {ResourceManager.getImage("sb10"), ResourceManager.getImage("sb11"), ResourceManager.getImage("sb12"), ResourceManager.getImage("sb13"), ResourceManager.getImage("sb14"), ResourceManager.getImage("sb15")};
                int[] duration1 = new int[bTravel2.length];
                for (int i = 0; i < duration1.length; i++) {
                    duration1[i] = 120;
                }
                int[] duration2 = new int[bExplode2.length];
                for (int i = 0; i < duration2.length; i++) {
                    duration2[i] = 120;
                }
                bulletTravel = new Animation(bTravel2, duration1, true);
                bulletExplode = new Animation(bExplode2, duration2, true);
                bullet = bulletTravel;
                setHitBox(42, 48, 25, 25);
                break;
            case 3://ShoopWoop

                if (face == 0) {
                    this.x -= 85;
                    this.y -= 80;
                    mapX -= 85;
                    mapY -= 80;
                    Image[] bTravel3 = {ResourceManager.getImage("sdw1"), ResourceManager.getImage("sdw2"), ResourceManager.getImage("sdw3"), ResourceManager.getImage("sdw4"), ResourceManager.getImage("sdw5"), ResourceManager.getImage("sdw6"), ResourceManager.getImage("sdw7")};
                    Image[] bExplode3 = {ResourceManager.getImage("sdw8"), ResourceManager.getImage("sdw9"), ResourceManager.getImage("sdw10"), ResourceManager.getImage("sdw11"), ResourceManager.getImage("sdw12"), ResourceManager.getImage("sdw13"), ResourceManager.getImage("sdw14"), ResourceManager.getImage("sdw15"), ResourceManager.getImage("sdw16")};
                    //Rotate point =  659,81 (left)
                    duration1 = new int[bTravel3.length];
                    for (int i = 0; i < duration1.length; i++) {
                        duration1[i] = 150;
                    }
                    duration2 = new int[bExplode3.length];
                    for (int i = 0; i < duration2.length; i++) {
                        duration2[i] = 150;
                    }
                    bulletTravel = new Animation(bTravel3, duration1, true);
                    bulletExplode = new Animation(bExplode3, duration2, true);
                    bullet = bulletTravel;
                    setHitBox(0, 0, 0, 0, false);
                } else if (face == 1) {//Right
                    this.x -= 130;
                    this.y -= 80;
                    mapX -= 130;
                    mapY -= 80;
                    Image[] bTravel3 = {ResourceManager.getImage("sdw1").getFlippedCopy(true, false), ResourceManager.getImage("sdw2").getFlippedCopy(true, false), ResourceManager.getImage("sdw3").getFlippedCopy(true, false), ResourceManager.getImage("sdw4").getFlippedCopy(true, false), ResourceManager.getImage("sdw5").getFlippedCopy(true, false), ResourceManager.getImage("sdw6").getFlippedCopy(true, false), ResourceManager.getImage("sdw7").getFlippedCopy(true, false)};
                    Image[] bExplode3 = {ResourceManager.getImage("sdw8").getFlippedCopy(true, false), ResourceManager.getImage("sdw9").getFlippedCopy(true, false), ResourceManager.getImage("sdw10").getFlippedCopy(true, false), ResourceManager.getImage("sdw11").getFlippedCopy(true, false), ResourceManager.getImage("sdw12").getFlippedCopy(true, false), ResourceManager.getImage("sdw13").getFlippedCopy(true, false), ResourceManager.getImage("sdw14").getFlippedCopy(true, false), ResourceManager.getImage("sdw15").getFlippedCopy(true, false), ResourceManager.getImage("sdw16").getFlippedCopy(true, false)};
                    //Rotate point =  93,81 (Right)
                    duration1 = new int[bTravel3.length];
                    for (int i = 0; i < duration1.length; i++) {
                        duration1[i] = 90;
                    }
                    duration2 = new int[bExplode3.length];
                    for (int i = 0; i < duration2.length; i++) {
                        duration2[i] = 200;
                    }
                    bulletTravel = new Animation(bTravel3, duration1, true);
                    bulletExplode = new Animation(bExplode3, duration2, true);
                    bullet = bulletTravel;
                    setHitBox(0, 0, 0, 0, false);
                }

                break;
            case 4://NukE!!!
                this.x = 0;
                this.y = 0;

                Image[] bTravel4 = {ResourceManager.getImage("ts1"), ResourceManager.getImage("ts2"), ResourceManager.getImage("ts3"), ResourceManager.getImage("ts4")};
                Image[] bExplode4 = {ResourceManager.getImage("ts5"), ResourceManager.getImage("ts6"), ResourceManager.getImage("ts7"), ResourceManager.getImage("ts8"), ResourceManager.getImage("ts9"), ResourceManager.getImage("ts10"), ResourceManager.getImage("ts11"), ResourceManager.getImage("ts12"), ResourceManager.getImage("ts13")};
                duration1 = new int[bTravel4.length];
                duration2 = new int[bExplode4.length];
                for (int i = 0; i < duration1.length; i++) {
                    duration1[i] = 90;
                }
                for (int i = 0; i < duration2.length; i++) {
                    duration2[i] = 100;
                }
                bulletTravel = new Animation(bTravel4, duration1, true);
                bulletExplode = new Animation(bExplode4, duration2, true);
                bullet = bulletTravel;
                setHitBox(0, 0, 0, 0, false);
                break;

        }

    }
    /*Description: Sets coordinates of bullet on map
     * pre:n/a
     * post:n/a
     */

    public void setCoordinates(float x, float y) {
        this.x = x;
        this.y = y;

    }
    /*Description:Sets speed of X direction
     * pre: n/a
     * post:n/a
     */

    public void setVelocX(float s) {
        velocX = s;
    }
    /*Description:Sets speed ofY direction
     * pre: n/a
     * post:n/a
     */

    public void setVelocY(float s) {
        velocY = s;
    }
    /*Description: Checks to see if bullet is still within the 800 by 600 game container
     * pre: n/a
     * post:n/a
     */

    public boolean withinScreen() {
        if (x > 850 || x < -100 || y > 650 || y < -50) {
            return false;
        } else {
            return true;
        }
    }
    /*Description:Removes bullet and destroys it
     * pre: bullet has hit enemy or wall
     * post:bullet is destroyed
     */

    public void destroyAndRemove() {
        removedFromWorld();
        destroy();

    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        super.update(container, delta);

        x = x + velocX;
        y = y - velocY;
        mapX += velocX;
        mapY -= velocY;

        if (bulletType == 3 || bulletType == 4) {
            velocX = 0;
            velocY = 0;
            if (bullet == bulletTravel) {
                if (bullet.getFrame() == bulletTravel.getFrameCount() - 1) {
                    bullet = bulletExplode;
                }
            } else if (bullet == bulletExplode) {
                if (bullet.getFrame() == bulletExplode.getFrameCount() - 1) {
                    hit = true;
                }
            }
        }
        if (hit == true) {
            if (bulletType != 1) {
                bullet = bulletExplode;
                velocX = 0;
                velocY = 0;
            }
        }
        //Setting Hitboxes at diff intervals
        if (bullet == bulletExplode) {
            switch (bulletType) {
                case 1:

                    break;
                case 2:
                    if (explosion != true) {
                        x -= 60;
                        y -= 60;
                        mapX -= 60;
                        mapY -= 60;
                        explosion = true;
                    }
                    setHitBox(33, 44, 126, 124);
                    break;
                case 3:
                    if (facing == 0) {
                        setHitBox( -611, 46, 611, 85, true);
                    } else if (facing == 1) {
                        setHitBox(59, 46, 611, 85, true);
                    }

                    break;
                case 4:
                    if (facing == 1) {
                        setHitBox(0, 0, 800, 600, true);
                    } else if (facing == 0) {
                        setHitBox(0, 0, 800, 600, true);
                    }

                    break;
            }
        }
        if (withinScreen() != true) {
            destroyAndRemove();
        }
        if (pierceE != true) {

            if (collide(enemTypes, x, y) != null) {
                if (bulletType == 1 || bulletType == 2) {
                    velocY = 0;
                    velocX = 0;
                    hit = true;
                }

            }
        }
        if (hit) {
            time += delta;
            switch (bulletType) {
                case 1:
                    if (time > delta + 5) {
                        destroyAndRemove();
                        time = 0;
                    }
                    break;
                case 2:
                    if (time > 120 * (bulletExplode.getFrameCount() - 1)) {
                        destroyAndRemove();
                        time = 0;
                    }
                    break;
                case 3:
                    if (time > delta + 5) {
                        destroyAndRemove();
                        time = 0;
                    }
                    break;
                case 4:
                    if (time > delta + 5) {
                        destroyAndRemove();
                        time = 0;
                    }
                    break;
            }

        }

    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        super.render(container, g);
        if (bulletType == 4) {
            bullet.draw(-150, -100);
        }
        if (launched == false) {
            float targetAng = (float) getTargetAngle(x, y, container.getInput().getMouseX(), container.getInput().getMouseY());
            angle = targetAng;
            g.rotate(x + tx, y + ty, targetAng);
            switch (bulletType) {
                case 1:
                    object.draw(x, y);
                    break;
                case 2:
                    bullet.draw(x, y);
                    break;
            }
            g.rotate(x + tx, y + ty, -targetAng);
            launched = true;
        } else {
            if (bulletType == 1 || bulletType == 2) {
                g.rotate(x + tx, y + ty, angle);
                switch (bulletType) {
                    case 1:
                        object.draw(x, y);
                        break;
                    case 2:
                        bullet.draw(x, y);
                        break;
                }

                g.rotate(x + tx, y + ty, -angle);
            } else if (bulletType == 3) {
                if (facing == 0) {
                    bullet.draw(x - 540, y);
                } else {
                    bullet.draw(x, y);
                }
            }
        }
    }
/*Description:Finds the angle at which the bullet should rotate to match the position of the mouse
     * pre: bullet is fired
     * post:angle is returned
     */
    public double getTargetAngle(float startX, float startY, float targetX, float targetY) {

        double angle = 0;
        if (facing == 0) {
            angle = (Math.toDegrees(Math.atan2(startY - targetY, startX - targetX)));
        } else if (facing == 1) {
            angle = (Math.toDegrees(Math.atan2(startY - targetY, startX - targetX)) + 180);
        }
        if (angle < 0) {
            angle += 360;
        }
        return angle;
    }
}
