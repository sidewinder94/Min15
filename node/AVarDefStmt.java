/* This file was generated by SableCC (http://www.sablecc.org/). */

package node;

import analysis.*;

@SuppressWarnings("nls")
public final class AVarDefStmt extends PStmt
{
    private TVar _var_;
    private TId _id_;
    private TColon _colon_;
    private TClassName _className_;
    private TEol _eol_;

    public AVarDefStmt()
    {
        // Constructor
    }

    public AVarDefStmt(
        @SuppressWarnings("hiding") TVar _var_,
        @SuppressWarnings("hiding") TId _id_,
        @SuppressWarnings("hiding") TColon _colon_,
        @SuppressWarnings("hiding") TClassName _className_,
        @SuppressWarnings("hiding") TEol _eol_)
    {
        // Constructor
        setVar(_var_);

        setId(_id_);

        setColon(_colon_);

        setClassName(_className_);

        setEol(_eol_);

    }

    @Override
    public Object clone()
    {
        return new AVarDefStmt(
            cloneNode(this._var_),
            cloneNode(this._id_),
            cloneNode(this._colon_),
            cloneNode(this._className_),
            cloneNode(this._eol_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAVarDefStmt(this);
    }

    public TVar getVar()
    {
        return this._var_;
    }

    public void setVar(TVar node)
    {
        if(this._var_ != null)
        {
            this._var_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._var_ = node;
    }

    public TId getId()
    {
        return this._id_;
    }

    public void setId(TId node)
    {
        if(this._id_ != null)
        {
            this._id_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._id_ = node;
    }

    public TColon getColon()
    {
        return this._colon_;
    }

    public void setColon(TColon node)
    {
        if(this._colon_ != null)
        {
            this._colon_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._colon_ = node;
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
            + toString(this._var_)
            + toString(this._id_)
            + toString(this._colon_)
            + toString(this._className_)
            + toString(this._eol_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._var_ == child)
        {
            this._var_ = null;
            return;
        }

        if(this._id_ == child)
        {
            this._id_ = null;
            return;
        }

        if(this._colon_ == child)
        {
            this._colon_ = null;
            return;
        }

        if(this._className_ == child)
        {
            this._className_ = null;
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
        if(this._var_ == oldChild)
        {
            setVar((TVar) newChild);
            return;
        }

        if(this._id_ == oldChild)
        {
            setId((TId) newChild);
            return;
        }

        if(this._colon_ == oldChild)
        {
            setColon((TColon) newChild);
            return;
        }

        if(this._className_ == oldChild)
        {
            setClassName((TClassName) newChild);
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
