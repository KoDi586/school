package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exceptions.NotSaveAvatarEx;
import ru.hogwarts.school.service.AvatarService;

import java.io.IOException;

@RestController
@RequestMapping(path = "/avatar")
public class AvatarController {
    private final AvatarService service;

    @Autowired
    public AvatarController(AvatarService service) {
        this.service = service;
    }

    @PostMapping(value = "/{studentId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addAvatar(@PathVariable Long studentId,
                                            @RequestParam MultipartFile avatar) {
        try {
            service.uploadAvatar(studentId, avatar);
        } catch (NotSaveAvatarEx e) {
            return ResponseEntity.badRequest().body("ошибка при сохранении");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().build();
    }
}
