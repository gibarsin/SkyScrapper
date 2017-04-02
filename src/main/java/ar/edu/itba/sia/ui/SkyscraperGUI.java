package ar.edu.itba.sia.ui;

import ar.edu.itba.sia.gps.core.GPSNode;

public class SkyscraperGUI implements SkyscraperUI {

  @Override
  public void printSolution(final GPSNode node, final long explosionCounter) {
    SkyscraperApplication.display(node, explosionCounter);
  }
}
