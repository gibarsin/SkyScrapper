package ar.edu.itba.sia.gps.api;

import ar.edu.itba.sia.gps.core.GPSNode;
import ar.edu.itba.sia.gps.core.GPSSolutionNode;
import java.util.Deque;
import java.util.Map;
import java.util.Queue;

public interface SearchStrategyInterface {
  GPSSolutionNode findSolution(GPSProblem problem, Deque<GPSNode> openNodes);
}
