package com.game.service.impl;

import com.game.dto.request.GameRequest;
import com.game.exception.ApplicationException;
import com.game.properties.GameProperties;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CSVServiceImplTest {

    @Mock
    private GameProperties gameProperties;

    @Mock
    private CommonServiceImpl commonService;

    private CSVServiceImpl csvService;

    private MultipartFile multipartFile;

    @Before
    public void setUp() throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream("SampleFile.csv");
        multipartFile = Mockito.mock(MultipartFile.class);
        Mockito.when(multipartFile.getInputStream()).thenReturn(is);
        Mockito.when(multipartFile.getContentType()).thenReturn("text/csv");
        csvService = new CSVServiceImpl(commonService, gameProperties);
    }

    @Test(expected = ApplicationException.class)
    public void save_invalid_file_type() {
        Mockito.when(multipartFile.getContentType()).thenReturn("pdf");
        this.csvService.save(multipartFile);
    }

    @Test(expected = RuntimeException.class)
    public void save_no_header_mapping() {
        this.csvService.save(multipartFile);
    }

    @Test
    public void save_success() {
        Mockito.when(this.gameProperties.getHeaders()).thenReturn(new String[]{"game_id", "user_id", "score"});
        this.csvService.save(multipartFile);
    }
}