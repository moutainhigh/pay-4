package com.pay.poss.util;

import com.beust.jcommander.internal.Lists;
import org.apache.commons.collections.ListUtils;

import java.util.List;

/**
 * Created by songyilin on 2016/10/13 0013.
 */
public class ListUtil {

    public static void main(String[] args) {
        List<String> list1 = Lists.newArrayList();
        list1.add("1");
        list1.add("3");

        List<String> list2 = Lists.newArrayList();
        list2.add("2");
        List<String> list3 = ListUtils.union(list1, list2);

        System.out.println(list3);
    }
}
