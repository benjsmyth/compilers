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
 25:   LDC 0, 3(0)	* load int into register 0
 26:    ST 0, -11(5)	* store int
 27:   LDC 0, 3(0)	* load int into register 0
 28:    ST 0, -12(5)	* store int
 29:    LD 0, -11(5)	* load leftside op
 30:    LD 1, -12(5)	* load rightside op
 31:   DIV 0, 0, 1	* div
 32:    ST 0, -10(5)	* store value in opexp
* Operation Expression
* Operation Expression
 33:   LDC 0, 2(0)	* load int into register 0
 34:    ST 0, -15(5)	* store int
 35:   LDC 0, 10(0)	* load int into register 0
 36:    ST 0, -16(5)	* store int
 37:    LD 0, -15(5)	* load leftside op
 38:    LD 1, -16(5)	* load rightside op
 39:   MUL 0, 0, 1	* mult
 40:    ST 0, -14(5)	* store value in opexp
* Operation Expression
 41:   LDC 0, 4(0)	* load int into register 0
 42:    ST 0, -18(5)	* store int
* Operation Expression
 43:   LDC 0, 2(0)	* load int into register 0
 44:    ST 0, -20(5)	* store int
* Operation Expression
 45:    LD 0, -2(5)	* load simplevar
 46:    ST 0, -22(5)	* store simplevar
 47:   LDC 0, 4(0)	* load int into register 0
 48:    ST 0, -23(5)	* store int
 49:    LD 0, -22(5)	* load leftside op
 50:    LD 1, -23(5)	* load rightside op
 51:   ADD 0, 0, 1	* add
 52:    ST 0, -21(5)	* store value in opexp
 53:    LD 0, -20(5)	* load leftside op
 54:    LD 1, -21(5)	* load rightside op
 55:   ADD 0, 0, 1	* add
 56:    ST 0, -19(5)	* store value in opexp
 57:    LD 0, -18(5)	* load leftside op
 58:    LD 1, -19(5)	* load rightside op
 59:   SUB 0, 1, 0	* sub
 60:    ST 0, -17(5)	* store value in opexp
 61:    LD 0, -14(5)	* load leftside op
 62:    LD 1, -17(5)	* load rightside op
 63:   SUB 0, 1, 0	* sub
 64:    ST 0, -13(5)	* store value in opexp
 65:    LD 0, -10(5)	* load leftside op
 66:    LD 1, -13(5)	* load rightside op
 67:   SUB 0, 1, 0	* sub
 68:    ST 0, -9(5)	* store value in opexp
 69:    LD 0, -8(5)	* load leftside into reg 0
 70:    LD 1, -9(5)	* load rightside into reg 1
 71:    ST 1, 0(0)	* store into simpleVar
 72:    ST 1, -7(5)	* store into assign expression
 73:    LD 0, -3(5)	* 
 74:   OUT 0, 0, 0	* output
 75:    LD 0, -1(6)	* load simplevar
 76:    ST 0, -2(6)	* store simplevar
 77:    LD 7, -1(5)	* Return to caller
 11:   LDA 7, 66(7)	* jump around function
* FINALE
 78:    ST 5, -2(5)	* Push original frame pointer
 79:   LDA 5, -2(5)	* Push original frame
 80:   LDA 0, 1(7)	* Load data with return pointer
 81:   LDA 7, -70(7)	* Jump to main
 82:    LD 5, 0(5)	* Pop frame
 83: HALT 0, 0, 0
