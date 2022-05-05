package wetal.bibliotheque.object_holders;

import wetal.bibliotheque.models.Author;

public final class AuthorHolder {

    private Author author;
    private final static AuthorHolder INSTANCE = new AuthorHolder();

    AuthorHolder() {}

    public static AuthorHolder getInstance() {
        return INSTANCE;
    }

    public void setAuthor(Author a) {
        this.author = a;
    }

    public Author getAuthor() {
        return this.author;
    }
}
