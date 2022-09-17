package me.kqlqk.behealthy.gateway.feign_clients;

import me.kqlqk.behealthy.gateway.beans.UserBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "AuthenticationService")
public interface AuthenticationClient {
    @GetMapping("/api/v1/users/{id}")
    UserBean geUserById(@PathVariable long id);
}
