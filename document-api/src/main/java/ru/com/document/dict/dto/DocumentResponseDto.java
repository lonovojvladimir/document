package ru.com.document.dict.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import ru.com.document.dict.exception.DocumentExceptionDto;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@ApiModel("Document response dto")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DocumentResponseDto<T> {
    @ApiModelProperty(value = "Data")
    private T data;
    @ApiModelProperty(value = "Error info")
    private List<DocumentExceptionDto> error;
}
