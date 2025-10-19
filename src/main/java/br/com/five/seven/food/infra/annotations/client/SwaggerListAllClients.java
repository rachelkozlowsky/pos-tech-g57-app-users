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
@Operation(summary = "List all clients", description = "Retrieve a list of all registered clients.")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Clients successfully retrieved")
})
public @interface SwaggerListAllClients {
}
