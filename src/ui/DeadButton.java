package ui;

import static utilz.Constants.UI.Buttons.*;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import utilz.LoadSave;

public class DeadButton {
    protected  int x, y, Index, width, height;
    private int xOffsetCenter = B_WIDTH/2;
    protected  Rectangle bounds;
    private BufferedImage img;
    private boolean mousePressed;

    public DeadButton(int x, int y, int w, int h, int Index){
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.Index = Index;
        loadImgs();
        createBounds();
    }

    private void createBounds() {
        bounds = new Rectangle(x - xOffsetCenter, y, B_WIDTH, B_HEIGHT);
    }

    private void loadImgs() {
        BufferedImage temp = LoadSave.GetAtlas(LoadSave.RESTART_BUTTON);

        img = temp.getSubimage(0, Index * B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
    }

    public void draw(Graphics g){
        g.drawImage(img, x - xOffsetCenter, y, B_WIDTH, B_HEIGHT, null);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void resetBools(){
        mousePressed = false;
    }

    public boolean isPressed() {
        return mousePressed;
    }
    public void setMousePressed(boolean mousePressed){
        this.mousePressed = mousePressed;
    }
}
