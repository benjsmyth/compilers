import absyn.*;

public class ShowTreeVisitor implements AbsynVisitor {

  final static int SPACES = 4;

  private void indent( int level ) {
    for( int i = 0; i < level * SPACES; i++ ) System.out.print( " " );
  }

  public void visit( ArrayDec exp, int level ){
    System.out.println("ArrayDec");
    return;
  }
  
  public void visit( AssignExp exp, int level ){
    System.out.println("AssignExp");
    return;
  }

  public void visit( BoolExp exp, int level ){
    System.out.println("BoolExp");
    return;
  }

  public void visit( CallExp exp, int level ){
    System.out.println("CallExp");
    return;
  }

  public void visit( CompoundExp exp, int level ){
    System.out.println("CompoundExp");
    return;
  }

  public void visit( Dec exp, int level ){
    System.out.println("Dec");
    return;
  }

  public void visit( DecList decList, int level ){
    System.out.println("DecList");
    while( decList != null ) {
      decList.head.accept( this, level );
      decList = decList.tail;
    } 
  }

  public void visit( ExpList exp, int level ){
    System.out.println("ExpList");
    return;
  }

  public void visit( FunctionDec functionDec, int level ){
    System.out.println("FunctionDec");
    VarDecList varDecList = functionDec.params;
    while( varDecList != null ) {
      varDecList.head.accept( this, level );
      varDecList = varDecList.tail;
    } 
  }

  public void visit( IfExp exp, int level ){
    System.out.println("IfExp");
    return;
  }

  public void visit( IndexVar exp, int level ){
    System.out.println("IndexVar");
    return;
  }

  public void visit( IntExp exp, int level ){
    System.out.println("IntExp");
    return;
  }

  public void visit( NameTy exp, int level ){
    System.out.println("NameTy");
    return;
  }

  public void visit( NilExp exp, int level ){
    System.out.println("NilExp");
    return;
  }

  public void visit( OpExp exp, int level ){
    System.out.println("OpExp");
    return;
  }

  public void visit( ReturnExp exp, int level ){
    System.out.println("ReturnExp");
    return;
  }

  public void visit( SimpleDec exp, int level ){
    System.out.println("SimpleDec");
    return;
  }

  public void visit( SimpleVar exp, int level ){
    System.out.println("SimpleVar");
    return;
  }

  public void visit( Var exp, int level ){
    System.out.println("Var");
    return;
  }

  public void visit( VarDec exp, int level ){
    System.out.println("VarDec");
    return;
  }

  public void visit( VarDecList exp, int level ){
    System.out.println("VarDecList");
    return;
  }

  public void visit( VarExp exp, int level ){
    System.out.println("VarExp");
    return;
  }

  public void visit( WhileExp exp, int level ){
    System.out.println("WhileExp");
    return;
  }

}
