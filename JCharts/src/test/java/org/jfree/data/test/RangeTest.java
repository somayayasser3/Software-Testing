package org.jfree.data.test;

import org.jfree.data.Range;
import org.junit.*;

import javax.swing.plaf.basic.BasicBorders;
import java.security.InvalidParameterException;

public class RangeTest {
    Range ObjIntPositive1 =new Range(4,30);
    Range ObjIntPositive2=new Range(1,20);
    Range ObjIntNegative1 =new Range(-9,-1);
    Range ObjIntNegative2=new Range(-12,-3);
    Range ObjDoublePositive1=new Range(2.5,15.9);
    Range ObjDoublePositive2=new Range(0.1,7.3);
    Range ObjDoubleNegative1=new Range(-12.5,-7.9);
    Range ObjDoubleNegative2=new Range(-8.1,-7.3);

    @Test
    public void TestCombine()
    {

        Assert.assertEquals(ObjIntPositive2,Range.combine(null,ObjIntPositive2));
        Assert.assertEquals(ObjIntPositive1,Range.combine(ObjIntPositive1,null));
        Assert.assertNull(Range.combine(null,null));
        Assert.assertEquals(ObjDoublePositive2,Range.combine(null,ObjDoublePositive2));
        Assert.assertEquals(ObjDoublePositive1,Range.combine(ObjDoublePositive1,null));
        Assert.assertEquals(new Range(1,30),Range.combine(ObjIntPositive1,ObjIntPositive2)); //--> I get error as actual=[-1,20] not [-1,30] as it must return the minimum of 2 lowers as new lower range  and the maximum of 2 uppers as the new upper range but in the Range class , combine function returns the incorrect new upper Range as it returns the minimum of the 2 uppers not the maximum
        Assert.assertEquals(new Range(0.1,15.9),Range.combine(ObjDoublePositive1,ObjDoublePositive2));// actual=[0.1,7.3]
        Assert.assertEquals(new Range(-12,-1),Range.combine(ObjIntNegative1,ObjIntNegative2));// actual=[-12,-3]
        Assert.assertEquals(new Range(-12.5,-7.3),Range.combine(ObjDoubleNegative1,ObjDoubleNegative2)); //actual=[-12.5,-7.9]
    }
   @Test
   public void TestConstrain(){
       Assert.assertEquals(5,ObjIntPositive1.constrain(5),0);
       Assert.assertEquals(6.8,ObjIntPositive1.constrain(6.8),0);
       Assert.assertEquals(4,ObjIntPositive1.constrain(-2),0);
       Assert.assertEquals(30,ObjIntPositive1.constrain(35),0);

       Assert.assertEquals(-6,ObjIntNegative1.constrain(-6),0);
       Assert.assertEquals(-7.8,ObjIntNegative1.constrain(-7.8),0);
       Assert.assertEquals(-9,ObjIntNegative1.constrain(-11),0);
       Assert.assertEquals(-1,ObjIntNegative1.constrain(1),0);

       Assert.assertEquals(3.3,ObjDoublePositive1.constrain(3.3),0.000001);
       Assert.assertEquals(9,ObjDoublePositive1.constrain(9),0.000001);
       Assert.assertEquals(2.5,ObjDoublePositive1.constrain(-2),0.000001);
       Assert.assertEquals(15.9,ObjDoublePositive1.constrain(19.6),0.000001);

       Assert.assertEquals(-10.3,ObjDoubleNegative1.constrain(-10.3),0.000001);
       Assert.assertEquals(-9,ObjDoubleNegative1.constrain(-9),0.000001);
       Assert.assertEquals(-12.5,ObjDoubleNegative1.constrain(-13.5),0.000001);
       Assert.assertEquals(-7.9,ObjDoubleNegative1.constrain(-6.7),0.000001);
   }

