package me.kqlqk.behealthy.gateway.dto.conditionService;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPhotoDTO {
    public interface WithoutUserId {
    }

    private long userId;

    @JsonView(WithoutUserId.class)
    private String encodedPhoto;

    @JsonView(WithoutUserId.class)
    private String photoDate;
}
