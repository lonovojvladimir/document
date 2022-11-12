package ru.com.document.dict.controller;

import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.com.document.dict.BaseDictControllerTest;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DictControllerTest extends BaseDictControllerTest {

    @Test
    public void findByType() {
        ResponseEntity<ResponceDto<List<DictDto>>> response = restTemplate
                .exchange(url("/dict"),
                    HttpMethod.GET,
                    getHttpEntity(null),
                    new ParameterizedTypeReference<ResponceDto<List<DictDto>>>(){});

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());

        List<DictDto> data = response.getBody().getData();
        assertEquals(7, data.size());

        DictDto dictDto = data.get(0);
        assertEquals(dictDto.getCode(), "smsdetail");
        assertEquals(dictDto.getName(), "SMS details");
    }
}
