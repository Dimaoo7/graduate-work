package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.CommentService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;
    private final UserServiceImpl userService;

    public CommentServiceImpl(CommentRepository commentRepository,
                              CommentMapper commentMapper,
                              AdRepository adRepository,
                              UserRepository userRepository,
                              UserServiceImpl userService) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.adRepository = adRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Transactional
    @Override
    public Comments getComments(Long id) {
        log.info("Запущен метод сервиса {}", LoggingMethodImpl.getMethodName());
        List<Comment> comments = commentRepository.findByAdId(id).stream()
                .map(commentMapper::mapperToCommentDto)
                .collect(Collectors.toList());

        return new Comments(comments.size(), comments);
    }

    @Transactional
    @Override
    public Comment addComment(Long id, CreateOrUpdateComment createOrUpdateComment, String username) {
        log.info("Запущен метод сервиса {}", LoggingMethodImpl.getMethodName());

        UserEntity author = userService.getUser(username);//todo заменить метод на getUser
        AdEntity ad = adRepository.findById(id).orElse(null);

        //Создаем сущность comment и заполняем поля
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setAuthor(author);
        commentEntity.setAdEntity(ad);
        commentEntity.setText(createOrUpdateComment.getText());
        commentEntity.setCreatedAt(System.currentTimeMillis());

        //Сохраняем сущность commentEntity в БД
        commentRepository.save(commentEntity);

        //Заполняем поле с комментариями у пользователя и сохраняем в БД
        author.getComments().add(commentEntity);
        userRepository.save(author);

        //Создаем возвращаемую сущность ДТО comment и заполняем поля
        Comment commentDTO = new Comment();
        commentDTO.setAuthor(author.getId());

        Long avatarId = author.getPhoto().getId();
        log.info("id автора комментария - {}", author.getId());
        log.info("URL для получения аватара автора комментария: /photo/image/{}", avatarId);
        commentDTO.setAuthorImage("/photo/image/" + avatarId);

        commentDTO.setAuthorFirstName(author.getFirstName());
        commentDTO.setCreatedAt(commentEntity.getCreatedAt());
        commentDTO.setId(commentRepository.findFirstByText(createOrUpdateComment.getText()).getId());
        commentDTO.setText(commentRepository.findFirstByText(createOrUpdateComment.getText()).getText());

        return commentDTO;
    }

    @Override
    public String deleteComment(Long commentId, String username) {
        log.info("Запущен метод сервиса {}", LoggingMethodImpl.getMethodName());
        Optional<CommentEntity> comment = commentRepository.findById(commentId);
        if (comment.isPresent()) {
            UserEntity author = userService.getUser(username);
            if (author.getRole().equals(Role.ADMIN)) {
                commentRepository.delete(comment.get());
                return "комментарий удален";
            } else if (author.getRole().equals(Role.USER)) {
                if (comment.get().getAuthor().getUserName().equals(author.getUserName())) {
                    commentRepository.delete(comment.get());
                    return "комментарий удален";
                } else {
                    return "forbidden"; //'403' For the user deletion is forbidden
                }
            }
        }
        return "not found"; //'404' Comment not found
    }

    @Override
    public Comment updateComment(Long adId,
                                 Long commentId,
                                 CreateOrUpdateComment createOrUpdateComment) {
        log.info("Использован метод сервиса: {}", LoggingMethodImpl.getMethodName());

        CommentEntity comment = commentRepository.findById(commentId).get();
        comment.setText(createOrUpdateComment.getText());
        commentRepository.save(comment);
        return commentMapper.mapperToCommentDto(comment);
    }

}
