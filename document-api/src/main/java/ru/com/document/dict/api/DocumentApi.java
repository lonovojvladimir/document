package ru.com.document.dict.api;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import ru.com.document.dict.dto.*;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 *
 */

@Api("Api for Document operations")
@RequestMapping("/api/document")
public interface DocumentApi {

    @ApiOperation(value = "Create document",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @PostMapping()
    ResponseEntity<DocumentResponseDto<DocumentDto>> createDocument(
            @ApiParam(value = "Document create dto", required = true)
            @RequestBody DocumentCreateDto createDto
    );

    @ApiOperation(value = "Update document",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })

    @PutMapping("/{idDocument}")
    ResponseEntity<DocumentResponseDto<DocumentDto>> updateDocument(
            @ApiParam(value = "idDocument")
            @PathVariable long idDocument,
            @ApiParam(value = "Document update dto", required = true)
            @RequestBody DocumentUpdateDto updateDto
    );

    @ApiOperation(value = "Get document by id",
            consumes = MimeTypeUtils.APPLICATION_JSON_VALUE,
            produces = MimeTypeUtils.APPLICATION_JSON_VALUE
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @GetMapping("/{idDocument}")
    ResponseEntity<DocumentResponseDto<DocumentDto>> getDocument(
            @ApiParam(value = "idDocument")
            @PathVariable long idDocument
    );

    @ApiOperation(value = "All Drawings",
            consumes = MimeTypeUtils.APPLICATION_JSON_VALUE,
            produces = MimeTypeUtils.APPLICATION_JSON_VALUE
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @GetMapping("/all")
    ResponseEntity<DocumentResponseDto<DocumentListPageDto<DocumentDto>>> getAllDocument(
            @ApiParam(value = "page", defaultValue = "1") @RequestParam("page") int page,
            @ApiParam(value = "size", defaultValue = "20") @RequestParam("size") int size,
            @RequestParam(value = "term", required = false) String term
    );



}
