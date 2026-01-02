package br.com.Alyson.Controllers.docs;

import br.com.Alyson.data.dto.security.AccountCredentialsDTO;
import br.com.Alyson.data.dto.v1.PersonDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface AuthControllerDocs {

    @Operation(summary = "Authenticates an user and returns a token",
            description = "User Authentication",
            tags = {"Authentication"},
            responses = {
                    @ApiResponse(description = "Success",
                            responseCode = "200",
                            content =
                                    {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            array = @ArraySchema(schema = @Schema(implementation = AuthControllerDocs.class)))
                                    }),

                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bau Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "504", content = @Content)
            }
    )

    ResponseEntity<?> signin(AccountCredentialsDTO credentials);


    @Operation(summary = "Refresh token for authenticated user and returns a token",
            description = "Refresh token",
            tags = {"Authentication"},
            responses = {
                    @ApiResponse(description = "Success",
                            responseCode = "200",
                            content =
                                    {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            array = @ArraySchema(schema = @Schema(implementation = AuthControllerDocs.class)))
                                    }),

                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bau Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "504", content = @Content)
            }
    )
    ResponseEntity<?> refreshToken(String username,
                                   String refreshToken);
    @Operation(summary = "Create authenticated user",
            description = "Create authenticated user and token",
            tags = {"Authentication"},
            responses = {
                    @ApiResponse(description = "Success",
                            responseCode = "200",
                            content =
                                    {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            array = @ArraySchema(schema = @Schema(implementation = AuthControllerDocs.class)))
                                    }),

                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bau Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "504", content = @Content)
            }
    )

    AccountCredentialsDTO create(AccountCredentialsDTO credentials);
}
