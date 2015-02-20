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
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


public class Min15Interpreter extends DepthFirstAdapter
{
    //region Membres
    private final ClassTable _classTable = new ClassTable();

    private ClassInfo _currentClassInfo;

    private List<TId> _idList;

    private Token _operatorToken;

    private List<PExp> _expList;

    private Instance _expEval;

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
    private <T extends ClassInfo> T GetPrimitiveTypeClassInfo(Class klass)
    {
        return (T)this._primitiveClassInfo.get(klass);
    }


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

    private Instance GetExpEval(Node node)
    {
        Visit(node);
        Instance expEval = this._expEval;
        this._expEval = null;
        return expEval;
    }

    private List<PExp> GetExpList(PArgs node)
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
        this._currentFrame.SetReturnValue((this.<StringClassInfo>GetPrimitiveTypeClassInfo(String.class)).NewString(self.GetValue().toString()));
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
        this._currentFrame.SetReturnValue((this.<IntegerClassInfo>GetPrimitiveTypeClassInfo(Integer.class)).NewInteger(left.add(right)));
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

        this._currentFrame.SetReturnValue((this.<StringClassInfo>GetPrimitiveTypeClassInfo(String.class)).NewString(left.concat(right)));
    }

    private void CheckIfTypeExists(Class klass)
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

    //endregion

    //region Overrides

    //region Root Node (File)

    @Override
    public void caseAFile(AFile node)
    {
        node.getClassDef().forEach(this::Visit);

        HandleCompilerKnownClasses();

        Instance instance = this._primitiveClassInfo.get(Object.class).NewInstance();

        this._currentFrame = new Frame(null, instance, null);

        Visit(node.getStmts());
    }

    //endregion

    //region Class Definition

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

    //endregion

    //region Super Declaration

    @Override
    public void caseASuperDecl(ASuperDecl node)
    {
        //No need for definition, node is already checked in while creating class
    }

    //endregion

    //region Member

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

    //endregion

    //region Parameters

    @Override
    public void caseAParams(AParams node)
    {
        Visit(node.getParam());
    }

    //endregion

    //region Additional Parameters

    @Override
    public void caseAAdditionalParam(AAdditionalParam node)
    {
        Visit(node.getParam());
    }

    //endregion

    //region Parameter

    @Override
    public void caseAParam(AParam node)
    {
        this._idList.add(node.getId());
        //TODO : Complete for type safety
    }

    //endregion

    //region Return Declaration

    @Override
    public void caseAReturnDecl(AReturnDecl node)
    {
        //TODO : Implement for type safety
    }

    //endregion

    //region Operators
    //Pouvoir marquer les operateurs comme tels dans la grammaire et leur ajouter une interface mettant a disposition une méthode getOperator pourrait réduire la duplication de code (caseIOperator ou casePOperator)

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
    public void caseAPlusOperator(APlusOperator node)
    {
        this._operatorToken = node.getPlus();
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

    //region Statements
    @Override
    public void caseAStmts(AStmts node)
    {
        node.getStmt().forEach(this::Visit);
    }
    //endregion

    //region Statement

    private Instance CheckBooleanExpressionValidity(PStmt node)
    {
        Boolean ifStatement = node instanceof AIfStmt;
        Instance value = GetExpEval(ifStatement ? ((AIfStmt) node).getExp() : ((AWhileStmt) node).getExp());
        if (value == null)
        {
            throw new InterpreterException("L'expression est nulle", ifStatement ? ((AIfStmt) node).getEol1() : ((AWhileStmt) node).getEol1());
        }

        if (!value.is_a(this._primitiveClassInfo.get(Boolean.class)))
        {
            throw new InterpreterException("l'expression n'est pas booléenne", ifStatement ? ((AIfStmt) node).getEol1() : ((AWhileStmt) node).getEol1());
        }
        return value;
    }


    @Override
    public void caseAWhileStmt(AWhileStmt node)
    {
        while(true)
        {
            Instance value = CheckBooleanExpressionValidity(node);

            if (value == (this.<BooleanClassInfo>GetPrimitiveTypeClassInfo(Boolean.class)).GetFalse())
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

        if (value == (this.<BooleanClassInfo>GetPrimitiveTypeClassInfo(Boolean.class)).GetTrue())
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
        Instance value = GetExpEval(exp);
        this._currentFrame.SetReturnValue(value);

        throw new ReturnException();

    }


    @Override
    public void caseACallStmt(ACallStmt node)
    {
        GetExpEval(node.getCall());
    }

    @Override
    public void caseASelfCallStmt(ASelfCallStmt node)
    {
        GetExpEval(node.getSelfCall());
    }

    @Override
    public void caseAVarAssignStmt(AVarAssignStmt node)
    {
        Instance value = GetExpEval(node.getExp());
        this._currentFrame.SetVar(node.getId(), value);
    }

    @Override
    public void caseAFieldAssignStmt(AFieldAssignStmt node)
    {
        Instance value = GetExpEval(node.getExp());
        Instance self = this._currentFrame.GetReceiver();
        self.SetField(node.getFieldName(), value);
    }



    @Override
    public void caseAEmptyStmt(AEmptyStmt node)
    {}

    @Override
    public void caseAVarDefStmt(AVarDefStmt node)
    {
        super.caseAVarDefStmt(node);
    }

    @Override
    public void caseAVarInitStmt(AVarInitStmt node)
    {
        super.caseAVarInitStmt(node);
    }


    //endregion

    //region Else Part

    @Override
    public void caseAElsePart(AElsePart node)
    {
        Visit(node.getStmts());
    }

    //endregion

    //region Expression

    @Override
    public void caseAOrExp(AOrExp node)
    {
        BooleanClassInfo info = this.<BooleanClassInfo>GetPrimitiveTypeClassInfo(Boolean.class);
        Instance left = GetExpEval(node.getExp());
        Instance right = GetExpEval(node.getConjunction());

        if (left.is_a(info))
        {
            throw new InterpreterException("Le membre gauche de l'instruction AND n'est pas un booléen", node.getOr());
        }
        if (right.is_a(info))
        {
            throw new InterpreterException("Le membre gauche de l'instruction AND n'est pas un booléen", node.getOr());
        }

        if ((left == info.GetTrue()) || (right == info.GetTrue()))
        {
            this._expEval = info.GetTrue();
        }
        else
        {
            this._expEval = info.GetFalse();
        }
    }

    @Override
    public void caseASimpleExp(ASimpleExp node)
    {
        Visit(node.getConjunction());
    }


    //endregion

    //region Conjunction

    @Override
    public void caseAAndConjunction(AAndConjunction node)
    {
        BooleanClassInfo info = this.<BooleanClassInfo>GetPrimitiveTypeClassInfo(Boolean.class);
        Instance left = GetExpEval(node.getConjunction());
        Instance right = GetExpEval(node.getComparison());

        if (left.is_a(info))
        {
            throw new InterpreterException("Le membre gauche de l'instruction AND n'est pas un booléen", node.getAnd());
        }
        if (right.is_a(info))
        {
            throw new InterpreterException("Le membre gauche de l'instruction AND n'est pas un booléen", node.getAnd());
        }

        if (left == right)
        {
            this._expEval = info.GetTrue();
        }
        else
        {
            this._expEval = info.GetFalse();
        }

    }

    @Override
    public void caseASimpleConjunction(ASimpleConjunction node)
    {
        Visit(node.getComparison());
    }

    //endregion

    //region Comparison Expressions

    @Override
    public void caseAIsComparison(AIsComparison node)
    {
        BooleanClassInfo info = this.<BooleanClassInfo>GetPrimitiveTypeClassInfo(Boolean.class);
        Instance left = GetExpEval(node.getComparison());
        Instance right = GetExpEval(node.getArithExp());
        if (left == right)
        {
            this._expEval = info.GetTrue();
        }
        else
        {
            this._expEval = info.GetFalse();
        }
    }

    @Override
    public void caseAEqComparison(AEqComparison node)
    {
        BooleanClassInfo info = this.<BooleanClassInfo>GetPrimitiveTypeClassInfo(Boolean.class);
        Instance left = GetExpEval(node.getComparison());
        Instance right = GetExpEval(node.getArithExp());
        if (left == null || right == null)
        {
            if(left == right)
            {
                this._expEval = info.GetTrue();
            }
            else
            {
                this._expEval = info.GetFalse();
            }
        }
        else
        {
            MethodInfo invokedMethod = left.GetClassInfo().GetMethodTable().GetMethodInfo(node.getEq());
            Frame frame = new Frame(this._currentFrame, left, invokedMethod);
            frame.SetParam(right);
            this._expEval = Execute(invokedMethod, frame, node.getEq());
        }
    }

    @Override
    public void caseANeqComparison(ANeqComparison node)
    {
        BooleanClassInfo info = this.<BooleanClassInfo>GetPrimitiveTypeClassInfo(Boolean.class);
        Instance left = GetExpEval(node.getComparison());
        Instance right = GetExpEval(node.getArithExp());
        if (left == null || right == null)
        {
            if(left != right)
            {
                this._expEval = info.GetTrue();
            }
            else
            {
                this._expEval = info.GetFalse();
            }
        }
        else
        {
            MethodInfo invokedMethod = left.GetClassInfo().GetMethodTable().GetMethodInfo(node.getNeq());
            Frame frame = new Frame(this._currentFrame, left, invokedMethod);
            frame.SetParam(right);
            this._expEval = Execute(invokedMethod, frame, node.getNeq());
        }
    }

    @Override
    public void caseALtComparison(ALtComparison node)
    {
        BooleanClassInfo info = this.<BooleanClassInfo>GetPrimitiveTypeClassInfo(Boolean.class);
        Instance left = GetExpEval(node.getComparison());
        Instance right = GetExpEval(node.getArithExp());
        if (left == null || right == null)
        {
            this._expEval = info.GetFalse();
        }
        else
        {
            MethodInfo invokedMethod = left.GetClassInfo().GetMethodTable().GetMethodInfo(node.getLt());
            Frame frame = new Frame(this._currentFrame, left, invokedMethod);
            frame.SetParam(right);
            this._expEval = Execute(invokedMethod, frame, node.getLt());
        }
    }

    @Override
    public void caseAGtComparison(AGtComparison node)
    {
        BooleanClassInfo info = this.<BooleanClassInfo>GetPrimitiveTypeClassInfo(Boolean.class);
        Instance left = GetExpEval(node.getComparison());
        Instance right = GetExpEval(node.getArithExp());
        if (left == null || right == null)
        {
            this._expEval = info.GetFalse();
        }
        else
        {
            MethodInfo invokedMethod = left.GetClassInfo().GetMethodTable().GetMethodInfo(node.getGt());
            Frame frame = new Frame(this._currentFrame, left, invokedMethod);
            frame.SetParam(right);
            this._expEval = Execute(invokedMethod, frame, node.getGt());
        }
    }

    @Override
    public void caseALteqComparison(ALteqComparison node)
    {
        BooleanClassInfo info = this.<BooleanClassInfo>GetPrimitiveTypeClassInfo(Boolean.class);
        Instance left = GetExpEval(node.getComparison());
        Instance right = GetExpEval(node.getArithExp());
        if (left == null || right == null)
        {
            if(left == right)
            {
                this._expEval = info.GetTrue();
            }
            else
            {
                this._expEval = info.GetFalse();
            }
        }
        else
        {
            MethodInfo invokedMethod = left.GetClassInfo().GetMethodTable().GetMethodInfo(node.getLteq());
            Frame frame = new Frame(this._currentFrame, left, invokedMethod);
            frame.SetParam(right);
            this._expEval = Execute(invokedMethod, frame, node.getLteq());
        }
    }

    @Override
    public void caseAGteqComparison(AGteqComparison node)
    {
        BooleanClassInfo info = this.<BooleanClassInfo>GetPrimitiveTypeClassInfo(Boolean.class);
        Instance left = GetExpEval(node.getComparison());
        Instance right = GetExpEval(node.getArithExp());
        if (left == null || right == null)
        {
            if(left == right)
            {
                this._expEval = info.GetTrue();
            }
            else
            {
                this._expEval = info.GetFalse();
            }
        }
        else
        {
            MethodInfo invokedMethod = left.GetClassInfo().GetMethodTable().GetMethodInfo(node.getGteq());
            Frame frame = new Frame(this._currentFrame, left, invokedMethod);
            frame.SetParam(right);
            this._expEval = Execute(invokedMethod, frame, node.getGteq());
        }
    }

    @Override
    public void caseASimpleComparison(ASimpleComparison node)
    {
        this._expEval = GetExpEval(node.getArithExp());
    }

    //endregion

    //region Arithmetic Expressions

    @Override
    public void caseAAddArithExp(AAddArithExp node)
    {
        Instance left = GetExpEval(node.getArithExp());
        Instance right = GetExpEval(node.getFactor());

        if (left == null || right == null)
        {
            String side = "gauche";
            if (right == null)
            {
                side = "droite";
            }

            throw new InterpreterException("L'argument de " + side + " de la méthode + est nul", node.getPlus());
        }
        else
        {
            MethodInfo invokedMethod = left.GetClassInfo().GetMethodTable().GetMethodInfo(node.getPlus());
            Frame frame = new Frame(this._currentFrame, left, invokedMethod);
            frame.SetParam(right);
            this._expEval = Execute(invokedMethod, frame, node.getPlus());
        }
    }

    @Override
    public void caseASubArithExp(ASubArithExp node)
    {
        Instance left = GetExpEval(node.getArithExp());
        Instance right = GetExpEval(node.getFactor());

        if (left == null || right == null)
        {
            String side = "gauche";
            if (right == null)
            {
                side = "droite";
            }

            throw new InterpreterException("L'argument de " + side + " de la méthode - est nul", node.getMinus());
        }
        else
        {
            MethodInfo invokedMethod = left.GetClassInfo().GetMethodTable().GetMethodInfo(node.getMinus());
            Frame frame = new Frame(this._currentFrame, left, invokedMethod);
            frame.SetParam(right);
            this._expEval = Execute(invokedMethod, frame, node.getMinus());
        }
    }

    @Override
    public void caseASimpleArithExp(ASimpleArithExp node)
    {
        Visit(node.getFactor());
    }


    //endregion

    //region Factors

    @Override
    public void caseAMulFactor(AMulFactor node)
    {
        Instance left = GetExpEval(node.getRightUnaryExp());
        Instance right = GetExpEval(node.getFactor());

        if (left == null || right == null)
        {
            String side = "gauche";
            if (right == null)
            {
                side = "droite";
            }

            throw new InterpreterException("L'argument de " + side + " de la méthode + est nul", node.getStar());
        }
        else
        {
            MethodInfo invokedMethod = left.GetClassInfo().GetMethodTable().GetMethodInfo(node.getStar());
            Frame frame = new Frame(this._currentFrame, left, invokedMethod);
            frame.SetParam(right);
            this._expEval = Execute(invokedMethod, frame, node.getStar());
        }
    }

    @Override
    public void caseADivFactor(ADivFactor node)
    {
        Instance left = GetExpEval(node.getRightUnaryExp());
        Instance right = GetExpEval(node.getFactor());

        if (left == null || right == null)
        {
            String side = "gauche";
            if (right == null)
            {
                side = "droite";
            }

            throw new InterpreterException("L'argument de " + side + " de la méthode + est nul", node.getSlash());
        }
        else
        {
            MethodInfo invokedMethod = left.GetClassInfo().GetMethodTable().GetMethodInfo(node.getSlash());
            Frame frame = new Frame(this._currentFrame, left, invokedMethod);
            frame.SetParam(right);
            this._expEval = Execute(invokedMethod, frame, node.getSlash());
        }
    }

    @Override
    public void caseAModFactor(AModFactor node)
    {
        Instance left = GetExpEval(node.getRightUnaryExp());
        Instance right = GetExpEval(node.getFactor());

        if (left == null || right == null)
        {
            String side = "gauche";
            if (right == null)
            {
                side = "droite";
            }

            throw new InterpreterException("L'argument de " + side + " de la méthode + est nul", node.getPercent());
        }
        else
        {
            MethodInfo invokedMethod = left.GetClassInfo().GetMethodTable().GetMethodInfo(node.getPercent());
            Frame frame = new Frame(this._currentFrame, left, invokedMethod);
            frame.SetParam(right);
            this._expEval = Execute(invokedMethod, frame, node.getPercent());
        }
    }

    @Override
    public void caseASimpleFactor(ASimpleFactor node)
    {
        Visit(node.getRightUnaryExp());
    }


    //endregion

    //region Right Unary Expressions

    @Override
    public void caseACallRightUnaryExp(ACallRightUnaryExp node)
    {
        Visit(node.getCall());
    }

    @Override
    public void caseAIsaRightUnaryExp(AIsaRightUnaryExp node)
    {
        BooleanClassInfo info = this.<BooleanClassInfo>GetPrimitiveTypeClassInfo(Boolean.class);
        Instance left = GetExpEval(node.getRightUnaryExp());
        ClassInfo right = this._classTable.Get(node.getClassName());

        if (left == null)
        {
            this._expEval = info.GetFalse();
        }
        else if (left.is_a(right))
        {
            this._expEval = info.GetTrue();
        }
        else
        {
            this._expEval = info.GetFalse();
        }
    }

    @Override
    public void caseAAsRightUnaryExp(AAsRightUnaryExp node)
    {
        //TODO : Implémenter
        throw new NotImplementedException();
    }

    @Override
    public void caseASimpleRightUnaryExp(ASimpleRightUnaryExp node)
    {
        Visit(node.getTerm());
    }


    //endregion

    //region Left Unary Expressions

    @Override
    public void caseANotLeftUnaryExp(ANotLeftUnaryExp node)
    {
        Instance value = GetExpEval(node.getLeftUnaryExp());
        if(value == null)
        {
            throw new InterpreterException("expression is null", node.getNot());
        }
    }

    @Override
    public void caseANegLeftUnaryExp(ANegLeftUnaryExp node)
    {
        Instance value = GetExpEval(node.getLeftUnaryExp());
        if(!(value.is_a(this._primitiveClassInfo.get(Integer.class))))
        {
            throw new InterpreterException("IL est seulement possible d'avoir un entien négatif", node.getMinus());
        }

        (this.<IntegerClassInfo>GetPrimitiveTypeClassInfo(Integer.class))
                .NewInteger(((IntegerInstance) value).GetValue().multiply(new BigInteger("-1")));
    }

    @Override
    public void caseASimpleLeftUnaryExp(ASimpleLeftUnaryExp node)
    {
        this._expEval = GetExpEval(node.getRightUnaryExp());
    }


    //endregion

    //region Terms

    @Override
    public void caseASelfCallTerm(ASelfCallTerm node)
    {
        super.caseASelfCallTerm(node);
    }

    @Override
    public void caseAParTerm(AParTerm node)
    {
        super.caseAParTerm(node);
    }

    @Override
    public void caseANewTerm(ANewTerm node)
    {
        ClassInfo classInfo = this._classTable.Get(node.getClassName());

        String name = classInfo.GetName();
        if (name.equals("Boolean") ||
            name.equals("Integer") ||
            name.equals("String"))
        {
            throw new InterpreterException("Utilisation invalide de l'opérateur new", node.getNew());
        }

        this._expEval = classInfo.NewInstance();
    }

    @Override
    public void caseAFieldTerm(AFieldTerm node)
    {
        Instance self = this._currentFrame.GetReceiver();
        this._expEval = self.GetField(node.getFieldName());
    }

    @Override
    public void caseAVarTerm(AVarTerm node)
    {
        this._expEval = this._currentFrame.GetVar(node.getId());
    }

    @Override
    public void caseANumTerm(ANumTerm node)
    {
        this._expEval = (this.<IntegerClassInfo>GetPrimitiveTypeClassInfo(Integer.class))
                .NewInteger(new BigInteger(node.getNumber().getText()));
    }

    @Override
    public void caseANullTerm(ANullTerm node)
    {
        this._expEval = null;
    }

    @Override
    public void caseASelfTerm(ASelfTerm node)
    {
        this._expEval = this._currentFrame.GetReceiver();
    }

    @Override
    public void caseATrueTerm(ATrueTerm node)
    {
        this._expEval = this.<BooleanClassInfo>GetPrimitiveTypeClassInfo(Boolean.class).GetTrue();
    }

    @Override
    public void caseAFalseTerm(AFalseTerm node)
    {
        this._expEval = this.<BooleanClassInfo>GetPrimitiveTypeClassInfo(Boolean.class).GetFalse();
    }

    @Override
    public void caseAStringTerm(AStringTerm node)
    {
        String string = node.getString().getText();
        this._expEval = this.<StringClassInfo>GetPrimitiveTypeClassInfo(String.class)
                .NewString(string.substring(1, string.length() - 1));
    }


    //endregion

    //region Calls

    @Override
    public void caseACall(ACall node)
    {
        List<PExp> expList = GetExpList(node.getArgs());

        Instance receiver = GetExpEval(node.getRightUnaryExp());

        if (receiver == null)
        {
            throw new InterpreterException("La méthode réceptionnant " + node.getId().getText() + " est nulle",
                    node.getId());
        }

        MethodInfo invokedMethod = receiver.GetClassInfo().GetMethodTable().GetMethodInfo(node.getId());

        if(invokedMethod.GetParamCount() != expList.size())
        {
            throw new InterpreterException("La méthode " + invokedMethod.GetName() +
                                           "attends " + invokedMethod.GetParamCount() +
                                           " arguments", node.getId());
        }

        Frame frame = new Frame(this._currentFrame, receiver, invokedMethod);

        for(PExp exp : expList)
        {
            frame.SetParam(GetExpEval(exp));
        }

        this._expEval = Execute(invokedMethod, frame, node.getId());
    }

    @Override
    public void caseASelfCall(ASelfCall node)
    {
        List<PExp> expList = GetExpList(node.getArgs());

        Instance receiver = this._currentFrame.GetReceiver();

        if (receiver == null)
        {
            throw new InterpreterException("La méthode réceptionnant " + node.getId().getText() + " est nulle",
                    node.getId());
        }

        MethodInfo invokedMethod = receiver.GetClassInfo().GetMethodTable().GetMethodInfo(node.getId());

        if(invokedMethod.GetParamCount() != expList.size())
        {
            throw new InterpreterException("La méthode " + invokedMethod.GetName() +
                    "attends " + invokedMethod.GetParamCount() +
                    " arguments", node.getId());
        }

        Frame frame = new Frame(this._currentFrame, receiver, invokedMethod);

        for(PExp exp : expList)
        {
            frame.SetParam(GetExpEval(exp));
        }

        this._expEval = Execute(invokedMethod, frame, node.getId());
    }


    //endregion

    //region Arguments

    @Override
    public void caseAArgs(AArgs node)
    {
        Visit(node.getArg());
        node.getAdditionalArg().forEach(this::Visit);
    }

    @Override
    public void caseAAdditionalArg(AAdditionalArg node)
    {
        Visit(node.getArg());
    }

    @Override
    public void caseAArg(AArg node)
    {
        this._expList.add(node.getExp());
    }

    //endregion
    //endregion
}

