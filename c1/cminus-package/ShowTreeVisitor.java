import absyn.*;

public class ShowTreeVisitor implements AbsynVisitor {

  final static int SPACES = 4;

  private void indent( int level ) {
    for( int i = 0; i < level * SPACES; i++ ) System.out.print( " " );
  }

  public void visit( ArrayDec arrayDec, int level ){
    indent( level );
    System.out.println("ArrayDec");
    level ++;

    indent(level);
    switch (arrayDec.typ.type){
      case 0:
        System.out.println("type: BOOL");
      break;

      case 1:
        System.out.println("type: INT");
      break;

      case 2:
        System.out.println("type: VOID");
      break;

      default:
        System.err.println("Error: Invalid Typing");
        
    }

    indent(level);
    System.out.println("name: " + arrayDec.name);
    indent(level);
    System.out.println("size: " + arrayDec.size);

  }
  
  public void visit( AssignExp assignExp, int level ){
    indent( level );
    System.out.println("AssignExp");

    level ++;
    if (assignExp.lhs != null)
      assignExp.lhs.accept( this, level );

    if (assignExp.rhs != null)
      assignExp.rhs.accept( this, level );

  }

  public void visit( BoolExp boolExp, int level ){
    indent( level );
    System.out.println("BoolExp");
    
    level ++;
    indent(level);
    System.out.println("value: " + boolExp.value);

  }

  public void visit( CallExp callExp, int level ){
    indent( level );
    System.out.println("CallExp");
    
    level ++;

    indent(level);
    System.out.println("function name: " + callExp.func);

    if (callExp.args != null)
      callExp.args.accept( this, level );
  }

  public void visit( CompoundExp compoundExp, int level ){
    indent( level );
    System.out.println("CompoundExp");
    level ++;
    if (compoundExp.decs != null)
      compoundExp.decs.accept( this, level );
    if (compoundExp.exps != null)
      compoundExp.exps.accept( this, level );
  }

  public void visit( DecList decList, int level ){
    System.out.println("DecList");
    level ++;
    while( decList != null ) {
      decList.head.accept( this, level );
      decList = decList.tail;
    } 
  }

  public void visit( ExpList expList, int level ){
    indent(level);
    System.out.println("ExpList");
    level ++;
    while( expList != null ) {
      expList.head.accept( this, level );
      expList = expList.tail;
    } 
  }
  

  public void visit( FunctionDec functionDec, int level ){
    indent( level );
    System.out.println("FunctionDec");
    level ++;
    if (functionDec.params != null)
      functionDec.params.accept( this, level );
    else {
      if (functionDec.body != null)
        functionDec.body.accept( this, level );
    }
  }

  public void visit( IfExp ifExp, int level ){
    indent( level );
    System.out.println( "IfExp:" );
    level++;
    if (ifExp.test != null)
      ifExp.test.accept( this, level );
    if (ifExp.thenpart != null)
      ifExp.thenpart.accept( this, level );
    if (ifExp.elsepart != null ){
       indent(level);
       System.out.println("ELSE");
       ifExp.elsepart.accept( this, level );
    }
  }

  public void visit( IndexVar indexVar, int level ){
    indent(level);
    System.out.println("IndexVar");

    level ++;
    indent(level);
    System.out.println("name: " + indexVar.name);

    indexVar.index.accept( this, level );
  }

  public void visit( IntExp intExp, int level ){
    indent( level );
    System.out.println("IntExp");
    
    level ++;
    indent(level);
    System.out.println("value: " + intExp.value);
  }

  public void visit( NameTy nameTy, int level ){
    indent(level);
    System.out.println("NameTy");

    level ++;
    indent(level);
    switch(nameTy.type){
      case 0:
        System.out.println("type: BOOL");
      break;

      case 1:
        System.out.println("type: INT");
      break;

      case 2:
        System.out.println("type: VOID");
      break;

      default:
        System.err.println("Error: Invalid Type");
    }
  }

  public void visit( NilExp nilExp, int level ){
    indent( level );
    System.out.println("NilExp");
  }

  public void visit( OpExp opExp, int level ){
    indent( level );
    System.out.println("OpExp");
    level ++;
    if (opExp.left != null)
      opExp.left.accept( this, level );
    
    if (opExp.op != -1){
      indent( level );
      System.out.print("Operator: ");
      switch (opExp.op){

        case 0:
          System.out.print("+");
        break;

        case 1:
          System.out.print("-");
        break;

        case 2:
          System.out.print("uminus");
        break;

        case 3:
          System.out.print("*");
        break;

        case 4:
          System.out.print("/");
        break;

        case 5:
          System.out.print("=");
        break;

        case 6:
          System.out.print("!=");
        break;

        case 7:
          System.out.print("<");
        break;

        case 8:
          System.out.print("<=");
        break;

        case 9:
          System.out.print(">");
        break;

        case 10:
          System.out.print(">=");
        break;

        case 11:
          System.out.print("~");
        break;

        case 12:
          System.out.print("&&");
        break;

        case 13:
          System.out.print("||");
        break;

        default:
        System.out.print("Invalid Operation");

      }
    }
    System.out.println();

    if (opExp.right != null)
      opExp.right.accept( this, level );

  }

  public void visit( ReturnExp returnExp, int level ){
    indent( level );
    System.out.println("ReturnExp");

    level ++;
    if (returnExp != null)
      returnExp.exp.accept( this, level );
  }

  public void visit( SimpleDec simpleDec, int level ){
    indent( level );
    System.out.println("SimpleDec");

    level ++;
    if (simpleDec.typ != null)
      simpleDec.typ.accept( this, level );

    indent(level);
    System.out.println("name: " + simpleDec.name);
  }

  public void visit( SimpleVar simpleVar, int level ){
    indent( level );
    System.out.println("SimpleVar");

    level ++;

    indent(level);
    System.out.println("name: " + simpleVar.name);
  }

  public void visit( VarDecList varDecList, int level ){
    indent(level);
    System.out.println("VarDecList");

    level ++;
    while( varDecList != null ) {
      varDecList.head.accept( this, level );
      varDecList = varDecList.tail;
    } 
  }

  public void visit( VarExp varExp, int level ){
    indent( level );
    System.out.println("VarExp");

    level ++;
    if (varExp.variable != null)
      varExp.variable.accept( this, level );
  }

  public void visit( WhileExp whileExp, int level ){
    indent( level );
    System.out.println("WhileExp");
    level++;
    if (whileExp.test != null)
      whileExp.test.accept( this, level );
    if (whileExp.body != null)
      whileExp.body.accept( this, level );
  }

}
