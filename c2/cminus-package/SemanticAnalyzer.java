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

  // Constructor
  public SemanticAnalyzer(PrintStream old, PrintStream current) {
    this.table = new HashMap<String, ArrayList<NodeType>>();
    this.old = old;
    this.tableStream = current;
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
               if (typ == -1 || typ == node.typ.type){
                typ = node.typ.type;
               }
               else {
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

    boolean error = false;
    if (lookup(arrayDec.name) != null) {
      
      ArrayList<NodeType> node = lookup(arrayDec.name);
      if (node != null) {
        for (NodeType n : node) {
          if(n.level == level){
            error = true;
            printError(
              String.format("Error in line %d, column %d at `%s': Redeclaration error",
              arrayDec.row, arrayDec.col, arrayDec.name)
            );
          }
        }
      }
    
    }

    if(error == false){
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
          printError("Error: No type");
	  break;
      }
      NodeType newNode = new NodeType(arrayDec.name, arrayDec, arrayDec.typ, level);
      insert(arrayDec.name, newNode);
    }
  }

  public void visit(AssignExp assignExp, int level) {
    indent(level);
    System.out.println("assign");
    level ++;
    if (assignExp.lhs != null) assignExp.lhs.accept(this, level);
    if (assignExp.rhs != null) assignExp.rhs.accept(this, level);
    if (sameLevelTypes(level) == -1){
      printError(
        String.format("Error in line %d, column %d: Invalid assignment error",
        assignExp.row + 1, assignExp.col)
      );
    }
    deleteLevelEntries(level); 

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

    boolean error = false;

    if (lookup(functionDec.func) != null) {
      
      ArrayList<NodeType> node = lookup(functionDec.func);
      if (node != null) {
        for (NodeType n : node) {
          if(n.level == level){
            error = true;
            printError(
              String.format("Error in line %d, column %d at `%s': Redeclaration error",
              functionDec.row, functionDec.col, functionDec.func)
            );
          }
        }
      }
    
    }
    if(error == false){
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
      NodeType newNode = new NodeType(functionDec.func, functionDec, functionDec.result, level);
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
    indent(level);

    NodeType newNode = new NodeType("$" + Integer.toString(intExp.value), null, new NameTy(intExp.row, intExp.col, NameTy.INT), level);
    insert(newNode.name, newNode);

    System.out.println(intExp.value + ": " + "int");
  }

  public void visit(NameTy nameTy, int level) {
    // Not needed
  }

  public void visit(NilExp nilExp, int level) {
    // Not implemented
  }

  public void visit(OpExp opExp, int level) {

    indent(level);
    System.out.print("operation: ");
    switch (opExp.op){

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

    level ++;
    if (opExp.left != null){
      opExp.left.accept( this, level );
    }
    
    if (opExp.right != null){
      opExp.right.accept( this, level );
    }

    int typ;

    if ((typ = sameLevelTypes(level)) == -1){
      printError(
        String.format("Error in line %d, column %d: Invalid operand type error",
        opExp.row + 1, opExp.col)
      );
    }
    deleteLevelEntries(level);

    level --;

    NodeType newNode = new NodeType(Integer.toString(opExp.op), null, new NameTy(opExp.row, opExp.col, typ), level);
    insert(newNode.name, newNode);

  }

  public void visit(ReturnExp returnExp, int level) {
    // Not implemented
  }

  public void visit(SimpleDec simpleDec, int level) {
    if (simpleDec.typ != null)
      simpleDec.typ.accept(this, level);
    boolean error = false;
      if (lookup(simpleDec.name) != null) {
        ArrayList<NodeType> node = lookup(simpleDec.name);
        if (node != null) {
          for (NodeType n : node) {
            if(n.level == level){
              error = true;
              printError(
                String.format("Error in line %d, column %d at `%s': Redeclaration error",
                simpleDec.row, simpleDec.col, simpleDec.name)
              );
            }
          }
        }
      
      }
    if(error == false){
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
          printError("Error: No type");
          break;
      }
      NodeType newNode = new NodeType(simpleDec.name, simpleDec, simpleDec.typ, level);
      insert(simpleDec.name, newNode);
    }
  }

  public void visit(SimpleVar simpleVar, int level) {
    //simpleVar.accept(this, level);
    ArrayList<NodeType> node;
    if ((node = lookup(simpleVar.name)) == null) {
      printError(
        String.format("Error in line %d, column %d at `%s': Undefined variable error",
          simpleVar.row + 1, simpleVar.col, simpleVar.name)
      );
    }
    else {
      NodeType newNode = new NodeType(simpleVar.name, null, node.get(0).typ, level);
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
    while(varDecList != null) {
      if (varDecList.head != null)
        varDecList.head.accept(this, level);
      varDecList = varDecList.tail;
    }
  }

  public void visit(VarExp varExp, int level) {
    // Not implemented
    if (varExp.variable != null)
      varExp.variable.accept( this, level );
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

  public void printError(String err){
    System.setOut(this.old);
    System.err.println(err);
    System.setOut(this.tableStream);
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
