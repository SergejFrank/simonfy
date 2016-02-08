package sample;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;

public class Controller {

    public Button start;
    public Label score;
    public Rectangle red;
    public Rectangle blue;
    public Rectangle green;
    public Rectangle yellow;
    private SimonGame game;

    public void startGame(ActionEvent actionEvent) {
        start.setVisible(false);
        initializeGame();
    }

    private void initializeGame() {
        game = new SimonGame(red, blue, green, yellow, start, score);
        game.startGame();
    }

    public void redClicked(Event event) {
        game.addPlayerSequenze(red);
    }

    public void blueClicked(Event event) {
        game.addPlayerSequenze(blue);
    }

    public void greenClicked(Event event) {
        game.addPlayerSequenze(green);
    }

    public void yellowClicked(Event event) {
        game.addPlayerSequenze(yellow);
    }
}
