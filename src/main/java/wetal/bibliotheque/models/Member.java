package wetal.bibliotheque.models;


public class Member implements Comparable<Member> {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String register_date;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getDate() {
        return register_date;
    }

    public String getRegister_date() { return register_date; }

    public Member(String id, String name, String email, String phone, String register_date) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.register_date = register_date;
    }

    @Override
    public int compareTo(Member o) {
        return this.name.compareToIgnoreCase(o.name);
    }
}
