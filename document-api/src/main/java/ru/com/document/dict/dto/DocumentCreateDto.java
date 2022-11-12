package ru.com.document.dict.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@ApiModel("Document create dto")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DocumentCreateDto {

    @ApiModelProperty(value = "code")
    private String code;
    @ApiModelProperty(value = "title")
    private String title;
    @ApiModelProperty(value = "comment")
    private String comment;

}
