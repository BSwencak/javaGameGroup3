public abstract class Enemy {
    int enemyX, enemyY;
    int ENEMY_WIDTH, ENEMY_HEIGHT;
    int ENEMY_SPEED;

    public Enemy (int enemyX, int enemyY, int ENEMY_HEIGHT, int ENEMY_WIDTH, int ENEMY_SPEED){
        this.enemyX = enemyX;
        this.enemyY = enemyY;
        this.ENEMY_HEIGHT = ENEMY_HEIGHT;
        this.ENEMY_WIDTH = ENEMY_WIDTH;
        this.ENEMY_SPEED = ENEMY_SPEED;
    }

}

class VerticalEnemy extends Enemy{

    public VerticalEnemy(int enemyX, int enemyY, int ENEMY_HEIGHT, int ENEMY_WIDTH, int ENEMY_SPEED) {
        super(enemyX, enemyY, ENEMY_HEIGHT, ENEMY_WIDTH, ENEMY_SPEED);
    }
}