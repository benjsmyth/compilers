import absyn.*;

public class ShowTreeVisitor implements AbsynVisitor {

  final static int SPACES = 4;

  private void indent( int level ) {
    for( int i = 0; i < level * SPACES; i++ ) System.out.print( " " );
  }

  public void visit( ArrayDec arrayDec, int level ){
    indent( level );
    System.out.println("ArrayDec");

    return;
  }
  
  public void visit( AssignExp assignExp, int level ){
    indent( level );
    System.out.println("AssignExp");

    level ++;
    if (assignExp.lhs != null)
      assignExp.lhs.accept( this, level );

    if (assignExp.rhs != null)
      assignExp.rhs.accept( this, level );
    return;
  }

  public void visit( BoolExp exp, int level ){
    indent( level );
    System.out.println("BoolExp");
    return;
  }

  public void visit( CallExp exp, int level ){
    indent( level );
    System.out.println("CallExp");
    return;
  }

  public void visit( CompoundExp compoundExp, int level ){
    indent( level );
    System.out.println("CompoundExp");
    level ++;
    VarDecList varDecList = compoundExp.decs;
    while( varDecList != null ) {
      varDecList.head.accept( this, level );
      varDecList = varDecList.tail;
    } 
    ExpList expList = compoundExp.exps;
    while( expList != null ) {
      expList.head.accept( this, level );
      expList = expList.tail;
    } 
  }

  public void visit( Dec exp, int level ){
    System.out.println("Dec");
    return;
  }

  public void visit( DecList decList, int level ){
    System.out.println("DecList");
    level ++;
    while( decList != null ) {
      decList.head.accept( this, level );
      decList = decList.tail;
    } 
  }

  public void visit( ExpList exp, int level ){
    System.out.println("ExpList");
    level ++;
    while( exp != null ) {
      exp.head.accept( this, level );
      exp = exp.tail;
    } 
  }
  

  public void visit( FunctionDec functionDec, int level ){
    indent( level );
    System.out.println("FunctionDec");
    level ++;
    VarDecList varDecList = functionDec.params;
    while( varDecList != null ) {
      varDecList.head.accept( this, level );
      varDecList = varDecList.tail;
    } 

    functionDec.body.accept( this, level );
  }

  public void visit( IfExp exp, int level ){
    indent( level );
    System.out.println("IfExp");
    return;
  }

  public void visit( IndexVar exp, int level ){
    System.out.println("IndexVar");
    return;
  }

  public void visit( IntExp exp, int level ){
    indent( level );
    System.out.println("IntExp");
    return;
  }

  public void visit( NameTy exp, int level ){
    System.out.println("NameTy");
    return;
  }

  public void visit( NilExp exp, int level ){
    indent( level );
    System.out.println("NilExp");
    return;
  }

  public void visit( OpExp opExp, int level ){
    indent( level );
    System.out.println("OpExp");
    level ++;
    if (opExp.left != null)
      opExp.left.accept( this, level );
    
    if (opExp.op != -1){
      indent( level );
      System.out.println(opExp.op);
    }

    if (opExp.right != null)
      opExp.right.accept( this, level );


    return;
  }

  public void visit( ReturnExp exp, int level ){
    indent( level );
    System.out.println("ReturnExp");
    return;
  }

  public void visit( SimpleDec exp, int level ){
    indent( level );
    System.out.println("SimpleDec");
    return;
  }

  public void visit( SimpleVar exp, int level ){
    indent( level );
    System.out.println("SimpleVar");
    return;
  }

  public void visit( Var exp, int level ){
    System.out.println("Var");
    return;
  }

  public void visit( VarDec exp, int level ){
    indent( level );
    System.out.println("VarDec");
    return;
  }

  public void visit( VarDecList exp, int level ){
    System.out.println("VarDecList");
    while( exp != null ) {
      exp.head.accept( this, level );
      exp = exp.tail;
    } 
  }

  public void visit( VarExp exp, int level ){
    indent( level );
    System.out.println("VarExp");
    return;
  }

  public void visit( WhileExp exp, int level ){
    indent( level );
    System.out.println("WhileExp");
    return;
  }

}
