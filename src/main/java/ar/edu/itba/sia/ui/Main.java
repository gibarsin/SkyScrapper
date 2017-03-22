package ar.edu.itba.sia.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class Main extends Application {
  private static final int BOARD_SIZE = 4;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    setUserAgentStylesheet(STYLESHEET_MODENA);
    primaryStage.setTitle("Skyscraper");
    primaryStage.setResizable(true);
    primaryStage.centerOnScreen();
    primaryStage.setOnCloseRequest(event -> System.exit(0));

    ScrollPane primaryPane = new ScrollPane();
    BoardPane boardPane = new BoardPane(BOARD_SIZE);
    primaryPane.setContent(boardPane);

    Scene primaryScene = new Scene(primaryPane);
    primaryStage.setScene(primaryScene);

    primaryStage.show();
  }
}
