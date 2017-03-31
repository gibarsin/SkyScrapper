package ar.edu.itba.sia.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class SkyscraperJavaxUI extends Application {
  private static final int BOARD_SIZE = 4; // TODO Remove, for testing-purposes only

  @Override
  public void start(Stage primaryStage) throws Exception {
    setUserAgentStylesheet(STYLESHEET_MODENA);
    primaryStage.setTitle("Skyscraper");
    primaryStage.setResizable(true);
    primaryStage.centerOnScreen();
    primaryStage.setOnCloseRequest(event -> Platform.exit());

    ScrollPane primaryPane = new ScrollPane();
    SkyscraperBoardPane skyscraperBoardPane = new SkyscraperBoardPane(BOARD_SIZE);
    primaryPane.setContent(skyscraperBoardPane);

    Scene primaryScene = new Scene(primaryPane);
    primaryStage.setScene(primaryScene);

    primaryStage.show();
  }
}
