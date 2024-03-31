package absyn;

abstract public class Absyn {
  public int row, col;
  public boolean isAddress;

  abstract public void accept(AbsynVisitor visitor, int value, boolean isAddress);
}
