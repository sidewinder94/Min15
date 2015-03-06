package min15.structure;

import min15.exceptions.InterpreterException;
import node.AFieldMember;
import node.Token;

import java.util.*;

/**
 * Created by Antoine-Ali on 18/02/2015.
 */
public class FieldTable
{
    private final ClassInfo _classInfo;

    private final Map<String, FieldInfo> _nameToFieldInfoMap = new LinkedHashMap<>();

    private Set<FieldInfo> _fields;

    public FieldTable(ClassInfo classInfo) {
        this._classInfo = classInfo;
    }

    public FieldInfo GetField(String name)
    {
        return _nameToFieldInfoMap.get(name);
    }

    public void Add(AFieldMember definition, ClassInfo type)
    {
        Token nameToken = definition.getFieldName();
        String name = nameToken.getText();

        ClassInfo superClassInfo = this._classInfo.GetSuperClassInfoOrNull();
        if(superClassInfo != null)
        {
            if(superClassInfo.GetFieldTable().Contains(name))
            {
                throw new InterpreterException("Le champ " + name + " existe dans la super classe", nameToken);
            }
        }

        if(this._nameToFieldInfoMap.containsKey(name))
        {
            throw new InterpreterException("Définition double du champ " + name, nameToken);
        }

        _nameToFieldInfoMap.put(name, new FieldInfo(this, definition, type));

    }

    public void Add(AFieldMember definition)
    {
        Token nameToken = definition.getFieldName();
        String name = nameToken.getText();

        ClassInfo superClassInfo = this._classInfo.GetSuperClassInfoOrNull();
        if(superClassInfo != null)
        {
            if(superClassInfo.GetFieldTable().Contains(name))
            {
                throw new InterpreterException("Le champ " + name + " existe dans la super classe", nameToken);
            }
        }

        if(this._nameToFieldInfoMap.containsKey(name))
        {
            throw new InterpreterException("Définition double du champ " + name, nameToken);
        }

        _nameToFieldInfoMap.put(name, new FieldInfo(this, definition));
    }

    public boolean Contains(String name)
    {
        ClassInfo superClassInfo = this._classInfo.GetSuperClassInfoOrNull();
        if (superClassInfo != null)
        {
            if(superClassInfo.GetFieldTable().Contains(name))
            {
                return true;
            }
        }

        return this._nameToFieldInfoMap.containsKey(name);
    }

    public Set<FieldInfo> GetFields()
    {
        if(this._fields == null)
        {
            Set<FieldInfo> fields = new LinkedHashSet<FieldInfo>();

            ClassInfo superClassInfo = this._classInfo.GetSuperClassInfoOrNull();
            if (superClassInfo != null)
            {
                fields.addAll(superClassInfo.GetFieldTable().GetFields());
            }

            fields.addAll(this._nameToFieldInfoMap.values());
            this._fields = Collections.unmodifiableSet(fields);
        }

        return this._fields;
    }
}
