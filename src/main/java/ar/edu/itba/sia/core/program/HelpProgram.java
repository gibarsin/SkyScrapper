package ar.edu.itba.sia.core.program;

import static ar.edu.itba.sia.core.InputParam.DATA_PATH;
import static ar.edu.itba.sia.core.InputParam.HEURISTIC;
import static ar.edu.itba.sia.core.InputParam.METHOD;
import static ar.edu.itba.sia.core.InputParam.SEARCH_STRATEGY;

import ar.edu.itba.sia.core.MainProgram;

public class HelpProgram implements MainProgram {
  private static final String HELP_TEXT =
      "Skyscraper Game Solver.\n" +
          "Arguments: \n" +
          "* help : prints all available commands\n" +
          "* solve " + DATA_PATH.getName() + " " + SEARCH_STRATEGY.getName() + " " +
            METHOD.getName() + " [" + HEURISTIC.getName() + ", ...]: \n" +
          "     solves the skyscraper game according with the given input.\n" +
          "     - " + DATA_PATH.getName() + ": path to file containing game's matrix and visibilities.\n" +
          "       For format examples, check out the '<project_root>/resources/games/*' files.\n" +
          "     - " + SEARCH_STRATEGY.getName() + ": one of: dfs, bfs, iddfs, greedy, astar.\n" +
          "     - " + METHOD.getName() + ": one of: put, swap \n" +
          "     - " + HEURISTIC.getName() + ": OPTIONAL: only available for 'swap' method. \n"+
          "       List of all the desired heuristics to be applied (no matter if they are admissible or not).\n" +
          "       Possible values: \n" +
          "       - h1: ideal amount of swaps heuristic (admissible)\n";

  @Override
  public void run(final String[] args) {
    System.out.println(HELP_TEXT);
  }
}
