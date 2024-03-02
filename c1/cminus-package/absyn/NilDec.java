package absyn;

public class NilDec extends VarDec {
    

    public NilDec( int row, int col){
    
        this.row = row;
        this.col = col;
    }

  public void accept( AbsynVisitor visitor, int level ) {
    visitor.visit( this, level );
  }
}
