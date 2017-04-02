package ar.edu.itba.sia.core.program;

import static ar.edu.itba.sia.core.InputParam.DATA_PATH;
import static ar.edu.itba.sia.core.InputParam.HEURISTIC;
import static ar.edu.itba.sia.core.InputParam.SEARCH_STRATEGY;
import static ar.edu.itba.sia.core.helper.IOService.ExitStatus.BAD_ARGUMENT;

import ar.edu.itba.sia.core.helper.DataFileContent;
import ar.edu.itba.sia.core.helper.IOService;
import ar.edu.itba.sia.core.helper.InputSerializerHelper;
import ar.edu.itba.sia.game.BoardValidator;
import ar.edu.itba.sia.game.BoardValidatorImpl;
import ar.edu.itba.sia.game.SkyscraperBoard;
import ar.edu.itba.sia.game.SkyscraperBoardImpl;
import ar.edu.itba.sia.game.SkyscraperProblem;
import ar.edu.itba.sia.game.heuristic.IdealAmountOfSwapsHeuristic;
import ar.edu.itba.sia.game.heuristic.StatisticalAmountOfSwapsHeuristic;
import ar.edu.itba.sia.game.rules.SkyscraperSwapRule;
import ar.edu.itba.sia.gps.api.GPSProblem;
import ar.edu.itba.sia.gps.api.GPSRule;
import ar.edu.itba.sia.game.heuristic.Heuristic;
import ar.edu.itba.sia.gps.core.GPSEngine;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SkyscraperSwapProgram extends SkyscraperProgram {
  private final GPSEngine engine;

  public SkyscraperSwapProgram(final String[] args) {
    this.engine = initEngine(args);
  }

  private GPSEngine initEngine(final String[] args) {
    final String dataPath = IOService.validArgsAccess(args, DATA_PATH.getIndex());
    final DataFileContent dataFileContent = InputSerializerHelper.loadDataFile(dataPath);
    final int[][] matrix = dataFileContent.getFullMatrix().getCompleteMatrix();
    final SkyscraperBoard board = new SkyscraperBoardImpl(
        matrix,
        dataFileContent.getFixedCells(),
        dataFileContent.getVisibility()
    );
    final List<GPSRule> rules = buildRules(dataFileContent.getMatrix().length);
    final BoardValidator boardValidator = new BoardValidatorImpl(board.getSize());
    final List<Heuristic> heuristics = buildHeuristics(args, boardValidator);
    final boolean areAdmissible = areAdmissibleHeuristics(heuristics);
    final GPSProblem problem = new SkyscraperProblem(
        board, rules, heuristics,
        areAdmissible, boardValidator);
    final String searchStrategy = IOService.validArgsAccess(args, SEARCH_STRATEGY.getIndex());
    return new GPSEngine(problem, getStrategyEnum(searchStrategy));
  }

  /**
   *
   * @param heuristics THe heuristics to be evaluated
   * @return true if ALL heuristics are admissible; false otherwise
   */
  private boolean areAdmissibleHeuristics(final List<Heuristic> heuristics) {
    return heuristics.stream().reduce(true,
        (aBoolean, h) -> aBoolean && h.isAdmissible(),
        (aBoolean, aBoolean2) -> aBoolean && aBoolean2);
  }

  private List<Heuristic> buildHeuristics(final String[] args, final BoardValidator boardValidator) {
    final List<Heuristic> heuristics = new LinkedList<>();
    int i = 0;
    while (args.length > HEURISTIC.getIndex() + i) {
      addHeuristic(heuristics, args[HEURISTIC.getIndex() + i], boardValidator);
      i++;
    }
    return heuristics;
  }

  private void addHeuristic(final List<Heuristic> heuristics, final String heuristic,
      final BoardValidator boardValidator) {
    switch (heuristic.toLowerCase()) {
      case "h1":
        heuristics.add(new IdealAmountOfSwapsHeuristic(boardValidator));
        break;
      case "h2":
        heuristics.add(new StatisticalAmountOfSwapsHeuristic(boardValidator));
        break;
      default:
        IOService.exit(BAD_ARGUMENT, HEURISTIC.getName());
        // should never return from the above code
        throw new IllegalStateException();
    }
  }

  private static List<GPSRule> buildRules(final int size) {
    final List<GPSRule> rules = new ArrayList<>();
    for (int row1 = 0; row1 < size ; row1++) {
      for (int col1 = 0; col1 < size ; col1++) {
        for (int row2 = row1; row2 < size ; row2++) {
          for (int col2 = col1 ; col2 < size ; col2++) {
            if (row1 != row2 || col1 != col2) {
              rules.add(new SkyscraperSwapRule(row1, col1, row2, col2));
            }
          }
        }
      }
    }
    return rules;
  }

  @Override
  protected GPSEngine getEngine() {
    return engine;
  }
}
