package min15.structure;

import node.AClassDef;

import java.util.*;

/**
 * Created by Antoine-Ali on 18/02/2015.
 */
public class StringClassInfo extends ClassInfo
{
    private final Map<String, Instance> _valueMap = new LinkedHashMap<>();

    public StringClassInfo(ClassTable classTable, AClassDef definition) {
        super(classTable, definition);
    }


    public Instance NewString(String value)
    {
        Instance instance = this._valueMap.get(value);

        if (instance == null)
        {
            instance = new StringInstance(this, value);
            this._valueMap.put(value, instance);
        }

        return instance;
    }
}
