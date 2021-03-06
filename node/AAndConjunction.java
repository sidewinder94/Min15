/* This file was generated by SableCC (http://www.sablecc.org/). */

package node;

import analysis.*;

@SuppressWarnings("nls")
public final class AAndConjunction extends PConjunction
{
    private PConjunction _conjunction_;
    private TAnd _and_;
    private TEol _eol_;
    private PComparison _comparison_;

    public AAndConjunction()
    {
        // Constructor
    }

    public AAndConjunction(
        @SuppressWarnings("hiding") PConjunction _conjunction_,
        @SuppressWarnings("hiding") TAnd _and_,
        @SuppressWarnings("hiding") TEol _eol_,
        @SuppressWarnings("hiding") PComparison _comparison_)
    {
        // Constructor
        setConjunction(_conjunction_);

        setAnd(_and_);

        setEol(_eol_);

        setComparison(_comparison_);

    }

    @Override
    public Object clone()
    {
        return new AAndConjunction(
            cloneNode(this._conjunction_),
            cloneNode(this._and_),
            cloneNode(this._eol_),
            cloneNode(this._comparison_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAAndConjunction(this);
    }

    public PConjunction getConjunction()
    {
        return this._conjunction_;
    }

    public void setConjunction(PConjunction node)
    {
        if(this._conjunction_ != null)
        {
            this._conjunction_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._conjunction_ = node;
    }

    public TAnd getAnd()
    {
        return this._and_;
    }

    public void setAnd(TAnd node)
    {
        if(this._and_ != null)
        {
            this._and_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._and_ = node;
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

    @Override
    public String toString()
    {
        return ""
            + toString(this._conjunction_)
            + toString(this._and_)
            + toString(this._eol_)
            + toString(this._comparison_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._conjunction_ == child)
        {
            this._conjunction_ = null;
            return;
        }

        if(this._and_ == child)
        {
            this._and_ = null;
            return;
        }

        if(this._eol_ == child)
        {
            this._eol_ = null;
            return;
        }

        if(this._comparison_ == child)
        {
            this._comparison_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._conjunction_ == oldChild)
        {
            setConjunction((PConjunction) newChild);
            return;
        }

        if(this._and_ == oldChild)
        {
            setAnd((TAnd) newChild);
            return;
        }

        if(this._eol_ == oldChild)
        {
            setEol((TEol) newChild);
            return;
        }

        if(this._comparison_ == oldChild)
        {
            setComparison((PComparison) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
