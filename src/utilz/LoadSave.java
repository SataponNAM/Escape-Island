package utilz;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

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
    public static BufferedImage[] GetAllLevels(){
        URL url = LoadSave.class.getResource("/res/levels");
        File file = null;

        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        File[]  files = file.listFiles();
        File[] filesSort = new File[files.length];

        // sort file level => 1, 2, 3, ...
        for (int i = 0; i < filesSort.length; i++) {
            for (int j = 0; j < files.length; j++) {
                if (files[j].getName().equals("" + (i + 1) + ".png")) {
                    filesSort[i] = files[j];
                }
            }
        }

        BufferedImage[] Imgs = new BufferedImage[filesSort.length];

        for (int i = 0; i < Imgs.length; i++) {
            try {
                Imgs[i] = ImageIO.read(filesSort[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return Imgs;
    } 
}
