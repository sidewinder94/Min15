package min15.statistics;

import java.util.regex.*;
import java.io.IOException;
import java.nio.charset.*;
import java.nio.file.*;

import static min15.statistics.Comparison.getInstance;

/**
 * Created by Antoine-Ali on 02/04/2015.
 * Permanent Code ZARA20069408
 */
public class FileSearch
{

    private static Pattern emptyLines = Pattern.compile("\\s*\\n$", Pattern.MULTILINE);
    private static Pattern comments = Pattern.compile("(#(?:[^\\n\\r])*)$", Pattern.MULTILINE);


    public static void computeLineDifference(String original, String tested) throws IOException
    {
        String origin = new String(Files.readAllBytes(Paths.get(original)), StandardCharsets.UTF_8);
        String test   = new String(Files.readAllBytes(Paths.get(tested)), StandardCharsets.UTF_8);

        if(origin.equals(test))
        {
            getInstance().cheatProbability += 100.0d;
        }

        int originLength  = compressString(origin).length();
        int testedLength = compressString(test).length();

        getInstance().linesSimilarity = (((double)testedLength * 100.0d) / (double)originLength);

    }


    private static String compressString(String input)
    {
        return emptyLines.matcher(comments.matcher(input).replaceAll("")).replaceAll("");
    }
}
