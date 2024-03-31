0: LD 6, 0(0) * Load global pointer with address 1023
1: LDA 5, 0(6) * Copy global pointer to frame pointer
2: ST 0, 0(0) * Clear data address 0
* IO routines here
3: ST 5, -1(5) * Push original frame pointer
4: LDA 5, -1(5) * Push original frame
5: LDA 0, 1(7) * Load data address 0 with return pointer
6: LDA 7, 0(7) * Jump to main
7: LD 5, 0(5) * Pop frame
8: HALT 0, 0, 0 * Halt program
