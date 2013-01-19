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
public class HacksUtility extends World {

    private int account, money, cost;
    public static Constants constants = new Constants();
    private HacksInfo hacks;
    private ReadSaveSlotsXML readSave;
    private ModifySaveSlotXML modSave;
    private ReadHacksInfoXML readHacks;
    private Image background = null, buy, activate, TimeFreeze, Fly, Teleport, NoClip;
    boolean timeFreeze, fly, teleport, noClip, a, b, poor;
    public String checker, utility;

    /* constructor
     * 
     * pre: state id, container
     * post: get pictures
     */
    public HacksUtility(int id, GameContainer container, int acc) {
	super(id, container);
	account = acc;
	TimeFreeze = ResourceManager.getImage("Freeze");
	Fly = ResourceManager.getImage("Fly");
	Teleport = ResourceManager.getImage("Teleport");
	NoClip = ResourceManager.getImage("NoClip");
	background = ResourceManager.getImage("hMenuUtil");
	buy = ResourceManager.getImage("buy");
	activate = ResourceManager.getImage("activate");
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
	super.enter(container, game);
	readSave = new ReadSaveSlotsXML();
	modSave = new ModifySaveSlotXML();
	readHacks = new ReadHacksInfoXML();
	hacks = new HacksInfo();
	checker = readSave.getBoughtHacks(account);
	utility = readSave.getUtilityHacks(account);
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

	    if ((mouseX >= 230 && mouseX <= 230 + TimeFreeze.getWidth())
		    && (mouseY >= 300 && mouseY <= 300 + TimeFreeze.getHeight())) {
		timeFreeze = true;
		fly = false;
		noClip = false;
		teleport = false;
		poor = false;
	    }

	    if ((mouseX >= 353 && mouseX <= 353 + Fly.getWidth())
		    && (mouseY >= 300 && mouseY <= 300 + Fly.getHeight())) {
		timeFreeze = false;
		fly = true;
		noClip = false;
		teleport = false;
		poor = false;
	    }

	    if ((mouseX >= 476 && mouseX <= 476 + NoClip.getWidth())
		    && (mouseY >= 300 && mouseY <= 300 + NoClip.getHeight())) {
		timeFreeze = false;
		fly = false;
		noClip = true;
		teleport = false;
		poor = false;
	    }

	    if ((mouseX >= 600 && mouseX <= 600 + Teleport.getWidth())
		    && (mouseY >= 300 && mouseY <= 300 + Teleport.getHeight())) {
		timeFreeze = false;
		fly = false;
		noClip = false;
		teleport = true;
		poor = false;
	    }

	    if ((mouseX >= 47 && mouseX <= 135) && (mouseY >= 247 && mouseY <= 274)) {
		sbg.enterState(constants.LOADOUTSCREEN, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
	    }

	    if ((mouseX >= 641 && mouseX <= 761)
		    && (mouseY >= 521 && mouseY <= 556)) {
		if (a) {
		    if (timeFreeze) {
			modSave.saveUtility(account, "Freeze");
		    } else if (fly) {
			modSave.saveUtility(account, "Fly");
		    } else if (noClip) {
			modSave.saveUtility(account, "NoClip");
		    } else if (teleport) {
			modSave.saveUtility(account, "Teleport");
		    }
		    sbg.enterState(constants.LOADOUTSCREEN, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
		} else if (b) {
		    if (timeFreeze) {
			if (money >= readHacks.getCost(7)) {
			    modSave.saveHacks(account, "Freeze");
			    cost = readHacks.getCost(7);
			    poor = false;
			} else {
			    poor = true;
			}
		    } else if (fly) {
			if (money >= readHacks.getCost(8)) {
			    modSave.saveHacks(account, "Fly");
			    cost = readHacks.getCost(8);
			    poor = false;
			} else {
			    poor = true;
			}
		    } else if (noClip) {
			if (money >= readHacks.getCost(9)) {
			    modSave.saveHacks(account, "NoClip");
			    cost = readHacks.getCost(9);
			    poor = false;
			} else {
			    poor = true;
			}
		    } else if (teleport) {
			if (money >= readHacks.getCost(10)) {
			    modSave.saveHacks(account, "Teleport");
			    cost = readHacks.getCost(10);
			    poor = false;
			} else {
			    poor = true;
			}
		    }
		    if (poor == false) {
			modSave.save(account, readSave.getName(account), (money - cost), readSave.getCurrentLevel(account), readSave.getCurrentStage(account), false);
			sbg.enterState(constants.HACKSUTILITY, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
		    }
		}
	    }
	}
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
	super.render(container, game, g);
	background.draw(0, 0);
	TimeFreeze.draw(230, 300);
	Fly.draw(353, 300);
	NoClip.draw(476, 300);
	Teleport.draw(600, 300);
	
	if (poor) {
	    timeFreeze = false;
	    fly = false;
	    noClip = false;
	    teleport = false;
	    g.setColor(Color.blue);
	    g.drawString("You don't have enough money!", 240, 162);
	} else if (timeFreeze) {
	    g.setColor(Color.blue);
	    g.drawString(hacks.getUtility(0), 240, 162);
	    if (utility.contains("Freeze")) {
		a = false;
		b = false;
	    } else if (checker.contains("Freeze")) {
		activate.draw(641, 521);
		a = true;
		b = false;
	    } else {
		buy.draw(641, 521);
		a = false;
		b = true;
	    }
	} else if (fly) {
	    g.setColor(Color.blue);
	    g.drawString(hacks.getUtility(1), 240, 162);
	    if (utility.contains("Fly")) {
		a = false;
		b = false;
	    } else if (checker.contains("Fly")) {
		activate.draw(641, 521);
		a = true;
		b = false;
	    } else {
		buy.draw(641, 521);
		a = false;
		b = true;
	    }
	} else if (noClip) {
	    g.setColor(Color.blue);
	    g.drawString(hacks.getUtility(2), 240, 162);
	    if (utility.contains("NoClip")) {
		a = false;
		b = false;
	    } else if (checker.contains("NoClip")) {
		activate.draw(641, 521);
		a = true;
		b = false;
	    } else {
		buy.draw(641, 521);
		a = false;
		b = true;
	    }
	} else if (teleport) {
	    g.setColor(Color.blue);
	    g.drawString(hacks.getUtility(3), 240, 162);
	    if (utility.contains("Teleport")) {
		a = false;
		b = false;
	    } else if (checker.contains("Teleport")) {
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
