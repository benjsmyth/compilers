import java.io.*;
import absyn.*;

class Main {
  public static String programName;
  public static boolean GEN_TREE = false;
  public static boolean GEN_TABLE = false;
  public static boolean GEN_CODE = false;
  public static void main(String argv[]) {

    /* Parse arguments to compiler */
    for (String arg : argv) {
      if (arg.equals("-a")) {  // -a
        GEN_TREE = true;
      }
      else if (arg.equals("-s")) {  // -s
        GEN_TABLE = true;
      }
      else if (arg.equals("-c")) {  // -c
        GEN_CODE = true;
      }
      else if (arg.endsWith(".cm")) {  // <program>.cm
        int e = arg.lastIndexOf('.');
        int p = arg.lastIndexOf('/');
        programName = (p == -1) ? arg.substring(0, e)
          : arg.substring(p + 1, e);
      }
    }

    // Create filenames
    String astFile = String.format("./ast/%s.abs", programName);
    String symFile = String.format("./sym/%s.sym", programName);
    String codeFile = String.format("./code/%s.tm", programName);

    // Create files
    PrintStream old = System.out;
    if (GEN_TREE) {  // ast/<program>.abs
      try {
        File f = new File(astFile);
        if (f.createNewFile()) {
          System.out.println("Created " + f.getName());
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    if (GEN_TABLE) {  // sym/<program>.sym
      try {
        File f = new File(symFile);
        if (f.createNewFile()) {
          System.out.println("Created " + f.getName());
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    if (GEN_CODE) {  // code/<program>.tm
      try {
        File f = new File(codeFile);
        if (f.createNewFile()) {
          System.out.println("Created " + f.getName());
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    /* AST output */
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(baos);
    System.setOut(ps);

    /* Symbol table output */
    ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
    PrintStream ps2 = new PrintStream(baos2);

    /* Code output */
    ByteArrayOutputStream baos3 = new ByteArrayOutputStream();
    PrintStream ps3 = new PrintStream(baos3);

    /* Start the parser */
    try {
      parser p = new parser(new Lexer(new FileReader(argv[0])));
      Absyn result = (Absyn)(p.parse().value);
      if (result != null) {
        /* Parse */
        AbsynVisitor visitor = new ShowTreeVisitor();
        result.accept(visitor, 0);
        /* Write parse */
        BufferedWriter writer = new BufferedWriter(new FileWriter(astFile));
        writer.write(baos.toString());
        writer.close();
      }

      /* Analyze only if parsed */
      System.setOut(ps2);
      if (result != null && p.valid) {
        AbsynVisitor visitor = new SemanticAnalyzer(old, ps2);
        result.accept(visitor, 0);
      }

      /* Generate only if analyzed */
      // System.setOut(ps3);
      // if (result != null && s.valid) {  // How do we check that analysis worked?
      //   AbsynVisitor codeGenerator = new CodeGenerator();
      //   result.accept(codeGenerator, 0);
      // }
    } catch (Exception e) {
      e.printStackTrace();
    }

    /* Write analysis */
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

    /* Write code */
    if (GEN_CODE) {
      try {
        BufferedWriter writer = new BufferedWriter(new FileWriter(codeFile));
        writer.write(baos3.toString());
        writer.close();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }

    /* Print to terminal */
    System.setOut(old);
    if (GEN_TREE) System.out.print(baos.toString());
    if (GEN_TABLE) System.out.print(baos2.toString());
    if (GEN_CODE) System.out.print(baos3.toString());
  }
}
