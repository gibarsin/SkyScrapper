package ar.edu.itba.sia.core;

public enum InputParam {
  // solve parameters
  DATA_PATH(1, "<path/to/data.dat>"), SEARCH_STRATEGY(2, "<search_strategy>"),
  METHOD(3, "<method>"), HEURISTIC(4, "<heuristic>");
  private final int index;
  private final String name;
  InputParam(final int index, final String name) {
    this.index = index;
    this.name = name;
  }

  public int getIndex() {
    return index;
  }

  public String getName() {
    return name;
  }
}
