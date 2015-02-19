/* This file was generated by SableCC (http://www.sablecc.org/). */

package min15.node;

import min15.analysis.*;

@SuppressWarnings("nls")
public final class ASlashOperator extends POperator implements IOperator
{
    private TSlash _slash_;

    public ASlashOperator()
    {
        // Constructor
    }

    public ASlashOperator(
        @SuppressWarnings("hiding") TSlash _slash_)
    {
        // Constructor
        setSlash(_slash_);

    }

    @Override
    public Object clone()
    {
        return new ASlashOperator(
            cloneNode(this._slash_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseASlashOperator(this);
    }

    public TSlash getSlash()
    {
        return this._slash_;
    }

    public void setSlash(TSlash node)
    {
        if(this._slash_ != null)
        {
            this._slash_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._slash_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._slash_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._slash_ == child)
        {
            this._slash_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._slash_ == oldChild)
        {
            setSlash((TSlash) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    public Token GetOperator()
    {
        return getSlash();
    }
}
