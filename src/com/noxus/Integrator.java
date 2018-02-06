package com.noxus;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.function.Supplier;

/**
 * Created by alex on 2/4/18.
 */
public class Integrator {


    public static void createSceneForSwing(Container container, Supplier<Board> supplier){
        JFXPanel jfxPanel = new JFXPanel();
        container.add(jfxPanel);
        Platform.setImplicitExit(false);
        Platform.runLater(()-> jfxPanel.setScene(createScene(supplier.get())));
    }

    public static Scene createScene(Board board){
        AnchorPane root = new AnchorPane();
        Label labelPuntos = new Label("Points: 0");
        Label labelMoves = new Label("Moves: 0");
        Label labelInstructions = new Label("Press N for new game, B for back");

        labelInstructions.setAlignment(Pos.CENTER);
        labelPuntos.setFont(new Font(25));
        labelMoves.setFont(new Font(25));

        HBox hBoxContainer = new HBox();
        hBoxContainer.getChildren().add(labelPuntos);
        hBoxContainer.getChildren().add(labelMoves);
        hBoxContainer.setAlignment(Pos.CENTER);
        hBoxContainer.setPrefHeight(40);
        hBoxContainer.setSpacing(20.0);

        root.getChildren().add(hBoxContainer);
        root.getChildren().add(board);
        root.getChildren().add(labelInstructions);

        AnchorPane.setTopAnchor(hBoxContainer, 0.0);
        AnchorPane.setRightAnchor(hBoxContainer, 0.0);
        AnchorPane.setLeftAnchor(hBoxContainer, 0.0);

        AnchorPane.setTopAnchor(board, 50.0);
        AnchorPane.setLeftAnchor(board, 50.0);
        AnchorPane.setBottomAnchor(board, 50.0);
        AnchorPane.setRightAnchor(board, 50.0);

        AnchorPane.setBottomAnchor(labelInstructions, 10.0);
        AnchorPane.setLeftAnchor(labelInstructions, 10.0);
        AnchorPane.setRightAnchor(labelInstructions, 10.0);

        DecimalFormat format = new DecimalFormat("#,##0");

        board.pointsProperty().addListener((observable, oldValue, newValue) -> {
            labelPuntos.setText("Points: "+format.format(newValue.intValue()));
            labelMoves.setText("Moves: "+board.getMoves());
        });

        Scene scene = new Scene(root, 500,500);

        scene.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.UP){
                board.move(Move.UP);
            }else if(event.getCode() == KeyCode.DOWN){
                board.move(Move.DOWN);
            }else if(event.getCode() == KeyCode.RIGHT){
                board.move(Move.RIGHT);
            }else if(event.getCode() == KeyCode.LEFT){
                board.move(Move.LEFT);
            }else if(event.getCode() == KeyCode.N){
                board.newGame();
            }else if(event.getCode() == KeyCode.P){
                board.print();
            }else if(event.getCode() == KeyCode.T){
                board.test();
            }else if(event.getCode() == KeyCode.B){
                board.reverse();
            }
        });

        return scene;
    }

}
