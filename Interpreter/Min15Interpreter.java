/**
 * Created by Antoine-Ali on 18/02/2015.
 */
package Interpreter;

import java.util.*;

import min15.node.*;
import min15.structure.*;
import min15.analysis.DepthFirstAdapter;


public class Min15Interpreter extends DepthFirstAdapter
{
    private final ClassTable _classTable = new ClassTable();

    private ClassInfo _currentClassInfo;

    private List<TId> _idList;

    private Token _operatorToken;

    private List<PExp> _expList;

    private Instance _expVal;

    private Frame _currentFrame; //proposition de traduction => Contexte (d'exécution)

    private ClassInfo _objectClassInfo;

    private BooleanClassInfo _booleanClassInfo;

    private IntegerClassInfo _integerClassInfo;

    private StringClassInfo _stringClassInfo;

    public void Visit(Node node)
    {
        if (node != null)
        {
            node.Apply(this);
        }
    }

    public void PrintStackTrace() {
        Frame frame = this._currentFrame;
        while (frame != null) {
            Token locationToken = frame.GetCurrentLocation();
            String location = "";

            if (locationToken != null) {
                location = " ligne " + locationToken.getLine() + " position " + locationToken.getPos();
            }

            MethodInfo invokedMethod = frame.GetInvokedMethod();
            if (invokedMethod != null) {
                System.err.println(" dans " + invokedMethod.GetClassInfo().GetName() + "." + invokedMethod.GetName() + "()" + location);
            } else {
                System.err.println(" dans le programme principal" + location);
            }
            frame = frame.GetPreviousFrame();
        }
    }

        private List<TId> GetParams(AArgs node)
        {
            this._idList = new LinkedList<>();
            Visit(node);
            List<TId> idList = this._idList;
            this._idList = null;
            return idList;
        }
}