    @Test
    public void TestContains() {

        Assert.assertTrue(ObjIntPositive1.contains(7));
        Assert.assertFalse(ObjIntPositive1.contains(-5));
        Assert.assertTrue(ObjIntPositive1.contains(4)); //--> this returns false although it must contain lower value as it behaves as the range is opened at lower value not closed like that = ]lower, upper]
        Assert.assertTrue(ObjIntPositive1.contains(4.0000001));
        Assert.assertTrue(ObjIntPositive1.contains(30));
        Assert.assertFalse(ObjIntPositive1.contains(31));

        Assert.assertTrue(ObjIntNegative1.contains(-7));
        Assert.assertFalse(ObjIntNegative1.contains(5));
        Assert.assertTrue(ObjIntNegative1.contains(-9)); //--> this returns false although it must contain lower value as it behaves as the range is opened at lower value not closed like that = ]lower, upper]
        Assert.assertTrue(ObjIntNegative1.contains(-8.99999999));
        Assert.assertTrue(ObjIntNegative1.contains(-1));
        Assert.assertFalse(ObjIntNegative1.contains(0));

        Assert.assertTrue(ObjDoublePositive1.contains(7.3));
        Assert.assertFalse(ObjDoublePositive1.contains(-5.9));
        Assert.assertTrue(ObjDoublePositive1.contains(2.5)); //--> this returns false although it must contain lower value as it behaves as the range is opened at lower value not closed like that = ]lower, upper]
        Assert.assertTrue(ObjDoublePositive1.contains(2.5111111111));
        Assert.assertTrue(ObjDoublePositive1.contains(15.9));
        Assert.assertFalse(ObjDoublePositive1.contains(16));

        Assert.assertTrue(ObjDoubleNegative1.contains(-8.3));
        Assert.assertFalse(ObjDoubleNegative1.contains(3.2));
        Assert.assertTrue(ObjDoubleNegative1.contains(-12.5)); //--> this returns false although it must contain lower value as it behaves as the range is opened at lower value not closed like that = ]lower, upper]
        Assert.assertTrue(ObjDoubleNegative1.contains(-12.499999999));
        Assert.assertTrue(ObjDoubleNegative1.contains(-7.9));
        Assert.assertFalse(ObjDoubleNegative1.contains(-7));
    }


    @Test
    public void TestEquals()
    {
        Range obj=new Range(12,19);
        Object refobj= new Range(12,19);
        Object refobj2= new Range(10,90);
        Object refobj3= new String("ABC");
        //Object refobj4 = null;
        //Range obj2 =null;
        Assert.assertTrue(obj.equals(refobj));
        Assert.assertFalse(obj.equals(refobj2));
        Assert.assertFalse(obj.equals(refobj3));
        //Assert.assertFalse(obj2.equals(refobj4));
    }

