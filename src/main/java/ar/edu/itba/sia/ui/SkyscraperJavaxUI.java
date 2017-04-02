package ar.edu.itba.sia.ui;

import ar.edu.itba.sia.game.SkyscraperState;
import ar.edu.itba.sia.gps.core.GPSNode;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class SkyscraperJavaxUI extends Application {

  private static Map<GPSNode, GPSNode> childes;
  private static GPSNode currentNode;

  public static void display(final GPSNode finalNode, final long explodedNodes) {
    Objects.requireNonNull(finalNode);

    SkyscraperJavaxUI.childes = new HashMap<>();
    SkyscraperJavaxUI.currentNode = finalNode;

    GPSNode last = null;
    GPSNode curr = finalNode;
    SkyscraperJavaxUI.childes.put(curr, last);
    while (curr != null) {
      last = curr;
      curr = curr.getParent();
      SkyscraperJavaxUI.childes.put(curr, last);
    }

    launch();
  }

  @Override
  public void start(final Stage primaryStage) throws Exception {
    setUserAgentStylesheet(STYLESHEET_MODENA);
    primaryStage.setTitle("Skyscraper");
    primaryStage.setResizable(true);
    primaryStage.centerOnScreen();
    primaryStage.setOnCloseRequest(event -> Platform.exit());

    final ScrollPane primaryPane = new ScrollPane();
    final HBox secondaryPane = new HBox();
    final SkyscraperState lastState = (SkyscraperState) currentNode.getState();
    final SkyscraperBoardPane boardPane = new SkyscraperBoardPane(lastState.getBoard());
    final Button buttonLeft = new Button();
    buttonLeft.setText("<");
    buttonLeft.setMaxHeight(Double.MAX_VALUE);
    final Button buttonRight = new Button();
    buttonRight.setText(">");
    buttonRight.setMaxHeight(Double.MAX_VALUE);
    buttonRight.setDisable(true);
    secondaryPane.getChildren().addAll(buttonLeft, boardPane, buttonRight);

    buttonLeft.setOnAction(event -> {
      final GPSNode auxNode = currentNode.getParent();

      if (auxNode != null) {
        currentNode = auxNode;

        if (currentNode.getParent() == null) {
          buttonLeft.setDisable(true);
        }
        buttonRight.setDisable(false);

        SkyscraperState state = (SkyscraperState) currentNode.getState();
        boardPane.display(state.getBoard());
      }
    });
    buttonRight.setOnAction(event -> {
      final GPSNode auxNode = childes.get(currentNode);

      if (auxNode != null) {
        currentNode = auxNode;

        if (childes.get(currentNode) == null) {
          buttonRight.setDisable(true);
        }
        buttonLeft.setDisable(false);

        SkyscraperState state = (SkyscraperState) currentNode.getState();
        boardPane.display(state.getBoard());
      }
    });

    primaryPane.setContent(secondaryPane);
    final Scene primaryScene = new Scene(primaryPane);
    primaryPane.setOnKeyPressed(event -> {
      switch (event.getCode()) {
        case LEFT:
          buttonLeft.getOnAction().handle(null);
          break;
        case RIGHT:
          buttonRight.getOnAction().handle(null);
          break;
      }
    });

    primaryStage.setScene(primaryScene);
    primaryStage.show();
  }
}
