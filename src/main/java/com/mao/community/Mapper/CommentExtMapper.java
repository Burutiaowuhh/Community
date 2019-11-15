package com.mao.community.Mapper;

import com.mao.community.model.Comment;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}