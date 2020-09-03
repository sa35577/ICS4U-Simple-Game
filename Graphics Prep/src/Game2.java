import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Game2 extends JFrame implements ActionListener{
    JButton nButton = new JButton("North");
    JButton sButton = new JButton("South");
    JButton eButton = new JButton("East");
    JButton wButton = new JButton("West");
    JButton cButton = new JButton("Center");
    GamePanel2 game= new GamePanel2();

    public Game2() {
        super("Move the Box");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,500);
        setLayout(new BorderLayout());

        nButton.addActionListener(this);
        sButton.addActionListener(this);
        eButton.addActionListener(this);
        wButton.addActionListener(this);

        add(nButton, BorderLayout.NORTH);
        add(sButton, BorderLayout.SOUTH);
        add(eButton, BorderLayout.EAST);
        add(wButton, BorderLayout.WEST);
        add(game, BorderLayout.CENTER);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent evt){
        Object source = evt.getSource();
        if(source == nButton){
            game.move(0,-5);
        }
        else if(source == sButton){
            game.move(0,5);
        }
        else if(source == eButton){
            game.move(5,0);
        }
        else if(source == wButton){
            game.move(-5,0);
        }
    }

    public static void main(String[] arguments) {
        Game2 frame = new Game2();

    }
}

class GamePanel2 extends JPanel{
    private int boxx,boxy;

    public GamePanel2(){
        boxx = 170;
        boxy = 170;
    }

    public void move(int dx,int dy){
        boxx += dx;
        boxy += dy;
        repaint();
    }

    public void paintComponent(Graphics g){
        g.setColor(new Color(255,222,222));
        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(Color.blue);
        g.fillRect(boxx,boxy,40,40);
    }
}