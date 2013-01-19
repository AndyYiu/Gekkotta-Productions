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
import org.newdawn.slick.Sound;

/** Author: Stanley Fung
 * Date:
 * Teacher:
 * Description:
 * 
 */
public class MainGamePlayerArms extends Entity {

    private Animation shootRight, shootLeft, sprite;
    private int frames, facing, time = 0;//0 = left, 1 = right
    public float angle, mapX, mapY;
    private double speed = 20, jumpHeight = 0, maxHeight = 200;
    public static final double gravity = 8;
    private int jumping; // 1= rising, 2 = falling,0 = not jumping
    public int bulletType;//Default 1, Splash 2, Shoop Da Whoop 3, Nuke 4
    public int account;
    public boolean justShot, piercingShot, shoop, storm, energyOrbs, pHack;
    public String power;
    private Image sdwFace, pH;
    private Sound pistol1, pistol2, shoopDaWhoop, splash, thunderStorm;
    private Random rand = new Random();
    private ReadSaveSlotsXML readSave = new ReadSaveSlotsXML();

    public MainGamePlayerArms(float x, float y, int direc, float mapx, float mapy, int acc) {
        super(x, y);
        account = acc;
        mapX = mapx;
        mapY = mapy;
        sdwFace = ResourceManager.getImage("mSDW");
        pistol1 = ResourceManager.getSound("pistol1");
        pistol2 = ResourceManager.getSound("pistol2");

        shoopDaWhoop = ResourceManager.getSound("shoopDaWhoop");
        splash = ResourceManager.getSound("splash");
        thunderStorm = ResourceManager.getSound("ThunderStorm");
        Image[] shootingRight = {ResourceManager.getImage("mShooting1"), ResourceManager.getImage("mShooting1f2"), ResourceManager.getImage("mShooting2"), ResourceManager.getImage("mShooting3"), ResourceManager.getImage("mShooting4"), ResourceManager.getImage("mShooting5")};
        Image[] shootingLeft = {ResourceManager.getImage("mShooting1").getFlippedCopy(true, false), ResourceManager.getImage("mShooting1f2").getFlippedCopy(true, false), ResourceManager.getImage("mShooting2").getFlippedCopy(true, false), ResourceManager.getImage("mShooting3").getFlippedCopy(true, false), ResourceManager.getImage("mShooting4").getFlippedCopy(true, false), ResourceManager.getImage("mShooting5")};
        int[] duration = {50, 75, 75, 50, 50, 50};
        shootRight = new Animation(shootingRight, duration, false);
        shootLeft = new Animation(shootingLeft, duration, false);
        frames = duration.length;
        jumping = 0;
        facing = direc;
        sprite = shootRight;
        bulletType = 1;
        justShot = false;
        power = readSave.getPowerHacks(account);
    }

    public MainGameBullet generateBullet(float angle) {
        MainGameBullet bullet = null;
        angle = (float) Math.toRadians(angle);
        if (facing == 0) {//LEft
            bullet = new MainGameBullet(x + 25, y + 13, mapX + 25, mapY + 13, facing, piercingShot, bulletType);
            bullet.setVelocX((float) (-50 * Math.sin(angle)));
            bullet.setVelocY((float) (-50 * Math.cos(angle)));
        } else if (facing == 1) {//right
            bullet = new MainGameBullet(x + 34, y + 13, mapX + 34, mapY + 13, facing, piercingShot, bulletType);
            bullet.setVelocX((float) (50 * Math.sin(angle)));
            bullet.setVelocY((float) (50 * Math.cos(angle)));
        }
        justShot = true;
        switch (bulletType) {
            case 1:
                if (rand.nextBoolean()) {
                    pistol1.play();
                } else {
                    pistol2.play();
                }
                break;
            case 2:
                splash.play();
                break;
            case 3:
                shoopDaWhoop.play();
                break;
            case 4:
                thunderStorm.play();
                break;

        }
        return bullet;
    }

    public void shoot() {
        sprite.setAutoUpdate(true);
    }

    public void setDirec(int d) {
        facing = d;
    }

