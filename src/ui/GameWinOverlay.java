package ui;

import static utilz.Constants.UI.Buttons.B_HEIGHT;
import static utilz.Constants.UI.Buttons.B_WIDTH;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.event.MouseEvent;

import gamestates.GameState;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

public class GameWinOverlay {
    private Playing playing;
    private BufferedImage backgroundImg;
    private int bgX, bgY, bgW, bgH;
    private DeadButton menuB;

    public GameWinOverlay(Playing playing){
        this.playing = playing;

        loadBackground();
        createButtons();
    }

    private void createButtons() {
        int menuX = (int) (417 * Game.SCALE);
        int mby = 450;

        menuB = new DeadButton(menuX, mby, B_WIDTH, B_HEIGHT,1);
    }

    private void loadBackground() {
        backgroundImg = LoadSave.GetAtlas(LoadSave.GAMEWIN_BG);

        bgW = (int) (backgroundImg.getWidth() * Game.SCALE);
        bgH = (int) (backgroundImg.getHeight() * Game.SCALE);
        bgX = Game.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (100 * Game.SCALE);
    }

    public void draw(Graphics g){
        g.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null);

        menuB.draw(g);
    }

    private boolean isIn(MouseEvent e ,DeadButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}

    public void mousePressed(MouseEvent e) {
        if (isIn(e,menuB)) {
            menuB.setMousePressed(true);
            
        }
    }

    public void mouseReleased(MouseEvent e) {
        if(isIn(e,menuB)){
            if (menuB.isPressed()) {
                playing.resetAll();
                GameState.state = GameState.MENU;
            }
        }

        menuB.resetBools();
    }

    public void keyPressed(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            playing.resetAll();
            GameState.state = GameState.MENU;
        }
    }
}
