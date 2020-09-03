import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class Game5 extends JFrame implements ActionListener{
    Timer myTimer;
    GamePanel5 game;

    public Game5() {
        super("Move the Box");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,650);
        myTimer = new Timer(10, this);
        myTimer.start();
        game = new GamePanel5();
        add(game);
        setResizable(false);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent evt){
        if(game!= null && game.ready){
            game.repaint();
        }
    }

    public static void main(String[] arguments) {
        Game5 frame = new Game5();
    }
}

class GamePanel5 extends JPanel{
    BufferedImage back = null;
    public boolean ready=false;
    int [][]used=new int[800][600];

    public GamePanel5(){
        try {
            back = ImageIO.read(new File("OuterSpace.jpg"));
        }
        catch (IOException e) {
        }
        setSize(800,600);
    }

    public void addNotify() {
        super.addNotify();
        requestFocus();
        ready = true;
    }


    public void paintComponent(Graphics g){
        int red,green,blue,px,py,r2,g2,b2,c,c2=0;

        for(int i=0; i<500; i++){
            px = (int)(Math.random()*800);
            py = (int)(Math.random()*600);
            if(used[px][py]==0){
                used[px][py]=1;
                c = back.getRGB(px,py);
                red = (c >> 16) & 0xFF;
                green = (c >> 8) & 0xFF;
                blue = c & 0xFF;
                r2 = Math.min(255,(int)(red * .393 + green *.769 + blue * .189));
                g2 = Math.min(255,(int)(red * .349 + green *.686 + blue * .168));
                b2 = Math.min(255,(int)(red * .272 + green *.534 + blue * .131));
                c2 = r2 << 16 | g2 << 8 | b2;
                back.setRGB(px,py,c2);
            }
        }
        if(g!= null){
            g.drawImage(back,0,0,this);
        }
    }
}