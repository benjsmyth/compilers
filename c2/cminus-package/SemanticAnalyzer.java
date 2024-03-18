import absyn.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.io.PrintStream;

public class SemanticAnalyzer implements AbsynVisitor {
  public HashMap<String, ArrayList<NodeType>> table;
  public PrintStream old;
  public PrintStream tableStream;

  public SemanticAnalyzer(PrintStream old, PrintStream current) {
    this.table = new HashMap<String, ArrayList<NodeType>>();
    this.old = old;
    this.tableStream = current;
  }

  public void insert(String key, NodeType node) {
    ArrayList<NodeType> list = table.get(key);
    if (list == null) {
      list = new ArrayList<NodeType>();
      table.put(key, list);
    }
    list.add(node);
  }

  public ArrayList<NodeType> lookup(String key) {
    return table.get(key);
  }

  public void delete(String key) {
    table.remove(key);
  }

  public void deleteLevelEntries(int level) { // for deleting a level from the hashmap
    Iterator<HashMap.Entry<String, ArrayList<NodeType>>> iterator;
    iterator = table.entrySet().iterator();
    while (iterator.hasNext()) {
      Map.Entry<String, ArrayList<NodeType>> entry = iterator.next();
      ArrayList<NodeType> nodeList = entry.getValue();
      for (Iterator<NodeType> nodeIter = nodeList.iterator(); nodeIter.hasNext();) {
        NodeType node = nodeIter.next();
        if (node.level == level) {
          nodeIter.remove();
        }
      }
      if (nodeList.isEmpty()) {
        iterator.remove();
      }
    }
  }

  public int sameLevelTypes(int level) {
    Iterator<HashMap.Entry<String, ArrayList<NodeType>>> iterator;
    iterator = table.entrySet().iterator();
    boolean same = true;
    int typ = -1;
    while (iterator.hasNext()) {
      Map.Entry<String, ArrayList<NodeType>> entry = iterator.next();
      ArrayList<NodeType> nodeList = entry.getValue();
      for (Iterator<NodeType> nodeIter = nodeList.iterator(); nodeIter.hasNext();) {
        NodeType node = nodeIter.next();
        if (node.level == level) {
          if (typ == -1 || typ == node.typ.type) {
            typ = node.typ.type;
          } else {
            same = false;
          }
        }
      }
      if (nodeList.isEmpty()) {
        iterator.remove();
      }
    }
    if (!same)
      return -1;
    return typ;
  }

  public boolean checkReturnEntry(int level, int type) {
    Iterator<HashMap.Entry<String, ArrayList<NodeType>>> iterator;
    iterator = table.entrySet().iterator();
    while (iterator.hasNext()) {
      Map.Entry<String, ArrayList<NodeType>> entry = iterator.next();
      ArrayList<NodeType> nodeList = entry.getValue();
      for (Iterator<NodeType> nodeIter = nodeList.iterator(); nodeIter.hasNext();) {
        NodeType node = nodeIter.next();
        if (node.level == level) {
          if (node.size == Constants.RETURN) {
            if (node.typ.type == type) {
              String looking = "";
              switch (type) {
                case 0:
                  looking = "bool";
                  break;

                case 1:
                  looking = "int";
                  break;

                case 2:
                  looking = "void";
                  break;

                default:
                  break;
              }
              String got = "";
              switch (node.typ.type) {
                case 0:
                  got = "bool";
                  break;

                case 1:
                  got = "int";
                  break;

                case 2:
                  got = "void";
                  break;

                default:
                  break;
              }
              printError(
                  String.format("Error in line %d, column %d: Invalid return value; Looking for '%s', got '%s'.",
                      node.typ.row + 1, node.typ.col, looking, got));        
            }
            return true;
          }
        }
      }
      if (nodeList.isEmpty()) {
        iterator.remove();
      }
    }

    return false;
  }

  public void printHashTable() {
    System.out.println("\nHash Table:");
    Iterator<HashMap.Entry<String, ArrayList<NodeType>>> iterator;
    iterator = table.entrySet().iterator();
    while (iterator.hasNext()) {
      HashMap.Entry<String, ArrayList<NodeType>> entry = iterator.next();
      System.out.println(entry.getKey() + ": " + entry.getValue().toString());
    }
  }

  private void indent(int level) {
    for (int i = 0; i < level * 4; i++)
      System.out.print(" ");
  }

