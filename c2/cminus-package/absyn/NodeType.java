package absyn;

public class NodeType {
    public String name;
    public Dec def;
    public int level;
    public NameTy typ;

    public NodeType(String name, Dec def, NameTy typ, int level) {
        this.name = name;
        this.def = def;
        this.typ = typ;
        this.level = level;
    }

    public String toString() {
        return "NodeType{" +
                "name='" + name + '\'' + ", def=" + def + ", level=" + level + '}';
    }
}