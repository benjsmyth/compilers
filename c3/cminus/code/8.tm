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
* FUNCTION fun
 12:    ST 0, -1(5)	* Store return address
* RETURN
 13:   LDC 0, 5(0)	* load int into register 0
 14:    ST 0, -2(5)	* store int
 15:    LD 0, -2(5)	* load return value
 16:    LD 7, -1(5)	* Return to caller
 17:    LD 7, -1(5)	* Return to caller
 11:   LDA 7, 6(7)	* jump around function
* FUNCTION main
 19:    ST 0, -1(5)	* Store return address
* declare variable x
* Assign Expression
 20:   LDA 0, -2(5)	* load simplevar
 21:    ST 0, -4(5)	* store simplevar
 22:   LDC 0, 7(0)	* load int into register 0
 23:    ST 0, -5(5)	* store int
 24:    LD 0, -4(5)	* load leftside into reg 0
 25:    LD 1, -5(5)	* load rightside into reg 1
 26:    ST 1, 0(0)	* store into var
 27:    ST 1, -3(5)	* store into assign expression
 28:    LD 0, -2(5)	* 
 29:   OUT 0, 0, 0	* output
* Assign Expression
 30:   LDA 0, -2(5)	* load simplevar
 31:    ST 0, -7(5)	* store simplevar
 32:    ST 5, -8(5)	* Push original frame pointer
 33:   LDA 5, -8(5)	* Push original frame
 34:   LDA 0, 1(7)	* Load data with return pointer
 35:   LDA 7, -24(7)	* Jump to function
 36:    LD 5, 0(5)	* Pop frame
 37:    ST 0, -8(5)	* store return value
 38:    LD 0, -7(5)	* load leftside into reg 0
 39:    LD 1, -8(5)	* load rightside into reg 1
 40:    ST 1, 0(0)	* store into var
 41:    ST 1, -6(5)	* store into assign expression
 42:    LD 0, -2(5)	* 
 43:   OUT 0, 0, 0	* output
* RETURN
 44:    LD 0, -9(5)	* load return value
 45:    LD 7, -1(5)	* Return to caller
 46:    LD 7, -1(5)	* Return to caller
 18:   LDA 7, 28(7)	* jump around function
* FINALE
 47:    ST 5, 0(5)	* Push original frame pointer
 48:   LDA 5, 0(5)	* Push original frame
 49:   LDA 0, 1(7)	* Load data with return pointer
 50:   LDA 7, -32(7)	* Jump to main
 51:    LD 5, 0(5)	* Pop frame
 52: HALT 0, 0, 0
