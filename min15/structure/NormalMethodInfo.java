package min15.structure;

import Interpreter.Min15Interpreter;
import min15.node.AMethodMember;
import min15.node.PParam;
import min15.node.TId;

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
    public void Execute(Min15Interpreter interpreter)
    {
        interpreter.Visit(this._definition.getStmts());
    }
}
