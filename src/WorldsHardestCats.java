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
    private int level = 1; // int for player level
    private boolean victory; // true/false for if final victory
    private ArrayList<Rectangle> walls = new ArrayList<>(); // list for rectangle walls
    private ArrayList<Rectangle> enemies = new ArrayList<>(); // list for rectangle enemies

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
        playerX = 100;
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
        playerX = 100;
        playerY = 150;

        victory = false;
        level = 1; // Resets all levels
        walls.clear();
        repaint();
    }


    // Function for resetting level
    private void resetLevel(){
        playerX = 100;
        playerY = 150;

        victory = false;
        walls.clear();
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
            g.drawString("VICTORY!", WIDTH / 2 - 80, HEIGHT / 2);
        }

    }

    private void update(){
        if (!victory){
                // Horizontal movement
                int movementX = playerX;
                if (leftPressed) movementX -= PLAYER_SPEED;
                if (rightPressed) movementX += PLAYER_SPEED;
                Rectangle nextX = new Rectangle(movementX, playerY, PLAYER_WIDTH, PLAYER_HEIGHT);
                if (!wallCollision(nextX) && movementX >= 0 && movementX <= WIDTH - PLAYER_WIDTH) playerX = movementX;

                // Vertical movement
                int movementY = playerY;
                if (upPressed) movementY -= PLAYER_SPEED;
                if (downPressed) movementY += PLAYER_SPEED;
                Rectangle nextY = new Rectangle(playerX, movementY, PLAYER_WIDTH, PLAYER_HEIGHT);
                if (!wallCollision(nextY) && movementY >= 0 && movementY <= HEIGHT - PLAYER_HEIGHT) playerY = movementY;


                // runs loadLevel to add walls to level
                if (level == 1 && walls.isEmpty()){
                    loadLevel();
                } else if (level == 2 && walls.isEmpty()){
                    loadLevel();
                } else if (level >= 3 && walls.isEmpty()){
                    loadLevel();
                }


                // Checks if player is in victory space
                Rectangle playerRect = new Rectangle(playerX, playerY, PLAYER_WIDTH, PLAYER_HEIGHT);
                Rectangle victoryRect = new Rectangle(500, 250, 75, 75);
                if (victoryRect.contains(playerRect) || cheatPressed){
                    level++; // increments level


                    cheatPressed = false;

                    // victory if player beats level 3
                    if (level == 4) {
                        victory = true;
                    } else {
                        playerX = 100; // resets player position
                        playerY = 150;
                        walls.clear();
                    }
                }
        }
    }




    private void loadLevel(){
        walls.clear();
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
