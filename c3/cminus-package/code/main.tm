* PRELUDE
0: LD 6, 0(0) * Load global pointer with address 1023
1: LDA 5, 0(6) * Copy global pointer to frame pointer
2: ST 0, 0(0) * Clear data address 0
* IO
* FUNCTION main
3: ST 0, -1(5) * Store return address
4: LD 7, -1(5) * Return to caller
* FINALE
5: ST 5, -1(5) * Push original frame pointer
6: LDA 5, -1(5) * Push original frame
7: LDA 0, 1(7) * Load data address 0 with return pointer
8: LDA 7, -4(7) * Jump to main
9: LD 5, 0(5) * Pop frame
10: HALT 0, 0, 0 * Halt program
