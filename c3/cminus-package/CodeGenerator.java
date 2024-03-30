import absyn.*;
import java.io.PrintStream;

public class CodeGenerator implements AbsynVisitor {
  int mainEntry, globalOffset;
  PrintStream console, stream;
  public static boolean valid;

  private static int ac = 0;
  private static int fp = 5;
  private static int gp = 6;
  private static int pc = 7;

  public CodeGenerator(PrintStream console, PrintStream stream) {
    this.console = console;
    this.stream = stream;
    this.valid = true;
  }

  public void prelude() {
    System.out.println(String.format("0: LD %d, 0(%d)",
      this.gp, this.ac));  // Load global pointer with maxaddress
    System.out.println(String.format("1: LDA %d, 0(%d)",
      this.fp, this.gp));  // Copy global pointer to frame pointer
    System.out.println(String.format("2: ST %d, 0(%d)",
      this.ac, this.ac));  // Clear data address 0
  }
  private void finale() {
    //
  }

  public void printError(String err) {
    SemanticAnalyzer.valid = false;
    System.setOut(this.console);
    System.err.println(err);
    System.setOut(this.stream);
  }

  // Emitting routines
  private void emitCode(String code) {
    System.out.println(code);
  }
  private void emitError(String message, int row, int col) {
    System.err.println(String.format("Error: %s at row %d, column %d",
      message, row, col)
    );
  }

  // Visitor wrapper
  public void visit(Absyn tree) {
    this.prelude();
    // Generate I/O routines
    tree.accept(this, 0, false);
    this.finale();
  }

  // Visitor methods
  public void visit(ArrayDec arrayDec, int level, boolean isAddr) {
    level++;
    switch (arrayDec.typ.type){
      case 0:
        // System.out.println("type: BOOL");
        break;
      case 1:
        // System.out.println("type: INT");
        break;
      case 2:
        // System.out.println("type: VOID");
        break;
      default:
        // System.err.println("Error: Invalid Typing");
        break;
    }
    // System.out.println("name: " + arrayDec.name);
    // System.out.println("size: " + arrayDec.size);
  }

  public void visit(AssignExp assignExp, int level, boolean isAddress) {
    level++;
    if (assignExp.lhs != null)
      assignExp.lhs.accept(this, level, isAddress);
    if (assignExp.rhs != null)
      assignExp.rhs.accept(this, level, isAddress);
  }

  public void visit(BoolExp boolExp, int level, boolean isAddress) {
    level++;
    System.out.println("value: " + boolExp.value);
  }

  public void visit(CallExp callExp, int level, boolean isAddress) {
    level++;
    // System.out.println("function name: " + callExp.func);
    if (callExp.args != null)
      callExp.args.accept(this, level, isAddress);
  }

  public void visit(CompoundExp compoundExp, int level, boolean isAddress) {
    level++;
    if (compoundExp.decs != null)
      compoundExp.decs.accept(this, level, isAddress);
    if (compoundExp.exps != null)
      compoundExp.exps.accept(this, level, isAddress);
  }

  public void visit(DecList decList, int level, boolean isAddress) {
    level++;
    while(decList != null) {
      if (decList.head != null)
        decList.head.accept(this, level, isAddress);
      decList = decList.tail;
    }
  }

  public void visit(ExpList expList, int level, boolean isAddress) {
    level++;
    while(expList != null) {
      if (expList.head != null)
        expList.head.accept(this, level, isAddress);
      expList = expList.tail;
    }
  }

  public void visit(FunctionDec functionDec, int level, boolean isAddress) {
    level++;
    if (functionDec.params != null)
      functionDec.params.accept(this, level, isAddress);
    if (functionDec.body != null)
      functionDec.body.accept(this, level, isAddress);
  }

  public void visit(IfExp ifExp, int level, boolean isAddress) {
    level++;
    if (ifExp.test != null)
      ifExp.test.accept(this, level, isAddress);
    if (ifExp.thenpart != null)
      ifExp.thenpart.accept(this, level, isAddress);
    if (ifExp.elsepart != null) {
       System.out.println("ELSE");
       ifExp.elsepart.accept(this, level, isAddress);
    }
  }

