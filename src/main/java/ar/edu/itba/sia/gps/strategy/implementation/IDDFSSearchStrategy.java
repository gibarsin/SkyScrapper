package ar.edu.itba.sia.gps.strategy.implementation;

import ar.edu.itba.sia.gps.api.GPSProblem;
import ar.edu.itba.sia.gps.api.GPSState;
import ar.edu.itba.sia.gps.api.SearchStrategyInterface;
import ar.edu.itba.sia.gps.core.GPSNode;
import ar.edu.itba.sia.gps.core.GPSSolutionNode;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class IDDFSSearchStrategy implements SearchStrategyInterface {
  private DFSSearchStrategy dfsSearchStrategy;

  @Override
  public GPSSolutionNode findSolution(final GPSProblem problem) {
    long currentMaxDepth = 0;
    long previousMaxDepth = currentMaxDepth - 1;
    long maxReachedDepth = previousMaxDepth; // for valid initial conditions
    // while the previous maxReachedDepth is the previousMaxDepth
    while(maxReachedDepth == previousMaxDepth) {
      dfsSearchStrategy = new DFSSearchStrategy(currentMaxDepth);
      final GPSSolutionNode solutionNode = dfsSearchStrategy.findSolution(problem);
      if (solutionNode != null && solutionNode.getSolutionNode() != null) { // we truly find a solution
        return solutionNode;
      }
      maxReachedDepth = dfsSearchStrategy.getMaxReachedDepth();
      previousMaxDepth = currentMaxDepth;
      currentMaxDepth ++;
    }
    return new GPSSolutionNode(null, "", getExplosionCounter(), -1);
  }

  @Override
  public Queue<GPSNode> getOpenNodes() {
    return dfsSearchStrategy == null ? new LinkedList<>() : dfsSearchStrategy.getOpenNodes();
  }

  @Override
  public Map<GPSState, Integer> getBestCostsPerState() {
    return dfsSearchStrategy == null ? new HashMap<>() : dfsSearchStrategy.getBestCostsPerState();
  }

  @Override
  public long getExplosionCounter() {
    return dfsSearchStrategy == null ? 0 : dfsSearchStrategy.getExplosionCounter();
  }

}
