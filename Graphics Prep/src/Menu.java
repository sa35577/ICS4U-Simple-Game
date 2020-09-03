/*
File: Menu.java
Name: Sat Arora
Description: This is Tron Lightcycles. It was inspired mostly off of fltron.com. Extra features besides rubric include:
- AI that allows to play one player
- Speed boost for a short period of time
- A victory sound effect when someone wins/loses the full game (3 pts)
- A post-game menu that allows to play a new game, return to menu, or quit
- Starting menu that shows the different options
*/


//importing necessary packages
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.applet.*;
//the main menu class
public class Menu extends JFrame implements ActionListener {
    JButton player1 = new JButton(new ImageIcon("1player.png")); //button for the option to play one player
    JButton player2 = new JButton(new ImageIcon("2Player.png")); //button to play 2 player
    MenuPanel menu;
    //constructor
    public Menu() {
        super("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Rectangle startRect = new Rectangle(250,100,400,100); //dimensions for the 1 player button
        Rectangle startRect2 = new Rectangle(250,600,400,100); //dimensions for the 2 player button
        setSize(1000,800); //1000 by 800 menu
        player1.setBounds(startRect); player2.setBounds(startRect2); //setting the bounds for the buttons
        player1.addActionListener(this); player2.addActionListener(this);
        menu = new MenuPanel(); 
        add(player1); add(player2); add(menu); 
        setVisible(true);
        setResizable(false);
    }
    public void actionPerformed(ActionEvent evt) {
        menu.repaint();
        Object source = evt.getSource();
        if (source == player1) { //if the first player button is hit
            setVisible(false);
            Arrow newArrow = new Arrow(true); //calling the newArrow with the argument that it is one Player
        }
        if (source == player2) { //if the two player button is hit
            setVisible(false);
            Arrow newArrow = new Arrow(false); //calling the newArrow with the argument that it is two players
        }

    }
    public static void main(String[] args) { Menu frame = new Menu();}
}
class MenuPanel extends JPanel {
    private static Image title = new ImageIcon("title.png").getImage(), profile = new ImageIcon("profile.png").getImage(); //the texts for the welcome screen
    public void paintComponent(Graphics g) {
        g.setColor(Color.black); 
        g.fillRect(0,0,1000,800); //setting the entire screen for the menu as black
        g.drawImage(profile,500,25,null); 
        g.drawImage(title,100,300,null);
    }
}
//arrow control (the actual game)
class Arrow extends JFrame implements ActionListener {
    ArrowPanel game; //panel used in sync with the Arrow class
    Timer myTimer; //timer for the delay of the moves

