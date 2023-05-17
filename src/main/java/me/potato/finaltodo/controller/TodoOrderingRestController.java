package me.potato.finaltodo.controller;

import lombok.RequiredArgsConstructor;
import me.potato.finaltodo.controller.dtos.CreateTodoRequest;
import me.potato.finaltodo.service.TodoOrderingService;
import me.potato.finaltodo.service.exceptions.CommonException;
import me.potato.finaltodo.service.exceptions.TodoNotExistException;
import me.potato.finaltodo.store.entity.Ordering;
import me.potato.finaltodo.store.entity.Todo;
import me.potato.finaltodo.utils.EntityDtoUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todoOrderings")
public class TodoOrderingRestController {

    private final TodoOrderingService todoOrderingService;

    @PostMapping
    public Ordering createTodo(@RequestBody CreateTodoRequest createTodoRequest) {
        return todoOrderingService.createTodoOrdering(createTodoRequest);
    }

    @DeleteMapping("{id}")
    public void deleteTodo(@PathVariable Long todoId, @RequestParam Long userId) {
        todoOrderingService.deleteTodo(todoId,userId);
    }

    @PatchMapping("{id}")
    public Ordering completeTodo(@PathVariable Long todoId, @RequestParam Long userId) {
        return todoOrderingService.completeTodo(todoId,userId);
    }

    @GetMapping("{userNo}")
    public List<Todo> getTodoList(@PathVariable Long userNo) {
        return todoOrderingService.getTodoListByUserNo(userNo);
    }


    @ExceptionHandler(TodoNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse TodoNotExistException(CommonException e) {
        return (ErrorResponse)EntityDtoUtil.toErrorDto(e);
    }

}
