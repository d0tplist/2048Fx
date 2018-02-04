package com.noxus;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 2048
 * Created by alex on 2/3/18.
 */
public final class Board extends VBox {

    private final ArrayList<Tile> tiles;
    private final Tile[][] array;
    private final ArrayList<int[][]> history;
    private final SimpleIntegerProperty points;
    private int last = 0;

    public Board() {

        this.tiles = new ArrayList<>();
        ;
        this.array = new Tile[4][4];
        this.history = new ArrayList<>();
        this.points = new SimpleIntegerProperty(0);
        this.points.addListener((observable, oldValue, newValue) -> this.last = newValue.intValue());

        super.setStyle("-fx-background-color: #B3A396");
        super.setSpacing(10);
        super.setAlignment(Pos.CENTER);

        for (int i = 0; i < 4; i++) {
            HBox hBox = new HBox();

            hBox.setSpacing(10);
            hBox.setAlignment(Pos.CENTER);
            hBox.setPrefHeight(60);
            super.getChildren().add(hBox);

            for (int j = 0; j < 4; j++) {
                array[i][j] = new Tile(i, j, 0);
                tiles.add(array[i][j]);
                hBox.getChildren().add(array[i][j]);
            }

        }
    }

    private boolean moved = false;

    private void move(Tile tile, Move move) {
        if (!tile.isFree()) {
            findFarthestPosition(tile, move).ifPresent(found -> {
                found.setValue(tile.getValue());
                tile.reset();
                moved = true;
            });
        }
    }

    private boolean test = false;

    public void test() {
        test = true;

        array[0][0].setValue(2);
        array[0][2].setValue(2);

        array[2][0].setValue(2);
        array[2][2].setValue(2);

        array[2][1].setValue(2);
    }

    private void merge(Tile tile, Move move) {

        if (tile.isFree()) {
            return;
        }

        if (tile.isMerged()) {
            return;
        }

        switch (move) {
            case LEFT:
                if (tile.getY() - 1 >= 0) {
                    if (array[tile.getX()][tile.getY() - 1].getValue() == tile.getValue() && !array[tile.getX()][tile.getY() - 1].isMerged()) {
                        array[tile.getX()][tile.getY() - 1].setValue(tile.getValue() * 2);
                        array[tile.getX()][tile.getY() - 1].setMerged(true);
                        points.setValue(points.intValue() + tile.getValue() * 2);
                        tile.reset();
                    }
                }
                break;
            case RIGHT:
                if (tile.getY() < 3) {
                    if (array[tile.getX()][tile.getY() + 1].getValue() == tile.getValue() && !array[tile.getX()][tile.getY() + 1].isMerged()) {
                        array[tile.getX()][tile.getY() + 1].setValue(tile.getValue() * 2);
                        array[tile.getX()][tile.getY() + 1].setMerged(true);
                        points.setValue(points.intValue() + tile.getValue() * 2);
                        tile.reset();
                    }
                }
                break;
            case UP:
                if (tile.getX() > 0) {
                    if (array[tile.getX() - 1][tile.getY()].getValue() == tile.getValue() && !array[tile.getX() - 1][tile.getY()].isMerged()) {
                        array[tile.getX() - 1][tile.getY()].setValue(tile.getValue() * 2);
                        array[tile.getX() - 1][tile.getY()].setMerged(true);
                        points.setValue(points.intValue() + tile.getValue() * 2);
                        tile.reset();
                    }
                }
                break;
            case DOWN:
                if (tile.getX() < 3) {
                    if (array[tile.getX() + 1][tile.getY()].getValue() == tile.getValue() && !array[tile.getX() + 1][tile.getY()].isMerged()) {
                        array[tile.getX() + 1][tile.getY()].setValue(tile.getValue() * 2);
                        array[tile.getX() + 1][tile.getY()].setMerged(true);
                        points.setValue(points.intValue() + tile.getValue() * 2);
                        tile.reset();
                    }
                }
                break;
        }
    }

