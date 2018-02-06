package com.noxus;

/**
 * Created by alex on 2/3/18.
 */
public enum Move {
    LEFT, RIGHT, UP, DOWN;

    public static Move getNext(Move move) {//For dummy AI
        switch (move) {
            case DOWN:
                return LEFT;
            case UP:
                return RIGHT;
            case LEFT:
                return UP;
            case RIGHT:
                return DOWN;
            default:
                return UP;
        }
    }
}
