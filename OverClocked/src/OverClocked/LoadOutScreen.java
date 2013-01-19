/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OverClocked;

import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.World;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/** Author: Stanley Fung
 * Date:
 * Teacher:
 * Description:
 * 
 */
public class LoadOutScreen extends World {

    public static Constants constants = new Constants();
    private ReadSaveSlotsXML readSave = new ReadSaveSlotsXML();
    private Image background = null;
    private int account, level, stage;
    private boolean loading;

    /* constructor
     * 
     * pre: state id, containre
     * post: get pictures
     */
    public LoadOutScreen(int id, GameContainer container, int acc) {
        super(id, container);
        account = acc;
        background = ResourceManager.getImage("loadout");
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        super.enter(container, game);
        level = readSave.getCurrentLevel(account);
        stage = readSave.getCurrentStage(account);
        loading = false;
    }

    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
        super.leave(container, game);
        loading = false;

    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        super.update(container, game, delta);
        Input input = container.getInput();

        int mouseX = input.getMouseX();
        int mouseY = input.getMouseY();

        if ((mouseX >= 191 && mouseX <= 281) && (mouseY >= 231 && mouseY <= 271)) {
            if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                game.addState(new HacksPower(constants.HACKSPOWER, container, account));
                game.enterState(constants.HACKSPOWER, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
        }
        if ((mouseX >= 311 && mouseX <= 401) && (mouseY >= 231 && mouseY <= 271)) {
            if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                game.addState(new HacksDefense(constants.HACKSDEFENSE, container, account));
                game.enterState(constants.HACKSDEFENSE, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
        }
        if ((mouseX >= 431 && mouseX <= 521) && (mouseY >= 231 && mouseY <= 271)) {
            if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                game.addState(new HacksUtility(constants.HACKSUTILITY, container, account));
                game.enterState(constants.HACKSUTILITY, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
        }
        if ((mouseX >= 551 && mouseX <= 641) && (mouseY >= 231 && mouseY <= 271)) {
            if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                game.addState(new HacksPassive(constants.HACKSPASSIVE, container, account));
                game.enterState(constants.HACKSPASSIVE, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
        }
        if ((mouseX >= 42 && mouseX <= 130) && (mouseY >= 188 && mouseY <= 216)) {
            if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                {
                    game.enterState(constants.STORYLEVELSELECTOR, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
                }
            }
        }
        if ((mouseX >= 635 && mouseX <= 635 + 138) && (mouseY >= 519 && mouseY <= 519 + 52)) {
            if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                {
                    loading = true;
                    if (stage == 5) {
                        if (level == 1) {
                            try {
                                ResourceManager.loadResources("data/ResourcesBossDrag.xml");

                            } catch (IOException ex) {
                                Logger.getLogger(OverClocked.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        if (level == 2) {
                            try {
                                ResourceManager.loadResources("data/ResourcesBossBerg.xml");

                            } catch (IOException ex) {
                                Logger.getLogger(OverClocked.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        if (level == 3) {
                            try {
                                ResourceManager.loadResources("data/ResourcesBossNib.xml");

                            } catch (IOException ex) {
                                Logger.getLogger(OverClocked.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                    }
                    if (level != 4) {
                        if (game.getState(constants.MAINGAMESTATE) == null) {

                            game.addState(new MainGameState(constants.MAINGAMESTATE, container, readSave.getCurrentLevel(account), readSave.getCurrentStage(account), account));
                            MainGameState target = (MainGameState) game.getState(constants.MAINGAMESTATE);
                            target.setLevelAndStageAndSlot(readSave.getCurrentLevel(account), readSave.getCurrentStage(account), account);
                            game.enterState(constants.MAINGAMESTATE, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
                        } else if (game.getState(constants.MAINGAMESTATE) != null) {

                            MainGameState target = (MainGameState) game.getState(constants.MAINGAMESTATE);
                            target.setLevelAndStageAndSlot(readSave.getCurrentLevel(account), readSave.getCurrentStage(account), account);
                            game.enterState(constants.MAINGAMESTATE, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
                        }
                    }


                }
            }
        }
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        super.render(container, game, g);
        background.draw(0, 0);
        if (loading) {
            g.drawString("LOADING...", 288, 518);
        }
        switch (level) {
            case 1:
                g.setColor(Color.black);
                g.drawString("The Amazon Forest: *SECURITY*(Weak) \nDifficult to traverse.\nBeware of pitfalls and tough jumps!", 215, 102);

                g.setColor(Color.white);
                g.drawString("GOOD LUCK!", 587, 454);
                switch (stage) {

                    case 1:
                        g.drawString("It's been a long time since your last mission Zeke.\nJust try to remember the basics and survive.\nSecurity in this sector is minimal...", 163, 337);
                        break;
                    case 2:
                        g.drawString("Not bad for your first time back. It has been \nconfirmed that Gage has hidden a part of his\nweapon here. Keep moving forward!", 163, 337);

                        break;
                    case 3:
                        g.drawString("You may want to learn the FLY ability on the H.A.C.K\nsystem.It will be useful in all environments,\nespecially this one...", 163, 337);

                        break;
                    case 4:
                        g.drawString("Looks like we're getting close... We've recieved reports\nof a strange creature from the locals.\nWho knows what Gage has done...\nAnyways, remember to take time to train with the\n H.A.C.K system if you need more abilities!", 163, 337);

                        break;
                    case 5:
                        g.drawString("Radar shows that the artifact is near by! We are also\ndetecting a mysterious energy up ahead...It's\nnot human or machine...prepare for anything!", 163, 337);
                        break;
                }
                break;
            case 2:
                g.setColor(Color.black);
                g.drawString("Alberta Coal Mines:*SECURITY*(Medium)\nMost pathways are tight and narrow.\nThere isn't too much cover so move fast!", 215, 102);
                g.setColor(Color.white);
                g.drawString("GOOD LUCK!", 587, 454);
                switch (stage) {

                    case 1:
                        g.drawString("I can't believe MR.G was able to create something like that!\n Gage is really become a danger to the world...\nNext stop is the coalmines in Alberta!", 163, 337);
                        break;
                    case 2:
                        g.drawString("Pretty scary down there eh? These passages have been\nabandoned for years...Be careful of ambushes and traps,\nGage has definitely rigged this place.", 163, 337);
                        break;
                    case 3:
                        g.drawString("The next artifact is somewhere deep in the mines. \nYou're only about half way there.\nLet's see what we can find!", 163, 337);
                        break;
                    case 4:
                        g.drawString("You're getting much closer! I can see enemies are \nstarting to grow in numbers. You \nmight want to invest in a bit more firepower from H.A.C.K.", 163, 337);
                        break;
                    case 5:
                        g.drawString("Our signals can not reach that far into the mine anymore...\nLooks like you're own your own this time Zeke!", 163, 337);
                        break;
                }
                break;
            case 3:
                g.setColor(Color.black);
                g.drawString("Himalayas:*SECURITY*(High)\nExpect to do a lot of climbing...\nYou are out in the open so remember to duck!", 215, 102);
                g.setColor(Color.white);
                g.drawString("GOOD LUCK!", 587, 454);
                switch (stage) {
                    case 1:
                        g.drawString("Alright, the next stop on our radar is the \nHimalayan mountains!The terrain here is gonna be a bit odd, \nso remember to use H.A.C.K to it's fullest!", 163, 337);

                        break;
                    case 2:
                        g.drawString("Have you at least gotten the Shield yet? It can really\nsave you when there are many bullets flying!\nThere are going to be a lot of Droids due to higher security.", 163, 337);

                        break;
                    case 3:
                        g.drawString("It would make sense that Gage would hide the artifact\nat the top of the mountain. Time to get\nclimbing!", 163, 337);

                        break;
                    case 4:
                        g.drawString("You're almost there! The target should be just above\nthat crevasse!\n", 163, 337);

                        break;
                    case 5:
                        g.drawString("A large heat source is approaching from the air!\nGet ready for battle!", 163, 337);
                        break;
                }
                break;
            case 4:
                g.setColor(Color.black);
                g.drawString("Japan:*SECURITY*(HIGH)\nMr.G(AKA Gage)'s home base\nNot much is known about this place anymore due to G's forces...", 215, 102);
                switch (stage) {
                    case 1:
                        g.drawString("TO BE CONTINUED!", 163, 337);
                        break;
                    case 2:
                        g.drawString("TO BE CONTINUED!", 163, 337);

                        break;
                    case 3:
                        g.drawString("TO BE CONTINUED!", 163, 337);

                        break;
                    case 4:
                        g.drawString("TO BE CONTINUED!", 163, 337);

                        break;
                    case 5:
                        g.drawString("TO BE CONTINUED!", 163, 337);

                        break;
                }
                break;
        }
    }
}
