package entities.AllEnemies;

import static utilz.Constants.Enemy.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import entities.Enemy;
import entities.Player;
import main.Game;

public class Crab extends Enemy{
    public static final int IDLE = 0;
    public static final int ATTACK = 1;
    public static final int RUN = 2;
    public static final int HIT = 3;
    public static final int DEAD = 4;

    // Attack Box
    private Rectangle2D.Float attackBox;
    private int attackBoxOffsetX;

    public Crab(float x, float y) {
        super(x, y, WIDTH, HEIGHT, CRAB, CRAB);
        initHitbox(x,y, (int) (31 * Game.SCALE), (int) (19 * Game.SCALE));
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x ,y , (int) (71 * Game.SCALE) ,(int) (19 * Game.SCALE));
        attackBoxOffsetX = (int) (Game.SCALE * 20);
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
