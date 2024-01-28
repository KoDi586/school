package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Avatar;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Avatar findAvatarById(Long id);

    Avatar findAvatarByFilePath(String filePath);

    @Modifying
    @Query("UPDATE Avatar a SET a.id = :needId WHERE a.id = :nowId")
    void changeIdByLostId(String needId, String nowId);

}
