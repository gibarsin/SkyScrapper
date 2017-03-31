package ar.edu.itba.sia.gps.strategy;

import ar.edu.itba.sia.gps.api.GPSProblem;
import ar.edu.itba.sia.gps.api.GPSRule;
import ar.edu.itba.sia.gps.api.GPSState;
import ar.edu.itba.sia.gps.api.SearchStrategyInterface;
import ar.edu.itba.sia.gps.core.GPSNode;
import ar.edu.itba.sia.gps.core.GPSSolutionNode;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class SSOneTimeCycle implements SearchStrategyInterface {
  private final Map<GPSState, Integer> bestCostsPerState;
  private int explosionCounter;

  protected SSOneTimeCycle() {
    this.bestCostsPerState = new HashMap<>();
    this.explosionCounter = 0;
  }

  protected abstract boolean nodeShouldBeExploded(Map<GPSState, Integer> bestCostsPerState, GPSNode node);

  protected abstract void addNode(GPSNode node);
  protected abstract boolean isNextNode();
  protected abstract GPSNode getNextNode();

  @Override
  public Map<GPSState, Integer> getBestCostsPerState() {
    return bestCostsPerState;
  }

  @Override
  public long getExplosionCounter() {
    return explosionCounter;
  }

  @Override
  public GPSSolutionNode findSolution(final GPSProblem problem) {
    final GPSNode rootNode = new GPSNode(problem.getInitState(), 0, null);
    // As the deque is empty, it is the same to add the root element first or last
    addNode(rootNode);

    explosionCounter = 0;
    while (isNextNode()) {
      // Consume always the first item of the deque
      final GPSNode currentNode = getNextNode();
      if (problem.isGoal(currentNode.getState())) {
        return new GPSSolutionNode(currentNode, currentNode.getSolution(),
            explosionCounter, currentNode.getCost());
      } else if (nodeShouldBeExploded(bestCostsPerState, currentNode)){
        explode(currentNode, problem);
      }
    }
    return new GPSSolutionNode(null, "No solution found", explosionCounter, -1);
  }

  private void explode(final GPSNode currentNode,
      final GPSProblem problem) {
    incrementExplosionCounter();
    updateBestCostState(currentNode);
    for (final GPSRule rule : problem.getRules()) {
      final Optional<GPSState> newState = getNewStateBasedOn(rule, currentNode.getState());
      if (newState.isPresent()) {
        final int newCost = getNewCost(currentNode, rule);
        final GPSNode newNode = new GPSNode(newState.get(), newCost, rule);
        newNode.setParent(currentNode);
        addNode(newNode);
      }
    }
  }

  private int getNewCost(final GPSNode node, final GPSRule rule) {
    return node.getCost() + rule.getCost(); // TODO: fix for A* is not like this
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
