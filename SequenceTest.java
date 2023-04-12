package proj2;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * Test cases for Sequence's methods
 */
public class SequenceTest {

    @Test   // Sequence that has default capacity = 10
    public void testDefaultConstructor(){
        Sequence data = new Sequence();
        assertEquals(0, data.size());
        assertEquals(10, data.getCapacity());
        assertEquals("{} (capacity = 10)", data.toString());
        assertEquals(false, data.isCurrent());
    }

    @Test   // Sequence that has initial capacity
    public void testGivenCapacityConstructor(){
        Sequence data = new Sequence(50);
        assertEquals(false, data.isCurrent());
        assertEquals(50, data.getCapacity());
        assertEquals(0, data.size());
        assertEquals("{} (capacity = 50)", data.toString());
    }

    @Test
    public void testAddBeforeWithNonEmptySequence(){
        Sequence data = new Sequence();
        data.addBefore("bla");
        data.addBefore("blo");
        data.addBefore("ble");
        assertEquals("{>ble, blo, bla} (capacity = 10)", data.toString());
    }

    @Test
    public void testAddBeforeWithEmptySequence(){
        Sequence data = new Sequence();
        data.addBefore("bla");
        assertEquals("{>bla} (capacity = 10)", data.toString());
    }

    @Test
    public void testAddBeforeWithNoCurrentElement(){
        Sequence data = new Sequence();
        data.addBefore("bla");
        data.addBefore("blo");
        data.advance();
        data.advance();
        data.addBefore("ble");
        assertEquals("{>ble, blo, bla} (capacity = 10)", data.toString());
    }

    @Test
    public void testAddBeforeToTheLastElement(){
        Sequence data = new Sequence();
        data.addBefore("bla");
        data.addBefore("blo");
        data.advance();
        data.addBefore("ble");
        assertEquals("{blo, >ble, bla} (capacity = 10)", data.toString());
    }

    @Test
    public void testAddBeforeWithMaxCapacity(){
        Sequence data = new Sequence(2);
        data.addBefore("bla");
        data.addBefore("blo");
        data.addBefore("ble");
        data.addBefore("bli");
        assertEquals("{>bli, ble, blo, bla} (capacity = 5)", data.toString());
    }

    @Test
    public void testAddAfterWithNonEmptySequence(){
        Sequence data = new Sequence();
        data.addAfter("bla");
        data.addAfter("blo");
        data.addAfter("ble");
        assertEquals("{bla, blo, >ble} (capacity = 10)", data.toString());
    }

    @Test
    public void testAddAfterWithFirstElement(){
        Sequence data = new Sequence();
        data.addAfter("bla");
        data.addAfter("blo");
        data.start();
        data.addAfter("ble");
        assertEquals("{bla, >ble, blo} (capacity = 10)", data.toString());
    }

    @Test
    public void testAddAfterMaxCapacity(){
        Sequence data = new Sequence(2);
        data.addAfter("bla");
        data.addAfter("blo");
        data.addAfter("ble");
        data.addAfter("bli");
        data.addAfter("blu");
        assertEquals("{bla, blo, ble, bli, >blu} (capacity = 5)", data.toString());
    }

    @Test
    public void testAddAfterWithEmptySequence(){
        Sequence data = new Sequence();
        data.addAfter("bla");
        assertEquals("{>bla} (capacity = 10)", data.toString());
    }

    @Test
    public void testAddAfterWithNoCurrentElement(){
        Sequence data = new Sequence();
        data.addAfter("bla");
        data.addAfter("blo");
        data.advance();
        data.addAfter("ble");
        assertEquals("{bla, blo, >ble} (capacity = 10)", data.toString());
    }

    @Test
    public void testGetCapacity(){
        Sequence data = new Sequence(19);
        assertEquals(19, data.getCapacity());
    }

    @Test
    public void testIsCurrentWithEmptySequence(){
        Sequence data = new Sequence(19);
        assertEquals(false, data.isCurrent());
    }

    @Test
    public void testIsCurrentAdvanceToEnd(){
        Sequence data = new Sequence();
        data.addAfter("bla");
        data.addAfter("blo");
        data.advance();
        assertEquals(false, data.isCurrent());
    }

