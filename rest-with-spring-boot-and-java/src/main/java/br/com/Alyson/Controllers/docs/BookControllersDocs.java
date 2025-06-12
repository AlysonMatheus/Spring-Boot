package br.com.Alyson.Controllers.docs;

import br.com.Alyson.data.dto.books.BookDTO;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface BookControllersDocs {
    @Operation(summary = "Find All Books",
            description = "Find All Books",
            tags = {"Book"},
            responses = {
                    @ApiResponse(description = "Success",
                            responseCode = "200",
                            content =
                                    {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            array = @ArraySchema(schema = @Schema(implementation = BookDTO.class)))
                                    }),

                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bau Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "504", content = @Content)
            }

    )
    List<BookDTO> findAll();


    @Operation(summary = "Finds a Books",
            description = "Find a specific person by your ID",
            tags = {"Book"},
            responses = {
                    @ApiResponse(description = "Success",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = BookDTO.class))
                    ),

                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bau Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "504", content = @Content)
            }

    )
    BookDTO findById(@PathVariable("id") Long id);


    @Operation(summary = "Create Books",
            description = "Find a specific create",
            tags = {"Book"},
            responses = {
                    @ApiResponse(description = "Success",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = BookDTO.class))
                    ),

                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bau Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "504", content = @Content)
            }

    )
    BookDTO create(@RequestBody BookDTO bookDTO);





    @Operation(summary = "Finds a Books Update",
            description = "Find a specific information Books",
            tags = {"Book"},
            responses = {
                    @ApiResponse(description = "Success",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = BookDTO.class))
                    ),

                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bau Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "504", content = @Content)
            }

    )
    BookDTO update(@RequestBody BookDTO person);


    @Operation(summary = "Delete a Books",
            description = "Find a specific person by your ID",
            tags = {"Book"},
            responses = {
                    @ApiResponse(description = "Success",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = BookDTO.class))
                    ),

                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bau Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "504", content = @Content)
            }

    )
    ResponseEntity<?> delete(@PathVariable("id") Long id);
}
