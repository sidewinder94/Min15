package min15.structure;

/**
 * Created by Antoine-Ali on 02/03/2015.
 */

import node.*;
import min15.exceptions.*;
import java.util.*;

public class Scope
{
    private final Scope _previousScope;

    private final Map<String, Instance> _vars = new LinkedHashMap<>();

    public Scope(Scope _previousScope)
    {
        this._previousScope = _previousScope;
    }

    public Scope GetPreviousScope()
    {
        return _previousScope;
    }

    public Instance GetVar(TId node) throws SemanticException
    {
        String varName = node.getText();
        if (!this._vars.containsKey(varName)) {

            if (this._previousScope != null) {

                return this._previousScope.GetVar(node);
            }

            throw new SemanticException("[" + node.getLine() + ","
                    + node.getPos() + "] VARIABLE " + varName + " NON DECLAREE");
        }

        return this._vars.get(varName);
    }

    public boolean HasVar(
            TId var) {

        String varName = var.getText();
        if (this._vars.containsKey(varName)) {

            return true;
        }

        if (this._previousScope != null) {

            return this._previousScope.HasVar(var);
        }

        return false;
    }

    public void DeclareVar(
            TId var,
            Instance instance) throws SemanticException{

        String varName = var.getText();

        if (HasVar(var)) {

            throw new SemanticException("[" + var.getLine() + ","
                    + var.getPos() + "] VARIABLE " + varName
                    + " EST DEJA DECLAREE");
        }

        this._vars.put(varName, instance);
    }
}
