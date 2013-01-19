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
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 *
 * @author Andrew Yiu
 */
public class HacksPower extends World {

    private int account, money, cost;
    public static Constants constants = new Constants();
    private HacksInfo hacks;
    private ReadSaveSlotsXML readSave;
    private ModifySaveSlotXML modSave;
    private ReadHacksInfoXML readHacks;
    private Image background = null, buy, activate, Shoop, EnergyOrbs, PiercingShot, Storm;
    boolean shoop, energyOrbs, piercingShot, storm, a, b, poor;
    public String checker, power;

    /* constructor
     * 
     * pre: state id, container
     * post: get pictures
     */
    public HacksPower(int id, GameContainer container, int acc) {
	super(id, container);
	account = acc;
	background = ResourceManager.getImage("hMenuPow");
	buy = ResourceManager.getImage("buy");
	activate = ResourceManager.getImage("activate");
	Shoop = ResourceManager.getImage("Shoop");
	EnergyOrbs = ResourceManager.getImage("EnergyOrbs");
	Storm = ResourceManager.getImage("Storm");
	PiercingShot = ResourceManager.getImage("PiercingShot");
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
	super.enter(container, game);
	readSave = new ReadSaveSlotsXML();
	modSave = new ModifySaveSlotXML();
	readHacks = new ReadHacksInfoXML();
	hacks = new HacksInfo();
	checker = readSave.getBoughtHacks(account);
	power = readSave.getPowerHacks(account);
	money = readSave.getMoney(account);
	cost = 0;
    }

    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
	super.leave(container, game);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
	super.update(gc, sbg, delta);
	Input input = container.getInput();

	int mouseX = input.getMouseX();
	int mouseY = input.getMouseY();

	if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
	    if ((mouseX >= 230 && mouseX <= 230 + Shoop.getWidth())
		    && (mouseY >= 300 && mouseY <= 300 + Shoop.getHeight())) {
		shoop = true;
		energyOrbs = false;
		storm = false;
		piercingShot = false;
		poor = false;
	    }

	    if ((mouseX >= 353 && mouseX <= 353 + EnergyOrbs.getWidth())
		    && (mouseY >= 300 && mouseY <= 300 + EnergyOrbs.getHeight())) {
		shoop = false;
		energyOrbs = true;
		storm = false;
		piercingShot = false;
		poor = false;
	    }

	    if ((mouseX >= 476 && mouseX <= 476 + Storm.getWidth())
		    && (mouseY >= 300 && mouseY <= 300 + Storm.getHeight())) {
		shoop = false;
		energyOrbs = false;
		storm = false;
		piercingShot = true;
		poor = false;
	    }

	    if ((mouseX >= 600 && mouseX <= 600 + Storm.getWidth())
		    && (mouseY >= 300 && mouseY <= 300 + Storm.getHeight())) {
		shoop = false;
		energyOrbs = false;
		storm = true;
		piercingShot = false;
		poor = false;
	    }

	    if ((mouseX >= 641 && mouseX <= 761)
		    && (mouseY >= 521 && mouseY <= 556)) {
		if (a) {
		    if (shoop) {
			modSave.savePower(account, "Shoop");
		    } else if (energyOrbs) {
			modSave.savePower(account, "EnergyOrbs");
		    } else if (piercingShot) {
			modSave.savePower(account, "PiercingShot");
		    } else if (storm) {
			modSave.savePower(account, "Storm");
		    }
		    sbg.enterState(constants.LOADOUTSCREEN, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
		} else if (b) {
		    if (shoop) {
			if (money >= readHacks.getCost(3)) {
			    modSave.saveHacks(account, "Shoop");
			    cost = readHacks.getCost(3);
			    poor = false;
			} else {
			    poor = true;
			}
		    } else if (energyOrbs) {
			if (money >= readHacks.getCost(4)) {
			    modSave.saveHacks(account, "EnergyOrbs");
			    cost = readHacks.getCost(4);
			    poor = false;
			} else {
			    poor = true;
			}
		    } else if (piercingShot) {
			if (money >= readHacks.getCost(5)) {
			    modSave.saveHacks(account, "PiercingShot");
			    cost = readHacks.getCost(5);
			    poor = false;
			} else {
			    poor = true;
			}
		    } else if (storm) {
			if (money >= readHacks.getCost(6)) {
			    modSave.saveHacks(account, "Storm");
			    cost = readHacks.getCost(6);
			    poor = false;
			} else {
			    poor = true;
			}
		    }
		    if (poor == false) {
			modSave.save(account, readSave.getName(account), (money - cost), readSave.getCurrentLevel(account), readSave.getCurrentStage(account), false);
			sbg.enterState(constants.HACKSPOWER, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
		    }
		}
	    }

	    if ((mouseX >= 44 && mouseX <= 130) && (mouseY >= 289 && mouseY <= 315)) {
		sbg.enterState(constants.LOADOUTSCREEN, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
	    }
	}
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
	super.render(container, game, g);
	background.draw(0, 0);
	Shoop.draw(230, 300);
	EnergyOrbs.draw(353, 300);
	Storm.draw(600, 300);
	PiercingShot.draw(476, 300);
	
	if (poor) {
	    shoop = false;
	    energyOrbs = false;
	    storm = false;
	    g.setColor(Color.blue);
	    g.drawString("You don't have enough money!", 240, 162);
	} else if (shoop) {
	    g.setColor(Color.blue);
	    g.drawString(hacks.getPower(0), 240, 162);
	    if (power.contains("Shoop")) {
		a = false;
		b = false;
	    } else if (checker.contains("Shoop")) {
		activate.draw(641, 521);
		a = true;
		b = false;
	    } else {
		buy.draw(641, 521);
		a = false;
		b = true;
	    }
	} else if (energyOrbs) {
	    g.setColor(Color.blue);
	    g.drawString(hacks.getPower(1), 240, 162);
	    if (power.contains("EnergyOrbs")) {
		a = false;
		b = false;
	    } else if (checker.contains("EnergyOrbs")) {
		activate.draw(641, 521);
		a = true;
		b = false;
	    } else {
		buy.draw(641, 521);
		a = false;
		b = true;
	    }
	} else if (piercingShot) {
	    g.setColor(Color.blue);
	    g.drawString(hacks.getPower(2), 240, 162);
	    if (power.contains("PiercingShot")) {
		a = false;
		b = false;
	    } else if (checker.contains("PiercingShot")) {
		activate.draw(641, 521);
		a = true;
		b = false;
	    } else {
		buy.draw(641, 521);
		a = false;
		b = true;
	    }
	} else if (storm) {
	    g.setColor(Color.blue);
	    g.drawString(hacks.getPower(3), 240, 162);
	    if (power.contains("Storm")) {
		a = false;
		b = false;
	    } else if (checker.contains("Storm")) {
		activate.draw(641, 521);
		a = true;
		b = false;
	    } else {
		buy.draw(641, 521);
		a = false;
		b = true;
	    }
	} else {
	    a = false;
	    b = false;
	}
    }
}
