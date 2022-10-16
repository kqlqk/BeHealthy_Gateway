package me.kqlqk.behealthy.gateway.controller.rest.v1.user;

import com.fasterxml.jackson.annotation.JsonView;
import me.kqlqk.behealthy.gateway.dto.kcalCounterService.KcalsInfoDTO;
import me.kqlqk.behealthy.gateway.dto.kcalCounterService.UserConditionDTO;
import me.kqlqk.behealthy.gateway.exception.exceptions.UserException;
import me.kqlqk.behealthy.gateway.feign_client.KcalsCounterClient;
import me.kqlqk.behealthy.gateway.service.AuthenticationClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users/{id}")
public class UserConditionRestController {
    private final AuthenticationClientService authenticationClientService;
    private final KcalsCounterClient kcalsCounterClient;

    @Autowired
    public UserConditionRestController(AuthenticationClientService authenticationClientService,
                                       KcalsCounterClient kcalsCounterClient) {
        this.authenticationClientService = authenticationClientService;
        this.kcalsCounterClient = kcalsCounterClient;
    }

    @GetMapping("/condition")
    @JsonView(UserConditionDTO.WithoutUserIdView.class)
    public UserConditionDTO getCurrentUserCondition(@PathVariable long id) {
        if (id != authenticationClientService.getUserFromContext().getId()) {
            throw new UserException("Id = " + id + " is not your, please, use id = " +
                    authenticationClientService.getUserFromContext().getId());
        }

        return kcalsCounterClient.getUserConditionByUserId(id);
    }

    @PutMapping("/condition")
    public ResponseEntity<?> updateUserCondition(@PathVariable long id,
                                                 @RequestBody @Valid UserConditionDTO userConditionDTO,
                                                 HttpServletResponse response) {
        if (id != authenticationClientService.getUserFromContext().getId()) {
            throw new UserException("Id = " + id + " is not your, please, use id = " +
                    authenticationClientService.getUserFromContext().getId());
        }

        kcalsCounterClient.updateUserCondition(id, userConditionDTO);

        if (response.getStatus() != 200) {
            return ResponseEntity.status(response.getStatus()).build();
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/condition")
    public ResponseEntity<?> createUserCondition(@PathVariable long id,
                                                 @RequestBody @Valid UserConditionDTO userConditionDTO,
                                                 HttpServletResponse response) {
        if (id != authenticationClientService.getUserFromContext().getId()) {
            throw new UserException("Id = " + id + " is not your, please, use id = " +
                    authenticationClientService.getUserFromContext().getId());
        }

        userConditionDTO.setUserId(id);
        kcalsCounterClient.createUserCondition(userConditionDTO);

        if (response.getStatus() != 200) {
            return ResponseEntity.status(response.getStatus()).build();
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/kcals")
    public KcalsInfoDTO getCurrentUserKcalsInfo(@PathVariable long id) {
        if (id != authenticationClientService.getUserFromContext().getId()) {
            throw new UserException("Id = " + id + " is not your, please, use id = " +
                    authenticationClientService.getUserFromContext().getId());
        }

        return kcalsCounterClient.getKcalsInfoByUserId(id);
    }
}
