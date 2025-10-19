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
@Operation(summary = "Update client by CPF", description = "Update an existing client's details by their CPF.")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Client successfully updated"),
        @ApiResponse(responseCode = "404", description = "Client not found")
})
public @interface SwaggerUpdateClient {
}
