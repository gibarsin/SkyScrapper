package ar.edu.itba.sia.ui;

import ar.edu.itba.sia.game.SkyscraperState;
import ar.edu.itba.sia.gps.core.GPSNode;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SkyscraperApplication extends Application {

  private static final int PADDING = 10;

  private static Map<GPSNode, GPSNode> childes;
  private static GPSNode currentNode;
  private static long openNodes;
  private static long explodedNodes;
  private static long time;
  private static int cost;
  private static long depth;

  public static void display(final GPSNode finalNode, final int openNodes,
      final long explodedNodes, final long time) {
    if (finalNode == null) {
      launch();
      return;
    }

    SkyscraperApplication.childes = new HashMap<>();
    SkyscraperApplication.currentNode = finalNode;
    SkyscraperApplication.openNodes = openNodes;
    SkyscraperApplication.explodedNodes = explodedNodes;
    SkyscraperApplication.time = time;
    SkyscraperApplication.cost = finalNode.getCost();
    SkyscraperApplication.depth = finalNode.getDepth();

    GPSNode last = null;
    GPSNode curr = finalNode;
    SkyscraperApplication.childes.put(curr, last);
    while (curr != null) {
      last = curr;
      curr = curr.getParent();
      SkyscraperApplication.childes.put(curr, last);
    }

    launch();
  }

  private static Scene getPrimaryScene() {
    if (currentNode != null) {
      return getSolutionFoundPrimaryScene();
    } else {
      return getSolutionNotFoundPrimaryScene();
    }
  }

  private static Scene getSolutionFoundPrimaryScene() {
    final ScrollPane primaryPane = new ScrollPane();
    final HBox hBox = new HBox(10);
    final SkyscraperState lastState = (SkyscraperState) currentNode.getState();
    final SkyscraperBoardPane boardPane = new SkyscraperBoardPane(lastState.getBoard());
    final Button buttonLeft = new Button();
    buttonLeft.setText("<");
    buttonLeft.setMaxHeight(Double.MAX_VALUE);
    final Button buttonRight = new Button();
    buttonRight.setText(">");
    buttonRight.setMaxHeight(Double.MAX_VALUE);
    buttonRight.setDisable(true);
    hBox.getChildren().addAll(buttonLeft, boardPane, buttonRight);

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

    Text explodedNodesText = new Text("Exploded nodes: " + explodedNodes);
    Text openNodesText = new Text("Open nodes: " + openNodes);
    Text costText = new Text("Cost: " + cost);
    Text depthText = new Text("Depth: " + depth);
    Text timeText = new Text("Time: " + time);
    final VBox vBox = new VBox(PADDING, hBox, explodedNodesText, openNodesText, costText, depthText,
        timeText);
    vBox.setAlignment(Pos.CENTER);
    vBox.setPadding(new Insets(PADDING));
    primaryPane.setContent(vBox);

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

    return primaryScene;
  }

  private static Scene getSolutionNotFoundPrimaryScene() {
    final Text text = new Text("Solution not found");
    final VBox primaryPane = new VBox(PADDING, text);
    primaryPane.setAlignment(Pos.CENTER);
    primaryPane.setPadding(new Insets(PADDING));

    final Scene primaryScene = new Scene(primaryPane);
    return primaryScene;
  }

  @Override
  public void start(final Stage primaryStage) throws Exception {
    setUserAgentStylesheet(STYLESHEET_MODENA);
    primaryStage.setTitle("Skyscraper");
    primaryStage.setResizable(true);
    primaryStage.centerOnScreen();
    primaryStage.setOnCloseRequest(event -> Platform.exit());
    primaryStage.setScene(getPrimaryScene());
    primaryStage.show();
  }
}
