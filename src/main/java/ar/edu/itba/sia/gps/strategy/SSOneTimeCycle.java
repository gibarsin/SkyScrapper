package ar.edu.itba.sia.gps.strategy;

import ar.edu.itba.sia.gps.api.GPSProblem;
import ar.edu.itba.sia.gps.api.GPSRule;
import ar.edu.itba.sia.gps.api.GPSState;
import ar.edu.itba.sia.gps.api.SearchStrategyInterface;
import ar.edu.itba.sia.gps.core.GPSNode;
import ar.edu.itba.sia.gps.core.GPSSolutionNode;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;

public abstract class SSOneTimeCycle implements SearchStrategyInterface {
  private final Map<GPSState, Integer> bestCostsPerState;
  private int explosionCounter;

  protected SSOneTimeCycle() {
    this.bestCostsPerState = new HashMap<>();
    this.explosionCounter = 0;
  }

  protected abstract boolean nodeShouldBeExploded(Map<GPSState, Integer> bestCostsPerState, GPSNode node);

  protected abstract Queue<GPSNode> createNewOpenNodesQueue();

  /**
   * Add the new open nodes to the existent open nodes
   * @param openNodes The deque with openNodes nodes
   * @param newOpenNodes The new open node to be inserted into the openNodes deque
   */
  protected abstract void addBasedOnStrategy(Deque<GPSNode> openNodes, Queue<GPSNode> newOpenNodes);

  public GPSSolutionNode findSolution(final GPSProblem problem,
                                      final Deque<GPSNode> openNodes) {
    final GPSNode rootNode = new GPSNode(problem.getInitState(), 0);
    // As the deque is empty, it is the same to add the root element first or last
    openNodes.offerFirst(rootNode);

    explosionCounter = 0;
    while (!openNodes.isEmpty()) {
      // Consume always the first item of the deque
      final GPSNode currentNode = openNodes.pollFirst();
      if (problem.isGoal(currentNode.getState())) {
        return new GPSSolutionNode(currentNode, currentNode.getSolution(),
            explosionCounter, currentNode.getCost());
      } else if (nodeShouldBeExploded(bestCostsPerState, currentNode)){
        explode(currentNode, problem, openNodes);
      }
    }
    return new GPSSolutionNode(null, "No solution found", explosionCounter, -1);
  }

  private void explode(final GPSNode currentNode,
      final GPSProblem problem,
      final Deque<GPSNode> openNodes) {
    incrementExplosionCounter();
    updateBestCostState(currentNode);
    final Queue<GPSNode> newOpenNodes = createNewOpenNodesQueue();
    for (final GPSRule rule : problem.getRules()) {
      final Optional<GPSState> newState = getNewStateBasedOn(rule, currentNode.getState());
      if (newState.isPresent()) {
        final int newCost = getNewCost(currentNode, rule);
        final GPSNode newNode = new GPSNode(newState.get(), newCost);
        newNode.setParent(currentNode);
        newOpenNodes.add(newNode);
      }
    }
    addBasedOnStrategy(openNodes, newOpenNodes);
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

}
