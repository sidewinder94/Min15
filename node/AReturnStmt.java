/* This file was generated by SableCC (http://www.sablecc.org/). */

package node;

import java.util.*;
import analysis.*;

@SuppressWarnings("nls")
public final class AReturnStmt extends PStmt
{
    private final LinkedList<TEol> _eols_ = new LinkedList<TEol>();
    private TReturn _return_;
    private PExp _exp_;
    private TEol _eol_;

    public AReturnStmt()
    {
        // Constructor
    }

    public AReturnStmt(
        @SuppressWarnings("hiding") List<?> _eols_,
        @SuppressWarnings("hiding") TReturn _return_,
        @SuppressWarnings("hiding") PExp _exp_,
        @SuppressWarnings("hiding") TEol _eol_)
    {
        // Constructor
        setEols(_eols_);

        setReturn(_return_);

        setExp(_exp_);

        setEol(_eol_);

    }

    @Override
    public Object clone()
    {
        return new AReturnStmt(
            cloneList(this._eols_),
            cloneNode(this._return_),
            cloneNode(this._exp_),
            cloneNode(this._eol_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAReturnStmt(this);
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

    public TReturn getReturn()
    {
        return this._return_;
    }

    public void setReturn(TReturn node)
    {
        if(this._return_ != null)
        {
            this._return_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._return_ = node;
    }

    public PExp getExp()
    {
        return this._exp_;
    }

    public void setExp(PExp node)
    {
        if(this._exp_ != null)
        {
            this._exp_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._exp_ = node;
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
            + toString(this._return_)
            + toString(this._exp_)
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

        if(this._return_ == child)
        {
            this._return_ = null;
            return;
        }

        if(this._exp_ == child)
        {
            this._exp_ = null;
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

        if(this._return_ == oldChild)
        {
            setReturn((TReturn) newChild);
            return;
        }

        if(this._exp_ == oldChild)
        {
            setExp((PExp) newChild);
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