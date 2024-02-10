package com.blog.controller;

import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.service.CommentService;
import com.blog.vo.Comment;
import com.blog.vo.Result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class CommentController {
    
    @Autowired
    CommentService commentService;

    private static final Logger log = LoggerFactory.getLogger(PostController.class);

    @PostMapping("/comment")
    public Object savePost(HttpServletResponse response, @RequestBody Comment commentParam) {
        Comment comment = new Comment(commentParam.getPostId(), commentParam.getUser(), commentParam.getComment());
        boolean isSuccess = commentService.saveComment(comment);

        if (isSuccess) {
            return new Result(200, "Success");
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new Result(500, "Fail");
        }
    }

    @GetMapping("/comments")
    public List<Comment> getComments(@RequestParam("post_id") Long postId) {
        return commentService.getCommentList(postId);
    }

    @GetMapping("/comment")
    public Object getComment(@RequestParam("id") Long id) {
        return commentService.getComment(id);
    }

    @DeleteMapping("/comment")
    public Object deleteComment(HttpServletResponse response, @RequestParam("id") Long id){
        boolean isSuccess = commentService.deleteComment(id);

        log.info("id ::: " + id);

        if (isSuccess) {
            return new Result(200, "Success");
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new Result(404, "Comment not found");
        }
    }

    @GetMapping("/comments/search")
    public List<Comment> searchComments(@RequestParam("post_id") Long postId, @RequestParam("query") String query) {
        return commentService.searchComments(postId, query);
    }

}
