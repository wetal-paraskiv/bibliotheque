package wetal.bibliotheque.models;


public class Author implements Comparable<Author> {
    private String id;
    private String name;
    private String email;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Author(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    @Override
    public int compareTo(Author o) {
        return this.name.compareToIgnoreCase(o.name);
    }
}
