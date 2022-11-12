package ru.com.document.dict.exception;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel("Document exception parameters dto")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DocumentExceptionParameters {

    @ApiModelProperty(value = "Max")
    private Integer max;

    @ApiModelProperty(value = "Min")
    private Integer min;
}
