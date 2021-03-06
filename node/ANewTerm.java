/* This file was generated by SableCC (http://www.sablecc.org/). */

package node;

import analysis.*;

@SuppressWarnings("nls")
public final class ANewTerm extends PTerm
{
    private TNew _new_;
    private TClassName _className_;

    public ANewTerm()
    {
        // Constructor
    }

    public ANewTerm(
        @SuppressWarnings("hiding") TNew _new_,
        @SuppressWarnings("hiding") TClassName _className_)
    {
        // Constructor
        setNew(_new_);

        setClassName(_className_);

    }

    @Override
    public Object clone()
    {
        return new ANewTerm(
            cloneNode(this._new_),
            cloneNode(this._className_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseANewTerm(this);
    }

    public TNew getNew()
    {
        return this._new_;
    }

    public void setNew(TNew node)
    {
        if(this._new_ != null)
        {
            this._new_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._new_ = node;
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
            + toString(this._new_)
            + toString(this._className_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._new_ == child)
        {
            this._new_ = null;
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
        if(this._new_ == oldChild)
        {
            setNew((TNew) newChild);
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
