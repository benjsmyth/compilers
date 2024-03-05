import java.io.InputStreamReader;
import java_cup.runtime.Symbol;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class Scanner {
  private Lexer scanner = null;

  public Scanner( Lexer lexer ) {
    scanner = lexer; 
  }

  public Symbol getNextToken() throws java.io.IOException {
    return scanner.next_token();
  }

  public static void main(String argv[]) {
    String filename = "./ast/structure.ast";
    //attempt to create file to write to
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

    try {
      Scanner scanner = new Scanner(new Lexer(new InputStreamReader(System.in)));
      BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
      Symbol tok = null;
      writer.write("");
      while( (tok=scanner.getNextToken()) != null ){
        System.out.println(sym.terminalNames[tok.sym]);
        writer.append(sym.terminalNames[tok.sym]);
      }
      writer.flush();
      writer.close();
    }
    catch (Exception e) {
      System.out.println("Unexpected exception:");
      e.printStackTrace();
    }

  }
}
