package example;

import com.noxus.Board;
import com.noxus.Integrator;
import com.noxus.Move;
import javafx.application.Platform;

import javax.swing.*;
import java.awt.*;

public class MainSwing {


   //static Board board;

    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        jFrame.setLayout(new BorderLayout());
        Integrator.createSceneForSwing(jFrame, Board::new);

        /*
        //Integrator for dummies
        Integrator.createSceneForSwing(jFrame, ()->{
             board = new Board();
             return board;
        });
        */

        jFrame.setTitle("2048");
        jFrame.setPreferredSize(new Dimension(500,500));
        jFrame.setMinimumSize(jFrame.getPreferredSize());
        jFrame.setLocationRelativeTo(null);
        SwingUtilities.invokeLater(()-> jFrame.setVisible(true));

        /*
         //To move the board from a Swing application please use Platform.RunLater
         Platform.runLater(()-> board.move(Move.DOWN));
        */
    }
}
