package min15.structure;

import min15.exceptions.InterpreterException;
import node.AClassDef;
import node.TClassName;
import node.Token;

import java.util.*;

/**
 * Created by Antoine-Ali on 18/02/2015.
 */
public class ClassTable {
    private final Map<String, ClassInfo> _nameToClassInfoMap = new LinkedHashMap<>();

    public ClassInfo Add(AClassDef definition)
    {
        Token nameToken = definition.getClassName();
        String name = nameToken.getText();

        if (this._nameToClassInfoMap.containsKey(name))
        {
            throw new InterpreterException("Définition de classe dupliquée" + name, nameToken);
        }

        ClassInfo classInfo;

        if(name.equals("Boolean"))
        {
            classInfo = new BooleanClassInfo(this, definition);
        }
        else if (name.equals("Integer"))
        {
            classInfo = new IntegerClassInfo(this, definition);
        }
        else if(name.equals("String"))
        {
            classInfo = new StringClassInfo(this, definition);
        }
        else
        {
            classInfo = new ClassInfo(this, definition);
        }

        this._nameToClassInfoMap.put(name, classInfo);
        return classInfo;
    }

    public ClassInfo GetObjectClassInfoOrNull()
    {
        return this._nameToClassInfoMap.get("Object");
    }

    public ClassInfo Get(TClassName classNameToken)
    {
        String name = classNameToken.getText();
        if(!this._nameToClassInfoMap.containsKey(name))
        {
            throw new InterpreterException("La classe " + name + "n'a pas encore été définie", classNameToken);
        }

        return this._nameToClassInfoMap.get(name);
    }

    public  ClassInfo GetClassInfoOrNull(Class klass)
    {
        if (klass == Integer.class)
        {
            return this._nameToClassInfoMap.get("Integer");
        }
        if (klass == Boolean.class)
        {
            return this._nameToClassInfoMap.get("Boolean");
        }
        if (klass == String.class)
        {
            return this._nameToClassInfoMap.get("String");
        }
        if (klass == Object.class)
        {
            return this._nameToClassInfoMap.get("Object");
        }
        return null;
    }



}
