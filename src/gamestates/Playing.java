package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import main.Game;
import objects.ObjectManager;
import ui.GameOverOverlay;
import ui.GameWinOverlay;
import ui.PauseOverlay;
import utilz.LoadSave;

public class Playing extends State implements StateMethods{
    private Player player;
	private LevelManager levelManager;
    private EnemyManager enemymanager;
    private ObjectManager objectManager;

    private PauseOverlay pauseOverlay;
    private GameOverOverlay gameOverOverlay;
    private GameWinOverlay gameWinOverlay;

    private boolean paused = false; // Check paused
    private boolean gameOver; // check game over
    private boolean gameWin; // check game win

    // Bigger Lvl
    private int xLvlOffset;
    private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
    private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
    private int maxLvlOffsetX;

    // Background
    private BufferedImage backgroundImg;

    public Playing(Game game) {
        super(game);
        initClasses();

        backgroundImg = LoadSave.GetAtlas(LoadSave.PLAYING_BACKGROUND);

        calLevelOffset();
        loadStartLevel();
    }

    private void initClasses() {
		levelManager = new LevelManager(game);
        enemymanager = new EnemyManager(this);;
        objectManager = new ObjectManager(this);

		player = new Player(200, 200, (int) (64 * Game.SCALE), (int) (40 * Game.SCALE), this);
		player.loadlvlData(levelManager.getcurrentLevel().getlvlData());
        player.setSpawn(levelManager.getcurrentLevel().getPlayerSpawn());

        pauseOverlay = new PauseOverlay(this);
        gameOverOverlay = new GameOverOverlay(this);
        gameWinOverlay = new GameWinOverlay(this);
	}

    public void loadNextLevel(){
        resetAll();
        levelManager.loadNextLevel();
        player.setSpawn(levelManager.getcurrentLevel().getPlayerSpawn());
    }

    private void loadStartLevel() {
        enemymanager.loadEnemies(levelManager.getcurrentLevel());
        objectManager.loadObject(levelManager.getcurrentLevel());
    }

    private void calLevelOffset() {
        maxLvlOffsetX = levelManager.getcurrentLevel().getlvlOffset();
    }

    @Override
    public void update() {
        if (!paused && !gameOver) {
            player.update();
            enemymanager.update(levelManager.getcurrentLevel().getlvlData(), player);
            checkCloseToBorder();
        } else if(paused){
            pauseOverlay.update();
        } 
    }

    // เช็คซ้าย ขวา สำหรับ load map เวลาเดิน
    private void checkCloseToBorder() {
        int playerX = (int) player.getHitbox().getX();
        int diff = playerX - xLvlOffset;

        if (diff > rightBorder) {
            xLvlOffset += diff - rightBorder;
        } else if(diff < leftBorder){
            xLvlOffset += diff - leftBorder;
        }

        if (xLvlOffset > maxLvlOffsetX) {
            xLvlOffset = maxLvlOffsetX;
        } else if(xLvlOffset < 0){
            xLvlOffset = 0;
        }
    }
    
    // draw playing window
    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);

        levelManager.draw(g, xLvlOffset);
        objectManager.draw(g, xLvlOffset);
        player.render(g, xLvlOffset);
        enemymanager.draw(g, xLvlOffset);

        if (paused) {
            g.setColor(new Color(0 ,0 , 0, 100));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pauseOverlay.draw(g);
        } else if(gameOver){
            g.setColor(new Color(0 ,0 , 0, 100));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            gameOverOverlay.draw(g);
        } 
        else if(gameWin){
            g.setColor(new Color(0 ,0 , 0, 100));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            gameWinOverlay.draw(g);
        }
    }

    // reset game
    public void resetAll(){
        gameOver = false;
        gameWin = false;
        paused = false;
        player.resetAll();
        enemymanager.resetAllEnemies();
    }

    public void setGameOver(boolean gameOver){
        this.gameOver = gameOver;
    }

    public void setGameWin(boolean gameWin){
        this.gameWin = gameWin;
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox){
        enemymanager.checkEnemyHit(attackBox);
    }

    public void checkPortalTouch(Player p){
        objectManager.checkPortalTouch(p);
    }

    public void checkSpikeTouch(Player p){
        objectManager.checkSpikeTouch(p);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!gameOver) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                player.setAttacking(true);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(!gameOver){
            if (paused) {
                pauseOverlay.mousePressed(e);
            }else if(gameWin){
                gameWinOverlay.mousePressed(e);
            }
        } else {
            gameOverOverlay.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(!gameOver){
            if (paused) {
                pauseOverlay.mouseReleased(e);
            }else if(gameWin){
                gameWinOverlay.mouseReleased(e);
            }
        } else {
            gameOverOverlay.mouseReleased(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver) {
            gameOverOverlay.keyPressed(e);
        } else if(gameWin){
            gameWinOverlay.keyPressed(e);
        }else {
            switch(e.getKeyCode()){
                case KeyEvent.VK_A: 
                    player.setLeft(true);
                    break; 
                case KeyEvent.VK_D:
                    player.setRight(true);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(true);
                    break;
                case KeyEvent.VK_ESCAPE:
                    paused = !paused;
                    break;
            }
        } 
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(!gameOver && !gameWin){
            switch(e.getKeyCode()){
                case KeyEvent.VK_A: 
                    player.setLeft(false);
                    break; 
                case KeyEvent.VK_D:
                    player.setRight(false);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(false);
                    break;
            }
        }
    }

    public void unpauseGame(){
        paused = false;
    }

    public void windowFocusLost() {
		player.resetDirBooleans();
	}

    public void setMaxLvlOffset(int lvlOffset){
        this.maxLvlOffsetX = lvlOffset;
    }

	public Player getPlayer(){
		return player;
	}

    public EnemyManager getEnemyManager(){
        return enemymanager;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public ObjectManager getObjectManager(){
        return objectManager;
    }

}
