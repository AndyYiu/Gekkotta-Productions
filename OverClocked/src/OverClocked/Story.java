/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OverClocked;

import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.World;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.xml.sax.SAXException;

/** Author: Stanley Fung
 * Date:
 * Teacher:
 * Description:
 * 
 */
public class Story extends World {

    public static Constants constants = new Constants();
    private Rectangle newGameBox, loadGameBox, backBox;
    private SaveSlotsXML saveSlots;
    private ReadSaveSlotsXML readSaveSlots;
    private HacksInfoXML hacksInfo;
    private boolean noSlots;
    private Image background;

    public Story(int id, GameContainer container) {
        super(id, container);
        newGameBox = new Rectangle(275, 175, 325, 100);
        loadGameBox = new Rectangle(275, 275, 325, 100);
        backBox = new Rectangle(10, 10, 100, 50);
        saveSlots = new SaveSlotsXML();
        readSaveSlots = new ReadSaveSlotsXML();
        hacksInfo = new HacksInfoXML();
        background = ResourceManager.getImage("storybackground");
    }



    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

        super.update(container, game, delta);
        Input input = container.getInput();
        int mouseX = input.getMouseX();
        int mouseY = input.getMouseY();

        try {
            if (saveSlots.firstTime() == true) {
                saveSlots.createSlots();
                if (hacksInfo.firstTime()) {
                    hacksInfo.createInfo();
                }
                game.addState(new NewGameState(constants.NEWGAMESTATE, container, true));
                game.enterState(constants.NEWGAMESTATE, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Story.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Story.class.getName()).log(Level.SEVERE, null, ex);
        }

        if ((mouseX >= newGameBox.getX() && mouseX <= newGameBox.getX() + newGameBox.getWidth())
                && (mouseY >= newGameBox.getY() && mouseY <= newGameBox.getY() + newGameBox.getHeight())) {
            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                if (readSaveSlots.getSlotsLeft() < 1) {
                    noSlots = true;
                } else if (readSaveSlots.getSlotsLeft() >= 1) {
                    game.addState(new NewGameState(constants.NEWGAMESTATE, container, false));
                    game.enterState(constants.NEWGAMESTATE, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
                }

            }
        } else if ((mouseX >= loadGameBox.getX() && mouseX <= loadGameBox.getX() + loadGameBox.getWidth())
                && (mouseY >= loadGameBox.getY() && mouseY <= loadGameBox.getY() + loadGameBox.getHeight())) {
            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                game.addState(new LoadGameState(constants.LOADGAMESTATE, container));
                game.enterState(constants.LOADGAMESTATE, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
        } else if ((mouseX >= backBox.getX() && mouseX <= backBox.getX() + backBox.getWidth())
                && (mouseY >= backBox.getY() && mouseY <= backBox.getY() + backBox.getHeight())) {

            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {

                game.enterState(constants.MAINMENUSTATE, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }

        }
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        super.render(container, game, g);
        background.draw(0, 0);
        g.setColor(Color.white);
        g.drawString("NEW GAME", newGameBox.getX() + 25, newGameBox.getY() + 25);
        g.draw(newGameBox);
        g.drawString("LOAD GAME", loadGameBox.getX() + 25, loadGameBox.getY() + 25);
        g.draw(loadGameBox);
        g.drawString("BACK", backBox.getX() + 10, backBox.getY() + 20);
        g.draw(backBox);
        if (noSlots) {
            g.drawString("NO MORE SLOTS LEFT!", 300, 400);
        }
    }
}
