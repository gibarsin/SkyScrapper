package ar.edu.itba.sia.ui;

import ar.edu.itba.sia.gps.core.GPSNode;

public interface SkyscraperUI {

  /**
   * Prints the path from the root node to the given goal node
   * @param node The goal node
   * @param explosionCounter How many nodes were exploded to reach the goal node
   */
  void printSolution(GPSNode node, long explosionCounter);
}
