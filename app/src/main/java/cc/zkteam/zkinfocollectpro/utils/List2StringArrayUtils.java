package cc.zkteam.zkinfocollectpro.utils;

import java.util.Arrays;
import java.util.List;

/**
 * 将 List 和 String[] 数组  互转
 * Created by WangQing on 2017/12/30.
 */
public class List2StringArrayUtils {
    public static String[] list2StringArray(List<String> list) {
        return list.toArray(new String[list.size()]);
    }

    public static List<String> string2List(String[] strings) {
        return Arrays.asList(strings);
    }
}
