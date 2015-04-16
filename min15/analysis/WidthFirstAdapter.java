/* This file was generated by SableCC (http://www.sablecc.org/). */

package min15.analysis;

import Utils.Tuple;
import analysis.AnalysisAdapter;
import node.*;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class WidthFirstAdapter extends AnalysisAdapter
{

    public int level = 0;
    public int limit = 4;

    private <T extends node.Node> List<Method> getNodeMethods(T node)
    {
        List<Method> methods = new LinkedList<>();
        for(Method m : node.getClass().getMethods())
        {
            Class<?> returnType = m.getReturnType();

            if(returnType.getName().contains("node.P")||
                    returnType.getName().contains("node.T") ||
                    returnType.getName().contains("LinkedList"))
            {
                methods.add(m);
            }
        }
        return methods;
    }

    public <T extends Node> Tuple<List<Node>, List<Token>> getChildrens(T node)
    {
        Tuple<List<Node>, List<Token>> result =
                new Tuple<>(new LinkedList<Node>(), new LinkedList<Token>());
        List<Method> methods = getNodeMethods(node);
        for(Method m : methods)
        {
            try
            {
                if (m.getReturnType().getName().contains("node.P"))
                {
                    result.left.add((Node) m.invoke(node));
                } else if (m.getReturnType().getName().contains("node.T"))
                {
                    result.right.add((Token) m.invoke(node));
                }
                else {
                    LinkedList list = (LinkedList)m.invoke(node);
                    if (list.getFirst().getClass().getName().contains("node.P")
                            || list.getFirst().getClass().getName().contains("node.A"))
                    {
                        result.left.addAll((LinkedList<Node>) list);
                    }
                    else {
                        result.right.addAll((LinkedList<Token>)list);
                    }
                }
            }
            catch(Exception e)
            {
                continue;
            }
        }
        return result;
    }
}