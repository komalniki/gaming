package com.game.service.impl;

import com.game.dto.request.ScoreRequest;
import com.game.exception.ApplicationException;
import com.game.properties.GameProperties;
import com.game.service.CSVService;
import com.game.utils.CSVUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class CSVServiceImpl implements CSVService {

    private final CommonServiceImpl commonService;

    private final GameProperties gameProperties;

    @Autowired
    public CSVServiceImpl(CommonServiceImpl commonService,
                          GameProperties gameProperties) {
        this.commonService = commonService;
        this.gameProperties = gameProperties;
    }

    @Override
    public void save(MultipartFile file) {
        try {
            if (CSVUtils.hasCSVFormat(file)) {
                for (ScoreRequest scoreRequest : CSVUtils.csvToModel(file.getInputStream(), this.gameProperties.getHeaders())) {
                    try {
                        this.commonService.publishScore(scoreRequest);
                    } catch (ApplicationException e) {
                        log.error("Error occurred for Score Request: {}", scoreRequest);
                    }
                }
            }
        } catch (IOException e) {
            log.error("Exception Occurred:", e);
            throw new ApplicationException("Failed to Process Data: " + e.getMessage());
        }
    }
}