package ar.edu.itba.sia.gps.core;

public class GPSSolutionNode {
  private final GPSNode solutionNode;
  private final String solution;
  private final long explodedNodes;
  private final int cost;

  public GPSSolutionNode(final GPSNode solutionNode, final String solution,
      final long explodedNodes, final int cost) {
    this.solutionNode = solutionNode;
    this.solution = solution;
    this.explodedNodes = explodedNodes;
    this.cost = cost;
  }

  /**
   *
   * @return the solution node if solution was found ; null otherwise
   */
  public GPSNode getSolutionNode() { return solutionNode; }

  public String getSolution() {
    return solution;
  }

  public long getExplodedNodes() {
    return explodedNodes;
  }

  public int getCost() {
    return cost;
  }
}
