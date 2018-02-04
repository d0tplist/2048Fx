package example;

import com.noxus.Board;
import com.noxus.Integrator;
import javafx.application.Platform;

import javax.swing.*;
import java.awt.*;

public class MainSwing {


    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        jFrame.setLayout(new BorderLayout());
        Integrator.createSceneForSwing(jFrame);

        jFrame.setTitle("2048");
        jFrame.setPreferredSize(new Dimension(500,500));
        jFrame.setMinimumSize(jFrame.getPreferredSize());
        jFrame.setLocationRelativeTo(null);
        SwingUtilities.invokeLater(()-> jFrame.setVisible(true));
    }
}
