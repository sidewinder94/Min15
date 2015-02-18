package min15.structure;

import min15.exceptions.InterpreterException;
import min15.node.TFieldName;

import java.util.*;

/**
 * Created by Antoine-Ali on 18/02/2015.
 */
public class Instance {
    private final ClassInfo _classInfo;

    private final Map<String, Instance> _fieldNameToValueMap = new LinkedHashMap<String,Instance>();

    Instance(ClassInfo classInfo)
    {
        this._classInfo = classInfo;
        for(FieldInfo fieldInfo : classInfo.GetFieldTable().GetFields())
        {
            this._fieldNameToValueMap.put(fieldInfo.GetName(), null);
        }
    }

    public void SetField(TFieldName fieldName, Instance value)
    {
        String name = fieldName.getText();
        if(!this._fieldNameToValueMap.containsKey(name))
        {
            throw new InterpreterException("La classe " + this._classInfo.GetName() + " n'a pas de champ " + name, fieldName);
        }

        this._fieldNameToValueMap.put(name, value);
    }

    public boolean is_a (ClassInfo classInfo)
    {
        return this._classInfo.is_a(classInfo);
    }

    public ClassInfo GetClassInfo()
    {
        return this._classInfo;
    }

    public Instance GetField(TFieldName fieldName)
    {
        String name = fieldName.getText();
        if(!this._fieldNameToValueMap.containsKey(name))
        {
            throw new InterpreterException("La classe " + this._classInfo.GetName() + " n'a pas de champ " + name, fieldName);
        }

        return this._fieldNameToValueMap.get(name);
    }
}
