import javax.swing.*;

public class Swing2 extends JFrame
{
    JButton load = new JButton("Load");
    JButton save = new JButton("Save");
    JButton exit = new JButton("Exit");

    public Swing2 (){
        super("Fun with buttons");

        setSize (80,170);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel pane = new JPanel();
        pane.add(load);
        pane.add(save);
        pane.add(exit);
        add(pane);
        setVisible (true);
    }

    public static void main(String[] args){
        Swing2 sButtons = new Swing2();
    }
}
