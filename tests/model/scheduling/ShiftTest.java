package model.scheduling;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ShiftTest {
    AlphaShift alpha;
    BetaShift beta;
    GammaShift gamma;
    DeltaShift delta;

    @Before
    public void setUp(){
        alpha = new AlphaShift(Department.BRIDGE);
        beta = new BetaShift(Department.BRIDGE);
        gamma = new GammaShift(Department.BRIDGE);
        delta = new DeltaShift(Department.BRIDGE);
    }

    @Test
    public void testToString() {
        //"%s Shift: %s\n\t%d - %d\n"
        String expectedAlpha = "Alpha Shift: Bridge\n\t06:00 - 12:00\n";
        assertEquals(expectedAlpha, alpha.toString());

        String expectedBeta = "Beta Shift: Bridge\n\t12:00 - 18:00\n";
        assertEquals(expectedBeta, beta.toString());

        String expectedGamma = "Gamma Shift: Bridge\n\t18:00 - 00:00\n";
        assertEquals(expectedGamma, gamma.toString());

        String expectedDelta = "Delta Shift: Bridge\n\t00:00 - 06:00\n";
        assertEquals(expectedDelta, delta.toString());
    }

    @Test
    public void testGetAndSetDepartment() {
        assertEquals(Department.BRIDGE, alpha.getDepartment());
        alpha.setDepartment(Department.MEDICAL_BAY);
        assertEquals(Department.MEDICAL_BAY, alpha.getDepartment());
        alpha.setDepartment(Department.ENGINEERING);
        assertEquals(Department.ENGINEERING, alpha.getDepartment());
        alpha.setDepartment(Department.SCIENCE);
        assertEquals(Department.SCIENCE, alpha.getDepartment());
        alpha.setDepartment(Department.SECURITY);
        assertEquals(Department.SECURITY, alpha.getDepartment());
    }

    @Test
    public void testGetNumHours() {
        assertEquals(6, alpha.getNumHours(), 0.01);
        assertEquals(6, beta.getNumHours(), 0.01);
        assertEquals(6, gamma.getNumHours(), 0.01);
        assertEquals(6, delta.getNumHours(), 0.01);
    }

    @Test
    public void testGetAndSetStart() {
    }

    @Test
    public void testGetAndSetEnd() {
    }

    @Test
    public void testGetAndSetBoth() {
    }


    @Test
    public void testGetAndSetShiftName() {
    }

}