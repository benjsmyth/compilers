# Checkpoint 3

Scanner/parser, semantic analyzer, and code generator.

## Authors
Ben Smyth, Nathan McGuire, Eli Daniels

## Compilation
In `cminus`, run `make` to compile all the components.
Run `make clean` to destroy all the components.

## Execution
In `cminus`, run `./cm <filename>.cm <options>` (please ensure that the script is executable).
Alternatively, run `java -classpath /usr/share/java/cup.jar:. Main <filename>.cm <options>`.
At least one option must be given.

## Simulation
In `cminus`, run `sim/tm code/<filename>.tm`.

## Options
- `-a`: Generate the AST (abstract syntax tree). The AST will be written to `ast/<filename>.abs`.
- `-s`: Generate the symbol table. The symbol table output will be written to `sym/<filename>.sym`.
- `-c`: Generate the assembly code. The assembly code will be written to `code/<filename>.tm`.
