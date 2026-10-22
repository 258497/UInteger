package org.example;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UIntegerTest {

    @Test
    public void testBoundaryValuesCreation() {
        UInteger min = UInteger.valueOf(0L);
        UInteger max = UInteger.valueOf(4294967295L);

        assertEquals(0L, min.longValue());
        assertEquals(4294967295L, max.longValue());
    }

    @Test
    public void testOutOfBoundValuesCreation() {
        assertThrows(IllegalArgumentException.class, () -> UInteger.valueOf(-1L));
        assertThrows(IllegalArgumentException.class, () -> UInteger.valueOf(4294967296L));
    }

    @Test
    public void testAddition() {
        UInteger a = UInteger.valueOf(100);
        UInteger b = UInteger.valueOf(200);

        assertEquals(300L, a.add(b).longValue());
    }

    @Test
    public void testAdditionOverflow() {
        UInteger max = UInteger.valueOf(UInteger.MAX_VALUE);
        UInteger one = UInteger.valueOf(1L);

        assertThrows(ArithmeticException.class, () -> max.add(one));
    }

    @Test
    public void testSubtraction() {
        UInteger a = UInteger.valueOf(500);
        UInteger b = UInteger.valueOf(200);

        assertEquals(300L, a.subtract(b).longValue());
    }

    @Test
    public void testSubtractionUnderflow() {
        UInteger small = UInteger.valueOf(10);
        UInteger large = UInteger.valueOf(20);

        assertThrows(ArithmeticException.class, () -> small.subtract(large));
    }

    @Test
    public void testMultiplication() {
        UInteger a = UInteger.valueOf(50);
        UInteger b = UInteger.valueOf(4);

        assertEquals(200L, a.multiply(b).longValue());
    }

    @Test
    public void testMultiplicationOverflow() {
        UInteger max = UInteger.valueOf(UInteger.MAX_VALUE);
        UInteger two = UInteger.valueOf(2L);

        assertThrows(ArithmeticException.class, () -> max.multiply(two));
    }

    @Test
    public void testDivisionAndRemainder() {
        UInteger a = UInteger.valueOf(10);
        UInteger b = UInteger.valueOf(3);

        assertEquals(3L, a.divide(b).longValue());
        assertEquals(1L, a.remainder(b).longValue());
    }

    @Test
    public void testDivisionByZero() {
        UInteger a = UInteger.valueOf(10);
        UInteger zero = UInteger.valueOf(0);

        assertThrows(ArithmeticException.class, () -> a.divide(zero));
        assertThrows(ArithmeticException.class, () -> a.remainder(zero));
    }

    @Test
    public void testParsingValidAndInvalidStrings() {
        UInteger valid = UInteger.valueOf("4294967295");

        assertEquals(UInteger.MAX_VALUE, valid.longValue());
        assertThrows(NumberFormatException.class, () -> UInteger.valueOf("4294967296"));
        assertThrows(NumberFormatException.class, () -> UInteger.valueOf("-1"));
        assertThrows(NumberFormatException.class, () -> UInteger.valueOf("abc"));
    }

    @Test
    public void testComparison() {
        UInteger a = UInteger.valueOf(100);
        UInteger b = UInteger.valueOf(200);
        UInteger max = UInteger.valueOf(UInteger.MAX_VALUE);

        assertTrue(a.compareTo(b) < 0);
        assertTrue(b.compareTo(a) > 0);
        assertEquals(0, a.compareTo(UInteger.valueOf(100)));
        assertTrue(max.compareTo(a) > 0);
    }

    @Test
    public void testNumberConversions() {
        UInteger max = UInteger.valueOf(UInteger.MAX_VALUE);

        assertEquals(4294967295L, max.longValue());
        assertEquals(-1, max.intValue());
        assertEquals((short) -1, max.shortValue());
        assertEquals((byte) -1, max.byteValue());
        assertEquals(4294967295.0f, max.floatValue(), 0.001);
        assertEquals(4294967295.0, max.doubleValue(), 0.001);
    }

    @Test
    public void testEqualsAndHashCode() {
        UInteger a = UInteger.valueOf(12345);
        UInteger b = UInteger.valueOf(12345);
        UInteger c = UInteger.valueOf(54321);

        assertEquals(a, b);
        assertNotEquals(a, c);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void testTextRepresentation() {
        UInteger val = UInteger.valueOf(255);

        assertEquals("255", val.toString());
        assertEquals("ff", val.toString(16));

        UInteger max = UInteger.valueOf(UInteger.MAX_VALUE);
        assertEquals("4294967295", max.toString());
        assertEquals("ffffffff", max.toString(16));
    }
}