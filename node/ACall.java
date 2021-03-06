/* This file was generated by SableCC (http://www.sablecc.org/). */

package node;

import analysis.*;

@SuppressWarnings("nls")
public final class ACall extends PCall
{
    private PRightUnaryExp _rightUnaryExp_;
    private TDot _dot_;
    private TEol _eol_;
    private TId _id_;
    private TLPar _lPar_;
    private PArgs _args_;
    private TRPar _rPar_;

    public ACall()
    {
        // Constructor
    }

    public ACall(
        @SuppressWarnings("hiding") PRightUnaryExp _rightUnaryExp_,
        @SuppressWarnings("hiding") TDot _dot_,
        @SuppressWarnings("hiding") TEol _eol_,
        @SuppressWarnings("hiding") TId _id_,
        @SuppressWarnings("hiding") TLPar _lPar_,
        @SuppressWarnings("hiding") PArgs _args_,
        @SuppressWarnings("hiding") TRPar _rPar_)
    {
        // Constructor
        setRightUnaryExp(_rightUnaryExp_);

        setDot(_dot_);

        setEol(_eol_);

        setId(_id_);

        setLPar(_lPar_);

        setArgs(_args_);

        setRPar(_rPar_);

    }

    @Override
    public Object clone()
    {
        return new ACall(
            cloneNode(this._rightUnaryExp_),
            cloneNode(this._dot_),
            cloneNode(this._eol_),
            cloneNode(this._id_),
            cloneNode(this._lPar_),
            cloneNode(this._args_),
            cloneNode(this._rPar_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseACall(this);
    }

    public PRightUnaryExp getRightUnaryExp()
    {
        return this._rightUnaryExp_;
    }

    public void setRightUnaryExp(PRightUnaryExp node)
    {
        if(this._rightUnaryExp_ != null)
        {
            this._rightUnaryExp_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._rightUnaryExp_ = node;
    }

    public TDot getDot()
    {
        return this._dot_;
    }

    public void setDot(TDot node)
    {
        if(this._dot_ != null)
        {
            this._dot_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._dot_ = node;
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

    public TLPar getLPar()
    {
        return this._lPar_;
    }

    public void setLPar(TLPar node)
    {
        if(this._lPar_ != null)
        {
            this._lPar_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._lPar_ = node;
    }

    public PArgs getArgs()
    {
        return this._args_;
    }

    public void setArgs(PArgs node)
    {
        if(this._args_ != null)
        {
            this._args_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._args_ = node;
    }

    public TRPar getRPar()
    {
        return this._rPar_;
    }

    public void setRPar(TRPar node)
    {
        if(this._rPar_ != null)
        {
            this._rPar_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._rPar_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._rightUnaryExp_)
            + toString(this._dot_)
            + toString(this._eol_)
            + toString(this._id_)
            + toString(this._lPar_)
            + toString(this._args_)
            + toString(this._rPar_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._rightUnaryExp_ == child)
        {
            this._rightUnaryExp_ = null;
            return;
        }

        if(this._dot_ == child)
        {
            this._dot_ = null;
            return;
        }

        if(this._eol_ == child)
        {
            this._eol_ = null;
            return;
        }

        if(this._id_ == child)
        {
            this._id_ = null;
            return;
        }

        if(this._lPar_ == child)
        {
            this._lPar_ = null;
            return;
        }

        if(this._args_ == child)
        {
            this._args_ = null;
            return;
        }

        if(this._rPar_ == child)
        {
            this._rPar_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._rightUnaryExp_ == oldChild)
        {
            setRightUnaryExp((PRightUnaryExp) newChild);
            return;
        }

        if(this._dot_ == oldChild)
        {
            setDot((TDot) newChild);
            return;
        }

        if(this._eol_ == oldChild)
        {
            setEol((TEol) newChild);
            return;
        }

        if(this._id_ == oldChild)
        {
            setId((TId) newChild);
            return;
        }

        if(this._lPar_ == oldChild)
        {
            setLPar((TLPar) newChild);
            return;
        }

        if(this._args_ == oldChild)
        {
            setArgs((PArgs) newChild);
            return;
        }

        if(this._rPar_ == oldChild)
        {
            setRPar((TRPar) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
