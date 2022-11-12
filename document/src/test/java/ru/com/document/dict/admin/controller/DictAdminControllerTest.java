package ru.com.document.dict.admin.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.com.document.dict.BaseDictControllerTest;
import ru.reksoft.gpi.wps.dict.admin.dto.*;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DictAdminControllerTest extends BaseDictControllerTest {

    @Override
    protected String getApiUrl() {
        return "admin/" + super.getApiUrl();
    }

    private DictAdminCreateDto createDto;
    private DictAdminUpdateDto updateDto;

    private DictAdminDto dto;
    private String dictType;

    @Before
    public void init() {
        dictType = "permittype";
        createDto = DictAdminCreateDto.builder()
                .code("code")
                .name("name")
                .hide(false)
                .build();

        updateDto = DictAdminUpdateDto.builder()
                .name("name2")
                .hide(true)
                .build();

        ResponseEntity<ResponseAdminDto<DictAdminDto>> response = restTemplate.exchange(url("/" + dictType),
                HttpMethod.POST,
                getHttpEntity(createDto),
                new ParameterizedTypeReference<ResponseAdminDto<DictAdminDto>>(){});

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());

        DictAdminDto dto = response.getBody().getData();
        assertNotNull(dto.getCreated());
        assertNotNull(dto.getCreator());
        assertNotNull(dto.getUpdated());
        assertNotNull(dto.getUpdater());
        assertEquals(dto.getCode(), createDto.getCode());
        assertEquals(dto.getName(), createDto.getName());
        assertEquals(dto.isHide(), createDto.isHide());
        this.dto = dto;
    }

    @Test
    public void update() {
        ResponseEntity<ResponseAdminDto<DictAdminDto>> response = restTemplate
                .exchange(url("/" + dictType + "/" + dto.getId()),
                        HttpMethod.PUT,
                        getHttpEntity(updateDto),
                        new ParameterizedTypeReference<ResponseAdminDto<DictAdminDto>>(){});

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());

        DictAdminDto dto = response.getBody().getData();
        assertEquals(dto.getId(), this.dto.getId());
        assertEquals(dto.getName(), updateDto.getName());
        assertEquals(dto.getCode(), this.dto.getCode());
        assertEquals(dto.isHide(), updateDto.isHide());
    }

    @Test
    public void findAllByType() {
        ResponseEntity<ResponseAdminDto<DictAdminListPageDto>> response = restTemplate
                .exchange(url("/" + dictType + "/all?page=1&size=20"),
                        HttpMethod.GET,
                        getHttpEntity(null),
                        new ParameterizedTypeReference<ResponseAdminDto<DictAdminListPageDto>>(){});

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());

        DictAdminListPageDto data = response.getBody().getData();
        assertNotNull(data.getContent());
        assertEquals(data.getTotalElements(), 4);

        DictAdminDto dto = data.getContent().get(3);
        assertEquals(1000, dto.getId());
        assertEquals(dto.getName(), this.dto.getName());
        assertEquals(dto.getCode(), this.dto.getCode());
        assertEquals(dto.isHide(), this.dto.isHide());
    }

    @Test
    public void findAllByTypeWithTerm() {
        ResponseEntity<ResponseAdminDto<DictAdminListPageDto>> response = restTemplate
                .exchange(url("/" + dictType + "/all?page=1&size=20&term=tes"),
                        HttpMethod.GET,
                        getHttpEntity(null),
                        new ParameterizedTypeReference<ResponseAdminDto<DictAdminListPageDto>>(){});

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());

        DictAdminListPageDto data = response.getBody().getData();
        assertNotNull(data.getContent());
        assertEquals(data.getTotalElements(), 1);

        DictAdminDto dto = data.getContent().get(0);
        assertEquals(1000, dto.getId());
        assertEquals(dto.getName(), this.dto.getName());
        assertEquals(dto.getCode(), this.dto.getCode());
        assertEquals(dto.isHide(), this.dto.isHide());
    }

    @Test
    public void validate() {
        ResponseEntity<ResponseAdminDto<DictAdminDto>> response = restTemplate.exchange(url("/" + dictType + "/validate"),
                HttpMethod.POST,
                getHttpEntity(createDto),
                new ParameterizedTypeReference<ResponseAdminDto<DictAdminDto>>() {});

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertNotNull(response.getBody());
        assertNull(response.getBody().getData());
        assertNotNull(response.getBody().getError());

        List<DictAdminExceptionDto> error = response.getBody().getError();
        assertEquals(error.size(), 1);
        DictAdminExceptionDto exceptionDto = error.get(0);
        assertEquals(exceptionDto.getCode(), DictAdminExceptionCode.DUPLICATE_CODE);
        assertEquals(exceptionDto.getProperty(), "code");
    }

    @Test
    public void history() {
        ResponseEntity<ResponseAdminDto<DictAdminListHistoryDto>> response = restTemplate
                .exchange(url("/" + dictType + "/" + dto.getId() + "/history?page=1&size=20"),
                        HttpMethod.GET,
                        getHttpEntity(null),
                        new ParameterizedTypeReference<ResponseAdminDto<DictAdminListHistoryDto>>() {});

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());
        assertNull(response.getBody().getError());

        DictAdminListHistoryDto data = response.getBody().getData();
        assertNotNull(data.getContent());
        List<AuditDictAdminDto> content = data.getContent();
        assertEquals(content.size(), 2);

        AuditDictAdminDto first = content.get(1);
        assertEquals(first.getOperation(), "CREATE");
        assertEquals(first.getCode(), createDto.getCode());
        assertEquals(first.getDictType(), dictType);
        assertEquals(first.getProperty(), "Title");
        assertNull(first.getOldValue());
        assertEquals(first.getNewValue(), createDto.getName());

        AuditDictAdminDto second = content.get(0);
        assertEquals(second.getOperation(), "CREATE");
        assertEquals(second.getCode(), createDto.getCode());
        assertEquals(second.getDictType(), dictType);
        assertEquals(second.getProperty(), "Hide");
        assertNull(second.getOldValue());
        assertEquals(second.getNewValue(), String.valueOf(createDto.isHide()));
    }

    @Test
    public void searchUsers() {
        DictAdminSearchDto searchDto = DictAdminSearchDto.builder()
                .term("test")
                .build();
        ResponseEntity<ResponceDto<List<String>>> response = restTemplate.exchange(
                url("/" + dictType + "/search"),
                HttpMethod.POST,
                getHttpEntity(searchDto),
                new ParameterizedTypeReference<ResponceDto<List<String>>>(){});

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());

        List<String> data = response.getBody().getData();
        assertEquals(data.size(), 1);

        String dto = data.get(0);
        assertEquals(dto, "test");
    }

    @Test
    public void searchHistoryHelper() {
        DictAdminSearchDto searchDto = DictAdminSearchDto.builder()
                .term("test")
                .build();
        ResponseEntity<ResponceDto<List<String>>> response = restTemplate.exchange(
                url("/" + dictType + "/" + dto.getId() + "/history/search"),
                HttpMethod.POST,
                getHttpEntity(searchDto),
                new ParameterizedTypeReference<ResponceDto<List<String>>>(){});

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());

        List<String> data = response.getBody().getData();
        assertEquals(1, data.size());
        assertEquals(data.get(0), "test");
    }
}
