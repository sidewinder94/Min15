package min15.structure;

import min15.exceptions.InterpreterException;
import node.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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


    public <T extends PMember> void Add(T definition, List<TId> params, List<ClassInfo> paramsTypes, ClassInfo returnType)
    {
        Add(definition, params, paramsTypes, returnType, null);
    }

    public <T extends PMember> void Add(T definition, List<TId> params, List<ClassInfo> paramsTypes, ClassInfo returnType, Token operatorToken)
    {
        Token nameToken = null;
        String kind = "Opérateur";

        if (definition instanceof AMethodMember ||
                definition instanceof AInternMethodMember)
        {
            if (definition instanceof AMethodMember)
            {
                nameToken = ((AMethodMember) definition).getId();
            }
            else
            {
                nameToken = ((AInternMethodMember) definition).getId();
            }
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
            methodInfo = new NormalMethodInfo(this, (AMethodMember)definition, params, paramsTypes, returnType);
        }
        else if (definition instanceof AInternMethodMember)
        {
            methodInfo = new PrimitiveNormalMethodInfo(this, (AInternMethodMember)definition, params, paramsTypes, returnType);
        }
        else if (definition instanceof AOperatorMember)
        {
            methodInfo = new OperatorMethodInfo(this, (AOperatorMember)definition, params, paramsTypes, returnType, operatorToken);
        }
        else if (definition instanceof AInternOperatorMember)
        {
            methodInfo = new PrimitiveOperatorMethodInfo(this, (AInternOperatorMember)definition, params, paramsTypes, returnType, operatorToken);
        }
        else
        {
            throw new InterpreterException("Mauvais type", operatorToken == null ? nameToken : operatorToken);
        }
        this._nameToMethodInfoMap.put(name, methodInfo);
    }


    public Set<String> GetMethodNames()
    {
        return this._nameToMethodInfoMap.keySet();
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

    public MethodInfo GetMethodInfo(String name)
    {
        MethodInfo methodInfo = GetMethodInfoOrNull(name);

        if (methodInfo == null)
        {
            throw new StringIndexOutOfBoundsException("La classe " + this._classInfo.GetName() + " n'a pas de méthode " + name);
        }

        return methodInfo;
    }


    public ClassInfo GetClassInfo()
    {
        return this._classInfo;
    }
}
