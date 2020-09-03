import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class Swing19 extends JFrame implements ActionListener
{
    JButton b1, b2, b3,b4;
    Timer visTimer;
    JPanel opt1,opt2;
    int current = 2;

    public Swing19 ()
    {
        super ("Ummm ... Ugly stuff ... I Love Freedom");
        Container pane = getContentPane();

        pane.setLayout(null);
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        setSize (getToolkit ().getScreenSize ());
        b1 = new JButton ("With");
        b1.setSize (150, 45);
        b1.setLocation (150, 55);
        b2 = new JButton ("Freedom");
        b2.setSize (110, 115);
        b2.setLocation (250, 455);
        b3 = new JButton ("Comes");
        b3.setSize (170, 45);
        b3.setLocation (350, 255);
        b4 = new JButton ("Responsibility");
        b4.setSize (170, 25);
        b4.setLocation (450, 355);
        b4.addActionListener(this);
        b1.addActionListener(this);
        b2.setEnabled(false);
        opt1 = new JPanel();
        opt1.setSize (100, 200);
        opt1.setLocation (800, 200);
        opt1.setBackground(new Color(255,111,111));
        opt2 = new JPanel();
        opt2.setSize (100, 200);
        opt2.setLocation (800, 200);
        opt2.setBackground(new Color(111,255,111));

        opt1.add(new JLabel("Tmp Stuff"));
        opt1.add(new JLabel("use option buttons"));
        opt1.add(new JButton("Click"));
        opt2.add(new JLabel("New options"));
        opt2.add(new JLabel("playing with visible"));
        opt2.setVisible(false);
        pane.add (b1);
        pane.add (b2);
        pane.add (b3);
        pane.add (b4);
        pane.add (opt1);
        pane.add (opt2);
        visTimer = new Timer(1000,this);
        setVisible (true);
    }

    public void actionPerformed(ActionEvent ev){
        if(ev.getSource()==b1){
            visTimer.start();
        }
        if(ev.getSource()==b4){
            if(current==1){
                current=2;
                opt1.setVisible(true);
                opt2.setVisible(false);
            }
            else{
                current=1;
                opt2.setVisible(true);
                opt1.setVisible(false);
            }
        }
        if(ev.getSource()==visTimer){
            b2.setEnabled(true);
            visTimer.stop();
        }

    }


    public static void main (String [] arguments)
    {
        Swing19 frame = new Swing19 ();
    }
}