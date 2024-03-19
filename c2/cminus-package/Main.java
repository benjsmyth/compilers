import java.io.*;
import absyn.*;

class Main {
  public static String programName;
  public static boolean GEN_TREE = false;
  public static boolean GEN_TABLE = false;
  static public void main(String argv[]) {

    for (String arg : argv) {
      if (arg.equals("-a")) {
        GEN_TREE = true;
      }
      else if (arg.equals("-s")) {
        GEN_TABLE = true;
      }
      else if (arg.endsWith(".cm")) {
        int e = arg.lastIndexOf('.');
        int p = arg.lastIndexOf('/');
        programName = (p == -1) ? arg.substring(0, e)
          : arg.substring(p + 1, e);
      }
    }

    String absFile = String.format("./ast/%s.abs", programName);
    String symFile = String.format("./sym/%s.sym", programName);

    PrintStream old = System.out;

    if (GEN_TREE) {
      try {
        File f = new File(absFile);
        if (f.createNewFile()) {
          System.out.println("Created " + f.getName());
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    if (GEN_TABLE) {
      try {
        File f = new File(symFile);
        if (f.createNewFile()) {
          System.out.println("Created " + f.getName());
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    // For storing the output
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(baos);
    System.setOut(ps);

    ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
    PrintStream ps2 = new PrintStream(baos2);

    // Start the parser
    try {
      parser p = new parser(new Lexer(new FileReader(argv[0])));
      Absyn result = (Absyn)(p.parse().value);
      if (result != null) {
         AbsynVisitor visitor = new ShowTreeVisitor();
         result.accept(visitor, 0);

         BufferedWriter writer = new BufferedWriter(new FileWriter(absFile));
         writer.write(baos.toString());
         writer.close();
      }

      System.setOut(ps2);

      if (result != null && p.valid) {
        AbsynVisitor visitor = new SemanticAnalyzer(old, ps2);
        result.accept(visitor, 0);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    if (GEN_TABLE) {
      try {
        BufferedWriter writer = new BufferedWriter(new FileWriter(symFile));
        writer.write(baos2.toString());
        writer.close();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }

    System.setOut(old);

    if (GEN_TREE) System.out.print(baos.toString());
    if (GEN_TABLE) System.out.print(baos2.toString());
  }
}
