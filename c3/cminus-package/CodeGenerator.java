import absyn.*;
import java.io.PrintStream;

public class CodeGenerator implements AbsynVisitor {
  int frameOffset, globalOffset, initialFo, mainEntry;
  PrintStream console, stream;
  public static boolean valid;

  private static int ac = 0;  // Data address 0
  private static int ic = 0;  // Instruction counter
  private static int fp = 5;  // Register 5 (frame pointer)
  private static int gp = 6;  // Register 6 (global pointer)
  private static int pc = 7;  // Register 7 (program counter)

  public CodeGenerator(PrintStream console, PrintStream stream) {
    this.console = console;
    this.stream = stream;
    this.valid = true;
    this.frameOffset = 0;
    this.globalOffset = 0;
  }

  // Basic commands
  private String COMMENT(String comment) {
    return String.format("* %s", comment);
  }
  private String HALT() {
    return String.format("%d: HALT 0, 0, 0", this.ic);
  }
  private String JUMP(int d) {
    return this.LDA(this.pc, d, this.pc);
  }
  private String LD(int r, int d, int s) {
    return String.format("%d: LD %d, %d(%d)", this.ic, r, d, s);
  }
  private String LDA(int r, int d, int s) {
    return String.format("%d: LDA %d, %d(%d)", this.ic, r, d, s);
  }
  private String ST(int r, int d, int s) {
    return String.format("%d: ST %d, %d(%d)", this.ic, r, d, s);
  }

  // Code-emitting routines
  private void emitCode(String code) {
    System.out.println(code);
    this.ic++;
  }
  private void emitCode(String code, String comment) {
    System.out.println( String.format("%s %s", code, comment) );
    this.ic++;
  }
  private void emitComment(String comment) {
    System.out.println(comment);
  }
  private void emitError(String message, int row, int col) {
    System.err.println(String.format("Error: %s at row %d, column %d",
      message, row, col)
    );
  }

  // Prelude, IO, and finale
  public void prelude() {
    emitComment(
      this.COMMENT("PRELUDE")
    );
    emitCode(
      this.LD(this.gp, 0, this.ac),
      this.COMMENT("Load global pointer with address 1023")
    );
    emitCode(
      this.LDA(this.fp, 0, this.gp),
      this.COMMENT("Copy global pointer to frame pointer")
    );
    emitCode(
      this.ST(this.ac, 0, this.ac),
      this.COMMENT("Clear data address 0")
    );
  }
  public void io() {
    emitComment(
      this.COMMENT("IO")
    );
    // ...
  }
  public void finale() {
    emitComment(
      this.COMMENT("FINALE")
    );
    emitCode(
      this.ST(this.fp, -1, this.fp),
      this.COMMENT("Push original frame pointer")
    );
    emitCode(
      this.LDA(this.fp, -1, this.fp),
      this.COMMENT("Push original frame")
    );

    emitCode(
      this.LDA(this.ac, 1, this.pc),
      this.COMMENT("Load data address 0 with return pointer")
    );
    emitCode(
      this.JUMP(this.mainEntry - this.ic),
      this.COMMENT("Jump to main")
    );
    emitCode(
      this.LD(this.fp, 0, this.fp),
      this.COMMENT("Pop frame")
    );
    emitCode(
      this.HALT(),
      this.COMMENT("Halt program")
    );
  }

  // Visitor methods
  public void visit(ArrayDec arrayDec, int level, boolean isAddr) {
    level++;
    switch (arrayDec.typ.type){
      case 0:
        break;
      case 1:
        break;
      case 2:
        break;
      default:
        break;
    }
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
  }

  public void visit(CallExp callExp, int level, boolean isAddress) {
    level++;
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

  public void visit(FunctionDec functionDec, int level, boolean isAddr) {
    --this.frameOffset;
    functionDec.funaddr = this.ic;
    emitComment(
      this.COMMENT( String.format("FUNCTION %s", functionDec.func) )
    );
    emitCode(
      this.ST(this.ac, this.frameOffset, this.fp),
      this.COMMENT("Store return address")
    );
    if ( functionDec.func.equals("main") )
      this.mainEntry = this.ic;
    if (functionDec.params != null) {
      functionDec.params.accept(this, level, isAddr);
    }
    if (functionDec.body != null) {
      functionDec.body.accept(this, level, isAddr);
    }
    emitCode(
      this.LD(this.pc, this.frameOffset, this.fp),
      this.COMMENT("Return to caller")
    );
    ++this.frameOffset;
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
        break;
      case 1:
        break;
      case 2:
        break;
      default:
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
  }

  public void visit(SimpleVar simpleVar, int level, boolean isAddress) {
    level++;
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

  public void printError(String err) {
    SemanticAnalyzer.valid = false;
    System.setOut(this.console);
    System.err.println(err);
    System.setOut(this.stream);
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
