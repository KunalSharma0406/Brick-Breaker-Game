package demogame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePlay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;
    private int totalbricks = 21;
    private Timer Timer;
    private int delay = 8;
    private int playerX = 310;
    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;
    private MapGenerator map;

    public GamePlay() {
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        Timer = new Timer(delay, this);
        Timer.start();
    }



    public void paint(Graphics g) {
        //background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);
        map.draw((Graphics2D)g);
//borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 3, 3, 592);

        g.setColor(Color.white);
        g.setFont(new Font("Kunal", Font.BOLD, 25));
        g.drawString("" + score, 590, 30);
// the paddle
        g.setColor(Color.blue);
        g.fillRect(playerX, 550, 100, 8);
//the ball
        g.setColor(Color.green);
        g.fillOval(ballposX, ballposY, 20, 20);

       if(ballposY>570){
           play=false;
           ballXdir=0;
           ballYdir=0;
           g.setColor(Color.red);
           g.setFont(new Font("kunal",Font.BOLD,30));
           g.drawString("Game Over,Scores: "+score,190,300);
           g.setFont(new Font("kunal",Font.BOLD,20));
           g.drawString("Press Enter to restart ",230 ,350);
    }
     if(totalbricks==0){
         play=false;
         ballXdir=-2;
         ballYdir=-1;
         g.setColor(Color.red);
         g.setFont(new Font("kunal",Font.BOLD,30));
         g.drawString("Hey You Won: "+score,260,300);
         g.setFont(new Font("kunal",Font.BOLD,20));
         g.drawString("Press Enter to restart ",230 ,350);

     }
g.dispose();
}
    @Override
    public void actionPerformed(ActionEvent e) {
    Timer.start();

    if(play) {
        if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,8))){
            ballYdir=-ballYdir;
        }
        A:for(int i=0;i<map.map.length;i++) {
            for (int j = 0; j < map.map[0].length; j++) {
               // int[] map1 = new int[0];
                if( map.map[i][j]>0) {
                    int brickX = j * map.bricksWidth + 80;
                    int brickY = i * map.brickHeight + 50;
                    int bricksWidth = map.bricksWidth;
                    int bricksHeight = map.brickHeight;
                    Rectangle rect = new Rectangle(brickX, brickY, bricksWidth, bricksHeight);
                    Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
                    Rectangle brickRect = rect;
                    if (ballRect.intersects(brickRect)) {
                        map.SetBrickValue(0, i, j);
                        totalbricks--;
                        score += 5;
                        if (ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
                            ballXdir = -ballXdir;
                        } else {
                            ballYdir = -ballYdir;
                        }
                        break A;
                    }
                }
            }
        }
        ballposX += ballXdir;
        ballposY += ballYdir;
        if (ballposX < 0) {
            ballXdir = -ballXdir;
        }
        if (ballposY < 0) {
            ballYdir = -ballYdir;
        }
       if (ballposX > 670) {
            ballXdir = -ballXdir;
        }
    }
    repaint();



    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 700) {
                playerX = 700;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            if(!play){
                play=true;
                ballposX=120;
                ballposY=350;
                ballXdir=-1;
                ballYdir=-2;
                playerX=310;
                score=0;
                totalbricks=21;
                map=new MapGenerator(3,7);
                repaint();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void moveRight(){
        play=true;
        playerX+=20;
    }
    public void moveLeft(){
        play=true;
        playerX-=20;
    }


}
