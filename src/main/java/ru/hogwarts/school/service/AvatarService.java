package ru.hogwarts.school.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exceptions.NotSaveAvatarEx;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {
    private final AvatarRepository avatarRepository;
    private final StudentService studentService;
    @Value("${avatar.image.directory}")
    private String avatarsDir;
    // = "C:\\Users\\hirof\\IdeaProjects\\school\\src\\main\\java\\ru\\hogwarts\\school\\avatars";

    private final Logger logger = LoggerFactory.getLogger(AvatarService.class);

    @Autowired
    public AvatarService(AvatarRepository repository, StudentService service) {
        this.avatarRepository = repository;
        this.studentService = service;
    }

    //добавление аватара студенту
    public void uploadAvatar(Long studentId, MultipartFile avatar) throws IOException {
        logger.debug("file directory is: {}", avatarsDir);

        Student student = studentService.findStudent(studentId); //поиск нужного студента

        Path filePath = null;
        try {
            filePath = Path.of(avatarsDir, student.toString() + "." + getExtensions(avatar.getOriginalFilename()));
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
            newAvatar = new Avatar();
        }
        newAvatar.setId(studentId);
        newAvatar.setData(avatar.getBytes());
        newAvatar.setFilePath(filePath.toString());
        newAvatar.setStudent(student);
        newAvatar.setMediaType(avatar.getContentType());
        newAvatar.setFileSize(avatar.getSize());

        student.setAvatar(newAvatar);
        //добавить студенту его аву
        avatarRepository.save(newAvatar);

    }

    private String getExtensions(String fileName) {
        String extentions = fileName.substring(fileName.lastIndexOf(".") + 1);
        logger.debug("extentions is: {}", extentions);
        return extentions;
    }

    public Avatar findAvatar(Long id) {
        Avatar avatarById = avatarRepository.findAvatarById(id);
        if (avatarById != null) {
            logger.info("avatar is found");
        }
        return avatarById;
    }

    public List<Avatar> findPagingAvatars(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page-1, size);
        List<Avatar> avatars = avatarRepository.findAll(pageRequest).getContent();
        logger.info("found count avatars: {}", avatars.size());
        return avatars;

    }
}
