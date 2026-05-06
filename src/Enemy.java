import java.awt.*;
import java.util.ArrayList;

public abstract class Enemy {
    int enemyX, enemyY;
    int ENEMY_WIDTH, ENEMY_HEIGHT;
    int ENEMY_SPEED;
    int direction = 1; // 1 down, -1 up

    Image image;

    public Enemy (int enemyX, int enemyY, int ENEMY_HEIGHT, int ENEMY_WIDTH, int ENEMY_SPEED, Image image){
        this.enemyX = enemyX;
        this.enemyY = enemyY;
        this.ENEMY_HEIGHT = ENEMY_HEIGHT;
        this.ENEMY_WIDTH = ENEMY_WIDTH;
        this.ENEMY_SPEED = ENEMY_SPEED;
        this.image = image;
    }

}

class VerticalEnemy extends Enemy {

    public VerticalEnemy(int enemyX, int enemyY, int ENEMY_HEIGHT, int ENEMY_WIDTH, int ENEMY_SPEED, Image image) {
        super(enemyX, enemyY, ENEMY_HEIGHT, ENEMY_WIDTH, ENEMY_SPEED, image);
    }

    public void update(ArrayList<Rectangle> walls) {
        int nextY = enemyY + (ENEMY_SPEED * direction);
        Rectangle nextRect = new Rectangle(enemyX+6, nextY+6, ENEMY_WIDTH-13, ENEMY_HEIGHT-14);

        // Check collision with walls
        for (Rectangle wall : walls) {
            if (nextRect.intersects(wall)) {
                direction *= -1; // bounce, changes direction
                return;          // don't move this frame
            }
        }
        // No collision, apply movement
        enemyY = nextY;
    }
}


class HorizontalEnemy extends Enemy {

    public HorizontalEnemy(int enemyX, int enemyY, int ENEMY_HEIGHT, int ENEMY_WIDTH, int ENEMY_SPEED, Image image) {
        super(enemyX, enemyY, ENEMY_HEIGHT, ENEMY_WIDTH, ENEMY_SPEED, image);
    }

    public void update(ArrayList<Rectangle> walls) {
        int nextX = enemyX + (ENEMY_SPEED * direction);
        Rectangle nextRect = new Rectangle(nextX+6, enemyY+6, ENEMY_WIDTH-13, ENEMY_HEIGHT-14);

        // Check collision with walls
        for (Rectangle wall : walls) {
            if (nextRect.intersects(wall)) {
                direction *= -1; // bounce, changes direction
                return;          // don't move this frame
            }
        }
        // No collision, apply movement
        enemyX = nextX;
    }
}

/** WIP
class CircularEnemy extends Enemy {

    public CircularEnemy(int enemyX, int enemyY, int ENEMY_HEIGHT, int ENEMY_WIDTH, int ENEMY_SPEED) {
        super(enemyX, enemyY, ENEMY_HEIGHT, ENEMY_WIDTH, ENEMY_SPEED);
    }

    public void update(ArrayList<Rectangle> walls) {
        int nextX = enemyX + (ENEMY_SPEED * direction);
        Rectangle nextRect = new Rectangle(nextX, enemyY, ENEMY_WIDTH, ENEMY_HEIGHT);

        // Check collision with walls
        for (Rectangle wall : walls) {
            if (nextRect.intersects(wall)) {
                direction *= -1; // bounce, changes direction
                return;          // don't move this frame
            }
        }
        // No collision, apply movement
        enemyX = nextX;
    }
}
 */
