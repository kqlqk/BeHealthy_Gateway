package me.kqlqk.behealthy.gateway.controller.rest.v1.user;

import com.fasterxml.jackson.annotation.JsonView;
import me.kqlqk.behealthy.gateway.dto.kcalCounterService.KcalsInfoDTO;
import me.kqlqk.behealthy.gateway.dto.kcalCounterService.UserConditionDTO;
import me.kqlqk.behealthy.gateway.exception.exceptions.UserException;
import me.kqlqk.behealthy.gateway.feign_client.KcalCounterClient;
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
    private final KcalCounterClient kcalCounterClient;

    @Autowired
    public UserConditionRestController(AuthenticationClientService authenticationClientService,
                                       KcalCounterClient kcalCounterClient) {
        this.authenticationClientService = authenticationClientService;
        this.kcalCounterClient = kcalCounterClient;
    }

    @GetMapping("/condition")
    @JsonView(UserConditionDTO.WithoutUserIdView.class)
    public UserConditionDTO getCurrentUserCondition(@PathVariable long id) {
        if (id != authenticationClientService.getUserFromContext().getId()) {
            throw new UserException("Id = " + id + " is not your, please, use id = " +
                    authenticationClientService.getUserFromContext().getId());
        }

        return kcalCounterClient.getUserConditionByUserId(id);
    }

    @PutMapping("/condition")
    public ResponseEntity<?> updateUserCondition(@PathVariable long id,
                                                 @RequestBody @Valid UserConditionDTO userConditionDTO,
                                                 HttpServletResponse response) {
        if (id != authenticationClientService.getUserFromContext().getId()) {
            throw new UserException("Id = " + id + " is not your, please, use id = " +
                    authenticationClientService.getUserFromContext().getId());
        }

        kcalCounterClient.updateUserCondition(id, userConditionDTO);

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
        kcalCounterClient.createUserCondition(userConditionDTO);

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

        return kcalCounterClient.getKcalsInfoByUserId(id);
    }
}
