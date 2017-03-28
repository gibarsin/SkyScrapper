package ar.edu.itba.sia.game;

import ar.edu.itba.sia.gps.api.GPSProblem;
import ar.edu.itba.sia.gps.api.GPSRule;
import ar.edu.itba.sia.gps.api.GPSState;
import ar.edu.itba.sia.gps.api.H;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SkyscraperProblem implements GPSProblem {
  private static final int NO_H_VALUE = 0;

  private final SkyscraperState initialState;
  private final List<GPSRule> rules;
  private final List<H> heuristics;
  private final boolean admissibleHeuristics;
  private final BoardValidator boardValidator;

  public SkyscraperProblem(final SkyscraperBoard initialBoard, final List<GPSRule> rules,
      final List<H> heuristics, final boolean admissibleHeuristics,
      final BoardValidator boardValidator) {
    this.initialState = new SkyscraperState(Objects.requireNonNull(initialBoard));
    this.rules = Objects.requireNonNull(rules);
    this.heuristics = Objects.requireNonNull(heuristics);
    this.admissibleHeuristics = admissibleHeuristics;
    this.boardValidator = Objects.requireNonNull(boardValidator);
  }

  @Override
  public GPSState getInitState() {
    return initialState;
  }

  @Override
  public boolean isGoal(final GPSState state) {
    final SkyscraperState ssState = (SkyscraperState) state;
    final SkyscraperBoard board = ssState.getBoard();

    return board.isFull() && !boardValidator.areConflictsOn(board);
  }

  @Override
  public List<GPSRule> getRules() {
    return rules;
  }

  @Override
  public Integer getHValue(final GPSState state) {
    return admissibleHeuristics ? maxH(state) : minH(state);
  }

  private Integer maxH(final GPSState state) {
    final Optional<H> maxH = heuristics.stream().max((h1, h2) -> {
      final int h1Value = h1.getValue(state);
      final int h2Value = h2.getValue(state);
      if (h1Value == Integer.MAX_VALUE) {
        if (h2Value == Integer.MAX_VALUE) {
          return Integer.MIN_VALUE;
        }
        return h2.getValue(state);
      }
      if (h2Value == Integer.MAX_VALUE) {
        return h1.getValue(state);
      }
      return Integer.max(h1Value, h2Value);
    });

    return maxH.map(h -> h.getValue(state)).orElse(NO_H_VALUE);
  }

  private Integer minH(final GPSState state) {
    final Optional<H> minH = heuristics.stream().min((h1, h2) -> {
      final int h1Value = h1.getValue(state);
      final int h2Value = h2.getValue(state);
      return Integer.min(h1Value, h2Value);
    });

    return minH.map(h -> h.getValue(state)).orElse(NO_H_VALUE);
  }
}
