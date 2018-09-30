package ir.ghaza_khoonegi.www.khoonegibebar.Datamodel;

import android.graphics.drawable.Drawable;

public class FoodModel {
    private int id;
    private String foodimage;
    private String foodtitle;
    private String cheftitle;
    private String material;
    private String cook;
    private String group;
    private int pricetitle;
    private int numberfood;
    private  Float rateFood;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodimage() {
        return foodimage;
    }

    public void setFoodimage(String foodimage) {
        this.foodimage = foodimage;
    }

    public String getFoodtitle() {
        return foodtitle;
    }

    public void setFoodtitle(String foodtitle) {
        this.foodtitle = foodtitle;
    }

    public String getCheftitle() {
        return cheftitle;
    }

    public void setCheftitle(String cheftitle) {
        this.cheftitle = cheftitle;
    }

    public int getPricetitle() {
        return pricetitle;
    }

    public void setPricetitle(int pricetitle) {
        this.pricetitle = pricetitle;
    }

    public int getNumberfood() {
        return numberfood;
    }

    public void setNumberfood(int numberfood) {
        this.numberfood = numberfood;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getCook() {
        return cook;
    }

    public void setCook(String cook) {
        this.cook = cook;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Float getRateFood() {
        return rateFood;
    }

    public void setRateFood(Float rateFood) {
        this.rateFood = rateFood;
    }
}
