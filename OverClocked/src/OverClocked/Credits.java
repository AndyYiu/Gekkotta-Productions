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

/** Author: Stanley Fung
 * Date:
 * Teacher:
 * Description:
 * 
 */
public class Credits extends World{

    public static Constants constants = new Constants();
    Image background;
    
    /*constructor
     *
     *pre: get's the state id and the container used in the game
     *post: gets the background image from resource manager
     */
    public Credits(int id, GameContainer container) {
	super(id, container);
	background = ResourceManager.getImage("credits");
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
	super.update(container, game, delta);
	Input input = container.getInput();
	
	int mouseX = input.getMouseX();
	int mouseY = input.getMouseY();
	
	if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
	    if((mouseX >= 43 && mouseX <= 130) && (mouseY >= 188 && mouseY <= 215)){
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
