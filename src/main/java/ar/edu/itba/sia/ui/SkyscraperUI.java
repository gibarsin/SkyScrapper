package ar.edu.itba.sia.ui;

import ar.edu.itba.sia.gps.core.GPSNode;

public interface SkyscraperUI {

  /**
   * Prints the path from the root node to the given goal node
   *
   * @param node The goal node
   * @param openNodes The remaining nodes
   * @param explosionCounter How many nodes were exploded to reach the goal node
   * @param time The elapsed time
   */
  void printSolution(final GPSNode node, final int openNodes, final long explosionCounter,
      final long time);
}
