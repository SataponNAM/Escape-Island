package objects;

import main.Game;

public class Spike extends GameObject{

    public Spike(int x, int y, int objType){
        super(x, y, objType);
        initHitbox(32, 14);
        xDrawOffset = 0;
        yDrawOffset = (int) (Game.SCALE * 14);
        hitbox.y += yDrawOffset;
    }
}
