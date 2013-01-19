/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OverClocked;

import it.randomtower.engine.ResourceManager;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 *
 * @author admin
 */
public class OverClocked extends StateBasedGame {

    public HacksInfoXML hacks;
    public static Constants constants = new Constants();

    /**
     * @param args the command line arguments
     */
    public OverClocked(String title) {
        super(title);


        hacks = new HacksInfoXML();
        hacks.createInfo();
    }

    public static void main(String[] args) throws SlickException {

        AppGameContainer app = new AppGameContainer(new OverClocked("OverClocked"));
        app.setDisplayMode(800, 600, false);
       // app.setFullscreen(true);
        app.setTargetFrameRate(45);
        app.setUpdateOnlyWhenVisible(true);
      
        app.setVSync(true);
        app.start();
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        try {
            ResourceManager.loadResources("data/Resources.xml");

        } catch (IOException ex) {
            Logger.getLogger(OverClocked.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.addState(new MenuState(constants.MAINMENUSTATE, gc));
        this.addState(new GameOver(constants.GAMEOVERSTATE, gc));
        this.enterState(constants.MAINMENUSTATE);

    }
}
