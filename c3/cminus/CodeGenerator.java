import absyn.*;
import java.io.PrintStream;

public class CodeGenerator implements AbsynVisitor {
  protected static boolean valid = true;
  protected static PrintStream console, stream;

  // Internal addresses
  private static int ioAddr = 0; // IO address
  private static int mainAddr = 0; // Main address
  private static int finaleAddr = 0; // Finale address
  private static int emitLoc = 0; // Emit location
  private static int dataOffset = 0; // Difference from ac
  private static int frameOffset = 0; // Difference from fp
  private static int globalOffset = 0; // Difference from gp
  private static int highEmitLoc = 0; // High emit location

  // Simulator addresses
  private static final int ac = 0; // Data address 0
  private static final int fp = 5; // Register 5 (frame pointer)
  private static final int gp = 6; // Register 6 (global pointer)
  private static final int pc = 7; // Register 7 (program counter)

  public CodeGenerator(PrintStream console, PrintStream stream) {
    this.console = console;
    this.stream = stream;
    this.prelude();
    this.io();
  }

  // Emitting shortcuts
  private void HALT() {
    this.emitHalt();
  }

  private void JUMP(int d, String comment) {
    this.LDA(this.pc, d, this.pc, comment);
  }

  private void LD(int r, int d, int s, String comment) {
    this.emitRM("LD", r, d, s, comment);
  }

  private void LDA(int r, int d, int s, String comment) {
    this.emitRM("LDA", r, d, s, comment);
  }

  private void ST(int r, int d, int s, String comment) {
    this.emitRM("ST", r, d, s, comment);
  }

  private void emitCode(String code) {
    System.out.println(String.format("%3d: %s", this.emitLoc, code));
  }

  private void emitCode(String code, String comment) {
    System.out.println(String.format("%3d: %s\t* %s", this.emitLoc, code, comment));
  }

  private void emitComment(String comment) {
    System.out.println(String.format("* %s", comment));
  }

  private void emitError(String message, int location) {
    System.err.println(String.format("Error: %s at emit location %d", message, location));
  }

  private void emitHalt() {
    this.emitCode("HALT 0, 0, 0");
  }

  // Emitting routines
  private void emitRM(String command, int r, int d, int s, String comment) {
    String code = String.format("%5s %d, %d(%d)", command, r, d, s);
    this.emitCode(code, comment);
    this.emitUpdate();
  }

  private void emitRMA(String command, int r, int a, String comment) {
    final int d = a - (this.emitLoc + 1);
    String code = String.format("%5s %d, %d(%d)", command, r, d, this.pc);
    this.emitCode(code, comment);
    this.emitUpdate();
  }

  private void emitRO(String command, int r, int s, int t, String comment) {
    String code = String.format("%5s %d, %d, %d", command, r, s, t);
    this.emitCode(code, comment);
    this.emitUpdate();
  }

  // Emitting utilities
  private void emitBackup(int location) {
    if (location > this.highEmitLoc)
      this.emitError("Could not backup", location);
    this.emitLoc = location;
  }

  private void emitRestore() {
    this.emitLoc = this.highEmitLoc;
  }

  private int emitSkip(int distance) {
    int i = this.emitLoc;
    this.emitLoc += distance;
    if (this.highEmitLoc < this.emitLoc)
      this.highEmitLoc = this.emitLoc;
    return i;
  }

  private void emitUpdate() {
    if (this.highEmitLoc < ++this.emitLoc)
      this.highEmitLoc = this.emitLoc;
  }

  // Prelude, IO, and finale
  public void prelude() {
    emitComment("PRELUDE");
    this.LD(this.gp, 0, this.ac, "Load global pointer with maximum address");
    this.LDA(this.fp, 0, this.gp, "Copy global pointer to frame pointer");
    this.ST(this.ac, 0, this.ac, "Clear maximum address");
  }

  public void io() {

    int savedLoc = emitSkip(1);
    emitComment("IO");
    emitComment("code for input routine");
    this.ST(0, -1, 5, "Store Return");
    this.emitRO("IN", 0, 0, 0, "input");
    this.LD(7, -1, 5, "return to caller");

    emitComment("code for output routine");
    this.ST(0, -1, 5, "Store Return");
    this.LD(0, -2, 5, "load output value");
    this.emitRO("OUT", 0, 0, 0, "output");
    this.LD(7, -1, 5, "return to caller");

    int savedLoc2 = emitSkip(0);
    emitBackup(savedLoc);
    emitRMA("LDA", pc, savedLoc2, "jump around i/o code");
    emitRestore();
    // ...
  }

  public void finale() {
    this.finaleAddr = this.emitLoc;
    emitComment("FINALE");
    this.ST(this.fp, this.globalOffset, this.fp, "Push original frame pointer");
    this.LDA(this.fp, this.globalOffset, this.fp, "Push original frame");
    this.LDA(this.ac, 1, this.pc, "Load data with return pointer");
    this.JUMP(this.mainAddr - this.emitLoc, "Jump to main");
    this.LD(this.fp, 0, this.fp, "Pop frame");
    this.HALT();
  }

