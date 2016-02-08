package sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Rectangle;

public class SimonGame {

    private Button startButton;
    private ArrayList<Rectangle> colors = new ArrayList<Rectangle>();
    private Map soundMap = new HashMap();
    private ArrayList<Rectangle> sequenz = new ArrayList<Rectangle>();
    private ArrayList<Rectangle> playerSequenz = new ArrayList<Rectangle>();
    private Label scoreLabel;
    private Random rand;
    private boolean gameOver;

    public SimonGame(final Rectangle red, final Rectangle blue, final Rectangle green, final Rectangle yellow, final Button start,
                     final Label score) {
        colors.add(red);
        colors.add(blue);
        colors.add(green);
        colors.add(yellow);

        soundMap.put(red, "red");
        soundMap.put(blue, "blue");
        soundMap.put(green, "green");
        soundMap.put(yellow, "yellow");

        startButton = start;
        scoreLabel = score;
        setColorsDisable(false);
    }

    private void setColorsDisable(Boolean status) {
        for (Rectangle color : colors) {
            color.setDisable(status);
        }
    }

    public void startGame() {
        gameOver = false;
        sequenz.clear();
        playerSequenz.clear();
        addToSequenz();
    }

    private void addToSequenz() {
        scoreLabel.setText("Score: " + sequenz.size());
        setColorsDisable(true);
        playerSequenz.clear();
        rand = new Random();
        Rectangle color = colors.get(rand.nextInt(colors.size()));
        sequenz.add(color);
        playSequenz();
    }

    private void playSound(String soundName) {
        try {
            AudioClip sound = new AudioClip(getClass().getResource(soundName + ".aiff").toURI().toString());
            sound.play();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void playColorSound(Rectangle color) {
        final String soundName = soundMap.get(color).toString();
        playSound(soundName);
    }

    public void highlightColor(final Rectangle color, final int sleep) {
        final Task task = new Task<Void>() {
            @Override
            public Void call() {
                playColorSound(color);
                color.setOpacity(0.5);
                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                color.setOpacity(1);
                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        new Thread(task).start();
    }

    private void playSequenz() {
        final Task task = new Task<Void>() {
            @Override
            public Void call() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (Rectangle color : sequenz) {

                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    highlightColor(color, 300);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                setColorsDisable(false);
                return null;
            }
        };

        new Thread(task).start();
    }

    public void addPlayerSequenze(Rectangle playerColor) {
        playerSequenz.add(playerColor);
        highlightColor(playerColor, 300);
        checkPlayerSequenze();
    }

    private void checkPlayerSequenze() {
        Iterator<Rectangle> playerItr = playerSequenz.iterator();
        Iterator<Rectangle> seqItr = sequenz.iterator();
        while (playerItr.hasNext() && seqItr.hasNext()) {
            Rectangle playerColor = playerItr.next();
            Rectangle color = seqItr.next();

            if (!playerColor.equals(color)) {
                gameOver();
            }
        }

        if (playerSequenz.size() == sequenz.size() && !gameOver) {
            addToSequenz();
        }
    }

    private void gameOver() {
        gameOver = true;
        startButton.setVisible(true);
        System.out.println("Game Over");
    }
}
