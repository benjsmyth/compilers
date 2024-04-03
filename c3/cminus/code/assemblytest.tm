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
 13:   LDA 0, -2(5)	* load simplevar
 14:    ST 0, -5(5)	* store simplevar
 15:   LDC 0, 10(0)	* load int into register 0
 16:    ST 0, -6(5)	* store int
 17:    LD 0, -5(5)	* load leftside into reg 0
 18:    LD 1, -6(5)	* load rightside into reg 1
 19:    ST 1, 0(0)	* store into simpleVar
 20:    ST 1, -4(5)	* store into assign expression
 21:    LD 0, 0(5)	* 
 22:   OUT 0, 0, 0	* output
* Assign Expression
 23:   LDA 0, -3(5)	* load simplevar
 24:    ST 0, -8(5)	* store simplevar
* Operation Expression
* Operation Expression
 25:    LD 0, -2(5)	* load simplevar
 26:    ST 0, -11(5)	* store simplevar
 27:   LDC 0, 9(0)	* load int into register 0
 28:    ST 0, -12(5)	* store int
 29:    LD 0, -11(5)	* load leftside op
 30:    LD 1, -12(5)	* load rightside op
 31:   SUB 0, 0, 1	* sub
 32:   JNE 0, 2(7)	* equal
 33:   LDC 0, 1(0)	* true
 34:   LDA 7, 1(7)	* jumping over 0 assignment
 35:   LDC 0, 0(0)	* false
 36:    ST 0, -10(5)	* store value in opexp
 37:    LD 0, -10(5)	* load leftside op
 38:   JNE 0, 2(7)	* equal
 39:   LDC 0, 1(0)	* true
 40:   LDA 7, 1(7)	* jumping over 0 assignment
 41:   LDC 0, 0(0)	* false
 42:    ST 0, -9(5)	* store value in opexp
 43:    LD 0, -8(5)	* load leftside into reg 0
 44:    LD 1, -9(5)	* load rightside into reg 1
 45:    ST 1, 0(0)	* store into simpleVar
 46:    ST 1, -7(5)	* store into assign expression
 47:    LD 0, -3(5)	* 
 48:   OUT 0, 0, 0	* output
 49:    LD 0, -1(6)	* load simplevar
 50:    ST 0, -2(6)	* store simplevar
 51:    LD 7, -1(5)	* Return to caller
 11:   LDA 7, 40(7)	* jump around function
* FINALE
 52:    ST 5, -2(5)	* Push original frame pointer
 53:   LDA 5, -2(5)	* Push original frame
 54:   LDA 0, 1(7)	* Load data with return pointer
 55:   LDA 7, -44(7)	* Jump to main
 56:    LD 5, 0(5)	* Pop frame
 57: HALT 0, 0, 0
