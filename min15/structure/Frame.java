package min15.structure;

import min15.exceptions.InterpreterException;
import node.TId;
import node.Token;

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

    private final Map<String, ClassInfo> _varNameToClassInfoMap = new LinkedHashMap<>();

    private Scope _scope;

    private Instance _returnValue;

    private int _nextParamIndex;

    private Token _currentLocation;

    public Frame(Frame previousFrame, ClassInfo receiver, MethodInfo invokedMethod, Scope scope)
    {
        this._previousFrame = previousFrame;
        this._receiver = receiver.NewInstance();
        this._invokedMethod = invokedMethod;
        this._scope = scope;
    }

    public Frame(Frame previousFrame, Instance receiver, MethodInfo invokedMethod, Scope scope)
    {
        this._previousFrame = previousFrame;
        this._receiver = receiver;
        this._invokedMethod = invokedMethod;
        this._scope = scope;
    }

    public Frame(Frame previousFrame, ClassInfo receiver, MethodInfo invokedMethod)
    {
        this(previousFrame, receiver, invokedMethod, new Scope(null));
    }

    public Frame(Frame previousFrame, Instance receiver, MethodInfo invokedMethod)
    {
        this(previousFrame, receiver, invokedMethod, new Scope(null));
    }

    public void SetVar(TId id, Instance value)
    {
        String name = id.getText();
        this._varNameToValueMap.put(name, value);
    }

    public void SetVar(TId id, ClassInfo value)
    {
        String name = id.getText();
        this._varNameToClassInfoMap.put(name, value);
    }

    public Scope GetScope()
    {
        return this._scope;
    }

    public void SetScope(Scope scope)
    {
        this._scope = scope;
    }

    public ClassInfo GetReceiver()
    {
        return this._receiver.GetClassInfo();
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

    public void SetParam(ClassInfo value)
    {
        String paramName = this._invokedMethod.GetParamName(this._nextParamIndex++);
        this._varNameToClassInfoMap.put(paramName,value);
    }

    public void SetParam(Instance value)
    {
        String paramName = this._invokedMethod.GetParamName(this._nextParamIndex++);
        this._varNameToValueMap.put(paramName,value);
    }

    public Instance GetReturnValue() {
        return _returnValue;
    }
    public ClassInfo GetReturnType() {return this._invokedMethod.GetReturnType();}
    public ClassInfo GetVar(TId id)
    {
        String name = id.getText();
        if(!this._varNameToClassInfoMap.containsKey(name))
        {
            if(!_scope.HasVar(id))
            {
                throw new InterpreterException("Paramètre " + name + " inconnu", id);
            }
            else
            {
                return this._scope.GetVar(id).GetClassInfo();
            }
        }

        return this._varNameToClassInfoMap.get(name);
    }

    public ClassInfo GetParameterValueWithoutId(String name)
    {
        if(!this._varNameToClassInfoMap.containsKey(name))
        {
            throw new RuntimeException("Paramètre non défini");
        }

        return this._varNameToClassInfoMap.get(name);
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
