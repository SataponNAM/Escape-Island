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

    public void loadNextLevel() {    
        levelIndex++;
        if(levelIndex >= levels.size()){ // - 1
            levelIndex = 0; // levels.size()-1
            System.out.println("WIN Game Complete");
            // show win game UI
            game.getPlaying().setGameWin(true);
        }  

        Level newLevel = levels.get(levelIndex);
        game.getPlaying().getEnemyManager().loadEnemies(newLevel);
        game.getPlaying().getPlayer().loadlvlData(newLevel.getlvlData());
        game.getPlaying().setMaxLvlOffset(newLevel.getlvlOffset());
        game.getPlaying().getObjectManager().loadObject(newLevel); 
    }

    private void buildAllLevels() {
        BufferedImage[] allLevels = LoadSave.GetAllLevels();

        for (BufferedImage img : allLevels) {
            levels.add(new Level(img));
        }
    }

    // load tile
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

    // draw tile in level
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

    public int getAmountOfLevels(){
        return levels.size();
    }

}
