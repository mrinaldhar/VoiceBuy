package model;
 
public class Movie {
    private String title, thumbnailUrl;
    private String year;
    private double price;
 
    public Movie() {
    }
 
    public Movie(String name, String thumbnailUrl, String year, double price) {
        this.title = name;
        this.thumbnailUrl = thumbnailUrl;
        this.year = year;
        this.price = price;
    }
 
    public String getTitle() {
        return title;
    }
 
    public void setTitle(String name) {
        this.title = name;
    }
 
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
 
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
 
    public String getYear() {
        return year;
    }
 
    public void setYear(String year) {
        this.year = year;
    }
 
    public double getRating() {
        return price;
    }
 
    public void setRating(double price) {
        this.price = price;
    }
 
}