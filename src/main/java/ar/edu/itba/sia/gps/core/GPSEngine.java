package ar.edu.itba.sia.gps.core;

import ar.edu.itba.sia.gps.api.GPSProblem;
import ar.edu.itba.sia.gps.api.GPSState;
import ar.edu.itba.sia.gps.api.SearchStrategyInterface;
import ar.edu.itba.sia.gps.strategy.SearchStrategy;
import ar.edu.itba.sia.gps.strategy.implementation.ASTARSearchStrategy;
import ar.edu.itba.sia.gps.strategy.implementation.BFSSearchStrategy;
import ar.edu.itba.sia.gps.strategy.implementation.DFSSearchStrategy;
import ar.edu.itba.sia.gps.strategy.implementation.GREEDYSearchStrategy;
import ar.edu.itba.sia.gps.strategy.implementation.IDDFSSearchStrategy;
import java.util.Map;
import java.util.Queue;

public class GPSEngine {
  private final GPSProblem problem;
  private final SearchStrategy strategy;
  private final SearchStrategyInterface searchStrategy;

  // Necessary because @apierri added them to: https://github.com/apierri/GeneralProblemSolver
  private boolean finished;
  private boolean failed;
  private GPSNode solutionNode;

  /**
   *
   * @param problem Object implementing the problem's knowledge base
   * @param strategy Strategy to be used to resolve the given problem.
   * @implNote {@code strategy} will be used to determine the order in which the nodes
   *           to be expanded are going to be add into the openNodes deque
   */
  public GPSEngine(final GPSProblem problem, final SearchStrategy strategy) {
    this.problem = problem;
    this.strategy = strategy;
    this.searchStrategy = chooseStrategy(strategy);
    this.finished = false;
    this.failed = false;
    this.solutionNode = null;
  }

  private SearchStrategyInterface chooseStrategy(final SearchStrategy strategy) {
    switch (strategy) {
      case BFS:
        return new BFSSearchStrategy();
      case DFS:
        return new DFSSearchStrategy(Long.MAX_VALUE);
      case IDDFS:
        return new IDDFSSearchStrategy();
      case GREEDY:
        return new GREEDYSearchStrategy(problem::getHValue);
      case ASTAR:
        return new ASTARSearchStrategy(problem::getHValue);
      default:
        throw new IllegalStateException();
    }
  }

  public void findSolution() {
    final GPSSolutionNode gpsSolutionNode = searchStrategy.findSolution(problem);
    solutionNode = gpsSolutionNode.getSolutionNode();
    finished = true;
    failed = solutionNode == null;
    printSolution(gpsSolutionNode);
  }

  private void printSolution(final GPSSolutionNode solNode) {
    if (solNode == null) {
      System.err.println("[FAILED] - solution not found!");
    } else {
      System.out.println("[OK] - Solution found!");
      System.out.println(solNode.getSolution());
      System.out.println("Expanded nodes: " + solNode.getExplodedNodes());
      System.out.println("Solution cost: " + solNode.getCost());
    }
  }

  // Code added by @apierri

  public Queue<GPSNode> getOpen() {
    return searchStrategy.getOpenNodes();
  }

  public Map<GPSState, Integer> getBestCosts() {
    return searchStrategy.getBestCostsPerState();
  }

  public GPSProblem getProblem() {
    return problem;
  }

  public long getExplosionCounter() {
    return searchStrategy.getExplosionCounter();
  }

  public boolean isFinished() {
    return finished;
  }

  public boolean isFailed() {
    return failed;
  }

  public GPSNode getSolutionNode() {
    return solutionNode;
  }

  public SearchStrategy getStrategy() {
    return strategy;
  }
}
