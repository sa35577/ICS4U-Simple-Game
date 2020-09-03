import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;

public class test extends JFrame implements ActionListener {
    JButton start = new JButton("Start");
    Rectangle startRect;
    testPanel pn;
    public test() {
        super("test");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000,800);
        startRect = new Rectangle(50,50,100,100);
        start.setBounds(startRect);
        pn = new testPanel();
        add(start);
        add(pn);
        setVisible(true);
        setResizable(false);

    }
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == start)
            pn.repaint();
    }



    public static void main(String[] args) {test TEST = new test();}
}
class testPanel extends JPanel {
    public void paintComponent(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(300,300,700,500);
    }

}