    public boolean getjustShot() {
        return justShot;
    }

    public void setMapCoor(float mx, float my) {
        mapX = mx;
        mapY = my;
    }

    public void setDefaultAnimation(boolean v, int f) {
        sprite.setAutoUpdate(v);
        sprite.setCurrentFrame(f);
    }

    public void setCoordinates(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public int getFrame() {
        return sprite.getFrame();
    }

    public int getFrameLength() {
        return frames;
    }

    public float getCurrentAngle() {
        return angle;
    }

    public void changeDirection() {
        if (facing == 0) {
            sprite = shootLeft;
        } else if (facing == 1) {
            sprite = shootRight;
        }
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        super.update(container, delta);
        Input input = container.getInput();
        if (getFrame() == getFrameLength() - 1) {
            setDefaultAnimation(false, 0);
        }
        if (justShot) {
            time += delta;
            switch (bulletType) {
                case 1:
                    if (time >= 100) {
                        justShot = false;
                        time = 0;
                    }
                    break;
                case 2:
                    if (time >= 200) {
                        justShot = false;
                        time = 0;
                    }
                    break;
                case 3:
                    if (time >= 1500) {
                        justShot = false;
                        time = 0;
                    }
                    break;
                case 4:
                    if (time >= 30000) {
                        justShot = false;
                        time = 0;
                    }
                    break;
            }
        }
        //HACKS/////////////////////////////////////////
        if (input.isKeyPressed(Input.KEY_Q)) {//Power
            if (readSave.getPowerHacks(account).equals("Shoop")) {
                if (shoop == false) {
                    shoop = true;
                    pHack = true;
                    bulletType = 3;
                } else if (shoop == true) {
                    shoop = false;
                    pHack = false;
                    bulletType = 1;
                }
            } else if (readSave.getPowerHacks(account).equals("EnergyOrbs")) {
                if (energyOrbs == false) {
                    energyOrbs = true;
                    pHack = true;
                    bulletType = 2;
                } else if (energyOrbs == true) {
                    energyOrbs = false;
                    pHack = false;
                    bulletType = 1;
                }
            } else if (readSave.getPowerHacks(account).equals("PiercingShot")) {
                if (piercingShot == false) {
                    piercingShot = true;
                    pHack = true;
                } else if (piercingShot == true) {
                    piercingShot = false;
                    pHack = false;
                }
            } else if (readSave.getPowerHacks(account).equals("Storm")) {
                if (storm == false) {
                    storm = true;
                    pHack = true;
                    bulletType = 4;
                } else if (storm == true) {
                    storm = false;
                    pHack = false;
                    bulletType = 1;
                }
            }
        }
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        super.render(container, g);
        float targetAng = 0;
        int tx = 0;//tx and ty set the point of rotation
        int ty = 0;
        if (facing == 1) {
            targetAng = (float) getTargetAngle(x + 6, y + 16, container.getInput().getMouseX(), container.getInput().getMouseY());
            tx = (6);//tx and ty set the point of rotation
            ty = (16);
        } else if (facing == 0) {
            targetAng = (float) getTargetAngle(x + 59, y + 16, container.getInput().getMouseX(), container.getInput().getMouseY());
            tx = (59);//tx and ty set the point of rotation
            ty = (16);
        }
        angle = targetAng;
        g.rotate(x + tx, y + ty, targetAng);
        g.drawAnimation(sprite, x, y);
        g.rotate(x + tx, y + ty, -targetAng);
        if (bulletType == 3) {
            if(facing==1){
                 sdwFace.draw(x, y - 10);
            }else if(facing==0){
                sdwFace.getFlippedCopy(true, false).draw(x+35, y - 10);
            }
           
        }
        if (!power.equals("z")) {
            getPowerPic().draw(83, 23);
        }
    }

    public Image getPowerPic() throws SlickException {
        if (pHack) {
            pH = ResourceManager.getImage(power);
        } else {
            pH = ResourceManager.getImage(power + "BW");
        }
        pH = pH.getScaledCopy(42, 40);
        return pH;
    }

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
