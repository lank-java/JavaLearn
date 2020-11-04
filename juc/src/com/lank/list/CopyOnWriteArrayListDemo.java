package com.lank.list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author lank
 * @since 2020/10/31 16:15
 */
public class CopyOnWriteArrayListDemo {

    public static void main(String[] args) {

        //arrayList迭代不能修改元素
        //ArrayList<String> arrayList = new ArrayList<>();

        CopyOnWriteArrayList<String> arrayList = new CopyOnWriteArrayList<>();
        arrayList.add("1");
        arrayList.add("2");
        arrayList.add("3");
        arrayList.add("4");
        arrayList.add("5");

        Iterator<String> iterator = arrayList.iterator();
        while (iterator.hasNext()){
            System.out.println("list:"+arrayList);
            String next = iterator.next();
            System.out.println(next);

            if (next.equals("2")){
                arrayList.remove("5");
            }
            if (next.equals("3")){
                arrayList.add("3 found");
            }
        }

    }
}
