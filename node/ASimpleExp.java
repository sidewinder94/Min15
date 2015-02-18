/* This file was generated by SableCC (http://www.sablecc.org/). */

package node;

import analysis.*;

@SuppressWarnings("nls")
public final class ASimpleExp extends PExp
{
    private PConjunction _conjunction_;

    public ASimpleExp()
    {
        // Constructor
    }

    public ASimpleExp(
        @SuppressWarnings("hiding") PConjunction _conjunction_)
    {
        // Constructor
        setConjunction(_conjunction_);

    }

    @Override
    public Object clone()
    {
        return new ASimpleExp(
            cloneNode(this._conjunction_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseASimpleExp(this);
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

    @Override
    public String toString()
    {
        return ""
            + toString(this._conjunction_);
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

        throw new RuntimeException("Not a child.");
    }
}
