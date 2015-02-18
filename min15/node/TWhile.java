/* This file was generated by SableCC (http://www.sablecc.org/). */

package min15.node;

import min15.analysis.*;

@SuppressWarnings("nls")
public final class TWhile extends Token
{
    public TWhile()
    {
        super.setText("while");
    }

    public TWhile(int line, int pos)
    {
        super.setText("while");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TWhile(getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTWhile(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TWhile text.");
    }
}