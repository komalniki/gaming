package com.game.service;

import org.springframework.web.multipart.MultipartFile;

public interface CSVService {

    void save(MultipartFile file);
}