/* This file was generated by SableCC (http://www.sablecc.org/). */

package min15.node;

import min15.analysis.*;

@SuppressWarnings("nls")
public final class AReturnStmt extends PStmt
{
    private TReturn _return_;
    private PExp _exp_;
    private TEol _eol_;

    public AReturnStmt()
    {
        // Constructor
    }

    public AReturnStmt(
        @SuppressWarnings("hiding") TReturn _return_,
        @SuppressWarnings("hiding") PExp _exp_,
        @SuppressWarnings("hiding") TEol _eol_)
    {
        // Constructor
        setReturn(_return_);

        setExp(_exp_);

        setEol(_eol_);

    }

    @Override
    public Object clone()
    {
        return new AReturnStmt(
            cloneNode(this._return_),
            cloneNode(this._exp_),
            cloneNode(this._eol_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAReturnStmt(this);
    }

    public TReturn getReturn()
    {
        return this._return_;
    }

    public void setReturn(TReturn node)
    {
        if(this._return_ != null)
        {
            this._return_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._return_ = node;
    }

    public PExp getExp()
    {
        return this._exp_;
    }

    public void setExp(PExp node)
    {
        if(this._exp_ != null)
        {
            this._exp_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._exp_ = node;
    }

    public TEol getEol()
    {
        return this._eol_;
    }

    public void setEol(TEol node)
    {
        if(this._eol_ != null)
        {
            this._eol_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._eol_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._return_)
            + toString(this._exp_)
            + toString(this._eol_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._return_ == child)
        {
            this._return_ = null;
            return;
        }

        if(this._exp_ == child)
        {
            this._exp_ = null;
            return;
        }

        if(this._eol_ == child)
        {
            this._eol_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._return_ == oldChild)
        {
            setReturn((TReturn) newChild);
            return;
        }

        if(this._exp_ == oldChild)
        {
            setExp((PExp) newChild);
            return;
        }

        if(this._eol_ == oldChild)
        {
            setEol((TEol) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}