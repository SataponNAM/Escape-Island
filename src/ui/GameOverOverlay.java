package ui;

import static utilz.Constants.UI.Buttons.*;


import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.GameState;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

public class GameOverOverlay {
    private Playing playing;
    private BufferedImage backgroundImg;
    private int bgX, bgY, bgW, bgH;
    private DeadButton restartB, menuB;

    public GameOverOverlay(Playing playing){
        this.playing = playing;

        loadBackground();
        createButtons();
    }

    private void createButtons() {
        int restartX = (int) (417 * Game.SCALE);
        int rby = 390;
        int mby = 500;

        restartB = new DeadButton(restartX, rby, B_WIDTH, B_HEIGHT,0);
        menuB = new DeadButton(restartX, mby, B_WIDTH, B_HEIGHT,1);
    }

    private void loadBackground() {
        backgroundImg = LoadSave.GetAtlas(LoadSave.DEAD_BG);
        bgW = (int) (backgroundImg.getWidth() * Game.SCALE);
        bgH = (int) (backgroundImg.getHeight() * Game.SCALE);
        bgX = Game.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (100 * Game.SCALE);
    }
    

    public void draw(Graphics g){
        g.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null);

        restartB.draw(g);
        menuB.draw(g);
    }

    public void keyPressed(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            playing.resetAll();
            GameState.state = GameState.MENU;
        }
    }

    private boolean isIn(MouseEvent e ,DeadButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}

    public void mousePressed(MouseEvent e) {
        if (isIn(e,menuB)) {
            menuB.setMousePressed(true);
            
        } else if (isIn(e,restartB)) {
            restartB.setMousePressed(true);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if(isIn(e,menuB)){
            if (menuB.isPressed()) {
                playing.resetAll();
                GameState.state = GameState.MENU;
            }
        } else if(isIn(e,restartB)){
            if (restartB.isPressed()) {
                playing.resetAll();
            }
        }

        menuB.resetBools();
        restartB.resetBools();
    }
}
