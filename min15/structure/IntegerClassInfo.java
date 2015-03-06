package min15.structure;

import node.AClassDef;

import java.math.BigInteger;
import java.util.*;

/**
 * Created by Antoine-Ali on 18/02/2015.
 */
public class IntegerClassInfo extends ClassInfo
{
    private final Map<BigInteger, Instance> _valueMap = new LinkedHashMap<>();

    public IntegerClassInfo(ClassTable classTable, AClassDef definition) {
        super(classTable, definition);
    }

    public Instance NewInteger(BigInteger value)
    {
        Instance instance = this._valueMap.get(value);

        if (instance == null)
        {
            instance = new IntegerInstance(this, value);
            this._valueMap.put(value, instance);
        }

        return instance;
    }
}
