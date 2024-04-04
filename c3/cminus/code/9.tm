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
* declare variable v
* declare variable u
* IF STATEMENT
* Operation Expression
 13:    LD 0, -2(5)	* load simplevar
 14:    ST 0, -5(5)	* store simplevar
 15:   LDC 0, 0(0)	* load int into register 0
 16:    ST 0, -6(5)	* store int
 17:    LD 0, -5(5)	* load leftside op
 18:    LD 1, -6(5)	* load rightside op
 19:   SUB 0, 0, 1	* sub
 20:   JGT 0, 2(7)	* equal
 21:   LDC 0, 1(0)	* true
 22:   LDA 7, 1(7)	* jumping over 0 assignment
 23:   LDC 0, 0(0)	* false
 24:    ST 0, -4(5)	* store value in opexp
 25:    LD 0, -4(5)	* load if expression test
 26:   JNE 0, 1(7)	* jump to if statement
* RETURN
 28:   LDC 0, 1(0)	* load int into register 0
 29:    ST 0, -7(5)	* store int
 30:    LD 0, -7(5)	* load return value
 31:    LD 7, -1(5)	* Return to caller
 27:   LDA 7, 5(7)	* jump to after if statement
* RETURN
 33:    ST 5, -8(5)	* Push original frame pointer
 34:   LDA 5, -8(5)	* Push original frame
* Operation Expression
 35:    LD 0, -2(5)	* load simplevar
 36:    ST 0, -3(5)	* store simplevar
 37:    LD 0, -3(5)	* load simplevar
 38:    ST 0, -4(5)	* store simplevar
 39:    LD 0, -3(5)	* load leftside op
 40:    LD 1, -4(5)	* load rightside op
 41:   SUB 0, 0, 1	* sub
 42:    ST 0, -2(5)	* store value in opexp
 43:    LD 0, -3(5)	* load simplevar
 44:    ST 0, -3(5)	* store simplevar
 45:   LDA 0, 1(7)	* Load data with return pointer
 46:   LDA 7, -35(7)	* Jump to function
 47:    LD 5, 0(5)	* Pop frame
 48:    ST 0, -8(5)	* store return value
 49:    LD 0, -8(5)	* load return value
 50:    LD 7, -1(5)	* Return to caller
 32:   LDA 7, 18(7)	* jump to after else
 51:    LD 7, -1(5)	* Return to caller
 11:   LDA 7, 40(7)	* jump around function
* FUNCTION main
 53:    ST 0, -1(5)	* Store return address
* declare variable x
* Assign Expression
 54:   LDA 0, -2(5)	* load simplevar
 55:    ST 0, -4(5)	* store simplevar
 56:    ST 5, -5(5)	* Push original frame pointer
 57:   LDA 5, -5(5)	* Push original frame
 58:   LDC 0, 10(0)	* load int into register 0
 59:    ST 0, -2(5)	* store int
 60:   LDC 0, 3(0)	* load int into register 0
 61:    ST 0, -3(5)	* store int
 62:   LDA 0, 1(7)	* Load data with return pointer
 63:   LDA 7, -52(7)	* Jump to function
 64:    LD 5, 0(5)	* Pop frame
 65:    ST 0, -5(5)	* store return value
 66:    LD 0, -4(5)	* load leftside into reg 0
 67:    LD 1, -5(5)	* load rightside into reg 1
 68:    ST 1, 0(0)	* store into var
 69:    ST 1, -3(5)	* store into assign expression
 70:    LD 0, -2(5)	* 
 71:   OUT 0, 0, 0	* output
* RETURN
 72:    LD 0, -6(5)	* load return value
 73:    LD 7, -1(5)	* Return to caller
 74:    LD 7, -1(5)	* Return to caller
 52:   LDA 7, 22(7)	* jump around function
* FINALE
 75:    ST 5, 0(5)	* Push original frame pointer
 76:   LDA 5, 0(5)	* Push original frame
 77:   LDA 0, 1(7)	* Load data with return pointer
 78:   LDA 7, -26(7)	* Jump to main
 79:    LD 5, 0(5)	* Pop frame
 80: HALT 0, 0, 0
