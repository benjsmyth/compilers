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
* Operation Expression
* Operation Expression
 15:   LDC 0, 6(0)	* load int into register 0
 16:    ST 0, -7(5)	* store int
 17:   LDC 0, 3(0)	* load int into register 0
 18:    ST 0, -8(5)	* store int
 19:    LD 0, -7(5)	* load leftside op
 20:    LD 1, -8(5)	* load rightside op
 21:   DIV 0, 0, 1	* div
 22:    ST 0, -6(5)	* store value in opexp
* Operation Expression
 23:   LDC 0, 2(0)	* load int into register 0
 24:    ST 0, -10(5)	* store int
 25:   LDC 0, 4(0)	* load int into register 0
 26:    ST 0, -11(5)	* store int
 27:    LD 0, -10(5)	* load leftside op
 28:    LD 1, -11(5)	* load rightside op
 29:   MUL 0, 0, 1	* mult
 30:    ST 0, -9(5)	* store value in opexp
 31:    LD 0, -6(5)	* load leftside op
 32:    LD 1, -9(5)	* load rightside op
 33:   ADD 0, 0, 1	* add
 34:    ST 0, -5(5)	* store value in opexp
 35:    LD 0, -4(5)	* load leftside into reg 0
 36:    LD 1, -5(5)	* load rightside into reg 1
 37:    ST 1, 0(0)	* store into var
 38:    ST 1, -3(5)	* store into assign expression
 39:    LD 0, -2(5)	* 
 40:   OUT 0, 0, 0	* output
* RETURN
 41:    LD 0, -12(5)	* load return value
 42:    LD 7, -1(5)	* Return to caller
 11:   LDA 7, 31(7)	* jump around function
* FINALE
 43:    ST 5, 0(5)	* Push original frame pointer
 44:   LDA 5, 0(5)	* Push original frame
 45:   LDA 0, 1(7)	* Load data with return pointer
 46:   LDA 7, -35(7)	* Jump to main
 47:    LD 5, 0(5)	* Pop frame
 48: HALT 0, 0, 0
