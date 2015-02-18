package min15.structure;

import min15.exceptions.InterpreterException;
import min15.node.TId;
import min15.node.Token;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Antoine-Ali on 18/02/2015.
 */
public class Frame
{
    private final Frame _previousFrame;
    private final Instance _receiver;
    private final MethodInfo _invokedMethod;
    private final Map<String, Instance> _varNameToValueMap = new LinkedHashMap<>();

    private Instance _returnValue;

    private int _nextParamIndex;

    private Token _currentLocation;

    public Frame(Frame previousFrame, Instance receiver, MethodInfo invokedMethod)
    {
        this._previousFrame = previousFrame;
        this._receiver = receiver;
        this._invokedMethod = invokedMethod;
    }

    public void SetVar(TId id, Instance value)
    {
        String name = id.getText();
        this._varNameToValueMap.put(name, value);
    }

    public Instance GetReceiver()
    {
        return this._receiver;
    }

    public MethodInfo GetInvokedMethod() {
        return _invokedMethod;
    }

    public Frame GetPreviousFrame() {
        return _previousFrame;
    }

    public void SetReturnValue(Instance value)
    {
        this._returnValue = value;
    }

    public void SetParam(Instance value)
    {
        String paramName = this._invokedMethod.GetParamName(this._nextParamIndex++);
        this._varNameToValueMap.put(paramName,value);
    }

    public Instance GetReturnValue() {
        return _returnValue;
    }

    public Instance GetVar(TId id)
    {
        String name = id.getText();
        if(!this._varNameToValueMap.containsKey(name))
        {
            throw new InterpreterException("Variavle " + name + " inconnue", id);
        }

        return this._varNameToValueMap.get(name);
    }

    public Token GetCurrentLocation()
    {
        return this._currentLocation;
    }

    public void SetCurrentLocation(Token currentLocation)
    {
        this._currentLocation = currentLocation;
    }
}
