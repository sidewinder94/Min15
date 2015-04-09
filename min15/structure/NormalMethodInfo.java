package min15.structure;


import min15.Interpreter.SyntaxicChecker;
import node.AMethodMember;
import node.AStmts;
import node.Node;
import node.TId;

import java.util.List;

/**
 * Created by Antoine-Ali on 18/02/2015.
 */
public class NormalMethodInfo extends MethodInfo
{
    private final AMethodMember _definition;


    @Override
    public AStmts getDefinition()
    {
        return (AStmts)_definition.getStmts();
    }


    public NormalMethodInfo(MethodTable methodTable, AMethodMember definition, List<TId> params)
    {
        super(methodTable, params);
        this._definition = definition;
    }

    public NormalMethodInfo(MethodTable methodTable, AMethodMember definition, List<TId> params, List<ClassInfo> paramTypes, ClassInfo returnType)
    {
        super(methodTable,params,paramTypes, returnType);
        this._definition = definition;
    }

    @Override
    public String GetName()
    {
        return this._definition.getId().getText();
    }


    @Override
    public void Execute(SyntaxicChecker checker)
    {
        checker.Visit(this._definition.getStmts());
    }
}
