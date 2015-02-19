package min15.structure;

import Interpreter.Min15Interpreter;
import min15.exceptions.InterpreterException;
import min15.node.*;

import java.util.List;

/**
 * Created by Antoine-Ali on 18/02/2015.
 */
public class OperatorMethodInfo extends MethodInfo
{
    private final AOperatorMember _definition;
    private final Token _operatorToken;

    public OperatorMethodInfo(MethodTable methodTable, AOperatorMember definition, List<TId> params, Token operatorToken)
    {
        super(methodTable, params);
        this._definition = definition;
        this._operatorToken = operatorToken;

        String operatorSymbol = "";

        if (GetName().equals("+"))
        {
            operatorSymbol = "+";
        }
        else if (GetName().equals("=="))
        {
            operatorSymbol = "==";
        }
        else
        {
            throw new RuntimeException("Opérateur non géré : " + GetName());
        }

        if (GetParamCount() != 1)
        {
            throw new InterpreterException("La méthode " + operatorSymbol + " n'a qu'un seul paramètre", operatorToken);
        }

    }

    @Override
    public String GetName() {
        return this._operatorToken.getText();
    }

    @Override
    public void Execute(Min15Interpreter interpreter)
    {
        interpreter.Visit(this._definition.getStmts());
    }
}
