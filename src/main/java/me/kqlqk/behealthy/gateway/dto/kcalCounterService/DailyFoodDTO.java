package me.kqlqk.behealthy.gateway.dto.kcalCounterService;

import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;

@Data
public class DailyFoodDTO {
    private long id;

    @Pattern(regexp = "\\w{2,50}", message = "Product name should be between 2 and 50 characters")
    private String name;

    @DecimalMin(value = "0.1", message = "Weight should be > 0")
    @DecimalMax(value = "9999.9", message = "Weight should be < 10000")
    private double weight;

    @DecimalMin(value = "0.0", message = "Kcals should be > 0")
    @DecimalMax(value = "999.9", message = "Kcals should be < 1000")
    private double kcals;

    @DecimalMin(value = "0.0", message = "Proteins should be > 0")
    @DecimalMax(value = "999.9", message = "Proteins should be < 1000")
    private double proteins;

    @DecimalMin(value = "0.0", message = "Fats should be > 0")
    @DecimalMax(value = "999.9", message = "Fats should be < 1000")
    private double fats;

    @DecimalMin(value = "0.0", message = "Carbs should be > 0")
    @DecimalMax(value = "999.9", message = "Carbs should be < 1000")
    private double carbs;
}
