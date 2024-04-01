* PRELUDE
  0:    LD 6, 0(0)	* Load global pointer with maximum address
  1:   LDA 5, 0(6)	* Copy global pointer to frame pointer
  2:    ST 0, 0(0)	* Clear maximum address
* IO
* FINALE
  6:    ST 5, 0(5)	* Push original frame pointer
  7:   LDA 5, 0(5)	* Push original frame
  8:   LDA 0, 1(7)	* Load data with return pointer
  9:   LDA 7, -6(7)	* Jump to main
 10:    LD 5, 0(5)	* Pop frame
 11: HALT 0, 0, 0
* FUNCTION main
  3:    ST 0, 0(5)	* Store control link
  4:    ST 1, -1(5)	* Store return address
  5:    LD 7, -1(5)	* Return to caller
