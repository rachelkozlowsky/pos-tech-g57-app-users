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
@Operation(summary = "Delete client by CPF", description = "Delete a client by their CPF.")
@ApiResponses({
        @ApiResponse(responseCode = "204", description = "Client successfully deleted"),
        @ApiResponse(responseCode = "404", description = "Client not found")
})
public @interface SwaggerDeleteClient {
}
