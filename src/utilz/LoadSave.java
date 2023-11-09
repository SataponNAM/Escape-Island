package utilz;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;


public class LoadSave {

    public static final String PLAYET_ATLAS = "res/Player.png";
    public static final String LEVEL_ATLAS = "res/Tiles.png";
    public static final String MENU_BUTTON = "res/Button_Menu_atlas.png";
    public static final String MENU_BACKGROUND = "res/Menu_Paper.png";
    public static final String PAUSE_BACKGROUND = "res/Pause_Paper.png";
    public static final String URM_BUTTONS = "res/URM_Button.png";
    public static final String MENU_BG_IMG = "res/Main_Bg.png";
    public static final String PLAYING_BACKGROUND = "res/Play_Bg.png";
    public static final String ENEMIES_ATLAS = "res/Monsters_spites.png";
    public static final String STATUS_BAR = "res/Health_Power_bar.png";
    
    public static final String PORTAL_IMG = "res/Portal.png";
    public static final String TRAP_ATLAS = "res/Trap.png";

    public static final String DEAD_BG = "res/dead_bg.png";
    public static final String RESTART_BUTTON = "res/Restart_Button.png";
    public static final String GAMEWIN_BG = "res/gamewin_bg.png";

    // read picture
    public static BufferedImage GetAtlas(String fileName) {
		BufferedImage img = null;
		InputStream is = LoadSave.class.getResourceAsStream("/"+fileName);

		try {
			img = ImageIO.read(is);

		} catch (IOException e) {
			e.printStackTrace();
		} 
        finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return img;
	}

    // load all levels
    public static BufferedImage[] GetAllLevels() {
        List<BufferedImage> levelImages = new ArrayList<>();
        
        for (int i = 1; ; i++) {
            String resourceName = "/res/levels/" + i + ".png";
            InputStream inputStream = LoadSave.class.getResourceAsStream(resourceName);
            
            if (inputStream == null) {
                // no more levels 
                break;
            }
            
            try {
                BufferedImage levelImage = ImageIO.read(inputStream);
                levelImages.add(levelImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return levelImages.toArray(new BufferedImage[0]);
    }
}
