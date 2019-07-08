package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DistanceBtwPointsTests {

    @Test
    public void testDistanceBtwPoints(){
        Point p1 = new Point(1,2);
        Point p2 = new Point(-1,-2);

        Assert.assertEquals(p1.distance(p2), 4.47213595499958);
    }
}
