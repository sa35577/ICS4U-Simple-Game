import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class McKMenu extends JFrame{
    private JLayeredPane layeredPane=new JLayeredPane();

    public McKMenu() {
        super("Use LayeredPane to put things on top of one another");
        setSize(800,600);

        ImageIcon backPic = new ImageIcon("menu.jpg");
        JLabel back = new JLabel(backPic);
        back.setBounds(0, 0,backPic.getIconWidth(),backPic.getIconHeight());
        layeredPane.add(back,1);

        ImageIcon startPic = new ImageIcon("start.png");
        JButton startBtn = new JButton(startPic);
        startBtn.addActionListener(new ClickStart());
        startBtn.setBounds(300,400,startPic.getIconWidth(),startPic.getIconHeight());
        layeredPane.add(startBtn,2);
        System.out.println(layeredPane);

        setContentPane(layeredPane);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] arguments) {
        McKMenu frame = new McKMenu();
    }

    class ClickStart implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent evt){
            Game4 game = new Game4();
            setVisible(false);
        }


    }
}
