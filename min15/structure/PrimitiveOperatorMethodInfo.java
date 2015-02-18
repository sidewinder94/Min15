package min15.structure;

import Interpreter.Min15Interpreter;
import min15.exceptions.InterpreterException;
import min15.node.AInternOperatorMember;
import min15.node.TId;
import min15.node.Token;

import java.util.List;

/**
 * Created by Antoine-Ali on 18/02/2015.
 */
public class PrimitiveOperatorMethodInfo extends MethodInfo
{
    private static enum Operation
    {
        INTEGER_PLUS,
        STRING_PLUS;
    }

    private final AInternOperatorMember _definition;

    private final Token _operatorToken;

    private final Operation _operation;

    PrimitiveOperatorMethodInfo(MethodTable methodTable, AInternOperatorMember definition, List<TId> params, Token operatorToken)
    {
        super(methodTable, params);
        this._definition = definition;
        this._operatorToken = operatorToken;

        String className = methodTable.GetClassInfo().GetName();
        if(GetParamCount() != 1)
        {
            throw new InterpreterException("La méthode " + GetName() + " ne prends qu'un paramètre", operatorToken);
        }


        if(GetName().equals("+"))
        {
            if (className.equals("Integer"))
            {
                this._operation = Operation.INTEGER_PLUS;
            }
            else if(className.equals("String"))
            {
                this._operation = Operation.STRING_PLUS;
            }
            else
            {
                throw new InterpreterException("La méthode + n'est pas une méthode primive dans la classe " + className, operatorToken);
            }
        }
        else if(GetName().equals("=="))
        {
            throw new InterpreterException("La méthode == n'est pas une méthode primitive dans la classe " + className, operatorToken);
        }
        else
        {
            throw new RuntimeException("Opérateur non géré " + GetName());
        }
    }

    @Override
    public String GetName() {
        return this._operatorToken.getText();
    }

    @Override
    public void Execute(Min15Interpreter interpreter)
    {
        switch(this._operation)
        {
            case INTEGER_PLUS:
                interpreter.IntegerPlus(this);
                break;
            case STRING_PLUS:
                interpreter.StringPlus(this);
                break;
            default:
                throw new RuntimeException("Cas non géré");
        }
    }
}
