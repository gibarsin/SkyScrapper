package ar.edu.itba.sia;

import ar.edu.itba.sia.gps.api.GPSProblem;
import ar.edu.itba.sia.gps.api.GPSRule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ar.edu.itba.sia.Board.CellContent.*;

public class MyGameFactory {

    public static GPSProblem getNewProblem(Board initialBoard, Heuristic heuristic) throws IOException{

        List<GPSRule> rulz = new ArrayList<>();
//      rulz.add(new TwoInLineRule());
//      rulz.add(new BetweenRule());
//      rulz.add(new SimpleRule(Board.CellContent.BLUE));
//      rulz.add(new SimpleRule(Board.CellContent.RED));

        for (int i = 0; i < initialBoard.getSize(); i++) {
            for (int j = 0; j < initialBoard.getSize(); j++) {
                if (initialBoard.getPiece(i,j) == EMPTY) {
                    rulz.add(new Rule(BLUE, i, j, heuristic.getCost()));
                    rulz.add(new Rule(RED, i, j, heuristic.getCost()));
                }
            }
        }

        return new Game(initialBoard, rulz, heuristic);
    }
}
