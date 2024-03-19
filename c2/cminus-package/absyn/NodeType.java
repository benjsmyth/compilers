package absyn;

public class NodeType {
    public String name;
    public Dec def;
    public int id;
    public int level;
    public NameTy typ;
    public boolean canReturn;

    public NodeType(String name, int id, NameTy typ, Dec def, int level) {
        this.name = name;
        this.def = def;
        this.id = id;
        this.typ = typ;
        this.level = level;
        this.canReturn = false;
    }
    
    public String toString() {
        return "NodeType{" +
                "name='" + name + '\'' + ", id=" + id + ", level=" + level + '}';
    }
}
