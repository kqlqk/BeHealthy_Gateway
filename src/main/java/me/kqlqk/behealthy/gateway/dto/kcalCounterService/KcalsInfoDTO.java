package me.kqlqk.behealthy.gateway.dto.kcalCounterService;

public class KcalsInfoDTO {
    private long id;
    private short protein;
    private short fat;
    private short carb;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public short getProtein() {
        return protein;
    }

    public void setProtein(short protein) {
        this.protein = protein;
    }

    public short getFat() {
        return fat;
    }

    public void setFat(short fat) {
        this.fat = fat;
    }

    public short getCarb() {
        return carb;
    }

    public void setCarb(short carb) {
        this.carb = carb;
    }
}
