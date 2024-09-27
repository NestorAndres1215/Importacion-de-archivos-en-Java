package com.example.demo.service;

import com.example.demo.model.FileModel;
import com.example.demo.repository.FileRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileRepository fileRepository;

    @Override
    @Transactional
    public FileModel saveFile(FileModel file) {
        System.out.println(file.getData());
        return fileRepository.save(file);
    }

    @Override
    public FileModel getFileById(Long id) {
        // Utiliza el repositorio para buscar el archivo por su ID
        Optional<FileModel> optionalFileModel = fileRepository.findById(id);
        return optionalFileModel.orElse(null); // Devuelve el archivo o null si no existe
    }

    @Override
    public List<FileModel> getAllFiles() {
        return fileRepository.findAll();  // Recupera todos los archivos
    }
    @Override
    public void deleteFileById(Long id) {
        fileRepository.deleteById(id);  // Elimina archivo por ID
    }


}