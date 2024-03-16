package absyn;

public class NodeType {
    public String name;
    public int size;
    public int level;
    public NameTy typ;

    public NodeType(String name, int size, NameTy typ, int level) {
        this.name = name;
        this.size = size;
        this.typ = typ;
        this.level = level;
    }

    public String toString() {
        return "NodeType{" +
                "name='" + name + '\'' + ", size=" + size + ", level=" + level + '}';
    }
}