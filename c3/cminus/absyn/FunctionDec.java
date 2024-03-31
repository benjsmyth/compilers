package absyn;

public class FunctionDec extends Dec {
    
    public NameTy result;
    public String func;
    public VarDecList params;
    public Exp body;
    public int funaddr;
    

    public FunctionDec( int row, int col, NameTy result, String func, VarDecList params, Exp body){
    
        this.row = row;
        this.col = col;
        this.func = func;
        this.result = result;
        this.params = params;
        this.body = body;
    }


    public FunctionDec( int row, int col, NameTy result, String func, VarDecList params, Exp body, int funaddr){
    
      this.row = row;
      this.col = col;
      this.func = func;
      this.result = result;
      this.params = params;
      this.body = body;
      this.funaddr = funaddr;
  }

  public void accept( AbsynVisitor visitor, int level, boolean flag ) {
    visitor.visit( this, level, flag );
  }
}
