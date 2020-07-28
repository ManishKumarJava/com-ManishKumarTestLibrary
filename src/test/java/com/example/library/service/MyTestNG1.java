package com.example.library.service;

import org.hibernate.result.Output;
import org.mockito.Mockito;

//public class MyTestNG1 {

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

    public class MyTestNG1 {

//        There is a difference in the behaviour of these frameworks:
//
//        JUnit creates a new instance of class for every of its test methods. This means that the fields are not shared between tests.
//        But TestNG creates only one object and thus the state in fields is shared between to @Tests
//        For Mockito you need to init mocks before every test method so that the state is not shared between two @Tests in TestNG:
//
//        @BeforeMethod
//        public void init() {
//            MockitoAnnotations.initMocks(this);
//        }
//        For JUnit it works out of box because 2nd @Test has its own fields and its own mocks.
/*
Output:
        before class
        before Method 1
        before Method 2
        method 1
        before Method 1
        before Method 2
        method 2
*/


        private CreateNumber createNumber = null;

        @BeforeClass
        public void init() {
            System.out.println("before class");
            createNumber = Mockito.mock(CreateNumber.class);
        }

        @BeforeMethod
        public void m1() {
            System.out.println("before Method 1");
        }

        @BeforeMethod
        public void m2() {
            System.out.println("before Method 2");
        }


        @Test
        public void testWith_TestNG() {
            System.out.println("method 1");
            int expected = 100;
            Mockito.when(createNumber.getThreeDigitNumber()).thenReturn(expected);

            int actual = createNumber.getThreeDigitNumber();
            Assert.assertEquals(actual, expected);
        }


        @Test
        public void testWith_TestNG2() {
            System.out.println("method 2");
            int expected = 100;
            Mockito.when(createNumber.getThreeDigitNumber()).thenReturn(expected);

            int actual = createNumber.getThreeDigitNumber();
            Assert.assertEquals(actual, expected);
        }

    }
