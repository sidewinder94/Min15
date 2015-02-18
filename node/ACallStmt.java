/* This file was generated by SableCC (http://www.sablecc.org/). */

package node;

import analysis.*;

@SuppressWarnings("nls")
public final class ACallStmt extends PStmt
{
    private PCall _call_;
    private TEol _eol_;

    public ACallStmt()
    {
        // Constructor
    }

    public ACallStmt(
        @SuppressWarnings("hiding") PCall _call_,
        @SuppressWarnings("hiding") TEol _eol_)
    {
        // Constructor
        setCall(_call_);

        setEol(_eol_);

    }

    @Override
    public Object clone()
    {
        return new ACallStmt(
            cloneNode(this._call_),
            cloneNode(this._eol_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseACallStmt(this);
    }

    public PCall getCall()
    {
        return this._call_;
    }

    public void setCall(PCall node)
    {
        if(this._call_ != null)
        {
            this._call_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._call_ = node;
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
            + toString(this._call_)
            + toString(this._eol_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._call_ == child)
        {
            this._call_ = null;
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
        if(this._call_ == oldChild)
        {
            setCall((PCall) newChild);
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
