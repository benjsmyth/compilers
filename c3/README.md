# Checkpoint 3

Scanner/parser, semantic analyzer, and code generator.

## Authors
Ben Smyth, Nathan McGuire, Eli Daniels

## Compilation
In `cminus_parser`, run `make` to compile the scanner/parser, semantic analyzer, and code generator.
Run `make clean` to destroy the scanner/parser, semantic analyzer, and code generator.

## Execution
In `cminus_parser`, run `./cm <filename>.cm <options>` (please ensure that the script is executable by running `chmod +x cm`).
Alternatively, run `java -classpath /usr/share/java/cup.jar:. Main <filename>.cm <options>`.
At least one option must be given.

## Options
- `-a`: Generate the AST (abstract syntax tree). The AST will be written to `ast/<filename>.abs`.
- `-s`: Generate the symbol table. The symbol table output will be written to `sym/<filename>.sym`.
- `-c`: Generate the assembly code. The assembly code will be written to `code/<filename>.tm`.
