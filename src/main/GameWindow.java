package main;

import javax.swing.JFrame;

public class GameWindow {
    private JFrame frame;
    public GameWindow(GamePanel gamePanel){
        frame = new JFrame();

        frame.setTitle("Escape Island");
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.add(gamePanel); 
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
