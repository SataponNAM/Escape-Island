package ui;

import java.awt.Rectangle;

public class PauseButton {
    
    protected  int x, y, width, height;
    protected  Rectangle bounds;

    public PauseButton(int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        createBounds();
    }

    private void createBounds() {
        bounds = new Rectangle(x, y, width, height);
    }

    public void setX(int x){
        this.x = x;
    }
    public int getX(){
        return x;
    }
    public void setY(int y){
        this.y = y;
    }
    public int getY(){
        return y;
    }
    public void setWidth(int w){
        this.width = w;
    }
    public int getWidth(){
        return width;
    }
    public void setHeight(int h){
        this.height = h;
    }
    public int getHeight(){
        return height;
    }
    public void setBounds(Rectangle bounds){
        this.bounds = bounds;
    }
    public Rectangle getBounds(){
        return bounds;
    }
}
