public class StoryBook extends Book {
    private String category;

    public StoryBook() {}

    public StoryBook(String isbn, String title, String author, double price, int qty, String category) {
        super(isbn, title, author, price, qty);
        this.category = category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void showDetails() {
        printCommonDetails("StoryBook");
        System.out.println("Category: " + category);
        printSeparator();
    }
}
