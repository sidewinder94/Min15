/* This file was generated by SableCC (http://www.sablecc.org/). */

package node;

import analysis.*;

@SuppressWarnings("nls")
public final class AGteqComparison extends PComparison
{
    private PComparison _comparison_;
    private TGteq _gteq_;
    private TEol _eol_;
    private PArithExp _arithExp_;

    public AGteqComparison()
    {
        // Constructor
    }

    public AGteqComparison(
        @SuppressWarnings("hiding") PComparison _comparison_,
        @SuppressWarnings("hiding") TGteq _gteq_,
        @SuppressWarnings("hiding") TEol _eol_,
        @SuppressWarnings("hiding") PArithExp _arithExp_)
    {
        // Constructor
        setComparison(_comparison_);

        setGteq(_gteq_);

        setEol(_eol_);

        setArithExp(_arithExp_);

    }

    @Override
    public Object clone()
    {
        return new AGteqComparison(
            cloneNode(this._comparison_),
            cloneNode(this._gteq_),
            cloneNode(this._eol_),
            cloneNode(this._arithExp_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAGteqComparison(this);
    }

    public PComparison getComparison()
    {
        return this._comparison_;
    }

    public void setComparison(PComparison node)
    {
        if(this._comparison_ != null)
        {
            this._comparison_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._comparison_ = node;
    }

    public TGteq getGteq()
    {
        return this._gteq_;
    }

    public void setGteq(TGteq node)
    {
        if(this._gteq_ != null)
        {
            this._gteq_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._gteq_ = node;
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

    public PArithExp getArithExp()
    {
        return this._arithExp_;
    }

    public void setArithExp(PArithExp node)
    {
        if(this._arithExp_ != null)
        {
            this._arithExp_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._arithExp_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._comparison_)
            + toString(this._gteq_)
            + toString(this._eol_)
            + toString(this._arithExp_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._comparison_ == child)
        {
            this._comparison_ = null;
            return;
        }

        if(this._gteq_ == child)
        {
            this._gteq_ = null;
            return;
        }

        if(this._eol_ == child)
        {
            this._eol_ = null;
            return;
        }

        if(this._arithExp_ == child)
        {
            this._arithExp_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._comparison_ == oldChild)
        {
            setComparison((PComparison) newChild);
            return;
        }

        if(this._gteq_ == oldChild)
        {
            setGteq((TGteq) newChild);
            return;
        }

        if(this._eol_ == oldChild)
        {
            setEol((TEol) newChild);
            return;
        }

        if(this._arithExp_ == oldChild)
        {
            setArithExp((PArithExp) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
