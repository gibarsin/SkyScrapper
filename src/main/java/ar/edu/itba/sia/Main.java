package ar.edu.itba.sia;

import static ar.edu.itba.sia.core.InputParam.METHOD;

import ar.edu.itba.sia.core.MainProgram;
import ar.edu.itba.sia.core.helper.IOService;
import ar.edu.itba.sia.core.program.HelpProgram;
import ar.edu.itba.sia.core.program.SkyscraperPutProgram;
import ar.edu.itba.sia.core.program.SkyscraperSwapProgram;

public class Main {
  public static void main(String[] args) {
    if (args.length == 0) {
      IOService.exit(IOService.ExitStatus.NO_ARGS, null);
    }
    final MainProgram mainProgram;
    switch (args[0]) {
      case "help":
        mainProgram = new HelpProgram();
        break;
      case "solve":
        mainProgram = initProgram(args);
        break;
      default:
        IOService.exit(IOService.ExitStatus.BAD_ARGUMENT, "Could not select main program");
        return;
    }
    mainProgram.run(args);
  }

  private static MainProgram initProgram(final String[] args) {
    switch (IOService.validArgsAccess(args, METHOD.getIndex())) {
      case "put":
        return new SkyscraperPutProgram(args);
      case "swap":
        return new SkyscraperSwapProgram(args);
      default:
        IOService.exit(IOService.ExitStatus.BAD_ARGUMENT, METHOD.getName());
        // should never return from the above code
        throw new IllegalStateException();
    }
  }
}