    public Arrow(boolean onePlayer) {
        super("Ron LightCycle!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000); //screen size of 1000 by 1000
        myTimer = new Timer(20, this); //delay by 20ms
        game = new ArrowPanel(this, onePlayer); //attaching the panel for the actual game itself
        add(game); 
        setResizable(false);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        game.move();
        game.check();
        game.repaint();
    }

    public static void main(String[] args) {
    }

    public void start() {
        myTimer.start();//starting the timer
        
    }


    public class ArrowPanel extends JPanel implements KeyListener {
        private int x1, x2, y1, y2; //current position variables for the two players
        private int d1, d2; //direction variables
        private Arrow mainFrame; //the arrow frame used
        private static final int RIGHT = 0, UP = 1, LEFT = 2, DOWN = 3; //constants for directions
        private boolean lost1 = false, lost2 = false; //boolean variables holding if the player lost the current round
        private boolean[][] occupied = new boolean[1001][801]; //occupied 2d array for holding each spot
        private int p1, p2; //the total number of rounds each person has won
        private Image[] blues, greens, numberScores,whitenums; //blues is for the images for point values in blue color, similar for green color scores, numberscores is for the post-game menu font to show the number of games won, whitenums is the transparent ones (for turbo count), 
        private int win1, win2; //the total number of games each person has won
        private boolean inRound, beginning; //boolean beginnnig to hold the case for drawing the grid at first, inRound for performing actions during the game
        private Image grid, p1Win, p2Win, scoreWord, dash, turbos,comWon,spacePrompt,backPrompt,qPrompt; //images used in the game
        private boolean com; //boolean variable holding if the computer is playing
        private boolean boost1 = false, boost2 = false; //boolean variable holding whether the boost is activated
        private int t1, t2; //timer (except stored as int and incrases) holding the condition for extra speed
        private final int moveFast = 15; //fast speed (during boost)
        private int spanx1=5,spany1=5,spanx2=5,spany2=5; //spanning variables for the rectangle sizes during speed bosts
        private int remTurbo1,remTurbo2; //variables for capping out at 3 turbos
        private File wavFile = new File("Happy Wheels Victory Sound (EARRAPE).wav"); //file for the music
        private AudioClip sound; //sound audioclip

        //constructor
        public ArrowPanel(Arrow a, boolean onePlayer) {
            mainFrame = a; //setting the frame for the game
            defaultSettings(); //setting the default settings
            p1 = 0; //points are at 0 for p2
            p2 = 0; //points are at 0 for p2
            win1 = 0; //0 wins originally
            win2 = 0; //0 wins originally
            for (int i = 0; i < 1001; i++)
                for (int j = 0; j < 801; j++)
                    occupied[i][j] = false; //every pixel is originally empty
            addKeyListener(this);
            blues = new Image[4];
            greens = new Image[4];
            numberScores = new Image[10];
            whitenums = new Image[4];
            for (int i = 0; i < 10; i++) { //going through the 10 different images, only needed for the number sprites at the game done menu
                if (i < 4) { //there are only 4 possible scores for the points
                    //setting the image at the specific index according to the format of the image name
                    blues[i] = new ImageIcon(String.format("%dBlue.png", i)).getImage();
                    greens[i] = new ImageIcon(String.format("%dGreen.png", i)).getImage();
                    whitenums[i] = new ImageIcon(String.format("%d.png",i)).getImage();
                }
                numberScores[i] = new ImageIcon(String.format("score%d.png", i)).getImage();
            }
            inRound = true; //currently in a round
            grid = new ImageIcon("grid.png").getImage();
            beginning = true;
            p1Win = new ImageIcon("p1winner.png").getImage();
            p2Win = new ImageIcon("p2Winner.jpg").getImage();
            scoreWord = new ImageIcon("ScoreWord.png").getImage();
            dash = new ImageIcon("dash.png").getImage();
            turbos = new ImageIcon("turbos.png").getImage();
            comWon = new ImageIcon("comWin.png").getImage();
            spacePrompt = new ImageIcon("spacePrompt.png").getImage();
            backPrompt = new ImageIcon("backPrompt.png").getImage();
            qPrompt = new ImageIcon("qprompt.png").getImage();
            com = onePlayer;
            try{
                sound = Applet.newAudioClip(wavFile.toURL());}
            catch(Exception e){e.printStackTrace();}

        }

        public void addNotify() {
            super.addNotify();
            requestFocus();
            mainFrame.start();
        }
        //move method that changes the position of the 2 players, with the automated portion inside an if statement
        public void move() {
            int accum = 0;
            if (com) {
                while (accum < 4) { //guaranteed to run through all 4 directions for the AI
                    //the idea is that the com will check all 4 directions, starting from the one it is currently at
                    if (d1 == RIGHT && x1 + 10 < 1000 && !(occupied[x1 + 10][y1] || occupied[x1 + 10][y1 + 5])) break; //if moving right, there is space to the right, and the spots are not occupied then the com can move right
                    else {
                        d1 = UP;
                        ++accum; //went through one direction
                    }

                    if (d1 == UP && y1 - 5 > 0 && !(occupied[x1][y1 - 5] || occupied[x1 + 5][y1 - 5])) break; //if moving up, there is space up, and the spots are not occupied then the com can move up
                    else {
                        d1 = LEFT;
                        ++accum; //went through a direction
                    }

                    if (d1 == LEFT && x1 - 5 > 0 && !(occupied[x1 - 5][y1] || occupied[x1 - 5][y1 + 5])) break; //if moving left, there is space to the left, and the spots are not occupied then the com can move left
                    else {
                        d1 = DOWN;
                        ++accum; //went through a direction
                    }

                    if (d1 == DOWN && y1 + 5 < 780 && !(occupied[x1][y1 + 10] || occupied[x1 + 5][y1 + 10])) break; //if moving down, there is space down, and the spots are not occupied then the com can move down
                    else {
                        d1 = RIGHT;
                        ++accum; //went through a direction
                    }

                }
            }
            if (boost1) {
                if (t1 > 5) { //like a timer, speed boost allowed only 5 times
                    boost1 = false; //the boost is over
                    if (d1 == RIGHT) x1 -= 5; //the offset moves 5 left, as it moved 5 right so it did not overlap with the previous square before the start of the boost
                    if (d1 == DOWN) y1 -= 5;//the offset moves 5 up, as it moved 5 down so it did not overlap with the previous square before the start of the boost
                } else ++t1; //incrementing the "timer"
            }
            if (boost2) {
                if (t2 > 5) { //like a timer, speed boost allowed only 5 times
                    boost2 = false; //boost is over
                    if (d2 == RIGHT) x2 -= 5; //the offset moves 5 left, as it moved 5 right so it did not overlap with the previous square before the start of the boost
                    if (d2 == DOWN) y2 -= 5; //the offset moves 5 up, as it moved 5 down so it did not overlap with the previous square before the start of the boost
                } else ++t2; //incrementing the "timer"
            }
            if (boost1) {//moving faster, therefore the change is more for p1
                if (d1 == RIGHT) x1 += moveFast;
                else if (d1 == UP) y1 -= moveFast;
                else if (d1 == LEFT) x1 -= moveFast;
                else if (d1 == DOWN) y1 += moveFast;
            }
            else {
                if (d1 == RIGHT) x1 += 5;
                else if (d1 == UP) y1 -= 5;
                else if (d1 == LEFT) x1 -= 5;
                else if (d1 == DOWN) y1 += 5;
            }
            if (boost2) {//moving faster, therefore the change is more for p2
                if (d2 == RIGHT) x2 += moveFast;
                else if (d2 == UP) y2 -= moveFast;
                else if (d2 == LEFT) x2 -= moveFast;
                else if (d2 == DOWN) y2 += moveFast;
            }
            if (!boost2) {
                if (d2 == RIGHT) x2 += 5;
                else if (d2 == UP) y2 -= 5;
                else if (d2 == LEFT) x2 -= 5;
                else if (d2 == DOWN) y2 += 5;
            }
        }
        //method checking if a move is valid or if the position is invalid due to out of bounds or collision
        public void check() {
            if (inRound) { //if currently in a round
                if (x1 == x2 && y1 == y2) { //if the positions are the exact same, the match is a tie
                    lost1 = true;
                    lost2 = true;
                }
                if (boost1) {
                    if (d1 % 2 == 0) { //if the d1 is right or left
                        spanx1 = 15; //the span is 15 pixels for left
                        if (d1 == RIGHT) spanx1 *= -1; //the span is -15 pixels for moving right
                        spany1 = 5; //the span for the y is the normal width
                    }
                    else {
                        spanx1 = 5; //the span for the x is the normal width
                        spany1 = 15; //the span is 15 pixels for up
                        if (d1 == DOWN) spany1 *= -1; //the span is -15 pixels for down
                    }
                }
                else {
                    spanx1 = 5; spany1 = 5; //the spans are just the same as normal, 5 and 5
                }
                //same logic as above, but for player 2
                if (boost2) {
                    if (d2 % 2 == 0) {
                        spanx2 = 15;
                        if (d2 == RIGHT) spanx2 *= -1;
                        spany2 = 5;
                    }
                    else {
                        spany2 = 15;
                        if (d2 == DOWN) spany2 *= -1;
                        spanx2 = 5;
                    }
                }
                else {
                    spanx2 = 5; spany2 = 5;
                }
                for (int i = Math.min(x1,x1+spanx1); i < Math.max(x1,x1+spanx1); i++) { //checking the pixels from the minimum of the current x  + spanned and the current (spanned could be backwards) to the other
                    for (int j = Math.min(y1,y1+spany1); j < Math.max(y1,y1+spany1); j++) { //checking the pixels from the minimum of the current y + spanned and the current (spanned could be backwards) to the other
                        if (i <= 0 || i >= 1000 || j <= 0 || j >= 780) lost1 = true; //if the position is outside of the playing room then the player lost
                        else if (occupied[i][j]) lost1 = true; //if the spot was already taken by another colour then also the player lost
                    }
                }
                for (int i = Math.min(x2,x2+spanx2); i < Math.max(x2,x2+spanx2); i++) { //checking the pixels from the minimum of the current x  + spanned and the current (spanned could be backwards) to the other
                    for (int j = Math.min(y2,y2+spany2); j < Math.max(y2,y2+spany2); j++) { //checking the pixels from the minimum of the current y  + spanned and the current (spanned could be backwards) to the other
                        if (i <= 0 || i >= 1000 || j <= 0 || j >= 780) lost2 = true; //if the position is outside of the playing room then the player lost
                        else if (occupied[i][j]) lost2 = true; //if the spot was already taken by another colour then also the player lost
                    }
                }
                for (int i = Math.min(x1,x1+spanx1); i < Math.max(x1,x1+spanx1); i++) { //checking the pixels from the minimum of the current x  + spanned and the current (spanned could be backwards) to the other
                    for (int j = Math.min(y1,y1+spany1); j < Math.max(y1,y1+spany1); j++) { //checking the pixels from the minimum of the current y  + spanned and the current (spanned could be backwards) to the other
                        if (i >= 0 && i <= 1000 && j >= 0 && j <= 780) occupied[i][j] = true; //if the point is inside the grid then we occupy the spot
                    }
                }
                for (int i = Math.min(x2,x2+spanx2); i < Math.max(x2,x2+spanx2); i++) { //checking the pixels from the minimum of the current x  + spanned and the current (spanned could be backwards) to the other
                    for (int j = Math.min(y2,y2+spany2); j < Math.max(y2,y2+spany2); j++) { //checking the pixels from the minimum of the current y  + spanned and the current (spanned could be backwards) to the other
                        if (i >= 0 && i <= 1000 && j >= 0 && j <= 780) occupied[i][j] = true; //if the point is inside the grid then we occupy the spot
                    }
                }

                if (lost1 && !lost2) ++p2; //if only the 1st player lost the second player receives a point
                if (lost2 && !lost1) ++p1; //if only the 1st player lost the second player receives a point
                if (lost1 || lost2) {
                    if (p1 == 3 || p2 == 3) { //if someone has reached 3 points
                        sound.play(); //play the victory sound
                        if (p1 == 3) ++win1; //adding a game point to player 1
                        else if (p2 == 3) ++win2; //adding a game point to player 2
                        inRound = false; //not in a current round
                        beginning = true; //ready to upload grid in the next game
                    }
                    defaultSettings(); //resetting positions and directions
                    //emptying the occupied array
                    for (int i = 0; i < 1001; i++)
                        for (int j = 0; j < 801; j++)
                            occupied[i][j] = false;
                }
            }
        }
        public void keyTyped(KeyEvent e) {
        }
        //checking which key is pressed, and will set directions or boosts based off of the conditions
        public void keyPressed(KeyEvent e) {
            if (inRound) { //if a round is going on
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) d2 = RIGHT;
                else if (e.getKeyCode() == KeyEvent.VK_UP) d2 = UP;
                else if (e.getKeyCode() == KeyEvent.VK_LEFT) d2 = LEFT;
                else if (e.getKeyCode() == KeyEvent.VK_DOWN) d2 = DOWN;
                else if (e.getKeyCode() == KeyEvent.VK_ENTER && remTurbo2 > 0) {
                    boost2 = true;
                    t2 = 0; //timer is 0
                    if (d2 == RIGHT) x2 += 5; //offsetting the x by 5 so that the rectangle just before the boost is not overlapped
                    if (d2 == DOWN) y2 += 5; //offsetting the y by 5 so that the rectangle just before the boost is not overlapped
                    --remTurbo2; //decrementing the remaining turbos for player 2
                }
                if(!com) {
                    if (e.getKeyCode() == KeyEvent.VK_D) d1 = RIGHT;
                    else if (e.getKeyCode() == KeyEvent.VK_W) d1 = UP;
                    else if (e.getKeyCode() == KeyEvent.VK_A) d1 = LEFT;
                    else if (e.getKeyCode() == KeyEvent.VK_S) d1 = DOWN;
                    else if (e.getKeyCode() == KeyEvent.VK_Q && remTurbo1 > 0) {
                        boost1 = true;
                        t1 = 0;
                        if (d1 == RIGHT) x1 += 5; //offsetting the x by 5 so that the rectangle just before the boost is not overlapped
                        if (d1 == DOWN) y1 += 5; //offsetting the y by 5 so that the rectangle just before the boost is not overlapped
                        --remTurbo1; //decrementing the remaining turbos for player 1
                    }
                }
            } else if (e.getKeyCode() == KeyEvent.VK_SPACE) { //if in the post-game menu and player wants to play another game
                inRound = true; //now, the player has started a new round
                p1 = 0;
                p2 = 0;
                if (lost1 && !lost2) --p2; //decrementing the winner's points to 0 because of the collision at the start being the collision at the end
                if (lost2 && !lost1) --p1; //decrementing the winner's points to 0 because of the collision at the start being the collision at the end
            } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) { //desiring to move back to the main menu
                Window win = SwingUtilities.getWindowAncestor(this); //getting the window the panel is at
                win.dispose(); //disposing the window
                Menu frame = new Menu(); //going back to the original menu by going to the new frame
            } else if (e.getKeyCode() == KeyEvent.VK_Q) { //if the user wants to quit
                System.exit(1);
            }
        }

