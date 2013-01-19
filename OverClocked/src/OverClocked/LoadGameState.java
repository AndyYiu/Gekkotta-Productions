/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OverClocked;

import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.World;
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

/** Author: Stanley Fung
 * Date:
 * Teacher:
 * Description:
 * 
 */
public class LoadGameState extends World {

    private ModifySaveSlotXML modSave = new ModifySaveSlotXML();
    private ReadSaveSlotsXML readSave = new ReadSaveSlotsXML();
    public static Constants constants = new Constants();
    private ReadSaveSlotsXML readSaves;
    private Rectangle backBox;
    private int incX, choice, time;
    private boolean invalid;
    private Image background;

    /* constructor
     * 
     * pre: state id, and container
     * post: reads saves and gets background
     */
    public LoadGameState(int id, GameContainer container) {
        super(id, container);
        backBox = new Rectangle(10, 10, 100, 50);
        readSaves = new ReadSaveSlotsXML();
        incX = 0;
        background = ResourceManager.getImage("storybackground");
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        super.update(container, game, delta);
        Input input = container.getInput();
        int mouseX = input.getMouseX();
        int mouseY = input.getMouseY();

        for (int i = 0; i < 3; i++) {
            if ((mouseX >= incX + 100 && mouseX <= incX + 100 + 200)
                    && (mouseY >= 100 && mouseY <= 100 + 150)) {
                if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                    if (readSaves.getCurrentLevel(i) == 0) {
                        invalid = true;
                    } else if (readSaves.getCurrentLevel(i) != 0) {
                        invalid = false;
                        game.addState(new StoryLevelSelector(constants.STORYLEVELSELECTOR, container));
                        StoryLevelSelector target = (StoryLevelSelector) game.getState(constants.STORYLEVELSELECTOR);
                        target.setInfo(readSave.getCurrentLevel(i), readSave.getCurrentStage(i), i,readSave.getMoney(i));
                        game.enterState(constants.STORYLEVELSELECTOR, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
                    }
                }
            }
            incX += 200;
            if (i == 2) {
                incX = 0;
            }
        }

        if ((mouseX >= backBox.getX() && mouseX <= backBox.getX() + backBox.getWidth())
                && (mouseY >= backBox.getY() && mouseY <= backBox.getY() + backBox.getHeight())) {
            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                game.enterState(constants.STORYSTATE, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
        }


    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        super.render(container, game, g);
        g.setColor(Color.white);
        background.draw(0, 0);
        g.drawString("BACK", backBox.getX() + 10, backBox.getY() + 20);
        g.drawString("Please click on a box to load an account", 100, 70);
        g.draw(backBox);
        for (int i = 0; i < 3; i++) {
            g.drawRect(incX + 100, 100, 200, 150);
            g.drawString("SLOT" + ":" + (i + 1), incX + 100, 100);
            g.drawString("Name: " + readSaves.getName(i), incX + 100, 150);
            g.drawString("Money: $" + readSaves.getMoney(i), incX + 100, 200);
            g.drawString("CurrentLevel: " + readSaves.getCurrentLevel(i), incX + 100, 250);
            g.drawString("CurrentStage: " + readSaves.getCurrentStage(i), incX + 100, 280);
            incX += 200;
            if (i == 2) {
                incX = 0;
            }
        }
        if (invalid == true) {
            g.drawString("There is no data in that account!", 300, 325);
        }
    }
}
