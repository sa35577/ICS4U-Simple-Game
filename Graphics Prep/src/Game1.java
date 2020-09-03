import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Game1 extends JFrame {
    JButton nButton = new JButton("North");
    JButton sButton = new JButton("South");
    JButton eButton = new JButton("East");
    JButton wButton = new JButton("West");
    JButton cButton = new JButton("Center");
	GamePanel game= new GamePanel();
		
    public Game1() {
		super("Move the Box");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500,500);
		setLayout(new BorderLayout());
		
		add(nButton, BorderLayout.NORTH);
		add(sButton, BorderLayout.SOUTH);
		add(eButton, BorderLayout.EAST);
		add(wButton, BorderLayout.WEST);
		add(game, BorderLayout.CENTER);

		setVisible(true);
    }

    public static void main(String[] arguments) {
		Game1 frame = new Game1();
    }
}

class GamePanel extends JPanel{
	private int boxx,boxy;
	
	public GamePanel(){
	    boxx = 170;
        boxy = 170;	
	}
	
	@Override
	public void paintComponent(Graphics g){
         g.setColor(new Color(255,222,222));  
         g.fillRect(0,0,getWidth(),getHeight());  
         g.setColor(Color.blue);  
         g.fillRect(boxx,boxy,40,40);
    }
}