import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class SwingTimer extends JFrame implements ActionListener {
    JLabel tickLabel = new JLabel("    ");
    int tick=0;

    public SwingTimer() {
        super("Tick Tock");
        Timer myTimer = new Timer(100, this);

        setSize( new Dimension(400, 400) );

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(tickLabel);

        myTimer.start();
        setVisible(true);
    }


    public void actionPerformed(ActionEvent evt) {
        tickLabel.setText("" + tick++);
    }

    public static void main(String[] arguments) {
        SwingTimer frame = new SwingTimer();
    }
}
