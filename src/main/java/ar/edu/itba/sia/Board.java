package ar.edu.itba.sia;

import ar.edu.itba.sia.gps.api.GPSState;
import java.util.Arrays;
import static ar.edu.itba.sia.Board.CellContent.*;

public class Board implements GPSState {

    private CellContent[][] board;
    private int hash;
    private int assumptions;
    private int sure;
    private int semiSure;
    private int emptyCells;
    private int fullCols;
    private int fullRows;

    public Board(CellContent[][] initialState) {
        this.board = initialState;
        this.assumptions = 0;
        this.sure = 0;
        this.semiSure = 0;
        this.emptyCells = 0;
        for (int i = 0; i < initialState.length; i++) {
            for (int j = 0; j < initialState.length; j++) {
                if (initialState[i][j] == EMPTY)
                    this.emptyCells++;
                else
                    this.sure++;
            }
        }
        updateHashCode();
    }

    public Board(CellContent[][] initialState, int assumptions, int emptyCells, int sure, int semiSure, int fullRows, int fullCols) {
        this.board = initialState;
        this.sure = sure;
        this.semiSure = semiSure;
        this.assumptions = assumptions;
        this.emptyCells = emptyCells;
        this.fullCols = fullCols;
        this.fullRows = fullRows;
        updateHashCode();
    }

