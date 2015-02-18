package min15.structure;

import min15.exceptions.InterpreterException;
import min15.node.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Antoine-Ali on 18/02/2015.
 */
public class MethodTable
{
    private final ClassInfo _classInfo;

    private final Map<String, MethodInfo> _nameToMethodInfoMap = new LinkedHashMap<>();

    MethodTable(ClassInfo classInfo)
    {
        this._classInfo = classInfo;
    }

    //Possibilité d'amélioration de SableCC => Interfaces indiquant les tokens composant un noeud => permettrait de réduire la duplication de code

    public <T extends PMember> void Add(T definition, List<TId> params)
    {
        Add(definition, params, null);
    }

    public <T extends PMember> void Add(T definition, List<TId> params, Token operatorToken)
    {
        Token nameToken = null;
        String kind = "Opérateur";

        if (definition instanceof AMethodMember ||
            definition instanceof AInternMethodMember)
        {
            nameToken = ((AMethodMember) definition).getId();
            kind = "Méthode";
        }

        String name = operatorToken == null ? nameToken.getText() : operatorToken.getText();

        if (this._nameToMethodInfoMap.containsKey(name))
        {
            throw new InterpreterException(kind + " déjà définie " + name, operatorToken == null ? nameToken : operatorToken);
        }

        MethodInfo methodInfo = null;

        if (definition instanceof AMethodMember)
        {
            methodInfo = new NormalMethodInfo(this, (AMethodMember)definition, params);
        }
        else if (definition instanceof AInternMethodMember)
        {
            methodInfo = new PrimitiveNormalMethodInfo(this, (AInternMethodMember)definition, params);
        }
        else if (definition instanceof AOperatorMember)
        {
            methodInfo = new OperatorMethodInfo(this, params, (AOperatorMember)definition, operatorToken);
        }
        else if (definition instanceof AInternOperatorMember)
        {
            methodInfo = new PrimitiveOperatorMethodInfo(this, (AInternOperatorMember)params, definition, operatorToken);
        }
        else
        {
            throw new InterpreterException("Mauvais type", operatorToken == null ? nameToken : operatorToken);
        }
        this._nameToMethodInfoMap.put(name, methodInfo);
    }

    private MethodInfo GetMethodInfoOrNull(String name)
    {
        MethodInfo methodInfo = this._nameToMethodInfoMap.get(name);
        ClassInfo superClassInfo = this._classInfo.GetSuperClassInfoOrNull();

        if (methodInfo == null && superClassInfo != null)
        {
            methodInfo = superClassInfo.GetMethodTable().GetMethodInfoOrNull(name);
        }

        return methodInfo;
    }


    public MethodInfo GetMethodInfo(Token nameToken)
    {
        String name = nameToken.getText();
        MethodInfo methodInfo = GetMethodInfoOrNull(name);

        if (methodInfo == null)
        {
            throw new InterpreterException("La classe " + this._classInfo.GetName() + " n'a pas de méthode " + name, nameToken);
        }

        return methodInfo;
    }

    public ClassInfo GetClassInfo()
    {
        return this._classInfo;
    }
}
