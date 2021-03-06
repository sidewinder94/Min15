/* This file was generated by SableCC (http://www.sablecc.org/). */

package node;

import java.util.*;
import analysis.*;

@SuppressWarnings("nls")
public final class AMethodMember extends PMember
{
    private final LinkedList<TEol> _eols_ = new LinkedList<TEol>();
    private TFun _fun_;
    private TId _id_;
    private TLPar _lPar_;
    private PParams _params_;
    private TRPar _rPar_;
    private PReturnDecl _returnDecl_;
    private TEol _eol1_;
    private TDo _do_;
    private TEol _eol2_;
    private PStmts _stmts_;
    private TEnd _end_;
    private TEol _eol3_;

    public AMethodMember()
    {
        // Constructor
    }

    public AMethodMember(
        @SuppressWarnings("hiding") List<?> _eols_,
        @SuppressWarnings("hiding") TFun _fun_,
        @SuppressWarnings("hiding") TId _id_,
        @SuppressWarnings("hiding") TLPar _lPar_,
        @SuppressWarnings("hiding") PParams _params_,
        @SuppressWarnings("hiding") TRPar _rPar_,
        @SuppressWarnings("hiding") PReturnDecl _returnDecl_,
        @SuppressWarnings("hiding") TEol _eol1_,
        @SuppressWarnings("hiding") TDo _do_,
        @SuppressWarnings("hiding") TEol _eol2_,
        @SuppressWarnings("hiding") PStmts _stmts_,
        @SuppressWarnings("hiding") TEnd _end_,
        @SuppressWarnings("hiding") TEol _eol3_)
    {
        // Constructor
        setEols(_eols_);

        setFun(_fun_);

        setId(_id_);

        setLPar(_lPar_);

        setParams(_params_);

        setRPar(_rPar_);

        setReturnDecl(_returnDecl_);

        setEol1(_eol1_);

        setDo(_do_);

        setEol2(_eol2_);

        setStmts(_stmts_);

        setEnd(_end_);

        setEol3(_eol3_);

    }

    @Override
    public Object clone()
    {
        return new AMethodMember(
            cloneList(this._eols_),
            cloneNode(this._fun_),
            cloneNode(this._id_),
            cloneNode(this._lPar_),
            cloneNode(this._params_),
            cloneNode(this._rPar_),
            cloneNode(this._returnDecl_),
            cloneNode(this._eol1_),
            cloneNode(this._do_),
            cloneNode(this._eol2_),
            cloneNode(this._stmts_),
            cloneNode(this._end_),
            cloneNode(this._eol3_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAMethodMember(this);
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

    public TEol getEol1()
    {
        return this._eol1_;
    }

    public void setEol1(TEol node)
    {
        if(this._eol1_ != null)
        {
            this._eol1_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._eol1_ = node;
    }

    public TDo getDo()
    {
        return this._do_;
    }

    public void setDo(TDo node)
    {
        if(this._do_ != null)
        {
            this._do_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._do_ = node;
    }

    public TEol getEol2()
    {
        return this._eol2_;
    }

    public void setEol2(TEol node)
    {
        if(this._eol2_ != null)
        {
            this._eol2_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._eol2_ = node;
    }

    public PStmts getStmts()
    {
        return this._stmts_;
    }

    public void setStmts(PStmts node)
    {
        if(this._stmts_ != null)
        {
            this._stmts_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._stmts_ = node;
    }

    public TEnd getEnd()
    {
        return this._end_;
    }

    public void setEnd(TEnd node)
    {
        if(this._end_ != null)
        {
            this._end_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._end_ = node;
    }

    public TEol getEol3()
    {
        return this._eol3_;
    }

    public void setEol3(TEol node)
    {
        if(this._eol3_ != null)
        {
            this._eol3_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._eol3_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._eols_)
            + toString(this._fun_)
            + toString(this._id_)
            + toString(this._lPar_)
            + toString(this._params_)
            + toString(this._rPar_)
            + toString(this._returnDecl_)
            + toString(this._eol1_)
            + toString(this._do_)
            + toString(this._eol2_)
            + toString(this._stmts_)
            + toString(this._end_)
            + toString(this._eol3_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._eols_.remove(child))
        {
            return;
        }

        if(this._fun_ == child)
        {
            this._fun_ = null;
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

        if(this._eol1_ == child)
        {
            this._eol1_ = null;
            return;
        }

        if(this._do_ == child)
        {
            this._do_ = null;
            return;
        }

        if(this._eol2_ == child)
        {
            this._eol2_ = null;
            return;
        }

        if(this._stmts_ == child)
        {
            this._stmts_ = null;
            return;
        }

        if(this._end_ == child)
        {
            this._end_ = null;
            return;
        }

        if(this._eol3_ == child)
        {
            this._eol3_ = null;
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

        if(this._fun_ == oldChild)
        {
            setFun((TFun) newChild);
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

        if(this._eol1_ == oldChild)
        {
            setEol1((TEol) newChild);
            return;
        }

        if(this._do_ == oldChild)
        {
            setDo((TDo) newChild);
            return;
        }

        if(this._eol2_ == oldChild)
        {
            setEol2((TEol) newChild);
            return;
        }

        if(this._stmts_ == oldChild)
        {
            setStmts((PStmts) newChild);
            return;
        }

        if(this._end_ == oldChild)
        {
            setEnd((TEnd) newChild);
            return;
        }

        if(this._eol3_ == oldChild)
        {
            setEol3((TEol) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
