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
 * twitter: @d0tplist
 *
 * Tested on OSX and Windows
 */
public final class Board extends VBox {

    private final ArrayList<Tile> tiles;
    private final Tile[][] array;
    private final ArrayList<int[][]> history;
    private final SimpleIntegerProperty points;
    private int last = 0;
    private boolean aimode = false;

    public Board(){
        this(true, false);
    }

    /**
     * AI mode disables the Alert dialog
     * @param animations
     * @param aimode
     */
    public Board(boolean animations, boolean aimode) {
        this.aimode = aimode;
        this.tiles = new ArrayList<>();
        this.array = new Tile[4][4];
        this.history = new ArrayList<>();
        this.points = new SimpleIntegerProperty(0);

        super.setStyle("-fx-background-color: #B3A396; -fx-background-radius: 10px;");
        super.setSpacing(10);
        super.setAlignment(Pos.CENTER);

        for (int i = 0; i < 4; i++) {
            HBox hBox = new HBox();

            hBox.setSpacing(10);
            hBox.setAlignment(Pos.CENTER);
            hBox.setPrefHeight(60);
            super.getChildren().add(hBox);

            for (int j = 0; j < 4; j++) {
                if(animations) {
                    array[i][j] = new TileComponent(i, j, 0);
                }else{
                    array[i][j] = new TileComponent(i, j, 0){
                        @Override
                        public void animate() {

                        }

                        @Override
                        public void appear() {

                        }
                    };
                }
                tiles.add(array[i][j]);
                hBox.getChildren().add((TileComponent)array[i][j]);
            }

        }
    }

    private boolean moved = false;
    private boolean merged = false;
    private boolean test = false;

    private void move(Tile tile, Move move) {
        if (!tile.isFree()) {
            findFarthestPosition(tile, move).ifPresent(found -> {
                found.setValue(tile.getValue());
                found.setMerged(tile.isMerged());
                tile.reset();
                moved = true;
            });
        }
    }

    public void test() {
        test = true;

        int[][] x = new int[4][];

        x[0] = new int[]{4, 0, 0, 0};
        x[1] = new int[]{2, 0, 0, 0};
        x[2] = new int[]{0, 0, 0, 0};
        x[3] = new int[]{2, 0, 0, 0};
        history.add(x);


        reverse();

        array[0][1].setMerged(true);
        array[0][1].setValue(array[0][1].getValue());
        System.out.println(array[0][1]);
    }

    private void addPoints(int value) {
        this.last = value;
        this.points.setValue(value + points.intValue());
        this.merged = true;
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
                        addPoints(tile.getValue() * 2);
                        tile.reset();
                    }
                }
                break;
            case RIGHT:
                if (tile.getY() < 3) {
                    if (array[tile.getX()][tile.getY() + 1].getValue() == tile.getValue() && !array[tile.getX()][tile.getY() + 1].isMerged()) {
                        array[tile.getX()][tile.getY() + 1].setValue(tile.getValue() * 2);
                        array[tile.getX()][tile.getY() + 1].setMerged(true);
                        addPoints(tile.getValue() * 2);
                        tile.reset();
                    }
                }
                break;
            case UP:
                if (tile.getX() > 0) {
                    if (array[tile.getX() - 1][tile.getY()].getValue() == tile.getValue() && !array[tile.getX() - 1][tile.getY()].isMerged()) {
                        array[tile.getX() - 1][tile.getY()].setValue(tile.getValue() * 2);
                        array[tile.getX() - 1][tile.getY()].setMerged(true);
                        addPoints(tile.getValue() * 2);
                        tile.reset();
                    }
                }
                break;
            case DOWN:
                if (tile.getX() < 3) {
                    if (array[tile.getX() + 1][tile.getY()].getValue() == tile.getValue() && !array[tile.getX() + 1][tile.getY()].isMerged()) {
                        array[tile.getX() + 1][tile.getY()].setValue(tile.getValue() * 2);
                        array[tile.getX() + 1][tile.getY()].setMerged(true);
                        addPoints(tile.getValue() * 2);
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

    private void moveTiles(Move move){
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

    public final void newGame() {
        history.clear();

        tiles.forEach(Tile::reset);

        addRandomTile();
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

            history.remove(history.size() - 1);
            this.points.setValue(Math.max(points.intValue() - last, 0));
            last = 0;
        }
    }


    /**
     * @param move
     * @return true if you loose
     */
    public final boolean move(Move move) {

        tiles.forEach(r -> r.setMerged(false));
        this.moved = false;
        this.merged = false;

        saveHistory();

        merge(move);

        moveTiles(move);

        merge(move);

        if(merged) {
            moveTiles(move);
        }

        for (Tile tile : tiles) {
            if (tile.isMerged()) {
                tile.setMerged(false);
                tile.animate();
            }
        }

        if ((!getFreeTiles().isEmpty() && moved) || merged) {
            addRandomTile();
        } else if (getFreeTiles().isEmpty()) {
            if(!aimode) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Perdíste");
                alert.setContentText("Sigue intentando");
                alert.showAndWait();
            }
            return true;
        }

        return false;
    }

    public final int getMoves() {
        return history.size();
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
            System.out.println("x["+i+"] = new int[]"+Arrays.toString(getData()[i]).replace("[", "{").replace("]", "}")+";");
        }
    }
}
