package wetal.bibliotheque.object_holders;

import wetal.bibliotheque.models.Book;

public final class BookHolder {

    private Book book;
    private final static BookHolder INSTANCE = new BookHolder();

    BookHolder() {}

    public static BookHolder getInstance() {
        return INSTANCE;
    }

    public void setBook(Book b) {
        this.book = b;
    }

    public Book getBook() {
        return this.book;
    }
}