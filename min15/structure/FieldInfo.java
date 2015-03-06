package min15.structure;

import node.*;

/**
 * Created by Antoine-Ali on 18/02/2015.
 */
public class FieldInfo
{
    private final FieldTable _fieldTable;

    private final AFieldMember _definition;

    private final ClassInfo _type;

    public FieldInfo(FieldTable fieldTable, AFieldMember definition, ClassInfo type) {
        this._fieldTable = fieldTable;
        this._definition = definition;
        this._type = type;
    }

    public FieldInfo(FieldTable fieldTable, AFieldMember definition) {
        this._fieldTable = fieldTable;
        this._definition = definition;
        this._type = null;
    }

    public String GetName()
    {
        return this._definition.getFieldName().getText();
    }
    public ClassInfo GetType()
    {
        return _type;
    }

    public AFieldMember GetDefinition()
    {
        return _definition;
    }
}
