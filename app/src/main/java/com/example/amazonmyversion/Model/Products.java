package com.example.amazonmyversion.Model;

public class Products
{
    private String pname,pid,category,image,date,price,time,description  ;
    public Products()
    {

    }

    public Products(String pname, String pid, String category, String image, String date, String price, String time, String description) {
        this.pname = pname;
        this.pid = pid;
        this.category = category;
        this.image = image;
        this.date = date;
        this.price = price;
        this.time = time;
        this.description = description;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
