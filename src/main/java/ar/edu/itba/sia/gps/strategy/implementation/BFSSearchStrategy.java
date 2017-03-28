package ar.edu.itba.sia.gps.strategy.implementation;

import ar.edu.itba.sia.gps.api.GPSState;
import ar.edu.itba.sia.gps.core.GPSNode;
import ar.edu.itba.sia.gps.strategy.SSOneTimeCycle;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class BFSSearchStrategy extends SSOneTimeCycle {
  private final Queue<GPSNode> openNodes;

  public BFSSearchStrategy() {
    this.openNodes = new LinkedList<>();
  }

  @Override
  public boolean nodeShouldBeExploded(final Map<GPSState, Integer> bestCostsPerState,
      final GPSNode node) {
    final Integer bestCostState = bestCostsPerState.get(node.getState());
    return bestCostState == null;
  }

  @Override
  protected void addNode(final GPSNode node) {
    openNodes.offer(node);
  }

  @Override
  protected boolean isNextNode() {
    return !openNodes.isEmpty();
  }

  @Override
  protected GPSNode getNextNode() {
    return openNodes.poll();
  }

  @Override
  public Queue<GPSNode> getOpenNodes() {
    return openNodes;
  }
}
