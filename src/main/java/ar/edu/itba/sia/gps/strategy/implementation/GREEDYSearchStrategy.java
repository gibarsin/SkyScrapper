package ar.edu.itba.sia.gps.strategy.implementation;

import ar.edu.itba.sia.gps.api.GPSState;
import ar.edu.itba.sia.gps.api.H;
import ar.edu.itba.sia.gps.core.GPSNode;
import ar.edu.itba.sia.gps.strategy.RandomOrder;
import ar.edu.itba.sia.gps.strategy.SSOneTimeCycle;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class GREEDYSearchStrategy extends SSOneTimeCycle {
  private final H h;
  private final Random rand;
  private final Deque<GPSNode> openNodes;
  private Queue<GPSNode> currentNodes;

  public GREEDYSearchStrategy(final H heuristic) {
    this.rand = new Random();
    this.h = heuristic;
    this.openNodes = new LinkedList<>();
    initCurrentNodes();
  }

  private void initCurrentNodes() {
    this.currentNodes = new PriorityQueue<>((node1, node2) -> {
      final int h1Value = h.getValue(node1.getState());
      node1.setH(h1Value);
      final int h2Value = h.getValue(node2.getState());
      node2.setH(h2Value);
      final int difference = h2Value - h1Value;
      return difference != 0 ? difference : RandomOrder.randomOrder();
    });
  }

  @Override
  public boolean nodeShouldBeExploded(final Map<GPSState, Integer> bestCostsPerState,
      final GPSNode node) {
    final Integer bestCostState = bestCostsPerState.get(node.getState());
    return bestCostState == null || bestCostState > node.getCost();
  }

  @Override
  protected void addNode(final GPSNode node) {
    currentNodes.offer(node);
  }

  @Override
  protected boolean isNextNode() {
    while (!currentNodes.isEmpty()) {
      openNodes.offerFirst(currentNodes.poll());
    }
    return !openNodes.isEmpty();
  }

  @Override
  protected GPSNode getNextNode() {
    return openNodes.pollFirst();
  }

  @Override
  public Queue<GPSNode> getOpenNodes() {
    return openNodes;
  }
}