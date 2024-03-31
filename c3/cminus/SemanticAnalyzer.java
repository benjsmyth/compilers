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
  public static boolean valid;

  public SemanticAnalyzer(PrintStream old, PrintStream current) {
    this.table = new HashMap<String, ArrayList<NodeType>>();
    this.old = old;
    this.tableStream = current;
    this.valid = true;

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

  public boolean checkCanReturn(int level) {
    Iterator<HashMap.Entry<String, ArrayList<NodeType>>> iterator;
    iterator = table.entrySet().iterator();
    while (iterator.hasNext()) {
      Map.Entry<String, ArrayList<NodeType>> entry = iterator.next();
      ArrayList<NodeType> nodeList = entry.getValue();
      for (Iterator<NodeType> nodeIter = nodeList.iterator(); nodeIter.hasNext();) {
        NodeType node = nodeIter.next();
        if (node.level == level) {
          if (node.canReturn) {
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

  public boolean checkCanReturnFinal(int level) {
    Iterator<HashMap.Entry<String, ArrayList<NodeType>>> iterator;
    iterator = table.entrySet().iterator();
    boolean check = false;
    while (iterator.hasNext()) {
      Map.Entry<String, ArrayList<NodeType>> entry = iterator.next();
      ArrayList<NodeType> nodeList = entry.getValue();
      for (Iterator<NodeType> nodeIter = nodeList.iterator(); nodeIter.hasNext();) {
        NodeType node = nodeIter.next();
        if (node.level == level && node.id == Constants.COMPOUND) {
          if (!node.canReturn) {
            return false;
          } else {
            check = true;
          }
        }
      }
      if (nodeList.isEmpty()) {
        iterator.remove();
      }
    }
    return check;
  }

  public ArrayList<String> sameLevelTypes(int level) {
    Iterator<HashMap.Entry<String, ArrayList<NodeType>>> iterator;
    iterator = table.entrySet().iterator();
    boolean same = true;
    String hold = "";
    int store = -1;
    ArrayList<String> typ = new ArrayList<String>();
    while (iterator.hasNext()) {
      Map.Entry<String, ArrayList<NodeType>> entry = iterator.next();
      ArrayList<NodeType> nodeList = entry.getValue();
      for (Iterator<NodeType> nodeIter = nodeList.iterator(); nodeIter.hasNext();) {
        NodeType node = nodeIter.next();
        if (node.level == level) {
          switch (node.typ.type) {
            case NameTy.BOOL:
              hold = "bool";
              break;

            case NameTy.INT:
              hold = "int";
              break;

            case NameTy.VOID:
              hold = "void";
              break;

            default:
              break;
          }
          if (!(typ.size() == 0 || typ.get(0).equals(hold))) {
            same = false;
          }
          typ.add(hold);
          store = node.typ.type;
        }
      }
      if (nodeList.isEmpty()) {
        iterator.remove();
      }
    }
    if (!same) {
      typ.add("-1");
    } else {
      typ.add(Integer.toString(store));
    }
    return typ;
  }

  public int indexInBounds(int level) {
    Iterator<HashMap.Entry<String, ArrayList<NodeType>>> iterator;
    iterator = table.entrySet().iterator();
    while (iterator.hasNext()) {
      Map.Entry<String, ArrayList<NodeType>> entry = iterator.next();
      ArrayList<NodeType> nodeList = entry.getValue();
      for (Iterator<NodeType> nodeIter = nodeList.iterator(); nodeIter.hasNext();) {
        NodeType node = nodeIter.next();
        if (node.level == level) {
          if (node.typ.type == NameTy.INT)
            return node.typ.type;
        }
      }
      if (nodeList.isEmpty()) {
        iterator.remove();
      }
    }
    return -1;
  }

  public boolean checkConditionType(int level) {
    Iterator<HashMap.Entry<String, ArrayList<NodeType>>> iterator;
    iterator = table.entrySet().iterator();
    while (iterator.hasNext()) {
      Map.Entry<String, ArrayList<NodeType>> entry = iterator.next();
      ArrayList<NodeType> nodeList = entry.getValue();
      for (Iterator<NodeType> nodeIter = nodeList.iterator(); nodeIter.hasNext();) {
        NodeType node = nodeIter.next();
        if (node.level == level) {
          if (node.typ.type == NameTy.VOID) {
            return false;
          }
        }
      }
      if (nodeList.isEmpty()) {
        iterator.remove();
      }
    }
    return true;
  }

  public String checkCallParam(int level, int type) {
    Iterator<HashMap.Entry<String, ArrayList<NodeType>>> iterator;
    iterator = table.entrySet().iterator();
    while (iterator.hasNext()) {
      Map.Entry<String, ArrayList<NodeType>> entry = iterator.next();
      ArrayList<NodeType> nodeList = entry.getValue();
      for (Iterator<NodeType> nodeIter = nodeList.iterator(); nodeIter.hasNext();) {
        NodeType node = nodeIter.next();
        if (node.level == level) {
          if (node.typ.type == type)
            return "correct";
          else {
            return node.name;
          }
        }
      }
      if (nodeList.isEmpty()) {
        iterator.remove();
      }
    }

    return "err";
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

  public void visit(ArrayDec arrayDec, int level, boolean flag) {
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
      NodeType newNode = new NodeType(arrayDec.name, arrayDec.size, arrayDec.typ, arrayDec, level);
      if (newNode.id == 0)
        newNode.id = 1;
      insert(arrayDec.name, newNode);
    }
  }

  public void visit(AssignExp assignExp, int level, boolean flag) {
    indent(level);
    System.out.println("Assignment:");
    level++;
    if (assignExp.lhs != null)
      assignExp.lhs.accept(this, level, flag);
    if (assignExp.rhs != null)
      assignExp.rhs.accept(this, level, flag);

    ArrayList<String> typ = sameLevelTypes(level);
    if (typ.size() == 3 && typ.get(2) == "-1") {
      printError(
          String.format("Error in line %d, column %d: Invalid assignment to '%s' from '%s'",
              assignExp.row + 1, assignExp.col, typ.get(0), typ.get(1)));
    }
    deleteLevelEntries(level);

  }

  public void visit(BoolExp boolExp, int level, boolean flag) {
    indent(level);
    NodeType newNode = new NodeType(Boolean.toString(boolExp.value), 0,
        new NameTy(boolExp.row, boolExp.col, NameTy.BOOL), null, level);
    insert(newNode.name, newNode);

    System.out.println(newNode.name + ": " + "bool");
  }

  public void visit(CallExp callExp, int level, boolean flag) {
    ArrayList<NodeType> func;
    if ((func = lookup(callExp.func)) != null && func.get(0).def instanceof FunctionDec) {
      NodeType node = func.get(0);
      VarDecList params = ((FunctionDec) node.def).params;

      indent(level);
      System.out.println(
          String.format("Call to function '%s':", node.name));

      ExpList args = callExp.args;
      String errStr = "";
      level++;
      while (args != null) {

        if (args.head != null) {

          if (params == null) {
            printError(
                String.format("Error in line %d, column %d: Invalid call to function '%s'",
                    callExp.row + 1, callExp.col, node.name));
            deleteLevelEntries(level);
            break;
          }

          if (params.head instanceof SimpleDec) {

            args.head.accept(this, level, flag);
            if (!(errStr = checkCallParam(level, ((SimpleDec) params.head).typ.type)).equals("correct")) {
              printError(
                  String.format("Error in line %d, column %d at '%s': Invalid call to function '%s'",
                      callExp.row + 1, callExp.col, errStr, node.name));
              deleteLevelEntries(level);
              break;
            }

            deleteLevelEntries(level);
          } else {

            args.head.accept(this, level, flag);
            if (!(errStr = checkCallParam(level, ((ArrayDec) params.head).typ.type)).equals("correct")) {
              printError(
                  String.format("Error in line %d, column %d at '%s': Invalid call to function '%s'",
                      callExp.row + 1, callExp.col, errStr, node.name));
              deleteLevelEntries(level);
              break;
            }

            deleteLevelEntries(level);
          }
        }

        params = params.tail;
        args = args.tail;
      }

      level--;
      NodeType newNode = new NodeType("$" + node.name, 0,
          new NameTy(callExp.row, callExp.col, node.typ.type), null, level);
      insert(newNode.name, newNode);

    } else {
      printError(String.format("Error in line %d, column %d at `%s': Undefined function call",
          callExp.row + 1, callExp.col, callExp.func));
    }
  }

  public void visit(CompoundExp compoundExp, int level, boolean flag) {

    boolean canReturn = false;
    if (compoundExp.decs != null)
      compoundExp.decs.accept(this, level, flag);
    if (compoundExp.exps != null)
      compoundExp.exps.accept(this, level, flag);

    canReturn = checkCanReturnFinal(level) || checkCanReturn(level);
    NodeType newNode = new NodeType("$" + "compound", Constants.COMPOUND,
        new NameTy(compoundExp.row, compoundExp.col, -1), null, level);
    newNode.canReturn = canReturn;
    insert(newNode.name, newNode);
  }

  public void visit(DecList decList, int level, boolean flag) {
    boolean hasMain = false;
    System.out.println("Entering global scope:");
    level++;
    while (decList != null) {
      if (decList.head != null)
        decList.head.accept(this, level, flag);
      if (decList.head instanceof FunctionDec) {
        FunctionDec functionDec = (FunctionDec) decList.head;
        if (functionDec.func.equals("main")) {
          hasMain = true;
          if (decList.tail != null)
            printError(String.format("Error in line %d, column %d: Function `main' must be last definition",
                decList.head.row, decList.head.col));
        }
      }
      decList = decList.tail;
    }
    if (!hasMain)
      printError("Error at EOF: Function `main' must be defined");
    System.out.println("Exiting global scope");
  }

  public void visit(ExpList expList, int level, boolean flag) {
    while (expList != null) {
      if (expList.head != null)
        expList.head.accept(this, level, flag);
      expList = expList.tail;
    }
  }

  public void visit(FunctionDec functionDec, int level, boolean flag) {
    boolean error = false;
    boolean canReturn = false;
    String type = null;
    if (lookup(functionDec.func) != null) {
      ArrayList<NodeType> node = lookup(functionDec.func);
      if (node != null) {
        for (NodeType n : node) {
          if (n.level == level) {
            if (n.def instanceof FunctionDec) {
              FunctionDec proto = (FunctionDec) n.def;
              if (proto.body instanceof NilExp) {

                if (functionDec.result.type == proto.result.type) {

                  VarDecList func = functionDec.params;
                  VarDecList prot = proto.params;

                  SimpleDec funcVal = null;
                  ArrayDec funcArr = null;
                  SimpleDec protVal = null;
                  ArrayDec protArr = null;

                  while (func != null) {
                    if (func.head != null) {

                      if (prot == null) {
                        printError(
                            String.format(
                                "Error in line %d, column %d at `%s': Function parameters do not match prototype",
                                functionDec.row + 1, functionDec.col, functionDec.func));
                        break;
                      }

                      if (func.head instanceof SimpleDec && prot.head instanceof SimpleDec) {
                        funcVal = (SimpleDec) func.head;
                        protVal = (SimpleDec) prot.head;

                        if (!(funcVal.name.equals(protVal.name) && funcVal.typ.type == protVal.typ.type)) {
                          printError(
                              String.format(
                                  "Error in line %d, column %d at `%s': Function parameters do not match prototype",
                                  functionDec.row + 1, functionDec.col, functionDec.func));
                        }
                      } else if (func.head instanceof SimpleDec && prot.head instanceof ArrayDec) {
                        funcVal = (SimpleDec) func.head;
                        protArr = (ArrayDec) prot.head;

                        if (!(funcVal.name.equals(protArr.name) && funcVal.typ.type == protArr.typ.type)) {
                          printError(
                              String.format(
                                  "Error in line %d, column %d at `%s': Function parameters do not match prototype",
                                  functionDec.row + 1, functionDec.col, functionDec.func));
                        }
                      } else if (func.head instanceof ArrayDec && prot.head instanceof ArrayDec) {
                        funcArr = (ArrayDec) func.head;
                        protArr = (ArrayDec) prot.head;

                        if (!(funcArr.name.equals(protArr.name) && funcArr.typ.type == protArr.typ.type)) {
                          printError(
                              String.format(
                                  "Error in line %d, column %d at `%s': Function parameters do not match prototype",
                                  functionDec.row + 1, functionDec.col, functionDec.func));
                        }
                      } else if (func.head instanceof ArrayDec && prot.head instanceof SimpleDec) {
                        funcArr = (ArrayDec) func.head;
                        protVal = (SimpleDec) prot.head;

                        if (!(funcArr.name.equals(protVal.name) && funcArr.typ.type == protVal.typ.type)) {
                          printError(
                              String.format(
                                  "Error in line %d, column %d at `%s': Function parameters do not match prototype",
                                  functionDec.row + 1, functionDec.col, functionDec.func));
                        }
                      }

                    } else {
                      printError(
                          String.format(
                              "Error in line %d, column %d at `%s': Function parameters do not match prototype",
                              functionDec.row + 1, functionDec.col, functionDec.func));
                    }
                    func = func.tail;
                    prot = prot.tail;
                  }

                } else {
                  error = true;
                  printError(
                      String.format(
                          "Error in line %d, column %d at `%s': Return value does not match function prototype",
                          functionDec.row + 1, functionDec.col, functionDec.func));
                }

              } else {
                error = true;
                printError(
                    String.format("Error in line %d, column %d at `%s': Redeclaration",
                        functionDec.row + 1, functionDec.col, functionDec.func));
              }
            } else {
              error = true;
              printError(
                  String.format("Error in line %d, column %d at `%s': Redeclaration",
                      functionDec.row + 1, functionDec.col, functionDec.func));
            }
          }
        }
      }
    }
    if (error == false) {
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
      NodeType newNode = new NodeType(functionDec.func, 0, functionDec.result, functionDec, level);
      insert(functionDec.func, newNode);
      if (!(functionDec.body instanceof NilExp)) {
        indent(level);
        System.out.println(
            String.format("Entering function scope `%s':", functionDec.func));
        level++;

        NodeType returnNode = new NodeType("$returnval", 0, functionDec.result, functionDec, level);
        insert(returnNode.name, returnNode);

        if (functionDec.params != null)
          functionDec.params.accept(this, level, flag);

        if (functionDec.body != null)
          functionDec.body.accept(this, level, flag);

        canReturn = checkCanReturnFinal(level);

        if (!canReturn) {
          if (functionDec.result.type != NameTy.VOID) {
            printError(
                String.format("Error in line %d, column %d: No return value",
                    functionDec.row + 1, functionDec.col));
          }
        }

        deleteLevelEntries(level);
        level--;

        indent(level);
        System.out.println(
            String.format("Exiting function scope `%s':", functionDec.func));
        indent(level);
      } else {
        indent(level);
        System.out.print(
            String.format("Function Prototype - "));
      }
      System.out.print(String.format("%s: (", functionDec.func));
      VarDecList params = functionDec.params;
      SimpleDec paramSimp;
      ArrayDec paramArr;
      while (params != null) {
        if (params.head instanceof SimpleDec) {
          paramSimp = (SimpleDec) params.head;
          System.out.print(paramSimp.name + ": ");
          if (paramSimp.typ.type == 0) // Parameter is BOOL
            System.out.print("bool");
          else if (paramSimp.typ.type == 1) // Parameter is INT
            System.out.print("int");
          else if (paramSimp.typ.type == 2) // Parameter is VOID
            System.out.print("void");
          if (params.tail != null)
            System.out.print(", ");
        } else {
          paramArr = (ArrayDec) params.head;
          System.out.print(paramArr.name + ": ");
          if (paramArr.typ.type == 0) // Parameter is BOOL
            System.out.print("bool");
          else if (paramArr.typ.type == 1) // Parameter is INT
            System.out.print("int");
          else if (paramArr.typ.type == 2) // Parameter is VOID
            System.out.print("void");
          if (params.tail != null)
            System.out.print(", ");
        }
        params = params.tail;

      }
      System.out.println(") -> " + type);
    }

  }

  public void visit(IfExp ifExp, int level, boolean flag) {
    indent(level);
    boolean canReturnThen = false;
    boolean canReturnElse = false;
    System.out.println("Entering IF conditional block scope:");
    if (ifExp.test != null) {
      level++;
      ifExp.test.accept(this, level, flag);
      if (!checkConditionType(level)) {
        printError(
            String.format("Error in line %d, column %d: Invalid condition type; Must be 'int' or 'bool' but got 'void'",
                ifExp.row + 1, ifExp.col));
      }
      level++;
      if (ifExp.thenpart != null) {
        ifExp.thenpart.accept(this, level, flag);
      }
      canReturnThen = checkCanReturn(level);
      indent(level - 2);
      System.out.println("Exiting IF conditional block scope");
      deleteLevelEntries(level);
      if (ifExp.elsepart != null) {
        indent(level - 2);
        System.out.println("Entering ELSE conditional block scope");
        ifExp.elsepart.accept(this, level, flag);
        indent(level - 2);
        System.out.println("Exiting ELSE conditional block scope");
      }

      canReturnElse = checkCanReturn(level);
      deleteLevelEntries(level);
      level--;
      deleteLevelEntries(level);
      level--;
    }

    NodeType newNode = new NodeType("if", Constants.COMPOUND, new NameTy(ifExp.row, ifExp.col, -1),
        null,
        level);
    newNode.canReturn = canReturnThen && canReturnElse;
    insert(newNode.name, newNode);
  }

  public void visit(IndexVar indexVar, int level, boolean flag) {
    ArrayList<NodeType> node;
    if ((node = lookup(indexVar.name)) == null || node.get(0).id == 0) {
      printError(
          String.format("Error in line %d, column %d at `%s': Undefined array",
              indexVar.row + 1, indexVar.col, indexVar.name));
    } else {

      indent(level);

      System.out.println("index variable: " + indexVar.name);
      level++;

      if (indexVar.index != null) {
        indexVar.index.accept(this, level, flag);
      }

      int typ;

      if ((typ = indexInBounds(level)) == -1) {
        printError(
            String.format("Error in line %d, column %d: Invalid array access",
                indexVar.row + 1, indexVar.col));
      } else if (typ < 0) {
        printError(
            String.format("Error in line %d, column %d: Index out of bounds",
                indexVar.row + 1, indexVar.col));
      }

      deleteLevelEntries(level);

      level--;

      NodeType newNode = new NodeType(indexVar.name, 0, new NameTy(indexVar.row, indexVar.col, node.get(0).typ.type),
          null,
          level);
      insert(newNode.name, newNode);
    }
  }

  public void visit(IntExp intExp, int level, boolean flag) {
    indent(level);

    NodeType newNode = new NodeType(Integer.toString(intExp.value), 0,
        new NameTy(intExp.row, intExp.col, NameTy.INT), null, level);
    insert(newNode.name, newNode);

    System.out.println(newNode.name + ": " + "int");
  }

  public void visit(NameTy nameTy, int level, boolean flag) {
    // Not needed
  }

  public void visit(NilExp nilExp, int level, boolean flag) {
    // Not needed
  }

  public void visit(OpExp opExp, int level, boolean flag) {

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
      opExp.left.accept(this, level, flag);
    }

    if (opExp.right != null) {
      opExp.right.accept(this, level, flag);
    }

    ArrayList<String> typ = sameLevelTypes(level);
    int set = NameTy.INT;
    if (typ.size() == 3) {
      if (typ.get(2) == "-1") {
        printError(
            String.format("Error in line %d, column %d: Invalid type operation between '%s' and '%s'",
                opExp.row + 1, opExp.col, typ.get(0), typ.get(1)));
      } else if (typ.get(0).equals("bool") && opExp.op < 5) {
        printError(
            String.format("Error in line %d, column %d: Invalid operand on 'bool'",
                opExp.row + 1, opExp.col));
      } else if (opExp.op >= 5) {
        set = NameTy.BOOL;
      }
    }
    deleteLevelEntries(level);

    level--;

    NodeType newNode = new NodeType(Integer.toString(opExp.op), 0,
        new NameTy(opExp.row, opExp.col, set), null, level);
    insert(newNode.name, newNode);

  }

  public void visit(ReturnExp returnExp, int level, boolean flag) {

    ArrayList<String> typ;

    indent(level);
    level++;

    System.out.println(
        "Return:");

    if (returnExp.exp != null)
      returnExp.exp.accept(this, level, flag);

    typ = sameLevelTypes(level);
    deleteLevelEntries(level);

    ArrayList<NodeType> returnCheck;


    level--;
    if (typ.size() == 2) {
      if ((returnCheck = lookup("$returnval")) != null){
        
          if (returnCheck.get(0).typ.type != Integer.parseInt(typ.get(1))) {
            String looking = "";
            switch (returnCheck.get(0).typ.type) {
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
            switch (Integer.parseInt(typ.get(1))) {
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
                    returnExp.row + 1, returnExp.col, looking, got));

          }
        
      }
      NodeType newNode = new NodeType("return", Constants.RETURN,
          new NameTy(returnExp.row, returnExp.col, Integer.parseInt(typ.get(1))), null,
          level);
      newNode.canReturn = true;
      insert(newNode.name, newNode);
    } else {
      if ((returnCheck = lookup("$returnval")) != null){
        
        if (returnCheck.get(0).typ.type != 2) {
          String looking = "";
          switch (returnCheck.get(0).typ.type) {
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
          String got = "void";

          printError(
              String.format("Error in line %d, column %d: Invalid return value; Looking for '%s', got '%s'.",
                  returnExp.row + 1, returnExp.col, looking, got));

        }
      
    }
      NodeType newNode = new NodeType("return", Constants.RETURN,
          new NameTy(returnExp.row, returnExp.col, 2), null,
          level);
      newNode.canReturn = true;
      insert(newNode.name, newNode);
    }
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

  public void visit(SimpleDec simpleDec, int level, boolean flag) {
    if (simpleDec.typ != null)
      simpleDec.typ.accept(this, level, flag);
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
      NodeType newNode = new NodeType(simpleDec.name, 0, simpleDec.typ, simpleDec, level);
      insert(simpleDec.name, newNode);
    }
  }

  public void visit(SimpleVar simpleVar, int level, boolean flag) {
    ArrayList<NodeType> node;
    if ((node = lookup(simpleVar.name)) == null) {
      printError(
          String.format("Error in line %d, column %d at `%s': Undefined variable",
              simpleVar.row + 1, simpleVar.col, simpleVar.name));
    } else {
      NodeType newNode = new NodeType(simpleVar.name, 0, node.get(0).typ, null, level);
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

  public void visit(VarDecList varDecList, int level, boolean flag) {
    while (varDecList != null) {
      if (varDecList.head != null)
        varDecList.head.accept(this, level, flag);
      varDecList = varDecList.tail;
    }
  }

  public void visit(VarExp varExp, int level, boolean flag) {
    // Not needed
    if (varExp.variable != null)
      varExp.variable.accept(this, level, flag);
  }

  public void visit(WhileExp whileExp, int level, boolean flag) {
    indent(level);
    System.out.println("Entering WHILE conditional block scope:");
    if (whileExp.test != null) {
      level++;
      whileExp.test.accept(this, level, flag);
      if (!checkConditionType(level)) {
        printError(
            String.format("Error in line %d, column %d: Invalid condition type; Must be 'int' or 'bool' but got 'void'",
                whileExp.row + 1, whileExp.col));
      }
      level++;
      if (whileExp.body != null)
        whileExp.body.accept(this, level, flag);

      deleteLevelEntries(level);
      level--;
      deleteLevelEntries(level);
      level--;
    }
    indent(level);
    System.out.println("Exiting WHILE conditional block scope");
  }

  public void printError(String err) {
    SemanticAnalyzer.valid = false;
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