  public void visit(IndexVar indexVar, int level, boolean isAddress) {
    level++;
    System.out.println("name: " + indexVar.name);
    indexVar.index.accept(this, level, isAddress);
  }

  public void visit(IntExp intExp, int level, boolean isAddress) {
    level++;
    System.out.println("value: " + intExp.value);
  }

  public void visit(NameTy nameTy, int level, boolean isAddress) {
    level++;
    switch(nameTy.type) {
      case 0:
        // System.out.println("type: BOOL");
        break;
      case 1:
        // System.out.println("type: INT");
        break;
      case 2:
        // System.out.println("type: VOID");
        break;
      default:
        // System.err.println("Error: Invalid Type");
        break;
    }
  }

  public void visit(NilExp nilExp, int level, boolean isAddress) {
  }

  public void visit(OpExp opExp, int level, boolean isAddress) {
    level++;
    String code = "";
    if (opExp.left != null)
      opExp.left.accept(this, level, isAddress);
    if (opExp.op != -1) {}
    System.out.println();
    if (opExp.right != null)
      opExp.right.accept(this, level, isAddress);
  }

  public void visit(ReturnExp returnExp, int level, boolean isAddress) {
    level++;
    if (returnExp != null)
      returnExp.exp.accept(this, level, isAddress);
  }

  public void visit(SimpleDec simpleDec, int level, boolean isAddress) {
    level++;
    if (simpleDec.typ != null)
      simpleDec.typ.accept(this, level, isAddress);
    // System.out.println("name: " + simpleDec.name);
  }

  public void visit(SimpleVar simpleVar, int level, boolean isAddress) {
    level++;
    // System.out.println("name: " + simpleVar.name);
  }

  public void visit(VarDecList varDecList, int level, boolean isAddress) {
    level++;
    while(varDecList != null) {
      if (varDecList.head != null)
        varDecList.head.accept(this, level, isAddress);
      varDecList = varDecList.tail;
    }
  }

  public void visit(VarExp varExp, int level, boolean isAddress) {
    level++;
    if (varExp.variable != null)
      varExp.variable.accept(this, level, isAddress);
  }

  public void visit(WhileExp whileExp, int level, boolean isAddress) {
    level++;
    if (whileExp.test != null)
      whileExp.test.accept(this, level, isAddress);
    if (whileExp.body != null)
      whileExp.body.accept(this, level, isAddress);
  }
}

  //private String newTemp(int n) {return String.format("t%d", n);}
  /* Code generation methods
  private void genCode(Exp tree) {
    if (tree != null) {
      String code;
      if (tree instanceof OpExp) {
        OpExp opTree = (OpExp)tree;
        String op;
        genCode(opTree.left);
        genCode(opTree.right);
        opTree.temp = newTemp(1);
        switch(opTree.op) {
          case  0: op = "+" ; break;
          case  1: op = "-" ; break;
          case  2: op = "-" ; break;
          case  3: op = "*" ; break;
          case  4: op = "/" ; break;
          case  5: op = "=" ; break;
          case  6: op = "!="; break;
          case  7: op = "<" ; break;
          case  8: op = "<="; break;
          case  9: op = ">" ; break;
          case 10: op = ">="; break;
          case 11: op = "~" ; break;
          case 12: op = "&&"; break;
          case 13: op = "||"; break;
          default: op = "ERROR";
        }
        code = String.format("%s = %s %s %s", opTree.temp,
        opTree.left.temp, op, opTree.right.temp);
        emitCode(code);
      } else if (tree instanceof AssignExp) {
        AssignExp assignTree = (AssignExp)tree;
        genCode(assignTree.rhs);
        assignTree.temp = assignTree.lhs.temp;
        code = String.format("%s = %s", assignTree.lhs.temp, assignTree.rhs.temp);
        emitCode(code);
      }
        else if (tree instanceof IntExp) {}
        else emitError("Could not generate expression", 0, 0);
    }
  }*/