    @Test(expected = InvalidParameterException.class)
    public void TestExandNull()
    {
        Range.expand(null,0.25,0.5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestExandNull2()
    {
        Range.expand(null,0.25,0.5);
    }

    @Test
    public void TestExpand()
    {
        Range obj =new Range(2,6);
        Assert.assertEquals(new Range(1,8),Range.expand(obj,0.25,0.5));// --> it gets error as in lower bound the output is 38 as the equations implemented is lowerbound+ length*(uppermargin-lowermargin) but the correct is lowerbound - length*(uppermargin-lowermargin) so the range doesn't expand from the lower
        Assert.assertEquals(new Range(-11,3),Range.expand(ObjIntNegative1,0.25,0.5));// actual=[-7,3]
        Assert.assertEquals(new Range(0.85,22.6),Range.expand(ObjDoublePositive1,0.25,0.5));// actual=[5.85,22.6]
        Assert.assertEquals(new Range(-13.65,-5.6),Range.expand(ObjDoubleNegative1,0.25,0.5)); // actual=[-11.35,-5.600000000005]
    }

    @Test
    public void TestExpandToInclude()
    {
        Range obj2 = null;
        Assert.assertEquals(new Range(4,30),Range.expandToInclude(ObjIntPositive1,15));
        Assert.assertEquals(new Range(2,30),Range.expandToInclude(ObjIntPositive1,2));
        Assert.assertEquals(new Range(4,35),Range.expandToInclude(ObjIntPositive1,35));

        Assert.assertEquals(new Range(-9,-1),Range.expandToInclude(ObjIntNegative1,-5));
        Assert.assertEquals(new Range(-12,-1),Range.expandToInclude(ObjIntNegative1,-12));
        Assert.assertEquals(new Range(-9,0),Range.expandToInclude(ObjIntNegative1,0));

        Assert.assertEquals(new Range(2.5,15.9),Range.expandToInclude(ObjDoublePositive1,3.8));
        Assert.assertEquals(new Range(1.3,15.9),Range.expandToInclude(ObjDoublePositive1,1.3));
        Assert.assertEquals(new Range(2.5,16.1),Range.expandToInclude(ObjDoublePositive1,16.1));

        Assert.assertEquals(new Range(-12.5,-7.9),Range.expandToInclude(ObjDoubleNegative1,-9.3));
        Assert.assertEquals(new Range(-13.9,-7.9),Range.expandToInclude(ObjDoubleNegative1,-13.9));
        Assert.assertEquals(new Range(-12.5,-3.2),Range.expandToInclude(ObjDoubleNegative1,-3.2));

        Assert.assertEquals(new Range(4,4),Range.expandToInclude(obj2,4));
        Assert.assertEquals(new Range(-9,-9),Range.expandToInclude(obj2,-9));
        Assert.assertEquals(new Range(2.3,2.3),Range.expandToInclude(obj2,2.3));
        Assert.assertEquals(new Range(-7.6,-7.6),Range.expandToInclude(obj2,-7.6));
    }

    @Test
    public void TestCentral()
    {
        Assert.assertEquals(17,ObjIntPositive1.getCentralValue(),0);
        Assert.assertEquals(-5,ObjIntNegative1.getCentralValue(),0);
        Assert.assertEquals(9.2,ObjDoublePositive1.getCentralValue(),0.000001);
        Assert.assertEquals(-10.2,ObjDoubleNegative1.getCentralValue(),0.000001);
        Assert.assertEquals(5.5,new Range(2,9).getCentralValue(),0.000001);
    }

    @Test
    public void TestgetLowerBound(){
        Assert.assertEquals(4,ObjIntPositive1.getLowerBound(),0);
        Assert.assertEquals(-9,ObjIntNegative1.getLowerBound(),0);
        Assert.assertEquals(2.5,ObjDoublePositive1.getLowerBound(),0.000001);
        Assert.assertEquals(-12.5,ObjDoubleNegative1.getLowerBound(),0.000001);
    }

    @Test
    public void TestgetUpperBound(){
        Assert.assertEquals(30,ObjIntPositive1.getUpperBound(),0);
        Assert.assertEquals(-1,ObjIntNegative1.getUpperBound(),0);
        Assert.assertEquals(15.9,ObjDoublePositive1.getUpperBound(),0.000001);
        Assert.assertEquals(-7.9,ObjDoubleNegative1.getUpperBound(),0.000001);
    }

    @Test
    public void TestLength(){
        Assert.assertEquals(26,ObjIntPositive1.getLength(),0);
        Assert.assertEquals(8,ObjIntNegative1.getLength(),0);
        Assert.assertEquals(13.4,ObjDoublePositive1.getLength(),0.000001);
        Assert.assertEquals(4.6,ObjDoubleNegative1.getLength(),0.000001);
        Assert.assertEquals(0.2,new Range(7.9,8.1).getLength(),0.000001); // in this case , the Test case fails without using Delta so the actual will be = 0.19999999999993 not 0.2 as double is always inaccurate because of floating point.
    }

    @Test
    public void TestIntersect(){
        Assert.assertTrue(ObjIntPositive1.intersects(3,35));
        Assert.assertTrue(ObjIntPositive1.intersects(9,15));
        Assert.assertTrue(ObjIntPositive1.intersects(3,15));
        Assert.assertTrue(ObjIntPositive1.intersects(9,35));
        Assert.assertTrue(ObjIntPositive1.intersects(7,7));
        Assert.assertTrue(ObjIntPositive1.intersects(0,35));
        Assert.assertTrue(ObjIntPositive1.intersects(4,7));
        Assert.assertFalse(ObjIntPositive1.intersects(0,3)); //it didn't cover if b0,b1< lower bound so there is no intersection
        Assert.assertFalse(ObjIntPositive1.intersects(36,40)); //it didn't cover if b0,b1> upper bound so there is no intersection

        Assert.assertTrue(ObjIntNegative1.intersects(-10,1));
        Assert.assertTrue(ObjIntNegative1.intersects(-8,-2));
        Assert.assertTrue(ObjIntNegative1.intersects(-10,-5));
        Assert.assertTrue(ObjIntNegative1.intersects(-6,2));
        Assert.assertTrue(ObjIntNegative1.intersects(-5,-5));
        Assert.assertTrue(ObjIntNegative1.intersects(-9,0));
        Assert.assertTrue(ObjIntNegative1.intersects(-12,-1));
        Assert.assertFalse(ObjIntNegative1.intersects(-13,-10)); //it didn't cover if b0,b1< lower bound so there is no intersection
        Assert.assertFalse(ObjIntNegative1.intersects(0,6)); //it didn't cover if b0,b1> upper bound so there is no intersection

        Assert.assertTrue(ObjDoublePositive1.intersects(1.1,18.2));
        Assert.assertTrue(ObjDoublePositive1.intersects(3.6,13.2));
        Assert.assertTrue(ObjDoublePositive1.intersects(1.2,14.2));
        Assert.assertTrue(ObjDoublePositive1.intersects(3.9,17.3));
        Assert.assertTrue(ObjDoublePositive1.intersects(8.9,8.9));
        Assert.assertTrue(ObjDoublePositive1.intersects(2.5,20.9));
        Assert.assertTrue(ObjDoublePositive1.intersects(0,15.9));
        Assert.assertFalse(ObjDoublePositive1.intersects(1.2,2.3)); //it didn't cover if b0,b1< lower bound so there is no intersection
        Assert.assertFalse(ObjDoublePositive1.intersects(17.3,19.8)); //it didn't cover if b0,b1> upper bound so there is no intersection

        Assert.assertTrue(ObjDoubleNegative1.intersects(-13.9,-2.5));
        Assert.assertTrue(ObjDoubleNegative1.intersects(-10.4,-5.7));
        Assert.assertTrue(ObjDoubleNegative1.intersects(-15.2,-12.9));
        Assert.assertTrue(ObjDoubleNegative1.intersects(-9.5,-3.8));
        Assert.assertTrue(ObjDoubleNegative1.intersects(-8.6,-8.6));
        Assert.assertTrue(ObjDoubleNegative1.intersects(-12.5,-5.3));
        Assert.assertTrue(ObjDoubleNegative1.intersects(-17.3,-7.9));
        Assert.assertFalse(ObjDoubleNegative1.intersects(-17.3,14.9)); //it didn't cover if b0,b1< lower bound so there is no intersection
        Assert.assertFalse(ObjDoubleNegative1.intersects(-3.5,-1.9)); //it didn't cover if b0,b1> upper bound so there is no intersection
    }

    @Test(expected = InvalidParameterException.class)
    public void TestShiftNull()
    {
        Range.shift(null,3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestShiftNull2()
    {
        Range.shift(null,3);
    }

    @Test
    public void TestShift(){
        Assert.assertEquals(new Range(7,15),Range.shift(new Range(4,12),3));
        Assert.assertEquals(new Range(2,10),Range.shift(new Range(4,12),-2));
        Assert.assertEquals(new Range(-1,15),Range.shift(new Range(-4,12),3));
        Assert.assertEquals(new Range(-7,0),Range.shift(new Range(-10,-1),3));
        Assert.assertEquals(new Range(7,7),Range.shift(new Range(4,4),3));
        Assert.assertEquals(new Range(3,11),Range.shift(new Range(0,8),3));
        Assert.assertEquals(new Range(-7,3),Range.shift(new Range(-10,0),3));
        Assert.assertEquals(new Range(-4,0),Range.shift(new Range(0,3),-4));
    }
    @Test(expected = InvalidParameterException.class)
    public void TestShift2Null()
    {
        Range.shift(null,3,true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestShift2Null2()
    {
        Range.shift(null,3,true);
    }


    @Test
    public void TestShift2(){
        Assert.assertEquals(new Range(7,15),Range.shift(new Range(4,12),3,true));
        Assert.assertEquals(new Range(2,10),Range.shift(new Range(4,12),-2,true));
        Assert.assertEquals(new Range(-1,15),Range.shift(new Range(-4,12),3,true));
        Assert.assertEquals(new Range(-7,2),Range.shift(new Range(-10,-1),3,true));
        Assert.assertEquals(new Range(7,7),Range.shift(new Range(4,4),3,true));
        Assert.assertEquals(new Range(3,11),Range.shift(new Range(0,8),3,true));
        Assert.assertEquals(new Range(-7,3),Range.shift(new Range(-10,0),3,true));
        Assert.assertEquals(new Range(-4,-1),Range.shift(new Range(0,3),-4,true));
    }

}