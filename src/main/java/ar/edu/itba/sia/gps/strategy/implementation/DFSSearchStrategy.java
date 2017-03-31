package ar.edu.itba.sia.gps.strategy.implementation;

import ar.edu.itba.sia.gps.api.GPSState;
import ar.edu.itba.sia.gps.core.GPSNode;
import ar.edu.itba.sia.gps.strategy.SSOneTimeCycle;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class DFSSearchStrategy extends SSOneTimeCycle {
  private final long maxDepth;
  private final Deque<GPSNode> openNodes;
  private long maxReachedDepth;

  public DFSSearchStrategy(final long maxDepth) {
    this.maxDepth = maxDepth;
    this.openNodes = new LinkedList<>();
    this.maxReachedDepth = 0;
  }

  @Override
  public boolean nodeShouldBeExploded(final Map<GPSState, Integer> bestCostsPerState,
      final GPSNode node) {
    final Integer bestCostState = bestCostsPerState.get(node.getState());
    // node will be exploded if its depth is lower than the max depth
    // this is a strict lower as if we were exploding a node that satisfies depth(node) == maxDepth,
    // its exploded nodes will have depth 'maxDepth + 1', ant they should not be analysed as
    // possible solutions
    return node.getDepth() < maxDepth && bestCostState == null;
  }

  @Override
  protected void addNode(final GPSNode node) {
    openNodes.offerFirst(node);
  }

  @Override
  protected boolean isNextNode() {
    return !openNodes.isEmpty();
  }

  @Override
  protected GPSNode getNextNode() {
    final GPSNode nextNode = openNodes.pollFirst();
    if (nextNode.getDepth() > maxReachedDepth) {
      maxReachedDepth = nextNode.getDepth();
    }
    return nextNode;
  }

  @Override
  public Queue<GPSNode> getOpenNodes() {
    return openNodes;
  }

  public long getMaxReachedDepth() {
    return maxReachedDepth;
  }
}
