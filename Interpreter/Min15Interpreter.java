/**
 * Created by Antoine-Ali on 18/02/2015.
 */
package Interpreter;

import java.math.BigInteger;
import java.util.*;

import min15.exceptions.InterpreterException;
import min15.exceptions.ReturnException;
import min15.node.*;
import min15.structure.*;
import min15.analysis.DepthFirstAdapter;


public class Min15Interpreter extends DepthFirstAdapter
{
    private final ClassTable _classTable = new ClassTable();

    private ClassInfo _currentClassInfo;

    private List<TId> _idList;

    private Token _operatorToken;

    private List<PExp> _expList;

    private Instance _expEVal;

    private Frame _currentFrame; //proposition de traduction => Contexte (d'exécution)

    private ClassInfo _objectClassInfo;

    private BooleanClassInfo _booleanClassInfo;

    private IntegerClassInfo _integerClassInfo;

    private StringClassInfo _stringClassInfo;

    public void Visit(Node node)
    {
        if (node != null)
        {
            node.apply(this);
        }
    }

    public void PrintStackTrace()
    {
        Frame frame = this._currentFrame;
        while (frame != null) {
            Token locationToken = frame.GetCurrentLocation();
            String location = "";

            if (locationToken != null) {
                location = " ligne " + locationToken.getLine() + " position " + locationToken.getPos();
            }

            MethodInfo invokedMethod = frame.GetInvokedMethod();
            if (invokedMethod != null) {
                System.err.println(" dans " + invokedMethod.GetClassInfo().GetName() + "." + invokedMethod.GetName() + "()" + location);
            } else {
                System.err.println(" dans le programme principal" + location);
            }
            frame = frame.GetPreviousFrame();
        }
    }

    private List<TId> GetParams(AArgs node)
    {
        this._idList = new LinkedList<>();
        Visit(node);
        List<TId> idList = this._idList;
        this._idList = null;
        return idList;
    }

    private Token GetOperatorToken(POperator node)
    {
        Visit(node);
        Token operatorToken = this._operatorToken;
        this._operatorToken = null;
        return operatorToken;
    }

    private Instance GetExpEVal(Node node)
    {
        Visit(node);
        Instance expEval = this._expEVal;
        this._expEVal = null;
        return expEval;
    }

    private List<PExp> GetExpList(AArgs node)
    {
        this._expList = new LinkedList<>();
        Visit(node);
        List<PExp> expList = this._expList;
        this._expList = null;
        return expList;
    }

    private Instance Execute(MethodInfo invokedMethod,
                             Frame frame,
                             Token location)
    {
        this._currentFrame.SetCurrentLocation(location);
        this._currentFrame = frame;
        try
        {
            invokedMethod.Execute(this);
        }
        catch(ReturnException e){}

        this._currentFrame = frame.GetPreviousFrame();
        this._currentFrame.SetCurrentLocation(null);
        return frame.GetReturnValue();
    }

    public void ObjectAbort(MethodInfo methodInfo)
    {
        String argName = methodInfo.GetParamName(0);
        Instance arg = this._currentFrame.GetParameterValueWithoutId(argName);
        if (arg == null)
        {
            throw new InterpreterException("L'argument de abort est null", this._currentFrame.GetPreviousFrame().GetCurrentLocation());
        }
        if (!arg.is_a(this._stringClassInfo))
        {
            throw new InterpreterException("L'argument de abort n'est pas une string", this._currentFrame.GetPreviousFrame().GetCurrentLocation());
        }
        String message = "[ABORT] : " + ((StringInstance) arg).GetValue();
        throw  new InterpreterException(message, this._currentFrame.GetPreviousFrame().GetCurrentLocation());
    }

    public void IntegerToS(PrimitiveNormalMethodInfo primitiveNormalMethodInfo)
    {
        IntegerInstance self = (IntegerInstance)this._currentFrame.GetReceiver();
        this._currentFrame.SetReturnValue(this._stringClassInfo.NewString(self.GetValue().toString()));
    }

    public void StringToSystemOut(PrimitiveNormalMethodInfo primitiveNormalMethodInfo)
    {
        StringInstance self = (StringInstance) this._currentFrame.GetReceiver();
        System.out.println(self.GetValue());
    }

    public void IntegerPlus(MethodInfo methodInfo)
    {
        IntegerInstance self = (IntegerInstance) this._currentFrame.GetReceiver();
        String argName = methodInfo.GetParamName(0);
        Instance arg = this._currentFrame.GetParameterValueWithoutId(argName);
        if(!arg.is_a(this._integerClassInfo))
        {
            throw new InterpreterException("Arguement de droite n'est pas un entier", this._currentFrame.GetPreviousFrame().GetCurrentLocation());
        }

        BigInteger left = self.GetValue();
        BigInteger right = ((IntegerInstance) arg).GetValue();
        this._currentFrame.SetReturnValue(this._integerClassInfo.NewInteger(left.add(right)));
    }

    public void StringPlus(MethodInfo methodInfo)
    {
        StringInstance self = (StringInstance) this._currentFrame.GetReceiver();

        String argName = methodInfo.GetParamName(0);

        Instance arg = this._currentFrame.GetParameterValueWithoutId(argName);

        if(!arg.is_a(this._stringClassInfo))
        {
            throw new InterpreterException("L'argument de droite n'est pas une chaine", this._currentFrame.GetPreviousFrame().GetCurrentLocation());
        }

        String left = self.GetValue();
        String right = ((StringInstance) arg).GetValue();

        this._currentFrame.SetReturnValue(this._stringClassInfo.NewString(left.concat(right)));
    }

}