    public boolean isFull() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if(board[i][j] == EMPTY)
                    return false;
            }
        }
        return true;
    }

    public boolean isValid() { //TODO improve perfomance
        int n = board.length;
        boolean fullR, fullC, eqR, eqC;
        int countRowsR, countRowsB, countColsR, countColsB;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                fullR = true;
                fullC = true;
                eqR = true;
                eqC = true;

                for (int k = 0; k < n; k++) {
                    if (board[i][k] != EMPTY) {
                        if (board[i][k] != board[j][k])
                            eqR = false;
                    }else
                        fullR = false;

                    if (board[k][i] != EMPTY) {
                        if (board[k][i] != board[k][j])
                            eqC = false;
                    }else
                        fullC = false;
                }
                if ((fullC && eqC) || (fullR && eqR))
                    return false;
            }
        }

        for (int i = 0; i < n; i++) {
        	countRowsB = 0;
        	countRowsR = 0;
        	countColsB = 0;
        	countColsR = 0;
            for (int j = 0; j < n; j++) {
                if (board[i][j] == RED)
                    countRowsR ++;
                else if (board[i][j] == BLUE)
                    countRowsB ++;
            	
                if (board[j][i] == RED)
                    countColsR ++;
                else if (board[j][i] == BLUE)
                    countColsB ++;

                if (board[i][j] != EMPTY) {
                    if ((j < n-2) && board[i][j] == board[i][j+1] && board[i][j] == board[i][j+2])
                        return false;
                    if ((i < n-2) && board[i][j] == board[i+1][j] && board[i][j] == board[i+2][j])
                        return false;
                }
            }
            if (countColsB > n/2 || countRowsB > n/2 || countColsR > n/2 || countRowsR > n/2)
                return false;
        }
        return true;
    }

    public static Board emptyBoard(int n) {
        CellContent[][] board = new CellContent[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                board[i][j] = EMPTY;
        }
        return new Board(board);
    }

    public Board copy() {
        int n = this.getSize();
        CellContent[][] board = new CellContent[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                board[i][j] = this.board[i][j];
        }
        return new Board(board,this.assumptions,this.emptyCells,this.sure,this.semiSure,this.fullRows,this.fullCols);
    }

    public enum CellContent {
        EMPTY, RED, BLUE
    }

    /*
    *
    *   Getters & Setters.
    *
    * */

    public Integer getSize() {
        return board.length;
    }

    public Board setPiece(int row, int col, CellContent piece) {
        int n = board.length;
        if (!(row >= n || row < 0 || col >= n || col < 0)) {
            if (board[row][col] == EMPTY)
                this.emptyCells--;
            board[row][col] = piece;
            updateHashCode();
        }
        return this;
    }

    public CellContent getPiece(int row, int col) {
        int n = board.length;
        if (row >= n || row < 0 || col >= n || col < 0)
            return null;
        return board[row][col];
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++)
                builder.append(board[i][j].toString().charAt(0));
            builder.append('\n');
        }
        return builder.toString();
    }

    public boolean isFullRow(int row) {
        for (int i = 0; i < this.getSize(); i++) {
            if (board[row][i] == EMPTY)
                return false;
        }
        return true;
    }

    public boolean isFullCol(int col) {
        for (int i = 0; i < this.getSize(); i++) {
            if (board[i][col] == EMPTY)
                return false;
        }
        return true;
    }

    public void incrementFullCols() {
        this.fullCols++;
    }
    public void incrementFullRows() {
        this.fullRows++;
    }

    public int countCellContentInRow(CellContent c, int row) {
        int count = 0;
        for (int i = 0; i < this.getSize(); i++) {
            if(board[row][i] == c)
                count++;
        }
        return count;
    }
    public int countCellContentInCol(CellContent c, int col) {
        int count = 0;
        for (int i = 0; i < this.getSize(); i++) {
            if(board[i][col] == c)
                count++;
        }
        return count;
    }

    public boolean validateMove(final int row, final int col) {
        int n = this.getSize();
        int countRowsR, countRowsB, countColsR, countColsB;
        CellContent piece = this.getPiece(row,col);

        if (piece == EMPTY)
            return true;

        if ((this.getPiece(row-1,col) == piece && this.getPiece(row+1,col) == piece)
            || (this.getPiece(row-1,col) == piece && this.getPiece(row-2,col) == piece)
            || (this.getPiece(row+1,col) == piece && this.getPiece(row+2,col) == piece)
            || (this.getPiece(row,col-1) == piece && this.getPiece(row,col+1) == piece)
            || (this.getPiece(row,col-1) == piece && this.getPiece(row,col-2) == piece)
            || (this.getPiece(row,col+1) == piece && this.getPiece(row,col+2) == piece)) {
            return false;
        }

        countRowsB = 0;
        countRowsR = 0;
        countColsB = 0;
        countColsR = 0;
        for (int j = 0; j < n; j++) {
            if (board[row][j] == RED)
                countRowsR++;
            else if (board[row][j] == BLUE)
                countRowsB++;

            if (board[j][col] == RED)
                countColsR++;
            else if (board[j][col] == BLUE)
                countColsB++;
        }

        if (countColsB > n / 2 || countRowsB > n / 2 || countColsR > n / 2 || countRowsR > n / 2)
            return false;

        /*EQ ROWS & COLS ULTIMO*/
        if (isFullRow(row)) {
            for (int i = 0; i < n; i++) {
                if ((i != row) && isFullRow(i)) {
                    boolean eq = true;
                    for (int j = 0; j < n; j++) {
                        if (board[row][j] != board[i][j]) {
                            eq = false;
                            break;
                        }
                    }
                    if (eq)
                        return false;
                }
            }
        }
        if (isFullCol(col)) {
            for (int j = 0; j < n; j++) {
                if ((j != col) && isFullCol(j)) {
                    boolean eq = true;
                    for (int i = 0; i < n; i++) {
                        if (board[i][col] != board[i][j]) {
                            eq = false;
                            break;
                        }
                    }
                    if (eq)
                        return false;
                }
            }
        }
        return true;
    }

    public void increaseAsumptions() {
        this.assumptions++;
    }

    public void increaseSure() {
        this.sure++;
    }
    public void increaseSemiSure() {
        this.semiSure++;
    }
    public int getAssumptions() {
        return assumptions;
    }
    public int getSure() {
        return sure;
    }
    public int getSemiSure() {
        return semiSure;
    }

    public int getFullCols() {
        return fullCols;
    }

    public int getFullRows() {
        return fullRows;
    }

    public int getEmptyCells() {
        return emptyCells;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Board)) return false;

        Board board1 = (Board) o;

        return emptyCells == board1.emptyCells && Arrays.deepEquals(board, board1.board);
    }

    private void updateHashCode() {
        int result = Arrays.deepHashCode(board);
        result = 31 * result + emptyCells;
        this.hash = result;
    }
    @Override
    public int hashCode() {
        return hash;
    }

    public void updateValues(final int x, final int y) {
        boolean assumption = true;
        CellContent opposite;

        if (this.getPiece(x,y) == CellContent.BLUE)
            opposite = CellContent.RED;
        else
            opposite = CellContent.BLUE;

        if ((this.getPiece(x-1,y) == opposite && this.getPiece(x+1,y) == opposite)
                || (this.getPiece(x-1,y) == opposite && this.getPiece(x-2,y) == opposite)
                || (this.getPiece(x+1,y) == opposite && this.getPiece(x+2,y) == opposite)
                || (this.getPiece(x,y-1) == opposite && this.getPiece(x,y+1) == opposite)
                || (this.getPiece(x,y-1) == opposite && this.getPiece(x,y-2) == opposite)
                || (this.getPiece(x,y+1) == opposite && this.getPiece(x,y+2) == opposite)
                || (this.countCellContentInRow(opposite,x) == this.getSize()/2)
                || (this.countCellContentInCol(opposite,y) == this.getSize()/2)) {
            if (this.getAssumptions() == 0)
                this.increaseSure();
            else
                this.increaseSemiSure();
            assumption = false;
        }
        if (this.isFullCol(y)) {
            this.incrementFullCols();
            assumption = false;
        }
        if (this.isFullRow(x)) {
            this.incrementFullRows();
            assumption = false;
        }
        if (assumption)
            this.increaseAsumptions();
    }
}
