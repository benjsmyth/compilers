import absyn.*;

public class CodeGenerator implements AbsynVisitor {

  /* Utility methods */
  private void emitCode(String code) {
    // Not sure what this does. Propagate to parent tree somehow?
  }
  private void emitError(String message, int row, int col) {
    System.err.println(String.format("Error: %s at row %d, column %d",
      message, row, col)
    );
  }
  private String newTemp(int n) {
    return String.format("t%d", n);
  }

  /* Code generation methods */
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
        else if (tree instanceof IntExp) {/* Do nothing */}
        else emitError("Could not generate expression", 0, 0);
      
    }
  }

  /* Visitor methods */
  public void visit(ArrayDec arrayDec, int level, boolean flag) {

    System.out.println("Generating code for ArrayDec:");
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
  
  public void visit(AssignExp assignExp, int level, boolean flag) {
    
    System.out.println("Generating code for AssignExp:");
    level++;
    if (assignExp.lhs != null)
      assignExp.lhs.accept(this, level, flag);
    if (assignExp.rhs != null)
      assignExp.rhs.accept(this, level, flag);
  }

  public void visit(BoolExp boolExp, int level, boolean flag) {
    
    System.out.println("Generating code for BoolExp:");
    level++;
    
    System.out.println("value: " + boolExp.value);
  }

  public void visit(CallExp callExp, int level, boolean flag) {
    
    System.out.println("Generating code for CallExp:");
    level++;
    
    // System.out.println("function name: " + callExp.func);
    if (callExp.args != null)
      callExp.args.accept(this, level, flag);
  }

  public void visit(CompoundExp compoundExp, int level, boolean flag) {
    
    System.out.println("Generating code for CompoundExp:");
    level++;
    if (compoundExp.decs != null)
      compoundExp.decs.accept(this, level, flag);
    if (compoundExp.exps != null)
      compoundExp.exps.accept(this, level, flag);
  }

  public void visit(DecList decList, int level, boolean flag) {
    System.out.println("Generating code for DecList:");
    level++;
    while(decList != null) {
      if (decList.head != null)
        decList.head.accept(this, level, flag);
      decList = decList.tail;
    } 
  }

  public void visit(ExpList expList, int level, boolean flag) {
    
    System.out.println("Generating code for ExpList:");
    level++;
    while(expList != null) {
      if (expList.head != null)
        expList.head.accept(this, level, flag);
      expList = expList.tail;
    } 
  }
  

  public void visit(FunctionDec functionDec, int level, boolean flag) {
    
    System.out.println("Generating code for FunctionDec:");
    level++;
    if (functionDec.params != null)
      functionDec.params.accept(this, level, flag);
    if (functionDec.body != null)
      functionDec.body.accept(this, level, flag);
  }

  public void visit(IfExp ifExp, int level, boolean flag) {
    
    System.out.println("Generating code for IfExp:");
    level++;
    if (ifExp.test != null)
      ifExp.test.accept(this, level, flag);
    if (ifExp.thenpart != null)
      ifExp.thenpart.accept(this, level, flag);
    if (ifExp.elsepart != null) {
       
       System.out.println("ELSE");
       ifExp.elsepart.accept(this, level, flag);
    }
  }

  public void visit(IndexVar indexVar, int level, boolean flag) {
    
    System.out.println("Generating code for IndexVar:");
    level++;
    
    System.out.println("name: " + indexVar.name);
    indexVar.index.accept(this, level, flag);
  }

  public void visit(IntExp intExp, int level, boolean flag) {
    
    System.out.println("Generating code for IntExp:");
    level++;
    
    System.out.println("value: " + intExp.value);
  }

  public void visit(NameTy nameTy, int level, boolean flag) {
    
    System.out.println("Generating code for NameTy:");
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

  public void visit(NilExp nilExp, int level, boolean flag) {
    
    System.out.println("Generating code for NilExp:");
  }

  public void visit(OpExp opExp, int level, boolean flag) {
    level++;
    String code = "";
    if (opExp.left != null)
      opExp.left.accept(this, level, flag);
    if (opExp.op != -1) {
      
    }
    System.out.println();
    if (opExp.right != null)
      opExp.right.accept(this, level, flag);
  }

  public void visit(ReturnExp returnExp, int level, boolean flag) {
    
    System.out.println("Generating code for ReturnExp:");
    level++;
    if (returnExp != null)
      returnExp.exp.accept(this, level, flag);
  }

  public void visit(SimpleDec simpleDec, int level, boolean flag) {
    
    System.out.println("Generating code for SimpleDec:");
    level++;
    if (simpleDec.typ != null)
      simpleDec.typ.accept(this, level, flag);
    
    // System.out.println("name: " + simpleDec.name);
  }

  public void visit(SimpleVar simpleVar, int level, boolean flag) {
    
    System.out.println("Generating code for SimpleVar:");
    level++;
    
    // System.out.println("name: " + simpleVar.name);
  }

  public void visit(VarDecList varDecList, int level, boolean flag) {
    
    System.out.println("Generating code for VarDecList:");
    level++;
    while(varDecList != null) {
      if (varDecList.head != null)
        varDecList.head.accept(this, level, flag);
      varDecList = varDecList.tail;
    } 
  }

  public void visit(VarExp varExp, int level, boolean flag) {
    
    System.out.println("Generating code for VarExp:");
    level++;
    if (varExp.variable != null)
      varExp.variable.accept(this, level, flag);
  }

  public void visit(WhileExp whileExp, int level, boolean flag) {
    
    System.out.println("Generating code for WhileExp:");
    level++;
    if (whileExp.test != null)
      whileExp.test.accept(this, level, flag);
    if (whileExp.body != null)
      whileExp.body.accept(this, level, flag);
  }
}
