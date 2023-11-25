package ru.skypro.homework.mapper;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.entity.CommentEntity;

@Service
public class CommentMapper {

    public Comment mapperToCommentDto(CommentEntity commentEntity) {
        Comment dtoComment = new Comment();
        dtoComment.setPk(commentEntity.getId());
        dtoComment.setAuthor(commentEntity.getAuthor().getId());
        dtoComment.setAuthorImage(commentEntity.getAuthor().getAvatar().getFilePath());
        dtoComment.setAuthorFirstName(commentEntity.getAuthor().getFirstName());
        dtoComment.setText(commentEntity.getText());
        dtoComment.setCreatedAt(commentEntity.getCreatedAt());
        return dtoComment;
    }
}