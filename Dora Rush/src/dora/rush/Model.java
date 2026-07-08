package dora.rush;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Model extends JPanel implements ActionListener {

    public Dimension d;
    public final Font smallFont = new Font("DORAEMON", Font.ITALIC, 28);
    public boolean inGame = false;
    public boolean dying = false;

    public final int BLOCK_SIZE = 48;
    public final int N_BLOCKS = 20;
    public final int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;
    public final int MAX_RATS = 12;
    public final int DORAEMON_SPEED = 4;

    public int N_RATS = 6;
    public int lives, score;
    public int[] dx, dy;
    public int[] rat_x, rat_y, rat_dx, rat_dy, ratSpeed;
    public int rat_Xpos[ ]= {3,5,9,10,15,16,5,7,11,12,17,19 };
    public int rat_Ypos [ ]={17,5,15,5,5,15,15,18,1,8,13,19};
    

    public Image heart, rat, Intro;
    public Image up, down, left, right;

    public int doraemon_x, doraemon_y, doraemond_x, doraemond_y;
    public int req_dx, req_dy;

    public final short levelData[] = {
        19,	26,	26,	26,	26,	26,	18,	26,	26,	26,	26,	26,	18,	18,	26,	26,	26,	26,	26,	22,
21,	0,	0,	0,	0,	0,	21,	0,	0,	0,	0,	0,	17,	20,	0,	0,	0,	0,	0,	21,
21,	0,	19,	18,	18,	18,	20,	0,	19,	18,	18,	18,	16,	20,	0,	19,	18,	18,	18,	20,
21,	0,	17,	16,	16,	16,	20,	0,	17,	16,	16,	16,	16,	20,	0,	17,	16,	16,	16,	20,
21,	0,	17,	16,	16,	16,	20,	0,	17,	16,	16,	16,	16,	20,	0,	17,	16,	16,	16,	20,
21,	0,	17,	16,	16,	16,	20,	0,	17,	16,	16,	16,	16,	20,	0,	17,	16,	16,	16,	20,
21,	0,	17,	16,	16,	16,	20,	0,	17,	16,	16,	16,	16,	20,	0,	17,	16,	16,	16,	20,
21,	0,	17,	16,	16,	16,	16,	26,	24,	24,	24,	24,	16,	20,	0,	17,	24,	24,	24,	20,
21,	0,	17,	16,	16,	16,	20,	0,	0,	0,	0,	0,	17,	16,	18,	20,	0,	0,	0,	21,
17,	26,	16,	16,	16,	16,	16,	18,	18,	18,	18,	26,	16,	16,	24,	20,	0,	0,	0,	21,
21,	0,	17,	16,	16,	16,	16,	16,	16,	16,	20,	0,	17,	20,	0,	17,	18,	18,	18,	20,
21,	0,	17,	16,	16,	16,	16,	16,	16,	16,	20,	0,	17,	20,	0,	17,	16,	16,	16,	20,
21,	0,	17,	16,	16,	16,	16,	16,	16,	16,	20,	0,	17,	20,	0,	17,	16,	16,	16,	20,
21,	0,	17,	16,	16,	16,	16,	16,	16,	16,	20,	0,	17,	20,	0,	17,	16,	16,	16,	20,
21,	0,	17,	16,	16,	16,	16,	16,	16,	16,	20,	0,	17,	20,	0,	17,	16,	16,	16,	20,
21,	0,	25,	24,	24,	24,	16,	24,	24,	24,	28,	0,	17,	20,	0,	25,	24,	24,	24,	20,
21,	0,	0,	0,	0,	0,	21,	0,	0,	0,	0,	0,	17,	20,	0,	0,	0,	0,	0,	21,
17,	18,	18,	18,	18,	18,	16,	18,	18,	18,	18,	18,	16,	16,	18,	18,	18,	18,	18,	20,
17,	16,	16,	16,	16,	16,	16,	16,	16,	16,	16,	16,	16,	16,	16,	16,	16,	16,	16,	20,
25,	24,	24,	24,	24,	24,	24,	24,	24,	24,	24,	24,	24,	24,	24,	24,	24,	24,	24,	28,
    };

    public final int validSpeeds[] = {1, 2, 3, 4, 6, 8};
    public final int maxSpeed = 6;

    public int currentSpeed = 3;
    public short[] screenData;
    public Timer timer;

    public Model() {

        loadImages();
        initVariables();
        addKeyListener(new TAdapter());
        setFocusable(true);
        initGame();
    }

    public void loadImages() {
        down = new ImageIcon("src\\down.gif").getImage();
        up = new ImageIcon("src\\up.gif").getImage();
        left = new ImageIcon("src\\left.gif").getImage();
        right = new ImageIcon("src\\right.gif").getImage();
        rat = new ImageIcon("src\\rat.gif").getImage();
        heart = new ImageIcon("src\\heart.png").getImage();
        Intro = new ImageIcon("C:\\Users\\Tanveer\\Documents\\NetBeansProjects\\Dora Rush\\src\\DoraRush.png").getImage();
        

    }

    public void initVariables() {

        screenData = new short[N_BLOCKS * N_BLOCKS];
        d = new Dimension(1000, 1000);
        rat_x = new int[MAX_RATS];
        rat_dx = new int[MAX_RATS];
        rat_y = new int[MAX_RATS];
        rat_dy = new int[MAX_RATS];
        ratSpeed = new int[MAX_RATS];
        dx = new int[4];
        dy = new int[4];

        timer = new Timer(15, this);
        timer.restart();
    }

    public void playGame(Graphics2D g2d) {

        if (dying) {

            death();

        } else {

            moveDoraemon();
            drawDoraemon(g2d);
            moveRats(g2d);
            checkMaze();
        }
    }

    public void showIntroScreen(Graphics2D g2d) {
        
        g2d.drawImage(Intro, WIDTH, WIDTH, Color.yellow, this);


       /* ImageIcon image;
        image = new ImageIcon("DoraRush"); */
        
        String start = "Press SPACE to start";
        g2d.setColor(Color.yellow);
        g2d.drawString(start, 340, 420);
    }

    public void drawScore(Graphics2D g) {
        g.setFont(smallFont);
        g.setColor(new Color(255, 223, 0));
        String s = "Score: " + score;
        g.drawString(s, SCREEN_SIZE / 2 + 96, SCREEN_SIZE + 30);

        for (int i = 0; i < lives; i++) {
            g.drawImage(heart, i * 28 + 8, SCREEN_SIZE + 8, this);
        }
    }

    public void checkMaze() {

        int i = 0;
        boolean finished = true;

        while (i < N_BLOCKS * N_BLOCKS && finished) {

            if ((screenData[i]) != 0) {
                finished = false;
            }

            i++;
        }

        if (score == 323) {

            score += 50;

            if (N_RATS < MAX_RATS) {
                N_RATS++;
            }

            if (currentSpeed < maxSpeed) {
                currentSpeed++;
            }

            initLevel();
        }
    }

    public void death() {

        lives--;

        if (lives == 0) {
            inGame = false;
        }

        continueLevel();
    }

    public void moveRats(Graphics2D g2d) {

        int pos;
        int count;

        for (int i = 0; i < N_RATS; i++) {
            if (rat_x[i] % BLOCK_SIZE == 0 && rat_y[i] % BLOCK_SIZE == 0) {
                pos = rat_x[i] / BLOCK_SIZE + N_BLOCKS * (int) (rat_y[i] / BLOCK_SIZE);

                count = 0;

                if ((screenData[pos] & 1) == 0 && rat_dx[i] != 1) {
                    dx[count] = -1;
                    dy[count] = 0;
                    count++;
                }

                if ((screenData[pos] & 2) == 0 && rat_dy[i] != 1) {
                    dx[count] = 0;
                    dy[count] = -1;
                    count++;
                }

                if ((screenData[pos] & 4) == 0 && rat_dx[i] != -1) {
                    dx[count] = 1;
                    dy[count] = 0;
                    count++;
                }

                if ((screenData[pos] & 8) == 0 && rat_dy[i] != -1) {
                    dx[count] = 0;
                    dy[count] = 1;
                    count++;
                }

                if (count == 0) {

                    if ((screenData[pos] & 15) == 15) {
                        rat_dx[i] = 0;
                        rat_dy[i] = 0;
                    } else {
                        rat_dx[i] = -rat_dx[i];
                        rat_dy[i] = -rat_dy[i];
                    }

                } else {

                    count = (int) (Math.random() * count);

                    if (count > 3) {
                        count = 3;
                    }

                    rat_dx[i] = dx[count];
                    rat_dy[i] = dy[count];
                }

            }

            rat_x[i] = rat_x[i] + (rat_dx[i] * ratSpeed[i]);
            rat_y[i] = rat_y[i] + (rat_dy[i] * ratSpeed[i]);
            drawRat(g2d, rat_x[i] + 5, rat_y[i] + 5);

            if (doraemon_x > (rat_x[i] - 12) && doraemon_x < (rat_x[i] + 12)
                    && doraemon_y > (rat_y[i] - 12) && doraemon_y < (rat_y[i] + 12)
                    && inGame) {

                dying = true;
            }
        }
    }

    public void drawRat(Graphics2D g2d, int x, int y) {
        g2d.drawImage(rat, x, y, this);
    }

    public void moveDoraemon() {

        int pos;
        short ch;

        if (doraemon_x % BLOCK_SIZE == 0 && doraemon_y % BLOCK_SIZE == 0) {
            pos = doraemon_x / BLOCK_SIZE + N_BLOCKS * (int) (doraemon_y / BLOCK_SIZE);
            ch = screenData[pos];

            if ((ch & 16) != 0) {
                screenData[pos] = (short) (ch & 15);
                score++;
            }

            if (req_dx != 0 || req_dy != 0) {
                if (!((req_dx == -1 && req_dy == 0 && (ch & 1) != 0)
                        || (req_dx == 1 && req_dy == 0 && (ch & 4) != 0)
                        || (req_dx == 0 && req_dy == -1 && (ch & 2) != 0)
                        || (req_dx == 0 && req_dy == 1 && (ch & 8) != 0))) {
                    doraemond_x = req_dx;
                    doraemond_y = req_dy;
                }
            }

            // Check for standstill
            if ((doraemond_x == -1 && doraemond_y == 0 && (ch & 1) != 0)
                    || (doraemond_x == 1 && doraemond_y == 0 && (ch & 4) != 0)
                    || (doraemond_x == 0 && doraemond_y == -1 && (ch & 2) != 0)
                    || (doraemond_x == 0 && doraemond_y == 1 && (ch & 8) != 0)) {
                doraemond_x = 0;
                doraemond_y = 0;
            }
        }
        doraemon_x = doraemon_x + DORAEMON_SPEED * doraemond_x;
        doraemon_y = doraemon_y + DORAEMON_SPEED * doraemond_y;
    }

    public void drawDoraemon(Graphics2D g2d) {

        if (req_dx == -1) {
            g2d.drawImage(left, doraemon_x + 1, doraemon_y + 1, this);
        } else if (req_dx == 1) {
            g2d.drawImage(right, doraemon_x + 1, doraemon_y + 1, this);
        } else if (req_dy == -1) {
            g2d.drawImage(up, doraemon_x + 1, doraemon_y + 1, this);
        } else {
            g2d.drawImage(down, doraemon_x + 1, doraemon_y + 1, this);
        }
    }

    public void drawMaze(Graphics2D g2d) {

        short i = 0;
        int x, y;

        for (y = 0; y < SCREEN_SIZE; y += BLOCK_SIZE) {
            for (x = 0; x < SCREEN_SIZE; x += BLOCK_SIZE) {

                g2d.setColor(new Color(0, 72, 251));
                g2d.setStroke(new BasicStroke(5));

                if ((levelData[i] == 0)) {
                    g2d.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
                }

                if ((screenData[i] & 1) != 0) {
                    g2d.drawLine(x, y, x, y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 2) != 0) {
                    g2d.drawLine(x, y, x + BLOCK_SIZE - 1, y);
                }

                if ((screenData[i] & 4) != 0) {
                    g2d.drawLine(x + BLOCK_SIZE - 1, y, x + BLOCK_SIZE - 1,
                            y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 8) != 0) {
                    g2d.drawLine(x, y + BLOCK_SIZE - 1, x + BLOCK_SIZE - 1,
                            y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 16) != 0) {
                    g2d.setColor(new Color(171,93,42));
                    g2d.fillOval(x + 10, y + 10, 15, 10);
                }

                i++;
            }
        }
    }

    public void initGame() {

        lives = 3;
        score = 0;
        initLevel();
        N_RATS = 6;
        currentSpeed = 3;
    }

    public void initLevel() {

        int i;
        for (i = 0; i < N_BLOCKS * N_BLOCKS; i++) {
            screenData[i] = levelData[i];
        }

        continueLevel();
    }

    public void continueLevel() {

        int dx = 1;
        int random;

        for (int i = 0; i < N_RATS; i++) {

            rat_y[i] = rat_Ypos[i] * BLOCK_SIZE; //start position
            rat_x[i] = rat_Xpos[i] * BLOCK_SIZE;
            rat_dy[i] = 0;
            rat_dx[i] = dx;
            dx = -dx;
            random = (int) (Math.random() * (currentSpeed + 1));

            if (random > currentSpeed) {
                random = currentSpeed;
            }

            ratSpeed[i] = validSpeeds[random];
        }

        doraemon_x = 7 * BLOCK_SIZE;  //start position
        doraemon_y = 11 * BLOCK_SIZE;
        doraemond_x = 0;	//reset direction move
        doraemond_y = 0;
        req_dx = 0;		// reset direction controls
        req_dy = 0;
        dying = false;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, d.width, d.height);

        drawMaze(g2d);
        drawScore(g2d);

        if (inGame) {
            playGame(g2d);
        } else {
            showIntroScreen(g2d);
        }

        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }

    //controls
    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (inGame) {
                if (key == KeyEvent.VK_LEFT) {
                    req_dx = -1;
                    req_dy = 0;
                } else if (key == KeyEvent.VK_RIGHT) {
                    req_dx = 1;
                    req_dy = 0;
                } else if (key == KeyEvent.VK_UP) {
                    req_dx = 0;
                    req_dy = -1;
                } else if (key == KeyEvent.VK_DOWN) {
                    req_dx = 0;
                    req_dy = 1;
                } else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
                    inGame = false;
                }
            } else {
                if (key == KeyEvent.VK_SPACE) {
                    inGame = true;
                    initGame();
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

}
