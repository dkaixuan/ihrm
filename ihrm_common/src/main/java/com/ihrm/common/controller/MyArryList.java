package com.ihrm.common.controller;

import jdk.nashorn.internal.ir.IfNode;
import org.hibernate.engine.jdbc.Size;

import java.util.ArrayList;

/**
 * @author kaixuan
 * @version 1.0
 * @date 2020/3/3 21:50
 */
public class MyArryList {



    private Object [] elementData;

    private int size;

    public MyArryList() {
        elementData = new Object[5];
    }

    public void add(Object o) {
        if (size >= elementData.length) {
           //int a= (int)(elementData.length*1.5);
            Object[] temp = new Object[elementData.length*2];
            System.arraycopy(elementData, 0, temp, 0,size);
            elementData=temp;

        }
        elementData[size++]=o;
    }

    public int size() {
        return this.size;
    }


    public static void main(String[] args) {
        MyArryList myArryList = new MyArryList();
        myArryList.add(1);
        myArryList.add(2);
        myArryList.add(3);
        myArryList.add(4);
        myArryList.add(5);
        myArryList.add(7);
        myArryList.add(8);
        myArryList.add(9);
        myArryList.add(10);
        System.out.println(myArryList.size);
    }


}
