package min15.Interpreter;

import analysis.DepthFirstAdapter;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import min15.exceptions.InterpreterException;
import min15.exceptions.ReturnException;
import min15.exceptions.SemanticException;
import min15.structure.*;
import node.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.math.BigInteger;
import java.util.*;

/**
 * Created by Antoine-Ali on 03/03/2015.
 */
public class SyntaxicChecker extends DepthFirstAdapter
{
    //region Membres
    private Boolean methodCalled = false;

    private final ClassTable _classTable = new ClassTable();

    private ClassInfo _currentClassInfo;

    private List<TId> _idList;

    private List<TClassName> _typeList;

    private Token _operatorToken;

    private List<PExp> _expList;

    private ClassInfo _expEval;

    private Frame _currentFrame; //proposition de traduction => Contexte (d'exécution)

    private ClassInfo _objectClassInfo;

    private BooleanClassInfo _booleanClassInfo;

    private IntegerClassInfo _integerClassInfo;

    private StringClassInfo _stringClassInfo;

    //endregion

    //region Main Methods
    public void Visit(Node node)
    {
        if (node != null)
        {
            node.apply(this);
        }
    }


    public ClassTable GetClassTable()
    {
        return this._classTable;
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
    private void ThrowSemantic(int line, int pos, String message)
    {
        throw new SemanticException(message + " " + "[" + line + "," + pos +"]");
    }

    private List<TId> GetParams(PParams node)
    {
        this._idList = new LinkedList<>();
        Visit(node);
        List<TId> idList = this._idList;
        this._idList = null;
        return idList;
    }

    private List<ClassInfo> GetTypes(PParams node)
    {
        this._typeList = new LinkedList<>();
        Visit(node);
        List<TClassName> typeList = this._typeList;
        this._typeList = null;
        List<ClassInfo> classInfoList = new LinkedList<>();
        for (TClassName className : typeList)
        {
            classInfoList.add(_classTable.Get(className));
        }


        return classInfoList;
    }

    private Token GetOperatorToken(POperator node)
    {
        Visit(node);
        Token operatorToken = this._operatorToken;
        this._operatorToken = null;
        return operatorToken;
    }

    private ClassInfo GetExpEval(Node node)
    {
        Visit(node);
        ClassInfo expEval = this._expEval;
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

    private ClassInfo Execute(MethodInfo invokedMethod,
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
        return frame.GetReturnType();
    }

    public void ObjectAbort(MethodInfo methodInfo)
    {
        String argName = methodInfo.GetParamName(0);
        ClassInfo arg = this._currentFrame.GetParameterValueWithoutId(argName);
        if (arg == null)
        {
            throw new InterpreterException("L'argument de abort est null", this._currentFrame.GetPreviousFrame().GetCurrentLocation());
        }
        if (!arg.is_a(_stringClassInfo))
        {
            throw new InterpreterException("L'argument de abort n'est pas une string", this._currentFrame.GetPreviousFrame().GetCurrentLocation());
        }
    }

    public void IntegerToS(PrimitiveNormalMethodInfo primitiveNormalMethodInfo)
    {
        if (!this._currentFrame.GetReceiver().is_a(_integerClassInfo)){
        ThrowSemantic(this._currentFrame.GetCurrentLocation().getPos(), this._currentFrame.GetCurrentLocation().getLine(), "L'argument n'est pas un entier");
        }

    }

    public void StringToSystemOut(PrimitiveNormalMethodInfo primitiveNormalMethodInfo)
    {
        if (!this._currentFrame.GetReceiver().is_a(_stringClassInfo)){
            ThrowSemantic(this._currentFrame.GetCurrentLocation().getPos(), this._currentFrame.GetCurrentLocation().getLine(), "L'argument n'est pas un entier");
        }
    }

    public void IntegerPlus(MethodInfo methodInfo)
    {
        String argName = methodInfo.GetParamName(0);
        ClassInfo arg = this._currentFrame.GetParameterValueWithoutId(argName);
        if(!arg.is_a(_integerClassInfo))
        {
            throw new InterpreterException("Arguement de droite n'est pas un entier", this._currentFrame.GetPreviousFrame().GetCurrentLocation());
        }
        if (!this._currentFrame.GetReceiver().is_a(_integerClassInfo)){
            ThrowSemantic(this._currentFrame.GetCurrentLocation().getPos(), this._currentFrame.GetCurrentLocation().getLine(), "L'argument de gauche n'estp as un entier");
        }

    }

    public void IntegerMinus(PrimitiveOperatorMethodInfo primitiveOperatorMethodInfo)
    {
        String argName = primitiveOperatorMethodInfo.GetParamName(0);
        ClassInfo arg = this._currentFrame.GetParameterValueWithoutId(argName);
        if(!arg.is_a(_integerClassInfo))
        {
            throw new InterpreterException("Arguement de droite n'est pas un entier", this._currentFrame.GetPreviousFrame().GetCurrentLocation());
        }
        if (!this._currentFrame.GetReceiver().is_a(_integerClassInfo)){
            ThrowSemantic(this._currentFrame.GetCurrentLocation().getPos(), this._currentFrame.GetCurrentLocation().getLine(), "L'argument de gauche n'estp as un entier");
        }
    }
    public void IntegerMult(PrimitiveOperatorMethodInfo primitiveOperatorMethodInfo)
    {
        String argName = primitiveOperatorMethodInfo.GetParamName(0);
        ClassInfo arg = this._currentFrame.GetParameterValueWithoutId(argName);
        if(!arg.is_a(_integerClassInfo))
        {
            throw new InterpreterException("Arguement de droite n'est pas un entier", this._currentFrame.GetPreviousFrame().GetCurrentLocation());
        }
        if (!this._currentFrame.GetReceiver().is_a(_integerClassInfo)){
            ThrowSemantic(this._currentFrame.GetCurrentLocation().getPos(), this._currentFrame.GetCurrentLocation().getLine(), "L'argument de gauche n'estp as un entier");
        }
    }

    public void IntegerDiv(PrimitiveOperatorMethodInfo primitiveOperatorMethodInfo)
    {
        String argName = primitiveOperatorMethodInfo.GetParamName(0);
        ClassInfo arg = this._currentFrame.GetParameterValueWithoutId(argName);
        if(!arg.is_a(_integerClassInfo))
        {
            throw new InterpreterException("Arguement de droite n'est pas un entier", this._currentFrame.GetPreviousFrame().GetCurrentLocation());
        }
        if (!this._currentFrame.GetReceiver().is_a(_integerClassInfo)){
            ThrowSemantic(this._currentFrame.GetCurrentLocation().getPos(), this._currentFrame.GetCurrentLocation().getLine(), "L'argument de gauche n'estp as un entier");
        }
    }

    public void IntegerMod(PrimitiveOperatorMethodInfo primitiveOperatorMethodInfo)
    {
        String argName = primitiveOperatorMethodInfo.GetParamName(0);
        ClassInfo arg = this._currentFrame.GetParameterValueWithoutId(argName);
        if(!arg.is_a(_integerClassInfo))
        {
            throw new InterpreterException("Arguement de droite n'est pas un entier", this._currentFrame.GetPreviousFrame().GetCurrentLocation());
        }
        if (!this._currentFrame.GetReceiver().is_a(_integerClassInfo)){
            ThrowSemantic(this._currentFrame.GetCurrentLocation().getPos(), this._currentFrame.GetCurrentLocation().getLine(), "L'argument de gauche n'estp as un entier");
        }
    }

    public void IntegerLt(PrimitiveOperatorMethodInfo primitiveOperatorMethodInfo)
    {
        String argName = primitiveOperatorMethodInfo.GetParamName(0);
        ClassInfo arg = this._currentFrame.GetParameterValueWithoutId(argName);
        if(!arg.is_a(_integerClassInfo))
        {
            throw new InterpreterException("Arguement de droite n'est pas un entier", this._currentFrame.GetPreviousFrame().GetCurrentLocation());
        }
        if (!this._currentFrame.GetReceiver().is_a(_integerClassInfo)){
            ThrowSemantic(this._currentFrame.GetCurrentLocation().getPos(), this._currentFrame.GetCurrentLocation().getLine(), "L'argument de gauche n'estp as un entier");
        }
    }

    public void IntegerLtEq(PrimitiveOperatorMethodInfo primitiveOperatorMethodInfo)
    {
        String argName = primitiveOperatorMethodInfo.GetParamName(0);
        ClassInfo arg = this._currentFrame.GetParameterValueWithoutId(argName);
        if(!arg.is_a(_integerClassInfo))
        {
            throw new InterpreterException("Arguement de droite n'est pas un entier", this._currentFrame.GetPreviousFrame().GetCurrentLocation());
        }
        if (!this._currentFrame.GetReceiver().is_a(_integerClassInfo)){
            ThrowSemantic(this._currentFrame.GetCurrentLocation().getPos(), this._currentFrame.GetCurrentLocation().getLine(), "L'argument de gauche n'estp as un entier");
        }
    }

    public void IntegerGt(PrimitiveOperatorMethodInfo primitiveOperatorMethodInfo)
    {
        String argName = primitiveOperatorMethodInfo.GetParamName(0);
        ClassInfo arg = this._currentFrame.GetParameterValueWithoutId(argName);
        if(!arg.is_a(_integerClassInfo))
        {
            throw new InterpreterException("Arguement de droite n'est pas un entier", this._currentFrame.GetPreviousFrame().GetCurrentLocation());
        }
        if (!this._currentFrame.GetReceiver().is_a(_integerClassInfo)){
            ThrowSemantic(this._currentFrame.GetCurrentLocation().getPos(), this._currentFrame.GetCurrentLocation().getLine(), "L'argument de gauche n'estp as un entier");
        }
    }

    public void IntegerGtEq(PrimitiveOperatorMethodInfo primitiveOperatorMethodInfo)
    {
        String argName = primitiveOperatorMethodInfo.GetParamName(0);
        ClassInfo arg = this._currentFrame.GetParameterValueWithoutId(argName);
        if(!arg.is_a(_integerClassInfo))
        {
            throw new InterpreterException("Arguement de droite n'est pas un entier", this._currentFrame.GetPreviousFrame().GetCurrentLocation());
        }
        if (!this._currentFrame.GetReceiver().is_a(_integerClassInfo)){
            ThrowSemantic(this._currentFrame.GetCurrentLocation().getPos(), this._currentFrame.GetCurrentLocation().getLine(), "L'argument de gauche n'estp as un entier");
        }
    }

    public void IntegerEq(PrimitiveOperatorMethodInfo primitiveOperatorMethodInfo)
    {
        String argName = primitiveOperatorMethodInfo.GetParamName(0);
        ClassInfo arg = this._currentFrame.GetParameterValueWithoutId(argName);
        if(!arg.is_a(_integerClassInfo))
        {
            throw new InterpreterException("Arguement de droite n'est pas un entier", this._currentFrame.GetPreviousFrame().GetCurrentLocation());
        }
        if (!this._currentFrame.GetReceiver().is_a(_integerClassInfo)){
            ThrowSemantic(this._currentFrame.GetCurrentLocation().getPos(), this._currentFrame.GetCurrentLocation().getLine(), "L'argument de gauche n'estp as un entier");
        }
    }

    public void IntegerNeq(PrimitiveOperatorMethodInfo primitiveOperatorMethodInfo)
    {
        String argName = primitiveOperatorMethodInfo.GetParamName(0);
        ClassInfo arg = this._currentFrame.GetParameterValueWithoutId(argName);
        if(!arg.is_a(_integerClassInfo))
        {
            throw new InterpreterException("Arguement de droite n'est pas un entier", this._currentFrame.GetPreviousFrame().GetCurrentLocation());
        }
        if (!this._currentFrame.GetReceiver().is_a(_integerClassInfo)){
            ThrowSemantic(this._currentFrame.GetCurrentLocation().getPos(), this._currentFrame.GetCurrentLocation().getLine(), "L'argument de gauche n'estp as un entier");
        }
    }


    public void StringPlus(MethodInfo methodInfo)
    {
        StringClassInfo self = (StringClassInfo) this._currentFrame.GetReceiver();

        String argName = methodInfo.GetParamName(0);

        ClassInfo arg = this._currentFrame.GetParameterValueWithoutId(argName);

        if(!arg.is_a(_stringClassInfo))
        {
            throw new InterpreterException("L'argument de droite n'est pas une chaine", this._currentFrame.GetPreviousFrame().GetCurrentLocation());
        }
    }

    private void CheckIfTypeExists(Class klass)
    {
        Boolean failed = true;
        if(klass == Object.class)
        {
            _objectClassInfo = this._classTable.GetObjectClassInfoOrNull();
            if (_objectClassInfo != null)
                failed = false;
        }
        else if (klass == Integer.class)
        {
            _integerClassInfo = this._classTable.GetIntegerClassInfoOrNull();
            if (_integerClassInfo != null)
                failed = false;
        }
        else if (klass == Boolean.class)
        {
            _booleanClassInfo = this._classTable.GetBooleanClassInfoOrNull();
            if (_booleanClassInfo != null)
                failed = false;
        }
        else if(klass == String.class)
        {
            _stringClassInfo = this._classTable.GetStringClassInfoOrNull();
            if (_stringClassInfo != null)
                failed = false;
        }

        if (failed)
            throw new InterpreterException("La classe " + klass.getName() + " n'est pas définie", null);

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
        for(PClassDef classDef : node.getClassDefs())
        {
            Visit(classDef);
        }

        HandleCompilerKnownClasses();

        Instance instance = _objectClassInfo.NewInstance();

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
        this._currentClassInfo.GetFieldTable().Add(node, this._classTable.Get(node.getClassName()));
    }

    @Override
    public void caseAMethodMember(AMethodMember node)
    {
        List<TId> params = GetParams(node.getParams());
        ClassInfo returnType = GetExpEval(node.getReturnDecl());
        this._currentClassInfo.GetMethodTable().Add(node, params, GetTypes(node.getParams()), returnType);
    }

    @Override
    public void caseAOperatorMember(AOperatorMember node)
    {
        List<TId> params = GetParams(node.getParams());
        ClassInfo returnType = GetExpEval(node.getReturnDecl());
        Token operatorToken = GetOperatorToken(node.getOperator());
        this._currentClassInfo.GetMethodTable().Add(node, params, GetTypes(node.getParams()), returnType, operatorToken);
    }

    @Override
    public void caseAInternMethodMember(AInternMethodMember node)
    {
        List<TId> params = GetParams(node.getParams());
        List<ClassInfo> paramsType = GetTypes(node.getParams());
        ClassInfo returnType = GetExpEval(node.getReturnDecl());
        this._currentClassInfo.GetMethodTable().Add(node, params, paramsType, returnType);
    }

    @Override
    public void caseAInternOperatorMember(AInternOperatorMember node)
    {
        List<TId> params = GetParams(node.getParams());
        Token operatorToken = GetOperatorToken(node.getOperator());
        ClassInfo returnType = GetExpEval(node.getReturnDecl());
        this._currentClassInfo.GetMethodTable().Add(node, params, GetTypes(node.getParams()), returnType, operatorToken);
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
        if(this._idList != null)
        {
            this._idList.add(node.getId());
        }
        else if(this._typeList != null)
        {
            this._typeList.add(node.getClassName());
        }
        else
        {
            throw new RuntimeException("Listes de type et d'id nulles");
        }
        //TODO : Complete for type safety
    }

    //endregion

    //region Return Declaration

    @Override
    public void caseAReturnDecl(AReturnDecl node)
    {
        this._expEval = this._classTable.Get(node.getClassName());
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
        for(PStmt stmt : node.getStmts())
        {
            Visit(stmt);
        }
    }
    //endregion

    //region Statement

    private ClassInfo CheckBooleanExpressionValidity(PStmt node)
    {
        Boolean ifStatement = node instanceof AIfStmt;
        ClassInfo value = GetExpEval(ifStatement ? ((AIfStmt) node).getExp() : ((AWhileStmt) node).getExp());
        if (value == null)
        {
            throw new InterpreterException("L'expression est nulle", ifStatement ? ((AIfStmt) node).getIf() : ((AWhileStmt) node).getWhile());
        }

        if (!value.is_a(_booleanClassInfo))
        {
            throw new InterpreterException("l'expression n'est pas booléenne", ifStatement ? ((AIfStmt) node).getIf() : ((AWhileStmt) node).getWhile());
        }
        return value;
    }

    @Override
    public void caseAWhileStmt(AWhileStmt node)
    {

        Scope newScope = new Scope(this._currentFrame.GetScope());
        this._currentFrame.SetScope(newScope);

        ClassInfo value = CheckBooleanExpressionValidity(node);

        if(!value.is_a(_booleanClassInfo))
        {
            ThrowSemantic(node.getWhile().getLine(), node.getWhile().getPos(), "L'expression du while n'est pas booléene");
        }

        Visit(node.getStmts());
        this._currentFrame.SetScope(this._currentFrame.GetScope().GetPreviousScope());
    }


    @Override
    public void caseAIfStmt(AIfStmt node)
    {
        Scope newScope = new Scope(this._currentFrame.GetScope());
        this._currentFrame.SetScope(newScope);
        ClassInfo value = CheckBooleanExpressionValidity(node);

        if(!value.is_a(_booleanClassInfo))
        {
            ThrowSemantic(node.getIf().getLine(), node.getIf().getPos(), "L'expression du fi n'est pas booléenne");
        }

        Visit(node.getStmts());
        Visit(node.getElsePart());

        this._currentFrame.SetScope(this._currentFrame.GetScope().GetPreviousScope());
    }


    @Override
    public void caseAReturnStmt(AReturnStmt node)
    {
        if (this._currentFrame.GetInvokedMethod() == null)
        {
            throw new InterpreterException("L'instruction return n'est pas autrisée dans le programme principal", node.getReturn());
        }

        PExp exp = node.getExp();
        ClassInfo value = GetExpEval(exp);

        if(!value.is_a(this._currentFrame.GetReturnType()))
        {
            ThrowSemantic(node.getReturn().getLine(), node.getReturn().getPos(), "Le type de retour (" + value.GetName()
                    + ") ne correspond pas a celui de la fonction (" + this._currentFrame.GetReturnType().GetName() + ")");
        }


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
        ClassInfo value = GetExpEval(node.getExp());
        this._currentFrame.SetVar(node.getId(), value);
    }

    @Override
    public void caseAFieldAssignStmt(AFieldAssignStmt node)
    {
        ClassInfo value = GetExpEval(node.getExp());
        ClassInfo self = this._currentFrame.GetReceiver();
        if (self.GetFieldTable().GetField(node.getFieldName().getText()).GetType().is_a(value))
        {
            ThrowSemantic(node.getFieldName().getLine(), node.getFieldName().getPos(), "L'expression de gauche n'est pas du type de la variable");
        }
    }


    @Override
    public void caseAVarDefStmt(AVarDefStmt node)
    {
        if(this._currentFrame.GetScope().HasVar(node.getId()))
        {
            throw new SemanticException(node.getId().getText() + " existe déjà dans le scope courant");
        }
        Instance newInstance = _classTable.Get(node.getClassName()).NewInstance();
        this._currentFrame.GetScope().DeclareVar(node.getId(), newInstance);
    }

    @Override
    public void caseAVarInitStmt(AVarInitStmt node)
    {
        if(this._currentFrame.GetScope().HasVar(node.getId()))
        {
            throw new SemanticException(node.getId().getText() + " existe déjà dans le scope courant");
        }

        ClassInfo value = GetExpEval(node.getExp());
        this._currentFrame.GetScope().DeclareVar(node.getId(), value.NewInstance());
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
        BooleanClassInfo info = _booleanClassInfo;
        ClassInfo left = GetExpEval(node.getExp());
        ClassInfo right = GetExpEval(node.getConjunction());

        if (left.is_a(info))
        {
            throw new InterpreterException("Le membre gauche de l'instruction OR n'est pas un booléen", node.getOr());
        }
        if (right.is_a(info))
        {
            throw new InterpreterException("Le membre gauche de l'instruction OR n'est pas un booléen", node.getOr());
        }

        this._expEval = info;
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
        BooleanClassInfo info = _booleanClassInfo;
        ClassInfo left = GetExpEval(node.getConjunction());
        ClassInfo right = GetExpEval(node.getComparison());

        if (left.is_a(info))
        {
            throw new InterpreterException("Le membre gauche de l'instruction AND n'est pas un booléen", node.getAnd());
        }
        if (right.is_a(info))
        {
            throw new InterpreterException("Le membre gauche de l'instruction AND n'est pas un booléen", node.getAnd());
        }

        this._expEval = info;

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
        this._expEval = _booleanClassInfo;
    }

    @Override
    public void caseAEqComparison(AEqComparison node)
    {
        BooleanClassInfo info = _booleanClassInfo;
        ClassInfo left = GetExpEval(node.getComparison());
        ClassInfo right = GetExpEval(node.getArithExp());
        if (left == null || right == null)
        {
            this._expEval = info;
        }
        else
        {
            MethodInfo invokedMethod = left.GetMethodTable().GetMethodInfo(node.getEq());
            Frame frame = new Frame(this._currentFrame, left, invokedMethod);
            frame.SetParam(right);
            this._expEval = Execute(invokedMethod, frame, node.getEq());
        }
    }

    @Override
    public void caseANeqComparison(ANeqComparison node)
    {
        BooleanClassInfo info = _booleanClassInfo;
        ClassInfo left = GetExpEval(node.getComparison());
        ClassInfo right = GetExpEval(node.getArithExp());
        if (left == null || right == null)
        {
            this._expEval = info;
        }
        else
        {
            MethodInfo invokedMethod = left.GetMethodTable().GetMethodInfo(node.getNeq());
            Frame frame = new Frame(this._currentFrame, left, invokedMethod);
            frame.SetParam(right);
            this._expEval = Execute(invokedMethod, frame, node.getNeq());
        }
    }

    @Override
    public void caseALtComparison(ALtComparison node)
    {
        BooleanClassInfo info = _booleanClassInfo;
        ClassInfo left = GetExpEval(node.getComparison());
        ClassInfo right = GetExpEval(node.getArithExp());
        if (left == null || right == null)
        {
            this._expEval = info;
        }
        else
        {
            MethodInfo invokedMethod = left.GetMethodTable().GetMethodInfo(node.getLt());
            Frame frame = new Frame(this._currentFrame, left, invokedMethod);
            frame.SetParam(right);
            this._expEval = Execute(invokedMethod, frame, node.getLt());
        }
    }

    @Override
    public void caseAGtComparison(AGtComparison node)
    {
        BooleanClassInfo info = _booleanClassInfo;
        ClassInfo left = GetExpEval(node.getComparison());
        ClassInfo right = GetExpEval(node.getArithExp());
        if (left == null || right == null)
        {
            this._expEval = info;
        }
        else
        {
            MethodInfo invokedMethod = left.GetMethodTable().GetMethodInfo(node.getGt());
            Frame frame = new Frame(this._currentFrame, left, invokedMethod);
            frame.SetParam(right);
            this._expEval = Execute(invokedMethod, frame, node.getGt());
        }
    }

    @Override
    public void caseALteqComparison(ALteqComparison node)
    {
        BooleanClassInfo info = _booleanClassInfo;
        ClassInfo left = GetExpEval(node.getComparison());
        ClassInfo right = GetExpEval(node.getArithExp());
        if (left == null || right == null)
        {
            this._expEval = info;
        }
        else
        {
            MethodInfo invokedMethod = left.GetMethodTable().GetMethodInfo(node.getLteq());
            Frame frame = new Frame(this._currentFrame, left, invokedMethod);
            frame.SetParam(right);
            this._expEval = Execute(invokedMethod, frame, node.getLteq());
        }
    }

    @Override
    public void caseAGteqComparison(AGteqComparison node)
    {
        BooleanClassInfo info = _booleanClassInfo;
        ClassInfo left = GetExpEval(node.getComparison());
        ClassInfo right = GetExpEval(node.getArithExp());
        if (left == null || right == null)
        {
            this._expEval = info;
        }
        else
        {
            MethodInfo invokedMethod = left.GetMethodTable().GetMethodInfo(node.getGteq());
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
        ClassInfo left = GetExpEval(node.getArithExp());
        ClassInfo right = GetExpEval(node.getFactor());

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
            MethodInfo invokedMethod = left.GetMethodTable().GetMethodInfo(node.getPlus());
            Frame frame = new Frame(this._currentFrame, left, invokedMethod);
            frame.SetParam(right);
            this._expEval = invokedMethod.GetReturnType();
        }
    }

    @Override
    public void caseASubArithExp(ASubArithExp node)
    {
        ClassInfo left = GetExpEval(node.getArithExp());
        ClassInfo right = GetExpEval(node.getFactor());

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
            MethodInfo invokedMethod = left.GetMethodTable().GetMethodInfo(node.getMinus());
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
        ClassInfo left = GetExpEval(node.getLeftUnaryExp());
        ClassInfo right = GetExpEval(node.getFactor());

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
            MethodInfo invokedMethod = left.GetMethodTable().GetMethodInfo(node.getStar());
            Frame frame = new Frame(this._currentFrame, left, invokedMethod);
            frame.SetParam(right);
            this._expEval = Execute(invokedMethod, frame, node.getStar());
        }
    }

    @Override
    public void caseADivFactor(ADivFactor node)
    {
        ClassInfo left = GetExpEval(node.getLeftUnaryExp());
        ClassInfo right = GetExpEval(node.getFactor());

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
            MethodInfo invokedMethod = left.GetMethodTable().GetMethodInfo(node.getSlash());
            Frame frame = new Frame(this._currentFrame, left, invokedMethod);
            frame.SetParam(right);
            this._expEval = Execute(invokedMethod, frame, node.getSlash());
        }
    }

