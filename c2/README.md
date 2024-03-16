# Checkpoint 2

Scanner, parser, and semantic analyzer.

## Authors
Ben Smyth, Nathan McGuire, Eli Daniels

## Compilation
In `cminus_parser`, run `make` to compile the scanner, parser, and analyzer.
Run `make clean` to destroy the scanner, parser, and analyzer.

## Execution
In `cminus_parser`, run `java -classpath /usr/share/java/cup.jar:. Main <filename>.cm <options>`,
where `filename` is the name of the C- program.

## Options
- `-a`: Generate the AST (abstract syntax tree). The AST will be written to `ast/<filename>.abs`.
- `-s`: Generate the symbol table. The symbol table will be written to `sym/<filename>.sym`.
