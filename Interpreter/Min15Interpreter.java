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
    //region Membres
    private final ClassTable _classTable = new ClassTable();

    private ClassInfo _currentClassInfo;

    private List<TId> _idList;

    private Token _operatorToken;

    private List<PExp> _expList;

    private Instance _expEVal;

    private Frame _currentFrame; //proposition de traduction => Contexte (d'exécution)

    private final Map<Class, ClassInfo> _primitiveClassInfo = new LinkedHashMap<>();
    //endregion

    //region Main Methods
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
    //endregion

    //region "Utility" Methods
    private List<TId> GetParams(PParams node)
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
        if (!arg.is_a(this._primitiveClassInfo.get(String.class)))
        {
            throw new InterpreterException("L'argument de abort n'est pas une string", this._currentFrame.GetPreviousFrame().GetCurrentLocation());
        }
        String message = "[ABORT] : " + ((StringInstance) arg).GetValue();
        throw  new InterpreterException(message, this._currentFrame.GetPreviousFrame().GetCurrentLocation());
    }

    public void IntegerToS(PrimitiveNormalMethodInfo primitiveNormalMethodInfo)
    {
        IntegerInstance self = (IntegerInstance)this._currentFrame.GetReceiver();
        this._currentFrame.SetReturnValue(((StringClassInfo)this._primitiveClassInfo.get(String.class)).NewString(self.GetValue().toString()));
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
        if(!arg.is_a(this._primitiveClassInfo.get(Integer.class)))
        {
            throw new InterpreterException("Arguement de droite n'est pas un entier", this._currentFrame.GetPreviousFrame().GetCurrentLocation());
        }

        BigInteger left = self.GetValue();
        BigInteger right = ((IntegerInstance) arg).GetValue();
        this._currentFrame.SetReturnValue(((IntegerClassInfo)this._primitiveClassInfo.get(Integer.class)).NewInteger(left.add(right)));
    }

    public void StringPlus(MethodInfo methodInfo)
    {
        StringInstance self = (StringInstance) this._currentFrame.GetReceiver();

        String argName = methodInfo.GetParamName(0);

        Instance arg = this._currentFrame.GetParameterValueWithoutId(argName);

        if(!arg.is_a(this._primitiveClassInfo.get(String.class)))
        {
            throw new InterpreterException("L'argument de droite n'est pas une chaine", this._currentFrame.GetPreviousFrame().GetCurrentLocation());
        }

        String left = self.GetValue();
        String right = ((StringInstance) arg).GetValue();

        this._currentFrame.SetReturnValue(((StringClassInfo)this._primitiveClassInfo.get(String.class)).NewString(left.concat(right)));
    }
    //endregion

    //region Overrides
    public void CheckIfTypeExists(Class klass)
    {
        this._primitiveClassInfo.put(klass, this._classTable.GetClassInfoOrNull(Object.class));
        if(this._primitiveClassInfo.get(klass) == null)
        {
            throw new InterpreterException("La classe " + klass.getName() + " n'est pas définie", null);
        }
    }

    private void HandleCompilerKnownClasses()
    {
        CheckIfTypeExists(Object.class);
        CheckIfTypeExists(Boolean.class);
        CheckIfTypeExists(Integer.class);
        CheckIfTypeExists(String.class);
    }

    @Override
    public void caseAFile(AFile node)
    {
        node.getClassDef().forEach(this::Visit);

        HandleCompilerKnownClasses();

        Instance instance = this._primitiveClassInfo.get(Object.class).NewInstance();

        this._currentFrame = new Frame(null, instance, null);

        Visit(node.getStmts());
    }

    @Override
    public void inAClassDef(AClassDef node)
    {
        this._currentClassInfo = this._classTable.Add(node);
    }

    @Override
    public void outAClassDef(AClassDef node)
    {
        this._currentClassInfo = null;
    }

    @Override
    public void caseAFieldMember(AFieldMember node)
    {
        this._currentClassInfo.GetFieldTable().Add(node);
    }

    @Override
    public void caseAMethodMember(AMethodMember node)
    {
        List<TId> params = GetParams(node.getParams());
        this._currentClassInfo.GetMethodTable().Add(node, params);
    }

    @Override
    public void caseAOperatorMember(AOperatorMember node)
    {
        List<TId> params = GetParams(node.getParams());
        Token operatorToken = GetOperatorToken(node.getOperator());
        this._currentClassInfo.GetMethodTable().Add(node, params, operatorToken);
    }

    @Override
    public void caseAInternMethodMember(AInternMethodMember node)
    {
        List<TId> params = GetParams(node.getParams());
        this._currentClassInfo.GetMethodTable().Add(node, params);
    }

    @Override
    public void caseAInternOperatorMember(AInternOperatorMember node)
    {
        List<TId> params = GetParams(node.getParams());
        Token operatorToken = GetOperatorToken(node.getOperator());
        this._currentClassInfo.GetMethodTable().Add(node, params, operatorToken);
    }

    @Override
    public void caseAParams(AParams node)
    {
        Visit(node.getParam());
    }

    @Override
    public void caseAParam(AParam node)
    {
        this._idList.add(node.getId());
    }

    @Override
    public void caseAAdditionalParam(AAdditionalParam node)
    {
        Visit(node.getParam());
    }


    //region Operators
    //Pouvoir marquer les operateurs comme tels dans la grammaire et leur ajouter une interface mettant a disposition une méthode getOperator pourrait réduire la duplication de code (caseIOperator ou casePOperator)
    @Override
    public void caseAPlusOperator(APlusOperator node)
    {
        this._operatorToken = node.getPlus();
    }

    @Override
    public void caseAEqOperator(AEqOperator node)
    {
        this._operatorToken = node.getEq();
    }

    @Override
    public void caseANeqOperator(ANeqOperator node)
    {
        this._operatorToken = node.getNeq();
    }

    @Override
    public void caseALtOperator(ALtOperator node)
    {
        this._operatorToken = node.getLt();
    }

    @Override
    public void caseAGtOperator(AGtOperator node)
    {
        this._operatorToken = node.getGt();
    }

    @Override
    public void caseALteqOperator(ALteqOperator node)
    {
        this._operatorToken = node.getLteq();
    }

    @Override
    public void caseAGteqOperator(AGteqOperator node)
    {
        this._operatorToken = node.getGteq();
    }

    @Override
    public void caseAMinusOperator(AMinusOperator node)
    {
        this._operatorToken = node.getMinus();
    }

    @Override
    public void caseAStarOperator(AStarOperator node)
    {
        this._operatorToken = node.getStar();
    }

    @Override
    public void caseASlashOperator(ASlashOperator node)
    {
        this._operatorToken = node.getSlash();
    }

    @Override
    public void caseAPercentOperator(APercentOperator node)
    {
        this._operatorToken = node.getPercent();
    }
    //endregion

    //region Assignements
    @Override
    public void caseAVarAssignStmt(AVarAssignStmt node)
    {
        Instance value = GetExpEVal(node.getExp());
        this._currentFrame.SetVar(node.getId(), value);
    }

    @Override
    public void caseAFieldAssignStmt(AFieldAssignStmt node)
    {
        Instance value = GetExpEVal(node.getExp());
        Instance self = this._currentFrame.GetReceiver();
        self.SetField(node.getFieldName(), value);
    }

    //endregion

    //region Flow Control
    private Instance CheckBooleanExpressionValidity(PStmt node)
    {
        Instance value = GetExpEVal(((AIfStmt)node).getExp());
        if (value == null)
        {
            throw new InterpreterException("L'expression est nulle", node.getEol1());
        }

        if (!value.is_a(this._primitiveClassInfo.get(Boolean.class)))
        {
            throw new InterpreterException("l'expression n'est pas booléenne", node.getEol1());
        }
        return value;
    }


    @Override
    public void caseAWhileStmt(AWhileStmt node)
    {
        while(true)
        {
            Instance value = CheckBooleanExpressionValidity(node);

            if (value == ((BooleanClassInfo)this._primitiveClassInfo.get(Boolean.class)).GetFalse())
            {
                break;
            }

            Visit(node.getStmts());
        }
    }


    @Override
    public void caseAIfStmt(AIfStmt node)
    {
        Instance value =CheckBooleanExpressionValidity(node);

        if (value == ((BooleanClassInfo)this._primitiveClassInfo.get(Boolean.class)).GetTrue())
        {
            Visit(node.getStmts());
        }
        else
        {
            Visit(node.getElsePart());
        }
    }

    @Override
    public void caseAReturnStmt(AReturnStmt node)
    {
        if (this._currentFrame.GetInvokedMethod() == null)
        {
            throw new InterpreterException("L'instruction return n'est pas autrisée dans le programme principal", node.getReturn());
        }

        PExp exp = node.getExp();
        Instance value = GetExpEVal(exp);
        this._currentFrame.SetReturnValue(value);

        throw new ReturnException();

    }


    //endregion



    //endregion
}

