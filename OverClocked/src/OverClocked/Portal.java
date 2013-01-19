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
public class Portal extends Entity {

    public static final String type = "PORTAL";
    private Animation portal;
		/*Constructor
     *
     *pre:
     *post: sets images and hitboxes for portals
     */
    public Portal(float x, float y) {
        super(x, y);

        addType(type);
        Image[] pics = {ResourceManager.getImage("portal1"), ResourceManager.getImage("portal2"), ResourceManager.getImage("portal3"), ResourceManager.getImage("portal4"), ResourceManager.getImage("portal5"), ResourceManager.getImage("portal6"), ResourceManager.getImage("portal7")};
        int[] duration = {120, 120, 120, 120, 120, 120, 120};
        portal = new Animation(pics, duration);
        this.y = y - portal.getHeight();
        setHitBox(0, 0, portal.getWidth(), portal.getHeight());
    }
		/*Determines portal distance on x axis
     *
     *pre: Distance
     *post:Changes location according to distance on x-axis
     */
    public void moveX(float distance) {
        x -= distance;
    }
		/*Determines portal distance on y axis
     *
     *pre: Distance
     *post: Changes location according to distance on y-axis
     */
    public void moveY(float distance) {
        y -= distance;
    }
		/*Draws the image
     *
     *pre:
     *post: draws portal
     */
    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        super.render(container, g);
        portal.draw(x, y);
    }
}
