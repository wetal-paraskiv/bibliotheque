package wetal.bibliotheque.models;


public class Book implements Comparable<Book>{
    private String id;
    private String title;
    private String authors;
    private String publisher;
    private String category;
    private String language;
    private boolean presence;

    public Book(String title, String authors, String publisher, String category, String language, String id, boolean is_available) {
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.category = category;
        this.language = language;
        this.id = id;
        this.presence = is_available;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getCategory() {
        return category;
    }

    public String getLanguage() {
        return language;
    }

    public String getId() {
        return id;
    }

    public boolean isPresence() { return presence; }

    @Override
    public int compareTo(Book o) {
        return this.title.compareToIgnoreCase(o.title);
    }
}

