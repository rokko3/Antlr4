import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.nio.file.*;

public class Calc {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.err.println("Uso: java Calc <archivo.txt>");
            System.exit(1);
        }

        // El primer argumento es el archivo de entrada
        String fileName = args[0];

        // Leemos el archivo
        String input = Files.readString(Paths.get(fileName));

        // Creamos lexer y parser
        LabeledExprLexer lexer = new LabeledExprLexer(CharStreams.fromString(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LabeledExprParser parser = new LabeledExprParser(tokens);

        // Parseamos desde la regla inicial 'prog'
        ParseTree tree = parser.prog();

        // Ejecutamos el visitor
        EvalVisitor eval = new EvalVisitor();
        eval.visit(tree);
    }
}
