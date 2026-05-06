import java.awt.*;
import java.util.ArrayList;

public abstract class Enemy {
    int enemyX, enemyY;
    int ENEMY_WIDTH, ENEMY_HEIGHT;
    int ENEMY_SPEED;
    int direction = 1; // 1 down, -1 up
    Image image; // image for enemy

    // Base Constructor for Enemy
    public Enemy (int enemyX, int enemyY, int ENEMY_HEIGHT, int ENEMY_WIDTH, int ENEMY_SPEED, Image image, int direction){
        this.enemyX = enemyX;
        this.enemyY = enemyY;
        this.ENEMY_HEIGHT = ENEMY_HEIGHT;
        this.ENEMY_WIDTH = ENEMY_WIDTH;
        this.ENEMY_SPEED = ENEMY_SPEED;
        this.image = image;
        this.direction = direction;
    }

}


class VerticalEnemy extends Enemy {

    // Constructor for vertical enemies
    public VerticalEnemy(int enemyX, int enemyY, int ENEMY_HEIGHT, int ENEMY_WIDTH, int ENEMY_SPEED, Image image, int direction) {
        super(enemyX, enemyY, ENEMY_HEIGHT, ENEMY_WIDTH, ENEMY_SPEED, image, direction);
    }

    // Update for vertical enemies
    public void update(ArrayList<Rectangle> walls) {
        int nextY = enemyY + (ENEMY_SPEED * direction);
        Rectangle nextRect = new Rectangle(enemyX+6, nextY+6, ENEMY_WIDTH-13, ENEMY_HEIGHT-14);

        // Check collision with walls
        for (Rectangle wall : walls) {
            if (nextRect.intersects(wall)) {
                direction *= -1; // bounce/changes direction
                return;
            }
        }
        // No collision, apply movement
        enemyY = nextY;
    }
}


class HorizontalEnemy extends Enemy {

    // Constructor for horizontal enemies
    public HorizontalEnemy(int enemyX, int enemyY, int ENEMY_HEIGHT, int ENEMY_WIDTH, int ENEMY_SPEED, Image image, int direction) {
        super(enemyX, enemyY, ENEMY_HEIGHT, ENEMY_WIDTH, ENEMY_SPEED, image, direction);
    }

    // Update for horizontal enemies
    public void update(ArrayList<Rectangle> walls) {
        int nextX = enemyX + (ENEMY_SPEED * direction);
        Rectangle nextRect = new Rectangle(nextX+6, enemyY+6, ENEMY_WIDTH-13, ENEMY_HEIGHT-14);

        // Check collision with walls
        for (Rectangle wall : walls) {
            if (nextRect.intersects(wall)) {
                direction *= -1; // bounce/changes direction
                return;
            }
        }
        // No collision, apply movement
        enemyX = nextX;
    }
}


class RotatingEnemy extends Enemy {

    double angle; // current angle in radians
    int radius; // distance from center
    int centerX, centerY; // Center point to rotate around

    // Constructor for rotating enemies
    public RotatingEnemy(int centerX, int centerY, int radius, int sizeW, int sizeH, int speed, Image image, double startAngle) {
        super(centerX, centerY, sizeH, sizeW, speed, image, 0); // sets all aspects of enemy

        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.angle = startAngle;
    }

    // Update for Rotating enemies
    public void update(ArrayList<Rectangle> walls) {
        angle += ENEMY_SPEED * 0.02; // rotation speed

        // Converts angle into X/Y position
        enemyX = (int)(centerX + Math.cos(angle) * radius);
        enemyY = (int)(centerY + Math.sin(angle) * radius);
    }
}