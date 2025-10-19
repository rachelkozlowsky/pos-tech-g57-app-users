package br.com.five.seven.food.infra.annotations.client;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Operation(summary = "Find client by CPF", description = "Retrieve a specific client by their CPF.")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Client successfully retrieved"),
        @ApiResponse(responseCode = "404", description = "Client not found")
})
public @interface SwaggerFindByCpf {
}
