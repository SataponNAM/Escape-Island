package utilz;

import static utilz.Constants.Enemy.CRAB;
import static utilz.Constants.Enemy.WORM;
import static utilz.Constants.Objects.*;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.AllEnemies.Crab;
import entities.AllEnemies.Worm;
import levels.LevelManager;
import main.Game;
import objects.Portal;
import objects.Spike;

public class HelpMethod {
    
	// เช็คว่าสามารถเดินได้ไหม
    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
		if (!IsSolid(x, y, lvlData))
			if (!IsSolid(x + width, y + height, lvlData))
				if (!IsSolid(x + width, y, lvlData))
					if (!IsSolid(x, y + height, lvlData))
						return true;
		return false;
	}
	
    private static boolean IsSolid(float x, float y, int[][] lvlData){
		int maxWidth = lvlData[0].length * Game.TILES_SIZE;

        if(x < 0 || x >= maxWidth){
            return true;}
        if(y < 0 || y >= Game.GAME_HEIGHT){
            return true;}

        float xIndex = x/Game.TILES_SIZE;
        float yIndex = y/Game.TILES_SIZE;

        return IsTileSolid((int) xIndex, (int) yIndex, lvlData);
    }

	// tile ทึบไหม
	public static boolean IsTileSolid(int xTile, int yTile, int[][] lvlData){
		int value = lvlData[yTile][xTile];

        if (value >= 30 || value < 0 || value != 9) {
            return  true;
        }

        return false;
	}

    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
		int currentTile = (int) (hitbox.x / Game.TILES_SIZE);
		if (xSpeed > 0) {
			// Right
			int tileXPos = currentTile * Game.TILES_SIZE;
			int xOffset = (int) (Game.TILES_SIZE - hitbox.width);
			return tileXPos + xOffset - 1;
		} else{
			// Left
			return currentTile * Game.TILES_SIZE;
		}
	}

    public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
		int currentTile = (int) (hitbox.y / Game.TILES_SIZE);
		if (airSpeed > 0) {
			// Fall
			int tileYPos = currentTile * Game.TILES_SIZE;
			int yOffset = (int) (Game.TILES_SIZE - hitbox.height);
			return tileYPos + yOffset - 1;
		} else {
			// Jump
			return currentTile * Game.TILES_SIZE;
		}
	}

    public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData){
        // check below bottomleft bottomright
        if(!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData)){
            if(!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1 , lvlData)){
                return false;
            }
        }

        return true;
    }

	public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData) {
		if (xSpeed > 0) {
			return IsSolid(hitbox.x + hitbox.width + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
		} else {
        	return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
		}
    }

	// เช็คว่าเดินได้ไหม
	public static boolean IsAllTileWalkable(int xStart, int xEnd, int y, int[][] lvlData){
		for (int i = 0; i < xEnd - xStart; i++) {
			if (IsTileSolid(xStart + i, y, lvlData)) {
				return false;
			}
			if (!IsTileSolid(xStart + i, y + 1, lvlData)) {
				return false;
			}
		}
		return true;
	} 

	// สำหรับ enemy เช็ค player กรณีถ้าเป็นเหวลงไป หรืออยู่ยนเนิน
	public static boolean isSightClear(int[][] lvlData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox, int tileY){
		int firstXTile = (int) (firstHitbox.x / Game.TILES_SIZE);
		int secondXTile = (int) (secondHitbox.x / Game.TILES_SIZE);

		if (firstXTile > secondXTile) {
			return IsAllTileWalkable(secondXTile, firstXTile, tileY, lvlData);
		} else {
			return IsAllTileWalkable(firstXTile, secondXTile, tileY, lvlData);
		}
	}

	// load level
    public static int[][] GetLevelData(BufferedImage img) {
		int[][] lvlData = new int[img.getHeight()][img.getWidth()];

		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getRed();
				if (value >= 30)
					value = 0;
				lvlData[j][i] = value;
			}
		return lvlData;

	}

	 // load crab
    public static ArrayList<Crab> GetCrabs(BufferedImage img){
        ArrayList<Crab> list = new ArrayList<>();

        for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen();
				if (value == CRAB){
					list.add(new Crab(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
                }
			}
		return list;
    } 

    // load worm
    public static ArrayList<Worm> GetWorms(BufferedImage img){
        ArrayList<Worm> list2 = new ArrayList<>();

        for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen();
				if (value == WORM){
					list2.add(new Worm(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
                }
			}
		return list2;
    }

	// player spawn
	public static Point GetPLayerSpawn(BufferedImage img){
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen();
				if (value == 100){
					return new Point(i * Game.TILES_SIZE, j * Game.TILES_SIZE);
                }
			}
		return new Point(1 * Game.TILES_SIZE, 1 * Game.TILES_SIZE);
	}

	// load Portal
	public static ArrayList<Portal> GetPortal(BufferedImage img){
        ArrayList<Portal> list = new ArrayList<>();

        for (int j = 0; j < img.getHeight(); j++){
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getBlue();
				if (value == PORTAL){
					list.add(new Portal(i * Game.TILES_SIZE, j * Game.TILES_SIZE, PORTAL));
                }
			}
		}
		return list;
    }

	// load Spike
    public static ArrayList<Spike> getSpike(BufferedImage img) {
		ArrayList<Spike> list = new ArrayList<>();

		for (int j = 0; j < img.getHeight(); j++){
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getBlue();
				if (value == SPIKE){
					list.add(new Spike(i * Game.TILES_SIZE, j * Game.TILES_SIZE, SPIKE));
                }
			}
		}
        return list;
    }

}
