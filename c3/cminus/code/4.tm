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
* FUNCTION main
 12:    ST 0, -1(5)	* Store return address
* declare variable x
* declare variable y
* Assign Expression
 13:   LDA 0, -3(5)	* load simplevar
 14:    ST 0, -5(5)	* store simplevar
 15:   LDC 0, 0(0)	* load bool into register 0
 16:    ST 0, -6(5)	* store bool
 17:    LD 0, -5(5)	* load leftside into reg 0
 18:    LD 1, -6(5)	* load rightside into reg 1
 19:    ST 1, 0(0)	* store into var
 20:    ST 1, -4(5)	* store into assign expression
 21:    LD 0, -2(5)	* 
 22:   OUT 0, 0, 0	* output
* IF STATEMENT
 23:    LD 0, -3(5)	* load simplevar
 24:    ST 0, -7(5)	* store simplevar
 25:    LD 0, -7(5)	* load if expression test
 26:   JNE 0, 1(7)	* jump to if statement
* Assign Expression
 28:   LDA 0, -2(5)	* load simplevar
 29:    ST 0, -9(5)	* store simplevar
 30:   LDC 0, 1(0)	* load int into register 0
 31:    ST 0, -10(5)	* store int
 32:    LD 0, -9(5)	* load leftside into reg 0
 33:    LD 1, -10(5)	* load rightside into reg 1
 34:    ST 1, 0(0)	* store into var
 35:    ST 1, -8(5)	* store into assign expression
 36:    LD 0, -2(5)	* 
 37:   OUT 0, 0, 0	* output
 27:   LDA 7, 11(7)	* jump to after if statement
* Assign Expression
 39:   LDA 0, -2(5)	* load simplevar
 40:    ST 0, -12(5)	* store simplevar
 41:   LDC 0, 0(0)	* load int into register 0
 42:    ST 0, -13(5)	* store int
 43:    LD 0, -12(5)	* load leftside into reg 0
 44:    LD 1, -13(5)	* load rightside into reg 1
 45:    ST 1, 0(0)	* store into var
 46:    ST 1, -11(5)	* store into assign expression
 47:    LD 0, -2(5)	* 
 48:   OUT 0, 0, 0	* output
 38:   LDA 7, 10(7)	* jump to after else
* RETURN
 49:    LD 0, -14(5)	* load return value
 50:    LD 7, -1(5)	* Return to caller
 11:   LDA 7, 39(7)	* jump around function
* FINALE
 51:    ST 5, 0(5)	* Push original frame pointer
 52:   LDA 5, 0(5)	* Push original frame
 53:   LDA 0, 1(7)	* Load data with return pointer
 54:   LDA 7, -43(7)	* Jump to main
 55:    LD 5, 0(5)	* Pop frame
 56: HALT 0, 0, 0
