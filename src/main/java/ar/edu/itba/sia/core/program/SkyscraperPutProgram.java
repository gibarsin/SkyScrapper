package ar.edu.itba.sia.core.program;

import static ar.edu.itba.sia.core.InputParam.DATA_PATH;
import static ar.edu.itba.sia.core.InputParam.SEARCH_STRATEGY;

import ar.edu.itba.sia.core.helper.DataFileContent;
import ar.edu.itba.sia.core.helper.IOService;
import ar.edu.itba.sia.core.helper.InputSerializerHelper;
import ar.edu.itba.sia.game.BoardValidator;
import ar.edu.itba.sia.game.BoardValidatorImpl;
import ar.edu.itba.sia.game.SkyscraperBoard;
import ar.edu.itba.sia.game.SkyscraperBoardImpl;
import ar.edu.itba.sia.game.SkyscraperProblem;
import ar.edu.itba.sia.game.rules.SkyscraperPutRule;
import ar.edu.itba.sia.gps.api.GPSProblem;
import ar.edu.itba.sia.gps.api.GPSRule;
import ar.edu.itba.sia.gps.api.Heuristic;
import ar.edu.itba.sia.gps.core.GPSEngine;
import java.util.LinkedList;
import java.util.List;

public class SkyscraperPutProgram extends SkyscraperProgram {
  private final GPSEngine engine;

  public SkyscraperPutProgram(final String[] args) {
    this.engine = initEngine(args);
  }

  private GPSEngine initEngine(final String[] args) {
    final String dataPath = IOService.validArgsAccess(args, DATA_PATH.getIndex());
    final DataFileContent dataFileContent = InputSerializerHelper.loadDataFile(dataPath);
    final SkyscraperBoard board
        = new SkyscraperBoardImpl(dataFileContent.getMatrix(), dataFileContent.getVisibility());
    final List<GPSRule> rules = buildRules(dataFileContent.getMatrix().length);
    final BoardValidator boardValidator = new BoardValidatorImpl(board.getSize());
    final GPSProblem problem =
        new SkyscraperProblem(board, rules, buildHeuristics(), true, boardValidator);
    final String searchStrategy = IOService.validArgsAccess(args, SEARCH_STRATEGY.getIndex());
    return new GPSEngine(problem, getStrategyEnum(searchStrategy));
  }

  private List<Heuristic> buildHeuristics() {
    return new LinkedList<>(); // +++ximprove: no heuristics needed here
  }

  private List<GPSRule> buildRules(final int size) {
    final List<GPSRule> rules = new LinkedList<>();
    for (int i = 0; i < size ; i++) {
      for (int j = 0; j < size ; j++) {
        for (int n = 1; n <= size ; n++) {
          rules.add(new SkyscraperPutRule(i, j, n));
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
