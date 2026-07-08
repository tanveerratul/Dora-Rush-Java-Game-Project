package dora.rush;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Tanveer
 */
public class DoraRush extends JFrame {

    public DoraRush() {
        add(new Model());
    }

    public static void main(String[] args) throws InterruptedException {

        

       /* JLabel label = new JLabel();
        label.setBounds(WIDTH, WIDTH, 1000, 1000);
          label.setIcon(intro);
            JPanel panel = new JPanel();
        //panel.setBackground(Color.red);*/
        DoraRush dora = new DoraRush();
        dora.setVisible(true);
        dora.setTitle("Dora Rush");
        dora.setSize(1000, 1500);
        dora.setDefaultCloseOperation(EXIT_ON_CLOSE);
        dora.setLocationRelativeTo(null);
      
        ImageIcon intro = new ImageIcon("\\src\\DoraRush.jpg");
       
        /* dora.add(label);
         dora.add(panel);
         Thread.sleep(1500);
         label.setVisible(false);
         panel.setVisible(false);
*/
    }

}
