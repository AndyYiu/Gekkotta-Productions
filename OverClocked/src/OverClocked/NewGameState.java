/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OverClocked;

import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.World;


import java.awt.Font;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/** Author: Stanley Fung
 * Date:
 * Teacher:
 * Description:
 *
 */
public class NewGameState extends World {

    public static Constants constants = new Constants();
    private TextField textTest;
    private Rectangle backBox;
    Font font;
    TrueTypeFont trueTypeFont;
    private ModifySaveSlotXML modSaveSlots;
    private ReadSaveSlotsXML readSaveSlots;
    private Image background;
    private int time;
    private boolean firstTime;
		/*Constructor
     *
     *pre:
     *post: Sets background image from resource manager, sets display for save slots
     */
    public NewGameState(int id, GameContainer container, boolean ftime) {
        super(id, container);
        firstTime = ftime;
        font = new Font("Verdana", Font.BOLD, 15);
        trueTypeFont = new TrueTypeFont(font, true);
        backBox = new Rectangle(10, 10, 100, 50);
        modSaveSlots = new ModifySaveSlotXML();
        readSaveSlots = new ReadSaveSlotsXML();
        textTest = new TextField(container, trueTypeFont, 400, 300, 200, 20);
        background = ResourceManager.getImage("storybackground");
    }
  		/*Slot selection
     *
     *pre:Locates mouse location
     *post: Loads slot when mouse is clicked on button
     */
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        super.update(container, game, delta);
        Input input = container.getInput();
        int mouseX = input.getMouseX();
        int mouseY = input.getMouseY();

        if (container.getInput().isKeyPressed(Input.KEY_ENTER)) {
            modSaveSlots.save(readSaveSlots.getNextSlot(), textTest.getText(), 0, 1, 1, true);
            game.addState(new LoadGameState(constants.LOADGAMESTATE, container));
            game.enterState(constants.LOADGAMESTATE);

        } else if ((mouseX >= backBox.getX() && mouseX <= backBox.getX() + backBox.getWidth())
                && (mouseY >= backBox.getY() && mouseY <= backBox.getY() + backBox.getHeight())) {
            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {

                game.enterState(constants.STORYSTATE,new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
        }


    }
		/*New save slot creation
     *
     *pre:
     *post: Displays slots left,Prompts player when creating new save slot
     */
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        super.render(container, game, g);

        background.draw(0, 0);
        textTest.render(container, g);
        textTest.setFocus(true);
        g.setColor(Color.white);
        g.drawString("Slots Left: " + readSaveSlots.getSlotsLeft(), 100, 200);
        g.drawString("ENTER NAME AND CLICK ENTER", 150, 300);
        g.drawString("BACK", backBox.getX() + 10, backBox.getY() + 20);
        g.draw(backBox);
        if (firstTime) {
            g.drawString(" The java.io.FileNotFoundException means that this is your first time playing! ", 50, 100);
            g.drawString(" A general save log has been created for you. Please create an account first! ", 50, 125);
        }
    }
}
