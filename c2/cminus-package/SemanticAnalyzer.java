import absyn.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

public class SemanticAnalyzer implements AbsynVisitor {
  public HashMap<String, ArrayList<NodeType>> table;

  // Constructor
  public SemanticAnalyzer() {
    this.table = new HashMap<String, ArrayList<NodeType>>();
  }

  // Table methods
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

  public void deleteLevelEntries(int level) {        //for deleting a level from the hashmap
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

  public void printHashTable() {
    System.out.println("\nHash Table:");
    Iterator<HashMap.Entry<String, ArrayList<NodeType>>> iterator;
    iterator = table.entrySet().iterator();
    while (iterator.hasNext()) {
      HashMap.Entry<String, ArrayList<NodeType>> entry = iterator.next();
      System.out.println(entry.getKey() + ": " + entry.getValue().toString());
    }
  }

  // Indent method
  private void indent(int level) {
    for (int i = 0; i < level * 4; i++) System.out.print(" ");
  }

  // Visitor methods (semantic rules)
  public void visit(ArrayDec arrayDec, int level) {
    if (lookup(arrayDec.name) != null) {
      System.err.println(
        String.format("Error in line %d, column %d at `%s': Redeclaration error",
          arrayDec.row, arrayDec.col, arrayDec.name)
      );
    }
    else {
      switch(arrayDec.typ.type){
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
          System.out.println("Error: No type");
	  break;
      }
      NodeType newNode = new NodeType(arrayDec.name, arrayDec, level);
      insert(arrayDec.name, newNode);
    }
  }

  public void visit(AssignExp assignExp, int level) {
    if (assignExp.lhs != null) assignExp.lhs.accept(this, level);
    if (assignExp.rhs != null) assignExp.rhs.accept(this, level);
  }

  public void visit(BoolExp boolExp, int level) {
    // Not implemented
  }

  public void visit(CallExp callExp, int level) {
    // Not implemented
  }

  public void visit(CompoundExp compoundExp, int level) {
    if (compoundExp.decs != null) compoundExp.decs.accept(this, level);
    if (compoundExp.exps != null) compoundExp.exps.accept(this, level);
  }

  public void visit(DecList decList, int level) {
    System.out.println("Entering global scope:");
    level++;
    while(decList != null) {
      if (decList.head != null)
	decList.head.accept(this, level);
      decList = decList.tail;
    }
    System.out.println("Exiting global scope");
  }

  public void visit(ExpList expList, int level) {
    while(expList != null) {
      if (expList.head != null)
        expList.head.accept(this, level);
      expList = expList.tail;
    }
  }

  public void visit(FunctionDec functionDec, int level) {
    if (lookup(functionDec.func) != null) {
      System.err.println(
        String.format("Error in line %d, column %d at `%s': Redeclaration error",
          functionDec.row, functionDec.col, functionDec.func)
      );
    }
    else {
      indent(level);
      System.out.println(
        String.format("Entering function scope for `%s':", functionDec.func)
      );
      int prevLevel = level;
      level++;
      indent(level);
      switch(functionDec.result.type) {
        case 0:
          System.out.println("function " + functionDec.func + ": bool");
          break;
        case 1:
          System.out.println("function " + functionDec.func + ": int");
          break;
        case 2:
          System.out.println("function " + functionDec.func + ": void");
          break;
        default:
          System.out.println("Error: No type");
	  break;
      }
      NodeType newNode = new NodeType(functionDec.func, functionDec, level);
      insert(functionDec.func, newNode);
      if (functionDec.params != null)
          functionDec.params.accept(this, level);
      else {
        if (functionDec.body != null)
          functionDec.body.accept(this, level);
      }
      deleteLevelEntries(level);                    //not sure if this should delete the function dec 
      indent(prevLevel);
      System.out.println("Exiting function scope");
    }
  }

  public void visit(IfExp ifExp, int level) {
    indent(level);
    System.out.println("Entering block scope:");
    int prevLevel = level;
    level++;
    if (ifExp.test != null)
      ifExp.test.accept(this, level);
    if (ifExp.thenpart != null)
      ifExp.thenpart.accept(this, level);
    if (ifExp.elsepart != null)
      ifExp.elsepart.accept( this, level );
    deleteLevelEntries(level);
    indent(prevLevel);
    System.out.println("Exiting block scope");
  }

  public void visit(IndexVar indexVar, int level) {
    // Not implemented
  }

  public void visit(IntExp intExp, int level) {
    // Not implemented
  }

  public void visit(NameTy nameTy, int level) {
    // Not implemented
  }

  public void visit(NilExp nilExp, int level) {
    // Not implemented
  }

  public void visit(OpExp opExp, int level) {
    // Not implemented
  }

  public void visit(ReturnExp returnExp, int level) {
    // Not implemented
  }

  public void visit(SimpleDec simpleDec, int level) {
    if (simpleDec.typ != null)
      simpleDec.typ.accept(this, level);
    if (lookup(simpleDec.name) != null) {
      System.err.println(
        String.format("Error in line %d, column %d at `%s': Redeclaration error",
          simpleDec.row, simpleDec.col, simpleDec.name)
      );
    }
    else {
      indent(level);
      switch(simpleDec.typ.type) {
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
          System.err.println("Error: No type");
          break;
      }
      NodeType newNode = new NodeType(simpleDec.name, simpleDec, level);
      insert(simpleDec.name, newNode);
    }
  }

  public void visit(SimpleVar simpleVar, int level) {
    simpleVar.accept(this, level);
    if (lookup(simpleVar.name) == null) {
      System.err.println(
        String.format("Error in line %d, column %d at `%s': Undefined error",
          simpleVar.row, simpleVar.col, simpleVar.name)
      );
    }
    // Not implemented
  }

  public void visit(VarDecList varDecList, int level) {
    while(varDecList != null) {
      if (varDecList.head != null)
        varDecList.head.accept(this, level);
      varDecList = varDecList.tail;
    }
  }

  public void visit(VarExp varExp, int level) {
    // Not implemented
  }

  public void visit(WhileExp whileExp, int level) {
    indent(level);
    System.out.println("Entering block scope");
    int prevLevel = level;
    level++;
    if (whileExp.test != null)
      whileExp.test.accept(this, level);
    if (whileExp.body != null)
      whileExp.body.accept(this, level);
    deleteLevelEntries(level);
    indent(prevLevel);
    System.out.println("Exiting block scope");
  }


  /*
  public boolean isInteger(Dec dtype) {
    if (dtype.typ == 1) {
      return true;
    } else {
      return false;
    }
  }
  */
}
