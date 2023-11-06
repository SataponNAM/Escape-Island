package entities;

import static utilz.Constants.Enemy.*;
import static utilz.Constants.Directions.*;
import static utilz.HelpMethod.*;

import java.awt.geom.Rectangle2D;

import entities.AllEnemies.Crab;
import entities.AllEnemies.Worm;

import main.Game;

public class Enemy extends Entity{
    protected int aniIndex, enemyState, enemyType;
    protected int aniTick, aniSpeed = 30;
    protected boolean firstUpdate = true;
    protected boolean inAir;
    protected float fallSpeed;
    protected float gravity = 0.04f * Game.SCALE;
    protected float walkSpeed = 0.5f * Game.SCALE;
    protected int walkDir = LEFT;
    protected int tileY;
    protected float attackDistance = Game.TILES_SIZE;
    protected int maxHealth, currentHealth;
    protected boolean active = true;
    protected boolean attackCheck;

    public Enemy(float x, float y, int width, int height, int enemyType, int enemyState) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        this.enemyState = enemyState;
        initHitbox(x, y, width, height);
        maxHealth = GetMaxHealth(enemyType);
        currentHealth = maxHealth;
    }

    protected void firstupdateCheck(int[][] lvlData){
        if(!IsEntityOnFloor(hitbox, lvlData)){
            inAir = true;
        }
        firstUpdate = false;
    }

    // เคลื่อนที่
    protected void move(int[][] lvlData){
        float xSpeed = 0;

        if(walkDir == LEFT){
            xSpeed = -walkSpeed;
        } else {
            xSpeed = walkSpeed;
        }

        // เดิน
        if(CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)){
            if(IsFloor(hitbox, xSpeed, lvlData)){
                hitbox.x += xSpeed;
                return;
            }
        }
        changeWalkDir();
    }

    // เดินเข้าหา player ถ้าอยู่ในระยะมองเห็น
    public void moveToPLayer(Player player){
        if (player.hitbox.x > hitbox.x) {
            walkDir = RIGHT;
        } else {
            walkDir = LEFT;
        }
    }

    // check ว่า player อยู่ในระยะมองเห็นของ enemy ไหม
    protected boolean canSeePlayer(int[][] lvlData, Player player){
        int playerTileY = (int) (player.getHitbox().getY() / Game.TILES_SIZE);

        if (playerTileY == tileY) {
            if (isPLayerInRange(player)) {
                if(isSightClear(lvlData, hitbox, player.hitbox, tileY)){
                    return true;
                }
            }
        }

        return false;
    }

    // player in see range
    private boolean isPLayerInRange(Player player) {
        int absValue = (int) (Math.abs(player.hitbox.x - hitbox.x));
        return absValue <= attackDistance * 5; // range can see 5 tile
    }

    // player in attack range
    protected boolean IsPlayerCloseForAttack(Player player){
        int absValue = (int) (Math.abs(player.hitbox.x - hitbox.x));
        return absValue <= attackDistance; // range can attack 1 tile
    }

    protected void newState(int enemyState){
        this.enemyState = enemyState;
        aniTick = 0;
        aniIndex = 0;
    }

    // ถ้า player อยู่ในระยะโจมตี enemy จะโจมตี
    protected void checkPlayerHit(Rectangle2D.Float attackBox ,Player player) {
        if (attackBox.intersects(player.hitbox)) {
            player.changeHealth(-GetEnemyDamag(enemyType));
        }
        attackCheck = true;
    }

    protected void updateInAir(int[][] lvlData){
        if(CanMoveHere(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, lvlData)){
            hitbox.y += fallSpeed;
            fallSpeed += gravity;
        } else {
            inAir = false;
            hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, fallSpeed);
            tileY = (int) (hitbox.y / Game.TILES_SIZE);
        }
    }

    // update animations
    protected void updateAniTick(){
        aniTick++;
        if(aniTick >= aniSpeed){
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= GetSpiriteAmount(enemyType, enemyState)){
                aniIndex = 0;

                switch (enemyType) {
                    case CRAB:
                        switch (enemyState) {
                            case Crab.ATTACK:
                                enemyState = Crab.IDLE;
                                break;
                            case Crab.HIT:
                                enemyState = Crab.IDLE;
                            case Crab.DEAD:
                                active = false;
                        }
                        break;
                    case WORM:
                        switch (enemyState) {
                            case Worm.ATTACK:
                                enemyState = Worm.IDLE;
                                break;
                            case Worm.HIT:
                                enemyState = Worm.IDLE;
                            case Worm.DEAD:
                                active = false;
                        }
                        break;
                }
            }
        }
    }

    protected void changeWalkDir() {
        if(walkDir == LEFT){
            walkDir = RIGHT;
        } else {
            walkDir = LEFT;
        }
    }

    // getter
    public int getAniIndex(){
        return aniIndex;
    }

    public int getEnemyState(){
        return enemyState;
    }

    public boolean isActive(){
        return active;
    }
    
}
