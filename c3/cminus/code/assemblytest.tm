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
 15:   LDC 0, 10(0)	* load int into register 0
 16:    ST 0, -5(5)	* store int
 17:    LD 0, -4(5)	* load leftside into reg 0
 18:    LD 1, -5(5)	* load rightside into reg 1
 19:    ST 1, 0(0)	* store into var
 20:    ST 1, -3(5)	* store into assign expression
 21:    LD 0, -2(5)	* 
 22:   OUT 0, 0, 0	* output
* WHILE STATEMENT
* Operation Expression
 24:    LD 0, -2(5)	* load simplevar
 25:    ST 0, -7(5)	* store simplevar
 26:   LDC 0, 0(0)	* load int into register 0
 27:    ST 0, -8(5)	* store int
 28:    LD 0, -7(5)	* load leftside op
 29:    LD 1, -8(5)	* load rightside op
 30:   SUB 0, 0, 1	* sub
 31:   JLE 0, 2(7)	* equal
 32:   LDC 0, 1(0)	* true
 33:   LDA 7, 1(7)	* jumping over 0 assignment
 34:   LDC 0, 0(0)	* false
 35:    ST 0, -6(5)	* store value in opexp
 36:    LD 0, -6(5)	* load while expression test
* Assign Expression
 37:   LDA 0, -2(5)	* load simplevar
 38:    ST 0, -10(5)	* store simplevar
* Operation Expression
 39:    LD 0, -2(5)	* load simplevar
 40:    ST 0, -12(5)	* store simplevar
 41:   LDC 0, 1(0)	* load int into register 0
 42:    ST 0, -13(5)	* store int
 43:    LD 0, -12(5)	* load leftside op
 44:    LD 1, -13(5)	* load rightside op
 45:   SUB 0, 0, 1	* sub
 46:    ST 0, -11(5)	* store value in opexp
 47:    LD 0, -10(5)	* load leftside into reg 0
 48:    LD 1, -11(5)	* load rightside into reg 1
 49:    ST 1, 0(0)	* store into var
 50:    ST 1, -9(5)	* store into assign expression
 51:    LD 0, -2(5)	* 
 52:   OUT 0, 0, 0	* output
 53:   LDA 7, -31(7)	* loop
 23:   JEQ 0, 30(7)	* jump to after while statement
 54:    LD 7, -1(5)	* Return to caller
 11:   LDA 7, 43(7)	* jump around function
* FINALE
 55:    ST 5, 0(5)	* Push original frame pointer
 56:   LDA 5, 0(5)	* Push original frame
 57:   LDA 0, 1(7)	* Load data with return pointer
 58:   LDA 7, -47(7)	* Jump to main
 59:    LD 5, 0(5)	* Pop frame
 60: HALT 0, 0, 0
