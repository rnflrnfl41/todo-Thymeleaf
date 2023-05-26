package me.potato.finaltodo.service;

import lombok.RequiredArgsConstructor;
import me.potato.finaltodo.controller.dtos.CreateTodoRequest;
import me.potato.finaltodo.store.entity.Ordering;
import me.potato.finaltodo.store.entity.Todo;
import me.potato.finaltodo.store.repository.OrderingRepository;
import me.potato.finaltodo.store.repository.TodoRepository;
import me.potato.finaltodo.utils.EntityDtoUtil;
import me.potato.finaltodo.utils.aspect.Tracking;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TodoOrderingService {

    private final OrderingRepository orderingRepository;
    private final TodoRepository todoRepository;


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
    public String completeTodo(Long todoId, Long userId) {
        Optional<Ordering> ordering = orderingRepository.findByUserNo(userId);
        Map<String, String> resultMap = new HashMap<>();
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
            orderingRepository.save(o);
            return resultMap.put("data","200");
        }).orElse(resultMap.put("data","100"));
    }

    @Tracking
    public String deleteTodo(Long todoId, Long userId) {
        Map<String, String> resultMap = new HashMap<>();
        Optional<Ordering> ordering = orderingRepository.findByUserNo(userId);
       return ordering.map(o -> {
            var todoList = o.getTodoList();
            for(int i=0; i<todoList.size(); i++) {
                var todo = todoList.get(i);
                if(todo.getId().equals(todoId)) {
                    todoList.remove(i);
                    o.setTodoList(todoList);
                    todoRepository.delete(todo);
                }
            }
            orderingRepository.save(o);
            return resultMap.put("result","200");
        }).orElse(resultMap.put("result","500"));

    }


    @Tracking
    public List<Todo> getTodoListByUserNo(Long userNo) {
        Optional<Ordering> ordering = orderingRepository.findByUserNo(userNo);
        List<Todo> todoList = new ArrayList<>();
        return ordering.map(o -> o.getTodoList()).orElse(todoList);
    }


}
