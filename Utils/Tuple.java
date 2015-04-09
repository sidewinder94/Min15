package Utils;

/**
 * Created by Antoine-Ali on 01/04/2015.
 * Permanent Code ZARA20069408
 */
public class Tuple<L, R>
{
    public final L left;
    public final R right;

    public <T extends L, U extends R> Tuple(T left, U right)
    {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString()
    {
        return "Tuple<" + left.getClass().getName() + ", " + right.getClass().getName() + "> : [" + left.toString() + ", " + right.toString() + "]";
    }
}
