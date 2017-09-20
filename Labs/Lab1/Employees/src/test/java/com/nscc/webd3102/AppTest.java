package com.nscc.webd3102;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        Employee JaneDoe = new Employee("Jane Doe","HR");
        Employee YeahHuang = new Employee("Yeah Huang","IT");
        Employee JohnPublic = new Employee("John Public","IT",10000000);
        Employee KyleMa = new Employee("Kyle Ma","IT",10000000);
        Employee AlisonSmith = new Employee("Alison Smith","Accounting",0);
        Employee XavierJones = new Employee("Xavier Jones","CEO",-5);


        //test null is less than positive
        assertTrue(JaneDoe.compareTo(JohnPublic)<0);
        //test null is less than 0
        assertTrue(JaneDoe.compareTo(AlisonSmith)<0);
        //test null is less than negative
        assertTrue(JaneDoe.compareTo(XavierJones)<0);
        //test 0 is bigger than negative
        assertTrue(AlisonSmith.compareTo(XavierJones)>0);
        //test positive bigger than 0
        assertTrue(JohnPublic.compareTo(AlisonSmith)>0);
        //test positive is bigger than negative
        assertTrue(JohnPublic.compareTo(XavierJones)>0);
        //test same salary sort by names
        assertTrue(KyleMa.compareTo(JohnPublic)<0);
        //test null compare to null
        assertTrue(YeahHuang.compareTo(JohnPublic)<0);




    }
}
