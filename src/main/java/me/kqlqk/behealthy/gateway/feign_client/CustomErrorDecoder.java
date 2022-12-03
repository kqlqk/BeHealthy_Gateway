package me.kqlqk.behealthy.gateway.feign_client;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import me.kqlqk.behealthy.gateway.exception.exceptions.IsOnDevelopingException;
import me.kqlqk.behealthy.gateway.exception.exceptions.authenticationService.*;
import me.kqlqk.behealthy.gateway.exception.exceptions.conditionService.UserConditionAlreadyExistsException;
import me.kqlqk.behealthy.gateway.exception.exceptions.conditionService.UserConditionNotFoundException;
import me.kqlqk.behealthy.gateway.exception.exceptions.workoutService.WorkoutNotFoundException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Component
public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        Map<String, String> info;

        try (InputStream body = response.body().asInputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            info = objectMapper.readValue(body, Map.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String errorMessage = info.get("info") != null ? info.get("info") : "No details about exception";

        if (response.status() == 404) {
            return new RuntimeException(errorMessage);
        }

        if (errorMessage.startsWith("WorkoutNotFound")) {
            throw new WorkoutNotFoundException(getErrorMessageWithoutPrefix(errorMessage, "WorkoutNotFound"));
        } else if (errorMessage.startsWith("IsOnDeveloping")) {
            throw new IsOnDevelopingException(getErrorMessageWithoutPrefix(errorMessage, "IsOnDeveloping"));
        } else if (errorMessage.startsWith("UserConditionAlreadyExists")) {
            throw new UserConditionAlreadyExistsException(getErrorMessageWithoutPrefix(errorMessage, "UserConditionAlreadyExists"));
        } else if (errorMessage.startsWith("UserConditionNotFound")) {
            throw new UserConditionNotFoundException(getErrorMessageWithoutPrefix(errorMessage, "UserConditionNotFound"));
        } else if (errorMessage.startsWith("UserAlreadyExists")) {
            throw new UserAlreadyExistsException(getErrorMessageWithoutPrefix(errorMessage, "UserAlreadyExists"));
        } else if (errorMessage.startsWith("UserNotFound")) {
            throw new UserNotFoundException(getErrorMessageWithoutPrefix(errorMessage, "UserNotFound"));
        } else if (errorMessage.startsWith("Token")) {
            throw new TokenException(getErrorMessageWithoutPrefix(errorMessage, "Token"));
        } else if (errorMessage.startsWith("TokenAlreadyExistsException")) {
            throw new TokenAlreadyExistsException(getErrorMessageWithoutPrefix(errorMessage, "TokenAlreadyExistsException"));
        } else if (errorMessage.startsWith("TokenNotFound")) {
            throw new TokenNotFoundException(getErrorMessageWithoutPrefix(errorMessage, "TokenNotFound"));
        } else {
            throw new RuntimeException(errorMessage);
        }

    }

    private String getErrorMessageWithoutPrefix(String errorWithPrefix, String prefix) {
        String[] arr = errorWithPrefix.split(prefix + " \\| ");

        return arr[1];
    }
}