    @Test
    public void testGetCurrentThatInvalid(){
        Sequence data = new Sequence();
        data.addAfter("bla");
        data.addAfter("blo");
        data.advance();
        assertEquals(null, data.getCurrent());
    }

    @Test
    public void testGetCurrentThatValid(){
        Sequence data = new Sequence();
        data.addAfter("bla");
        data.addAfter("blo");
        assertEquals("blo", data.getCurrent());
    }

    @Test
    public void testEnsureCapacityToALargerCapacity(){
        Sequence data = new Sequence();
        data.ensureCapacity(19);
        assertEquals(19, data.getCapacity());
    }

    @Test
    public void testEnsureCapacityToSmallerCapacity(){
        Sequence data = new Sequence(19);
        data.ensureCapacity(10);
        assertEquals(19, data.getCapacity());
    }

    @Test
    public void testAddAllNormalWithEmpty(){
        Sequence data1 = new Sequence();
        Sequence data2 = new Sequence();
        data1.addAfter("bla");
        data1.addAfter("blo");
        data1.addAfter("ble");
        data1.addAll(data2);
        assertEquals("{bla, blo, >ble} (capacity = 10)", data1.toString());
    }

    @Test
    public void testAddAllEmptyWithEmpty(){
        Sequence data1 = new Sequence();
        Sequence data2 = new Sequence();
        data1.addAll(data2);
        assertEquals("{} (capacity = 10)", data1.toString());
    }

    @Test
    public void testAddAllNormalNoCurrentWithNormalCurrent(){
        Sequence data1 = new Sequence();
        Sequence data2 = new Sequence();
        data1.addAfter("bla");
        data1.addAfter("ble");
        data1.advance();
        data2.addAfter("blo");
        data2.addAfter("bli");
        data1.addAll(data2);
        assertEquals("{bla, ble, blo, bli} (capacity = 10)", data1.toString());
    }

    @Test
    public void testAddAllNormalWithNormal(){
        Sequence data1 = new Sequence();
        Sequence data2 = new Sequence();
        data1.addAfter("bla");
        data2.addAfter("ble");
        data1.addAll(data2);
        assertEquals("{>bla, ble} (capacity = 10)", data1.toString());
    }

    @Test
    public void testAdvanceThatIsInvalid(){
        Sequence data = new Sequence();
        data.addBefore("bla");
        data.addBefore("ble");
        data.advance();
        data.advance();
        assertEquals("{ble, bla} (capacity = 10)", data.toString());
    }

    @Test
    public void testAdvanceThatIsValid(){
        Sequence data = new Sequence();
        data.addAfter("bla");
        data.addBefore("ble");
        data.advance();
        assertEquals("{ble, >bla} (capacity = 10)", data.toString());
    }

    @Test
    public void testCloneNormal(){
        Sequence data1 = new Sequence();
        data1.addBefore("bla");
        data1.addBefore("ble");
        Sequence data2 = data1.clone();
        assertEquals("{>ble, bla} (capacity = 10)", data2.toString());
    }

    @Test
    public void testCloneThatDoesntChangeOriginal(){
        Sequence data1 = new Sequence();
        data1.addBefore("bla");
        data1.addBefore("ble");
        Sequence data2 = data1.clone();
        data2.removeCurrent();
        assertEquals("{>bla} (capacity = 10)", data2.toString());
        assertEquals("{>ble, bla} (capacity = 10)", data1.toString());
    }

    @Test
    public void testRemoveCurrentNormal(){
        Sequence data = new Sequence();
        data.addBefore("bla");
        data.addBefore("ble");
        data.removeCurrent();
        assertEquals("{>bla} (capacity = 10)", data.toString());
    }

    @Test
    public void testRemoveCurrentNoCurrentElement(){
        Sequence data = new Sequence();
        data.addAfter("ble");
        data.addAfter("blo");
        data.advance();
        data.removeCurrent();
        assertEquals("{ble, blo} (capacity = 10)", data.toString());
    }

    @Test
    public void testRemoveCurrentLastElement(){
        Sequence data = new Sequence();
        data.addAfter("bla");
        data.addAfter("ble");
        data.removeCurrent();
        assertEquals("{bla} (capacity = 10)", data.toString());
    }

