/* This file was generated by SableCC (http://www.sablecc.org/). */

package node;

import analysis.*;

@SuppressWarnings("nls")
public final class ASuperDecl extends PSuperDecl
{
    private TEol _eol_;
    private TSuper _super_;
    private TClassName _className_;

    public ASuperDecl()
    {
        // Constructor
    }

    public ASuperDecl(
        @SuppressWarnings("hiding") TEol _eol_,
        @SuppressWarnings("hiding") TSuper _super_,
        @SuppressWarnings("hiding") TClassName _className_)
    {
        // Constructor
        setEol(_eol_);

        setSuper(_super_);

        setClassName(_className_);

    }

    @Override
    public Object clone()
    {
        return new ASuperDecl(
            cloneNode(this._eol_),
            cloneNode(this._super_),
            cloneNode(this._className_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseASuperDecl(this);
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

    public TSuper getSuper()
    {
        return this._super_;
    }

    public void setSuper(TSuper node)
    {
        if(this._super_ != null)
        {
            this._super_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._super_ = node;
    }

    public TClassName getClassName()
    {
        return this._className_;
    }

    public void setClassName(TClassName node)
    {
        if(this._className_ != null)
        {
            this._className_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._className_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._eol_)
            + toString(this._super_)
            + toString(this._className_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._eol_ == child)
        {
            this._eol_ = null;
            return;
        }

        if(this._super_ == child)
        {
            this._super_ = null;
            return;
        }

        if(this._className_ == child)
        {
            this._className_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._eol_ == oldChild)
        {
            setEol((TEol) newChild);
            return;
        }

        if(this._super_ == oldChild)
        {
            setSuper((TSuper) newChild);
            return;
        }

        if(this._className_ == oldChild)
        {
            setClassName((TClassName) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}