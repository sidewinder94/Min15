package min15.structure;

import Interpreter.InterpreterEngine;
import min15.exceptions.InterpreterException;
import node.TId;

import java.util.*;

/**
 * Created by Antoine-Ali on 18/02/2015.
 */
public abstract class MethodInfo
{
    private final MethodTable _methodTable;

    private final List<String> _paramNames = new LinkedList<>();

    public MethodInfo(MethodTable methodTable, List<TId> params)
    {
        this._methodTable = methodTable;
        Set<String> paramsNameSet = new LinkedHashSet<>();
        for(TId id : params)
        {
            String name = id.getText();
            if (paramsNameSet.contains(name))
            {
                throw new InterpreterException("Paramètre " + name + " présent en double", id);
            }

            paramsNameSet.add(name);
        }

        this._paramNames.addAll(paramsNameSet);
    }

    public abstract String GetName();

    public String GetParamName(int i)
    {
        return this._paramNames.get(i);
    }

    public int GetParamCount()
    {
        return this._paramNames.size();
    }

    public abstract void Execute(InterpreterEngine interpreter);

    public ClassInfo GetClassInfo()
    {
        return this._methodTable.GetClassInfo();
    }
}
