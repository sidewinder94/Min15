package min15.structure;

import min15.Interpreter.InterpreterEngine;
import min15.exceptions.InterpreterException;
import node.AInternMethodMember;
import node.TId;

import java.util.List;

/**
 * Created by Antoine-Ali on 18/02/2015.
 */
public class PrimitiveNormalMethodInfo extends MethodInfo
{
    private static enum Operation
    {
        OBJECT_ABORT,
        INTEGER_TO_S,
        STRING_TO_SYSTEM_OUT;
    }

    private final AInternMethodMember _definition;

    private final Operation _operation;


    public PrimitiveNormalMethodInfo(MethodTable methodTable, AInternMethodMember definition, List<TId> params)
    {
        super(methodTable, params);
        this._definition = definition;

        String className = methodTable.GetClassInfo().GetName();
        if(className.equals("Object") && GetName().equals("abort"))
        {
            if(params.size() != 1)
            {
                throw new InterpreterException("La méthode abort a un paramètre", definition.getId());
            }
            this._operation = Operation.OBJECT_ABORT;
        }
        else if(className.equals("Integer") && GetName().equals("toString"))
        {
            if(params.size() != 0)
            {
                throw new InterpreterException("La méthode to_s ne prends pas de paramètres", definition.getId());
            }
            this._operation = Operation.INTEGER_TO_S;
        }
        else if(className.equals("String") && GetName().equals("toSystemOut"))
        {
            if(params.size() != 0)
            {
                throw new InterpreterException("La méthode to_system_out ne prends pas de paramètres", definition.getId());
            }
                this._operation = Operation.STRING_TO_SYSTEM_OUT;
        }
        else
        {
            throw new InterpreterException("La méthode " + GetName() + " n'est pas une primitive dans la classe " + className, definition.getId());
        }
    }

    @Override
    public String GetName() {
        return this._definition.getId().getText();
    }

    @Override
    public void Execute(InterpreterEngine interpreter)
    {
        switch(this._operation)
        {
            case OBJECT_ABORT:
                interpreter.ObjectAbort(this);
                break;
            case INTEGER_TO_S:
                interpreter.IntegerToS(this);
                break;
            case STRING_TO_SYSTEM_OUT:
                interpreter.StringToSystemOut(this);
                break;
            default:
                throw new RuntimeException("Cas non défini");
        }
    }
}
