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
 15:   LDC 0, 5(0)	* load int into register 0
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
 25:   LDC 0, 3(0)	* load int into register 0
 26:    ST 0, -10(5)	* store int
* Operation Expression
 27:   LDC 0, 2(0)	* load int into register 0
 28:    ST 0, -12(5)	* store int
* Operation Expression
 29:   LDC 0, 10(0)	* load int into register 0
 30:    ST 0, -14(5)	* store int
* Operation Expression
 31:   LDC 0, 1(0)	* load int into register 0
 32:    ST 0, -16(5)	* store int
 33:    LD 0, -2(5)	* load simplevar
 34:    ST 0, -17(5)	* store simplevar
 35:    LD 0, -16(5)	* load leftside op
 36:    LD 1, -17(5)	* load rightside op
 37:   ADD 0, 0, 1	* add
 38:    ST 0, -15(5)	* store value in opexp
 39:    LD 0, -14(5)	* load leftside op
 40:    LD 1, -15(5)	* load rightside op
 41:   ADD 0, 0, 1	* add
 42:    ST 0, -13(5)	* store value in opexp
 43:    LD 0, -12(5)	* load leftside op
 44:    LD 1, -13(5)	* load rightside op
 45:   ADD 0, 0, 1	* add
 46:    ST 0, -11(5)	* store value in opexp
 47:    LD 0, -10(5)	* load leftside op
 48:    LD 1, -11(5)	* load rightside op
 49:   ADD 0, 0, 1	* add
 50:    ST 0, -9(5)	* store value in opexp
 51:    LD 0, -8(5)	* load leftside into reg 0
 52:    LD 1, -9(5)	* load rightside into reg 1
 53:    ST 1, 0(0)	* store into simpleVar
 54:    ST 1, -7(5)	* store into assign expression
 55:    LD 0, -3(5)	* 
 56:   OUT 0, 0, 0	* output
 57:    LD 0, -1(6)	* load simplevar
 58:    ST 0, -2(6)	* store simplevar
 59:    LD 7, -1(5)	* Return to caller
 11:   LDA 7, 48(7)	* jump around function
* FINALE
 60:    ST 5, -2(5)	* Push original frame pointer
 61:   LDA 5, -2(5)	* Push original frame
 62:   LDA 0, 1(7)	* Load data with return pointer
 63:   LDA 7, -52(7)	* Jump to main
 64:    LD 5, 0(5)	* Pop frame
 65: HALT 0, 0, 0
