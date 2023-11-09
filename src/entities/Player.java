package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

import static utilz.Constants.Playerconstants.*;
import static utilz.HelpMethod.*;

public class Player extends Entity {
    private Playing playing;

    private BufferedImage img;
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 20;
    private int playerAction = IDLE;
    private boolean moving = false;
    private boolean attacking = false;
    private boolean left, right, jump;
    private float playerSpeed = 2.0f;
    private int[][] lvlData;
    private float xDrawoffset = 20 * Game.SCALE;
    private float yDrawoffset = 4 * Game.SCALE;

    // Jump Gravity
    private float airSpeed = 0f;
	private float gravity = 0.04f * Game.SCALE;
	private float jumpSpeed = -2.25f * Game.SCALE;
	private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
	private boolean inAir = false;

    // status Bar UI
    private BufferedImage statusBarImg;

    private int statusBarWidth = (int) (192 * Game.SCALE);
    private int statusBarHeight = (int) (33 * Game.SCALE);
    private int statusBarX = (int) (10 * Game.SCALE);
    private int statusBarY = (int) (10 * Game.SCALE);

    private int healthBarWidth = (int) (156 * Game.SCALE);
    private int healthBarHeight = (int) (4 * Game.SCALE);
    private int healthBarXStart = (int) (33 * Game.SCALE);
    private int healthBarYStart = (int) (15 * Game.SCALE);

    private int maxHealth = 100;
    private int currentHealth = maxHealth; // เลือดที่เหลือ
    private int healthWidth = healthBarWidth;

    // Attacak Box
    private Rectangle2D.Float attackBox;

    // flip img
    private int flipX = 0;
    private int flipW = 1;

    // attack
    private boolean attackCheck;

    public Player(float x, float y ,int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        loadAnimations();
        initHitbox(x, y, (int)(24 * Game.SCALE), (int)(30 * Game.SCALE));
        initAttackBox();
    }

