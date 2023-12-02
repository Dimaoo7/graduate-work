package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
@Service
public interface CommentService {

    Comments getComments(Integer id);

    Comment addComment(Integer id, CreateOrUpdateComment createOrUpdateComment, String username);

    Comment updateComment(Long id, CreateOrUpdateComment createOrUpdateComment, String username);
}
