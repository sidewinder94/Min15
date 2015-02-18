package min15.structure;

/**
 * Created by Antoine-Ali on 18/02/2015.
 */
public class StringInstance extends Instance
{
    private final String _value;

    public StringInstance(ClassInfo classInfo, String value)
    {
        super(classInfo);
        this._value = value;
    }

    public String GetValue()
    {
        return this._value;
    }
}
