package ar.edu.itba.sia;

import ar.edu.itba.sia.gps.api.GPSProblem;
import ar.edu.itba.sia.gps.api.GPSState;
import ar.edu.itba.sia.gps.core.GPSEngine;
import ar.edu.itba.sia.gps.strategy.SearchStrategy;
import ar.edu.itba.sia.matrix.Matrix4HardDataPut;
import ar.edu.itba.sia.matrix.Matrix4HardDataSwap;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class MainTest {
  private final String name;
  private final GPSProblem problem;
  private final GPSState solvedState;
  private final String method;

  public MainTest(final String name, final GPSProblem problem, final GPSState solvedState,
      final String method) {
    this.name = name;
    this.problem = problem;
    this.solvedState = solvedState;
    this.method = method;
    Assert.assertTrue(problem.isGoal(solvedState));
  }

  @Parameters(name = "Matrix {0}")
  public static Collection<Object[]> data() {
    return Arrays.asList(
        new Matrix4HardDataPut().getData(),
        new Matrix4HardDataSwap().getData()
//        new Matrix9HardDataSwap().getData()
//        Matrix5HardData.getData()
//        Matrix6HardData.getData(),
//        Matrix9HardData.getData()
    );
  }

//  @Test
//  public void testBFS() throws Exception {
//    if (method.equals("PUT")) {
//      final GPSEngine engine = new GPSEngine(problem, SearchStrategy.BFS);
//      engine.findSolution();
//      Assert.assertTrue(engine.getSolutionNode().getState().equals(solvedState));
//    }
//  }
//
//  @Test
//  public void testDFS() throws Exception {
//    if (method.equals("PUT")) {
//      final GPSEngine engine = new GPSEngine(problem, SearchStrategy.DFS);
//      engine.findSolution();
//      Assert.assertTrue(engine.getSolutionNode().getState().equals(solvedState));
//    }
//  }

  @Test
  public void testIDDFS() throws Exception {
    if (method.equals("PUT")) {
      final GPSEngine engine = new GPSEngine(problem, SearchStrategy.IDDFS);
      engine.findSolution();
      Assert.assertTrue(engine.getSolutionNode().getState().equals(solvedState));
    }
  }

  @Test
  public void testGREEDY() throws Exception {
    final GPSEngine engine = new GPSEngine(problem, SearchStrategy.GREEDY);
    engine.findSolution();
    Assert.assertTrue(engine.getSolutionNode().getState().equals(solvedState));
  }

//  @Test
//  public void testASTAR() throws Exception {
//    final GPSEngine engine = new GPSEngine(problem, SearchStrategy.ASTAR);
//    engine.findSolution();
//    Assert.assertTrue(engine.getSolutionNode().getState().equals(solvedState));
//  }
}
