package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;

@Service
public interface CommentService {
    /**
     * Получение комментариев объявления
     * @param id - ID объявления
     * @return Comments - список комментариев объявления.
     */
    Comments getComments(Long id);
    /**
     * Добавление комментария к объявлению
     * @param id - ID объявления
     * @param createOrUpdateComment - создание комментария
     * @param username - имя пользователя
     * @return Comment - комментарий
     */
    Comment addComment(Long id, CreateOrUpdateComment createOrUpdateComment, String username);
    /**
     * Удаление комментария
     * @param commentId - ID комментария
     * @param username - имя пользователя
     */
    String deleteComment(Long commentId, String username);
    /**
     * Обновление комментария
     * @param adId - ID объявления
     * @param commentId - ID комментария
     * @param createOrUpdateComment - редактирование комментария
     * @return Comment - комментарий
     */
    Comment updateComment(Long adId,
                          Long commentId,
                          CreateOrUpdateComment createOrUpdateComment);
}