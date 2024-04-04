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
 15:   LDC 0, 5(0)	* load int into register 0
 16:    ST 0, -5(5)	* store int
 17:    LD 0, -4(5)	* load leftside into reg 0
 18:    LD 1, -5(5)	* load rightside into reg 1
 19:    ST 1, 0(0)	* store into var
 20:    ST 1, -3(5)	* store into assign expression
 21:    LD 0, -3(5)	* 
 22:   OUT 0, 0, 0	* output
* RETURN
 23:    LD 0, -6(5)	* load return value
 24:    LD 7, -1(5)	* Return to caller
 25:    LD 7, -1(5)	* Return to caller
 11:   LDA 7, 14(7)	* jump around function
* FINALE
 26:    ST 5, 0(5)	* Push original frame pointer
 27:   LDA 5, 0(5)	* Push original frame
 28:   LDA 0, 1(7)	* Load data with return pointer
 29:   LDA 7, -18(7)	* Jump to main
 30:    LD 5, 0(5)	* Pop frame
 31: HALT 0, 0, 0
