package min15.statistics;

import Utils.Tuple;
import com.sun.javaws.exceptions.InvalidArgumentException;
import min15.structure.ClassInfo;
import min15.structure.ClassTable;
import min15.structure.FileStatistics;
import min15.structure.MethodInfo;
import node.TClassName;
import node.Token;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.PrintWriter;
import java.util.*;

/**
 * Created by Antoine-Ali on 02/04/2015.
 * Permanent Code ZARA20069408
 */
public class Comparison
{
    public Double cheatProbability = 0.0d;
    public Double linesSimilarity = 0.0d;

    public FileStatistics original;
    public FileStatistics tested;

    public ClassTable oClassTable;
    public ClassTable tClassTable;


    private List<Double> methodSimilarities;
    private Double methodNameSimilarities = 0.0d;

    public void printOutput(PrintWriter pw)
    {
        throw new NotImplementedException();
    }

    public void compare()
    {

        //if cheat probability is already 100 or more, we are sure that the second file cheated on the first one no need to compute anything
        //if(cheatProbability >= 100) return;

        Double classNumberSimilarity = 0.0d;
        Double classNamesDifference = 0.0d;
        Double methodNumberSimilarity = 0.0d;


        classNumberSimilarity = ((double)tested.classNames.size() * 100.0d) / (double)original.classNames.size();

        classNamesDifference = computeClassNameDifference(classNumberSimilarity);

        methodNumberSimilarity = computeMethodNumberSimilarity();


        Tuple[] array = new Tuple[methodSimilarities.size() + 4];
        array[0] = new Tuple<>(classNamesDifference, 1.0d);
        array[1] = new Tuple<>(classNumberSimilarity, 1.0d);
        array[2] = new Tuple<>(methodNumberSimilarity, 1.0d);
        array[3] = new Tuple<>(linesSimilarity, 2.0d);

        for(int i = 4; i < array.length; i++)
        {
            array[i] = new Tuple<>(methodSimilarities.get(i-4), 1.0d);
        }

        average(array);
    }

    private Double computeClassNameDifference(Double classNamesDifference)
    {
        int diffCount = 0;
        //Taking the smallest
        Dictionary<String, Set<String>> smallest = classNamesDifference > 100.0d ? original.classNames : tested.classNames;
        Dictionary<String, Set<String>> larger = classNamesDifference > 100.0d ? tested.classNames : original.classNames;

        Set<String> commonClasses = new LinkedHashSet<>();


        Enumeration<String> cl = smallest.keys();

        while(cl.hasMoreElements())
        {
            String name = cl.nextElement();
            if (larger.get(name) == null)
            {
                diffCount++;

            }
            else
            {
                commonClasses.add(name);
            }
        }
        classNamesDifference = (((double)diffCount * 100.0d) / (double)larger.size());
        methodSimilarities = new LinkedList<>();
        computeMethodSimilarities(commonClasses);
        return classNamesDifference;
    }

    private void computeMethodSimilarities(Set<String> commonClasses)
    {
        for(String name : commonClasses)
        {
            ClassInfo originalClass = oClassTable.Get(name);
            ClassInfo testedClass = tClassTable.Get(name);
            Boolean originalSizeSuperior = original.classNames.get(name).size() > tested.classNames.get(name).size();
            Set<String> looper = originalSizeSuperior ? original.classNames.get(name) : tested.classNames.get(name);
            Set<String> against = originalSizeSuperior ? tested.classNames.get(name) : original.classNames.get(name);

            for(String methodName : looper)
            {
                if(against.contains(methodName))
                {
                    MethodInfo oMethod = originalClass.GetMethodTable().GetMethodInfo(methodName);
                    MethodInfo tMethod = testedClass.GetMethodTable().GetMethodInfo(methodName);

                    try
                    {
                        methodSimilarities.add(computeMethodSimilarity(oMethod, tMethod));
                    } catch (InvalidArgumentException e)
                    {}
                }
            }
        }
    }

    private Double computeMethodSimilarity(MethodInfo original, MethodInfo tested) throws InvalidArgumentException
    {
        if(original.getDefinition() == null || tested.getDefinition() == null) throw new InvalidArgumentException(new String[]{"Methods musn't be primitives"});

        TokenListing lister = new TokenListing();
        List<Token> originalList = lister.getTokenList(original.getDefinition());
        List<Token> testedList = lister.getTokenList(tested.getDefinition());

        int indexO = 0;
        int indexT = 0;
        int longestMatchingTokens = 0;

        for(; indexO < originalList.size(); indexO++)
        {
            Boolean found = false;
            for(; indexT < testedList.size(); indexT++)
            {
                if(originalList.get(indexO).equals(testedList.get(indexT)))
                {
                    longestMatchingTokens++;
                    found = true;
                }
            }
            if(!found) indexT = 0;
        }


        int longest = (originalList.size() > testedList.size()) ? originalList.size() : testedList.size();

        return (((double)longestMatchingTokens * 100.0d) / ((double)longest));

    }

    private Double computeMethodNumberSimilarity()
    {
        Double methodNumberSimilarity;
        int testedMethodNumber = 0;
        Enumeration<Set<String>> ml = tested.classNames.elements();
        while(ml.hasMoreElements())
        {
            Set<String> methods  = ml.nextElement();
            testedMethodNumber += methods.size();
        }

        int originalMethodNumber = 0;
        Enumeration<Set<String>> ol = original.classNames.elements();
        while(ol.hasMoreElements())
        {
            Set<String> methods  = ol.nextElement();
            originalMethodNumber += methods.size();
        }

        methodNumberSimilarity = ((double)testedMethodNumber * 100.0d) / (double)originalMethodNumber;
        return methodNumberSimilarity;
    }

    /**
     * @param values Left element of tuple is the value, left is ponderation
     */
    private void average(@SuppressWarnings("uncheked") Tuple<Double, Double>[] values)
    {
        double result = 0.0d;
        double total = 0.0d;

        for(Tuple<Double, Double> v : values)
        {
            result += v.left * v.right;
            total += v.right;
        }

        cheatProbability += result / total;
    }

    // region singleton implementation
    private Comparison()
    {

    }

    private static class ComparisonHolder
    {
        private final static Comparison instance  = new Comparison();
    }

    public static Comparison getInstance()
    {
        return ComparisonHolder.instance;
    }
    //endregion
}
