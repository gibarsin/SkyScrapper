package ar.edu.itba.sia.gps.strategy.implementation;

import ar.edu.itba.sia.gps.api.GPSState;
import ar.edu.itba.sia.gps.core.GPSNode;
import ar.edu.itba.sia.gps.strategy.SSOneTimeCycle;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class BFSSearchStrategy extends SSOneTimeCycle {

  @Override
  public boolean nodeShouldBeExploded(final Map<GPSState, Integer> bestCostsPerState,
      final GPSNode node) {
    final Integer bestCostState = bestCostsPerState.get(node.getState());
    return bestCostState == null;
  }

  @Override
  public Queue<GPSNode> createNewOpenNodesQueue() {
    return new LinkedList<>();
  }

  public void addBasedOnStrategy(final Deque<GPSNode> openNodes,
      final Queue<GPSNode> newOpenNodes) {
    newOpenNodes.forEach(openNodes::offerLast);
  }
}