  // Visitor methods
  public void visit(ArrayDec arrayDec, int level, boolean isAddr) {
    level++;
    if (arrayDec.nestLevel == 0) {
      ST(this.pc, --this.frameOffset, this.gp, String.format(
          "declare variable %s", arrayDec.name));
    } else {
      ST(this.pc, --this.frameOffset, this.fp, String.format(
          "declare variable %s", arrayDec.name));
    }
    arrayDec.offset = this.frameOffset;
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
    while (decList != null) {
      if (decList.head != null)
        decList.head.accept(this, level, isAddress);
      decList = decList.tail;
    }
  }

  public void visit(ExpList expList, int level, boolean isAddress) {
    level++;
    while (expList != null) {
      if (expList.head != null)
        expList.head.accept(this, level, isAddress);
      expList = expList.tail;
    }
  }

  public void visit(FunctionDec functionDec, int level, boolean isAddr) {
    if (functionDec.params != null)
      functionDec.params.accept(this, level, isAddr);
    if (functionDec.body != null) {
      int bodySize = 1; // Minimum number of instructions (but need to measure dynamically)
      functionDec.funaddr = this.emitSkip(bodySize); // Skip function body

      this.emitComment(String.format("FUNCTION %s", functionDec.func));
      // this.ST(this.dataOffset++, this.frameOffset, this.fp, "Store control link");
      this.ST(this.dataOffset, --this.frameOffset, this.fp, "Store return address");
      int returnOffset = this.frameOffset;
      functionDec.body.accept(this, level, isAddr); // Generate body code
      this.LD(this.pc, returnOffset, this.fp, "Return to caller");

      int savedLoc2 = emitSkip(0);
      emitBackup(functionDec.funaddr);

      emitRMA("LDA", pc, savedLoc2, "jump around function");
      emitRestore();

      if (functionDec.func.equals("main")) {
        this.mainAddr = functionDec.funaddr; // Set main address
        this.finale(); // Generate finale
        this.emitBackup(this.mainAddr); // Backup to main
      }
    }
  }

  public void visit(IfExp ifExp, int level, boolean isAddress) {
    level++;
    if (ifExp.test != null)
      ifExp.test.accept(this, level, isAddress);
    if (ifExp.thenpart != null)
      ifExp.thenpart.accept(this, level, isAddress);
    if (ifExp.elsepart != null) {
      printConsole("ELSE");
      ifExp.elsepart.accept(this, level, isAddress);
    }
  }

  public void visit(IndexVar indexVar, int level, boolean isAddress) {
    level++;
    printConsole("name: " + indexVar.name);
    indexVar.index.accept(this, level, isAddress);
  }

  public void visit(IntExp intExp, int level, boolean isAddress) {
    level++;
    printConsole("value: " + intExp.value);
  }

  public void visit(NameTy nameTy, int level, boolean isAddress) {
    level++;
    switch (nameTy.type) {
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
    if (opExp.op != -1) {
    }
    printConsole("");
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
    if (simpleDec.nestLevel == 0) {
      ST(this.pc, --this.frameOffset, this.gp, String.format(
          "declare variable %s", simpleDec.name));
    } else {
      ST(this.pc, --this.frameOffset, this.fp, String.format(
          "declare variable %s", simpleDec.name));
    }
    simpleDec.offset = this.frameOffset;
  }

  public void visit(SimpleVar simpleVar, int level, boolean isAddress) {
    level++;
    printConsole(((SimpleDec)simpleVar.dtype).name);
  }

  public void visit(VarDecList varDecList, int level, boolean isAddress) {
    level++;
    while (varDecList != null) {
      if (varDecList.head != null){
        varDecList.head.nestLevel = 1;
        varDecList.head.accept(this, level, isAddress);
      }
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

  public void printConsole(String string) {
    System.setOut(this.console);
    System.out.println(string);
    System.setOut(this.stream);
  }
}

// private String newTemp(int n) {return String.format("t%d", n);}
/*
 * Code generation methods
 * private void genCode(Exp tree) {
 * if (tree != null) {
 * String code;
 * if (tree instanceof OpExp) {
 * OpExp opTree = (OpExp)tree;
 * String op;
 * genCode(opTree.left);
 * genCode(opTree.right);
 * opTree.temp = newTemp(1);
 * switch(opTree.op) {
 * case 0: op = "+" ; break;
 * case 1: op = "-" ; break;
 * case 2: op = "-" ; break;
 * case 3: op = "*" ; break;
 * case 4: op = "/" ; break;
 * case 5: op = "=" ; break;
 * case 6: op = "!="; break;
 * case 7: op = "<" ; break;
 * case 8: op = "<="; break;
 * case 9: op = ">" ; break;
 * case 10: op = ">="; break;
 * case 11: op = "~" ; break;
 * case 12: op = "&&"; break;
 * case 13: op = "||"; break;
 * default: op = "ERROR";
 * }
 * code = String.format("%s = %s %s %s", opTree.temp,
 * opTree.left.temp, op, opTree.right.temp);
 * emitCode(code);
 * } else if (tree instanceof AssignExp) {
 * AssignExp assignTree = (AssignExp)tree;
 * genCode(assignTree.rhs);
 * assignTree.temp = assignTree.lhs.temp;
 * code = String.format("%s = %s", assignTree.lhs.temp, assignTree.rhs.temp);
 * emitCode(code);
 * }
 * else if (tree instanceof IntExp) {}
 * else emitError("Could not generate expression", 0, 0);
 * }
 * }
 */