        public void keyReleased(KeyEvent e) {
        }
        //painting the graphics in the game and in the post-game menu
        public void paintComponent(Graphics g) {
            if (!inRound) { //if not in a round, post-game menu
                g.setColor(Color.gray);
                g.fillRect(0, 0, 1000, 1000);
                g.setColor(Color.black);
                if (p1 == 3) {
                    if (com) g.drawImage(comWon, 50, 300, null); //showing text saying that the computer won
                    else g.drawImage(p1Win, 100, 300, null); //showing that the player won
                }
                if (p2 == 3) {
                    if (!com) g.drawImage(p2Win, 100, 300, null); //showing the 2nd player won
                    else g.drawImage(p1Win, 100, 300, null); //if there is a com but the player won, we treat player 2 as player 1, and display the winning output for p1
                }
                g.drawImage(scoreWord, 200, 100, null); //showing the Score:
                int x = 200 + scoreWord.getWidth(null) + 10; //variable x for the starting position of the numbers
                String a = Integer.toString(win1); //bringing it to a string
                for (int i = 0; i < a.length(); i++) { //looping through each digit
                    int idx = (int)(a.charAt(i)) - 48;  //getting the integer value of the digit
                    g.drawImage(numberScores[idx], x, 100, null); //blitting the digit at the x position
                    x += numberScores[idx].getWidth(null); //adding the width of the digit on to x for the next digit
                }
                x += 5; //putting a space of 5 for the dash
                g.drawImage(dash, x, 100 + 18, null); //drawing a dash
                x += 5 + dash.getWidth(null); //adding the width of dash plus an extra 5
                String b = Integer.toString(win2); //the string for the number of victories of player 2
                for (int i = 0; i < b.length(); i++) { //looping through each digit
                    int idx = (int) (b.charAt(i)) - 48; //getting the integer value of the digit
                    g.drawImage(numberScores[idx], x, 100, null); //blitting the digit at the x position
                    x += numberScores[idx].getWidth(null); //adding the width of the digit on to x for the next digit
                }
                g.drawImage(spacePrompt,50,500,null); //drawing the text for the prompt to hit space to play a new game
                g.drawImage(backPrompt,50,575,null);  //drawing the text for the prompt to hit back to return to menu
                g.drawImage(qPrompt,50,650,null);//drawing the text for the prompt to hit q to quit

            } else {
                if (beginning) { //if this is the first instance of the game
                    g.drawImage(grid, 0, 0, null); //drawing the grid on
                    beginning = false; //now it is no longer the first instance of the game
                }
                if (lost1 || lost2) { //if it is resetting after a point was awarded
                    g.drawImage(grid, 0, 0, null); //drawing grid
                    lost1 = false; //resetting the lost variables so the next round can be played
                    lost2 = false; //same as line above
                }
                g.setColor(Color.blue);
                if (boost1 && (d1 == RIGHT || d1 == DOWN)) { //if the direction is right or down, we need to account for the offset
                    if (d1 == RIGHT) g.fillRect(x1-moveFast,y1,moveFast,spany1); //drawing the image with the respective positions, as x1,y1 is the top right, therefore we drew it from the width of the move and the same y, down wit hte same width and height
                    if (d1 == DOWN) g.fillRect(x1,y1-moveFast,spanx1,moveFast); //drawing the image with the respective positions, as x1,y1 is the bottom left, therefore we drew it from the same x and the y - the width, with the enlarged height and the regular width
                }
                else {
                    g.fillRect(x1,y1,spanx1,spany1);
                }

                g.setColor(Color.green);
                if (boost2 && (d2 == RIGHT || d2 == DOWN)) { //if the direction is right or down, we need to account for the offset
                    if (d2 == RIGHT) g.fillRect(x2-moveFast,y2,moveFast,spany2); //drawing the image with the respective positions, as x1,y1 is the top right, therefore we drew it from the width of the move and the same y, down wit hte same width and height
                    if (d2 == DOWN) g.fillRect(x2,y2-moveFast,spanx2,moveFast); //drawing the image with the respective positions, as x2,y2 is the bottom left, therefore we drew it from the same x and the y - the width, with the enlarged height and the regular width
                }
                else {
                    g.fillRect(x2,y2,spanx2,spany2);
                }
                g.setColor(Color.WHITE);
                g.fillRect(0,800,1000,200); //drawing the rectangle at the bottom
                if (p1 == -1) p1 = 0; //if in the negatives (this was a bug fix), set it to 0
                if (p2 == -1) p2 = 0; //if in the negatives (this was a bug fix), set it to 0

                g.drawImage(blues[p1], 50, 800, null); //drawing the number of points for player1
                g.drawImage(greens[p2], 850, 800, null); //drawing the number of points for player1
                g.drawImage(turbos,160,875,null);
                g.drawImage(turbos,640,870,null);
                g.drawImage(whitenums[remTurbo1],200,900,null); //drawing remaining turbos for player 1
                g.drawImage(whitenums[remTurbo2],680,895,null); //drawing remaining turbos for player 2
            }
        }
        //setting default settings
        private void defaultSettings() {
            x1 = 200;
            x2 = 600;
            d1 = RIGHT; //original direction for player 1 is right
            y1 = 300;
            y2 = 300;
            d2 = LEFT; //original direction for player 2 is left
            t1 = 0;
            t2 = 0;
            remTurbo1 = 3; //starting with 3 turbos each
            remTurbo2 = 3;
        }
    }
}