/*
  Created by: Fei Song
  File Name: Main.java
  To Build: 
  After the Scanner.java, tiny.flex, and tiny.cup have been processed, do:
    javac Main.java
  
  To Run: 
    java -classpath /usr/share/java/cup.jar:. Main gcd.tiny

  where gcd.tiny is an test input file for the tiny language.
*/
   
import java.io.*;
import absyn.*;
   
class Main {
  public static boolean SHOW_TREE = false;
  static public void main(String argv[]) {    
    /* Start the parser */

    for(String arg : argv){
      if (arg.equals("-a")){
        SHOW_TREE = true;
      }
    }
    String filename = "./ast/structure.ast";
    try {
      File f = new File(filename);
      if (f.createNewFile()) {
        System.out.println("File Created: " + f.getName());
      }
    }
    catch (Exception e) {
      System.out.println("Unexpected exception:");
      e.printStackTrace();
    }
    //for storing the output to system.out
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(baos);
    PrintStream old = System.out;
    System.setOut(ps);
    try {
      parser p = new parser(new Lexer(new FileReader(argv[0])));
      Absyn result = (Absyn)(p.parse().value);      
      if (SHOW_TREE && result != null) {
         System.out.println("The abstract syntax tree is:");
         AbsynVisitor visitor = new ShowTreeVisitor();
         result.accept(visitor, 0); 
      }
    } catch (Exception e) {
      /* do cleanup here -- possibly rethrow e */
      e.printStackTrace();
    }

    if (SHOW_TREE){
      try{
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write(baos.toString());
        writer.close();
      }
      catch (Exception e) {
        System.out.println("Unexpected exception:");
        e.printStackTrace();
      }
    }
    System.out.flush();
    System.setOut(old);

    System.out.print(baos.toString());
  }
}