    @Override
    public void caseAModFactor(AModFactor node)
    {
        ClassInfo left = GetExpEval(node.getLeftUnaryExp());
        ClassInfo right = GetExpEval(node.getFactor());

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
            MethodInfo invokedMethod = left.GetMethodTable().GetMethodInfo(node.getPercent());
            Frame frame = new Frame(this._currentFrame, left, invokedMethod);
            frame.SetParam(right);
            this._expEval = Execute(invokedMethod, frame, node.getPercent());
        }
    }

    @Override
    public void caseASimpleFactor(ASimpleFactor node)
    {
        Visit(node.getLeftUnaryExp());
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
        BooleanClassInfo info = _booleanClassInfo;
        ClassInfo left = GetExpEval(node.getRightUnaryExp());
        ClassInfo right = this._classTable.Get(node.getClassName());

        this._expEval = info;
    }

    @Override
    public void caseAAsRightUnaryExp(AAsRightUnaryExp node)
    {


        if (!GetExpEval(node.getRightUnaryExp()).is_a(_classTable.Get(node.getClassName())))
        {
            ThrowSemantic(node.getAs().getPos(), node.getAs().getLine(), "L'expression de gauche n'est pas un supertype du type spécifié");
        }

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
        ClassInfo value = GetExpEval(node.getLeftUnaryExp());
        BooleanClassInfo info = _booleanClassInfo;
        if(value == null)
        {
            throw new InterpreterException("expression is null", node.getNot());
        }

        this._expEval = value;
    }

    @Override
    public void caseANegLeftUnaryExp(ANegLeftUnaryExp node)
    {
        ClassInfo value = GetExpEval(node.getLeftUnaryExp());
        if(!(value.is_a(_integerClassInfo)))
        {
            throw new InterpreterException("IL est seulement possible d'avoir un entier négatif", node.getMinus());
        }

        this._expEval = _integerClassInfo;
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

        this._expEval = classInfo;
    }

    @Override
    public void caseAFieldTerm(AFieldTerm node)
    {
        ClassInfo self = this._currentFrame.GetReceiver();
        this._expEval = self.GetFieldTable().GetField(node.getFieldName().getText()).GetType();
    }

    @Override
    public void caseAVarTerm(AVarTerm node)
    {
        this._expEval = this._currentFrame.GetVar(node.getId());
    }

    @Override
    public void caseANumTerm(ANumTerm node)
    {
        this._expEval = _integerClassInfo;
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
        this._expEval = _booleanClassInfo;
    }

    @Override
    public void caseAFalseTerm(AFalseTerm node)
    {
        this._expEval = _booleanClassInfo;
    }

    @Override
    public void caseAStringTerm(AStringTerm node)
    {
        String string = node.getString().getText();
        //Handling quotes
        string = string.substring(1, string.length() - 1);
        //Handling escaped characters, we don't want to negate the effect of the control chars (\t \b \n \r \f)
        string = string.replaceAll("\\\\([^tbnrtf])","$1");
        this._expEval = _stringClassInfo;
    }


    //endregion

    //region Calls

    @Override
    public void caseACall(ACall node)
    {
        List<PExp> expList = GetExpList(node.getArgs());

        ClassInfo receiver = GetExpEval(node.getRightUnaryExp());

        if (receiver == null)
        {
            throw new InterpreterException("La méthode réceptionnant " + node.getId().getText() + " est nulle",
                    node.getId());
        }

        MethodInfo invokedMethod = receiver.GetMethodTable().GetMethodInfo(node.getId());

        if(invokedMethod.GetParamCount() != expList.size())
        {
            throw new InterpreterException("La méthode " + invokedMethod.GetName() +
                    "attends " + invokedMethod.GetParamCount() +
                    " arguments", node.getId());
        }

        Frame frame = new Frame(this._currentFrame, receiver, invokedMethod);
        frame.SetCurrentLocation(node.getDot());
        for(PExp exp : expList)
        {
            frame.SetParam(GetExpEval(exp));
        }
        
        //On ne veut surtout pas exécuter la méthode...., dans le cas d'une méthode récursive, le vérificateur exploserait (StackOverflow Error)
        //Puisque auune valeur n'est effectivement calculée, on entrerait dans une boucle de récursion infinie !!!!
        if (!this.methodCalled)
        {
            this.methodCalled = true;
            this.Execute(invokedMethod, frame, node.getDot());
        }

        this._expEval = invokedMethod.GetReturnType();
        this.methodCalled = false;
    }

    @Override
    public void caseASelfCall(ASelfCall node)
    {
        List<PExp> expList = GetExpList(node.getArgs());

        ClassInfo receiver = this._currentFrame.GetReceiver();

        if (receiver == null)
        {
            throw new InterpreterException("La méthode réceptionnant " + node.getId().getText() + " est nulle",
                    node.getId());
        }

        MethodInfo invokedMethod = receiver.GetMethodTable().GetMethodInfo(node.getId());

        if(invokedMethod.GetParamCount() != expList.size())
        {
            throw new InterpreterException("La méthode " + invokedMethod.GetName() +
                    "attends " + invokedMethod.GetParamCount() +
                    " arguments", node.getId());
        }

        Frame frame = new Frame(this._currentFrame, receiver, invokedMethod);
        frame.SetCurrentLocation(node.getLPar());
        for(PExp exp : expList)
        {
            frame.SetParam(GetExpEval(exp));
        }

        //On ne veut surtout pas exécuter la méthode...., dans le cas d'une méthode récursive, le vérificateur exploserait (StackOverflow Error)
        //Puisque auune valeur n'est effectivement calculée, on entrerait dans une boucle de récursion infinie !!!!
        if (!this.methodCalled)
        {
            this.methodCalled = true;
            this.Execute(invokedMethod, frame, node.getLPar());
        }
        this._expEval = invokedMethod.GetReturnType();

        this.methodCalled = false;
    }


    //endregion

    //region Arguments

    @Override
    public void caseAArgs(AArgs node)
    {
        Visit(node.getArg());

        for(PAdditionalArg arg : node.getAdditionalArgs())
        {
            Visit(arg);
        }
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
