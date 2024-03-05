# Checkpoint 1
Disclaimer: The sample parser provided by Dr. Fei Song was used to build this parser.

## Authors
Ben Smyth, Nathan McGuire, Eli Daniels

## Compilation
In `cminus_parser`, run `make` to compile the parser.
Run `make clean` to destroy the parser.

## Execution
In `cminus_parser`, run `java -classpath /usr/share/java/cup.jar:. Main <filename>.cm`
where `filename` is the name of the C- program.

## AST Generation
To generate the abstract syntax tree associated with the C- program, run
`java -classpath /usr/share/java/cup.jar:. Main <filename>.cm -a`. The AST will be written to
`<structure>.abs`.

