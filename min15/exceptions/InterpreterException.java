package min15.exceptions;

import node.Token;

/**
 * Created by Antoine-Ali on 18/02/2015.
 */
public class InterpreterException extends RuntimeException
{
    private final String _message;
    private final Token _token;

    public InterpreterException(String message, Token _token)
    {
        this._message = message;
        this._token = _token;
    }

    @Override
    public String getMessage()
    {
        if (this._token != null)
        {
            return this._message + " ligne  " + this._token.getLine() + " position " + this._token.getPos();
        }

        return this._message + " ligne 1 position 1";
    }
}
