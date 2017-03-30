package ar.edu.itba.sia.gps.strategy.implementation;

import ar.edu.itba.sia.gps.api.GPSState;
import ar.edu.itba.sia.gps.api.H;
import ar.edu.itba.sia.gps.core.GPSNode;
import ar.edu.itba.sia.gps.strategy.SSOneTimeCycle;
import java.util.Comparator;
import java.util.Deque;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class GREEDYSearchStrategy extends SSOneTimeCycle {
  private final H h;

  public GREEDYSearchStrategy(final H heuristic) {
    this.h = heuristic;
  }

  @Override
  public boolean nodeShouldBeExploded(final Map<GPSState, Integer> bestCostsPerState,
      final GPSNode node) {
    final Integer bestCostState = bestCostsPerState.get(node.getState());
    return bestCostState == null || bestCostState > node.getCost();
  }

  @Override
  public Queue<GPSNode> createNewOpenNodesQueue() {
    return new PriorityQueue<>(Comparator.comparingInt(node -> h.getValue(node.getState())));
  }

  @Override
  public void addBasedOnStrategy(final Deque<GPSNode> openNodes,
      final Queue<GPSNode> newOpenNodes) {
    // order open nodes on the priority queue of the new open nodes and then pass them all to the
    // open nodes deque
    while (!openNodes.isEmpty()) {
      newOpenNodes.offer(openNodes.peek());
    }
    openNodes.addAll(newOpenNodes);
  }
}
