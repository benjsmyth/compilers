import absyn.*;

public class ShowTreeVisitor implements AbsynVisitor {

  final static int SPACES = 4;

  private void indent( int level ) {
    for( int i = 0; i < level * SPACES; i++ ) System.out.print( " " );
  }

  public void visit( ArrayDec exp, int level ){
    return;
  }
  
  public void visit( AssignExp exp, int level ){
    return;
  }

  public void visit( BoolExp exp, int level ){
    return;
  }

  public void visit( CallExp exp, int level ){
    return;
  }

  public void visit( CompoundExp exp, int level ){
    return;
  }

  public void visit( Dec exp, int level ){
    return;
  }

  public void visit( DecList exp, int level ){
    return;
  }

  public void visit( ExpList exp, int level ){
    return;
  }

  public void visit( FunctionDec exp, int level ){
    return;
  }

  public void visit( IfExp exp, int level ){
    return;
  }

  public void visit( IndexVar exp, int level ){
    return;
  }

  public void visit( IntExp exp, int level ){
    return;
  }

  public void visit( NameTy exp, int level ){
    return;
  }

  public void visit( NilExp exp, int level ){
    return;
  }

  public void visit( OpExp exp, int level ){
    return;
  }

  public void visit( ReturnExp exp, int level ){
    return;
  }

  public void visit( SimpleDec exp, int level ){
    return;
  }

  public void visit( SimpleVar exp, int level ){
    return;
  }

  public void visit( Var exp, int level ){
    return;
  }

  public void visit( VarDec exp, int level ){
    return;
  }

  public void visit( VarDecList exp, int level ){
    return;
  }

  public void visit( VarExp exp, int level ){
    return;
  }

  public void visit( WhileExp exp, int level ){
    return;
  }

}
