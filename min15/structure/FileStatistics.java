package min15.structure;

import java.util.*;
/**
 * Created by Antoine-Ali on 02/04/2015.
 * Permanent Code ZARA20069408
 */
public class FileStatistics
{
    public String filePath;
    public Dictionary<String, Set<String>> classNames;

    public FileStatistics(String filePath)
    {
        this.filePath = filePath;
        this.classNames = new Hashtable<>();
    }
}
