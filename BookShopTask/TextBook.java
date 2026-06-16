public class TextBook extends Book {
    private int standard;

    public TextBook() {}

    public TextBook(String isbn, String title, String author, double price, int qty, int standard) {
        super(isbn, title, author, price, qty);
        this.standard = standard;
    }

    public void setStandard(int standard) {
        this.standard = standard;
    }

    public int getStandard() {
        return standard;
    }

    public void showDetails() {
        printCommonDetails("TextBook");
        System.out.println("Standard: " + standard);
        printSeparator();
    }
}
