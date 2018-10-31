package com.bracelet;

import com.bracelet.util.Utils;

public class UtilsTest {

    public static void main(String[] args) {
        System.out.println(Utils.randomInt(5,20));
        System.out.println(Utils.randomInt(1,20));
        System.out.println(Utils.randomInt(5,200));
        System.out.println(Utils.randomString(10));
        System.out.println(Utils.randomString(5));
        System.out.println(Utils.randomString(0));

        System.out.println(Utils.getMD5("123456"));
    }

}
