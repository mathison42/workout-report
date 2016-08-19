package com.seven.discs.tests;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FirstTest {
   @BeforeClass
   public static void beforeClassTest() {
       System.out.println("Before Class Test Sample");
   }
    
   @AfterClass
   public static void afterClassTest() {
       System.out.println("After Class Test Sample");
   }
   
   @Before
   public void beforeTest() {
       System.out.println("Before Test Sample");
   }
   
   @After
   public void afterTest() {
       System.out.println("After Test Sample");
   }
    
   @Test
   public void test() {
		System.out.println("Test Run!");
   }
}