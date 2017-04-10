package ar.edu.itba.sia;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static ar.edu.itba.sia.Board.CellContent.*;

public class BoardParser {

    public static Board readBoard(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        int n = Integer.parseInt(reader.readLine());
        Board.CellContent[][] board = new Board.CellContent[n][n];
        String line;
        for (int i = 0; i < n; i++) {
            line = reader.readLine();
            if (line == null) {
                reader.close();
                throw new IllegalArgumentException();
            }
            board[i] = parseLine(n,line);
        }
        reader.close();
        return new Board(board);
    }
    private static Board.CellContent[] parseLine(int n, String line) {
        Board.CellContent[] row = new Board.CellContent[n];
        for (int i = 0; i < n; i++) {
            switch (Character.toUpperCase(line.charAt(i))) {
                case '-':
                    row[i] = EMPTY; break;
                case 'R':
                    row[i] = RED; break;
                case 'B':
                    row[i] = BLUE; break;
                default:
                    throw new IllegalArgumentException();
            }
        }
        return row;
    }
}