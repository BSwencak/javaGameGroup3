// Imported Classes Needed
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

// Main game class
public class WorldsHardestCats extends JFrame implements KeyListener {

    // All static variables initialized
    private static final int WIDTH = 640; // Width of game frame
    private static final int HEIGHT = 480; // Height of game frame
    private static final int PLAYER_SPEED = 2; // Player speed
    private static final int PLAYER_WIDTH = 32; // Player width
    private static final int PLAYER_HEIGHT = 32; // Player height

    // Player boolean to update if player is inputting
    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean cheatPressed = false;

    private int playerX, playerY; // Player coords

    private JPanel gamePanel;
    private Timer timer;
    private int totalFails;
    private int levelFails;
    private int level = 1; // int for player level
    private boolean victory; // true/false for if final victory
    private ArrayList<Rectangle> walls = new ArrayList<>(); // list for rectangle walls
    private ArrayList<Enemy> enemies = new ArrayList<>(); // list for enemies

    private BufferedImage dog1;
    private BufferedImage dog2;
    private BufferedImage dog3;
    private BufferedImage dog4;
    private BufferedImage alpha;
    private BufferedImage yoda;
    private BufferedImage explorer;

    // Constructor method for game
    public WorldsHardestCats() {


        //Importing the digital assets
        try {

            // Import Images
            dog1 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/dog1.png")));
            dog2 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/dog2.png")));
            dog3 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/dog3.png")));
            dog4 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/toby.png")));
            alpha = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/alpha.png")));
            explorer = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/explorer.png")));
            yoda = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/yoda.png")));

            //AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("laser.wav").getAbsoluteFile());
            //clip = AudioSystem.getClip();
            //clip.open(audioInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Sets title and JFrame options
        setTitle("Worlds Hardest Cats");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);


        // Creates the gamePanel and draws
        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                draw(g);
            }
        };


        // Creates and sets up gamePanel
        add(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.addKeyListener(this);


        // Sets variable data
        playerX = 97;
        playerY = 150;
        victory = false;


        // Creates game timer to call update so the came updates its logic
        timer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!victory) {
                    update();
                    gamePanel.repaint();
                }
            }
        });
        timer.start();
    }


    // Function for resetting whole game
    private void resetGame(){
        // Resets player location
        playerX = 97;
        playerY = 150;

        victory = false;
        levelFails = 0;
        totalFails = 0;
        level = 1; // Resets all levels
        walls.clear();
        enemies.clear();
        repaint();
    }


    // Function for resetting level
    private void resetLevel(){
        playerX = 97;
        playerY = 150;

        victory = false;
        walls.clear();
        enemies.clear();
        repaint();
    }

    private void draw(Graphics g){
        // Sets background
        g.setColor(new Color(28, 161, 218));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Displays level
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        if (level <= 3){
            g.drawString("Level: " + level, 15, 40);
            g.drawString("Fails: " + levelFails, 500, 40);
        }
        else {
            g.drawString("Level: 3", 15, 40);
        }


        // Sets victory location
        g.setColor(new Color(70, 200, 96, 255));
        g.fillRect(500, 250, 75, 75);

        // Sets starting location
        g.setColor(new Color(111, 70, 200, 255));
        g.fillRect(75, 125, 75, 75);

        // Displays walls
        g.setColor(Color.BLACK);
        for (Rectangle wall : walls) g.fillRect(wall.x, wall.y, wall.width, wall.height);


        //g.setColor(Color.RED);
        for (Enemy e : enemies) {
            // g.fillRect(e.enemyX, e.enemyY, e.ENEMY_WIDTH, e.ENEMY_HEIGHT);
            g.drawImage(e.image,e.enemyX,e.enemyY,e.ENEMY_WIDTH,e.ENEMY_HEIGHT,null);
        }


        // Sets player color
       // g.setColor(new Color(151, 2, 23, 255));
        //g.fillRect(playerX, playerY, PLAYER_WIDTH, PLAYER_HEIGHT);
        if (level == 1){
            g.drawImage(alpha,playerX,playerY,PLAYER_WIDTH,PLAYER_HEIGHT,null);
        }else if (level == 2){
            g.drawImage(explorer,playerX,playerY,PLAYER_WIDTH,PLAYER_HEIGHT,null);
        }else if (level >= 3){
            g.drawImage(yoda,playerX,playerY,PLAYER_WIDTH,PLAYER_HEIGHT,null);
        }



        // Displays game over screen if player loses
        if (victory) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 24));
            FontMetrics metrics = g.getFontMetrics(g.getFont());
            int x = (WIDTH - metrics.stringWidth("VICTORY!"))/2;
            g.drawString("VICTORY!", x, 220);
            x = (WIDTH - metrics.stringWidth("Total Fails: "+totalFails))/2;
            g.drawString("Total Fails:  "+ totalFails, x , 260);
        }

    }

    private void update(){
        if (!victory){

            // runs loadLevel to add walls to level
            if (level == 1 && walls.isEmpty()){
                loadLevel();
            } else if (level == 2 && walls.isEmpty()){
                loadLevel();
            } else if (level >= 3 && walls.isEmpty()){
                loadLevel();
            }

            // Horizontal movement
            int movementX = playerX;
            if (leftPressed) movementX -= PLAYER_SPEED;
            if (rightPressed) movementX += PLAYER_SPEED;
            Rectangle nextX = new Rectangle(movementX + 6, playerY + 6,PLAYER_WIDTH - 13, PLAYER_HEIGHT - 14);
            if (!wallCollision(nextX) && movementX >= 0 && movementX <= WIDTH - PLAYER_WIDTH) playerX = movementX;

            // Vertical movement
            int movementY = playerY;
            if (upPressed) movementY -= PLAYER_SPEED;
            if (downPressed) movementY += PLAYER_SPEED;
            Rectangle nextY = new Rectangle(playerX +6, movementY + 6, PLAYER_WIDTH- 13, PLAYER_HEIGHT - 14);
            if (!wallCollision(nextY) && movementY >= 0 && movementY <= HEIGHT - PLAYER_HEIGHT) playerY = movementY;

            // Creates offset player hitbox to compensate for image difference
            Rectangle playerHitbox = new Rectangle(playerX + 6, playerY + 6,PLAYER_WIDTH - 13, PLAYER_HEIGHT - 14);
            for (Enemy e : enemies) {
                Rectangle enemyHitbox = new Rectangle(e.enemyX + 6, e.enemyY + 6,e.ENEMY_WIDTH - 13, e.ENEMY_HEIGHT - 14);
                // Resets player in level if hit enemy
                if (playerHitbox.intersects(enemyHitbox)){
                    playerX = 97;
                    playerY = 150;
                    levelFails++;
                    return;
                }
                if (e instanceof VerticalEnemy) {
                    ((VerticalEnemy) e).update(walls);
                }
                if (e instanceof HorizontalEnemy) {
                    ((HorizontalEnemy) e).update(walls);
                }
                if (e instanceof RotatingEnemy) {
                    ((RotatingEnemy) e).update(walls);
                }

            }


            // Checks if player is in victory space
            Rectangle playerRect = new Rectangle(playerX, playerY, PLAYER_WIDTH, PLAYER_HEIGHT);
            Rectangle victoryRect = new Rectangle(500, 250, 75, 75);
            if (victoryRect.contains(playerRect) || cheatPressed){
                totalFails=totalFails+levelFails;
                levelFails=0;
                level++; // increments level


                cheatPressed = false;

                // victory if player beats level 3
                if (level == 4) {
                    victory = true;
                } else {
                    playerX = 100; // resets player position
                    playerY = 150;
                    walls.clear();
                    enemies.clear();
                }
            }
        }
    }




    private void loadLevel(){
        walls.clear();
        enemies.clear();
        // adds wall rectangles to list "walls"
        if (level == 1){
            // Start box
            walls.add(new Rectangle(75, 125, 75, 5)); // top
            walls.add(new Rectangle(75, 125, 5, 75)); // left
            walls.add(new Rectangle(145, 125, 5, 75)); // right

            // Finish box
            walls.add(new Rectangle(500, 250, 75, 5)); // top
            walls.add(new Rectangle(500, 320, 75, 5)); // bottom
            walls.add(new Rectangle(570, 250, 5, 75)); // right

            // Level 1 Corridor Walls
            // Top Section
            walls.add(new Rectangle(145, 195, 5, 100)); // drop down from right start
            walls.add(new Rectangle(145, 295, 60, 5)); // small right from drop
            walls.add(new Rectangle(205, 175, 5, 125)); // up from small right
            walls.add(new Rectangle(205, 175, 240, 5)); // main top
            walls.add(new Rectangle(445, 175, 5, 80)); // down from top
            walls.add(new Rectangle(445, 250, 55, 5)); // over to finish

            // Bottom Section
            walls.add(new Rectangle(75, 195, 5, 225)); // drop down from left start
            walls.add(new Rectangle(75, 420, 360, 5)); // bottom
            walls.add(new Rectangle(435, 325, 5, 100)); // bottom up right
            walls.add(new Rectangle(435, 320, 65, 5)); // finish box bottom connection

            // Enemy Spawns
            enemies.add(new VerticalEnemy(160,399,32,32,4, dog2,-1));
            enemies.add(new VerticalEnemy(235,235,32,32,3, dog3,1));
            enemies.add(new VerticalEnemy(310,338,32,32,3, dog1,-1));
            enemies.add(new VerticalEnemy(385,235,32,32,3, dog4,1));


        } else if (level == 2){
            // Start box
            walls.add(new Rectangle(75, 125, 75, 5)); // top
            walls.add(new Rectangle(75, 195, 75, 5)); // bottom
            walls.add(new Rectangle(75, 125, 5, 75)); // left

            // Finish box
            walls.add(new Rectangle(500, 320, 75, 5)); // bottom
            walls.add(new Rectangle(500, 250, 5, 75)); // left
            walls.add(new Rectangle(570, 250, 5, 75)); // right
        } else if (level >= 3){
            // Start box
            walls.add(new Rectangle(75, 125, 75, 5)); // top
            walls.add(new Rectangle(75, 125, 5, 75)); // left
            walls.add(new Rectangle(75, 195, 75, 5)); // bottom

            // Finish box
            walls.add(new Rectangle(500, 250, 75, 5)); // top
            walls.add(new Rectangle(500, 320, 75, 5)); // bottom
            walls.add(new Rectangle(570, 250, 5, 75)); // right

            // center point of rotation
            int centerX = 300;
            int centerY = 200;
            int radius = 80; // radius of rotation circle
            int size = 32; // enemy size
            int speed = 2;

            Image[] dogs = {dog2,dog3,dog1,dog4}; // array of dogs to pick from for the circle

            for (int i = 0; i < 6; i++) { // loops to make 6 enemies
                double angle = (Math.PI * 2 / 6) * i; // evenly spaces enemies
                Image dog = dogs[i % dogs.length]; // cycles through the dogs 1 by 1
                enemies.add(new RotatingEnemy(centerX, centerY, radius, size, size, speed, dog, angle));
            }
        }

    }


    // function to check if wall collision returns true or false.
    private boolean wallCollision(Rectangle rect) {
        for (Rectangle wall : walls) {
            if (rect.intersects(wall)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A)  leftPressed = true;
        if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) rightPressed = true;
        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W)    upPressed = true;
        if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S)  downPressed = true;

        if (keyCode == KeyEvent.VK_ESCAPE) resetLevel();
        if (keyCode == KeyEvent.VK_BACK_SPACE) resetGame();

        if (keyCode == KeyEvent.VK_ALT && !cheatPressed) cheatPressed = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A)  leftPressed = false;
        if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) rightPressed = false;
        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W)    upPressed = false;
        if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S)  downPressed = false;

        if (keyCode == KeyEvent.VK_ALT) cheatPressed = false;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WorldsHardestCats().setVisible(true);
            }
        });
    }
}
