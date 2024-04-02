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
 11:    ST 7, 0(6)	* declare variable i
* FUNCTION main
 13:    ST 0, -1(5)	* Store return address
 14:    ST 7, -2(5)	* declare variable x
 15:    ST 7, -3(5)	* declare array z
 16:    ST 7, -4(5)	* declare variable y
 17:    LD 7, -1(5)	* Return to caller
 12:   LDA 7, 5(7)	* jump around function
* FINALE
 18:    ST 5, 0(5)	* Push original frame pointer
 19:   LDA 5, 0(5)	* Push original frame
 20:   LDA 0, 1(7)	* Load data with return pointer
 21:   LDA 7, -9(7)	* Jump to main
 22:    LD 5, 0(5)	* Pop frame
 23: HALT 0, 0, 0
