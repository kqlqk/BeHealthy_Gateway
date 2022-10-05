package me.kqlqk.behealthy.gateway.dto.kcalCounterService;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Data
public class UserConditionDTO {
    public interface WithoutUserIdView {
    }

    @JsonView(WithoutUserIdView.class)
    private long id;
    private long userId;

    @Pattern(regexp = "MALE|FEMALE", message = "Please, use valid gender (MALE or FEMALE)")
    @JsonView(WithoutUserIdView.class)
    private String gender;

    @Min(value = 10, message = "you should be over 10 years old")
    @Max(value = 90, message = "you should be under 90 years old")
    @JsonView(WithoutUserIdView.class)
    private byte age;

    @Min(value = 100, message = "you should be over 100 cm")
    @Max(value = 200, message = "you should be under 200 cm")
    @JsonView(WithoutUserIdView.class)
    private short height;

    @Min(value = 40, message = "you should weight more than 40 kg")
    @Max(value = 150, message = "you should weight less than 150 kg")
    @JsonView(WithoutUserIdView.class)
    private short weight;

    @Pattern(regexp = "MIN|AVGG|MAX", message = "Please, use valid intensity (MIN or AVG or MAX)")
    @JsonView(WithoutUserIdView.class)
    private String intensity;

    @Pattern(regexp = "LOSE|MAINTAIN|GAIN", message = "Please, use valid goal (LOSE or MAINTAIN or GAIN)")
    @JsonView(WithoutUserIdView.class)
    private String goal;
}
