package min15.structure;

import min15.node.*;

/**
 * Created by Antoine-Ali on 18/02/2015.
 */
public class FieldInfo
{
    private final FieldTable _fieldTable;

    private final AFieldMember _definition;

    public FieldInfo(FieldTable fieldTable, AFieldMember definition) {
        this._fieldTable = fieldTable;
        this._definition = definition;
    }

    public String GetName()
    {
        return this._definition.getFieldName().getText();
    }
}
