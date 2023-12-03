package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.impl.AuthServiceImpl;

@RestController
@RequestMapping("/ads")
public class CommentController {
    AuthServiceImpl authService;
    CommentService commentService;

    public CommentController(AuthServiceImpl authService, CommentService commentService) {
        this.authService = authService;
        this.commentService = commentService;
    }

    @GetMapping("{id}/comments")
    public ResponseEntity<Comments> getComments(@PathVariable("id") Long id) {
        return ResponseEntity.ok(commentService.getComments(id));
    }

    @PostMapping("{id}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable("Id") Long id, @RequestBody CreateOrUpdateComment createOrUpdateComment) {
        return ResponseEntity.ok(commentService.addComment(id,createOrUpdateComment,authService.getLogin()));
    }
}
