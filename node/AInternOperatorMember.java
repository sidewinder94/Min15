/* This file was generated by SableCC (http://www.sablecc.org/). */

package node;

import java.util.*;
import analysis.*;

@SuppressWarnings("nls")
public final class AInternOperatorMember extends PMember
{
    private final LinkedList<TEol> _eols_ = new LinkedList<TEol>();
    private TIntern _intern_;
    private TFun _fun_;
    private POperator _operator_;
    private TLPar _lPar_;
    private PParams _params_;
    private TRPar _rPar_;
    private PReturnDecl _returnDecl_;
    private TEol _eol_;

    public AInternOperatorMember()
    {
        // Constructor
    }

    public AInternOperatorMember(
        @SuppressWarnings("hiding") List<?> _eols_,
        @SuppressWarnings("hiding") TIntern _intern_,
        @SuppressWarnings("hiding") TFun _fun_,
        @SuppressWarnings("hiding") POperator _operator_,
        @SuppressWarnings("hiding") TLPar _lPar_,
        @SuppressWarnings("hiding") PParams _params_,
        @SuppressWarnings("hiding") TRPar _rPar_,
        @SuppressWarnings("hiding") PReturnDecl _returnDecl_,
        @SuppressWarnings("hiding") TEol _eol_)
    {
        // Constructor
        setEols(_eols_);

        setIntern(_intern_);

        setFun(_fun_);

        setOperator(_operator_);

        setLPar(_lPar_);

        setParams(_params_);

        setRPar(_rPar_);

        setReturnDecl(_returnDecl_);

        setEol(_eol_);

    }

    @Override
    public Object clone()
    {
        return new AInternOperatorMember(
            cloneList(this._eols_),
            cloneNode(this._intern_),
            cloneNode(this._fun_),
            cloneNode(this._operator_),
            cloneNode(this._lPar_),
            cloneNode(this._params_),
            cloneNode(this._rPar_),
            cloneNode(this._returnDecl_),
            cloneNode(this._eol_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAInternOperatorMember(this);
    }

    public LinkedList<TEol> getEols()
    {
        return this._eols_;
    }

    public void setEols(List<?> list)
    {
        for(TEol e : this._eols_)
        {
            e.parent(null);
        }
        this._eols_.clear();

        for(Object obj_e : list)
        {
            TEol e = (TEol) obj_e;
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
            this._eols_.add(e);
        }
    }

    public TIntern getIntern()
    {
        return this._intern_;
    }

    public void setIntern(TIntern node)
    {
        if(this._intern_ != null)
        {
            this._intern_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._intern_ = node;
    }

    public TFun getFun()
    {
        return this._fun_;
    }

    public void setFun(TFun node)
    {
        if(this._fun_ != null)
        {
            this._fun_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._fun_ = node;
    }

    public POperator getOperator()
    {
        return this._operator_;
    }

    public void setOperator(POperator node)
    {
        if(this._operator_ != null)
        {
            this._operator_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._operator_ = node;
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

    public PParams getParams()
    {
        return this._params_;
    }

    public void setParams(PParams node)
    {
        if(this._params_ != null)
        {
            this._params_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._params_ = node;
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

    public PReturnDecl getReturnDecl()
    {
        return this._returnDecl_;
    }

    public void setReturnDecl(PReturnDecl node)
    {
        if(this._returnDecl_ != null)
        {
            this._returnDecl_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._returnDecl_ = node;
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
            + toString(this._eols_)
            + toString(this._intern_)
            + toString(this._fun_)
            + toString(this._operator_)
            + toString(this._lPar_)
            + toString(this._params_)
            + toString(this._rPar_)
            + toString(this._returnDecl_)
            + toString(this._eol_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._eols_.remove(child))
        {
            return;
        }

        if(this._intern_ == child)
        {
            this._intern_ = null;
            return;
        }

        if(this._fun_ == child)
        {
            this._fun_ = null;
            return;
        }

        if(this._operator_ == child)
        {
            this._operator_ = null;
            return;
        }

        if(this._lPar_ == child)
        {
            this._lPar_ = null;
            return;
        }

        if(this._params_ == child)
        {
            this._params_ = null;
            return;
        }

        if(this._rPar_ == child)
        {
            this._rPar_ = null;
            return;
        }

        if(this._returnDecl_ == child)
        {
            this._returnDecl_ = null;
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
        for(ListIterator<TEol> i = this._eols_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((TEol) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        if(this._intern_ == oldChild)
        {
            setIntern((TIntern) newChild);
            return;
        }

        if(this._fun_ == oldChild)
        {
            setFun((TFun) newChild);
            return;
        }

        if(this._operator_ == oldChild)
        {
            setOperator((POperator) newChild);
            return;
        }

        if(this._lPar_ == oldChild)
        {
            setLPar((TLPar) newChild);
            return;
        }

        if(this._params_ == oldChild)
        {
            setParams((PParams) newChild);
            return;
        }

        if(this._rPar_ == oldChild)
        {
            setRPar((TRPar) newChild);
            return;
        }

        if(this._returnDecl_ == oldChild)
        {
            setReturnDecl((PReturnDecl) newChild);
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
