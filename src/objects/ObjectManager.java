package objects;

import static utilz.Constants.Objects.PORTAL_HEIGHT;
import static utilz.Constants.Objects.PORTAL_WIDTH;
import static utilz.Constants.Objects.SPIKE_HEIGHT;
import static utilz.Constants.Objects.SPIKE_WIDTH;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Player;
import gamestates.Playing;
import levels.Level;
import utilz.LoadSave;

public class ObjectManager {
    private Playing playing;
    private BufferedImage portalImg;
    private BufferedImage spikeImg;
    private ArrayList<Portal> portals;
    private ArrayList<Spike> spikes;

    public ObjectManager(Playing playing){
        this.playing = playing;
        loadImgs();

    }

    public void checkSpikeTouch(Player p){
        for (Spike s : spikes) {
            if(s.isActive()){
                if (p.getHitbox().intersects(s.getHitbox())) {
                    System.out.println("Spike !!");
                    p.kill();   
                }
            }
        }
    }

    public void checkPortalTouch(Player p){
        for (Portal pr : portals) {
            if(pr.isActive()){
                if (p.getHitbox().intersects(pr.getHitbox())) {
                    System.out.println("Portal!!");
                    playing.loadNextLevel();
                    
                }
            }
        }
    }

    public void loadObject(Level newLevel) {
        portals = newLevel.getPortals();
        spikes = newLevel.getSpikes();
    }

    private void loadImgs() {
        portalImg = LoadSave.GetAtlas(LoadSave.PORTAL_IMG);
        spikeImg = LoadSave.GetAtlas(LoadSave.TRAP_ATLAS);
    }

    public void draw(Graphics g, int xLvlOffset){
        drawPortal(g, xLvlOffset);
        drawTrap(g, xLvlOffset);
    }

    private void drawTrap(Graphics g, int xLvlOffset) {
        for (Spike sp : spikes) {
            g.drawImage(spikeImg, (int) (sp.hitbox.x - xLvlOffset), (int) (sp.hitbox.y - sp.yDrawOffset), SPIKE_WIDTH, SPIKE_HEIGHT, null);
        }
    }

    private void drawPortal(Graphics g, int xLvlOffset) {
        for (Portal p : portals) {
            g.drawImage(portalImg, (int) (p.hitbox.x - xLvlOffset), (int) p.hitbox.y, PORTAL_WIDTH, PORTAL_HEIGHT, null);
        }
    }

    
}
