package com.game.utils;

import com.game.constants.AppConstants;
import com.game.dto.request.ScoreRequest;
import com.game.exception.ApplicationException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CSVUtils {

    private CSVUtils() {
    }

    public static boolean hasCSVFormat(MultipartFile file) {
        if (!AppConstants.FILE_TYPE.equals(file.getContentType())) {
            throw new ApplicationException("Invalid File Type");
        }
        return true;
    }

    public static List<ScoreRequest> csvToModel(InputStream is, String[] headers) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = CSVFormat.DEFAULT.builder().setHeader(headers).setSkipHeaderRecord(true).build().parse(fileReader)) {
            List<ScoreRequest> scoreRequestList = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                ScoreRequest scoreRequest = new ScoreRequest();
                scoreRequest.setGameId(Integer.valueOf(csvRecord.get("game_id")));
                scoreRequest.setUserId(Integer.valueOf(csvRecord.get("user_id")));
                scoreRequest.setScore(Double.parseDouble(csvRecord.get("score")));
                scoreRequestList.add(scoreRequest);
            }
            return scoreRequestList;
        } catch (IOException e) {
            throw new RuntimeException("Failed to Parse CSV: " + e.getMessage());
        }
    }
}
