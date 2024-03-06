import java.io.*;
import absyn.*;

class Main {
  public static String programName;
  public static boolean SHOW_TREE = false;
  static public void main(String argv[]) {

    for (String arg : argv) {
      if (arg.equals("-a")) {
        SHOW_TREE = true;
      }
      if (arg.endsWith(".cm")) {
        int e = arg.lastIndexOf('.');
        int p = arg.lastIndexOf('/');
        programName = (p == -1) ? arg.substring(0, e)
          : arg.substring(p + 1, e);
      }
    }
    String filename = String.format("./ast/%s.abs", programName);
//    String filename = "./structure.abs";
    try {
      File f = new File(filename);
      if (f.createNewFile()) {
        System.out.println("File created: " + f.getName());
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

    /* Start the parser */
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
