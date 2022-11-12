package ru.com.document.dict.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel("Document update dto")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DocumentUpdateDto {

    @ApiModelProperty(value = "title")
    private String title;
    @ApiModelProperty(value = "comment")
    private String comment;
}
