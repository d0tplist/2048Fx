package com.noxus;

public interface Tile {

    void setValue(int value);

    int getX();

    int getY();

    int getValue();

    void setUI();

    boolean isMerged();

    void setMerged(boolean merged);

    void animate();

    void appear();

    default boolean isFree(){
        return getValue() == 0;
    }

    default void reset() {
        setValue(0);
        setMerged(false);
    }
}
