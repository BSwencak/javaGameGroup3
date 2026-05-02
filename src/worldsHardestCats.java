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
    private int level = 1; // int for player level
    private boolean victory; // true/false for if final victory


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
        repaint();
    }


    // Function for resetting level
    private void resetLevel(){
        playerX = 100;
        playerY = 150;

        victory = false;
        repaint();
    }

    private void draw(Graphics g){
        // Sets background
        g.setColor(new Color(28, 161, 218));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Sets victory location
        g.setColor(new Color(70, 200, 96, 255));
        g.fillRect(500, 250, 50, 50);

        // Sets player color
        g.setColor(new Color(151, 2, 23, 255));
        g.fillRect(playerX, playerY, PLAYER_WIDTH, PLAYER_HEIGHT);


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
                if (leftPressed && playerX > 0) playerX -= PLAYER_SPEED;
                if (rightPressed && playerX < WIDTH - PLAYER_WIDTH) playerX += PLAYER_SPEED;

                // Vertical movement
                if (upPressed && playerY > 0) playerY -= PLAYER_SPEED;
                if (downPressed && playerY < HEIGHT - PLAYER_HEIGHT) playerY += PLAYER_SPEED;

                // Checks if player is in victory space
                Rectangle playerRect = new Rectangle(playerX, playerY, PLAYER_WIDTH, PLAYER_HEIGHT);
                Rectangle victoryRect = new Rectangle(500, 250, 50, 50);
                if (victoryRect.contains(playerRect)){
                    level++; // increments level

                    playerX=100; // resets player position
                    playerY=150;
                }

                // victory if player beats level 3
                if (level == 4){
                    victory = true;
                }
        }
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
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A)  leftPressed = false;
        if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) rightPressed = false;
        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W)    upPressed = false;
        if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S)  downPressed = false;
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
