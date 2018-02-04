package example;

import com.noxus.Board;
import com.noxus.Integrator;
import javafx.application.Application;
import javafx.stage.Stage;


public class MainFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("2048");
        primaryStage.setScene(Integrator.createScene(new Board()));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
