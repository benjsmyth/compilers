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
* Assign Expression
 13:   LDA 0, -2(5)	* load
 14:    ST 0, -5(5)	* store
 15:   LDC 0, 1(0)	* load int into register 0
 16:    ST 0, -6(5)	* store int
 17:    LD 0, -5(5)	* load leftside into reg 0
 18:    LD 1, -6(5)	* load rightside into reg 1
 19:    ST 1, 0(0)	* store into simpleVar
 20:    ST 1, -4(5)	* store into assign expression
 21:    LD 0, -3(5)	* null
 22:   OUT 0, 0, 0	* output
* Assign Expression
 23:   LDA 0, -3(5)	* load
 24:    ST 0, -8(5)	* store
 25:    LD 0, -2(5)	* load
 26:    ST 0, -9(5)	* store
 27:    LD 0, -8(5)	* load leftside into reg 0
 28:    LD 1, -9(5)	* load rightside into reg 1
 29:    ST 1, 0(0)	* store into simpleVar
 30:    ST 1, -7(5)	* store into assign expression
 31:    LD 0, -6(5)	* null
 32:   OUT 0, 0, 0	* output
 33:    LD 0, -1(6)	* load
 34:    ST 0, -2(6)	* store
 35:    LD 7, -1(5)	* Return to caller
 11:   LDA 7, 24(7)	* jump around function
* FINALE
 36:    ST 5, -2(5)	* Push original frame pointer
 37:   LDA 5, -2(5)	* Push original frame
 38:   LDA 0, 1(7)	* Load data with return pointer
 39:   LDA 7, -28(7)	* Jump to main
 40:    LD 5, 0(5)	* Pop frame
 41: HALT 0, 0, 0
