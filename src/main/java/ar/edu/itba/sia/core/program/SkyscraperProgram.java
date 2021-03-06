package ar.edu.itba.sia.core.program;

import ar.edu.itba.sia.core.InputParam;
import ar.edu.itba.sia.core.MainProgram;
import ar.edu.itba.sia.core.helper.IOService;
import ar.edu.itba.sia.core.helper.IOService.ExitStatus;
import ar.edu.itba.sia.gps.core.GPSEngine;
import ar.edu.itba.sia.gps.strategy.SearchStrategy;
import ar.edu.itba.sia.ui.SkyscraperGUI;
import ar.edu.itba.sia.ui.SkyscraperUI;

public abstract class SkyscraperProgram implements MainProgram {
  private final SkyscraperUI ui;

  protected SkyscraperProgram() {
    this.ui = new SkyscraperGUI();
  }

  protected abstract GPSEngine getEngine();

  protected SearchStrategy getStrategyEnum(final String searchStrategy) {
    try {
      return SearchStrategy.valueOf(searchStrategy.toUpperCase());
    } catch (final IllegalArgumentException e) {
      IOService.exit(ExitStatus.BAD_ARGUMENT, InputParam.SEARCH_STRATEGY.getName());
      // should never return from the above code
      throw new IllegalStateException();
    }
  }

  @Override
  public void run(final String[] args) {
    final GPSEngine engine = getEngine();

    final long startTime = System.currentTimeMillis();
    engine.findSolution();
    final long endTime = System.currentTimeMillis();
    ui.printSolution(
        engine.getSolutionNode(),
        engine.getOpen().size(),
        engine.getExplosionCounter(),
        endTime - startTime
    );
  }
}
