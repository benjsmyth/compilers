# Checkpoint 2

Parser and semantic analyzer.

## Authors
Ben Smyth, Nathan McGuire, Eli Daniels

## Compilation
In `cminus_parser`, run `make` to compile the parser and semantic analyzer.
Run `make clean` to destroy the parser.

## Execution
In `cminus_parser`, run `java -classpath /usr/share/java/cup.jar:. Main <filename>.cm`,
where `filename` is the name of the C- program.

## Options
`-a`: Generate the AST (abstract syntax tree).
	Run `java -classpath /usr/share/java/cup.jar:. Main <filename>.cm -a`.
	The AST will be written to `ast/<filename>.abs`.
`-s`: Generate the symbol table.
	Run `java -classpath /usr/share/java/cup.jar:. Main <filename>.cm -s`.
	The symbol table will be written to `sym/<filename>.sym`.
