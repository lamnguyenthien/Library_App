package lam.tutorials.libraryapp.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "book")
public class Book {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String author;
    private String publish_date;
    private String publish_comp;
    private int availableForSale;
    private int availableForLoan;
    private int borrowedQuantity;
    private String category;
    private String type;
    private String faculty;
    private long price;
    private int enable;

    public Book(int id, String name, String author, String publish_date, String publish_comp, int availableForSale, int availableForLoan, int borrowedQuantity, String category, String type, String faculty, long price, int enable) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.publish_date = publish_date;
        this.publish_comp = publish_comp;
        this.availableForSale = availableForSale;
        this.availableForLoan = availableForLoan;
        this.borrowedQuantity = borrowedQuantity;
        this.category = category;
        this.type = type;
        this.faculty = faculty;
        this.price = price;
        this.enable = enable;
    }

    public Book(String name, String author, String publish_date, String publish_comp, int availableForSale, int availableForLoan, int borrowedQuantity, String category, String type, String faculty, long price, int enable) {
        this.name = name;
        this.author = author;
        this.publish_date = publish_date;
        this.publish_comp = publish_comp;
        this.availableForSale = availableForSale;
        this.availableForLoan = availableForLoan;
        this.borrowedQuantity = borrowedQuantity;
        this.category = category;
        this.type = type;
        this.faculty = faculty;
        this.price = price;
        this.enable = enable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
    }

    public String getPublish_comp() {
        return publish_comp;
    }

    public void setPublish_comp(String publish_comp) {
        this.publish_comp = publish_comp;
    }

    public int getAvailableForSale() {
        return availableForSale;
    }

    public void setAvailableForSale(int availableForSale) {
        this.availableForSale = availableForSale;
    }

    public int getAvailableForLoan() {
        return availableForLoan;
    }

    public void setAvailableForLoan(int availableForLoan) {
        this.availableForLoan = availableForLoan;
    }

    public int getBorrowedQuantity() {
        return borrowedQuantity;
    }

    public void setBorrowedQuantity(int borrowedQuantity) {
        this.borrowedQuantity = borrowedQuantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }
}
