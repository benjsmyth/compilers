package absyn;

abstract public class Absyn {
  public int row, col;
  public boolean flag;

  abstract public void accept( AbsynVisitor visitor, int level, boolean flag );
}
