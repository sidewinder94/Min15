package min15.structure;

import min15.exceptions.InterpreterException;
import min15.node.AClassDef;
import min15.node.ASuperDecl;

/**
 * Created by Antoine-Ali on 18/02/2015.
 */
public class ClassInfo {

    private final ClassTable _classTable;

    private final AClassDef _definition;

    private final ClassInfo _superClass;

    private final MethodTable _methodTable = new MethodTable(this);

    private final FieldTable _fieldTable = new FieldTable(this);

    ClassInfo(ClassTable classTable, AClassDef definition)
    {
        this._classTable = classTable;
        this._definition = definition;

        if (GetName().equals("Object"))
        {
            if(definition.getSuperDecl() instanceof ASuperDecl)
            {
                throw new InterpreterException("La class Object ne peut avoir de SuperClasse",
                definition.getClassName());
            }
            this._superClass = null;
        }

        else if (definition.getSuperDecl() == null)
        {
            ClassInfo objClass = this._classTable.GetObjectClassInfoOrNull();
            if(objClass == null)
            {
                throw new InterpreterException("La classe Object n'ap as encore été définie",
                        definition.getClassName());
            }
            this._superClass = objClass;
        }
        else
        {
            ASuperDecl sup = (ASuperDecl) definition.getSuperDecl();
            String superClassName = sup.getClassName().getText();

            if (superClassName.equals("Boolean") ||
                superClassName.equals("Integer") ||
                superClassName.equals("String"))
            {
                throw new InterpreterException("La classe " + superClassName + " ne peut être spécialisée", sup.getClassName());
            }

            this._superClass = classTable.Get(sup.getClassName());
        }

    }

    public String GetName()
    {
        return this._definition.getClassName().getText();
    }

    public MethodTable GetMethodTable()
    {
        return this._methodTable;
    }

    public FieldTable GetFieldTable()
    {
        return this._fieldTable;
    }

    public ClassInfo GetSuperClassInfoOrNull()
    {
        return this._superClass;
    }

    public Instance NewInstance()
    {
        return new Instance(this);
    }

    public boolean is_a(ClassInfo classInfo)
    {
        if (this == classInfo)
        {
            return true;
        }

        if (this._superClass != null)
        {
            return this._superClass.is_a(classInfo);
        }

        return false;
    }
}
