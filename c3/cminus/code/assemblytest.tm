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
* Assign Expression
 13:   LDA 0, -2(5)	* load simplevar
 14:    ST 0, -4(5)	* store simplevar
 15:   LDC 0, 0(0)	* load int into register 0
 16:    ST 0, -5(5)	* store int
 17:    LD 0, -4(5)	* load leftside into reg 0
 18:    LD 1, -5(5)	* load rightside into reg 1
 19:    ST 1, 0(0)	* store into var
 20:    ST 1, -3(5)	* store into assign expression
 21:    LD 0, -2(5)	* 
 22:   OUT 0, 0, 0	* output
* IF STATEMENT
* Operation Expression
 23:   LDC 0, 1(0)	* load int into register 0
 24:    ST 0, -7(5)	* store int
 25:   LDC 0, 1(0)	* load int into register 0
 26:    ST 0, -8(5)	* store int
 27:    LD 0, -7(5)	* load leftside op
 28:    LD 1, -8(5)	* load rightside op
 29:   SUB 0, 0, 1	* sub
 30:   JNE 0, 2(7)	* equal
 31:   LDC 0, 1(0)	* true
 32:   LDA 7, 1(7)	* jumping over 0 assignment
 33:   LDC 0, 0(0)	* false
 34:    ST 0, -6(5)	* store value in opexp
 35:    LD 0, -6(5)	* load if expression test
* Assign Expression
 38:   LDA 0, -2(5)	* load simplevar
 39:    ST 0, -10(5)	* store simplevar
 40:   LDC 0, 1(0)	* load int into register 0
 41:    ST 0, -11(5)	* store int
 42:    LD 0, -10(5)	* load leftside into reg 0
 43:    LD 1, -11(5)	* load rightside into reg 1
 44:    ST 1, 0(0)	* store into var
 45:    ST 1, -9(5)	* store into assign expression
 46:    LD 0, -2(5)	* 
 47:   OUT 0, 0, 0	* output
 36:   JNE 0, 1(7)	* jump to after if statement
 37:   JEQ 0, 10(7)	* jump to after else statement
 48:    LD 7, -1(5)	* Return to caller
 11:   LDA 7, 37(7)	* jump around function
* FINALE
 49:    ST 5, 0(5)	* Push original frame pointer
 50:   LDA 5, 0(5)	* Push original frame
 51:   LDA 0, 1(7)	* Load data with return pointer
 52:   LDA 7, -41(7)	* Jump to main
 53:    LD 5, 0(5)	* Pop frame
 54: HALT 0, 0, 0
