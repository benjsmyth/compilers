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
* FUNCTION foo
 12:    ST 0, -1(5)	* Store return address
* declare variable x
* Assign Expression
 13:   LDA 0, -2(5)	* load simplevar
 14:    ST 0, -4(5)	* store simplevar
* Operation Expression
 15:   LDC 0, 1(0)	* load int into register 0
 16:    ST 0, -6(5)	* store int
* Operation Expression
 17:    LD 0, -2(5)	* load simplevar
 18:    ST 0, -8(5)	* store simplevar
* Operation Expression
 19:   LDC 0, 2(0)	* load int into register 0
 20:    ST 0, -10(5)	* store int
 21:   LDC 0, 4(0)	* load int into register 0
 22:    ST 0, -11(5)	* store int
 23:    LD 0, -10(5)	* load leftside op
 24:    LD 1, -11(5)	* load rightside op
 25:   MUL 0, 0, 1	* mult
 26:    ST 0, -9(5)	* store value in opexp
 27:    LD 0, -8(5)	* load leftside op
 28:    LD 1, -9(5)	* load rightside op
 29:   ADD 0, 0, 1	* add
 30:    ST 0, -7(5)	* store value in opexp
 31:    LD 0, -6(5)	* load leftside op
 32:    LD 1, -7(5)	* load rightside op
 33:   ADD 0, 0, 1	* add
 34:    ST 0, -5(5)	* store value in opexp
 35:    LD 0, -4(5)	* load leftside into reg 0
 36:    LD 1, -5(5)	* load rightside into reg 1
 37:    ST 1, 0(0)	* store into var
 38:    ST 1, -3(5)	* store into assign expression
 39:    LD 0, -2(5)	* 
 40:   OUT 0, 0, 0	* output
* RETURN
 41:    LD 0, -2(5)	* load simplevar
 42:    ST 0, -12(5)	* store simplevar
 43:    LD 0, -12(5)	* load return value
 44:    LD 7, -1(5)	* Return to caller
 11:   LDA 7, 33(7)	* jump around function
* FUNCTION main
 46:    ST 0, -1(5)	* Store return address
* declare variable y
* declare variable x
* Assign Expression
 47:   LDA 0, -2(5)	* load simplevar
 48:    ST 0, -5(5)	* store simplevar
 49:    ST 5, -6(5)	* Push original frame pointer
 50:   LDA 5, -6(5)	* Push original frame
 51:   LDC 0, 15(0)	* load int into register 0
 52:    ST 0, -2(5)	* store int
 53:   LDA 0, 1(7)	* Load data with return pointer
 54:   LDA 7, -43(7)	* Jump to function
 55:    LD 5, 0(5)	* Pop frame
 56:    ST 0, -6(5)	* store return value
 57:    LD 0, -5(5)	* load leftside into reg 0
 58:    LD 1, -6(5)	* load rightside into reg 1
 59:    ST 1, 0(0)	* store into var
 60:    ST 1, -4(5)	* store into assign expression
 61:    LD 0, -2(5)	* 
 62:   OUT 0, 0, 0	* output
* Assign Expression
 63:   LDA 0, -3(5)	* load simplevar
 64:    ST 0, -8(5)	* store simplevar
 65:    ST 5, -9(5)	* Push original frame pointer
 66:   LDA 5, -9(5)	* Push original frame
 67:   LDC 0, 15(0)	* load int into register 0
 68:    ST 0, -2(5)	* store int
 69:   LDA 0, 1(7)	* Load data with return pointer
 70:   LDA 7, -59(7)	* Jump to function
 71:    LD 5, 0(5)	* Pop frame
 72:    ST 0, -9(5)	* store return value
 73:    LD 0, -8(5)	* load leftside into reg 0
 74:    LD 1, -9(5)	* load rightside into reg 1
 75:    ST 1, 0(0)	* store into var
 76:    ST 1, -7(5)	* store into assign expression
 77:    LD 0, -2(5)	* 
 78:   OUT 0, 0, 0	* output
* RETURN
 79:    LD 0, -10(5)	* load return value
 80:    LD 7, -1(5)	* Return to caller
 45:   LDA 7, 35(7)	* jump around function
* FINALE
 81:    ST 5, 0(5)	* Push original frame pointer
 82:   LDA 5, 0(5)	* Push original frame
 83:   LDA 0, 1(7)	* Load data with return pointer
 84:   LDA 7, -39(7)	* Jump to main
 85:    LD 5, 0(5)	* Pop frame
 86: HALT 0, 0, 0
