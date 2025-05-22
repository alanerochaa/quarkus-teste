package com.rail.response.system.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginDTO {

    @Schema(examples = "1")
    @JsonProperty("idLogin")
    private Integer idLogin;

    @Schema(examples = "usuario@test.com")
    @JsonProperty("email")
    @Size(max = 255)
    private String email;

    @Schema(examples = "test@123")
    @JsonProperty("senha")
    @Size(max = 100)
    private String senha;

    @Schema(examples = "ativo")
    @JsonProperty("status")
    @Size(max = 40)
    private String status;

    @Schema(examples = "2024-05-20T08:30:00Z")
    @JsonProperty("dataCriacao")
    private Date dataCriacao;

    @Schema(examples = "2024-05-22T17:45:00Z")
    @JsonProperty("dataAtualizacao")
    private Date dataAtualizacao;
}


