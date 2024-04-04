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
* declare array a
* Assign Expression
 13:   LDC 0, 0(0)	* load int into register 0
 14:    ST 0, -14(5)	* store int
 15:    LD 1, -14(5)	* load index expression
 16:   LDA 0, -3(5)	* load indexVar
 17:   SUB 0, 0, 1	* get proper offset
 18:    ST 0, -14(5)	* store indexVar
* Operation Expression
 19:   LDC 0, 1(0)	* load int into register 0
 20:    ST 0, -16(5)	* store int
 21:   LDC 0, 5(0)	* load int into register 0
 22:    ST 0, -17(5)	* store int
 23:    LD 0, -16(5)	* load leftside op
 24:    LD 1, -17(5)	* load rightside op
 25:   ADD 0, 0, 1	* add
 26:    ST 0, -15(5)	* store value in opexp
 27:    LD 0, -14(5)	* load leftside into reg 0
 28:    LD 1, -15(5)	* load rightside into reg 1
 29:    ST 1, 0(0)	* store into var
 30:    ST 1, -13(5)	* store into assign expression
 31:    LD 0, -3(5)	* 
 32:   OUT 0, 0, 0	* output
* RETURN
 33:    LD 0, -18(5)	* load return value
 34:    LD 7, -1(5)	* Return to caller
 35:    LD 7, -1(5)	* Return to caller
 11:   LDA 7, 24(7)	* jump around function
* FINALE
 36:    ST 5, 0(5)	* Push original frame pointer
 37:   LDA 5, 0(5)	* Push original frame
 38:   LDA 0, 1(7)	* Load data with return pointer
 39:   LDA 7, -28(7)	* Jump to main
 40:    LD 5, 0(5)	* Pop frame
 41: HALT 0, 0, 0
