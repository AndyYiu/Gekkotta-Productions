/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OverClocked;

import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.entity.Entity;
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
public class MapImage extends Entity {

    private Image background;
    private int level, stage;

    public MapImage(float x, float y, int level, int stage) throws SlickException {
        super(x, y);
        this.level = level;
        this.stage = stage;

        switch (level) {
            case 1:
                background = ResourceManager.getImage("forest1");

                break;
            case 2:
                background = ResourceManager.getImage("dung1");
                break;
            case 3:
                background = ResourceManager.getImage("snow1");
                break;
            case 4:background=ResourceManager.getImage("japan1");
                break;
        }
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        super.render(container, g);
        background.draw(0, 0);
    }
}
