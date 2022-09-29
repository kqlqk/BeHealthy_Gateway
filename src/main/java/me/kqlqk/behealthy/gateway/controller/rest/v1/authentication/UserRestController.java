package me.kqlqk.behealthy.gateway.controller.rest.v1.authentication;

import me.kqlqk.behealthy.gateway.dto.authenticationService.UserAuthDTO;
import me.kqlqk.behealthy.gateway.dto.kcalCounterService.KcalsInfoDTO;
import me.kqlqk.behealthy.gateway.dto.kcalCounterService.UserConditionDTO;
import me.kqlqk.behealthy.gateway.exception.exceptions.UserException;
import me.kqlqk.behealthy.gateway.feign_client.AuthenticationClient;
import me.kqlqk.behealthy.gateway.feign_client.KcalCounterClient;
import me.kqlqk.behealthy.gateway.service.AuthenticationClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class UserRestController {
    private final AuthenticationClient authenticationClient;
    private final KcalCounterClient kcalCounterClient;
    private final AuthenticationClientService authenticationClientService;

    @Autowired
    public UserRestController(AuthenticationClient authenticationClient,
                              KcalCounterClient kcalCounterClient,
                              AuthenticationClientService authenticationClientService) {
        this.authenticationClient = authenticationClient;
        this.kcalCounterClient = kcalCounterClient;
        this.authenticationClientService = authenticationClientService;
    }

    @GetMapping("/users/{id}")
    public UserAuthDTO getCurrentUser(@PathVariable long id) {
        if (id != authenticationClientService.getUserFromContext().getId()) {
            throw new UserException("Id = " + id + " is not your, please, use id = " +
                    authenticationClientService.getUserFromContext().getId());
        }

        return authenticationClient.getUserById(id);
    }

    @GetMapping("/users/{id}/condition")
    public UserConditionDTO getCurrentUserCondition(@PathVariable long id) {
        if (id != authenticationClientService.getUserFromContext().getId()) {
            throw new UserException("Id = " + id + " is not your, please, use id = " +
                    authenticationClientService.getUserFromContext().getId());
        }

        return kcalCounterClient.getUserConditionByUserId(id);
    }

    @GetMapping("/users/{id}/kcals")
    public KcalsInfoDTO getCurrentUserKcalsInfo(@PathVariable long id) {
        if (id != authenticationClientService.getUserFromContext().getId()) {
            throw new UserException("Id = " + id + " is not your, please, use id = " +
                    authenticationClientService.getUserFromContext().getId());
        }

        return kcalCounterClient.getKcalsInfoByUserId(id);
    }

    @PostMapping("/users/{id}/condition")
    public ResponseEntity<?> createUserCondition(@PathVariable long id, @RequestBody @Valid UserConditionDTO userConditionDTO) {
        userConditionDTO.setUserId(id);
        kcalCounterClient.createUserCondition(userConditionDTO);

        return ResponseEntity.ok().build();
    }

}
