package me.potato.finaltodo.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import me.potato.finaltodo.controller.dtos.CreateTodoRequest;
import me.potato.finaltodo.service.exceptions.NoUserInfoInSessionException;
import me.potato.finaltodo.service.exceptions.OrderingNotExistException;
import me.potato.finaltodo.service.exceptions.TodoListNotExistException;
import me.potato.finaltodo.service.exceptions.TodoNotExistException;
import me.potato.finaltodo.store.entity.Ordering;
import me.potato.finaltodo.store.entity.Todo;
import me.potato.finaltodo.store.entity.User;
import me.potato.finaltodo.store.repository.OrderingRepository;
import me.potato.finaltodo.store.repository.TodoRepository;
import me.potato.finaltodo.utils.EntityDtoUtil;
import me.potato.finaltodo.utils.Tracking;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoOrderingService {

    private final OrderingRepository orderingRepository;
    private final TodoRepository todoRepository;

    public User verificationSession(HttpSession session) {
        User user = (User)session.getAttribute("user");
        return Optional.ofNullable(user).orElseThrow(() -> new NoUserInfoInSessionException("",""));
    }

    @Tracking
    public Ordering createTodoOrdering(CreateTodoRequest request) {
        Optional<Ordering> ordering = orderingRepository.findByUserNo(request.getUserNo());
        var todo = todoRepository.save(EntityDtoUtil.todoRtoE(request));
        Ordering newOrdering = new Ordering();
        List<Todo> newList = new ArrayList<>();
        newList.add(todo);
        return ordering.map(o -> {
            List<Todo> list = o.getTodoList();
            if(list != null) {
                list.add(todo);
                o.setTodoList(list);
            }else {
                o.setTodoList(newList);
            }
            return orderingRepository.save(o);
        }).orElseGet(() -> {
            newOrdering.setTodoList(newList);
            newOrdering.setUserNo(request.getUserNo());
            return orderingRepository.save(newOrdering);
        });
    }

    @Tracking
    public Ordering completeTodo(Long todoId, Long userId) {
        Optional<Ordering> ordering = orderingRepository.findByUserNo(userId);
        return ordering.map(o -> {
            var todoList = o.getTodoList();
            Todo todo = new Todo();
            for(int i=0; i<todoList.size(); i++) {
                todo = todoList.get(i);
                if(todo.getId().equals(todoId)) {
                    todo.setStatus("completed");
                    todo.setCompletedTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                }
            }
            o.setTodoList(todoList);
            todoRepository.save(todo);
            return orderingRepository.save(o);
        }).orElseThrow(() -> new OrderingNotExistException("10003","ordering not exist,,"));
    }

    @Tracking
    public void deleteTodo(Long todoId, Long userId) {
        throw new TodoNotExistException("test","testException");
        /*Optional<Ordering> ordering = orderingRepository.findByUserNo(userId);
        ordering.map(o -> {
            var todoList = o.getTodoList();
            for(int i=0; i<todoList.size(); i++) {
                var todo = todoList.get(i);
                if(todo.getId().equals(todoId)) {
                    todoList.remove(i);
                    o.setTodoList(todoList);
                    todoRepository.delete(todo);
                }
            }
            System.out.println("ordering: "+o);
            return orderingRepository.save(o);
        }).orElseThrow(() -> new OrderingNotExistException("10003","ordering not exist,,"));*/

    }


    @Tracking
    public List<Todo> getTodoListByUserNo(Long userNo) {
        Optional<Ordering> ordering = orderingRepository.findByUserNo(userNo);
        List<Todo> todoList = new ArrayList<>();
        return ordering.map(o -> o.getTodoList()).orElse(todoList);
    }

    @Tracking
    public Todo getTodo(Long id) {
        Optional<Todo> todo = todoRepository.findById(id);
        if(todo.isPresent()) {
            return todo.get();
        }else {
            throw new TodoNotExistException("10001","Todo Not Exist..");
        }
    }

}
