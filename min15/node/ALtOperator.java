/* This file was generated by SableCC (http://www.sablecc.org/). */

package min15.node;

import min15.analysis.*;

@SuppressWarnings("nls")
public final class ALtOperator extends POperator implements IOperator
{
    private TLt _lt_;

    public ALtOperator()
    {
        // Constructor
    }

    public ALtOperator(
        @SuppressWarnings("hiding") TLt _lt_)
    {
        // Constructor
        setLt(_lt_);

    }

    @Override
    public Object clone()
    {
        return new ALtOperator(
            cloneNode(this._lt_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseALtOperator(this);
    }

    public TLt getLt()
    {
        return this._lt_;
    }

    public void setLt(TLt node)
    {
        if(this._lt_ != null)
        {
            this._lt_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._lt_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._lt_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._lt_ == child)
        {
            this._lt_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._lt_ == oldChild)
        {
            setLt((TLt) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    public Token GetOperator()
    {
        return getLt();
    }
}
