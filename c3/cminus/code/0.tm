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
* declare array arr
* declare variable i
* Assign Expression
 13:   LDA 0, -14(5)	* load simplevar
 14:    ST 0, -16(5)	* store simplevar
 15:   LDC 0, 8(0)	* load int into register 0
 16:    ST 0, -17(5)	* store int
 17:    LD 0, -16(5)	* load leftside into reg 0
 18:    LD 1, -17(5)	* load rightside into reg 1
 19:    ST 1, 0(0)	* store into var
 20:    ST 1, -15(5)	* store into assign expression
 21:    LD 0, -2(5)	* 
 22:   OUT 0, 0, 0	* output
* Assign Expression
 23:   LDA 0, -2(5)	* load simplevar
 24:    ST 0, -19(5)	* store simplevar
 25:   LDC 0, 0(0)	* load int into register 0
 26:    ST 0, -20(5)	* store int
 27:    LD 0, -19(5)	* load leftside into reg 0
 28:    LD 1, -20(5)	* load rightside into reg 1
 29:    ST 1, 0(0)	* store into var
 30:    ST 1, -18(5)	* store into assign expression
 31:    LD 0, -2(5)	* 
 32:   OUT 0, 0, 0	* output
* Assign Expression
 33:    LD 0, -14(5)	* load simplevar
 34:    ST 0, -22(5)	* store simplevar
 35:    LD 1, -22(5)	* load index expression
 36:   LDA 0, -4(5)	* load indexVar
 37:   SUB 0, 0, 1	* get proper offset
 38:    ST 0, -22(5)	* store indexVar
 39:   LDC 0, 0(0)	* load int into register 0
 40:    ST 0, -23(5)	* store int
 41:    LD 0, -22(5)	* load leftside into reg 0
 42:    LD 1, -23(5)	* load rightside into reg 1
 43:    ST 1, 0(0)	* store into var
 44:    ST 1, -21(5)	* store into assign expression
 45:    LD 0, -2(5)	* 
 46:   OUT 0, 0, 0	* output
* WHILE STATEMENT
* Operation Expression
 47:    LD 0, -14(5)	* load simplevar
 48:    ST 0, -25(5)	* store simplevar
 49:    LD 1, -25(5)	* load index expression
 50:   LDA 0, -4(5)	* load indexVar
 51:   SUB 0, 0, 1	* get proper offset
 52:    LD 0, 0(0)	* get value from index in array
 53:    ST 0, -25(5)	* store indexVar
 54:   LDC 0, 10(0)	* load int into register 0
 55:    ST 0, -26(5)	* store int
 56:    LD 0, -25(5)	* load leftside op
 57:    LD 1, -26(5)	* load rightside op
 58:   SUB 0, 0, 1	* sub
 59:   JGE 0, 2(7)	* equal
 60:   LDC 0, 1(0)	* true
 61:   LDA 7, 1(7)	* jumping over 0 assignment
 62:   LDC 0, 0(0)	* false
 63:    ST 0, -24(5)	* store value in opexp
 64:    LD 0, -24(5)	* load while expression test
* Assign Expression
 66:    LD 0, -14(5)	* load simplevar
 67:    ST 0, -28(5)	* store simplevar
 68:    LD 1, -28(5)	* load index expression
 69:   LDA 0, -4(5)	* load indexVar
 70:   SUB 0, 0, 1	* get proper offset
 71:    ST 0, -28(5)	* store indexVar
* Operation Expression
 72:    LD 0, -14(5)	* load simplevar
 73:    ST 0, -30(5)	* store simplevar
 74:    LD 1, -30(5)	* load index expression
 75:   LDA 0, -4(5)	* load indexVar
 76:   SUB 0, 0, 1	* get proper offset
 77:    LD 0, 0(0)	* get value from index in array
 78:    ST 0, -30(5)	* store indexVar
 79:   LDC 0, 1(0)	* load int into register 0
 80:    ST 0, -31(5)	* store int
 81:    LD 0, -30(5)	* load leftside op
 82:    LD 1, -31(5)	* load rightside op
 83:   ADD 0, 0, 1	* add
 84:    ST 0, -29(5)	* store value in opexp
 85:    LD 0, -28(5)	* load leftside into reg 0
 86:    LD 1, -29(5)	* load rightside into reg 1
 87:    ST 1, 0(0)	* store into var
 88:    ST 1, -27(5)	* store into assign expression
 89:    LD 0, -2(5)	* 
 90:   OUT 0, 0, 0	* output
* Assign Expression
 91:   LDA 0, -2(5)	* load simplevar
 92:    ST 0, -33(5)	* store simplevar
* Operation Expression
 93:    LD 0, -2(5)	* load simplevar
 94:    ST 0, -35(5)	* store simplevar
 95:   LDC 0, 1(0)	* load int into register 0
 96:    ST 0, -36(5)	* store int
 97:    LD 0, -35(5)	* load leftside op
 98:    LD 1, -36(5)	* load rightside op
 99:   SUB 0, 0, 1	* sub
100:    ST 0, -34(5)	* store value in opexp
101:    LD 0, -33(5)	* load leftside into reg 0
102:    LD 1, -34(5)	* load rightside into reg 1
103:    ST 1, 0(0)	* store into var
104:    ST 1, -32(5)	* store into assign expression
105:    LD 0, -2(5)	* 
106:   OUT 0, 0, 0	* output
* IF STATEMENT
* Operation Expression
107:    LD 0, -2(5)	* load simplevar
108:    ST 0, -38(5)	* store simplevar
* Operation Expression
109:   LDC 0, 5(0)	* load int into register 0
110:    ST 0, -40(5)	* store int
111:    LD 0, -40(5)	* load leftside op
112:   LDC 1, -1(0)	* negative number to multiply by
113:   MUL 0, 0, 1	* mult
114:    ST 0, -39(5)	* store value in opexp
115:    LD 0, -38(5)	* load leftside op
116:    LD 1, -39(5)	* load rightside op
117:   SUB 0, 0, 1	* sub
118:   JGT 0, 2(7)	* equal
119:   LDC 0, 1(0)	* true
120:   LDA 7, 1(7)	* jumping over 0 assignment
121:   LDC 0, 0(0)	* false
122:    ST 0, -37(5)	* store value in opexp
123:    LD 0, -37(5)	* load if expression test
124:   JNE 0, 1(7)	* jump to if statement
* RETURN
126:    LD 0, -41(5)	* load return value
127:    LD 7, -1(5)	* Return to caller
125:   LDA 7, 3(7)	* jump to after if statement
128:   LDA 7, 0(7)	* jump to after else
129:   LDA 7, -83(7)	* loop
 65:   JEQ 0, 64(7)	* jump to after while statement
130:    LD 7, -1(5)	* Return to caller
 11:   LDA 7, 119(7)	* jump around function
* FINALE
131:    ST 5, 0(5)	* Push original frame pointer
132:   LDA 5, 0(5)	* Push original frame
133:   LDA 0, 1(7)	* Load data with return pointer
134:   LDA 7, -123(7)	* Jump to main
135:    LD 5, 0(5)	* Pop frame
136: HALT 0, 0, 0
