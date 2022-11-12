package ru.com.document.document.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.com.document.dict.dto.DocumentCreateDto;
import ru.com.document.dict.exception.DocumentExceptionCode;
import ru.com.document.dict.exception.DocumentExceptionDto;

import java.util.*;

@Service
@AllArgsConstructor
public class DocumentValidator {


    public void validate(DocumentCreateDto dto) {
//        Map<String, Integer> limits = restrictionsConfig.getDtoLimit("project", dto);
        List<DocumentExceptionDto> exceptions = new ArrayList<>();

//        if (dto == null || dto.getTitleDocument() == null || dto.getTitleDocument().trim().isEmpty()) {
//            exceptions.addAll(emptyRequiredField("code", (dto != null) ? dto.getTitleDocument() : null));
//        }
//        } else {
//            if (!limits.containsKey("code")) {
//                Optional<ProjectEntity> optional = repository.findByCode(dto.getCode());
//                if (optional.isPresent()) {
//                    exceptions.add(ProjectExceptionDto.builder()
//                            .code(ProjectExceptionCode.DUPLICATE_CODE)
//                            .property("code")
//                            .build()
//                    );
//                }
//            }
//        }

        exceptions.addAll(emptyRequiredField("title_document", (dto != null) ? dto.getTitle() : null));
        exceptions.addAll(emptyRequiredField("comment_document", (dto != null) ? dto.getComment() : null));

        if (!exceptions.isEmpty()) {
            throw new DocumentBusinessException(exceptions);
        }
    }

    private List<DocumentExceptionDto> emptyRequiredField(String property, String value) {
        if (value == null || value.trim().isEmpty()) {
            return Collections.singletonList(DocumentExceptionDto.builder()
                    .code(DocumentExceptionCode.REQUIRED_FIELD)
                    .property(property)
                    .build());
        }
        return Collections.emptyList();
    }

}
