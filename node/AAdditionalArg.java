/* This file was generated by SableCC (http://www.sablecc.org/). */

package node;

import analysis.*;

@SuppressWarnings("nls")
public final class AAdditionalArg extends PAdditionalArg
{
    private TComma _comma_;
    private TEol _eol_;
    private PArg _arg_;

    public AAdditionalArg()
    {
        // Constructor
    }

    public AAdditionalArg(
        @SuppressWarnings("hiding") TComma _comma_,
        @SuppressWarnings("hiding") TEol _eol_,
        @SuppressWarnings("hiding") PArg _arg_)
    {
        // Constructor
        setComma(_comma_);

        setEol(_eol_);

        setArg(_arg_);

    }

    @Override
    public Object clone()
    {
        return new AAdditionalArg(
            cloneNode(this._comma_),
            cloneNode(this._eol_),
            cloneNode(this._arg_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAAdditionalArg(this);
    }

    public TComma getComma()
    {
        return this._comma_;
    }

    public void setComma(TComma node)
    {
        if(this._comma_ != null)
        {
            this._comma_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._comma_ = node;
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

    public PArg getArg()
    {
        return this._arg_;
    }

    public void setArg(PArg node)
    {
        if(this._arg_ != null)
        {
            this._arg_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._arg_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._comma_)
            + toString(this._eol_)
            + toString(this._arg_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._comma_ == child)
        {
            this._comma_ = null;
            return;
        }

        if(this._eol_ == child)
        {
            this._eol_ = null;
            return;
        }

        if(this._arg_ == child)
        {
            this._arg_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._comma_ == oldChild)
        {
            setComma((TComma) newChild);
            return;
        }

        if(this._eol_ == oldChild)
        {
            setEol((TEol) newChild);
            return;
        }

        if(this._arg_ == oldChild)
        {
            setArg((PArg) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
