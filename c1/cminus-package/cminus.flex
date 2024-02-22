/*
  Created By: Fei Song
  File Name: tiny.flex
  To Build: jflex tiny.flex

  and then after the parser is created
    javac Lexer.java
*/
   
/* --------------------------Usercode Section------------------------ */
   
import java_cup.runtime.*;
      
%%
   
/* -----------------Options and Declarations Section----------------- */
   
/* 
   The name of the class JFlex will create will be Lexer.
   Will write the code to the file Lexer.java. 
*/
%class Lexer

%eofval{
  return null;
%eofval};

/*
  The current line number can be accessed with the variable yyline
  and the current column number with the variable yycolumn.
*/
%line
%column
    
/* 
   Will switch to a CUP compatibility mode to interface with a CUP
   generated parser.
*/
%cup

/*
  Declarations
   
  Code between %{ and %}, both of which must be at the beginning of a
  line, will be copied letter to letter into the lexer class source.
  Here you declare member variables and functions that are used inside
  scanner actions.  
*/
%{   

    /* To create a new java_cup.runtime.Symbol with information about
       the current token, the token will have no value in this
       case. */
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }
    
    /* Also creates a new java_cup.runtime.Symbol with information
       about the current token, but this object has a value. */
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}
   

/*
  Macro Declarations
  
  These declarations are regular expressions that will be used latter
  in the Lexical Rules Section.  
*/
   
/* A line terminator is a \r (carriage return), \n (line feed), or
   \r\n. */
LineTerminator = \r|\n|\r\n
   
/* White space is a line terminator, space, tab, or form feed. */
WhiteSpace     = {LineTerminator} | [ \t\f]

ID = [_a-zA-Z][_a-zA-Z0-9]*                                                  //from the c minus specification
NUM = [0-9]+

COMMENT = "/*"(. | {WhiteSpace})*"*/"
   
%%
/* ------------------------Lexical Rules Section---------------------- */
   
/*
   This section contains regular expressions and actions, i.e. Java
   code, that will be executed when the scanner matches the associated
   regular expression. */

"bool"             { System.out.println("bool");  return symbol(sym.BOOL);}   
"else"             { System.out.println("else");  return symbol(sym.ELSE); }
"if"               { System.out.println("if");  return symbol(sym.IF); }
"int"              { System.out.println("int");  return symbol(sym.INT); }
"return"           { System.out.println("return");  return symbol(sym.RETURN); }
"void"             { System.out.println("void");  return symbol(sym.VOID); }
"while"            { System.out.println("while");  return symbol(sym.WHILE);} 

"+"                { System.out.println("+");  return symbol(sym.PLUS);}
"-"                { System.out.println("-");  return symbol(sym.MINUS);}
"*"                { System.out.println("*");  return symbol(sym.MULT);}
"/"                { System.out.println("/");  return symbol(sym.DIV);}
"<"                { System.out.println("<");  return symbol(sym.LESS);}
"<="               { System.out.println("<=");  return symbol(sym.LESSEQ);}
">"                { System.out.println(">");  return symbol(sym.GREATER);}
">="               { System.out.println(">=");  return symbol(sym.GREATEREQ);}
"=="               { System.out.println("==");  return symbol(sym.EQUAL);}
"!="               { System.out.println("!=");  return symbol(sym.NEQUAL);}
"~"                { System.out.println("~");  return symbol(sym.NOT);}
"||"               { System.out.println("||");  return symbol(sym.OR);}
"&&"               { System.out.println("&&");  return symbol(sym.AND);}
"="                { System.out.println("=");  return symbol(sym.ASSIGN);}
";"                { System.out.println(";");  return symbol(sym.SEMICOLON);}
","                { System.out.println(",");  return symbol(sym.COMMA);}
"("                { System.out.println("(");  return symbol(sym.LPAREN);}
")"                { System.out.println(")");  return symbol(sym.RPAREN);}
"["                { System.out.println("[");  return symbol(sym.LBRACKET);}
"]"                { System.out.println("]");  return symbol(sym.RBRACKET);}
"{"                { System.out.println("{");  return symbol(sym.LCURLY);}
"}"                { System.out.println("}");  return symbol(sym.RCURLY);}

{NUM}              { System.out.println("NUM");  return symbol(sym.NUM, yytext()); }
{ID}               { 
                     if (yytext().equals("true") || yytext().equals("false")){
                        System.out.println("TRUTH");
                        return symbol(sym.TRUTH, yytext());
                     }
                     System.out.println("ID");  return symbol(sym.ID, yytext()); 
                   }

{WhiteSpace}+      { /* skip whitespace */ System.out.println("whitespace");  }  
{COMMENT}          { /* skip comments */ System.out.println("comment");  } 
.                  { System.out.println("error");  return symbol(sym.ERROR); }
