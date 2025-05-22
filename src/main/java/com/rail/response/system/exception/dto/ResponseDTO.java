package com.rail.response.system.exception.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {
    @JsonProperty("mensagem")
    private String message;
    @JsonProperty("erros")
    private List<String> errors;
    @JsonProperty("status")
    private Integer statusCode;
    @JsonProperty("data_error")
    private LocalDateTime errorDate;
}
