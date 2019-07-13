package com.jtw.main.controller;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

public class Test {
    public static void main(String[] args) {
        Enumeration drivers = DriverManager.getDrivers();

        while(drivers.hasMoreElements()) {
            Driver driver = (Driver)drivers.nextElement();
            System.out.println((driver.getClass().getName() + ", diver=" + driver));
        }
    }
}
