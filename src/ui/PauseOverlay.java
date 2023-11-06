package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.event.MouseEvent;

import gamestates.GameState;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;
import static utilz.Constants.UI.URMButton.*;

public class PauseOverlay {
    private Playing playing;
    private BufferedImage backgroundImg;
    private int bgX, bgY, bgW, bgH;
    private UrmButton menuB, unpauseB; 
    
    public PauseOverlay(Playing playing){
        this.playing = playing;

        loadBackground();
        createUrmButtons();
    }

    private void createUrmButtons() {
        int menuX = (int) (300*Game.SCALE);
        int unpauseX = (int) (440*Game.SCALE);
        int by = 390;

        menuB = new UrmButton(menuX, by, URM_SIZE, URM_SIZE, 0);
        unpauseB = new UrmButton(unpauseX, by, URM_SIZE, URM_SIZE, 1);
    }

    private void loadBackground() {
        backgroundImg = LoadSave.GetAtlas(LoadSave.PAUSE_BACKGROUND);
        bgW = (int) (backgroundImg.getWidth() * 3f);
        bgH = (int) (backgroundImg.getHeight() * 3f);
        bgX = Game.GAME_WIDTH / 2 - bgW / 2;
        bgY = 150;
    }

    public void update(){
        menuB.update();
        unpauseB.update();
    }

    public void draw(Graphics g){
        g.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null); // draw background 

        // draw button
        menuB.draw(g);
        unpauseB.draw(g);
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(e,menuB)) {
            menuB.setMousePressed(true);
            
        } else if (isIn(e,unpauseB)) {
            unpauseB.setMousePressed(true);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if(isIn(e,menuB)){
            if (menuB.isPressed()) {
                GameState.state = GameState.MENU;
                playing.resetAll();
            }
        } else if(isIn(e,unpauseB)){
            if (unpauseB.isPressed()) {
                playing.unpauseGame();
            }
        }

        menuB.resetBools();
        unpauseB.resetBools();
    }

    private boolean isIn(MouseEvent e, PauseButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}

}
