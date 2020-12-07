package com.givts.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.*;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.stream.Stream;

@Component
public class StorageService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Path root = Paths.get("uploads");
    private MessageDigest md = null;

    public void init() {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ignored) {

        }
        try {
            Files.createDirectory(root);
        } catch (FileAlreadyExistsException ignored) {
            // we only want to create the directory once
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    public Stream<Path> getFiles(String userId) {
        try {
            return Files.walk(this.root.resolve(userId), 1)
                    .filter(path -> !path.equals(this.root) && !path.equals(this.root.resolve(userId)))
                    .map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load files!");
        }
    }

    public String store(long userId, MultipartFile file) {
        try {
            tryCreateDir(String.valueOf(userId));
            String fileName = UUID.randomUUID().toString();
            Files.copy(file.getInputStream(), this.root.resolve(userId + "/" + fileName));
            return fileName;
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    private void tryCreateDir(String dirName) {
        try {
            Files.createDirectory(root.resolve(dirName));
        } catch (FileAlreadyExistsException ignored) {

        } catch (IOException e) {
            throw new RuntimeException("Was unable to create dir " + dirName);
        }
    }

    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public void delete(String filename) {
        try {
            Files.delete(this.root.resolve(filename));
        } catch (Exception e) {
            logger.error("Failed deleting the file");
        }
    }

    public String tryUploadFile(long userId, MultipartFile file) {
        String fileName = null;
        try {
            if (file != null) {
                fileName = this.store(userId, file);
            }
        } catch (Exception e) {
            logger.error("Unable to upload file " + file.getOriginalFilename());
        }
        return fileName;
    }

    public boolean areFilesDifferent(MultipartFile file, String fileName) throws NoSuchFileException {
        try {
            return !getMD5SumFromFileName(fileName).equals(getMD5SumFromFile(file));
        } catch (NoSuchFileException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed reading file");
        }
    }

    private String getMD5SumFromFileName(String fileName) throws IOException {
        return getMD5Sum(Files.newInputStream(this.root.resolve(fileName)));
    }

    private String getMD5SumFromFile(MultipartFile file) throws IOException {
        return getMD5Sum(file.getInputStream());
    }

    private String getMD5Sum(InputStream is) throws IOException {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            try (is) {
                DigestInputStream dis = new DigestInputStream(is, md);
                int read = 0;
                do {
                    read = dis.read();
                } while (read > -1);
            }
            StringBuilder result = new StringBuilder();
            for (byte b : md.digest()) {
                result.append(String.format("%02x", b));
            }
            return result.toString();
        } catch (NoSuchAlgorithmException ignored) {

        }
        return "";
    }
}
