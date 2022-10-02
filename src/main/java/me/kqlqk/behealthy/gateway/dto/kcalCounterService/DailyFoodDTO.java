package me.kqlqk.behealthy.gateway.dto.kcalCounterService;

import com.fasterxml.jackson.annotation.JsonView;

import javax.validation.constraints.*;

public class DailyFoodDTO {
    public interface WithoutUserIdView {
    }

    @JsonView(WithoutUserIdView.class)
    private long id;

    private long userId;

    @Pattern(regexp = "\\w{2,50}", message = "Product name should be between 2 and 50 characters")
    @JsonView(WithoutUserIdView.class)
    private String name;

    @DecimalMin(value = "0.1", message = "Weight should be > 0")
    @DecimalMax(value = "9999.9", message = "Weight should be < 10000")
    @JsonView(WithoutUserIdView.class)
    private double weight;

    @DecimalMin(value = "0.0", message = "Kcals should be > 0")
    @DecimalMax(value = "999.9", message = "Kcals should be < 1000")
    @JsonView(WithoutUserIdView.class)
    private double kcals;

    @DecimalMin(value = "0.0", message = "Proteins should be > 0")
    @DecimalMax(value = "999.9", message = "Proteins should be < 1000")
    @JsonView(WithoutUserIdView.class)
    private double proteins;

    @DecimalMin(value = "0.0", message = "Fats should be > 0")
    @DecimalMax(value = "999.9", message = "Fats should be < 1000")
    @JsonView(WithoutUserIdView.class)
    private double fats;

    @DecimalMin(value = "0.0", message = "Carbs should be > 0")
    @DecimalMax(value = "999.9", message = "Carbs should be < 1000")
    @JsonView(WithoutUserIdView.class)
    private double carbs;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getKcals() {
        return kcals;
    }

    public void setKcals(double kcals) {
        this.kcals = kcals;
    }

    public double getProteins() {
        return proteins;
    }

    public void setProteins(double proteins) {
        this.proteins = proteins;
    }

    public double getFats() {
        return fats;
    }

    public void setFats(double fats) {
        this.fats = fats;
    }

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }
}
