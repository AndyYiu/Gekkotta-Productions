/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OverClocked;

import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.World;
import java.awt.Font;
import java.util.Random;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author admin
 */
public class MiniGameState extends World {

    private ModifySaveSlotXML modSave = new ModifySaveSlotXML();
    private ReadSaveSlotsXML readSave = new ReadSaveSlotsXML();
    private Image background, cursor;
    private int choice, score, amount, correctCount = 0;
    private MiniGameEnemy enemies[];
    private MiniGameObject object[];
    private MiniGamePlayer player;
    private Random rand;
    private float enemyX, enemyY;
    private float speed, rotation, hip;
    private float timeMax, timeMin, newTime, roll1, roll2, mouseX, mouseY;
    private int time, overAllTime = 60000, slot, prevGame = 6;
    private boolean hit, loaded, musicStart;
    public static Constants constants = new Constants();
    private Music bgm;

    public MiniGameState(int id, GameContainer gc, int selection, int slot) throws SlickException {
        super(id, gc);
        this.slot = slot;
        choice = selection;
        score = 0;
    }

	//pre: int s
	//post: slot is created
	//This method takes 
    public void setSlot(int s) {
        this.slot = s;
        overAllTime = 60000;
    }

	//pre: none
	//post: all objects created and removed
	//removes all objects created in the mini game state at the current instant
    public void resetAll() {

        switch (choice) {
            case 1:
                player.removedFromWorld();

                for (int i = 0; i < enemies.length; i++) {
                    if (enemies[i] != null) {
                        enemies[i].removedFromWorld();
                    }
                }
                this.clear();
                break;
            case 2:

                for (int i = 0; i < object.length; i++) {
                    if (object[i] != null) {
                        object[i].removedFromWorld();
                    }
                }
                for (int i = 0; i < enemies.length; i++) {
                    if (enemies[i] != null) {
                        enemies[i].removedFromWorld();
                    }
                }
                this.clear();
                break;
            case 3://pong

                player.removedFromWorld();
                enemies[0].removedFromWorld();
                if (object[0] != null) {
                    object[0].removedFromWorld();
                }
                this.clear();
                break;
            case 4:
                this.clear();

                break;
            case 5:
                this.clear();

                break;
            case 6:
                player.removedFromWorld();
                for (int i = 0; i < object.length; i++) {
                    if (object[i] != null) {
                        object[i].removedFromWorld();
                    }
                }
                for (int i = 0; i < enemies.length; i++) {
                    if (enemies[i] != null) {
                        enemies[i].removedFromWorld();
                    }
                }
                this.clear();
                break;
            case 7:
                this.clear();
                break;
            case 8:
                this.clear();
                break;
            case 9:

                player.removedFromWorld();
                for (int i = 0; i < object.length; i++) {
                    if (object[i] != null) {
                        object[i].removedFromWorld();
                    }
                }
                for (int i = 0; i < enemies.length; i++) {
                    if (enemies[i] != null) {
                        enemies[i].removedFromWorld();
                    }
                }
                this.clear();
                break;
            case 11:
                player.removedFromWorld();
                for (int i = 0; i < object.length; i++) {
                    if (object[i] != null) {
                        object[i].removedFromWorld();
                    }
                }
                for (int i = 0; i < enemies.length; i++) {
                    if (enemies[i] != null) {
                        enemies[i].removedFromWorld();
                    }
                }
                this.clear();
                break;
            case 12:
                player.removedFromWorld();
                for (int i = 0; i < enemies.length; i++) {
                    if (enemies[i] != null) {
                        enemies[i].removedFromWorld();
                    }
                }
                this.clear();
                break;
            case 13:
                player.removedFromWorld();
                for (int i = 0; i < enemies.length; i++) {
                    if (enemies[i] != null) {
                        enemies[i].removedFromWorld();
                    }
                }
                this.clear();
                break;
            case 14:
                player.removedFromWorld();
                for (int i = 0; i < enemies.length; i++) {
                    if (enemies[i] != null) {
                        enemies[i].removedFromWorld();
                    }
                }
                this.clear();
                break;
            case 15:
                for (int i = 0; i < object.length; i++) {
                    if (object[i] != null) {
                        object[i].removedFromWorld();;
                    }
                }
                for (int i = 0; i < enemies.length; i++) {
                    if (enemies[i] != null) {
                        enemies[i].removedFromWorld();
                    }
                }
                this.clear();
                break;
            case 16:
                player.removedFromWorld();
                for (int i = 0; i < enemies.length; i++) {
                    if (enemies[i] != null) {
                        enemies[i].removedFromWorld();
                    }
                }
                this.clear();
                break;
        }
    }

