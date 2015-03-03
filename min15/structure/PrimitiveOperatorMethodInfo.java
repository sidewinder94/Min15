package min15.structure;

import min15.Interpreter.InterpreterEngine;
import min15.Interpreter.SyntaxicChecker;
import min15.exceptions.InterpreterException;
import node.AInternOperatorMember;
import node.TId;
import node.Token;

import java.util.List;

/**
 * Created by Antoine-Ali on 18/02/2015.
 */
public class PrimitiveOperatorMethodInfo extends MethodInfo
{
    private static enum Operation
    {
        STRING_PLUS,
        INTEGER_PLUS,
        INTEGER_MINUS,
        INTEGER_MULT,
        INTEGER_DIV,
        INTEGER_MOD,
        INTEGER_LT,
        INTEGER_LTEQ,
        INTEGER_GT,
        INTEGER_GTEQ,
        INTEGER_EQ,
        INTEGER_NEQ;
    }

    private final AInternOperatorMember _definition;

    private final Token _operatorToken;

    private final Operation _operation;

    PrimitiveOperatorMethodInfo(MethodTable methodTable, AInternOperatorMember definition, List<TId> params, List<ClassInfo>paramsType, ClassInfo returnType,Token operatorToken)
    {
        super(methodTable, params, paramsType, returnType);
        this._definition = definition;
        this._operatorToken = operatorToken;

        String className = methodTable.GetClassInfo().GetName();
        if(GetParamCount() != 1)
        {
            throw new InterpreterException("La méthode " + GetName() + " ne prends qu'un paramètre", operatorToken);
        }


        if (className.equals("Integer"))
        {
            if(GetName().equals("+"))
            {
                this._operation = Operation.INTEGER_PLUS;
            }
            else if(GetName().equals("-"))
            {
                this._operation = Operation.INTEGER_MINUS;
            }
            else if(GetName().equals("*"))
            {
                this._operation = Operation.INTEGER_MULT;
            }
            else if(GetName().equals("/"))
            {
                this._operation = Operation.INTEGER_DIV;
            }
            else if(GetName().equals(">"))
            {
                this._operation = Operation.INTEGER_GT;
            }
            else if(GetName().equals(">="))
            {
                this._operation = Operation.INTEGER_GTEQ;
            }
            else if(GetName().equals("<"))
            {
                this._operation = Operation.INTEGER_LT;
            }
            else if(GetName().equals("<="))
            {
                this._operation = Operation.INTEGER_LTEQ;
            }
            else if(GetName().equals("=="))
            {
                this._operation = Operation.INTEGER_EQ;
            }
            else if(GetName().equals("!="))
            {
                this._operation = Operation.INTEGER_NEQ;
            }
            else
            {
                throw new InterpreterException("La méthode " + GetName() + " n'est pas une méthode primive dans la classe " + className, operatorToken);
            }
        }
        else if (className.equals("String"))
        {
            if (GetName().equals("+"))
            {
                this._operation = Operation.STRING_PLUS;
            }
            else
            {
                throw new InterpreterException("La méthode " + GetName() + " n'est pas une méthode primive dans la classe " + className, operatorToken);
            }
        }
        else
        {
            throw new InterpreterException("La méthode " + GetName() + " n'est pas une méthode primive dans la classe " + className, operatorToken);
        }
    }

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


        if (className.equals("Integer"))
        {
            if(GetName().equals("+"))
            {
                this._operation = Operation.INTEGER_PLUS;
            }
            else if(GetName().equals("-"))
            {
                this._operation = Operation.INTEGER_MINUS;
            }
            else if(GetName().equals("*"))
            {
                this._operation = Operation.INTEGER_MULT;
            }
            else if(GetName().equals("/"))
            {
                this._operation = Operation.INTEGER_DIV;
            }
            else if(GetName().equals(">"))
            {
                this._operation = Operation.INTEGER_GT;
            }
            else if(GetName().equals(">="))
            {
                this._operation = Operation.INTEGER_GTEQ;
            }
            else if(GetName().equals("<"))
            {
                this._operation = Operation.INTEGER_LT;
            }
            else if(GetName().equals("<="))
            {
                this._operation = Operation.INTEGER_LTEQ;
            }
            else if(GetName().equals("=="))
            {
                this._operation = Operation.INTEGER_EQ;
            }
            else if(GetName().equals("!="))
            {
                this._operation = Operation.INTEGER_NEQ;
            }
            else
            {
                throw new InterpreterException("La méthode " + GetName() + " n'est pas une méthode primive dans la classe " + className, operatorToken);
            }
        }
        else if (className.equals("String"))
        {
            if (GetName().equals("+"))
            {
                this._operation = Operation.STRING_PLUS;
            }
            else
            {
                throw new InterpreterException("La méthode " + GetName() + " n'est pas une méthode primive dans la classe " + className, operatorToken);
            }
        }
        else
        {
            throw new InterpreterException("La méthode " + GetName() + " n'est pas une méthode primive dans la classe " + className, operatorToken);
        }
    }

    @Override
    public String GetName() {
        return this._operatorToken.getText();
    }

    @Override
    public void Execute(InterpreterEngine interpreter)
    {
        switch(this._operation)
        {
            case INTEGER_PLUS:
                interpreter.IntegerPlus(this);
                break;
            case INTEGER_MINUS:
                interpreter.IntegerMinus(this);
                break;
            case INTEGER_MULT:
                interpreter.IntegerMult(this);
                break;
            case INTEGER_DIV:
                interpreter.IntegerDiv(this);
                break;
            case INTEGER_MOD:
                interpreter.IntegerMod(this);
                break;
            case INTEGER_LT:
                interpreter.IntegerLt(this);
                break;
            case INTEGER_LTEQ:
                interpreter.IntegerLtEq(this);
                break;
            case INTEGER_GT:
                interpreter.IntegerGt(this);
                break;
            case INTEGER_GTEQ:
                interpreter.IntegerGtEq(this);
                break;
            case INTEGER_EQ:
                interpreter.IntegerEq(this);
                break;
            case INTEGER_NEQ:
                interpreter.IntegerNeq(this);
                break;
            case STRING_PLUS:
                interpreter.StringPlus(this);
                break;
            default:
                throw new RuntimeException("Cas non géré");
        }
    }

    @Override
    public void Execute(SyntaxicChecker interpreter)
    {
        switch(this._operation)
        {
            case INTEGER_PLUS:
                interpreter.IntegerPlus(this);
                break;
            case INTEGER_MINUS:
                interpreter.IntegerMinus(this);
                break;
            case INTEGER_MULT:
                interpreter.IntegerMult(this);
                break;
            case INTEGER_DIV:
                interpreter.IntegerDiv(this);
                break;
            case INTEGER_MOD:
                interpreter.IntegerMod(this);
                break;
            case INTEGER_LT:
                interpreter.IntegerLt(this);
                break;
            case INTEGER_LTEQ:
                interpreter.IntegerLtEq(this);
                break;
            case INTEGER_GT:
                interpreter.IntegerGt(this);
                break;
            case INTEGER_GTEQ:
                interpreter.IntegerGtEq(this);
                break;
            case INTEGER_EQ:
                interpreter.IntegerEq(this);
                break;
            case INTEGER_NEQ:
                interpreter.IntegerNeq(this);
                break;
            case STRING_PLUS:
                interpreter.StringPlus(this);
                break;
            default:
                throw new RuntimeException("Cas non géré");
        }
    }
}
