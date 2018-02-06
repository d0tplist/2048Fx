package example;

import com.noxus.Board;
import com.noxus.Integrator;
import com.noxus.Move;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Created by alex on 2/6/18.
 */
public class UsingAIExample extends Application {

    private boolean breakingbad = false;
    private Move move = Move.RIGHT;
    private Board board;
    private Thread currentThread;
    private int best = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        board = new Board(false, true);
        primaryStage.setScene(Integrator.createScene(board));//Please disable the animations
        primaryStage.show();

        board.newGame();

        primaryStage.setOnCloseRequest(event -> {
            breakingbad = true;
        });


        //Nasty code
        Button button = new Button("RUN");
        button.setOnAction(event -> {
            if (currentThread != null && currentThread.isAlive()) {
                breakingbad = true;
            } else {
                breakingbad = false;
                currentThread = new Thread(dummyAi);
                board.newGame();
                currentThread.start();
            }
        });

        ((AnchorPane) primaryStage.getScene().getRoot()).getChildren().add(button);

        AnchorPane.setTopAnchor(button, 10.0);
        AnchorPane.setRightAnchor(button, 10.0);

    }

    private final Thread dummyAi = new Thread(() -> {

        while (true) {

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            move = Move.getNext(move);

            Platform.runLater(()-> {
                if (board.move(move)) {
                    best = Math.max(board.getPoints(), best);
                    System.out.println("Retry with new game score: "+board.getPoints()+" best: "+best);
                    board.newGame();
                }
            });

            if (breakingbad) {
                break;
            }

        }

    });
}
