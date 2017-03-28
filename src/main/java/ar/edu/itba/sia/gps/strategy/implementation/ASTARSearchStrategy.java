package ar.edu.itba.sia.gps.strategy.implementation;

import ar.edu.itba.sia.gps.api.GPSState;
import ar.edu.itba.sia.gps.api.H;
import ar.edu.itba.sia.gps.core.GPSNode;
import ar.edu.itba.sia.gps.strategy.SSOneTimeCycle;
import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class ASTARSearchStrategy extends SSOneTimeCycle {
  private static final int LOWEST_F = 0;
  private final H heuristic;
  private final Queue<GPSNode> openNodes;

  public ASTARSearchStrategy(final H heuristic) {
    this.heuristic = heuristic;
    this.openNodes = new PriorityQueue<>(Comparator.comparingInt(this::f));
  }

  @Override
  public boolean nodeShouldBeExploded(final Map<GPSState, Integer> bestCostsPerState,
      final GPSNode node) {
    final Integer bestCostState = bestCostsPerState.get(node.getState());
    return bestCostState == null || bestCostState > node.getCost();
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

  private int f(final GPSNode node) {
    final int fNode = g(node) + h(node);
    final GPSNode parentNode = node.getParent();
    final int fParentNode = parentNode == null ? LOWEST_F : parentNode.getF();
    final int maxF = Integer.max(fNode, fParentNode);
    node.setF(maxF);
    return maxF;
  }

  private int g(final GPSNode node) {
    return node.getCost();
  }

  private int h(final GPSNode node) {
    return heuristic.getValue(node.getState());
  }
}
