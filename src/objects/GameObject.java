package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.Game;

public class GameObject {
    protected int x,y;
    protected int objType;
    protected boolean active = true;
    protected Rectangle2D.Float hitbox;
    protected int xDrawOffset, yDrawOffset;

    public GameObject(int x, int y, int objType){
        this.x = x;
        this.y = y;
        this.objType = objType;
    }

    protected void drawHitbox(Graphics g, int xLvlOffset){
        // Debugging
        g.setColor(Color.BLUE);
        g.drawRect((int) (hitbox.x - xLvlOffset), (int)hitbox.y, (int) (hitbox.width * Game.SCALE), (int) (hitbox.height * Game.SCALE));
    }

    protected void initHitbox(int width ,int height) {
        hitbox =  new Rectangle2D.Float(x, y, width, height);
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle2D.Float hitbox) {
        this.hitbox = hitbox;
    }

    public boolean isActive() {
		return active;
	}
}
