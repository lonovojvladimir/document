package ru.com.document.dict.exception;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@ApiModel("Document exception dto")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DocumentExceptionDto {
    @ApiModelProperty(value = "Property")
    private String property;
    @ApiModelProperty(value = "Code")
    private DocumentExceptionCode code;
    @ApiModelProperty(value = "Parameters")
    private DocumentExceptionParameters parameters;
}