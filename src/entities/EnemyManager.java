package entities;

import gamestates.Playing;
import levels.Level;
import utilz.LoadSave;
import static utilz.Constants.Enemy.*;

import entities.AllEnemies.Crab;
import entities.AllEnemies.Worm;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.geom.Rectangle2D;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[][] EnemyArr;
    private ArrayList<Crab> crabbies = new ArrayList<>();
    private ArrayList<Worm> worms = new ArrayList<>();

    public EnemyManager(Playing playing){
        this.playing = playing;
        loadEnemyImg();
    }

    // เอาค่าของ enemy แต่ละชนิดที่อ่านได้จากภาพ level มาเก็บ
    public void loadEnemies(Level level) {
        crabbies = level.getCrabs();
        worms = level.getWorms();
    }

    // update animations
    public void update(int[][] lvlData, Player player){
        for(Crab c : crabbies){
            if (c.isActive()) {
                c.update(lvlData, player);
            }   
        }
        for(Worm w : worms){
            if(w.isActive()){
                w.update(lvlData, player);
            }    
        }
    }

    // draw crab and worm
    public void draw(Graphics g, int xLvlOffset){
        drawCrabs(g, xLvlOffset);
        drawWorms(g, xLvlOffset);
    }

    private void drawCrabs(Graphics g, int xLvlOffset) {
        for(Crab c : crabbies){
            if (c.isActive()) {
                g.drawImage(EnemyArr[c.getEnemyState()][c.getAniIndex()], 
                    (int)c.getHitbox().getX() - CRAB_DRAWOFFSET_X - xLvlOffset, 
                    (int)c.getHitbox().getY() - CRAB_DRAWOFFSET_Y,
                    WIDTH, HEIGHT, null);
                //c.drawHitbox(g, xLvlOffset);
                //c.drawAttackBox(g, xLvlOffset);
            } 
        }
    }

    private void drawWorms(Graphics g, int xLvlOffset) {
        for(Worm w : worms){
            if (w.isActive()) {
                g.drawImage(EnemyArr[w.getEnemyState()][w.getAniIndex()], 
                    (int)w.getHitbox().getX() - WORM_DRAWOFFSET_X - xLvlOffset + w.flipX(), 
                    (int)w.getHitbox().getY() - WORM_DRAWOFFSET_Y, 
                    WIDTH * w.flipW(), HEIGHT, null);
                //w.drawHitbox(g, xLvlOffset);
                //w.drawAttackBox(g, xLvlOffset);
            }
        }
    }

    // check ว่า enemy ถูกโจมตีไหม
    public void checkEnemyHit(Rectangle2D.Float attackBox){
        for(Crab c : crabbies){
            if (c.isActive()) {
                if (attackBox.intersects(c.getHitbox())) {
                    c.hurt(5);
                    return;
                }
            }  
        }
        for(Worm w : worms){
            if (w.isActive()) {
                if (attackBox.intersects(w.getHitbox())) {
                    w.hurt(5);
                    return;
                }
            } 
        }
    }

    // load รูปภาพเก็บใน Array
    private void loadEnemyImg() {
        EnemyArr = new BufferedImage[10][3];
        BufferedImage temp = LoadSave.GetAtlas(LoadSave.ENEMIES_ATLAS);

        for (int i = 0; i < EnemyArr.length; i++) {
            for (int j = 0; j < EnemyArr[i].length; j++) {
                EnemyArr[i][j] = temp.getSubimage(j * WIDTH_DEFAULT, i * HEIGHT_DEFAULT, WIDTH_DEFAULT, HEIGHT_DEFAULT);
            }
        }
    }

    // reset game
    public void resetAllEnemies() {
        for(Crab c: crabbies){
            c.resetEnemy();
        }
        for(Worm w: worms){
            w.resetEnemy();
        }
    }
}
