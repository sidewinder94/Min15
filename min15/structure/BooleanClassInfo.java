package min15.structure;

import min15.node.AClassDef;

/**
 * Created by Antoine-Ali on 18/02/2015.
 */
public class BooleanClassInfo extends ClassInfo{
    private final BooleanInstance _trueInstance = new BooleanInstance(this, true);

    private final BooleanInstance  _falseInstance = new BooleanInstance(this, false);


    BooleanClassInfo(ClassTable classTable, AClassDef definition) {
        super(classTable, definition);
    }

    @Override
    public Instance NewInstance() {
        throw new RuntimeException("Invalid Boolean Instance Creation");
    }

    public Instance GetTrue()
    {
        return this._trueInstance;
    }

    public Instance GetFalse()
    {
        return this._falseInstance;
    }
}
