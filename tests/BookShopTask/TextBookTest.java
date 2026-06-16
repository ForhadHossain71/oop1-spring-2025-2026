import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TextBook Tests")
class TextBookTest {

    @Test
    @DisplayName("Parameterized constructor sets all fields correctly")
    void constructorSetsFields() {
        TextBook tb = new TextBook("ISBN-T1", "Math", "Author X", 400.0, 10, 10);
        assertEquals("ISBN-T1", tb.getIsbn());
        assertEquals("Math", tb.getBookTitle());
        assertEquals("Author X", tb.getAuthorName());
        assertEquals(400.0, tb.getPrice(), 0.001);
        assertEquals(10, tb.getAvailableQuantity());
        assertEquals(10, tb.getStandard());
    }

    @Test
    @DisplayName("Default constructor creates object with null/default fields")
    void defaultConstructor() {
        TextBook tb = new TextBook();
        assertNull(tb.getIsbn());
        assertNull(tb.getBookTitle());
        assertEquals(0, tb.getStandard());
        assertEquals(0, tb.getAvailableQuantity());
    }

    @Test
    @DisplayName("Standard getter and setter work")
    void standardGetterSetter() {
        TextBook tb = new TextBook();
        tb.setStandard(12);
        assertEquals(12, tb.getStandard());
    }

    @Test
    @DisplayName("Inherited setters from Book work on TextBook")
    void inheritedSetters() {
        TextBook tb = new TextBook();
        tb.setIsbn("ISBN-T99");
        tb.setBookTitle("Physics");
        tb.setAuthorName("Author Z");
        tb.setPrice(500.0);
        tb.setAvailableQuantity(15);

        assertEquals("ISBN-T99", tb.getIsbn());
        assertEquals("Physics", tb.getBookTitle());
        assertEquals("Author Z", tb.getAuthorName());
        assertEquals(500.0, tb.getPrice(), 0.001);
        assertEquals(15, tb.getAvailableQuantity());
    }

    @Test
    @DisplayName("addQuantity increases stock")
    void addQuantityIncreasesStock() {
        TextBook tb = new TextBook("T1", "T", "A", 100, 5, 10);
        tb.addQuantity(10);
        assertEquals(15, tb.getAvailableQuantity());
    }

    @Test
    @DisplayName("sellQuantity decreases stock when sufficient")
    void sellQuantityDecreases() {
        TextBook tb = new TextBook("T1", "T", "A", 100, 10, 10);
        tb.sellQuantity(7);
        assertEquals(3, tb.getAvailableQuantity());
    }

    @Test
    @DisplayName("sellQuantity does not decrease stock when insufficient")
    void sellQuantityInsufficientStock() {
        TextBook tb = new TextBook("T1", "T", "A", 100, 3, 10);
        tb.sellQuantity(5);
        assertEquals(3, tb.getAvailableQuantity());
    }

    @Test
    @DisplayName("sellQuantity exact amount leaves zero stock")
    void sellQuantityExactAmount() {
        TextBook tb = new TextBook("T1", "T", "A", 100, 5, 10);
        tb.sellQuantity(5);
        assertEquals(0, tb.getAvailableQuantity());
    }

    @Test
    @DisplayName("showDetails runs without error")
    void showDetailsDoesNotThrow() {
        TextBook tb = new TextBook("T1", "Math", "Author", 400, 10, 10);
        assertDoesNotThrow(tb::showDetails);
    }
}
