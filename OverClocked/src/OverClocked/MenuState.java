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
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 *
 * @author admin
 */
public class MenuState extends World {

    public static Constants constants = new Constants();
    private Image background = null;
    private Image story = null;
    private Image freePlay = null;
    private Image credits = null;
    private float storyScale = 1;
    private float freePlayScale = 1;
    private float creditsScale = 1;
    private static int menuX = 0;
    private static int menuY = 390;
    private Music bgm;

    public MenuState(int id, GameContainer container) {
        super(id, container);
        background = ResourceManager.getImage("menuBackground");
        background = background.getScaledCopy(800, 600);
        Image menuOptions = ResourceManager.getImage("menuText");
        story = menuOptions.getSubImage(0, 0, 209, 220);
        freePlay = menuOptions.getSubImage(209, 0, 524 - 209, 220);
        credits = menuOptions.getSubImage(524, 0, 800 - 524, 220);
        bgm = ResourceManager.getMusic("menuTheme");
        bgm.loop();
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        super.enter(container, game);
        background = ResourceManager.getImage("menuBackground");
        background = background.getScaledCopy(800, 600);
        Image menuOptions = ResourceManager.getImage("menuText");
        story = menuOptions.getSubImage(0, 0, 209, 220);
        freePlay = menuOptions.getSubImage(209, 0, 524 - 209, 220);
        credits = menuOptions.getSubImage(524, 0, 800 - 524, 220);
    }

    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
        super.leave(container, game);

    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, int delta) throws SlickException {
        Input input = gc.getInput();

        int mouseX = input.getMouseX();
        int mouseY = input.getMouseY();

        boolean insideStoryGame = false;
        boolean insideFreePlay = false;
        boolean insideCredits = false;

        float scaleStep = 0.0001f;
        if ((mouseX >= menuX && mouseX <= menuX + story.getWidth())
                && (mouseY >= menuY && mouseY <= menuY + story.getHeight())) {
            insideStoryGame = true;
        } else if ((mouseX >= menuX && mouseX <= menuX + story.getWidth() + freePlay.getWidth())
                && (mouseY >= menuY && mouseY <= menuY + freePlay.getHeight())) {
            insideFreePlay = true;
        } else if ((mouseX >= menuX && mouseX <= menuX + credits.getWidth() + story.getWidth() + freePlay.getWidth())
                && (mouseY >= menuY && mouseY <= menuY + credits.getHeight())) {
            insideCredits = true;
        }

        if (insideStoryGame) {
            if (storyScale < 1.05f) {
                storyScale += scaleStep * delta;
            }

            if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                sb.addState(new Story(constants.STORYSTATE, gc));
                sb.enterState(constants.STORYSTATE, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
        } else {
            if (storyScale > 1.0f) {
                storyScale -= scaleStep * delta;

            }
        }

        if (insideFreePlay) {
            if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                sb.addState(new Instructions(constants.INSTRUCTIONSTATE, gc));
                sb.enterState(constants.INSTRUCTIONSTATE, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
            if (freePlayScale < 1.05f) {
                freePlayScale += scaleStep * delta;
            }
        } else {
            if (freePlayScale > 1.0f) {
                freePlayScale -= scaleStep * delta;
            }
        }

        if (insideCredits) {
            if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                sb.addState(new Credits(constants.CREDITSTATE, gc));
                sb.enterState(constants.CREDITSTATE, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
            if (creditsScale < 1.05f) {
                creditsScale += scaleStep * delta;
            }
        } else {
            if (creditsScale > 1.0f) {
                creditsScale -= scaleStep * delta;
            }
        }


    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.setColor(Color.orange);
        background.draw(0, 0);
        story.draw(menuX, menuY, storyScale);
        freePlay.draw(menuX + story.getWidth(), menuY, freePlayScale);
        credits.draw(menuX + story.getWidth() + freePlay.getWidth(), menuY, creditsScale);
    }
}
