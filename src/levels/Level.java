package levels;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.AllEnemies.Crab;
import entities.AllEnemies.Worm;
import main.Game;
import objects.Portal;
import objects.Spike;
import utilz.HelpMethod;

import static utilz.HelpMethod.GetLevelData;
import static utilz.HelpMethod.GetPLayerSpawn;
import static utilz.HelpMethod.GetCrabs;
import static utilz.HelpMethod.GetWorms;

public class Level {
    private BufferedImage img;
    private int[][] lvlData;
    private ArrayList<Crab> crabs;
    private ArrayList<Worm> worms;
    private ArrayList<Portal> portals;
    private ArrayList<Spike> spikes;
    private int lvlTilesWide;
    private int maxTileOffset;
    private int maxLvlOffsetX;
    private Point PlayerSpawn;
    
    public Level(BufferedImage img){
        this.img = img;
        createLevelData();
        createEnemies();
        createPortal();
        createSpike();
        calLevelOffset();
        calPlayerSpawn();
    }   

    // create objects
    private void createSpike() {
        spikes = HelpMethod.getSpike(img);
    }

    private void createPortal() {
        portals = HelpMethod.GetPortal(img);
    }

    // calculate player spawn
    private void calPlayerSpawn() {
        PlayerSpawn = GetPLayerSpawn(img);
    }

    // calculate level offset
    private void calLevelOffset() {
        lvlTilesWide = img.getWidth();
        maxTileOffset = lvlTilesWide = Game.TILES_IN_WIDTH;
        maxLvlOffsetX = Game.TILES_SIZE * maxTileOffset;
    }

    private void createEnemies() {
        crabs = GetCrabs(img);
        worms = GetWorms(img);
    }

    private void createLevelData() {
        lvlData = GetLevelData(img);
    }

    // getter
    public int getSpriteIndex(int x, int y) {
		return lvlData[y][x];
	}

    public int[][] getlvlData(){
        return lvlData;
    }

    public int getlvlOffset(){
        return maxLvlOffsetX;
    }

    public ArrayList<Crab> getCrabs(){
        return crabs;
    }

    public ArrayList<Worm> getWorms(){
        return worms;
    }

    public Point getPlayerSpawn(){
        return PlayerSpawn;
    }

    public ArrayList<Portal> getPortals(){
        return portals;
    }

    public ArrayList<Spike> getSpikes(){
        return spikes;
    }
}
