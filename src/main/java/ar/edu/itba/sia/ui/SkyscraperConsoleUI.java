package ar.edu.itba.sia.ui;

import ar.edu.itba.sia.gps.core.GPSNode;

public class SkyscraperConsoleUI implements SkyscraperUI {

  @Override
  public void printSolution(final GPSNode node, final int openNodes, final long explosionCounter,
      final long time) {
    if (node == null) {
      System.err.println("[FAILED] - solution not found!");
    } else {
      System.out.println("[OK] - Solution found!");
      System.out.println(node.getSolution());
      System.out.println("Solution depth: " + node.getDepth());
      System.out.println("Solution cost: " + node.getCost());
      System.out.println("Expanded nodes: " + explosionCounter);
      System.out.println("Opened nodes: " + openNodes);
      System.out.println("Time: " + time);
    }
  }
}
