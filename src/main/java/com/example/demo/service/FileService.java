package com.example.demo.service;


import com.example.demo.model.FileModel;

import java.util.List;

public interface FileService {
    FileModel saveFile(FileModel file);
        FileModel getFileById(Long id);
    List<FileModel> getAllFiles();
    void deleteFileById(Long id);
}