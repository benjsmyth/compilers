package absyn;

public class NodeType {
    public String name;
    public Dec def;
    public int level;

    public NodeType(String name, Dec def, int level) {
        this.name = name;
        this.def = def;
        this.level = level;
    }

    public String toString() {
        return "NodeType{" +
                "name='" + name + '\'' + ", def=" + def + ", level=" + level + '}';
    }
}