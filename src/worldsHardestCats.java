// Imported Classes Needed
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

// Main game class
public class worldsHardestCats extends JFrame implements KeyListener {

    // All static variables initialized
    private static final int WIDTH = 640; // Width of game frame
    private static final int HEIGHT = 480; // Height of game frame
    private static final int PLAYER_SPEED = 2; // Player speed
    private static final int PLAYER_WIDTH = 25; // Player width
    private static final int PLAYER_HEIGHT = 25; // Player height

    // Player boolean to update if player is inputting
    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    private int playerX, playerY; // Player coords

    private JPanel gamePanel;
    private JLabel scoreLabel;
    private Timer timer;
    private boolean isGameOver; // true/false for if the game is over


    // Constructor method for game
    public  worldsHardestCats() {

        /**
        //Importing the digital assets
        try {
            shipImage = ImageIO.read(new File("ship.png"));
            spriteSheet = ImageIO.read(new File ("astro.png"));
            lifeImage = ImageIO.read(new File("life.png"));
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("laser.wav").getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        */


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

        // Sets scoreLabel to be displayed
        scoreLabel = new JLabel("Worlds Hardest Cats");
        scoreLabel.setForeground(Color.BLACK);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 25));
        scoreLabel.setBounds(10, 10, 100, 20);
        gamePanel.add(scoreLabel);

        // Creates and sets up gamePanel
        add(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.addKeyListener(this);



        // Sets variable data
        playerX = WIDTH / 2 - PLAYER_WIDTH / 2;
        playerY = HEIGHT - PLAYER_HEIGHT - 20;
        isGameOver = false;


        // Creates game timer to call update so the came updates its logic
        timer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isGameOver) {
                    update();
                    gamePanel.repaint();
                }
            }
        });
        timer.start();
    }

    // Function for resetting game
    private void reset(){
        isGameOver = false;
        repaint();
    }

    private void draw(Graphics g){
        // Sets background
        g.setColor(new Color(28, 161, 218));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Sets player color
        g.setColor(new Color(151, 2, 23, 255));
        g.fillRect(playerX, playerY, PLAYER_WIDTH, PLAYER_HEIGHT);


        // Displays game over screen if player loses
        if (isGameOver) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.drawString("Game Over!", WIDTH / 2 - 80, HEIGHT / 2);
        }

    }

    private void update(){
        if (!isGameOver){
                // Horizontal movement
                if (leftPressed && playerX > 0) playerX -= PLAYER_SPEED;
                if (rightPressed && playerX < WIDTH - PLAYER_WIDTH) playerX += PLAYER_SPEED;

                // Vertical movement
                if (upPressed && playerY > 0) playerY -= PLAYER_SPEED;
                if (downPressed && playerY < HEIGHT - PLAYER_HEIGHT) playerY += PLAYER_SPEED;



                    /**
                    // Check collision with player
                    Rectangle playerRect = new Rectangle(playerX, playerY, PLAYER_WIDTH, PLAYER_HEIGHT);
                    for (Point obstacle : obstacles) {
                        Rectangle obstacleRect = new Rectangle(obstacle.x, obstacle.y, OBSTACLE_WIDTH, OBSTACLE_HEIGHT);
                        if (playerRect.intersects(obstacleRect)) {
                            isGameOver = true;
                            break;
                        }
                    }
                     */

        }
    }




    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT)  leftPressed = true;
        if (keyCode == KeyEvent.VK_RIGHT) rightPressed = true;
        if (keyCode == KeyEvent.VK_UP)    upPressed = true;
        if (keyCode == KeyEvent.VK_DOWN)  downPressed = true;

        if (keyCode == KeyEvent.VK_ESCAPE) reset();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT)  leftPressed = false;
        if (keyCode == KeyEvent.VK_RIGHT) rightPressed = false;
        if (keyCode == KeyEvent.VK_UP)    upPressed = false;
        if (keyCode == KeyEvent.VK_DOWN)  downPressed = false;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new worldsHardestCats().setVisible(true);
            }
        });
    }
}
