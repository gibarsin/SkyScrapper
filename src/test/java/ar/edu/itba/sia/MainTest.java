package ar.edu.itba.sia;

import ar.edu.itba.sia.gps.api.GPSProblem;
import ar.edu.itba.sia.gps.api.GPSState;
import ar.edu.itba.sia.gps.core.GPSEngine;
import ar.edu.itba.sia.gps.strategy.SearchStrategy;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class MainTest {

  private final GPSProblem problem;
  private final SearchStrategy strategy;
  private final GPSState solvedState;

  public MainTest(final GPSProblem problem, final SearchStrategy strategy,
      final GPSState solvedState) {
    this.problem = problem;
    this.strategy = strategy;
    this.solvedState = solvedState;
    problem.isGoal(solvedState);
  }

  @Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(Matrix4HardData.getDataFor(SearchStrategy.BFS),
        Matrix4HardData.getDataFor(SearchStrategy.IDDFS),
        Matrix5HardData.getDataFor(SearchStrategy.BFS),
        Matrix4HardData.getDataFor(SearchStrategy.IDDFS),
        Matrix4HardData.getDataFor(SearchStrategy.DFS),
        Matrix5HardData.getDataFor(SearchStrategy.DFS));


  }

  @Test
  public void test() throws Exception {
    final GPSEngine engine = new GPSEngine(problem, strategy);
    engine.findSolution();
    engine.getSolutionNode().getState().equals(solvedState);
  }
}