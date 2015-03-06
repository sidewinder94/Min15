package min15.structure;


import min15.Interpreter.SyntaxicChecker;
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

    private final List<ClassInfo> _paramTypes = new LinkedList<>();

    private final ClassInfo _returnType;

    public MethodInfo(MethodTable methodTable, List<TId> params, List<ClassInfo> paramsType, ClassInfo returnType)
    {
        this._methodTable = methodTable;
        Set<String> paramsNameSet = new LinkedHashSet<>();
        for(int i = 0; i < params.size(); i++)
        {
            String name = params.get(i).getText();
            if (paramsNameSet.contains(name))
            {
                throw new InterpreterException("Paramètre " + name + " présent en double", params.get(i));
            }

            _paramTypes.add(paramsType.get(i));
            paramsNameSet.add(name);
        }
        this._paramNames.addAll(paramsNameSet);
        this._returnType = returnType;
    }

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
        this._returnType = null;
    }

    public abstract String GetName();

    public String GetParamName(int i)
    {
        return this._paramNames.get(i);
    }
    public ClassInfo GetParamType(int i)
    {
        return this._paramTypes.get(i);
    }

    public ClassInfo GetReturnType(){return this._returnType;}
    public int GetParamCount()
    {
        return this._paramNames.size();
    }

    public abstract void Execute(SyntaxicChecker checker);

    public ClassInfo GetClassInfo()
    {
        return this._methodTable.GetClassInfo();
    }
}
