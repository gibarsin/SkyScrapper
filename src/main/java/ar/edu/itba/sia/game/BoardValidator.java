package ar.edu.itba.sia.game;

public interface BoardValidator {

  /**
   *
   * @param board the board to be analyzed
   * @return true if there is any conflict on the given board (board is invalid); false otherwise
   */
  boolean areConflictsOn(SkyscraperBoard board);

  /**
   *
   * @param board the board to be analyzed
   * @return how many conflicts there are for the current board; 0 if none
   */
  int countConflicts(SkyscraperBoard board);
}
