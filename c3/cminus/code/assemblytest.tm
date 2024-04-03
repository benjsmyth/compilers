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
* declare array i
* declare variable k
* FUNCTION main
 12:    ST 0, -1(5)	* Store return address
* declare variable x
* Assign Expression
 13:   LDA 0, -12(6)	* load simplevar
 14:    ST 0, -4(5)	* store simplevar
 15:   LDC 0, 1(0)	* load int into register 0
 16:    ST 0, -5(5)	* store int
 17:    LD 0, -4(5)	* load leftside into reg 0
 18:    LD 1, -5(5)	* load rightside into reg 1
 19:    ST 1, 0(0)	* store into var
 20:    ST 1, -3(5)	* store into assign expression
 21:    LD 0, -2(5)	* 
 22:   OUT 0, 0, 0	* output
* Assign Expression
 23:   LDA 0, -2(5)	* load simplevar
 24:    ST 0, -7(5)	* store simplevar
 25:    LD 0, -12(6)	* load simplevar
 26:    ST 0, -8(5)	* store simplevar
 27:    LD 0, -7(5)	* load leftside into reg 0
 28:    LD 1, -8(5)	* load rightside into reg 1
 29:    ST 1, 0(0)	* store into var
 30:    ST 1, -6(5)	* store into assign expression
 31:    LD 0, -2(5)	* 
 32:   OUT 0, 0, 0	* output
 33:    LD 7, -1(5)	* Return to caller
 11:   LDA 7, 22(7)	* jump around function
* FINALE
 34:    ST 5, -12(5)	* Push original frame pointer
 35:   LDA 5, -12(5)	* Push original frame
 36:   LDA 0, 1(7)	* Load data with return pointer
 37:   LDA 7, -26(7)	* Jump to main
 38:    LD 5, 0(5)	* Pop frame
 39: HALT 0, 0, 0
