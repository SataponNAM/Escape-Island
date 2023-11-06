package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utilz.LoadSave;
import static utilz.Constants.UI.URMButton.*;

public class UrmButton extends PauseButton{
    private BufferedImage[] imgs; 
    private int rowIndex, index;
    private boolean mousePressed;

    public UrmButton(int x, int y, int w, int h, int rowIndex) {
        super(x, y, w, h);
        this.rowIndex = rowIndex;
        loadImgs();
    }

    private void loadImgs() {
        BufferedImage temp = LoadSave.GetAtlas(LoadSave.URM_BUTTONS);
        imgs = new BufferedImage[2];

        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = temp.getSubimage(i*URM_DEFAULT_SIZE, rowIndex * URM_DEFAULT_SIZE, URM_DEFAULT_SIZE, URM_DEFAULT_SIZE);
        }
    }

    public void update(){
        index = 0;
        if (mousePressed) {
            index = 1;
        }
    }

    // draw button
    public void draw(Graphics g){
        g.drawImage(imgs[index], x, y, URM_SIZE, URM_SIZE, null);
    }

    public void resetBools(){
        mousePressed = false;
    }

    public boolean isPressed(){
        return mousePressed;
    }
    public void setMousePressed(boolean mousePressed){
        this.mousePressed = mousePressed;
    }
    
}
