package utilz;

import main.Game;

import entities.AllEnemies.Crab;
import entities.AllEnemies.Worm;

public class Constants {
    public static class Objects{
        // Portal
        public static int PORTAL = 0;
        public static int PORTAL_WIDTH_DEFAULT = 32;
        public static int PORTAL_HEIGHT_DEFAULT = 32;

        public static int PORTAL_WIDTH = (int) (PORTAL_WIDTH_DEFAULT * Game.SCALE);
        public static int PORTAL_HEIGHT = (int) (PORTAL_HEIGHT_DEFAULT * Game.SCALE);

        // Spike
        public static int SPIKE = 1;
        public static int SPIKE_WIDTH_DEFAULT = 32;
        public static int SPIKE_HEIGHT_DEFAULT = 32;

        public static int SPIKE_WIDTH = (int) (SPIKE_WIDTH_DEFAULT * Game.SCALE);
        public static int SPIKE_HEIGHT = (int) (SPIKE_HEIGHT_DEFAULT * Game.SCALE);
    }

    public static class Enemy{
        // Crab
        public static final int CRAB = 0;
        public static final int CRAB_DRAWOFFSET_X = (int) (22 * Game.SCALE);
        public static final int CRAB_DRAWOFFSET_Y = (int) (14 * Game.SCALE);

        // Worm
        public static final int WORM = 5;
        public static final int WORM_DRAWOFFSET_X = (int) (6 * Game.SCALE);
        public static final int WORM_DRAWOFFSET_Y = (int) (12 * Game.SCALE);

        // All enemy constants
        public static final int WIDTH_DEFAULT = 72;
        public static final int HEIGHT_DEFAULT = 40;

        public static final int WIDTH = (int) (WIDTH_DEFAULT * Game.SCALE);
        public static final int HEIGHT = (int) (HEIGHT_DEFAULT * Game.SCALE);

        public static int GetSpiriteAmount(int enemy_type, int enemy_state) {
            switch(enemy_type){
                case CRAB:
                    switch (enemy_state) {
                        case Crab.IDLE:
                            return 3;
                        case Crab.RUN:
                            return 3;
                        case Crab.ATTACK:
                            return 3;
                        case Crab.HIT:
                            return 1;
                        case Crab.DEAD:
                            return 2;
                    }
                case WORM:
                    switch (enemy_state) {
                        case Worm.IDLE:
                            return 2;
                        case Worm.RUN:
                            return 2;
                        case Worm.ATTACK:
                            return 3;
                        case Worm.HIT:
                            return 1;
                        case Worm.DEAD:
                            return 1;
                    }
            }
            return 0;
        }

        public static int GetMaxHealth(int enemy_type){
            switch (enemy_type) {
                case CRAB:
                    return 50;
                case WORM:
                    return 50;
                default:
                    return 10;
            }
        }

        public static int GetEnemyDamag(int enemy_type){
            switch (enemy_type) {
                case CRAB:
                    return 1;
                case WORM:
                    return 1;
                default:
                    return 0;
            }
        }
    }

    public static class UI{
        public static class Buttons{
            public static final int B_WIDTH_DEFAULT = 61;
            public static final int B_HEIGHT_DEFAULT = 21;
            public static final int B_WIDTH = (int)(B_WIDTH_DEFAULT * 4.5f);
            public static final int B_HEIGHT = (int)(B_HEIGHT_DEFAULT * 4.5f);
        }

        public static class URMButton{
            public static final int URM_DEFAULT_SIZE = 56;
            public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * 3f);
        }
    }

    // ทิศทาง
    public static class Directions{
        public static final int LEFT = 0;
        public static final int RIGHT = 2;
    }

    // player animation
    public static class Playerconstants{
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACK = 2;
        public static final int ATTACK2 = 3;

        public static int GetSpiriteAmount(int player_action){
            switch(player_action) {
                case IDLE :
                    return 4; // จำนวน animation
                case RUNNING :
                    return 3;
                case ATTACK :
                    return 1;
                case ATTACK2 :
                    return 1;
                default :
                    return 1;
            }
        }
    }
}
