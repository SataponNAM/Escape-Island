package entities.AllEnemies;

import static utilz.Constants.Enemy.*;
import static utilz.Constants.Directions.*;

import entities.Enemy;
import entities.Player;
import main.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public class Worm extends Enemy{
    public static final int IDLE = 5;
    public static final int RUN = 6;
    public static final int ATTACK = 7;
    public static final int HIT = 8;
    public static final int DEAD = 9;

    public static final int WORM_DRAWOFFSET_X = (int) (6 * Game.SCALE);
    public static final int WORM_DRAWOFFSET_Y = (int) (12 * Game.SCALE);

    // Attack Box
    private Rectangle2D.Float attackBox;
    private int attackBoxOffsetX;

    public Worm(float x, float y) {
        super(x, y, WIDTH, HEIGHT, WORM, WORM);
        initHitbox(x, y, (int) (62 * Game.SCALE), (int) (16 * Game.SCALE));
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x ,y , (int) (82 * Game.SCALE) ,(int) (16 * Game.SCALE));
        attackBoxOffsetX = (int) (Game.SCALE * 10);
    }

    public void update(int[][] lvlData, Player player){
        updateBehavior(lvlData, player);
        updateAniTick();
        updateAttackBox();
    }

    private void updateAttackBox() {
        attackBox.x = hitbox.x - attackBoxOffsetX;
        attackBox.y = hitbox.y;
    }

    private void updateBehavior(int[][] lvlData, Player player){
        if(firstUpdate){
            firstupdateCheck(lvlData);
        }

        if(inAir){
            updateInAir(lvlData);
        } else {
            switch (enemyState) {
                case IDLE:
                    newState(RUN);
                    break;
                case RUN:
                    if (canSeePlayer(lvlData, player)) {
                        moveToPLayer(player);
                        if (IsPlayerCloseForAttack(player)) {
                            newState(ATTACK);
                        }
                    }

                    move(lvlData);
                    break;
                    case ATTACK:
                    if (aniIndex == 0)
                        attackCheck = false;
                        
                    if (aniIndex == 1 && !attackCheck) {
                        checkPlayerHit(attackBox,player);
                    } else {
                        attackCheck = false;
                    }
                    break;
                case HIT:
                    break;
            }
        }
    }

    public void drawAttackBox(Graphics g, int xLvlOffset){
        g.setColor(Color.red);
        g.drawRect((int) (attackBox.x - xLvlOffset), (int)attackBox.y, (int)attackBox.width, (int)attackBox.height);
    }

    // flip img
    public int flipX(){
        if (walkDir == RIGHT) {
            return width;
        } else {
            return 0;
        }
    }
    public int flipW(){
        if(walkDir == RIGHT){
            return -1;
        } else {
            return 1;
        }
    }

    // ถูกโจมตี
    public void hurt(int amount){
        currentHealth -= amount;
        if (currentHealth <= 0) {
            newState(DEAD);
        } else {
            newState(HIT);
        }
    }

    // reset game
    public void resetEnemy() {
        hitbox.x = x;
        hitbox.y = y;
        firstUpdate = true;
        currentHealth = maxHealth;
        newState(IDLE);
        active = true;
        fallSpeed = 0;
    }
    
}
