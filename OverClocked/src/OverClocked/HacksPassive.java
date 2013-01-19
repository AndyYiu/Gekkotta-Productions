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
public class HacksPassive extends World {

    private int account, money, cost;
    public static Constants constants = new Constants();
    private HacksInfo hacks;
    private ReadSaveSlotsXML readSave;
    private ModifySaveSlotXML modSave;
    private ReadHacksInfoXML readHacks;
    private Image background = null, buy, HpIncrease, Haste, SharpEyes, Stance, PowerGuard, InfiniteAmmo;
    boolean hpIncrease, haste, sharpEyes, stance, powerGuard, infiniteAmmo, a, b, poor;
    public String checker;

    /* constructor
     * 
     * pre: state id, container
     * post: get pictures
     */
    public HacksPassive(int id, GameContainer container, int acc) {
        super(id, container);
        account = acc;
        background = ResourceManager.getImage("hMenuPass");
        buy = ResourceManager.getImage("buy");
        HpIncrease = ResourceManager.getImage("HpIncrease");
        Haste = ResourceManager.getImage("Haste");
        SharpEyes = ResourceManager.getImage("SharpEyes");
        Stance = ResourceManager.getImage("Stance");
        PowerGuard = ResourceManager.getImage("PowerGuard");
        InfiniteAmmo = ResourceManager.getImage("InfiniteAmmo");
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        super.enter(container, game);
        readSave = new ReadSaveSlotsXML();
        modSave = new ModifySaveSlotXML();
        readHacks = new ReadHacksInfoXML();
        hacks = new HacksInfo();
        checker = readSave.getBoughtHacks(account);
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
            if ((mouseX >= 266 && mouseX <= 266 + HpIncrease.getWidth())
                    && (mouseY >= 300 && mouseY <= 300 + HpIncrease.getHeight())) {
                hpIncrease = true;
                haste = false;
                sharpEyes = false;
                stance = false;
                powerGuard = false;
                infiniteAmmo = false;
                poor = false;
            }

            if ((mouseX >= 416 && mouseX <= 416 + Haste.getWidth())
                    && (mouseY >= 300 && mouseY <= 300 + Haste.getHeight())) {
                hpIncrease = false;
                haste = true;
                sharpEyes = false;
                stance = false;
                powerGuard = false;
                infiniteAmmo = false;
                poor = false;
            }

            if ((mouseX >= 566 && mouseX <= 566 + SharpEyes.getWidth())
                    && (mouseY >= 300 && mouseY <= 300 + SharpEyes.getHeight())) {
                hpIncrease = false;
                haste = false;
                sharpEyes = true;
                stance = false;
                powerGuard = false;
                infiniteAmmo = false;
                poor = false;
            }

            if ((mouseX >= 266 && mouseX <= 266 + Stance.getWidth())
                    && (mouseY >= 400 && mouseY <= 400 + Stance.getHeight())) {
                hpIncrease = false;
                haste = false;
                sharpEyes = false;
                stance = true;
                powerGuard = false;
                infiniteAmmo = false;
                poor = false;
            }

            if ((mouseX >= 416 && mouseX <= 416 + PowerGuard.getWidth())
                    && (mouseY >= 400 && mouseY <= 400 + PowerGuard.getHeight())) {
                hpIncrease = false;
                haste = false;
                sharpEyes = false;
                stance = false;
                powerGuard = true;
                infiniteAmmo = false;
                poor = false;
            }

            if ((mouseX >= 566 && mouseX <= 566 + InfiniteAmmo.getWidth())
                    && (mouseY >= 400 && mouseY <= 400 + InfiniteAmmo.getHeight())) {
                hpIncrease = false;
                haste = false;
                sharpEyes = false;
                stance = false;
                powerGuard = false;
                infiniteAmmo = true;
                poor = false;
            }

            if ((mouseX >= 44 && mouseX <= 130) && (mouseY >= 322 && mouseY <= 349)) {
                sbg.enterState(constants.LOADOUTSCREEN, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }

            if ((mouseX >= 641 && mouseX <= 761)
                    && (mouseY >= 521 && mouseY <= 556)) {
                if (b) {
                    if (hpIncrease) {
                        if (money >= readHacks.getCost(11)) {
                            modSave.saveHacks(account, "HpIncrease");
                            cost = readHacks.getCost(11);
                            poor = false;
                        } else {
                            poor = true;
                        }
                    } else if (haste) {
                        if (money >= readHacks.getCost(12)) {
                            modSave.saveHacks(account, "Haste");
                            cost = readHacks.getCost(12);
                            poor = false;
                        } else {
                            poor = true;
                        }
                    } else if (sharpEyes) {
                        if (money >= readHacks.getCost(13)) {
                            modSave.saveHacks(account, "SharpEyes");
                            cost = readHacks.getCost(13);
                            poor = false;
                        } else {
                            poor = true;
                        }
                    } else if (stance) {
                        if (money >= readHacks.getCost(14)) {
                            modSave.saveHacks(account, "Stance");
                            cost = readHacks.getCost(14);
                            poor = false;
                        } else {
                            poor = true;
                        }
                    } else if (powerGuard) {
                        if (money >= readHacks.getCost(15)) {
                            modSave.saveHacks(account, "PowerGuard");
                            cost = readHacks.getCost(15);
                            poor = false;
                        } else {
                            poor = true;
                        }
                    } else if (infiniteAmmo) {
                        if (money >= readHacks.getCost(16)) {
                            modSave.saveHacks(account, "InfiniteAmmo");
                            cost = readHacks.getCost(16);
                            poor = false;
                        } else {
                            poor = true;
                        }
                    }
                    if (poor == false) {
                        modSave.save(account, readSave.getName(account), (money - cost), readSave.getCurrentLevel(account), readSave.getCurrentStage(account), false);
                        sbg.enterState(constants.HACKSPASSIVE, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
                    }
                }
            }
        }
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        super.render(container, game, g);
        background.draw(0, 0);
        HpIncrease.draw(266, 300);
        Haste.draw(416, 300);
        SharpEyes.draw(566, 300);
        Stance.draw(266, 400);
        PowerGuard.draw(416, 400);
        InfiniteAmmo.draw(566, 400);

        if (poor) {
            hpIncrease = false;
            haste = false;
            sharpEyes = false;
            stance = false;
            powerGuard = false;
            infiniteAmmo = false;
            g.setColor(Color.blue);
            g.drawString("You don't have enough money!", 240, 162);
        } else if (hpIncrease) {
            g.setColor(Color.blue);
            g.drawString(hacks.getPassive(0), 240, 162);
            if (checker.contains("HpIncrease")) {
                b = false;
            } else {
                buy.draw(641, 521);
                b = true;
            }
        } else if (haste) {
            g.setColor(Color.blue);
            g.drawString(hacks.getPassive(1), 240, 162);
            if (checker.contains("Haste")) {
                b = false;
            } else {
                buy.draw(641, 521);
                b = true;
            }
        } else if (sharpEyes) {
            g.setColor(Color.blue);
            g.drawString(hacks.getPassive(2), 240, 162);
            if (checker.contains("SharpEyes")) {
                b = false;
            } else {
                buy.draw(641, 521);
                b = true;
            }
        } else if (stance) {
            g.setColor(Color.blue);
            g.drawString(hacks.getPassive(3), 240, 162);
            if (checker.contains("Stance")) {
                b = false;
            } else {
                buy.draw(641, 521);
                b = true;
            }
        } else if (powerGuard) {
            g.setColor(Color.blue);
            g.drawString(hacks.getPassive(4), 240, 162);
            if (checker.contains("PowerGuard")) {
                b = false;
            } else {
                buy.draw(641, 521);
                b = true;
            }
        } else if (infiniteAmmo) {
            g.setColor(Color.blue);
            g.drawString(hacks.getPassive(5), 240, 162);
            if (checker.contains("InfiniteAmmo")) {
                b = false;
            } else {
                buy.draw(641, 521);
                b = true;
            }
        } else {
            b = false;
        }
    }
}