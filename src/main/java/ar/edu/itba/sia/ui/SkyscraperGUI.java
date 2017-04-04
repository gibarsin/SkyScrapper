package ar.edu.itba.sia.ui;

import ar.edu.itba.sia.gps.core.GPSNode;

public class SkyscraperGUI implements SkyscraperUI {

  @Override
  public void printSolution(final GPSNode node, final int openNodes, final long explosionCounter,
      final long time) {
    SkyscraperApplication.display(node, openNodes, explosionCounter, time);
  }
}
