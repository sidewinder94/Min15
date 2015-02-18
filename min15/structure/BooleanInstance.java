package min15.structure;

/**
 * Created by Antoine-Ali on 18/02/2015.
 */
public class BooleanInstance extends Instance
{
    private final boolean _value;

    public BooleanInstance(ClassInfo classInfo, boolean value) {
        super(classInfo);
        this._value = value;
    }
}
