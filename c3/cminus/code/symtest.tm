* PRELUDE
  0:    LD 6, 0(0)	* Load global pointer with maximum address
  1:   LDA 5, 0(6)	* Copy global pointer to frame pointer
  2:    ST 0, 0(0)	* Clear maximum address
* IO
* code for input routine
  4:    ST 0, -1(5)	* Store Return
  5:    IN 0, 0, 0	* input
  6:    LD 7, -1(5)	* return to caller
* code for output routine
  7:    ST 0, -1(5)	* Store Return
  8:    LD 0, -2(5)	* load output value
  9:   OUT 0, 0, 0	* output
 10:    LD 7, -1(5)	* return to caller
  3:   LDA 7, 7(7)	* jump around i/o code
* FUNCTION foo
 12:    ST 0, -1(5)	* Store return address
 13:    LD 7, -1(5)	* Return to caller
 11:   LDA 7, 2(7)	* jump around function
* FUNCTION main
 15:    ST 0, -2(5)	* Store return address
 16:    LD 7, -2(5)	* Return to caller
 14:   LDA 7, 2(7)	* jump around function
* FINALE
 17:    ST 5, 0(5)	* Push original frame pointer
 18:   LDA 5, 0(5)	* Push original frame
 19:   LDA 0, 1(7)	* Load data with return pointer
 20:   LDA 7, -6(7)	* Jump to main
 21:    LD 5, 0(5)	* Pop frame
 22: HALT 0, 0, 0
