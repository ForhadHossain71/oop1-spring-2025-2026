import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BookShop Tests")
class BookShopTest {

    private BookShop shop;
    private StoryBook story1;
    private TextBook text1;

    @BeforeEach
    void setUp() {
        shop = new BookShop("Test Shop");
        story1 = new StoryBook("S1", "Golpo 1", "Author A", 200, 10, "Fiction");
        text1 = new TextBook("T1", "Math", "Author X", 400, 10, 10);
    }

    @Test
    @DisplayName("Constructor sets shop name")
    void constructorSetsName() {
        assertEquals("Test Shop", shop.getName());
    }

    @Test
    @DisplayName("Default constructor creates shop with null name")
    void defaultConstructor() {
        BookShop s = new BookShop();
        assertNull(s.getName());
    }

    @Test
    @DisplayName("Name setter and getter work")
    void nameGetterSetter() {
        shop.setName("New Name");
        assertEquals("New Name", shop.getName());
    }

    @Test
    @DisplayName("insertBook adds book and returns true")
    void insertBookSuccess() {
        assertTrue(shop.insertBook(story1));
    }

    @Test
    @DisplayName("insertBook fills all slots and returns false when full")
    void insertBookFull() {
        Book[] full = new Book[2];
        full[0] = story1;
        full[1] = text1;
        shop.setListOfBooks(full);

        StoryBook extra = new StoryBook("S2", "X", "Y", 100, 1, "Drama");
        assertFalse(shop.insertBook(extra));
    }

    @Test
    @DisplayName("removeBook removes existing book and returns true")
    void removeBookSuccess() {
        shop.insertBook(story1);
        assertTrue(shop.removeBook(story1));
    }

    @Test
    @DisplayName("removeBook returns false for non-existent book")
    void removeBookNotFound() {
        assertFalse(shop.removeBook(story1));
    }

    @Test
    @DisplayName("searchBook finds book by ISBN")
    void searchBookFound() {
        shop.insertBook(story1);
        shop.insertBook(text1);
        Book found = shop.searchBook("T1");
        assertNotNull(found);
        assertEquals("Math", found.getBookTitle());
    }

    @Test
    @DisplayName("searchBook returns null for unknown ISBN")
    void searchBookNotFound() {
        shop.insertBook(story1);
        assertNull(shop.searchBook("UNKNOWN"));
    }

    @Test
    @DisplayName("getListOfBooks returns the internal array")
    void getListOfBooks() {
        shop.insertBook(story1);
        Book[] books = shop.getListOfBooks();
        assertNotNull(books);
        assertSame(story1, books[0]);
    }

    @Test
    @DisplayName("showAllBooks runs without error")
    void showAllBooksDoesNotThrow() {
        shop.insertBook(story1);
        shop.insertBook(text1);
        assertDoesNotThrow(shop::showAllBooks);
    }

    @Test
    @DisplayName("Multiple inserts and search across different book types")
    void insertAndSearchMultiple() {
        StoryBook s2 = new StoryBook("S2", "Golpo 2", "Author B", 250, 5, "Drama");
        TextBook t2 = new TextBook("T2", "Physics", "Author Y", 450, 8, 11);

        shop.insertBook(story1);
        shop.insertBook(s2);
        shop.insertBook(text1);
        shop.insertBook(t2);

        assertEquals("Golpo 2", shop.searchBook("S2").getBookTitle());
        assertEquals("Physics", shop.searchBook("T2").getBookTitle());
        assertNull(shop.searchBook("S3"));
    }

    @Test
    @DisplayName("Remove then search returns null")
    void removeAndSearch() {
        shop.insertBook(story1);
        shop.removeBook(story1);
        assertNull(shop.searchBook("S1"));
    }
}
