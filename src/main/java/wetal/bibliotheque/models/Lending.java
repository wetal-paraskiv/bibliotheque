package wetal.bibliotheque.models;


public class Lending implements Comparable<Lending> {
    private String id; // member id
    private String name;
    private int cart_id;
    private String date; // cart date
    private String book_id;
    private String title;
    private Boolean book_duty;
    private String return_date;
    private int lending_days;

    public Lending(String id, String name, int cart_id, String date, String book_id,
                   String title, Boolean book_duty, String return_date, int lending_days) {
        this.id = id;
        this.name = name;
        this.cart_id = cart_id;
        this.date = date;
        this.book_id = book_id;
        this.title = title;
        this.book_duty = book_duty;
        this.return_date = return_date;
        this.lending_days = lending_days;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCart_id() {
        return cart_id;
    }

    public String getDate() {
        return date;
    }

    public String getBook_id() {
        return book_id;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getBook_duty() {
        return book_duty;
    }

    public String getReturn_date() {
        return return_date;
    }

    public int getLending_days() {
        return lending_days;
    }

    @Override
    public int compareTo(Lending o) {
        if (this.cart_id < o.cart_id) return -1;
        if (this.cart_id > o.cart_id) return 1;
        return 0;
    }
}
