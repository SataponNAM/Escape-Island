package main;

import javax.swing.JPanel;

import inputs.KeyBoardInput;
import inputs.MouseInput;

import java.awt.Dimension;
import java.awt.Graphics;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

public class GamePanel extends JPanel{
    private MouseInput mouseInputs;
    private Game game;
    
    public GamePanel(Game game){
        mouseInputs = new MouseInput(this);
        this.game = game;

        setPanelSize();

        addKeyListener(new KeyBoardInput(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);
        System.out.println("Width : "+GAME_WIDTH+" | Height : "+GAME_HEIGHT);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
       
        game.render(g);
    }   

    public Game getGame(){
        return game;
    }
    
}
