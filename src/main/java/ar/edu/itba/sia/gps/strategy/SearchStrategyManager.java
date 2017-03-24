package ar.edu.itba.sia.gps.strategy;

import ar.edu.itba.sia.gps.api.GPSProblem;
import ar.edu.itba.sia.gps.api.GPSState;
import ar.edu.itba.sia.gps.api.SearchStrategyInterface;
import ar.edu.itba.sia.gps.core.GPSNode;
import ar.edu.itba.sia.gps.core.GPSSolutionNode;
import java.util.Deque;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

public class SearchStrategyManager {
  private SearchStrategyInterface strategy;

  public void setStrategy(final SearchStrategyInterface strategy) {
    this.strategy = strategy;
  }

  public GPSSolutionNode findSolution(final GPSProblem problem, final Deque<GPSNode> openNodes) {
    return Objects.requireNonNull(strategy).findSolution(problem, openNodes);
  }
}
