package absyn;

public class OpExp extends Exp {
    
  public final static int PLUS      = 0;
  public final static int MINUS     = 1;
  public final static int UMINUS    = 2;
  public final static int MULT      = 3;
  public final static int DIV       = 4;
  public final static int EQUAL     = 5;
  public final static int NEQUAL    = 6;
  public final static int LESS      = 7;
  public final static int LESSEQ    = 8;
  public final static int GREATER   = 9;
  public final static int GREATEREQ = 10;
  public final static int NOT       = 11;
  public final static int AND       = 12;
  public final static int OR        = 13;

  public Exp left;
  public int op;
  public Exp right;
    

    public OpExp( int row, int col, Exp left, int op, Exp right){
    
        this.row = row;
        this.col = col;
        this.left = left;
        this.right = right;
        this.op = op;
    }

  public void accept( AbsynVisitor visitor, int level ) {
    visitor.visit( this, level );
  }
}
