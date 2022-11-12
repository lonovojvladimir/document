package ru.com.document.dict.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DocumentDto {

    @ApiModelProperty(value = "id_document")
    private Long idDocument;
    @ApiModelProperty(value = "code")
    private String code;
    @ApiModelProperty(value = "title")
    private String title;
    @ApiModelProperty(value = "comment")
    private String comment;
    @ApiModelProperty(value = "creator")
    private String creator;
    @ApiModelProperty(value = "created")
    private LocalDateTime created;
    @ApiModelProperty(value = "updater")
    private String updater;
    @ApiModelProperty(value = "updated")
    private LocalDateTime updated;
}
