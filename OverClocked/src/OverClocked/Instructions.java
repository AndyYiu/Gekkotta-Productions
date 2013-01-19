/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OverClocked;

import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.World;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 *
 * @author Andrew Yiu
 */
public class Instructions extends World{

    public static Constants constants = new Constants();
    Image background;
    
    /* Constructor
     * 
     * pre: state id, container
     * post: gets pic
     */
    public Instructions(int id, GameContainer container) {
        super(id, container);
    background = ResourceManager.getImage("instructions");
    }
    
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
	super.update(container, game, delta);
	Input input = container.getInput();
	
	int mouseX = input.getMouseX();
	int mouseY = input.getMouseY();
	
	if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
	    if((mouseX >= 11 && mouseX <= 98) && (mouseY >= 11 && mouseY <= 38)){
		game.enterState(constants.MAINMENUSTATE, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
	    }
	}
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
	super.render(container, game, g);
	background.draw(0,0);
    }
    
}