  public void visit(ArrayDec arrayDec, int level) {
    boolean error = false;
    if (lookup(arrayDec.name) != null) {
      ArrayList<NodeType> node = lookup(arrayDec.name);
      if (node != null) {
        for (NodeType n : node) {
          if (n.level == level) {
            error = true;
            printError(
                String.format("Error in line %d, column %d at `%s': Redeclaration",
                    arrayDec.row, arrayDec.col, arrayDec.name));
          }
        }
      }
    }
    if (error == false) {
      switch (arrayDec.typ.type) {
        case 0:
          indent(level);
          System.out.println(arrayDec.name + "[" + arrayDec.size + "]" + ": bool");
          break;
        case 1:
          indent(level);
          System.out.println(arrayDec.name + "[" + arrayDec.size + "]" + ": int");
          break;
        case 2:
          indent(level);
          System.out.println(arrayDec.name + "[" + arrayDec.size + "]" + ": void");
          break;
        default:
          printError("Error: No type");
          break;
      }
      NodeType newNode = new NodeType(arrayDec.name, arrayDec.size, arrayDec.typ, level);
      insert(arrayDec.name, newNode);
    }
  }

  public void visit(AssignExp assignExp, int level) {
    indent(level);
    System.out.println("Assignment:");
    level++;
    if (assignExp.lhs != null)
      assignExp.lhs.accept(this, level);
    if (assignExp.rhs != null)
      assignExp.rhs.accept(this, level);
    if (sameLevelTypes(level) == -1) {
      printError(
          String.format("Error in line %d, column %d: Invalid assignment",
              assignExp.row + 1, assignExp.col));
    }
    deleteLevelEntries(level);

  }

  public void visit(BoolExp boolExp, int level) {
    indent(level);
    NodeType newNode = new NodeType(Boolean.toString(boolExp.value), 0,
        new NameTy(boolExp.row, boolExp.col, NameTy.BOOL), level);
    insert(newNode.name, newNode);

    System.out.println(newNode.name + ": " + "bool");
  }

  public void visit(CallExp callExp, int level) {
    if (lookup(callExp.func) != null) {
      ExpList args = callExp.args;
      while (args != null) {
        // Idea: Match arg type with function.params in table
        args = args.tail;
      }
    } else {
      System.err.println(String.format("Error in line %d, column %d at `%s': Undefined function call",
          callExp.row, callExp.col, callExp.func));
    }
  }

  public void visit(CompoundExp compoundExp, int level) {
    if (compoundExp.decs != null)
      compoundExp.decs.accept(this, level);
    if (compoundExp.exps != null)
      compoundExp.exps.accept(this, level);
  }

  public void visit(DecList decList, int level) {
    boolean hasMain = false;
    System.out.println("Entering global scope:");
    level++;
    while (decList != null) {
      if (decList.head != null)
        decList.head.accept(this, level);
      if (decList.head instanceof FunctionDec) {
        FunctionDec functionDec = (FunctionDec) decList.head;
        if (functionDec.func.equals("main")) {
          hasMain = true;
          if (decList.tail != null)
            System.err.println(String.format("Error in line %d, column %d: Function `main' must be last definition",
                decList.head.row, decList.head.col));
        }
      }
      decList = decList.tail;
    }
    if (!hasMain)
      System.err.println("Error at EOF: Function `main' must be defined");
    System.out.println("Exiting global scope");
  }

  public void visit(ExpList expList, int level) {
    while (expList != null) {
      if (expList.head != null)
        expList.head.accept(this, level);
      expList = expList.tail;
    }
  }

  public void visit(FunctionDec functionDec, int level) {
    boolean error = false;
    String type = null;
    if (lookup(functionDec.func) != null) {
      ArrayList<NodeType> node = lookup(functionDec.func);
      if (node != null) {
        for (NodeType n : node) {
          if (n.level == level) {
            error = true;
            printError(
                String.format("Error in line %d, column %d at `%s': Redeclaration",
                    functionDec.row, functionDec.col, functionDec.func));
          }
        }
      }
    }
    if (error == false) {
      indent(level);
      System.out.println(
          String.format("Entering function scope `%s':", functionDec.func));
      switch (functionDec.result.type) {
        case 0:
          type = "bool";
          break;
        case 1:
          type = "int";
          break;
        case 2:
          type = "void";
          break;
        default:
          System.out.println("Error: No type");
          break;
      }
      NodeType newNode = new NodeType(functionDec.func, 0, functionDec.result, level);
      insert(functionDec.func, newNode);
      level++;
      if (functionDec.params != null)
        functionDec.params.accept(this, level);
      if (functionDec.body != null)
        functionDec.body.accept(this, level);

      if (!checkReturnEntry(level, functionDec.result.type)) {
        printError(
            String.format("Error in line %d, column %d: No return value",
                functionDec.row + 1, functionDec.col));
      }

      deleteLevelEntries(level);
      level--;
      indent(level);
      System.out.print(String.format("%s: (", functionDec.func));
      VarDecList params = functionDec.params;
      while (params != null) {
        SimpleDec param = (SimpleDec) params.head;
        System.out.print(param.name + ": ");
        if (param.typ.type == 0) // Parameter is BOOL
          System.out.print("bool");
        else if (param.typ.type == 1) // Parameter is INT
          System.out.print("int");
        else if (param.typ.type == 2) // Parameter is VOID
          System.out.print("void");
        if (params.tail != null)
          System.out.print(", ");
        params = params.tail;
      }
      System.out.println(") -> " + type);
    }
  }

