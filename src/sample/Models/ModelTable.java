package sample.Models;

public class ModelTable {
    private Integer id;
    private String name;
    private String producer;
    private Integer year;
    private double price;

    public ModelTable(Integer id, String name, String producer, Integer year, double price) {
        this.id = id;
        this.name = name;
        this.producer = producer;
        this.year = year;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
