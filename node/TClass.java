/* This file was generated by SableCC (http://www.sablecc.org/). */

package node;

import analysis.*;

@SuppressWarnings("nls")
public final class TClass extends Token
{
    public TClass()
    {
        super.setText("class");
    }

    public TClass(int line, int pos)
    {
        super.setText("class");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TClass(getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTClass(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TClass text.");
    }
}
