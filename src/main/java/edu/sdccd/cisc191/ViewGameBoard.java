package edu.sdccd.cisc191;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Presents the user with the game graphical user interface
 */
public class ViewGameBoard extends Application
{
    private Canvas gameCanvas;
    private ControllerGameBoard controller;
    private GameBoardLabel fishRemaining;
    private GameBoardLabel guessesRemaining;
    private GameBoardLabel message;

    public static void main(String[] args)
    {
        // TODO: launch the app
        launch();
    }

    public void updateHeader() {
        //TODO update labels
        fishRemaining.setText("Fish: " + controller.modelGameBoard.getFishRemaining());
        guessesRemaining.setText("Bait: " + controller.modelGameBoard.getGuessesRemaining());
        if(controller.fishWin()) {
            //"Fishes win!"
            message.setText("Fishes win!");
        } else if(controller.playerWins()) {
            //"You win!"
            message.setText("You win!");
        } else {
            //"Find the fish!"
            message.setText("Find the fish!");
        }
    }
    @Override
    public void start(Stage stage) throws Exception {
        controller = new ControllerGameBoard();
        // TODO initialize gameCanvas
        gameCanvas = new Canvas();
        fishRemaining = new GameBoardLabel();
        guessesRemaining = new GameBoardLabel();
        message = new GameBoardLabel();

        // TODO display game there are infinite ways to do this, I used BorderPane with HBox and VBox.
        HBox labelBar = new HBox(30, fishRemaining, guessesRemaining, message);
        labelBar.setAlignment(Pos.CENTER);

        VBox columnContainer = new VBox();

        BorderPane root = new BorderPane();
        root.setTop(labelBar);
        root.setCenter(columnContainer);
        root.setMargin(columnContainer, new Insets(10));

        updateHeader();

        for (int row=0; row < ModelGameBoard.DIMENSION; row++) {
            // TODO: create row container
            HBox rowContainer = new HBox();
            rowContainer.setSpacing(5);
            rowContainer.setAlignment(Pos.CENTER);
            for (int col=0; col < ModelGameBoard.DIMENSION; col++) {
                GameBoardButton button = new GameBoardButton(row, col, controller.modelGameBoard.fishAt(row,col));
                int finalRow = row;
                int finalCol = col;
                button.setOnAction(e -> {
                    controller.makeGuess(finalRow, finalCol);
                    if(!controller.isGameOver()) {
                        button.handleClick();
                        updateHeader();
                    }
                });
                // TODO: add button to row
                rowContainer.getChildren().add(button);
                rowContainer.setHgrow(button, Priority.ALWAYS);
            }
            // TODO: add row to column
            columnContainer.getChildren().add(rowContainer);
            columnContainer.setSpacing(5);
            columnContainer.setAlignment(Pos.CENTER);
        }

        // TODO: create scene, stage, set title, and show
        Scene scene = new Scene(root, 400, 400);
        stage.setScene(scene);
        stage.setTitle("Gone Fishing");
        stage.show();
    }
}