	//pre: none
	//post: resets all values that would carry on, back to 0, and stops music
	//resets values and stops music once leaving the class
    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
        super.leave(container, game);
        score = 0;
        roll1 = 0;
        roll2 = 0;
        bgm.stop();
        bgm = null;
        musicStart = false;
    }

	//pre: none
	//post: music plays
	//plays music
    public void startMusic() throws SlickException {
        bgm = new Music("data/Music/Base.ogg");
        bgm.play();
        musicStart = true;
    }

	//pre: container, game
	//post: initializes all the variables, creates and adds player, enemy and object objects, and sets background
	//intializes everything once the code enters this class
    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        super.enter(container, game);
        if (musicStart == false) {
            startMusic();
        }
        prevGame = choice;
        choice = (int) (Math.random() * 16 + 1);
        if (choice == prevGame) {
            choice = (int) (Math.random() * 16 + 1);
        }
        score = score;

        loaded = false;
        switch (choice) {
            case 1://Frogger
                time = 10000;
                score = score;
                enemies = new MiniGameEnemy[5];
                player = new MiniGamePlayer(300, 550, choice);
                add(player);
                enemyX = 0;
                enemyY = 100;
                background = ResourceManager.getImage("background");
                for (int i = 0; i < enemies.length; i++) {
                    enemies[i] = new MiniGameEnemy(enemyX, enemyY, choice);
                    enemyY = enemyY + 100;
                    add(enemies[i]);
                }

                break;
            case 2://Guitar Hero
                time = 10000;
                score = score;
                object = new MiniGameObject[4];
                enemies = new MiniGameEnemy[4];
                enemyX = 160;
                enemyY = -50;
                background = ResourceManager.getImage("background");
                for (int i = 0; i < enemies.length; i++) {
                    enemies[i] = new MiniGameEnemy(enemyX, enemyY, choice);//Orb
                    enemyX = enemyX + 160;
                    add(enemies[i]);
                }
                enemyX = 165;
                for (int i = 0; i < object.length; i++) {
                    enemyY = 500;
                    object[i] = new MiniGameObject(enemyX, enemyY, choice);//Catcher
                    enemyX = enemyX + 160;
                    add(object[i]);
                }

                break;
            case 3://pong
                time = 10000;
                score = score + 2000;
                object = new MiniGameObject[1];
                enemies = new MiniGameEnemy[1];
                player = new MiniGamePlayer(578, 250, choice);
                add(player);
                enemyX = 10;
                enemyY = 250;
                background = ResourceManager.getImage("background");
                enemies[0] = new MiniGameEnemy(enemyX, enemyY, 3);
                add(enemies[0]);
                object[0] = new MiniGameObject(290, 290, 3);
                add(object[0]);
                break;
            case 4: //spam click
                time = 5000;
                score = score;
                background = ResourceManager.getImage("background");
                break;
            case 5: //reaction click
                time = 10000;
                score = score;
                timeMax = 8;
                timeMin = 2;
                newTime = (int) (Math.random() * (timeMax - timeMin) + timeMin);
                newTime = newTime * 1000;
                break;
            case 6://cannon
                time = 10000;
                score = score;
                player = new MiniGamePlayer(250, 500, 6);
                add(player);
                object = new MiniGameObject[10];
                enemies = new MiniGameEnemy[10];
                enemyX = 0;
                enemyY = -75;
                background = ResourceManager.getImage("glab2");
                for (int i = 0; i < enemies.length; i++) {
                    enemies[i] = new MiniGameEnemy(enemyX, enemyY, 6);
                    enemyX = enemyX + 80;
                    add(enemies[i]);
                }
                break;
            case 7: //lucky dice
                time = 10000;
                score = score;
                roll1 = 0;
                break;
            case 8: //reverse reaction click
                time = 10000;
                score = score;
                timeMax = 8;
                timeMin = 2;
                newTime = (int) (Math.random() * (timeMax - timeMin) + timeMin);
                newTime = newTime * 1000;
                break;
            case 9: //zombie
                time = 10000;
                score = score;
                rand = new Random();
                player = new MiniGamePlayer(400, 300, 9);
                object = new MiniGameObject[10];
                enemies = new MiniGameEnemy[30];
                add(player);
                cursor = ResourceManager.getImage("crosshair");
                for (int i = 0; i < enemies.length; i++) {

                    if (enemies[i] == null) {
                        boolean cycle = rand.nextBoolean();
                        if (cycle) {
                            cycle = rand.nextBoolean();
                            enemyX = (float) (Math.random() * 0 + 800);
                            if (cycle) {
                                enemyY = (float) (Math.random() * 750 + 900);
                            } else if (!cycle) {
                                enemyY = (float) (Math.random() * -350 + -150);
                            }
                        } else if (!cycle) {
                            cycle = rand.nextBoolean();
                            enemyY = (float) (Math.random() * 0 + 600);
                            if (cycle) {
                                enemyX = (float) (Math.random() * -100 + -200);
                            } else if (!cycle) {
                                enemyX = (float) (Math.random() * 900 + 1000);
                            }
                        }
                        enemies[i] = new MiniGameEnemy(enemyX, enemyY, 9);
                        add(enemies[i]);
                    }
                }
                break;
            case 10: //percision click
                time = 8000;
                score = score;
                roll1 = 0;
                roll1 = (int) (Math.random() * (20 - 2) + 2);
                while (roll1 % 2 != 0) {
                    roll1 = (int) (Math.random() * (20 - 2) + 2);
                }
                roll1 = roll1 * 1000;
                break;
            case 11: //space invaders
                time = 10000;
                score = score;
                player = new MiniGamePlayer(365, 400, choice);
                add(player);
                object = new MiniGameObject[5];
                enemies = new MiniGameEnemy[15];
                background = ResourceManager.getImage("background");
                int xP = 10;
                for (int i = 0; i < enemies.length; i++) {
                    enemies[i] = new MiniGameEnemy(xP, 110, 11);
                    add(enemies[i]);
                    xP += 50;
                }
                break;
            case 12: //asian driver
                time = 10000;
                score = score;
                player = new MiniGamePlayer(365, 400, choice);
                add(player);
                enemies = new MiniGameEnemy[10];
                background = ResourceManager.getImage("street");
                for (int i = 0; i < enemies.length; i++) {
                    int xD = (int) (Math.random() * (750 + 1) - 1);
                    enemies[i] = new MiniGameEnemy(xD, 110, choice);
                    add(enemies[i]);
                }
                break;
            case 13: //dodge everything!
                time = 10000;
                score = score;
                player = new MiniGamePlayer(250, 300, choice);
                add(player);
                enemies = new MiniGameEnemy[15];
                background = ResourceManager.getImage("background");
                for (int i = 0; i < enemies.length; i++) {
                    int xD = (int) (Math.random() * (750 + 1) - 1);
                    enemies[i] = new MiniGameEnemy(xD, 110, choice);
                    add(enemies[i]);
                }
                break;
            case 14: //catch the fruit
                time = 10000;
                score = score;
                player = new MiniGamePlayer(365, 500, choice);
                add(player);
                enemies = new MiniGameEnemy[10];
                for (int i = 0; i < enemies.length; i++) {
                    int xD = (int) (Math.random() * (750 + 1) - 1);
                    enemies[i] = new MiniGameEnemy(xD, 110, choice);
                    add(enemies[i]);
                }
                break;
            case 15:
                time = 1500;
                score = score;
                cursor = ResourceManager.getImage("crosshair");
                rand = new Random();
                int numEnemy = rand.nextInt(3) + 1;
                object = new MiniGameObject[3 - numEnemy];
                enemies = new MiniGameEnemy[numEnemy];
                background = ResourceManager.getImage("background");
                int xPos = 5;
                int yPos = 250;
                for (int i = 0; i < enemies.length; i++) {
                    enemies[i] = new MiniGameEnemy(xPos, yPos, choice);
                    add(enemies[i]);
                    xPos += 200;
                }
                for (int i = 0; i < object.length; i++) {
                    object[i] = new MiniGameObject(xPos, yPos, choice);
                    add(object[i]);
                    xPos += 200;
                }
                break;
            case 16:
                time = 10000;
                score = score;
                rand = new Random();
                player = new MiniGamePlayer(400, 300, choice);
                enemies = new MiniGameEnemy[30];
                add(player);
                background = ResourceManager.getImage("background");
                for (int i = 0; i < enemies.length; i++) {
                    if (enemies[i] == null) {
                        boolean cycle = rand.nextBoolean();
                        if (cycle) {
                            cycle = rand.nextBoolean();
                            enemyX = (float) (Math.random() * 0 + 800);
                            if (cycle) {
                                enemyY = (float) (Math.random() * 750 + 900);
                            } else if (!cycle) {
                                enemyY = (float) (Math.random() * -350 + -150);
                            }
                        } else if (!cycle) {
                            cycle = rand.nextBoolean();
                            enemyY = (float) (Math.random() * 0 + 600);
                            if (cycle) {
                                enemyX = (float) (Math.random() * -100 + -200);
                            } else if (!cycle) {
                                enemyX = (float) (Math.random() * 900 + 1000);
                            }
                        }
                        enemies[i] = new MiniGameEnemy(enemyX, enemyY, choice);
                        add(enemies[i]);
                    }
                }
                break;
        }
        loaded = true;
    }


    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        super.update(gc, sbg, delta);
        Input input = container.getInput();
        overAllTime -= delta;
        time -= delta;
        if (overAllTime < 0) {
            resetAll();
            int money = (score / 100) + readSave.getMoney(slot);

            modSave.save(slot, readSave.getName(slot), money, readSave.getCurrentLevel(slot), readSave.getCurrentStage(slot), false);
            StoryLevelSelector target = (StoryLevelSelector) sbg.getState(constants.STORYLEVELSELECTOR);
            target.setInfo(readSave.getCurrentLevel(slot), readSave.getCurrentStage(slot), slot, money);
            sbg.enterState(constants.STORYLEVELSELECTOR);
        }
        if (score < 0) {
            score = 0;
        }
        if (time < 0) {
            resetAll();
            enter(container, sbg);

        }
        if (loaded) {
            switch (choice) {

                case 1://frogger
                    if (player.y < 99) {
                        score += 750;
                        player.resetPosition();
                    }
                    break;
                case 2://Guitar Hero

                    if (input.isKeyPressed(input.KEY_A)) {

                        if (enemies[0].y >= (object[0].y - 20) && enemies[0].y <= object[0].y + object[0].objectAnim.getHeight()) {
                            object[0].objectAnim.setAutoUpdate(true);
                            score += 250;
                        } else {
                            score -= 50;
                        }
                    }
                    if (input.isKeyPressed(input.KEY_S)) {

                        if (enemies[1].y >= (object[1].y - 20) && enemies[1].y <= object[1].y + object[1].objectAnim.getHeight()) {
                            object[1].objectAnim.setAutoUpdate(true);
                            score += 250;

                        } else {
                            score -= 50;

                        }
                    }
                    if (input.isKeyPressed(input.KEY_D)) {

                        if (enemies[2].y >= (object[2].y - 20) && enemies[2].y <= object[2].y + object[2].objectAnim.getHeight()) {
                            object[2].objectAnim.setAutoUpdate(true);
                            score += 250;

                        } else {
                            score -= 50;

                        }
                    }
                    if (input.isKeyPressed(input.KEY_F)) {

                        if (enemies[3].y >= (object[3].y - 20) && enemies[3].y <= object[3].y + object[3].objectAnim.getHeight()) {
                            object[3].objectAnim.setAutoUpdate(true);
                            score += 250;

                        } else {
                            score -= 50;

                        }
                    }
                    for (int i = 0; i < object.length; i++) {
                        if (object[i].objectAnim != null) {
                            if (object[i].objectAnim.getFrame() == 2) {
                                object[i].objectAnim.setCurrentFrame(0);
                                object[i].objectAnim.setAutoUpdate(false);
                            }

                        }
                    }
                    break;
                case 3: //pong
                    if (object[0].x < 0) {//Null pointer exception here sometimes
                        object[0].resetObject();
                    }
                    if (object[0].x > 600) {
                        score -= 300;
                        object[0].resetObject();
                    }

                    if (enemies[0].y > object[0].y) {
                        enemies[0].up = true;
                        enemies[0].down = false;
                    } else if ((enemies[0].y + enemies[0].height) < (object[0].y + object[0].height)) {
                        enemies[0].up = false;
                        enemies[0].down = true;
                    } else {
                        enemies[0].up = false;
                        enemies[0].down = false;
                    }
                    break;
                case 4: //spam clicker
                    if (input.isKeyPressed(input.KEY_SPACE)) {
                        score += 150;
                    }
                    break;
                case 5: //reaction clicker
                    if (newTime >= time - 500 && newTime <= time) {
                        if (input.isKeyPressed(input.KEY_ENTER)) {
                            score += 2500;

                        }
                    } else if (input.isKeyPressed(input.KEY_ENTER)) {
                        score -= 2500;
                        resetAll();

                        time = 0;
                    }

                    break;
                case 6: //cannnon shooter
                    if (input.isKeyPressed(input.KEY_SPACE)) {
                        for (int i = 0; i < object.length; i++) {
                            if (object[i] == null) {
                                float targetAngle = (float) player.getRotate();
                                if (targetAngle < 0) {
                                    targetAngle = 360 + targetAngle;
                                }
                                object[i] = (player.generateBullet(targetAngle, 6));
                                add(object[i]);
                                break;
                            }
                        }
                    }
                    for (int i = 0; i < object.length; i++) {
                        if (object[i] != null) {
                            if (object[i].y < -10 || object[i].x > 610 || object[i].x < -10) {
                                object[i] = null;
                            }
                        }
                    }
                    for (int i = 0; i < enemies.length; i++) {
                        if (enemies[i] != null) {
                            if (enemies[i].collide("MINIGAMEOBJECT", enemies[i].x, enemies[i].y) != null) {
                                score = score + 50;
                                enemies[i].resetPosition();
                            }
                        }
                    }
                    break;


                case 7: //lucky dice
                    if (input.isKeyPressed(input.KEY_ENTER)) {
                        roll1 = (int) (Math.random() * (6 - 1) + 1);
                        hit = true;
                        if (roll1 == 1) {
                            score = 0;
                        } else if (roll1 == 2) {
                            score -= 5000;
                        } else if (roll1 == 3) {
                            score = score / 2;
                        } else if (roll1 == 4) {
                            score = score / 4;
                        } else if (roll1 == 5) {
                            score = score * 2;
                        } else if (roll1 == 6) {
                            score = score * 10;
                        }
                    } else if (!input.isKeyPressed(input.KEY_ENTER)) {
                        hit = false;
                    }
                    break;
                case 8:
                    if (time <= 0) {
                        score += 3000;
                        resetAll();
                        time = 0;
                    }
                    if (newTime >= time - 500 && newTime <= time) {
                        if (input.isKeyPressed(input.KEY_ENTER)) {
                            score -= 2000;
                        }
                    } else if (input.isKeyPressed(input.KEY_ENTER)) {
                        score -= 2000;
                        resetAll();
                        time = 0;
                    }
                    break;
                case 9://zombie Survival
                    if (container.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                        for (int i = 0; i < object.length; i++) {
                            if (object[i] == null) {
                                float targetAngle = (float) player.getTargetAngle(player.x + 19, player.y + 48, container.getInput().getMouseX(), container.getInput().getMouseY());
                                if (targetAngle < 0) {
                                    targetAngle = 360 + targetAngle;
                                }
                                object[i] = (player.generateBullet(targetAngle, 9));

                                add(object[i]);
                                break;
                            }
                        }
                    }
                    for (int i = 0; i < object.length; i++) {
                        if (object[i] != null) {
                            if (object[i].x > 800 || object[i].x < 0 || object[i].y > 600 || object[i].y < 0) {
                                object[i] = null;
                            }
                        }
                    }

                    for (int i = 0; i < enemies.length; i++) {
                        if (enemies[i] != null) {
                            if (enemies[i].x >= 0 && enemies[i].x <= 800 && enemies[i].y >= 0 && enemies[i].y <= 600) {
                                if (enemies[i].x > player.x) {
                                    enemies[i].setVelocX(-3);
                                }
                                if (enemies[i].x < player.x) {
                                    enemies[i].setVelocX(3);
                                }
                                if (enemies[i].y > player.y) {
                                    enemies[i].setVelocY(-3);
                                }
                                if (enemies[i].y < player.y) {
                                    enemies[i].setVelocY(3);
                                }
                            } else {
                                if (enemies[i].x > container.getScreenWidth()) {
                                    enemies[i].setVelocX(-3);
                                } else if (enemies[i].x < 0) {
                                    enemies[i].setVelocX(3);
                                }
                                if (enemies[i].y > container.getScreenHeight()) {
                                    enemies[i].setVelocY(-2);
                                } else if (enemies[i].y < 0) {
                                    enemies[i].setVelocY(3);
                                }
                            }
                            if (enemies[i].collide("MINIGAMEOBJECT", enemies[i].x, enemies[i].y) != null) {
                                score = score + 100;
                                enemies[i].resetPosition();
                            }
                        }
                    }

                    if (player.getHealth() < 0) {

                        resetAll();
                        time = 0;
                    }

                    break;
                case 10:
                    if (time <= 900) {
                        if (roll2 == roll1) {
                            score += 3000;
                            resetAll();
                            time = 0;
                        } else if (roll2 != roll1) {
                            score -= 3000;
                            resetAll();
                            time = 0;
                        }
                    }
                    if (input.isKeyPressed(input.KEY_UP)) {
                        roll2 += 400;
                    } else if (input.isKeyPressed(input.KEY_DOWN)) {
                        roll2 -= 200;
                    }
                    if (input.isKeyPressed(input.KEY_UP)) {
                        roll2 += 400;
                    } else if (input.isKeyPressed(input.KEY_S)) {
                        roll2 -= 200;
                    }
                    break;
                case 11:
                    if (time <= 900) {
                        resetAll();
                        this.clear();
                        time = 0;
                    }
                    if (input.isKeyPressed(input.KEY_SPACE)) {
                        for (int i = 0; i < object.length; i++) {
                            if (object[i] == null) {
                                object[i] = new MiniGameObject(player.x + 28, player.y, choice);
                                add(object[i]);
                                break;
                            }
                        }
                    }
                    for (int i = 0; i < object.length; i++) {
                        if (object[i] != null) {
                            object[i].y -= 0.9f * delta;
                            if (object[i].y < -10) {
                                object[i] = null;
                            }
                        }
                    }
                    for (int i = 0; i < enemies.length; i++) {
                        if (enemies[i] != null) {
                            enemies[i].y++;
                            if (enemies[i].y > 600) {
                                resetAll();
                                this.clear();
                                time = 0;
                                score -= 1500;
                            }
                            if (enemies[i].collide("MINIGAMEOBJECT", enemies[i].x, enemies[i].y) != null) {
                                enemies[i].removedFromWorld();
                                enemies[i].destroy();
                                enemies[i] = null;
                                score += 250;
                            }
                        }
                    }

                    break;
                case 12:
                    if (time <= 900) {
                        resetAll();
                        this.clear();
                        time = 0;
                    }
                    for (int i = 0; i < enemies.length; i++) {
                        if (enemies[i].collide("MINIGAMEPLAYER", enemies[i].x, enemies[i].y) != null) {
                            score += 50;
                            enemies[i].resetPosition();
                        }
                    }
                    break;
                case 13:
                    if (time <= 900) {
                        score += 3000;
                        resetAll();
                        this.clear();
                        time = 0;
                    }
                    for (int i = 0; i < enemies.length; i++) {
                        if (enemies[i].collide("MINIGAMEPLAYER", enemies[i].x, enemies[i].y) != null) {
                            score -= 50;
                            enemies[i].resetPosition();
                        }
                    }
                    break;
                case 14:
                    if (time <= 900) {
                        resetAll();
                        this.clear();
                        time = 0;
                    }
                    for (int i = 0; i < enemies.length; i++) {
                        if (enemies[i].collide("MINIGAMEPLAYER", enemies[i].x, enemies[i].y) != null) {
                            score += 300;
                            enemies[i].resetPosition();
                        }
                        if (enemies[i].y > 600) {
                            score -= 50;
                            enemies[i].resetPosition();
                        }
                    }
                    break;
                case 15://shoot terrorist
                    if (container.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                        for (int i = 0; i < enemies.length; i++) {
                            if (enemies[i] != null) {
                                if ((container.getInput().getMouseX() > enemies[i].x && container.getInput().getMouseX() < enemies[i].x + enemies[i].width) && (container.getInput().getMouseY() > enemies[i].y && container.getInput().getMouseY() < enemies[i].y + enemies[i].height)) {
                                    enemies[i].resetPosition();
                                    enemies[i] = null;
                                    score += 3000;
                                }
                            }
                        }
                        for (int i = 0; i < object.length; i++) {
                            if (object[i] != null) {
                                if ((container.getInput().getMouseX() > object[i].x && container.getInput().getMouseX() < object[i].x + object[i].width) && (container.getInput().getMouseY() > object[i].y && container.getInput().getMouseY() < object[i].y + object[i].height)) {
                                    object[i].resetObject();
                                    object[i] = null;
                                    score -= 3000;
                                }
                            }
                        }
                    }
                    break;
                case 16:
                    for (int i = 0; i < enemies.length; i++) {
                        if (enemies[i] != null) {
                            if (enemies[i].x >= 0 && enemies[i].x <= 800 && enemies[i].y >= 0 && enemies[i].y <= 600) {
                                if (enemies[i].x > player.x) {
                                    enemies[i].setVelocX(-3);
                                }
                                if (enemies[i].x < player.x) {
                                    enemies[i].setVelocX(3);
                                }
                                if (enemies[i].y > player.y) {
                                    enemies[i].setVelocY(-3);
                                }
                                if (enemies[i].y < player.y) {
                                    enemies[i].setVelocY(3);
                                }
                            } else {
                                if (enemies[i].x > container.getScreenWidth()) {
                                    enemies[i].setVelocX(-3);
                                } else if (enemies[i].x < 0) {
                                    enemies[i].setVelocX(3);
                                }
                                if (enemies[i].y > container.getScreenHeight()) {
                                    enemies[i].setVelocY(-2);
                                } else if (enemies[i].y < 0) {
                                    enemies[i].setVelocY(3);
                                }
                            }
                        }
                        if (enemies[i].collide("MINIGAMEPLAYER", enemies[i].x, enemies[i].y) != null) {
                            score -= 10;

                        }
                        if (time < 0) {
                            score += 3000;
                        }
                    }
                    break;
            }
        }

    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        super.render(container, game, g);

        g.setBackground(Color.black);
        //g.drawString("CASE: " + choice, 400, 10);
        Font f = new Font("Times New Roman", 1, 20) {
        };
        g.setColor(Color.green);
        g.drawString("Overall time left: " + overAllTime / 1000 + "s", 50, 150);
        switch (choice) {
            case 1:
                g.setBackground(Color.gray);
                g.drawString("Overall time left: " + overAllTime / 1000 + "s", 50, 150);
                g.setColor(Color.green);
                g.drawString("Time Left: " + time / 1000 + "s", 50, 50);
                g.drawGradientLine(0, 100, Color.red, 800, 100, Color.blue);
                g.setColor(Color.blue);
                g.drawString("Score: " + score, 50, 10);
                if (time >= 8000) {
                    g.drawString("Cross the street! ", 300, 300);
                } else {
                    g.drawString("Cross the street! ", 450, 50);
                }
                break;
            case 2://Guitar Hero
                background.draw(0, 0);
                g.drawString("Overall time left: " + overAllTime / 1000 + "s", 50, 150);
                for (int i = 0; i < 4; i++) {
                    if (object[i] != null) {
                        object[i].render(container, g);
                    }

                }
                for (int i = 0; i < 4; i++) {
                    if (enemies[i] != null) {
                        enemies[i].render(container, g);
                    }
                }
                g.setColor(Color.red);
                g.drawString("Score: " + score, 50, 50);
                g.drawString("Time Left: " + time / 1000 + "s", 50, 100);
                g.setColor(Color.green);
                if (time >= 7500) {
                    g.drawString("Press A , S, D, or F, when the orb reaches the catcher. ", 250, 300);
                } else {
                    g.drawString("Press A , S, D, or F, when the orb reaches the catcher. ", 200, 50);
                }
                g.drawString("CATCHERS-->", 30, 500);
                g.drawString("A", 200, 570);
                g.drawString("S", 360, 570);
                g.drawString("D", 520, 570);
                g.drawString("F", 700, 570);


                break;
            case 3://pong
                g.drawString("Overall time left: " + overAllTime / 1000 + "s", 50, 150);
                g.setColor(Color.red);
                g.drawString("Time Left: " + time / 1000 + "s", 50, 50);
                g.drawString("Score: " + score, 50, 10);
                g.setColor(Color.green);
                if (time >= 8000) {
                    g.drawString("Score or keep up!", 300, 300);
                } else {
                    g.drawString("Score or keep up!", 450, 10);
                }
                g.drawGradientLine(0, 100, Color.red, 800, 100, Color.blue);
                break;
            case 4: //Spam Clicker
                background.draw(0, 0);
                g.drawString("Overall time left: " + overAllTime / 1000 + "s", 50, 150);
                g.setColor(Color.red);
                g.drawString("Score: " + score, 50, 50);
                g.drawString("Time Left: " + time / 1000 + "s", 50, 100);
                g.drawString("Mash! Space!!", 250, 320);
                break;
            case 5: //reaction time
                g.setColor(Color.green);
                g.drawString("Follow the instructions when they appear!", 200, 300);
                g.drawString("Score: " + score, 50, 50);
                g.drawString("Time Left: " + time / 1000 + "s", 50, 100);
                if (newTime >= time - 500 && newTime <= time) {
                    g.setBackground(Color.red);
                    g.drawString("Press ENTER!!!", 250, 350);
                } else {
                    g.setBackground(Color.black);
                }
                break;
            case 6:
                background.draw(0, 0);
                player.render(container, g);
                for (int i = 0; i < 10; i++) {
                    if (enemies[i] != null) {
                        enemies[i].render(container, g);
                    }
                }
                for (int i = 0; i < 5; i++) {
                    if (object[i] != null) {
                        object[i].render(container, g);
                    }
                }
                g.drawString("Overall time left: " + overAllTime / 1000 + "s", 50, 150);
                g.setColor(Color.red);
                g.drawString("Time Left: " + time / 1000 + "s", 50, 50);
                g.drawString("Score: " + score, 50, 10);
                g.setColor(Color.green);
                if (time >= 8000) {
                    g.drawString("Rotate the cannon with WASD and fire with space!", 250, 300);
                } else {
                    g.drawString("Rotate the cannon with WASD and fire with space!", 450, 10);
                }
                g.drawGradientLine(0, 100, Color.red, 800, 100, Color.blue);
                break;
            case 7: //lucky dice
                g.setColor(Color.red);
                g.drawString("Score: " + score, 50, 50);
                g.drawString("Time Left: " + time / 1000 + "s", 50, 100);
                g.drawString("Roll!!! See how unlucky you are ;D", 300, 150);
                g.drawString("1,2 and 3 = lose points | 4, 5 and 6 = gain points", 50, 200);
                g.drawString("Press enter to roll!", 50, 250);
                g.drawString("You rolled: " + roll1, 50, 300);
                break;
            case 8:
                g.setColor(Color.green);
                g.drawString("Follow the instructions when they appear!", 200, 300);
                g.drawString("Score: " + score, 50, 50);
                g.drawString("Time Left: " + time / 1000 + "s", 50, 100);
                if (newTime >= time - 500 && newTime <= time) {
                    g.setBackground(Color.blue);
                    g.drawString("Don't Press It!!!!!", 250, 350);
                } else {
                    g.setBackground(Color.black);
                }
                break;

            case 9:
                g.drawString("Overall time left: " + overAllTime / 1000 + "s", 50, 150);
                g.setColor(Color.red);
                g.drawString("Score: " + score, 50, 50);
                g.drawString("Time Left: " + time / 1000 + "s", 50, 100);
                g.drawString("Health ", 50, 170);
                g.setColor(Color.white);
                g.drawRect(50, 200, player.getHealth(), 20);
                g.fillRect(50, 200, player.getHealth(), 20);
                cursor.draw(container.getInput().getMouseX() - 14, container.getInput().getMouseY() - 15);
                if (time >= 8000) {
                    g.drawString("Use the mouse! LIVE!!!", 300, 300);
                } else {
                    g.drawString("Use the mouse! LIVE!!!", 450, 10);
                }
                break;
            case 10:

                g.drawString("Overall time left: " + overAllTime / 1000 + "s", 50, 150);
                g.setColor(Color.green);
                g.drawString("Time Left: " + time / 1000 + "s", 50, 50);
                g.drawGradientLine(0, 100, Color.red, 800, 100, Color.blue);
                g.setColor(Color.blue);
                g.drawString("Score: " + score, 50, 10);
                g.drawString("Points: " + roll2, 350, 200);
                g.setColor(Color.green);
                g.drawString("Get to: " + roll1 + "points!", 350, 250);
                g.drawString("Up to increase, Down to decrease!", 350, 300);
                break;
            case 11:
                background.draw(0, 0);
                player.render(container, g);
                for (int i = 0; i < 15; i++) {
                    if (enemies[i] != null) {
                        enemies[i].render(container, g);
                    }

                }
                for (int i = 0; i < 5; i++) {
                    if (object[i] != null) {
                        object[i].render(container, g);
                    }
                }
                g.drawString("Overall time left: " + overAllTime / 1000 + "s", 50, 150);
                g.setColor(Color.green);
                g.drawString("Time Left: " + time / 1000 + "s", 50, 50);
                g.drawGradientLine(0, 100, Color.red, 800, 100, Color.blue);
                g.setColor(Color.blue);
                g.drawString("Score: " + score, 50, 10);
                g.setColor(Color.red);
                if (time >= 8000) {
                    g.drawString("Don't let the aliens invade!!!", 280, 300);
                } else {
                    g.drawString("Don't let the aliens invade!!!", 500, 10);
                }
                break;
            case 12:
                //  background.draw(0, 0);
                player.render(container, g);
                for (int i = 0; i < 10; i++) {
                    enemies[i].render(container, g);
                }
                g.drawString("Overall time left: " + overAllTime / 1000 + "s", 50, 150);
                g.setColor(Color.green);
                g.drawString("Time Left: " + time / 1000 + "s", 50, 50);
                g.drawGradientLine(0, 100, Color.red, 800, 100, Color.blue);
                g.setColor(Color.blue);
                g.drawString("Score: " + score, 50, 10);
                g.setColor(Color.green);
                if (time >= 8000) {
                    g.drawString("Do what any asian drivers does best!", 250, 300);
                } else {
                    g.drawString("Do what any asian drivers does best!", 450, 10);
                }
                g.setColor(Color.red);
                break;
            case 13:
                background.draw(0, 0);
                player.render(container, g);
                for (int i = 0; i < 15; i++) {
                    enemies[i].render(container, g);
                }
                g.drawString("Overall time left: " + overAllTime / 1000 + "s", 50, 150);
                g.setColor(Color.green);
                g.drawString("Time Left: " + time / 1000 + "s", 50, 50);
                g.drawGradientLine(0, 100, Color.red, 800, 100, Color.blue);
                g.setColor(Color.blue);
                g.drawString("Score: " + score, 50, 10);
                g.setColor(Color.green);
                if (time >= 8000) {
                    g.drawString("Don't let them touch you!!", 280, 300);
                } else {
                    g.drawString("Don't let them touch you!!", 450, 10);
                }
                break;
            case 14:
                g.drawString("Overall time left: " + overAllTime / 1000 + "s", 50, 150);
                g.setColor(Color.green);
                g.drawString("Time Left: " + time / 1000 + "s", 50, 50);
                g.drawGradientLine(0, 100, Color.red, 800, 100, Color.blue);
                g.setColor(Color.blue);
                g.drawString("Score: " + score, 50, 10);
                g.setColor(Color.green);
                g.drawString("Catch em!", 450, 10);
                break;
            case 15:
                g.drawString("Overall time left: " + overAllTime / 1000 + "s", 50, 150);
                g.setColor(Color.blue);
                g.drawString("Score: " + score, 50, 50);
                g.drawString("Time Left: " + time / 1000 + "s", 50, 100);
                cursor.draw(container.getInput().getMouseX() - 14, container.getInput().getMouseY() - 15);
                g.setColor(Color.green);
                if (time > 2000) {
                    g.drawString("Use the mouse!", 300, 300);
                }
                g.drawString("Shoot the terrorists!", 450, 150);
                break;
            case 16:
                background.draw(0, 0);
                player.render(container, g);
                for (int i = 0; i < 30; i++) {
                    if (enemies[i] != null) {
                        enemies[i].render(container, g);
                    }

                }
                g.drawString("Overall time left: " + overAllTime / 1000 + "s", 50, 150);
                g.setColor(Color.green);
                g.drawString("Time Left: " + time / 1000 + "s", 50, 50);
                g.drawGradientLine(0, 100, Color.red, 800, 100, Color.blue);
                g.setColor(Color.blue);
                g.drawString("Score: " + score, 50, 10);
                g.setColor(Color.green);
                if (time >= 8000) {
                    g.drawString("Don't let them touch you!!", 280, 300);
                } else {
                    g.drawString("Don't let them touch you!!", 450, 10);
                }
                break;
        }

    }

	//pre: startX, startY, targetX, targetY
	//post: returns target angle
	//calculates and returns target angle
    public double getTargetAngle(float startX, float startY, float targetX, float targetY) {

        return (Math.toDegrees(Math.atan2(startY - targetY, startX - targetX)) - 90);
    }
}
