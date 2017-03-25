package ar.edu.itba.sia.gps.core;

import ar.edu.itba.sia.gps.api.GPSProblem;
import ar.edu.itba.sia.gps.api.GPSRule;
import ar.edu.itba.sia.gps.api.GPSState;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;

public class GPSEngine {
  // Will always be consumed from the beginning
  private Deque<GPSNode> openNodes;
  private Map<GPSState, Integer> bestCostsPerState;
  private long explosionCounter;

  /**
   *
   * @param problem Object implementing the problem's knowledge base
   * @param strategy Strategy to be used to resolve the given problem.
   * @implNote {@code strategy} will be used to determine the order in which the nodes
   *           to be expanded are going to be add into the openNodes deque
   */
  public void engine(final GPSProblem problem, final SearchStrategy strategy) {
    this.openNodes = new LinkedList<>();
    this.bestCostsPerState = new HashMap<>();
    this.explosionCounter = 0;

    final GPSNode rootNode = new GPSNode(problem.getInitState(), 0);
    // As the deque is empty, it is the same to add the root element first or last
    this.openNodes.offerFirst(rootNode);

    GPSSolution solNode = null;
    while (!openNodes.isEmpty() && solNode == null) {
      // Consume always the first item of the deque
      final GPSNode currentNode = openNodes.pollFirst();
      if (problem.isGoal(currentNode.getState())) {
        solNode = new GPSSolution(currentNode.getSolution(),
                explosionCounter, currentNode.getCost());
      } else if (nodeShouldBeExploded(currentNode)){
        System.out.println("Open Nodes: " + openNodes.size());
        explode(currentNode, strategy, problem);
      }
    }

    printSolution(solNode);
  }

  private void printSolution(final GPSSolution solNode) {
    if (solNode == null) {
      System.err.println("[FAILED] - solution not found!");
      System.err.println("Open Nodes: " + openNodes.size());
    } else {
      System.out.println("[OK] - Solution found!");
      System.out.println(solNode.getSolution());
      System.out.println("Expanded nodes: " + solNode.getExplodedNodes());
      System.out.println("Solution cost: " + solNode.getCost());
    }
  }

  private boolean nodeShouldBeExploded(final GPSNode node) {
    final Integer bestCostState = bestCostsPerState.get(node.getState());
    // Explode current node only if the current state hasn't already been reached or it has been
    // reached but with a higher cost
    return bestCostState == null || bestCostState > node.getCost();
  }

  private void explode(final GPSNode node,
                       final SearchStrategy strategy,
                       final GPSProblem problem) {
    incrementExplosionCounter();
    updateBestCostState(node);
    // TODO: test if forEach is applicable here
    for (final GPSRule rule : problem.getRules()) {
      final Optional<GPSState> newState = getNewStateBasedOn(rule, node.getState());
      if (newState.isPresent()) {
        final int newCost = getNewCost(node, rule);
        // TODO: can we change this code to initialize node's parent in the constructor?
        final GPSNode newNode = new GPSNode(newState.get(), newCost);
        newNode.setParent(node);
        addBasedOnStrategy(strategy, this.openNodes, newNode);
      }
    }
  }

  private int getNewCost(final GPSNode node, final GPSRule rule) {
    return node.getCost() + rule.getCost();
  }

  /**
   *
   * @param rule The rule to be applied to the given state to get the new one
   * @param state The state to which the given rule will be applied
   * @return The new GPSState if the current rule is applicable to the current state; null otherwise
   */
  private Optional<GPSState> getNewStateBasedOn(final GPSRule rule, final GPSState state) {
      return rule.evalRule(state);
  }

  private void incrementExplosionCounter() {
    this.explosionCounter ++;
  }

  private void updateBestCostState(GPSNode node) {
    this.bestCostsPerState.put(node.getState(), node.getCost());
  }

  /**
   *
   * @param strategy The search strategy being used
   * @param open The deque with openNodes nodes
   * @param newNode The new node to be inserted into the openNodes deque
   * @implNote The only difference between BFS and DFS is where the items are added to the deque
   *           as they are always consumed from the beginning
   */
  private void addBasedOnStrategy(final SearchStrategy strategy,
                                  final Deque<GPSNode> open,
                                  final GPSNode newNode) {
    switch (strategy) {
      case BFS:
        // Add to the back of the deque => consume later
        open.offerLast(newNode);
        break;
      case DFS:
        // Add to the front of the deque => consume right away
        open.offerFirst(newNode);
        break;
    }
  }

  private static class GPSSolution {
    private final String solution;
    private final long explodedNodes;
    private final int cost;

    private GPSSolution(final String solution, final long explodedNodes, final int cost) {
      this.solution = solution;
      this.explodedNodes = explodedNodes;
      this.cost = cost;
    }

    private String getSolution() {
      return solution;
    }

    private long getExplodedNodes() {
      return explodedNodes;
    }

    private int getCost() {
      return cost;
    }
  }
}