  public void visit(IfExp ifExp, int level) {
    System.out.println("Entering conditional block scope:");
    int prevLevel = level;
    level++;
    if (ifExp.test != null) {
      if (ifExp.test instanceof BoolExp || ifExp.test instanceof IntExp) {
        ifExp.test.accept(this, level);
        if (ifExp.thenpart != null)
          ifExp.thenpart.accept(this, level);
        if (ifExp.elsepart != null)
          ifExp.elsepart.accept(this, level);
      } else {
        printError(
            String.format("Error in line %d, column %d at `if': Expression is not boolean",
                ifExp.row, ifExp.col));
      }
    }
    deleteLevelEntries(level);
    indent(prevLevel);
    System.out.println("Exiting conditional block scope");
  }

  public void visit(IndexVar indexVar, int level) {
    ArrayList<NodeType> node;
    if ((node = lookup(indexVar.name)) == null || node.get(0).size == 0) {
      printError(
          String.format("Error in line %d, column %d at `%s': Undefined array",
              indexVar.row + 1, indexVar.col, indexVar.name));
    } else {

      indent(level);

      System.out.println("index variable: " + indexVar.name);
      level++;

      if (indexVar.index != null) {
        indexVar.index.accept(this, level);
      }

      int typ;

      if ((typ = sameLevelTypes(level)) == -1) {
        printError(
            String.format("Error in line %d, column %d: Invalid type operation",
                indexVar.row + 1, indexVar.col));
      } else if (typ != NameTy.INT) {
        printError(
            String.format("Error in line %d, column %d: Invalid array access",
                indexVar.row + 1, indexVar.col));
      }

      deleteLevelEntries(level);

      level--;

      NodeType newNode = new NodeType(indexVar.name, 0, new NameTy(indexVar.row, indexVar.col, node.get(0).typ.type),
          level);
      insert(newNode.name, newNode);
    }
  }

  public void visit(IntExp intExp, int level) {
    indent(level);

    NodeType newNode = new NodeType(Integer.toString(intExp.value), 0,
        new NameTy(intExp.row, intExp.col, NameTy.INT), level);
    insert(newNode.name, newNode);

    System.out.println(newNode.name + ": " + "int");
  }

  public void visit(NameTy nameTy, int level) {
    // Not needed
  }

  public void visit(NilExp nilExp, int level) {
    // Not needed
  }

  public void visit(OpExp opExp, int level) {

    indent(level);
    System.out.print("operation: ");
    switch (opExp.op) {

      case 0:
        System.out.print("+");
        break;

      case 1:
        System.out.print("-");
        break;

      case 2:
        System.out.print("uminus");
        break;

      case 3:
        System.out.print("*");
        break;

      case 4:
        System.out.print("/");
        break;

      case 5:
        System.out.print("=");
        break;

      case 6:
        System.out.print("!=");
        break;

      case 7:
        System.out.print("<");
        break;

      case 8:
        System.out.print("<=");
        break;

      case 9:
        System.out.print(">");
        break;

      case 10:
        System.out.print(">=");
        break;

      case 11:
        System.out.print("~");
        break;

      case 12:
        System.out.print("&&");
        break;

      case 13:
        System.out.print("||");
        break;

      default:
        System.out.print("Invalid Operation");

    }

    System.out.println();

    level++;
    if (opExp.left != null) {
      opExp.left.accept(this, level);
    }

    if (opExp.right != null) {
      opExp.right.accept(this, level);
    }

    int typ;

    if ((typ = sameLevelTypes(level)) == -1) {
      printError(
          String.format("Error in line %d, column %d: Invalid type operation",
              opExp.row + 1, opExp.col));
    } else if (typ == NameTy.BOOL && opExp.op < 5) {
      printError(
          String.format("Error in line %d, column %d: Invalid operand",
              opExp.row + 1, opExp.col));
    }

    deleteLevelEntries(level);

    level--;

    NodeType newNode = new NodeType(Integer.toString(opExp.op), 0, new NameTy(opExp.row, opExp.col, typ), level);
    insert(newNode.name, newNode);

  }

