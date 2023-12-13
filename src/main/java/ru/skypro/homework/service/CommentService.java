package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;

@Service
public interface CommentService {
    Comments getComments(Long id);

    Comment addComment(Long id, CreateOrUpdateComment createOrUpdateComment, String username);

    String deleteComment(Long commentId, String username);

    Comment updateComment(Long adId,
                          Long commentId,
                          CreateOrUpdateComment createOrUpdateComment);
}