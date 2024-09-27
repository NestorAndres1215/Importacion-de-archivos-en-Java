# Importación de Archivos en Java con Spring Boot

Este proyecto permite la carga, almacenamiento, descarga y manejo de diferentes tipos de archivos (PDF, Word, Excel, imágenes, etc.) en una aplicación Java utilizando Spring Boot y JPA.

## Características

- **Subida de archivos**: Permite subir archivos PDF, Word, Excel y otros formatos.
- **Descarga de archivos**: Los archivos almacenados pueden ser descargados por su ID.
- **Listar archivos**: Se pueden listar los archivos almacenados en la base de datos.
- **Eliminar archivos**: Soporte para eliminar archivos almacenados.

## Tecnologías utilizadas

- **Spring Boot**: Framework para crear aplicaciones Java.
- **JPA**: Para el almacenamiento de archivos en una base de datos MySQL.
- **Apache PDFBox**: Para validar que los archivos subidos son PDFs válidos (en caso necesario).
- **MySQL**: Base de datos para almacenar los archivos.


# Importación de Archivos en Java

## Endpoints de la API

### 1. Subir Archivo

**URL**: `/upload`

**Método**: `POST`

**Descripción**: Permite la carga de archivos (PDF, Word, Excel, etc.).

**Ejemplo de uso (Postman)**:
```bash
POST http://localhost:8080/upload
```
**Respuesta**:
```json
{
  "id": 1,
  "name": "archivo.pdf",
  "type": "application/pdf",
  "data": "..."
}
```


