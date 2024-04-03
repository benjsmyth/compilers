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
* declare array i
* FUNCTION main
 12:    ST 0, -1(5)	* Store return address
* declare variable x
* declare array y
* declare variable z
* Assign Expression
 13:   LDA 0, -2(5)	* load simplevar
 14:    ST 0, -7(5)	* store simplevar
 15:   LDC 0, 1(0)	* load bool into register 0
 16:    ST 0, -8(5)	* store bool
 17:    LD 0, -7(5)	* load leftside into reg 0
 18:    LD 1, -8(5)	* load rightside into reg 1
 19:    ST 1, 0(0)	* store into var
 20:    ST 1, -6(5)	* store into assign expression
 21:    LD 0, -5(5)	* 
 22:   OUT 0, 0, 0	* output
* Assign Expression
 23:   LDC 0, 0(0)	* load int into register 0
 24:    ST 0, -10(5)	* store int
 25:    LD 1, -10(5)	* load index expression
 26:   LDA 0, -2(6)	* load indexVar
 27:   SUB 0, 0, 1	* get proper offset
 28:    ST 0, -10(5)	* store indexVar
 29:   OUT 0, 0, 0	* output
 30:    LD 0, -2(5)	* load simplevar
 31:    ST 0, -11(5)	* store simplevar
 32:    LD 0, -10(5)	* load leftside into reg 0
 33:    LD 1, -11(5)	* load rightside into reg 1
 34:    ST 1, 0(0)	* store into var
 35:    ST 1, -9(5)	* store into assign expression
 36:    LD 0, -5(5)	* 
 37:   OUT 0, 0, 0	* output
* Assign Expression
 38:   LDA 0, -5(5)	* load simplevar
 39:    ST 0, -13(5)	* store simplevar
 40:   LDC 0, 0(0)	* load int into register 0
 41:    ST 0, -14(5)	* store int
 42:    LD 1, -14(5)	* load index expression
 43:   LDA 0, -2(6)	* load indexVar
 44:   SUB 0, 0, 1	* get proper offset
 45:    LD 0, 0(0)	* get value from index in array
 46:    ST 0, -14(5)	* store indexVar
 47:    LD 0, -13(5)	* load leftside into reg 0
 48:    LD 1, -14(5)	* load rightside into reg 1
 49:    ST 1, 0(0)	* store into var
 50:    ST 1, -12(5)	* store into assign expression
 51:    LD 0, -5(5)	* 
 52:   OUT 0, 0, 0	* output
 53:    LD 7, -1(5)	* Return to caller
 11:   LDA 7, 42(7)	* jump around function
* FINALE
 54:    ST 5, -11(5)	* Push original frame pointer
 55:   LDA 5, -11(5)	* Push original frame
 56:   LDA 0, 1(7)	* Load data with return pointer
 57:   LDA 7, -46(7)	* Jump to main
 58:    LD 5, 0(5)	* Pop frame
 59: HALT 0, 0, 0
