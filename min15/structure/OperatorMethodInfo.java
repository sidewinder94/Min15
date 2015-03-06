package min15.structure;


import min15.Interpreter.SyntaxicChecker;
import min15.exceptions.InterpreterException;
import node.*;

import java.util.List;

/**
 * Created by Antoine-Ali on 18/02/2015.
 */
public class OperatorMethodInfo extends MethodInfo
{
    private final AOperatorMember _definition;
    private final Token _operatorToken;
    public static final String[] _operators = {"+", "-", "/", "*", "%", "<", "<=", ">", ">=", "==", "!="};

    public OperatorMethodInfo(MethodTable methodTable, AOperatorMember definition, List<TId> params, List<ClassInfo> paramTypes, ClassInfo returnType, Token operatorToken)
    {

        super(methodTable, params, paramTypes, returnType);
        this._definition = definition;
        this._operatorToken = operatorToken;

        String operatorSymbol = "";

        if (OperatorSuported(GetName()))
        {
            operatorSymbol = GetName();
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

    public OperatorMethodInfo(MethodTable methodTable, AOperatorMember definition, List<TId> params, Token operatorToken)
    {

        super(methodTable, params);
        this._definition = definition;
        this._operatorToken = operatorToken;

        String operatorSymbol = "";

        if (OperatorSuported(GetName()))
        {
            operatorSymbol = GetName();
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

    private Boolean OperatorSuported(String operator)
    {
        Boolean found = false;
        for (String s : _operators)
        {
            if (s.equals(operator))
            {
                found = true;
            }
        }
        return found;
    }

    @Override
    public String GetName() {
        return this._operatorToken.getText();
    }


    @Override
    public void Execute(SyntaxicChecker checker)
    {
        checker.Visit(this._definition.getStmts());
    }
}
