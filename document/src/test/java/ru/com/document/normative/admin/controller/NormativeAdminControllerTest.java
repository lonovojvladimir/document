package ru.com.document.normative.admin.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.com.document.normative.BaseNormativeControllerTest;
import ru.reksoft.gpi.wps.normative.admin.dto.*;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class NormativeAdminControllerTest extends BaseNormativeControllerTest {

    @Override
    protected String getApiUrl() {
        return "admin/" + super.getApiUrl();
    }

    private NormativeAdminUpdateDto updateDto;

    @Before
    public void init() {
        updateDto = NormativeAdminUpdateDto.builder()
                .rate("120")
                .build();

        ResponseEntity<NormativeAdminResponseDto<NormativeAdminDto>> response = restTemplate.exchange(url("/type/"
                        + typeCode + "/section/" + sectionCode + "/activity/" + activityCode
                        + "/row/" + rowCode + "/column/" + columnCode),
                HttpMethod.PUT,
                getHttpEntity(updateDto),
                new ParameterizedTypeReference<NormativeAdminResponseDto<NormativeAdminDto>>(){});

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());

        NormativeAdminDto dto = response.getBody().getData();
        assertNotNull(dto.getType());
        assertNotNull(dto.getSection());
        assertNotNull(dto.getActivity());
        assertNotNull(dto.getRow());
        assertNotNull(dto.getColumn());
        assertNotNull(dto.getRate());

        assertEquals(dto.getRate(), updateDto.getRate());
    }

    @Test
    public void historyAll() {
        ResponseEntity<NormativeAdminResponseDto<NormativeAdminHistoryPageDto>> response = restTemplate.exchange(
                url("/history?page=1&size=20"),
                HttpMethod.GET,
                getHttpEntity(null),
                new ParameterizedTypeReference<NormativeAdminResponseDto<NormativeAdminHistoryPageDto>>(){});

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());
        assertNull(response.getBody().getError());

        NormativeAdminHistoryPageDto data = response.getBody().getData();
        assertNotNull(data.getContent());
        List<AuditNormativeAdminDto> content = data.getContent();
        assertEquals(content.size(), 1);

        AuditNormativeAdminDto first = content.get(0);
        assertEquals(first.getOperation(), "UPDATE");
        assertNotNull(first.getNormative());
        assertEquals(first.getProperty(), "Rate");
        assertNotNull(first.getOldValue());
        assertEquals(first.getNewValue(), updateDto.getRate());
    }

    @Test
    public void history() {
        ResponseEntity<NormativeAdminResponseDto<NormativeAdminHistoryPageDto>> response = restTemplate.exchange(
                url("/type/" + typeCode + "/section/" + sectionCode + "/activity/" + activityCode
                        + "/row/" + rowCode + "/column/" + columnCode + "/history?page=1&size=20"),
                HttpMethod.GET,
                getHttpEntity(null),
                new ParameterizedTypeReference<NormativeAdminResponseDto<NormativeAdminHistoryPageDto>>(){});

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());
        assertNull(response.getBody().getError());

        NormativeAdminHistoryPageDto data = response.getBody().getData();
        assertEquals(data.getTotalElements(), 1);
        assertNotNull(data.getContent());

        AuditNormativeAdminDto first = data.getContent().get(0);
        assertEquals(first.getOperation(), "UPDATE");
        assertNotNull(first.getNormative());
        assertEquals(first.getProperty(), "Rate");
        assertNotNull(first.getOldValue());
        assertEquals(first.getNewValue(), updateDto.getRate());
    }

    @Test
    public void searchHistoryHelper() {
        NormativeAdminSearchDto searchDto = NormativeAdminSearchDto.builder()
                .term("test")
                .build();
        ResponseEntity<NormativeAdminResponseDto<List<String>>> response = restTemplate.exchange(
                url("/type/" + typeCode + "/section/" + sectionCode + "/activity/" + activityCode
                        + "/row/" + rowCode + "/column/" + columnCode + "/history/search"),
                HttpMethod.POST,
                getHttpEntity(searchDto),
                new ParameterizedTypeReference<NormativeAdminResponseDto<List<String>>>(){});

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());

        List<String> data = response.getBody().getData();
        assertEquals(1, data.size());
        assertEquals(data.get(0), "test");
    }
}
