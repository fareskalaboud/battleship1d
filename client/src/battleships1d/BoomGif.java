package battleships1d;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class BoomGif {

    public static void main(String[] args) {
        new BoomGif();
    }

    public BoomGif() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame("Testing");
                frame.setUndecorated(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(new TestPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class TestPane extends JPanel {

        private ImageIcon spin;
        private ImageIcon still;

        private JLabel label;
        private Timer timer;
        private JButton button;

        public TestPane() {
        	this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 10, true));
            spin = new ImageIcon("boom.gif");
            still = new ImageIcon("testW.png");

            label = new JLabel(still);
            setSize(400, 400);
          
            label.setIcon(spin);

            timer = new Timer(2500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    label.setIcon(still);
                    System.exit(0);
                }
            });
            timer.setRepeats(false);
            timer.restart();
            setLayout(new BorderLayout());
            add(label);
        }
    }

}