    private Optional<Tile> findFarthestPosition(Tile tile, Move move) {
        switch (move) {
            case LEFT:
                return tiles.stream()
                        .filter(r -> r != tile && r.getX() == tile.getX() && r.isFree() && r.getY() < tile.getY() && !r.isMerged())
                        .sorted(Comparator.comparingInt(Tile::getY)).findFirst();
            case RIGHT:
                return tiles.stream()
                        .filter(r -> r != tile && r.getX() == tile.getX() && r.isFree() && r.getY() > tile.getY() && !r.isMerged())
                        .sorted((A, B) -> Integer.compare(A.getY(), B.getY()) * -1)
                        .filter(x -> x.getY() > tile.getY())
                        .findFirst();
            case UP:
                return tiles.stream()
                        .filter(r -> r != tile && r.getY() == tile.getY() && r.isFree() && r.getX() < tile.getX() && !r.isMerged())
                        .sorted(Comparator.comparingInt(Tile::getX)).findFirst();
            case DOWN:
                return tiles.stream()
                        .filter(r -> r != tile && r.getY() == tile.getY() && r.isFree() && r.getX() > tile.getX() && !r.isMerged())
                        .sorted((A, B) -> Integer.compare(A.getX(), B.getX()) * -1)
                        .filter(x -> x.getX() > tile.getX())
                        .findFirst();

            default:
                return Optional.empty();
        }

    }


    private void addRandomTile() {

        if (test) {
            return;
        }

        List<Tile> free = getFreeTiles();
        if (!free.isEmpty()) {
            Tile tile = free.get((int) (Math.random() * free.size()));
            tile.appear();
            tile.setValue(Math.random() > 0.75 ? 4 : 2);
        }
    }

    private void saveHistory() {
        int[][] x = new int[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                x[i][j] = array[i][j].getValue();
            }
        }

        history.add(x);
    }


    private List<Tile> getFreeTiles() {
        return tiles.stream()
                .filter(Tile::isFree)
                .collect(Collectors.toList());
    }

    private void merge(Move move) {
        switch (move) {
            case RIGHT:
                for (int i = 1; i <= 4; i++) {
                    for (int j = 1; j <= 4; j++) {
                        if (array[4 - j][4 - i].getY() < 3) {
                            merge(array[4 - j][4 - i], move);
                        }
                    }
                }
                break;
            case LEFT:
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        merge(array[i][j], move);
                    }
                }
                break;
            case UP:
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        merge(array[j][i], move);
                    }
                }
                break;
            case DOWN:
                for (int i = 1; i <= 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        merge(array[4 - i][j], move);
                    }
                }
                break;
        }
    }

    /**
     * PUBLIC API
     */

    public final int[][] getData() {
        int[][] x = new int[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                x[i][j] = array[i][j].getValue();
            }
        }

        return x;
    }

    public final void reset() {
        tiles.forEach(Tile::reset);
        addRandomTile();
        this.test = false;
        this.points.setValue(0);
    }


    public final void reverse() {
        if (!history.isEmpty()) {
            int[][] x = history.get(history.size() - 1);

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    array[i][j].setValue(x[i][j]);
                }
            }
            System.out.println(last);
            this.points.setValue(points.intValue() - last);
            history.remove(history.size() - 1);
        }
    }


    public final void move(Move move) {

        this.moved = false;
        saveHistory();

        merge(move);

        switch (move) {
            case RIGHT:
                for (int i = 1; i <= 4; i++) {
                    for (int j = 1; j <= 4; j++) {
                        if (array[4 - j][4 - i].getY() < 3) {
                            move(array[4 - j][4 - i], move);
                        }
                    }
                }
                break;
            case LEFT:
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        move(array[i][j], move);
                    }
                }
                break;
            case UP:
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        move(array[j][i], move);
                    }
                }
                break;
            case DOWN:
                for (int i = 1; i <= 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        move(array[4 - i][j], move);
                    }
                }
                break;
        }

        merge(move);


        for (Tile tile : tiles) {
            if (tile.isMerged()) {
                tile.setMerged(false);
                tile.animate();
            }
        }

        if (!getFreeTiles().isEmpty() && moved) {
            addRandomTile();
        } else if (getFreeTiles().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Perdíste");
            alert.setContentText("Sigue intentando");
            alert.showAndWait();
        }
    }

    public final SimpleIntegerProperty pointsProperty() {
        return points;
    }

    public final int getPoints() {
        return points.get();
    }

    public final void print() {
        System.out.println();
        for (int i = 0; i < 4; i++) {
            System.out.println(Arrays.toString(getData()[i]));
        }
    }
}