# SkyScraper
TP1 for Artificial Intelligence Systems course from I.T.B.A.
Java Implementation of different search algorithms to solve the skyscraper game

## Build
To build the project, it is necessary to have Maven and Java 1.8 installed.
Then, run

    $ mvn clean package

## Execution
To run the program, from the root folder

    $ java -jar target/skyscraper.jar <arguments>

## Usage
`help` argument is a highly detailed help menu that show possible usages of the current program.
So, we highly recommend that for using this jar, you may run

    $ java -jar target/skyscraper.jar help

### Usage examples
Solve a problem described at the file 'resources/games/mx4_1.dat' with the greedy search strategy and put method

    $ java -jar target/skyscraper.jar solve resources/games/mx4_1.dat greedy put

**We recommend to run 'resources/games/mx4_1.dat' and 'resources/games/mx5_1.dat' as our two favourite candidates
 to show how our project works. Run them with all the search methods and heuristics.
 Other used scenarios may be found at the report.**

## Custom games
You could easily configure any desired game following the template guide located at `resources/games/template.dat`.
Then, to solve your created file game, run

    $ java -jar target/skyscraper.jar solve <path/to/created/game.dat> <search_strategy> <method> [<heuristic>]

## Documents
The report (Informe.pdf) and the presentation (Presentacion.pdf & Presentacion.pptx) are located at the `docs` folder.

## Binary
In the `binaries` folder there is a generated .jar with the latest version of the compiled program.
However, we **strongly** recommend to use the above steps to run the project from scratch.

## Authors
This project is written and maintained by

- [Matías Nicolás Comercio Vázquez](https://github.com/MatiasComercio)
- [Gonzalo Ibars Ingman](https://github.com/gibarsin)
- [Matías Mercado](https://github.com/MatiasMercado)
- [Juan Moreno](https://github.com/jpmrno)

## License
    MIT License

    Copyright (c) 2017
      - Matías Nicolás Comercio Vázquez <mcomerciovazquez@gmail.com>
      - Gonzalo Ibars Ingman <gibarsin@itba.edu.ar>
      - Matías Mercado <mmercado@itba.edu.ar>
      - Juan Moreno <jpmrno@itba.edu.ar>
    
    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:
    
    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.
    
    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.