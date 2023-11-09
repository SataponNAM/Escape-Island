package levels;

import main.Game;
import utilz.LoadSave;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LevelManager {
    private Game game;
    private BufferedImage[] levelSprite;
    private ArrayList<Level> levels;
    private int levelIndex = 0;
    
    public LevelManager(Game game){
        this.game = game;

        importTileSprite();
        levels = new ArrayList<>();
        buildAllLevels();
    }

    // load next level
    public void loadNextLevel() {    
        levelIndex++;
        // ถ้าผ่านด่านสุดท้าย
        if(levelIndex >= levels.size()){ 
            levelIndex = 0; 
            System.out.println("WIN Game Complete");
            // show win game UI
            game.getPlaying().setGameWin(true);
        }  

        // load new level
        Level newLevel = levels.get(levelIndex);
        game.getPlaying().getEnemyManager().loadEnemies(newLevel);
        game.getPlaying().getPlayer().loadlvlData(newLevel.getlvlData());
        game.getPlaying().setMaxLvlOffset(newLevel.getlvlOffset());
        game.getPlaying().getObjectManager().loadObject(newLevel); 
    }

    // สร้าง levels ทั้งหมด โดยเพิ่มเข้าไปใน ArrayList levels
    private void buildAllLevels() {
        BufferedImage[] allLevels = LoadSave.GetAllLevels();

        for (BufferedImage img : allLevels) {
            levels.add(new Level(img));
        }
    }

    // load tiles
    private void importTileSprite() {
        BufferedImage img = LoadSave.GetAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[30];
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 10; i++) {
                int index = j*10 + i;
                levelSprite[index] = img.getSubimage(i*32, j*32, 32, 32);
            }
        }
    }

    // draw tile in each level
    public void draw(Graphics g, int xLvlOffset){
        for(int j = 0; j < Game.TILES_IN_HEIGHT; j++){
            for (int i = 0; i < levels.get(levelIndex).getlvlData()[0].length; i++) {
                int index = levels.get(levelIndex).getSpriteIndex(i, j);
                g.drawImage(levelSprite[index], i * Game.TILES_SIZE - xLvlOffset, j * Game.TILES_SIZE, Game.TILES_SIZE, Game.TILES_SIZE, null);
            }
        }
        
    }

    // level ปัจจุบัน
    public Level getcurrentLevel(){
        return levels.get(levelIndex);
    }

    // get level ทั้งหมด
    public int getAmountOfLevels(){
        return levels.size();
    }

}
