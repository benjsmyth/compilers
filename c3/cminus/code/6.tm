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
* Assign Expression
 23:   LDA 0, -2(5)	* load simplevar
 24:    ST 0, -8(5)	* store simplevar
* Operation Expression
 25:   LDC 0, 1(0)	* load bool into register 0
 26:    ST 0, -10(5)	* store bool
 27:    LD 0, -3(5)	* load simplevar
 28:    ST 0, -11(5)	* store simplevar
 29:    LD 0, -10(5)	* load leftside op
 30:    LD 1, -11(5)	* load rightside op
 31:   ADD 0, 0, 1	* add
 32:   JEQ 0, 2(7)	* equal
 33:   LDC 0, 1(0)	* true
 34:   LDA 7, 1(7)	* jumping over 0 assignment
 35:   LDC 0, 0(0)	* false
 36:    ST 0, -9(5)	* store value in opexp
 37:    LD 0, -8(5)	* load leftside into reg 0
 38:    LD 1, -9(5)	* load rightside into reg 1
 39:    ST 1, 0(0)	* store into var
 40:    ST 1, -7(5)	* store into assign expression
 41:    LD 0, -2(5)	* 
 42:   OUT 0, 0, 0	* output
* RETURN
 43:    LD 0, -12(5)	* load return value
 44:    LD 7, -1(5)	* Return to caller
 11:   LDA 7, 33(7)	* jump around function
* FINALE
 45:    ST 5, 0(5)	* Push original frame pointer
 46:   LDA 5, 0(5)	* Push original frame
 47:   LDA 0, 1(7)	* Load data with return pointer
 48:   LDA 7, -37(7)	* Jump to main
 49:    LD 5, 0(5)	* Pop frame
 50: HALT 0, 0, 0
