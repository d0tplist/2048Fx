package com.noxus;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

/**
 * Dificil de hackear :P
 * <p>
 * Created by alex on 2/3/18.
 */
final class Tile extends Label {

    private final int x;
    private final int y;
    private volatile int value;
    private ScaleTransition scale;
    private ScaleTransition fade;

    private boolean merged = false;

    Tile(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;

        super.setPrefSize(60, 60);
        super.setMinSize(60, 60);
        super.setAlignment(Pos.CENTER);

        scale = new ScaleTransition(Duration.millis(150), this);
        scale.setToX(1.3);
        scale.setToY(1.3);
        scale.setCycleCount(2);
        scale.setAutoReverse(true);

        fade = new ScaleTransition(Duration.millis(150), this);
        fade.setFromX(0.2);
        fade.setFromY(0.2);
        fade.setToY(1);
        fade.setToX(1);
        
        fade.setCycleCount(1);

        setUI();
    }

    void setMerged(boolean merged) {
        this.merged = merged;
    }

    boolean isMerged() {
        return merged;
    }

    void appear() {
        if (value == 0) {
            fade.play();
        }
    }

    void animate() {
        if (value != 0) {
            scale.play();
        }
    }

    final void reset() {
        this.value = 0;
        this.merged = false;
        setUI();
    }

    final boolean isFree() {
        return value == 0;
    }

    private void setUI() {

        super.setText(value > 0 ? "" + value : "");

        switch (value) {
            case 0:
                setStyle("-fx-text-fill: #eee4da; -fx-background-color: #C7B8AC; -fx-background-radius: 3px; -fx-font-size: 45px;");
                break;
            case 2:
                setStyle("-fx-text-fill: #6C635A; -fx-background-color: #EBE0D5; -fx-background-radius: 3px; -fx-font-size: 45px;");
                break;
            case 4:
                setStyle("-fx-text-fill: #6C635A; -fx-background-color: #EADABE; -fx-background-radius: 3px; -fx-font-size: 45px;");
                break;
            case 8:
                setStyle("-fx-text-fill: #F8F4F0; -fx-background-color: #f2b179; -fx-background-radius: 3px; -fx-font-size: 45px;");
                break;
            case 16:
                setStyle("-fx-text-fill: #F8F4F0; -fx-background-color: #f59563; -fx-background-radius: 3px; -fx-font-size: 35px;");
                break;
            case 32:
                setStyle("-fx-text-fill: #F8F4F0; -fx-background-color: #f67c5f; -fx-background-radius: 3px; -fx-font-size: 35px;");
                break;
            case 64:
                setStyle("-fx-text-fill: #F8F4F0; -fx-background-color: #f65e3b; -fx-background-radius: 3px; -fx-font-size: 35px;");
                break;
            case 128:
                setStyle("-fx-text-fill: #F8F4F0; -fx-background-color: #edcf72; -fx-background-radius: 3px; -fx-font-size: 30px;");
                break;
            case 256:
                setStyle("-fx-text-fill: #F8F4F0; -fx-background-color: #edcc61; -fx-background-radius: 3px; -fx-font-size: 30px;");
                break;
            case 512:
                setStyle("-fx-text-fill: #F8F4F0; -fx-background-color: #edc850; -fx-background-radius: 3px; -fx-font-size: 30px;");
                break;
            case 1024:
                setStyle("-fx-text-fill: #F8F4F0; -fx-background-color: #edc53f; -fx-background-radius: 3px; -fx-font-size: 20px;");
                break;
            case 2048:
                setStyle("-fx-text-fill: #F8F4F0; -fx-background-color: #edc22e; -fx-background-radius: 3px; -fx-font-size: 20px;");
                break;
            default:
                setStyle("-fx-text-fill: #3c3a32; -fx-background-color: #f9f6f2; -fx-background-radius: 3px; -fx-font-size: 45px;");
                break;
        }
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    int getValue() {
        return value;
    }

    void setValue(int value) {
        this.value = value;
        setTooltip(new Tooltip(toString()+" "+merged));
        setUI();
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", value=" + value+", merged="+merged;
    }
}
