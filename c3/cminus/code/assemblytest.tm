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
* declare array y
* Assign Expression
 13:   LDA 0, -2(5)	* load simplevar
 14:    ST 0, -6(5)	* store simplevar
 15:   LDC 0, 0(0)	* load bool into register 0
 16:    ST 0, -7(5)	* store bool
 17:    LD 0, -6(5)	* load leftside into reg 0
 18:    LD 1, -7(5)	* load rightside into reg 1
 19:    ST 1, 0(0)	* store into var
 20:    ST 1, -5(5)	* store into assign expression
 21:    LD 0, -4(5)	* 
 22:   OUT 0, 0, 0	* output
* Assign Expression
 23:   LDC 0, 0(0)	* load int into register 0
 24:    ST 0, -9(5)	* store int
 25:    LD 1, -9(5)	* load index expression
 26:   LDA 0, -4(5)	* load indexVar
 27:   SUB 0, 0, 1	* get proper offset
 28:    ST 0, -9(5)	* store indexVar
 29:    LD 0, -2(5)	* load simplevar
 30:    ST 0, -10(5)	* store simplevar
 31:    LD 0, -9(5)	* load leftside into reg 0
 32:    LD 1, -10(5)	* load rightside into reg 1
 33:    ST 1, 0(0)	* store into var
 34:    ST 1, -8(5)	* store into assign expression
 35:    LD 0, -4(5)	* 
 36:   OUT 0, 0, 0	* output
 37:    LD 0, -1(6)	* load simplevar
 38:    ST 0, -2(6)	* store simplevar
 39:    LD 7, -1(5)	* Return to caller
 11:   LDA 7, 28(7)	* jump around function
* FINALE
 40:    ST 5, -2(5)	* Push original frame pointer
 41:   LDA 5, -2(5)	* Push original frame
 42:   LDA 0, 1(7)	* Load data with return pointer
 43:   LDA 7, -32(7)	* Jump to main
 44:    LD 5, 0(5)	* Pop frame
 45: HALT 0, 0, 0
