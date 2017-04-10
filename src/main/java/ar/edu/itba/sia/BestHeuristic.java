package ar.edu.itba.sia;

import ar.edu.itba.sia.gps.api.GPSState;

public class BestHeuristic implements Heuristic{

    private Integer size;

    public BestHeuristic(Board board) {
        this.size = board.getSize();
    }

    @Override
    public Integer getHValue(GPSState gpsState) {
        Board board = (Board) gpsState;
        boolean[][] sure = new boolean[size][size];
        int suresLeft = 0;
        for (int i = 0; i < size; i++) {
            if ((board.countCellContentInCol(Board.CellContent.BLUE, i) == board.getSize() / 2
                    || (board.countCellContentInCol(Board.CellContent.RED, i) == board.getSize() / 2))) {
                for (int j = 0; j < size; j++) {
                    if (!sure[j][i] && board.getPiece(j, i) == Board.CellContent.EMPTY) {
                        sure[j][i] = true;
                        suresLeft++;
                    }
                }
            }
            if ((board.countCellContentInRow(Board.CellContent.BLUE, i) == board.getSize()/2)
                    || (board.countCellContentInRow(Board.CellContent.RED, i) == board.getSize()/2)) {
                for (int j = 0; j < size; j++) {
                    if (!sure[i][j] && board.getPiece(i, j) == Board.CellContent.EMPTY) {
                        sure[i][j] = true;
                        suresLeft++;
                    }
                }
            }
            for (int j = 0; j < size; j++) {
                if (board.getPiece(i,j) == Board.CellContent.EMPTY && !sure[i][j]) {
                    if (    areSamePiece(board.getPiece(i-1,j), board.getPiece(i-2,j))
                            || areSamePiece(board.getPiece(i-1,j), board.getPiece(i+1,j))
                            || areSamePiece(board.getPiece(i+1,j), board.getPiece(i+2,j))
                            || areSamePiece(board.getPiece(i,j-1), board.getPiece(i,j-2))
                            || areSamePiece(board.getPiece(i,j-1), board.getPiece(i,j+1))
                            || areSamePiece(board.getPiece(i,j+1), board.getPiece(i,j+2))) {
                        sure[i][j] = true;
                        suresLeft++;
                    }
                }
            }
        }

        return board.getEmptyCells() + 4 * suresLeft;
    }

    private boolean isCellFull(Board.CellContent piece) {
        return (piece != null) && (piece != Board.CellContent.EMPTY);
    }

    private boolean areSamePiece(Board.CellContent piece1, Board.CellContent piece2) {
        return isCellFull(piece1) && isCellFull(piece2) && (piece1 == piece2);
    }

    @Override
    public Integer getCost() {
        return 5;
    }

    @Override
    public String getName() {
        return "THE best heuristic ever";
    }
}
