package ar.edu.itba.sia.gps.core;

import ar.edu.itba.sia.gps.api.GPSRule;
import ar.edu.itba.sia.gps.api.GPSState;
import java.util.Objects;

public class GPSNode {
  private final GPSState state;
  private final int cost;
  private final long depth;
  private GPSNode parent;
  private GPSRule generationRule;
  private int f;

  /**
   * Create a new GPS node
   * @param state The non-null state associated with the node being created
   * @param cost The cost to reach the current node
   * @exception NullPointerException if {@code state} is null
   */
  public GPSNode(final GPSState state, final Integer cost, final GPSRule generationRule,
      final long depth) {
    this.state = Objects.requireNonNull(state);
    this.cost = cost;
    this.generationRule = generationRule;
    this.depth = depth;
  }

  public GPSNode getParent() {
    return parent;
  }

  public void setParent(final GPSNode parent) {
    this.parent = parent;
  }

  public GPSState getState() {
    return state;
  }

  public int getCost() {
    return cost;
  }

  public GPSRule getGenerationRule() {
    return generationRule;
  }

  public void setGenerationRule(final GPSRule generationRule) {
    this.generationRule = generationRule;
  }

  public int getF() {
    return f;
  }

  public void setF(final int f) {
    this.f = f;
  }

  @Override
  public String toString() {
    return state.toString();
  }

  public String getSolution() {
    if (this.parent == null) {
      return this.state.toString();
    }
    return this.parent.getSolution() + this.generationRule.toString() + this.state.toString();
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (!(o instanceof GPSNode)) return false;

    final GPSNode gpsNode = (GPSNode) o;

    return state.equals(gpsNode.state);
  }

  @Override
  public int hashCode() {
    return state.hashCode();
  }

  public long getDepth() {
    return depth;
  }
}
