/**
 * Created by Antoine-Ali on 18/02/2015.
 */


import min15.Interpreter.SyntaxicChecker;
import min15.exceptions.InterpreterException;
import lexer.Lexer;
import lexer.LexerException;
import min15.exceptions.SemanticException;
import node.Node;
import parser.Parser;
import parser.ParserException;

import java.io.*;

public class Program {


    public static void main(String[] args) {
        Reader in = null;

        if (args.length == 0)
        {
            //Si pas d'argument, on lit depuis l'entrée standard
            in = new InputStreamReader(System.in);
        }
        else if (args.length == 1)
        {
            try
            {
                in = new FileReader(args[0]);
            }
            catch(FileNotFoundException Fnfe)
            {
                System.err.print("[ERREUR][LECTURE] : Fichier introuvable " + args[0] + ".");
                System.exit(1);
            }
        }
        else
        {
            System.err.println("[ERREUR][ARGUMENTS] :  0 ou 1 acceptés");
            System.exit(1);
        }

        Node rootNode = null;

        try
        {
            rootNode = new Parser(new Lexer(new PushbackReader(in))).parse();

        }
        catch(IOException e)
        {
            String inputName = "de l'entrée standard";

            if (args.length == 1)
            {
                inputName = "du fichier '" + args[1] + "'";
            }

            System.err.println("[ERREUR][LECTURE] : " + inputName + " : " + e.getMessage());

            System.exit(1);
        }
        catch(ParserException e)
        {
            System.err.println("[ERREUR][SYNTAXE] : " + e.getMessage());
            System.exit(1);
        }
        catch(LexerException e)
        {
            System.err.println("[ERREUR][LEXICALE] : " + e.getMessage());
            System.exit(1);
        }

        SyntaxicChecker checker = new SyntaxicChecker();
        try
        {
            checker.Visit(rootNode);
        }
        catch(InterpreterException e)
        {
            System.out.flush();
            System.err.println("[ERREUR][INTERPRETEUR] : " + e.getMessage() + ".");
            checker.PrintStackTrace();
            System.exit(1);
        }
        catch(SemanticException e)
        {
            System.err.println("[ERREUR][SEMANTIC] : " + e.getMessage());
        }

        System.exit(0);
    }
}
