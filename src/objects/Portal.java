package objects;

public class Portal extends GameObject{

    public Portal(int x, int y, int objType) {
        super(x, y, objType);
        initHitbox(32, 32);
        xDrawOffset = 0;
        yDrawOffset = 0;
    }
    
}
