package org.conway.dockertest.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles({"development"})
public class UploadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void uploadFile() throws Exception {
        MockMultipartFile mockMultiPartFile = new MockMultipartFile("file", "dummy.csv",
                "text/plain", "Some dataset...".getBytes());

        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/uploadFile");

        MvcResult result = mockMvc.perform(builder.file(mockMultiPartFile).param("domainType", "customer")).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("file uploaded", response.getContentAsString());
    }

    @ParameterizedTest(name = "domain type of ''{0}'' should have a repsonse of ''{1}'' and Http status of {2}")
    @CsvSource({
            "customer,file uploaded,200",
            "CUStomer,file uploaded,200",
            "bank,bank is not an acceptable domainType,400",
            "bankaccount,bankaccount is not an acceptable domainType,400",
            "customeraccount,customeraccount is not an acceptable domainType,400",
            "customerbills,customerbills is not an acceptable domainType,400",
            "bad,bad is not an acceptable domainType,400"
    })
    public void domainTypeTest(String domainType, String expectedResponseBody, int expectedHttpStatusValue) throws Exception {
        MockMultipartFile mockMultiPartFile = new MockMultipartFile("file", "dummy.csv",
                "text/plain", "Some dataset...".getBytes());

        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/uploadFile");

        MvcResult result = mockMvc.perform(builder.file(mockMultiPartFile).param("domainType", domainType)).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(expectedHttpStatusValue, response.getStatus());
        assertEquals(expectedResponseBody, response.getContentAsString());
    }
}
