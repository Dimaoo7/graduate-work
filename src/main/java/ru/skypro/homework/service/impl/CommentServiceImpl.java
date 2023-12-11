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

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;
    private final AdRepository adRepository;
    private final UserServiceImpl userService;


    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper, UserRepository userRepository, AdRepository adRepository, UserServiceImpl userService) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.userRepository = userRepository;
        this.adRepository = adRepository;
        this.userService = userService;
    }

    @Transactional
    @Override
    public Comments getComments(Long id) {
        log.info("Method {}", LoggingMethodImpl.getMethodName());
        List<Comment> comments = commentRepository.findByAdId(id).stream()
                .map(commentMapper::mapperToCommentDto)
                .collect(Collectors.toList());

        return new Comments(comments.size(), comments);
    }

    @Transactional
    @Override
    public Comment addComment(Long id, CreateOrUpdateComment createOrUpdateComment, String username) {
        log.info("Method {}", LoggingMethodImpl.getMethodName());

        UserEntity author = userService.getUser(username);
        AdEntity ad = adRepository.findById(id).orElse(null);

        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setAuthor(author);
        commentEntity.setAdEntity(ad);
        commentEntity.setText(createOrUpdateComment.getText());
        commentEntity.setCreatedAt(System.currentTimeMillis());


        commentRepository.save(commentEntity);


        author.getComments().add(commentEntity);
        userRepository.save(author);

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
        log.info("Method {}", LoggingMethodImpl.getMethodName());
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
                    return "forbidden";
                }
            }
        }
        return "not found";
    }

    @Transactional
    @Override
    public Comment updateComment(Long commentId, CreateOrUpdateComment createOrUpdateComment, String username) {
        log.info("Запущен метод сервиса {}", LoggingMethodImpl.getMethodName());
        Optional<CommentEntity> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            CommentEntity comment = commentOptional.get();
            UserEntity author = userService.getUser(username);
            if (author.getComments().contains(comment)) {
                comment.setText(createOrUpdateComment.getText());
                commentRepository.save(comment);
                return commentMapper.mapperToCommentDto(comment);
            } else {
                return commentMapper.mapperToCommentDto(comment);
            }
        }
        return null; //'404' Comment not found
    }
}
