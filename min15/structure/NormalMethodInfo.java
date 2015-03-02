package min15.structure;

import min15.Interpreter.InterpreterEngine;
import node.AMethodMember;
import node.TId;

import java.util.List;

/**
 * Created by Antoine-Ali on 18/02/2015.
 */
public class NormalMethodInfo extends MethodInfo
{
    private final AMethodMember _definition;

    public NormalMethodInfo(MethodTable methodTable, AMethodMember definition, List<TId> params)
    {
        super(methodTable, params);
        this._definition = definition;
    }

    @Override
    public String GetName()
    {
        return this._definition.getId().getText();
    }

    @Override
    public void Execute(InterpreterEngine interpreter)
    {
        interpreter.Visit(this._definition.getStmts());
    }
}
