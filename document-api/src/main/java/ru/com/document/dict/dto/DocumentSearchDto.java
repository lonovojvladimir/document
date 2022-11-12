package ru.com.document.dict.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel("Document search criteria dto")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DocumentSearchDto {

    @ApiModelProperty(value = "Search string")
    private String term;
}
