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
* declare variable i
* FUNCTION main
 12:    ST 0, -1(5)	* Store return address
* declare variable x
* declare variable y
 13:   LDA 0, -2(5)	* load
 14:    ST 0, -4(5)	* store
 15:   LDA 0, -3(5)	* load
 16:    ST 0, -5(5)	* store
 17:   LDA 0, -1(6)	* load
 18:    ST 0, -2(6)	* store
 19:    LD 7, -1(5)	* Return to caller
 11:   LDA 7, 8(7)	* jump around function
* FINALE
 20:    ST 5, -2(5)	* Push original frame pointer
 21:   LDA 5, -2(5)	* Push original frame
 22:   LDA 0, 1(7)	* Load data with return pointer
 23:   LDA 7, -12(7)	* Jump to main
 24:    LD 5, 0(5)	* Pop frame
 25: HALT 0, 0, 0
