package ru.skypro.homework.service;

import ru.skypro.homework.dto.CommentDto;

public interface CommentService {

    public CommentDto getComments(Integer id);
}
