package ru.skypro.homework.mapper;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.entity.CommentEntity;

@Service
public class CommentMapper {

    public Comment mapperToCommentDto(CommentEntity commentEntity) {
        Comment dtoComment = new Comment();
        dtoComment.setId(commentEntity.getId());
        dtoComment.setAuthor(commentEntity.getAuthor().getId());
        dtoComment.setAuthorFirstName(commentEntity.getAuthor().getFirstName());
        dtoComment.setAuthorImage(commentEntity.getAuthor().getAvatarEntity().getFilePath());
        dtoComment.setText(commentEntity.getText());
        dtoComment.setCreatedAt(commentEntity.getCreatedAt());
        return dtoComment;
    }
}