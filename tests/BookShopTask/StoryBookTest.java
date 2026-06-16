import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("StoryBook Tests")
class StoryBookTest {

    @Test
    @DisplayName("Parameterized constructor sets all fields correctly")
    void constructorSetsFields() {
        StoryBook sb = new StoryBook("ISBN-001", "Golpo 1", "Author A", 200.0, 10, "Fiction");
        assertEquals("ISBN-001", sb.getIsbn());
        assertEquals("Golpo 1", sb.getBookTitle());
        assertEquals("Author A", sb.getAuthorName());
        assertEquals(200.0, sb.getPrice(), 0.001);
        assertEquals(10, sb.getAvailableQuantity());
        assertEquals("Fiction", sb.getCategory());
    }

    @Test
    @DisplayName("Default constructor creates object with null/default fields")
    void defaultConstructor() {
        StoryBook sb = new StoryBook();
        assertNull(sb.getIsbn());
        assertNull(sb.getBookTitle());
        assertNull(sb.getCategory());
        assertEquals(0, sb.getAvailableQuantity());
    }

    @Test
    @DisplayName("Category getter and setter work")
    void categoryGetterSetter() {
        StoryBook sb = new StoryBook();
        sb.setCategory("Drama");
        assertEquals("Drama", sb.getCategory());
    }

    @Test
    @DisplayName("Inherited setters from Book work on StoryBook")
    void inheritedSetters() {
        StoryBook sb = new StoryBook();
        sb.setIsbn("ISBN-999");
        sb.setBookTitle("New Title");
        sb.setAuthorName("New Author");
        sb.setPrice(350.0);
        sb.setAvailableQuantity(25);

        assertEquals("ISBN-999", sb.getIsbn());
        assertEquals("New Title", sb.getBookTitle());
        assertEquals("New Author", sb.getAuthorName());
        assertEquals(350.0, sb.getPrice(), 0.001);
        assertEquals(25, sb.getAvailableQuantity());
    }

    @Test
    @DisplayName("addQuantity increases stock")
    void addQuantityIncreasesStock() {
        StoryBook sb = new StoryBook("S1", "T", "A", 100, 5, "Fiction");
        sb.addQuantity(3);
        assertEquals(8, sb.getAvailableQuantity());
    }

    @Test
    @DisplayName("sellQuantity decreases stock when sufficient")
    void sellQuantityDecreases() {
        StoryBook sb = new StoryBook("S1", "T", "A", 100, 10, "Fiction");
        sb.sellQuantity(4);
        assertEquals(6, sb.getAvailableQuantity());
    }

    @Test
    @DisplayName("sellQuantity does not decrease stock when insufficient")
    void sellQuantityInsufficientStock() {
        StoryBook sb = new StoryBook("S1", "T", "A", 100, 2, "Fiction");
        sb.sellQuantity(5);
        assertEquals(2, sb.getAvailableQuantity());
    }

    @Test
    @DisplayName("showDetails runs without error")
    void showDetailsDoesNotThrow() {
        StoryBook sb = new StoryBook("S1", "Golpo", "Author", 200, 10, "Fiction");
        assertDoesNotThrow(sb::showDetails);
    }
}
