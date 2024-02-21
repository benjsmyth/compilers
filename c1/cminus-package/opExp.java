package absyn;

public class OpExp extends Exp {
  public final static int PLUS      = 0;
  public final static int MINUS     = 1;
  public final static int MULT      = 2;
  public final static int DIV       = 3;
  public final static int EQUAL     = 4;
  public final static int NEQUAL    = 5;
  public final static int LESS      = 6;
  public final static int LESSEQ    = 7;
   public final static int GREATER  = 6;
  public final static int GREATEREQ = 7;

  public Exp left;
  public int op;
  public Exp right;

  public OpExp( int row, int col, Exp left, int op, Exp right ) {
    this.row = row;
    this.col = col;
    this.left = left;
    this.op = op;
    this.right = right;
  }

  public void accept( AbsynVisitor visitor, int level ) {
    visitor.visit( this, level );
  }
}
