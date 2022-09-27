package com.example.vmo1.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Data
public class ApiResponse {
    private final HttpStatus status;
    private final String data;
    private final Boolean success;
    private final String timestamp;
    private final String cause;
    private final String path;

    public ApiResponse(HttpStatus status, Boolean success, String data, String cause, String path) {
        this.status = status;
        this.data = data;
        this.success = success;
        this.timestamp = Instant.now().toString();
        this.cause = cause;
        this.path = path;
    }

}