    @Test
    public void testSizeNormalCase(){
        Sequence data = new Sequence();
        data.addBefore("bla");
        data.addBefore("ble");
        data.addBefore("blo");
        assertEquals(3, data.size());
    }

    @Test
    public void testSizeEmptyCase(){
        Sequence data = new Sequence();
        assertEquals(0, data.size());
    }

    @Test
    public void testStartEmpty(){
        Sequence data = new Sequence();
        data.start();
        assertEquals(false, data.isCurrent());
    }

    @Test
    public void testStartNormal(){
        Sequence data = new Sequence();
        data.addAfter("bla");
        data.addAfter("blo");
        data.start();
        assertEquals("{>bla, blo} (capacity = 10)", data.toString());
    }

    @Test
    public void testTrimToSizeNormal(){
        Sequence data = new Sequence();
        data.addAfter("bla");
        data.addAfter("blo");
        data.addAfter("ble");
        data.trimToSize();
        assertEquals(3, data.getCapacity());
    }

    @Test
    public void testTrimToSizeWithSizeEqualsCapacity(){
        Sequence data = new Sequence(3);
        data.addAfter("bla");
        data.addAfter("blo");
        data.addAfter("ble");
        data.trimToSize();
        assertEquals(3, data.getCapacity());
    }

    @Test
    public void testToString(){
        Sequence data = new Sequence(3);
        data.addAfter("bla");
        data.addAfter("blo");
        data.addAfter("ble");
        data.trimToSize();
        assertEquals("{bla, blo, >ble} (capacity = 3)", data.toString());
    }

    @Test
    public void testEqualsWithDifferentCapacity(){
        Sequence data = new Sequence();
        data.addAfter("bla");
        data.addAfter("ble");
        Sequence data2 = new Sequence(15);
        data2.addAfter("bla");
        data2.addAfter("ble");
        assertEquals(true, data.equals(data2));
        assertEquals(true, data2.equals(data));
    }

    @Test
    public void testEqualsThatHaveDifferentSize(){
        Sequence data1 = new Sequence();
        data1.addAfter("bla");
        data1.addAfter("blo");
        data1.addAfter("ble");
        Sequence data2 = new Sequence(19);
        data2.addAfter("bla");
        data2.addAfter("blo");
        assertEquals(false, data1.equals(data2));
        assertEquals(false, data2.equals(data1));
    }

    @Test
    public void testEqualsTheSameCapacity(){
        Sequence data = new Sequence();
        data.addAfter("bla");
        data.addAfter("ble");
        Sequence data2 = data.clone();
        assertEquals(true, data.equals(data2));
        assertEquals(true, data2.equals(data));
    }

    @Test
    public void testEqualsSameElementDifferentCurrent(){
        Sequence data1 = new Sequence();
        data1.addAfter("bla");
        data1.addAfter("ble");
        data1.addAfter("blo");
        Sequence data2 = new Sequence();
        data2.addAfter("bla");
        data2.addAfter("blo");
        data2.addBefore("ble");
        assertEquals(false, data1.equals(data2));
        assertEquals(false, data2.equals(data1));
        assertEquals("{bla, ble, >blo} (capacity = 10)", data1.toString());
        assertEquals("{bla, >ble, blo} (capacity = 10)", data2.toString());
    }

    @Test
    public void isEmptyReturnTrue(){
        Sequence data = new Sequence(19);
        data.addAfter("1");
        data.removeCurrent();
        assertEquals(true, data.isEmpty());
    }

    @Test
    public void isEmptyReturnFalse(){
        Sequence data = new Sequence(19);
        data.addAfter("bla");
        assertEquals(false, data.isEmpty());
    }

    @Test
    public void clearEmpty(){
        Sequence seq1 = new Sequence();
        seq1.clear();
        assertEquals("{} (capacity = 10)", seq1.toString());
    }

    @Test
    public void clearNonEmpty(){
        Sequence data = new Sequence();
        data.addAfter("bla");
        data.addAfter("ble");
        data.clear();
        assertEquals("{} (capacity = 10)", data.toString());
    }
}
