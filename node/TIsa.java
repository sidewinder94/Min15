/* This file was generated by SableCC (http://www.sablecc.org/). */

package node;

import analysis.*;

@SuppressWarnings("nls")
public final class TIsa extends Token
{
    public TIsa()
    {
        super.setText("isa");
    }

    public TIsa(int line, int pos)
    {
        super.setText("isa");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TIsa(getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTIsa(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TIsa text.");
    }
}
