package ru.com.document.dict.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@ApiModel("Document on page")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DocumentListPageDto<T> {

    @ApiModelProperty(value = "List drawings on page")
    private List<T> content;
    @ApiModelProperty(value = "Total elements in DB")
    private long totalElements;

}
