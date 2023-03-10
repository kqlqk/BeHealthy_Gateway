package me.kqlqk.behealthy.gateway.dto.user_condition_service;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class AddUpdateUserConditionDTO {
    @Pattern(regexp = "MALE|FEMALE", message = "Please use valid gender (MALE or FEMALE)")
    private String gender;

    private int age;
    private int height;
    private int weight;

    @Pattern(regexp = "MIN|AVG|MAX", message = "Please use valid activity (MIN or AVG or MAX)")
    private String activity;

    @Pattern(regexp = "LOSE|MAINTAIN|GAIN", message = "Please use valid goal (LOSE or MAINTAIN or GAIN)")
    private String goal;

    private double fatPercent;
    private boolean fatPercentExists;
    private int fatFoldBetweenChestAndIlium;
    private int fatFoldBetweenNavelAndLowerBelly;
    private int fatFoldBetweenNippleAndArmpit;
    private int fatFoldBetweenNippleAndUpperChest;
    private int fatFoldBetweenShoulderAndElbow;
}
