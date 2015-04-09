package min15.statistics;

import Utils.Tuple;
import analysis.WidthFirstAdapter;
import min15.structure.FileStatistics;
import node.*;

import java.util.*;

/**
 * Created by Antoine-Ali on 01/04/2015.
 * Permanent Code ZARA20069408
 */
public class WidthSearch extends WidthFirstAdapter
{

    public FileStatistics stats;
    Object result = null;

    private <T> T eval(Node node)
    {
        node.apply(this);
        Object r = result;
        result = null;
        return (T)r;
    }


    public WidthSearch(String filePath)
    {
        super();
        stats = new FileStatistics(filePath);
    }


    public <T extends Node> void visit(T node)
    {
        Tuple<List<Node>, List<Token>> childrens = getChildrens(node);

        node.apply(this);

        if (childrens.right != null) applyTokens(childrens.right);
        level++;
        applyNodes(childrens.left);
        visit(childrens.left);
    }


    private void applyTokens(List<Token> tokens)
    {
        for(Token t : tokens)
        {
            if (t != null) t.apply(this);
        }
    }

    private void applyNodes(List<Node> nodes)
    {
        for(Node n : nodes)
        {
            if (n != null) n.apply(this);
        }
    }

    public void visit(List<Node> nodes)
    {
        nodes.removeAll(Collections.singleton(null));
        List<Node> nextNodes = new LinkedList<>();

        for(Node n : nodes)
        {
            Tuple<List<Node>, List<Token>> childrens = getChildrens(n);
            if (childrens.left != null) nextNodes.addAll(childrens.left);
            if (childrens.right != null) applyTokens(childrens.right);
        }
        level++;
        if(level < limit)
        {
            applyNodes(nextNodes);
            visit(nextNodes);
        }
    }

    @Override
    public void caseAClassDef(AClassDef node)
    {
        stats.classNames.put(node.getClassName().getText(), new LinkedHashSet<String>());
    }

    @Override
    public void caseAMethodMember(AMethodMember node)
    {
        String className = ((AClassDef)node.parent()).getClassName().getText();
        if(stats.classNames.get(className) != null)
        {
            stats.classNames.get(className).add(node.getId().getText());
        }
    }

    @Override
    public void caseAOperatorMember(AOperatorMember node)
    {
        String className = ((AClassDef)node.parent()).getClassName().getText();
        if(stats.classNames.get(className) != null)
        {
            String operator = eval(node.getOperator());
            stats.classNames.get(className).add(operator);
        }
    }

    @Override
    public void caseAInternMethodMember(AInternMethodMember node)
    {
        String className = ((AClassDef)node.parent()).getClassName().getText();
        if(stats.classNames.get(className) != null)
        {
            stats.classNames.get(className).add(node.getId().getText());
        }
    }

    @Override
    public void caseAInternOperatorMember(AInternOperatorMember node)
    {
        String className = ((AClassDef)node.parent()).getClassName().getText();
        if(stats.classNames.get(className) != null)
        {
            String operator = eval(node.getOperator());
            stats.classNames.get(className).add(operator);
        }
    }

    //region boilerplate
    @Override
    public void caseAEqOperator(AEqOperator node)
    {
        result = "=";
    }

    @Override
    public void caseANeqOperator(ANeqOperator node)
    {
        result = "!=";
    }

    @Override
    public void caseALtOperator(ALtOperator node)
    {
        result = "<";
    }

    @Override
    public void caseAGtOperator(AGtOperator node)
    {
        result = ">";
    }

    @Override
    public void caseALteqOperator(ALteqOperator node)
    {
        result = "<=";
    }

    @Override
    public void caseAGteqOperator(AGteqOperator node)
    {
        result = ">=";
    }

    @Override
    public void caseAPlusOperator(APlusOperator node)
    {
        result = "+";
    }

    @Override
    public void caseAMinusOperator(AMinusOperator node)
    {
        result = "-";
    }

    @Override
    public void caseAStarOperator(AStarOperator node)
    {
        result = "*";
    }

    @Override
    public void caseASlashOperator(ASlashOperator node)
    {
        result = "/";
    }

    @Override
    public void caseAPercentOperator(APercentOperator node)
    {
        result = "%";
    }
    //endregion

//    public <T extends Node> void defaultCase(T node)
//    {
//        super.defaultCase(node);
//    }
}
