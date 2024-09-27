package com.example.demo.controller;

import com.example.demo.model.FileModel;
import com.example.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {
    @Autowired
    private FileService fileService;



    @PostMapping("/upload")
    public ResponseEntity<FileModel> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            // Puedes validar si el archivo es de un tipo permitido (opcional)
            String fileType = file.getContentType();
            List<String> allowedTypes = Arrays.asList("application/pdf", "application/msword",
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                    "application/vnd.ms-excel",
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                    "image/jpeg", "image/png");

            if (!allowedTypes.contains(fileType)) {
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(null);
            }

            // Crea un modelo para almacenar el archivo
            FileModel fileModel = new FileModel();
            fileModel.setName(file.getOriginalFilename());
            fileModel.setType(fileType);
            fileModel.setData(file.getBytes());

            // Guarda el archivo en la base de datos
            FileModel savedFile = fileService.saveFile(fileModel);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedFile);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @GetMapping("/listar")
    public ResponseEntity<List<FileModel>> listFiles() {
        try {
            List<FileModel> files = fileService.getAllFiles();
            return ResponseEntity.ok(files);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
        try {
            // Obtiene el archivo por ID
            FileModel fileModel = fileService.getFileById(id);

            // Verifica si el archivo existe
            if (fileModel == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // Obtiene el tipo MIME del archivo
            String fileType = fileModel.getType();

            // Configura la respuesta con el archivo
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileModel.getName() + "\"")
                    .contentType(MediaType.parseMediaType(fileType))  // Establece el tipo de contenido dinámicamente
                    .contentLength(fileModel.getData().length)  // Establece la longitud del contenido
                    .body(fileModel.getData());  // Envía el contenido del archivo

        } catch (Exception e) {
            e.printStackTrace();  // Registra la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);  // Devuelve un error 500 en caso de excepción
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long id) {
        try {
            FileModel existingFile = fileService.getFileById(id);
            if (existingFile == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            fileService.deleteFileById(id);
            return ResponseEntity.noContent().build();  // Devuelve 204 No Content en caso de éxito
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}