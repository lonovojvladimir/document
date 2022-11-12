package ru.com.document.normative.controller;

import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.com.document.normative.BaseNormativeControllerTest;
import ru.reksoft.gpi.wps.normative.dto.NormativeDto;
import ru.reksoft.gpi.wps.normative.dto.NormativePageResponseDto;
import ru.reksoft.gpi.wps.normative.dto.NormativeResponseDto;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NormativeControllerTest extends BaseNormativeControllerTest {

    @Test
    public void getType() {
        ResponseEntity<NormativeResponseDto<List<DictDto>>> response = restTemplate
                .exchange(url("/type"),
                    HttpMethod.GET,
                        getHttpEntity(null),
                    new ParameterizedTypeReference<NormativeResponseDto<List<DictDto>>>(){});

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());

        List<DictDto> data = response.getBody().getData();
        assertEquals(data.size(), 4);

        DictDto dictDto = data.get(0);
        assertEquals(dictDto.getCode(), "00");
        assertEquals(dictDto.getName(), "MIXED");
    }

    @Test
    public void getSection() {
        ResponseEntity<NormativeResponseDto<List<DictDto>>> response = restTemplate
                .exchange(url("/type/" + typeCode + "/section"),
                        HttpMethod.GET,
                        getHttpEntity(null),
                        new ParameterizedTypeReference<NormativeResponseDto<List<DictDto>>>(){});

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());

        List<DictDto> data = response.getBody().getData();
        assertEquals(data.size(), 11);

        DictDto dictDto = data.get(0);
        assertEquals(dictDto.getCode(), sectionCode);
        assertEquals(dictDto.getName(), "PIPEWORK");
    }

    @Test
    public void getActivity() {
        ResponseEntity<NormativeResponseDto<List<DictDto>>> response = restTemplate
                .exchange(url("/type/" + typeCode + "/section/" + sectionCode + "/activity"),
                        HttpMethod.GET,
                        getHttpEntity(null),
                        new ParameterizedTypeReference<NormativeResponseDto<List<DictDto>>>(){});

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());

        List<DictDto> data = response.getBody().getData();
        assertEquals(data.size(), 40);

        DictDto dictDto = data.get(0);
        assertEquals(dictDto.getCode(), activityCode);
        assertEquals(dictDto.getName(), "PIPE END PREPARATION");
    }

    @Test
    public void getRow() {
        ResponseEntity<NormativeResponseDto<List<DictDto>>> response = restTemplate
                .exchange(url("/type/" + typeCode + "/section/" + sectionCode + "/activity/" + activityCode + "/row"),
                        HttpMethod.GET,
                        getHttpEntity(null),
                        new ParameterizedTypeReference<NormativeResponseDto<List<DictDto>>>(){});

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());

        List<DictDto> data = response.getBody().getData();
        assertEquals(data.size(), 14);

        DictDto dictDto = data.get(0);
        assertEquals(dictDto.getCode(), rowCode);
        assertEquals(dictDto.getName(), "1''");
    }

    @Test
    public void getColumn() {
        ResponseEntity<NormativeResponseDto<List<DictDto>>> response = restTemplate
                .exchange(url("/type/" + typeCode + "/section/" + sectionCode + "/activity/"
                                + activityCode + "/column"),
                        HttpMethod.GET,
                        getHttpEntity(null),
                        new ParameterizedTypeReference<NormativeResponseDto<List<DictDto>>>(){});

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());

        List<DictDto> data = response.getBody().getData();
        assertEquals(data.size(), 25);

        DictDto dictDto = data.get(0);
        assertEquals(dictDto.getCode(), columnCode);
        assertEquals(dictDto.getName(), "5 S");
    }

    @Test
    public void getNormRate() {
        ResponseEntity<NormativeResponseDto<String>> response = restTemplate
                .exchange(url("/type/" + typeCode + "/section/" + sectionCode + "/activity/"
                                + activityCode + "/row/" + rowCode + "/column/" + columnCode),
                        HttpMethod.GET,
                        getHttpEntity(null),
                        new ParameterizedTypeReference<NormativeResponseDto<String>>(){});

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());

        String data = response.getBody().getData();
        assertEquals(data, normRate);
    }

    @Test
    public void getAll() {
        ResponseEntity<NormativeResponseDto<NormativePageResponseDto>> response = restTemplate
                .exchange(url("/all?page=1&size=20"),
                        HttpMethod.GET,
                        getHttpEntity(null),
                        new ParameterizedTypeReference<NormativeResponseDto<NormativePageResponseDto>>(){});

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());

        NormativePageResponseDto data = response.getBody().getData();
        assertEquals(data.getTotalElements(), 24531);

        assertNotNull(data.getContent());
        NormativeDto dto = data.getContent().get(0);
        assertEquals(dto.getRate(), "1");
    }

    @Test
    public void getAllByType() {
        ResponseEntity<NormativeResponseDto<NormativePageResponseDto>> response = restTemplate
                .exchange(url("/type/" + typeCode + "?page=1&size=20"),
                        HttpMethod.GET,
                        getHttpEntity(null),
                        new ParameterizedTypeReference<NormativeResponseDto<NormativePageResponseDto>>(){});

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());

        NormativePageResponseDto data = response.getBody().getData();
        assertEquals(data.getTotalElements(), 8702);

        assertNotNull(data.getContent());
        NormativeDto dto = data.getContent().get(0);
        assertEquals(dto.getRate(), normRate);
    }

    @Test
    public void getAllByTypeAndSection() {
        ResponseEntity<NormativeResponseDto<NormativePageResponseDto>> response = restTemplate
                .exchange(url("/type/" + typeCode + "/section/" + sectionCode + "?page=1&size=20"),
                        HttpMethod.GET,
                        getHttpEntity(null),
                        new ParameterizedTypeReference<NormativeResponseDto<NormativePageResponseDto>>(){});

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());

        NormativePageResponseDto data = response.getBody().getData();
        assertEquals(data.getTotalElements(), 5993);

        assertNotNull(data.getContent());
        NormativeDto dto = data.getContent().get(0);
        assertEquals(dto.getRate(), normRate);
    }

    @Test
    public void getAllByTypeAndSectionAndActivity() {
        ResponseEntity<NormativeResponseDto<NormativePageResponseDto>> response = restTemplate
                .exchange(url("/type/" + typeCode + "/section/" + sectionCode + "/activity/"
                                + activityCode + "?page=1&size=20"),
                        HttpMethod.GET,
                        getHttpEntity(null),
                        new ParameterizedTypeReference<NormativeResponseDto<NormativePageResponseDto>>(){});

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());

        NormativePageResponseDto data = response.getBody().getData();
        assertEquals(data.getTotalElements(), 210);

        assertNotNull(data.getContent());
        NormativeDto dto = data.getContent().get(0);
        assertEquals(dto.getRate(), normRate);
    }

    @Test
    public void getAllByTypeAndSectionAndActivityAndRow() {
        ResponseEntity<NormativeResponseDto<NormativePageResponseDto>> response = restTemplate
                .exchange(url("/type/" + typeCode + "/section/" + sectionCode + "/activity/"
                                + activityCode + "/row/" + rowCode +  "?page=1&size=20"),
                        HttpMethod.GET,
                        getHttpEntity(null),
                        new ParameterizedTypeReference<NormativeResponseDto<NormativePageResponseDto>>(){});

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());

        NormativePageResponseDto data = response.getBody().getData();
        assertEquals(data.getTotalElements(), 15);

        assertNotNull(data.getContent());
        NormativeDto dto = data.getContent().get(0);
        assertEquals(dto.getRate(), normRate);
    }

    @Test
    public void getAllByTypeAndSectionAndActivityAndColumn() {
        ResponseEntity<NormativeResponseDto<NormativePageResponseDto>> response = restTemplate
                .exchange(url("/type/" + typeCode + "/section/" + sectionCode + "/activity/"
                                + activityCode + "/column/" + columnCode +  "?page=1&size=20"),
                        HttpMethod.GET,
                        getHttpEntity(null),
                        new ParameterizedTypeReference<NormativeResponseDto<NormativePageResponseDto>>(){});

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());

        NormativePageResponseDto data = response.getBody().getData();
        assertEquals(data.getTotalElements(), 13);

        assertNotNull(data.getContent());
        NormativeDto dto = data.getContent().get(0);
        assertEquals(dto.getRate(), normRate);
    }
}