    // set จุดเกิด
    public void setSpawn(Point spawn){
        this.x = spawn.x;
        this.y = spawn.y;
        hitbox.x = x;
        hitbox.y = y;
    }
    
    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int)(20 * Game.SCALE), (int)(20 * Game.SCALE));
    }

    public void update(){
        updateHealth();

        // เลือดหมดจะจบเกม
        if (currentHealth <= 0) {
            playing.setGameOver(true);
            return;
        }

        updateAttackBox();
        updatePos();

        if (moving) {
            checkPortalTouch();
            checkSpikeTouch();
        }

        if (attacking) {
            checkAttack();
        }
        updateAnimationTick();
        setAnimation();
    }

    // check ว่าโดน spike ไหม
    private void checkSpikeTouch() {
        playing.checkSpikeTouch(this);
    }

    // check player touch portal
    private void checkPortalTouch() {
        playing.checkPortalTouch(this);
    }

    // check ว่า player โจมตี
    private void checkAttack() {
        if (attackCheck || aniIndex != 0) {
            return;
        }
        attackCheck = true;
        playing.checkEnemyHit(attackBox);
    }

    private void updateAttackBox() {
        if(right) {
            attackBox.x = hitbox.x + hitbox.width + (int)(Game.SCALE * 5);
        } else if(left) {
            attackBox.x = hitbox.x - hitbox.width - (int)(Game.SCALE * 5);
        }
        attackBox.y = hitbox.y + (int) (Game.SCALE * 10);
    }

    // update หลอดเลือด
    private void updateHealth() {
        healthWidth = (int) ((currentHealth / (float) maxHealth) * healthBarWidth);
    }

    // วาด
    public void render(Graphics g, int xLvlOffset){
        g.drawImage(animations[playerAction][aniIndex], 
            (int)(hitbox.x - xDrawoffset) - xLvlOffset + flipX, 
            (int)(hitbox.y - yDrawoffset), 
            width * flipW, height,null);

        // draw health bar
        drawUI(g);
    }
    
    // debug attack box
    private void drawAttackBox(Graphics g, int xLvlOffset) {
        g.setColor(Color.RED);
        g.drawRect((int)attackBox.x - xLvlOffset, (int)attackBox.y, (int)attackBox.width, (int)attackBox.height);
    }

    // draw status bar
    private void drawUI(Graphics g) {
        g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight ,null);
        g.setColor(Color.RED);
        g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpiriteAmount(playerAction)) {
                aniIndex = 0;
                attacking = false;
                attackCheck = false;
            }
        }
    }

    private void setAnimation() {
        int startAni = playerAction;

        if (moving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        } 

        if(attacking){
            playerAction = ATTACK;
        }

        if(startAni != playerAction){
            resetAnitickk();
        }
    }

    private void resetAnitickk() {
        aniTick = 0;
        aniIndex = 0;
    }

    private void updatePos() {
		moving = false;

		if (jump)
			jump();
        
        if (!inAir) {
            if ((!left && !right) || (right && left)){
			    return;
            } 
        }

		float xSpeed = 0;

		if (left){ // เดินซ้าย
			xSpeed -= playerSpeed;
            flipX = width;
            flipW = -1; // กลับด้าน
        }
		if (right){ // เดินขวา
			xSpeed += playerSpeed;
            flipX = 0;
            flipW = 1; // กลับด้าน
        }

		if (!inAir){
			if (!IsEntityOnFloor(hitbox, lvlData)){
				inAir = true;
            }
        }

		if (inAir) {
			if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
				hitbox.y += airSpeed;
				airSpeed += gravity;
				updateXPos(xSpeed);
			} else {
				hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
				if (airSpeed > 0)
					resetInAir();
				else{
					airSpeed = fallSpeedAfterCollision;
                }
				updateXPos(xSpeed);
			}

		} else{
			updateXPos(xSpeed);
        }
		moving = true;
	}

    private void jump() {
        if (inAir) {
            return;
        }
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    // update x ตอนเดิน
    private void updateXPos(float xSpeed) {
        if (CanMoveHere(hitbox.x+xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
            hitbox.x += xSpeed;
        } else {
            hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
        }
    }

    // update เลือด
    protected void changeHealth(int value){
        currentHealth += value;

        if(currentHealth <= 0){
            currentHealth = 0;
            // gameOver();
        } else if(currentHealth >= maxHealth){
            currentHealth = maxHealth;
        }
    }

    // dead when touch trap
    public void kill() {
        currentHealth = 0;
    }

    // load Img
    private void loadAnimations() {
        img = LoadSave.GetAtlas(LoadSave.PLAYET_ATLAS);

        animations = new BufferedImage[4][4];
        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = img.getSubimage(j*64, i*40, 64, 40);
            }
        }	

        statusBarImg = LoadSave.GetAtlas(LoadSave.STATUS_BAR);
    }

    // load level
    public void loadlvlData(int[][] lvlData){
        this.lvlData = lvlData;
        if(!IsEntityOnFloor(hitbox, lvlData)){
            inAir = true;
        }
    }

    public void resetDirBooleans(){
        left = false;
		right = false;
    }

    public void setAttacking(boolean attacking){
        this.attacking = attacking;
    }

    public boolean isLeft(){
        return left;
    }

    public void setLeft(boolean left){
        this.left = left;
    }

    public boolean isRight(){
        return right;
    }

    public void setRight(boolean right){
        this.right = right;
    }
    
    public void setJump(boolean jump){
        this.jump = jump;
    }

    // reset game
    public void resetAll() {
        resetDirBooleans();
        inAir = false;
        attacking = false;
        moving = false;
        airSpeed = 0f;
        jump = false;
        playerAction = IDLE;
        currentHealth = maxHealth;

        hitbox.x = x;
        hitbox.y = y;

        if(!IsEntityOnFloor(hitbox, lvlData)){
            inAir = true;
        }
    }

}
