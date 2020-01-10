package com.fashionista.api.services;

import com.fashionista.api.exceptions.GenericException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@Service
public class FileStorageService {
    private final Path fileStorageLocation;

    public FileStorageService() {
        this.fileStorageLocation = Paths.get(System.getProperty("user.dir") + "/uploads")
                .toAbsolutePath()
                .normalize();
        try {
            if (!Files.exists(fileStorageLocation)) {
                Files.createDirectories(this.fileStorageLocation);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    String store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String uuid = UUID.randomUUID().toString();
        String[] split = filename.split("\\.");
        String uuidWithExt = uuid.concat("." + split[split.length - 1]);
        try {
            Files.copy(file.getInputStream(), this.fileStorageLocation.resolve(uuidWithExt), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new GenericException("Error occurred while uploading images. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return uuidWithExt;
    }

    List<String> storeMultiple(MultipartFile[] files) {
        List<String> filenames = new ArrayList<>();

        for (MultipartFile file : files) {
            filenames.add(store(file));
        }

        return filenames;
    }

    public ResponseEntity<?> getImage(String filename, HttpServletRequest request) {
        try {
            Path filepath = this.fileStorageLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(filepath.toUri());

            if (!resource.exists()) {
                throw new GenericException("File not found.", HttpStatus.BAD_REQUEST);
            }

            String contentType = request
                    .getServletContext()
                    .getMimeType(resource.getFile().getAbsolutePath());

            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                    .header(CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename())
                    .body(resource);

        } catch (MalformedURLException ex) {
            throw new GenericException("Error getting image from server.", HttpStatus.BAD_REQUEST);
        } catch (IOException ex) {
            throw new GenericException("Could not determine file type.", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (GenericException ex) {
            throw new GenericException(ex.getMessage(), ex.getStatus());
        }
    }
}
