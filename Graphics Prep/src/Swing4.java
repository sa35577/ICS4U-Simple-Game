import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class Swing4 extends JFrame implements ActionListener {
    JButton calc = new JButton("Calculate");
    JTextField inches = new JTextField(5);
    JLabel cm = new JLabel("            ");

    public Swing4() {
        super("Title Bar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        calc.addActionListener(this);

        FlowLayout flow = new FlowLayout();
        setLayout(flow);

        add(new JLabel("Inches:"));
        add(inches);
        add(new JLabel("Cenimeters:"));
        add(cm);
        add(calc);
        pack();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent evt) {
        double in= Double.parseDouble(inches.getText());
        cm.setText("" + in * 2.54);
    }

    public static void main(String[] arguments) {
        Swing4 frame = new Swing4();
    }
}
