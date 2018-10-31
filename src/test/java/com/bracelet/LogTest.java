package com.bracelet;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        new LogTest().logger.info("hahaha");

        System.out.println(new Date(1504490729L));
    }
}
