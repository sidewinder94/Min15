/**
 * Created by Antoine-Ali on 18/02/2015.
 */


import min15.Interpreter.SyntaxicChecker;
import min15.exceptions.InterpreterException;
import lexer.Lexer;
import lexer.LexerException;
import min15.exceptions.SemanticException;
import min15.statistics.Comparison;
import min15.statistics.FileSearch;
import min15.statistics.WidthSearch;
import node.Node;
import node.Start;
import parser.Parser;
import parser.ParserException;

import java.io.*;

public class Program {


    public static void main(String[] args) {
        Reader original = null;
        Reader tested = null;
        if (args.length == 0)
        {
            System.out.println("Need ");
        }
        else if (args.length == 2)
        {
            try
            {
                original = new FileReader(args[0]);
                tested = new FileReader(args[1]);
                FileSearch.computeLineDifference(args[0], args[1]);
            }
            catch(FileNotFoundException Fnfe)
            {
                System.err.print("[ERREUR][LECTURE] : Fichier introuvable " + args[0] + ".");
                System.exit(1);
            }
            catch (IOException ex)
            {
                System.err.print("[ERREUR][LECTURE] : Fichier illisible " + args[0] + ".");
                System.exit(1);
            }
        }
        else
        {
            System.err.println("[ERREUR][ARGUMENTS] : 2 uniquement acceptés");
            System.exit(1);
        }

        Start rootNodeO = null;
        Start rootNodeT = null;

        try
        {
            rootNodeO = new Parser(new Lexer(new PushbackReader(original))).parse();
            rootNodeT = new Parser(new Lexer(new PushbackReader(tested))).parse();
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

        SyntaxicChecker checkerO = new SyntaxicChecker();
        SyntaxicChecker checkerT = new SyntaxicChecker();


        WidthSearch searcherO = new WidthSearch(args[0]);
        WidthSearch searcherT = new WidthSearch(args[1]);
        try (PrintWriter pw = new PrintWriter("output.txt"))
        {
            checkerO.Visit(rootNodeO);
            checkerT.Visit(rootNodeT);
            searcherO.visit(rootNodeO.getPFile());
            searcherT.visit(rootNodeT.getPFile());
            Comparison.getInstance().oClassTable = checkerO._classTable;
            Comparison.getInstance().tClassTable = checkerT._classTable;
            Comparison.getInstance().original = searcherO.stats;
            Comparison.getInstance().tested = searcherT.stats;
            Comparison.getInstance().compare();
            Comparison.getInstance().printOutput(pw);
        }
        catch(InterpreterException e)
        {
            System.out.flush();
            System.err.println("[ERREUR][INTERPRETEUR] : " + e.getMessage() + ".");
            System.exit(1);
        }
        catch(SemanticException e)
        {
            System.err.println("[ERREUR][SEMANTIC] : " + e.getMessage());
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        System.exit(0);
    }
}
