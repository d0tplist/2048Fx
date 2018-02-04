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

import java.text.DecimalFormat;

/**
 * Created by alex on 2/4/18.
 */
public class Integrator {


    public static JFXPanel createSceneForSwing(Board board){
        JFXPanel jfxPanel = new JFXPanel();
        Platform.setImplicitExit(false);
        jfxPanel.setScene(createScene(board));
        return jfxPanel;
    }

    public static Scene createScene(Board board){
        AnchorPane root = new AnchorPane();
        Label labelPuntos = new Label("Points: 0");
        labelPuntos.setFont(new Font(35));

        HBox hBoxContainer = new HBox();
        hBoxContainer.getChildren().add(labelPuntos);
        hBoxContainer.setAlignment(Pos.CENTER);
        hBoxContainer.setPrefHeight(40);

        root.getChildren().add(hBoxContainer);
        root.getChildren().add(board);

        AnchorPane.setTopAnchor(hBoxContainer, 0.0);
        AnchorPane.setRightAnchor(hBoxContainer, 0.0);
        AnchorPane.setLeftAnchor(hBoxContainer, 0.0);

        AnchorPane.setTopAnchor(board, 50.0);
        AnchorPane.setLeftAnchor(board, 50.0);
        AnchorPane.setBottomAnchor(board, 50.0);
        AnchorPane.setRightAnchor(board, 50.0);

        DecimalFormat format = new DecimalFormat("#,##0");

        board.pointsProperty().addListener((observable, oldValue, newValue) -> {
            labelPuntos.setText("Points: "+format.format(newValue.intValue()));
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
            }else if(event.getCode() == KeyCode.R){
                board.reset();
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
