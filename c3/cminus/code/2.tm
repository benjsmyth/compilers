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
* declare variable x
* declare variable y
* RETURN
* Operation Expression
 13:    LD 0, -2(5)	* load simplevar
 14:    ST 0, -5(5)	* store simplevar
 15:    LD 0, -3(5)	* load simplevar
 16:    ST 0, -6(5)	* store simplevar
 17:    LD 0, -5(5)	* load leftside op
 18:    LD 1, -6(5)	* load rightside op
 19:   MUL 0, 0, 1	* mult
 20:    ST 0, -4(5)	* store value in opexp
 21:    LD 0, -4(5)	* load return value
 22:    LD 7, -1(5)	* Return to caller
 11:   LDA 7, 11(7)	* jump around function
* FUNCTION main
 24:    ST 0, -1(5)	* Store return address
* declare variable x
* Assign Expression
 25:   LDA 0, -2(5)	* load simplevar
 26:    ST 0, -4(5)	* store simplevar
 27:   LDC 0, 7(0)	* load int into register 0
 28:    ST 0, -5(5)	* store int
 29:    LD 0, -4(5)	* load leftside into reg 0
 30:    LD 1, -5(5)	* load rightside into reg 1
 31:    ST 1, 0(0)	* store into var
 32:    ST 1, -3(5)	* store into assign expression
 33:    LD 0, -2(5)	* 
 34:   OUT 0, 0, 0	* output
* IF STATEMENT
* Operation Expression
 35:    LD 0, -2(5)	* load simplevar
 36:    ST 0, -7(5)	* store simplevar
 37:   LDC 0, 3(0)	* load int into register 0
 38:    ST 0, -8(5)	* store int
 39:    LD 0, -7(5)	* load leftside op
 40:    LD 1, -8(5)	* load rightside op
 41:   SUB 0, 0, 1	* sub
 42:   JLE 0, 2(7)	* equal
 43:   LDC 0, 1(0)	* true
 44:   LDA 7, 1(7)	* jumping over 0 assignment
 45:   LDC 0, 0(0)	* false
 46:    ST 0, -6(5)	* store value in opexp
 47:    LD 0, -6(5)	* load if expression test
 48:   JNE 0, 1(7)	* jump to if statement
* Assign Expression
 50:   LDA 0, -2(5)	* load simplevar
 51:    ST 0, -10(5)	* store simplevar
 52:    ST 5, -11(5)	* Push original frame pointer
 53:   LDA 5, -11(5)	* Push original frame
 54:   LDC 0, 2(0)	* load int into register 0
 55:    ST 0, -2(5)	* store int
 56:   LDC 0, 3(0)	* load int into register 0
 57:    ST 0, -3(5)	* store int
 58:   LDA 0, 1(7)	* Load data with return pointer
 59:   LDA 7, -48(7)	* Jump to function
 60:    LD 5, 0(5)	* Pop frame
 61:    ST 0, -11(5)	* store return value
 62:    LD 0, -10(5)	* load leftside into reg 0
 63:    LD 1, -11(5)	* load rightside into reg 1
 64:    ST 1, 0(0)	* store into var
 65:    ST 1, -9(5)	* store into assign expression
 66:    LD 0, -2(5)	* 
 67:   OUT 0, 0, 0	* output
 49:   LDA 7, 19(7)	* jump to after if statement
 68:   LDA 7, 0(7)	* jump to after else
* RETURN
 69:    LD 0, -12(5)	* load return value
 70:    LD 7, -1(5)	* Return to caller
 23:   LDA 7, 47(7)	* jump around function
* FINALE
 71:    ST 5, 0(5)	* Push original frame pointer
 72:   LDA 5, 0(5)	* Push original frame
 73:   LDA 0, 1(7)	* Load data with return pointer
 74:   LDA 7, -51(7)	* Jump to main
 75:    LD 5, 0(5)	* Pop frame
 76: HALT 0, 0, 0
