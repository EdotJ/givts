package com.givts.app.controller;

import com.givts.app.exception.ResourceAlreadyExistsException;
import com.givts.app.payload.FileInfo;
import com.givts.app.service.StorageService;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/files")
public class FileController {

    StorageService storageService;

    public FileController(StorageService service) {
        storageService = service;
    }

    @GetMapping
    public ResponseEntity<List<FileInfo>> listFiles(@AuthenticationPrincipal OAuth2User principal) {
        String userId = String.valueOf((Long) principal.getAttribute("user_id"));
        List<FileInfo> fileInfos = storageService.getFiles(userId).map(path -> {
            String fileName = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FileController.class,
                            "getFile",
                            principal, path.getFileName().toString()).build().toString();
            return new FileInfo(fileName, url);
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping("/{fileName:.+}")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Resource> getFile(@AuthenticationPrincipal OAuth2User principal, @PathVariable String fileName) {
        String userId = String.valueOf((Long) principal.getAttribute("user_id"));
        try {
            Resource file = storageService.load(userId + "/" + fileName);
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + file.getFilename() + "\"")
                    .body(file);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build();
        }
    }
}
