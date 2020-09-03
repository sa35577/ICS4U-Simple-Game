import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class Swing3 extends JFrame implements ActionListener {
    JButton bStay = new JButton("Stay");
    JButton bGo = new JButton("Go");

    public Swing3() {
        super("Title Bar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        bStay.addActionListener(this);
        bGo.addActionListener(this);
        FlowLayout flow = new FlowLayout();
        setLayout(flow);


        add(bStay);
        add(bGo);

        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        Object source = evt.getSource();
        if (source == bStay) {
            setTitle("Stay");
        } else if (source == bGo) {
            setTitle("Go");
        }
    }

    public static void main(String[] arguments) {
        Swing3 frame = new Swing3();
    }
}
