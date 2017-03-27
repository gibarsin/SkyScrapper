package ar.edu.itba.sia.gps.strategy.implementation;

import ar.edu.itba.sia.gps.api.GPSState;
import ar.edu.itba.sia.gps.core.GPSNode;
import ar.edu.itba.sia.gps.strategy.SSOneTimeCycle;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class DFSSearchStrategy extends SSOneTimeCycle {
  private final long maxDepth;

  public DFSSearchStrategy(final long maxDepth) {
    this.maxDepth = maxDepth;
  }

  @Override
  public boolean nodeShouldBeExploded(final Map<GPSState, Integer> bestCostsPerState,
      final GPSNode node) {
    final Integer bestCostState = bestCostsPerState.get(node.getState());
    // node will be exploded if its depth is lower than the max depth
    // this is a strict lower as if we were exploding a node that satisfies depth(node) == maxDepth,
    // its exploded nodes will have depth 'maxDepth + 1', ant they should not be analysed as
    // possible solutions
    long depth = depth(node);
    System.out.println(depth);
    return bestCostState == null && depth < maxDepth;
  }

  @Override
  public Queue<GPSNode> createNewOpenNodesQueue() {
    return new LinkedList<>();
  }

  @Override
  public void addBasedOnStrategy(final Deque<GPSNode> openNodes,
      final Queue<GPSNode> newOpenNodes) {
    newOpenNodes.forEach(openNodes::offerFirst);
  }

  /**
   *
   * @param node The node which maxDepth is to be calculated
   * @return 0 if node is root ; 1 + parentNode maxDepth otherwise
   */
  private long depth(final GPSNode node) {
    return node.getParent() == null ? 0 : 1 + depth(node.getParent());
  }
}
