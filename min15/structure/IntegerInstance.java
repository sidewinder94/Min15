package min15.structure;

import java.math.BigInteger;

/**
 * Created by Antoine-Ali on 18/02/2015.
 */
public class IntegerInstance extends Instance
{
    private final BigInteger _value;

    public IntegerInstance(ClassInfo classInfo, BigInteger value) {
        super(classInfo);
        this._value = value;
    }
    public BigInteger GetValue()
    {
        return this._value;
    }


}
