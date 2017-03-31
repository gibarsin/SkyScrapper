package ar.edu.itba.sia.core.helper;

import static ar.edu.itba.sia.core.helper.IOService.ExitStatus.BAD_FILE_FORMAT;
import static ar.edu.itba.sia.core.helper.IOService.ExitStatus.COULD_NOT_OPEN_INPUT_FILE;

import ar.edu.itba.sia.game.ArrayVisibility;
import ar.edu.itba.sia.game.Point;
import ar.edu.itba.sia.game.Visibility;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class InputSerializerHelper {
  private static final int TOP = 0;
  private static final int BOTTOM = 1;
  private static final int LEFT = 2;
  private static final int RIGHT = 3;
  private static final int EMPTY_CELL = 0;
  private static final int VISIBILITY_LINES = 4;

  public static DataFileContent loadDataFile(final String dataPath) {
    final Path dataFilePath = Paths.get(dataPath);
    if (!IOService.openInputFile(dataFilePath)) {
      IOService.exit(COULD_NOT_OPEN_INPUT_FILE, dataFilePath);
      // should never reach here
      throw new IllegalStateException();
    }
    final DataFileContent dataFileContent;
    try {
      dataFileContent = InputSerializerHelper.readDataFile(dataFilePath);
    } catch (final InputMismatchException e) {
      IOService.exit(BAD_FILE_FORMAT, e);
      // should never reach here
      throw new IllegalStateException();
    }
    IOService.closeInputFile(dataFilePath);
    return dataFileContent;
  }

  private static DataFileContent readDataFile(final Path dataFilePath) {
    final Stream<String> stream = IOService.readLines(dataFilePath);
    final Iterator<String> streamLines = stream.iterator();

    final int n = IOService.parseAsInt(streamLines.next(), "<matrix_size>");

    final List<Point> fixedCells = new LinkedList<>();
    final FullMatrix fullMatrix = new FullMatrix(n);

    // scan game matrix
    final int[][] matrix = new int[n][n];
    for (int i = 0 ; i < n ; i++) {
      final Scanner numScanner = new Scanner(streamLines.next());
      for (int j = 0 ; j < n ; j++) {
        matrix[i][j] = numScanner.nextInt();
        if (matrix[i][j] != EMPTY_CELL) {
          fixedCells.add(new Point(i, j));
          fullMatrix.fix(i, j, matrix[i][j]);
        }
      }
    }

    // skip empty file line added for clarity purposes
    streamLines.next();

    // scan visibility matrix
    final int[][] visibilityMatrix = new int[VISIBILITY_LINES][n];
    for (int i = 0 ; i < VISIBILITY_LINES ; i++) {
      final Scanner numScanner = new Scanner(streamLines.next());
      for (int j = 0 ; j < n ; j++) {
        visibilityMatrix[i][j] = numScanner.nextInt();
      }
    }
    final Visibility visibility = new ArrayVisibility.Builder(
        n,
        visibilityMatrix[TOP], visibilityMatrix[BOTTOM],
        visibilityMatrix[LEFT], visibilityMatrix[RIGHT]
    ).build();

    return new DataFileContent(matrix, fixedCells, visibility, fullMatrix);
  }
}
