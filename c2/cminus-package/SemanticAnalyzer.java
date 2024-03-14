import absyn.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;



public class SemanticAnalyzer implements AbsynVisitor {

      public HashMap<String, ArrayList<NodeType>> table;

      public SemanticAnalyzer() {
        this.table = new HashMap<>();
      }

      public void delete(String key) {
        table.remove(key);
      }

      public void insert(String key, NodeType node) {
            ArrayList<NodeType> list = table.get(key);
            if (list == null) {
                list = new ArrayList<>();
                table.put(key, list);
            }
            list.add(node);
        }

      public ArrayList<NodeType> lookup(String key) {
          return table.get(key);
        }


      public void printHashTable(){
        System.out.println("Created HashMap:");
        Iterator<HashMap.Entry<String, ArrayList<NodeType>>> iterator;
        iterator = table.entrySet().iterator();
        while (iterator.hasNext()) {
          HashMap.Entry<String, ArrayList<NodeType>> entry = iterator.next();
          System.out.println(entry.getKey() + ": " + entry.getValue().toString());
        }        
      }

      /*public boolean isInterger(Dec dtype){
        if(dtype.typ == 1){
          return true;
        }
        else{
          return false;
        }
      }*/

      final static int SPACES = 4;

      private void indent( int level ) {
        for( int i = 0; i < level * SPACES; i++ ) System.out.print( " " );
      }

      

      public void visit( ArrayDec arrayDec, int level ){
        //indent(level);
        //System.out.println("1");
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
            System.out.println("Error: no type");

        }

        NodeType newNode = new NodeType(arrayDec.name, arrayDec, level);
        insert(arrayDec.name, newNode);
        //printHashTable();
        
      }
      
      public void visit( AssignExp assignExp, int level ){
        //System.out.println("2");
        if (assignExp.lhs != null)
        assignExp.lhs.accept( this, level );
  
      if (assignExp.rhs != null)
        assignExp.rhs.accept( this, level );
  
      }
    
      public void visit( BoolExp boolExp, int level ){
        //System.out.println("3");
      }
    
      public void visit( CallExp callExp, int level ){
        //System.out.println("4");
      }
    
      public void visit( CompoundExp compoundExp, int level ){
        //System.out.println("5");

        if (compoundExp.decs != null)
          compoundExp.decs.accept( this, level );
        if (compoundExp.exps != null)
          compoundExp.exps.accept( this, level );
        }
    
      public void visit( DecList decList, int level ){
        //System.out.println("6");
        System.out.println("Entering the global Scope");
        level++;

        while( decList != null ) {
          if (decList.head != null)
          decList.head.accept( this, level );
          decList = decList.tail;
        } 


        System.out.println("Leaving the global Scope");
      }
    
      public void visit( ExpList expList, int level ){
        //System.out.println("7");

        while( expList != null ) {
          if (expList.head != null)
            expList.head.accept( this, level );
          expList = expList.tail;
        } 

      }
    
      public void visit( FunctionDec functionDec, int level ){

        //indent( level );
        //System.out.println("8");
        indent( level );
        System.out.println("Entering the scope for function " + functionDec.func + ":");
        int prevLevel = level;
        level ++;

        if (functionDec.params != null)
          functionDec.params.accept( this, level );
        else {
          if (functionDec.body != null)
            functionDec.body.accept( this, level );
        }

        indent(prevLevel);
        System.out.println("Leaving the function scope");

      }
    
      public void visit( IfExp ifExp, int level ){

        //System.out.println("9");

        indent(level);
        System.out.println("Entering block scope");
        int prevLevel = level;
        level++;

        if (ifExp.test != null)
          ifExp.test.accept( this, level );
        if (ifExp.thenpart != null)
          ifExp.thenpart.accept( this, level );
        if (ifExp.elsepart != null ){
          ifExp.elsepart.accept( this, level );
        }
        indent(prevLevel);
        System.out.println("Exiting block scope");
      }
    
      public void visit( IndexVar indexVar, int level ){
        //System.out.println("10");
      }
    
      public void visit( IntExp intExp, int level ){
        //System.out.println("11");
      }
    
      public void visit( NameTy nameTy, int level ){
        //System.out.println("12"); 
      }
    
      public void visit( NilExp nilExp, int level ){
        //System.out.println("13");
      }
    
      public void visit( OpExp opExp, int level ){
        //System.out.println("14");
      }
    
      public void visit( ReturnExp returnExp, int level ){
        //System.out.println("15");
      }
    
      public void visit( SimpleDec simpleDec, int level ){
        //indent(level);
        //System.out.println("16");
        if (simpleDec.typ != null){
          simpleDec.typ.accept( this, level );
        }
        switch(simpleDec.typ.type){
          case 0:
            indent(level);
            System.out.println(simpleDec.name + ": bool");
            break;
          case 1:
            indent(level);
            System.out.println(simpleDec.name + ": int");
            break;
          case 2:
            indent(level);
            System.out.println(simpleDec.name + ": void");
            break;

          default:
            System.out.println("Error: no type");

        }

        NodeType newNode = new NodeType(simpleDec.name, simpleDec, level);
        insert(simpleDec.name, newNode);
        //printHashTable();
        
      }
    
      public void visit( SimpleVar simpleVar, int level ){
        //System.out.println("17");
      }
    
      public void visit( VarDecList varDecList, int level ){
        //System.out.println("18");

        while( varDecList != null ) {
          if (varDecList.head != null)
            varDecList.head.accept( this, level );
          varDecList = varDecList.tail;
        } 
      }
    
      public void visit( VarExp varExp, int level ){
        //System.out.println("19");
      }
    
      public void visit( WhileExp whileExp, int level ){
        //System.out.println("20");

        indent(level);
        System.out.println("Entering block scope");
        int prevLevel = level;
        level++;

        if (whileExp.test != null)
          whileExp.test.accept( this, level );
        if (whileExp.body != null)
          whileExp.body.accept( this, level );
        indent(prevLevel);
        System.out.println("Exiting block scope");
      }

}