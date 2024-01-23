package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exceptions.NotSaveAvatarEx;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

//import static io.swagger.v3.core.util.AnnotationsUtils.getExtensions;

@Service
public class AvatarService {
    private final AvatarRepository avatarRepository;
    private final StudentService studentService;
    String avatarsDir = "C:\\Users\\hirof\\IdeaProjects\\school\\src\\main\\java\\ru\\hogwarts\\school\\avatars";

    @Autowired
    public AvatarService(AvatarRepository repository, StudentService service) {
        this.avatarRepository = repository;
        this.studentService = service;
    }

    //добавление аватара студенту
    public void uploadAvatar(Long studentId, MultipartFile avatar) throws IOException {
        Student student = studentService.findStudent(studentId); //поиск нужного студента

        Path filePath = null;
        try {
            filePath = Path.of(avatarsDir, student + "." + getExtensions(avatar.getOriginalFilename()));
        } catch (Exception e) {
            throw new NotSaveAvatarEx("ошибка сохранения фотографии в БД");
        }
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        //работа с потоками
        try (
                InputStream is = avatar.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }


        Avatar newAvatar = null;
        newAvatar = avatarRepository.findAvatarById(studentId);
        //так можно сделать потому что у студента и его авы один и тот же id

        if (newAvatar == null) {
            newAvatar = new Avatar(studentId);
        }
        newAvatar.setData(avatar.getBytes());
        newAvatar.setFilePath(filePath.toString());
        newAvatar.setStudent(student);
        newAvatar.setMediaType(avatar.getContentType());
        newAvatar.setFileSize(avatar.getSize());
        avatarRepository.save(newAvatar);

    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
