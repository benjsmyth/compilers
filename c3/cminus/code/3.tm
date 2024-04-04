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
* declare array a
* declare variable i
* Assign Expression
 13:   LDA 0, -13(5)	* load simplevar
 14:    ST 0, -15(5)	* store simplevar
 15:   LDC 0, 0(0)	* load int into register 0
 16:    ST 0, -16(5)	* store int
 17:    LD 0, -15(5)	* load leftside into reg 0
 18:    LD 1, -16(5)	* load rightside into reg 1
 19:    ST 1, 0(0)	* store into var
 20:    ST 1, -14(5)	* store into assign expression
 21:    LD 0, -2(5)	* 
 22:   OUT 0, 0, 0	* output
* WHILE STATEMENT
* Operation Expression
 23:    LD 0, -13(5)	* load simplevar
 24:    ST 0, -18(5)	* store simplevar
 25:   LDC 0, 10(0)	* load int into register 0
 26:    ST 0, -19(5)	* store int
 27:    LD 0, -18(5)	* load leftside op
 28:    LD 1, -19(5)	* load rightside op
 29:   SUB 0, 0, 1	* sub
 30:   JGE 0, 2(7)	* equal
 31:   LDC 0, 1(0)	* true
 32:   LDA 7, 1(7)	* jumping over 0 assignment
 33:   LDC 0, 0(0)	* false
 34:    ST 0, -17(5)	* store value in opexp
 35:    LD 0, -17(5)	* load while expression test
* Assign Expression
 37:    LD 0, -13(5)	* load simplevar
 38:    ST 0, -21(5)	* store simplevar
 39:    LD 1, -21(5)	* load index expression
 40:   LDA 0, -3(5)	* load indexVar
 41:   SUB 0, 0, 1	* get proper offset
 42:    ST 0, -21(5)	* store indexVar
 43:   LDC 0, 0(0)	* load int into register 0
 44:    ST 0, -22(5)	* store int
 45:    LD 0, -21(5)	* load leftside into reg 0
 46:    LD 1, -22(5)	* load rightside into reg 1
 47:    ST 1, 0(0)	* store into var
 48:    ST 1, -20(5)	* store into assign expression
 49:    LD 0, -2(5)	* 
 50:   OUT 0, 0, 0	* output
* Assign Expression
 51:   LDA 0, -13(5)	* load simplevar
 52:    ST 0, -24(5)	* store simplevar
* Operation Expression
 53:    LD 0, -13(5)	* load simplevar
 54:    ST 0, -26(5)	* store simplevar
 55:   LDC 0, 1(0)	* load int into register 0
 56:    ST 0, -27(5)	* store int
 57:    LD 0, -26(5)	* load leftside op
 58:    LD 1, -27(5)	* load rightside op
 59:   ADD 0, 0, 1	* add
 60:    ST 0, -25(5)	* store value in opexp
 61:    LD 0, -24(5)	* load leftside into reg 0
 62:    LD 1, -25(5)	* load rightside into reg 1
 63:    ST 1, 0(0)	* store into var
 64:    ST 1, -23(5)	* store into assign expression
 65:    LD 0, -2(5)	* 
 66:   OUT 0, 0, 0	* output
 67:   LDA 7, -45(7)	* loop
 36:   JEQ 0, 31(7)	* jump to after while statement
* RETURN
 68:    LD 0, -13(5)	* load simplevar
 69:    ST 0, -28(5)	* store simplevar
 70:    LD 0, -28(5)	* load return value
 71:    LD 7, -1(5)	* Return to caller
 11:   LDA 7, 60(7)	* jump around function
* FUNCTION main
 73:    ST 0, -1(5)	* Store return address
* declare variable c
* declare variable i
* WHILE STATEMENT
* Operation Expression
 74:    LD 0, -3(5)	* load simplevar
 75:    ST 0, -5(5)	* store simplevar
 76:   LDC 0, 10(0)	* load int into register 0
 77:    ST 0, -6(5)	* store int
 78:    LD 0, -5(5)	* load leftside op
 79:    LD 1, -6(5)	* load rightside op
 80:   SUB 0, 0, 1	* sub
 81:   JGE 0, 2(7)	* equal
 82:   LDC 0, 1(0)	* true
 83:   LDA 7, 1(7)	* jumping over 0 assignment
 84:   LDC 0, 0(0)	* false
 85:    ST 0, -4(5)	* store value in opexp
 86:    LD 0, -4(5)	* load while expression test
* Assign Expression
 88:   LDA 0, -2(5)	* load simplevar
 89:    ST 0, -8(5)	* store simplevar
* Operation Expression
 90:    LD 0, -2(5)	* load simplevar
 91:    ST 0, -10(5)	* store simplevar
 92:   LDC 0, 1(0)	* load int into register 0
 93:    ST 0, -11(5)	* store int
 94:    LD 0, -10(5)	* load leftside op
 95:    LD 1, -11(5)	* load rightside op
 96:   ADD 0, 0, 1	* add
 97:    ST 0, -9(5)	* store value in opexp
 98:    LD 0, -8(5)	* load leftside into reg 0
 99:    LD 1, -9(5)	* load rightside into reg 1
100:    ST 1, 0(0)	* store into var
101:    ST 1, -7(5)	* store into assign expression
102:    LD 0, -2(5)	* 
103:   OUT 0, 0, 0	* output
* Assign Expression
104:   LDA 0, -3(5)	* load simplevar
105:    ST 0, -13(5)	* store simplevar
* Operation Expression
106:    LD 0, -3(5)	* load simplevar
107:    ST 0, -15(5)	* store simplevar
108:   LDC 0, 1(0)	* load int into register 0
109:    ST 0, -16(5)	* store int
110:    LD 0, -15(5)	* load leftside op
111:    LD 1, -16(5)	* load rightside op
112:   ADD 0, 0, 1	* add
113:    ST 0, -14(5)	* store value in opexp
114:    LD 0, -13(5)	* load leftside into reg 0
115:    LD 1, -14(5)	* load rightside into reg 1
116:    ST 1, 0(0)	* store into var
117:    ST 1, -12(5)	* store into assign expression
118:    LD 0, -2(5)	* 
119:   OUT 0, 0, 0	* output
120:   LDA 7, -47(7)	* loop
 87:   JEQ 0, 33(7)	* jump to after while statement
* RETURN
121:    LD 0, -17(5)	* load return value
122:    LD 7, -1(5)	* Return to caller
 72:   LDA 7, 50(7)	* jump around function
* FINALE
123:    ST 5, 0(5)	* Push original frame pointer
124:   LDA 5, 0(5)	* Push original frame
125:   LDA 0, 1(7)	* Load data with return pointer
126:   LDA 7, -54(7)	* Jump to main
127:    LD 5, 0(5)	* Pop frame
128: HALT 0, 0, 0
