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
public class StoryLevelSelector extends World {

    public static Constants constants = new Constants();
    private ReadSaveSlotsXML readSave = new ReadSaveSlotsXML();
    private Image background = null;
    private Image toronto = null;
    private Image amazon = null;
    private Image japan = null;
    private Image hima = null;
    private float torontoScale = (float) 0.04;
    private float amazonScale = (float) 0.04;
    private float japanScale = (float) 0.04;
    private float himaScale = (float) 0.04;
    private static int menuX = 0;
    private static int menuY = 390;
    private Rectangle tor, ama, jap, him, backBox, continueBox;
    private int account, level, stage;
    private String l, s, m;//level stage money in String Format

	/*Constructor
     *
     *pre:
     *post: loads images and buttons for level selection
     */
    public StoryLevelSelector(int id, GameContainer container) {
        super(id, container);

        background = ResourceManager.getImage("storylevelselector");
        toronto = ResourceManager.getImage("dung1");
        amazon = ResourceManager.getImage("forest1");
        japan = ResourceManager.getImage("japan1");
        hima = ResourceManager.getImage("snow1");
        backBox = new Rectangle(41, 184, 89, 28);
        continueBox = new Rectangle(510, 153, 110, 38);
        tor = new Rectangle(179, 286, 36, 20);
        ama = new Rectangle(205, 409, 27, 15);
        jap = new Rectangle(648, 325, 49, 29);
        him = new Rectangle(541, 335, 59, 29);

    }

    public void setInfo(int level, int stage, int slot,int money) {
        this.level = level;
        this.stage = stage;
        this.account = slot;
        this.l = "" + level;
        this.s = "" + stage;
        this.m = "" + money;
    }
	/*Allows selection on menu
     *
     *pre: gets co-ordinates on player's mouse
     *post: When mouse is clicked at preset locations, loads specified map or navigates level select menu
     */
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        super.update(container, game, delta);
        Input input = container.getInput();

        int mouseX = input.getMouseX();
        int mouseY = input.getMouseY();

        boolean insideToronto = false;
        boolean insideAmazon = false;
        boolean insideJapan = false;
        boolean insideHima = false;

        float scaleStep = 0.0003f;
        if (level == 1) {//Amazon/
            insideAmazon = true;
        } else if (level == 2) {//Toronto
            insideToronto = true;
        } else if (level == 3) {//Himalaya
            insideHima = true;
        } else if (level == 4) {//Japan
            insideJapan = true;

        }
        if ((mouseX >= backBox.getX() && mouseX <= backBox.getX() + backBox.getWidth())
                && (mouseY >= backBox.getY() && mouseY <= backBox.getY() + backBox.getHeight())) {
            if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                game.enterState(constants.LOADGAMESTATE, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
        }
        if ((mouseX >= continueBox.getX() && mouseX <= continueBox.getX() + continueBox.getWidth())
                && (mouseY >= continueBox.getY() && mouseY <= continueBox.getY() + continueBox.getHeight())) {
            if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                game.addState(new LoadOutScreen(constants.LOADOUTSCREEN, container, account));
                game.enterState(constants.LOADOUTSCREEN, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
        }
        if ((mouseX >= 264 && mouseX <= 264 + 283) && (mouseY >= 196 && mouseY <= 198+ 47)) {//H.A.C.K Training(MiniGames)
            if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                {
                    if (game.getState(constants.MINIGAMESTATE) == null) {
                        game.addState(new MiniGameState(constants.MINIGAMESTATE, container, 1, account));
                        MiniGameState target = (MiniGameState) game.getState(constants.MINIGAMESTATE);
                        target.setSlot(account);
                        game.enterState(constants.MINIGAMESTATE, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
                    } else if (game.getState(constants.MINIGAMESTATE) != null) {
                        MiniGameState target = (MiniGameState) game.getState(constants.MINIGAMESTATE);
                        target.setSlot(account);
                        game.enterState(constants.MINIGAMESTATE, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
                    }

                }
            }
        }

        if (insideToronto) {
            if (torontoScale < 0.18f) {
                torontoScale += scaleStep * delta;
            }

            if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
            }
        } else {
            if (torontoScale > 0.04f) {
                torontoScale -= scaleStep * delta;
            }
        }

        if (insideAmazon) {
            if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
            }
            if (amazonScale < 0.18f) {
                amazonScale += scaleStep * delta;
            }
        } else {
            if (amazonScale > 0.04f) {
                amazonScale -= scaleStep * delta;
            }
        }

        if (insideJapan) {
            if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
            }
            if (japanScale < 0.18f) {
                japanScale += scaleStep * delta;
            }
        } else {
            if (japanScale > 0.04f) {
                japanScale -= scaleStep * delta;
            }
        }
        if (insideHima) {
            if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
            }
            if (himaScale < 0.18f) {
                himaScale += scaleStep * delta;
            }
        } else {
            if (himaScale > 0.04f) {
                himaScale -= scaleStep * delta;
            }
        }
    }
		/*Displays info, draws images
     *
     *pre:	When player is at level selector
     *post: Draws buttons, displays level information, and displays player money
     */
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        super.render(container, game, g);

        background.draw(0, 0);
        toronto.draw(tor.getX(), tor.getY(), torontoScale);
        amazon.draw(ama.getX(), ama.getY(), amazonScale);
        japan.draw(jap.getX(), jap.getY(), japanScale);
        hima.draw(him.getX(), him.getY(), himaScale);
        g.setColor(Color.black);
        g.draw(continueBox);

        g.drawString("Proceed", continueBox.getX() + 20, continueBox.getY() + 10);
        g.drawString("Current Level: " + l, 216, 110);
        g.drawString("Current Stage: " + s, 216, 130);
        g.drawString("Money: $" + m, 216, 150);
        g.setColor(Color.blue);
        if (level == 1) {
            g.drawString("Destination: Amazon Forest", 216, 90);
        } else if (level == 2) {
            g.drawString("Destination: Alberta Coal Mines", 216, 90);
        } else if (level == 3) {
            g.drawString("Destination: Himalayan Mountains", 216, 90);
        } else if (level == 4) {
            g.drawString("Destination: Tokyo City", 216, 90);
        }
    }
}
