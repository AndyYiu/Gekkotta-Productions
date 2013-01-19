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
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 *
 * @author fungs4024
 */
public class GameOver extends World {

    public static Constants constants = new Constants();
    private Image gameOver;
    private Rectangle tryAgain, menu, storyLevel;
    private Music bgm;

    /* constructor
     * 
     * pre: state id and container
     * post: initializes pictures
     */
    public GameOver(int id, GameContainer container) throws SlickException {
        super(id, container);
        gameOver = ResourceManager.getImage("gameOver");
        tryAgain = new Rectangle(28, 508, 184, 56);
        storyLevel = new Rectangle(302, 508, 184, 56);
        menu = new Rectangle(582, 508, 184, 56);

    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        super.enter(container, game);
        bgm = ResourceManager.getMusic("gameOver");
        bgm.play();
    }

    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
        super.leave(container, game);
        bgm.stop();
        bgm = null;
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        super.update(container, game, delta);
        Input input = container.getInput();

        int mouseX = input.getMouseX();
        int mouseY = input.getMouseY();
        //try Again
        if ((mouseX >= tryAgain.getX() && mouseX <= tryAgain.getX() + tryAgain.getWidth())
                && (mouseY >= tryAgain.getY() && mouseY <= tryAgain.getY() + tryAgain.getHeight())) {
            if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                game.enterState(constants.MAINGAMESTATE, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
        }
        //Story Level Selector
        if ((mouseX >= storyLevel.getX() && mouseX <= storyLevel.getX() + storyLevel.getWidth())
                && (mouseY >= storyLevel.getY() && mouseY <= storyLevel.getY() + storyLevel.getHeight())) {
            if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                game.enterState(constants.STORYLEVELSELECTOR, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
        }
        //Menu
        if ((mouseX >= menu.getX() && mouseX <= menu.getX() + menu.getWidth())
                && (mouseY >= menu.getY() && mouseY <= menu.getY() + menu.getHeight())) {
            if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {

                game.enterState(constants.MAINMENUSTATE, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
        }
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        super.render(container, game, g);
        g.setColor(Color.green);
        gameOver.draw(0, 0);
        g.drawRect(28, 508, 184, 56);//Try again box
        g.drawString("Try Again?", tryAgain.getX() + 10, tryAgain.getY() + 10);
        g.drawRect(302, 508, 184, 56);//Back to story level selector
        g.drawString("Level Selector", storyLevel.getX() + 10, storyLevel.getY() + 10);
        g.drawRect(582, 508, 184, 56);//Back to menubox
        g.drawString("Main Menu", menu.getX() + 10, menu.getY() + 10);
    }
}
