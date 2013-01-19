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
public class HacksDefense extends World {

    private int account, money, cost;
    public static Constants constants = new Constants();
    private HacksInfo hacks;
    private ReadSaveSlotsXML readSave;
    private ModifySaveSlotXML modSave;
    private ReadHacksInfoXML readHacks;
    private Image background = null, buy, activate, Shield, BattleGrieves, Invincible;
    boolean shield, battleGrieves, invincible, a, b, poor;
    public String checker, defense;

    /* constructor
     * 
     * pre: state id, container
     * post: get pictures
     */
    public HacksDefense(int id, GameContainer container, int acc) {
        super(id, container);
        account = acc;
        background = ResourceManager.getImage("hMenuDef");
        buy = ResourceManager.getImage("buy");
        activate = ResourceManager.getImage("activate");
        Shield = ResourceManager.getImage("Shield");
        BattleGrieves = ResourceManager.getImage("BattleGrieves");
        Invincible = ResourceManager.getImage("Invincible");
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        super.enter(container, game);
        readSave = new ReadSaveSlotsXML();
        modSave = new ModifySaveSlotXML();
        readHacks = new ReadHacksInfoXML();
        hacks = new HacksInfo();
        checker = readSave.getBoughtHacks(account);
        defense = readSave.getDefenseHacks(account);
        money = readSave.getMoney(account);
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
            if ((mouseX >= 266 && mouseX <= 266 + Shield.getWidth())
                    && (mouseY >= 300 && mouseY <= 300 + Shield.getHeight())) {
                shield = true;
                battleGrieves = false;
                invincible = false;
                poor = false;
            }

            if ((mouseX >= 416 && mouseX <= 416 + BattleGrieves.getWidth())
                    && (mouseY >= 300 && mouseY <= 300 + BattleGrieves.getHeight())) {
                shield = false;
                battleGrieves = true;
                invincible = false;
                poor = false;
            }

            if ((mouseX >= 566 && mouseX <= 566 + Invincible.getWidth())
                    && (mouseY >= 300 && mouseY <= 300 + Invincible.getHeight())) {
                shield = false;
                battleGrieves = false;
                invincible = true;
                poor = false;
            }

            if ((mouseX >= 46 && mouseX <= 133) && (mouseY >= 327 && mouseY <= 353)) {
                sbg.enterState(constants.LOADOUTSCREEN, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }

            if ((mouseX >= 641 && mouseX <= 761)
                    && (mouseY >= 521 && mouseY <= 556)) {
                if (a) {
                    if (shield) {
                        modSave.saveDefense(account, "Shield");
                    } else if (battleGrieves) {
                        modSave.saveDefense(account, "BattleGrieves");
                    } else if (invincible) {
                        modSave.saveDefense(account, "Invincible");
                    }
                    sbg.enterState(constants.LOADOUTSCREEN, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
                } else if (b) {
                    if (shield) {
                        if (money >= readHacks.getCost(0)) {
                            modSave.saveHacks(account, "Shield");
                            cost = readHacks.getCost(0);
                            poor = false;
                        } else {
                            poor = true;
                        }
                    } else if (battleGrieves) {
                        if (money >= readHacks.getCost(1)) {
                            modSave.saveHacks(account, "BattleGrieves");
                            cost = readHacks.getCost(1);
                            poor = false;
                        } else {
                            poor = true;
                        }
                    } else if (invincible) {
                        if (money >= readHacks.getCost(2)) {
                            modSave.saveHacks(account, "Invincible");
                            cost = readHacks.getCost(2);
                            poor = false;
                        } else {
                            poor = true;
                        }
                    }
                    if (poor == false) {
                        modSave.save(account, readSave.getName(account), (money - cost), readSave.getCurrentLevel(account), readSave.getCurrentStage(account), false);
                        sbg.enterState(constants.HACKSDEFENSE, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
                    }
                }
            }
        }
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        super.render(container, game, g);
        background.draw(0, 0);
        Shield.draw(266, 300);
        BattleGrieves.draw(416, 300);
        Invincible.draw(566, 300);
        if (poor) {
            shield = false;
            battleGrieves = false;
            invincible = false;
            g.setColor(Color.blue);
            g.drawString("You don't have enough money!", 240, 162);
        } else if (shield) {
            g.setColor(Color.blue);
            g.drawString(hacks.getDefense(0), 240, 162);
            if (defense.contains("Shield")) {
                a = false;
                b = false;
            } else if (checker.contains("Shield")) {
                activate.draw(641, 521);
                a = true;
                b = false;
            } else {
                buy.draw(641, 521);
                a = false;
                b = true;
            }
        } else if (battleGrieves) {
            g.setColor(Color.blue);
            g.drawString(hacks.getDefense(1), 240, 162);
            if (defense.contains("BattleGrieves")) {
                a = false;
                b = false;
            } else if (checker.contains("BattleGrieves")) {
                activate.draw(641, 521);
                a = true;
                b = false;
            } else {
                buy.draw(641, 521);
                a = false;
                b = true;
            }
        } else if (invincible) {
            g.setColor(Color.blue);
            g.drawString(hacks.getDefense(2), 240, 162);
            if (defense.contains("Invincible")) {
                a = false;
                b = false;
            } else if (checker.contains("Invincible")) {
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