  public void visit(ReturnExp returnExp, int level) {

    int typ = -1;

    indent(level);
    level++;

    System.out.println(
        "Return:");

    if (returnExp.exp != null)
      returnExp.exp.accept(this, level);

    typ = sameLevelTypes(level);

    level--;
    NodeType newNode = new NodeType("return", Constants.RETURN, new NameTy(returnExp.row, returnExp.col, typ), level);
    insert(newNode.name, newNode);

    /*
     * this.table.forEach((key, list) -> { // Iterate table
     * for (NodeType node : list) { // Iterate list
     * if (node.level == level-1 && node.isFunc) { // Most recent prototype
     * System.out.print("return: ");
     * if (returnExp.exp instanceof BoolExp) { // return is BOOL
     * System.out.println("bool");
     * if (node.typ.type != 0) // Function was not BOOL
     * printError(String.
     * format("Error in line %d, column %d at `return': Incompatible return type",
     * returnExp.row + 1, returnExp.col));
     * }
     * else if (returnExp.exp instanceof IntExp) { // return is INT
     * System.out.println("int");
     * if (node.typ.type != 1) // Function was not INT
     * printError(String.
     * format("Error in line %d, column %d at `return': Incompatible return type",
     * returnExp.row + 1, returnExp.col));
     * }
     * else if (returnExp.exp instanceof NilExp) { // return is VOID
     * System.out.println("void");
     * if (node.typ.type != 2) // Function was not VOID
     * printError(String.
     * format("Error in line %d, column %d at `return': Incompatible return type",
     * returnExp.row + 1, returnExp.col));
     * }
     * }
     * }
     * });
     */
  }

  public void visit(SimpleDec simpleDec, int level) {
    if (simpleDec.typ != null)
      simpleDec.typ.accept(this, level);
    boolean error = false;
    if (lookup(simpleDec.name) != null) {
      ArrayList<NodeType> node = lookup(simpleDec.name);
      if (node != null) {
        for (NodeType n : node) {
          if (n.level == level) {
            error = true;
            printError(
                String.format("Error in line %d, column %d at `%s': Redefined variable",
                    simpleDec.row, simpleDec.col, simpleDec.name));
          }
        }
      }
    }
    if (error == false) {
      indent(level);
      switch (simpleDec.typ.type) {
        case 0:
          System.out.println(simpleDec.name + ": bool");
          break;
        case 1:
          System.out.println(simpleDec.name + ": int");
          break;
        case 2:
          System.out.println(simpleDec.name + ": void");
          break;
        default:
          printError("Error: No type");
          break;
      }
      NodeType newNode = new NodeType(simpleDec.name, 0, simpleDec.typ, level);
      insert(simpleDec.name, newNode);
    }
  }

  public void visit(SimpleVar simpleVar, int level) {
    ArrayList<NodeType> node;
    if ((node = lookup(simpleVar.name)) == null) {
      printError(
          String.format("Error in line %d, column %d at `%s': Undefined variable error",
              simpleVar.row + 1, simpleVar.col, simpleVar.name));
    } else {
      NodeType newNode = new NodeType(simpleVar.name, 0, node.get(0).typ, level);
      insert(simpleVar.name, newNode);
      indent(level);
      switch (newNode.typ.type) {
        case 0:
          System.out.println(simpleVar.name + ": bool");
          break;
        case 1:
          System.out.println(simpleVar.name + ": int");
          break;
        case 2:
          System.out.println(simpleVar.name + ": void");
          break;
        default:
          break;
      }
    }
  }

  public void visit(VarDecList varDecList, int level) {
    while (varDecList != null) {
      if (varDecList.head != null)
        varDecList.head.accept(this, level);
      varDecList = varDecList.tail;
    }
  }

  public void visit(VarExp varExp, int level) {
    // Not needed
    if (varExp.variable != null)
      varExp.variable.accept(this, level);
  }

  public void visit(WhileExp whileExp, int level) {
    indent(level);
    System.out.println("Entering conditional block scope:");
    int prevLevel = level;
    level++;
    if (whileExp.test != null) {
      if (whileExp.test instanceof BoolExp || whileExp.test instanceof IntExp) {
        whileExp.test.accept(this, level);
        if (whileExp.body != null)
          whileExp.body.accept(this, level);
      } else {
        System.err.println(String.format("Error in line %d, column %d at `while': Conditional is not boolean",
            whileExp.row, whileExp.col));
      }
    }
    deleteLevelEntries(level);
    indent(prevLevel);
    System.out.println("Exiting block scope");
  }

  public void printError(String err) {
    System.setOut(this.old);
    System.err.println(err);
    System.setOut(this.tableStream);
  }

  /*
   * public boolean isInteger(Dec dtype) {
   * if (dtype.typ == 1) {
   * return true;
   * } else {
   * return false;
   * }
   * }
   */
}
