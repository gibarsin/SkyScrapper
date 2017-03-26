package ar.edu.itba.sia.gps.strategy.implementation;

import ar.edu.itba.sia.gps.api.GPSProblem;
import ar.edu.itba.sia.gps.api.GPSState;
import ar.edu.itba.sia.gps.api.SearchStrategyInterface;
import ar.edu.itba.sia.gps.core.GPSNode;
import ar.edu.itba.sia.gps.core.GPSSolutionNode;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class IDDFSSearchStrategy implements SearchStrategyInterface {
  @Override
  public GPSSolutionNode findSolution(final GPSProblem problem, final Deque<GPSNode> openNodes) {
    long currentMaxDepth = 0;
    while(true) {
      final DFSSearchStrategy dfsSearchStrategy = new DFSSearchStrategy(currentMaxDepth);
      final GPSSolutionNode solutionNode = dfsSearchStrategy.findSolution(problem, openNodes);
      if (solutionNode != null && solutionNode.getSolutionNode() != null) { // we truly find a solution
        return solutionNode;
      }
      currentMaxDepth ++;
    }
  }
}
