public abstract class Book implements BookOperations {
    private String isbn;
    private String bookTitle;
    private String authorName;
    private double price;
    private int availableQuantity;

    public Book() {}

    public Book(String isbn, String bookTitle, String authorName, double price, int availableQuantity) {
        this.isbn = isbn;
        this.bookTitle = bookTitle;
        this.authorName = authorName;
        this.price = price;
        this.availableQuantity = availableQuantity;
    }

    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }
    public void setPrice(double price) { this.price = price; }
    public void setAvailableQuantity(int availableQuantity) { this.availableQuantity = availableQuantity; }

    public String getIsbn() { return isbn; }
    public String getBookTitle() { return bookTitle; }
    public String getAuthorName() { return authorName; }
    public double getPrice() { return price; }
    public int getAvailableQuantity() { return availableQuantity; }
    
    @Override
    public void addQuantity(int amount) {
        availableQuantity += amount;
    }

    @Override
    public void sellQuantity(int amount) {
        if (amount <= availableQuantity) {
            availableQuantity -= amount;
        } else {
            System.out.println("Not enough stock!");
        }
    }

    protected void printCommonDetails(String bookType) {
        System.out.println(bookType + ":");
        System.out.println("ISBN: " + isbn);
        System.out.println("Title: " + bookTitle);
        System.out.println("Author: " + authorName);
        System.out.println("Price: " + price);
        System.out.println("Quantity: " + availableQuantity);
    }

    protected void printSeparator() {
        System.out.println("----------------------");
    }

    public abstract void showDetails();
}
