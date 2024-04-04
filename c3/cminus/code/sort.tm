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
* declare array y
* FUNCTION minloc
 12:    ST 0, -1(5)	* Store return address
* declare array a
* declare variable low
* declare variable high
* declare variable i
* declare variable x
* declare variable k
* Assign Expression
 13:   LDA 0, -7(5)	* load simplevar
 14:    ST 0, -9(5)	* store simplevar
 15:    LD 0, -3(5)	* load simplevar
 16:    ST 0, -10(5)	* store simplevar
 17:    LD 0, -9(5)	* load leftside into reg 0
 18:    LD 1, -10(5)	* load rightside into reg 1
 19:    ST 1, 0(0)	* store into var
 20:    ST 1, -8(5)	* store into assign expression
 21:    LD 0, -2(5)	* 
 22:   OUT 0, 0, 0	* output
* Assign Expression
 23:   LDA 0, -6(5)	* load simplevar
 24:    ST 0, -12(5)	* store simplevar
 25:    LD 0, -3(5)	* load simplevar
 26:    ST 0, -13(5)	* store simplevar
 27:    LD 1, -13(5)	* load index expression
 28:   LDA 0, -3(5)	* load indexVar
 29:   SUB 0, 0, 1	* get proper offset
 30:    LD 0, 0(0)	* get value from index in array
 31:    ST 0, -13(5)	* store indexVar
 32:    LD 0, -12(5)	* load leftside into reg 0
 33:    LD 1, -13(5)	* load rightside into reg 1
 34:    ST 1, 0(0)	* store into var
 35:    ST 1, -11(5)	* store into assign expression
 36:    LD 0, -2(5)	* 
 37:   OUT 0, 0, 0	* output
* Assign Expression
 38:   LDA 0, -5(5)	* load simplevar
 39:    ST 0, -15(5)	* store simplevar
* Operation Expression
 40:    LD 0, -3(5)	* load simplevar
 41:    ST 0, -17(5)	* store simplevar
 42:   LDC 0, 1(0)	* load int into register 0
 43:    ST 0, -18(5)	* store int
 44:    LD 0, -17(5)	* load leftside op
 45:    LD 1, -18(5)	* load rightside op
 46:   ADD 0, 0, 1	* add
 47:    ST 0, -16(5)	* store value in opexp
 48:    LD 0, -15(5)	* load leftside into reg 0
 49:    LD 1, -16(5)	* load rightside into reg 1
 50:    ST 1, 0(0)	* store into var
 51:    ST 1, -14(5)	* store into assign expression
 52:    LD 0, -2(5)	* 
 53:   OUT 0, 0, 0	* output
* WHILE STATEMENT
* Operation Expression
 54:    LD 0, -5(5)	* load simplevar
 55:    ST 0, -20(5)	* store simplevar
 56:    LD 0, -4(5)	* load simplevar
 57:    ST 0, -21(5)	* store simplevar
 58:    LD 0, -20(5)	* load leftside op
 59:    LD 1, -21(5)	* load rightside op
 60:   SUB 0, 0, 1	* sub
 61:   JGE 0, 2(7)	* equal
 62:   LDC 0, 1(0)	* true
 63:   LDA 7, 1(7)	* jumping over 0 assignment
 64:   LDC 0, 0(0)	* false
 65:    ST 0, -19(5)	* store value in opexp
 66:    LD 0, -19(5)	* load while expression test
* IF STATEMENT
* Operation Expression
 68:    LD 0, -5(5)	* load simplevar
 69:    ST 0, -23(5)	* store simplevar
 70:    LD 1, -23(5)	* load index expression
 71:   LDA 0, -3(5)	* load indexVar
 72:   SUB 0, 0, 1	* get proper offset
 73:    LD 0, 0(0)	* get value from index in array
 74:    ST 0, -23(5)	* store indexVar
 75:    LD 0, -6(5)	* load simplevar
 76:    ST 0, -24(5)	* store simplevar
 77:    LD 0, -23(5)	* load leftside op
 78:    LD 1, -24(5)	* load rightside op
 79:   SUB 0, 0, 1	* sub
 80:   JGE 0, 2(7)	* equal
 81:   LDC 0, 1(0)	* true
 82:   LDA 7, 1(7)	* jumping over 0 assignment
 83:   LDC 0, 0(0)	* false
 84:    ST 0, -22(5)	* store value in opexp
 85:    LD 0, -22(5)	* load if expression test
 86:   JNE 0, 1(7)	* jump to if statement
* Assign Expression
 88:   LDA 0, -6(5)	* load simplevar
 89:    ST 0, -26(5)	* store simplevar
 90:    LD 0, -5(5)	* load simplevar
 91:    ST 0, -27(5)	* store simplevar
 92:    LD 1, -27(5)	* load index expression
 93:   LDA 0, -3(5)	* load indexVar
 94:   SUB 0, 0, 1	* get proper offset
 95:    LD 0, 0(0)	* get value from index in array
 96:    ST 0, -27(5)	* store indexVar
 97:    LD 0, -26(5)	* load leftside into reg 0
 98:    LD 1, -27(5)	* load rightside into reg 1
 99:    ST 1, 0(0)	* store into var
100:    ST 1, -25(5)	* store into assign expression
101:    LD 0, -2(5)	* 
102:   OUT 0, 0, 0	* output
* Assign Expression
103:   LDA 0, -7(5)	* load simplevar
104:    ST 0, -29(5)	* store simplevar
105:    LD 0, -5(5)	* load simplevar
106:    ST 0, -30(5)	* store simplevar
107:    LD 0, -29(5)	* load leftside into reg 0
108:    LD 1, -30(5)	* load rightside into reg 1
109:    ST 1, 0(0)	* store into var
110:    ST 1, -28(5)	* store into assign expression
111:    LD 0, -2(5)	* 
112:   OUT 0, 0, 0	* output
 87:   LDA 7, 26(7)	* jump to after if statement
113:   LDA 7, 0(7)	* jump to after else
* Assign Expression
114:   LDA 0, -5(5)	* load simplevar
115:    ST 0, -32(5)	* store simplevar
* Operation Expression
116:    LD 0, -5(5)	* load simplevar
117:    ST 0, -34(5)	* store simplevar
118:   LDC 0, 1(0)	* load int into register 0
119:    ST 0, -35(5)	* store int
120:    LD 0, -34(5)	* load leftside op
121:    LD 1, -35(5)	* load rightside op
122:   ADD 0, 0, 1	* add
123:    ST 0, -33(5)	* store value in opexp
124:    LD 0, -32(5)	* load leftside into reg 0
125:    LD 1, -33(5)	* load rightside into reg 1
126:    ST 1, 0(0)	* store into var
127:    ST 1, -31(5)	* store into assign expression
128:    LD 0, -2(5)	* 
129:   OUT 0, 0, 0	* output
130:   LDA 7, -77(7)	* loop
 67:   JEQ 0, 63(7)	* jump to after while statement
* RETURN
131:    LD 0, -7(5)	* load simplevar
132:    ST 0, -36(5)	* store simplevar
133:    LD 0, -36(5)	* load return value
134:    LD 7, -1(5)	* Return to caller
 11:   LDA 7, 123(7)	* jump around function
* FUNCTION sort
136:    ST 0, -1(5)	* Store return address
* declare array a
* declare variable low
* declare variable high
* declare variable i
* declare variable k
* Assign Expression
137:   LDA 0, -5(5)	* load simplevar
138:    ST 0, -8(5)	* store simplevar
139:    LD 0, -3(5)	* load simplevar
140:    ST 0, -9(5)	* store simplevar
141:    LD 0, -8(5)	* load leftside into reg 0
142:    LD 1, -9(5)	* load rightside into reg 1
143:    ST 1, 0(0)	* store into var
144:    ST 1, -7(5)	* store into assign expression
145:    LD 0, -2(5)	* 
146:   OUT 0, 0, 0	* output
* WHILE STATEMENT
* Operation Expression
147:    LD 0, -5(5)	* load simplevar
148:    ST 0, -11(5)	* store simplevar
* Operation Expression
149:    LD 0, -4(5)	* load simplevar
150:    ST 0, -13(5)	* store simplevar
151:   LDC 0, 1(0)	* load int into register 0
152:    ST 0, -14(5)	* store int
153:    LD 0, -13(5)	* load leftside op
154:    LD 1, -14(5)	* load rightside op
155:   SUB 0, 0, 1	* sub
156:    ST 0, -12(5)	* store value in opexp
157:    LD 0, -11(5)	* load leftside op
158:    LD 1, -12(5)	* load rightside op
159:   SUB 0, 0, 1	* sub
160:   JGE 0, 2(7)	* equal
161:   LDC 0, 1(0)	* true
162:   LDA 7, 1(7)	* jumping over 0 assignment
163:   LDC 0, 0(0)	* false
164:    ST 0, -10(5)	* store value in opexp
165:    LD 0, -10(5)	* load while expression test
* declare variable t
* Assign Expression
167:   LDA 0, -6(5)	* load simplevar
168:    ST 0, -17(5)	* store simplevar
169:    ST 5, -18(5)	* Push original frame pointer
170:   LDA 5, -18(5)	* Push original frame
