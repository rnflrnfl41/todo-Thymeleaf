package me.potato.finaltodo.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import me.potato.finaltodo.controller.dtos.CreateTodoRequest;
import me.potato.finaltodo.service.TodoOrderingService;
import me.potato.finaltodo.store.entity.Ordering;
import me.potato.finaltodo.store.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoOrderingController {

    private final TodoOrderingService service;

    @GetMapping("/main")
    public String todoMain(Model model, HttpSession session) {
        User user = (User)session.getAttribute("user");
        model.addAttribute("todoList",service.getTodoListByUserNo(user.getId()));
        model.addAttribute("userNo",user.getId());
        return "page/todo";
    }

    @RequestMapping(value ="/insert", method = RequestMethod.POST)
    @ResponseBody
    public Ordering insertOnT(@RequestBody CreateTodoRequest request) {
        return service.createTodoOrdering(request);
    }

    @GetMapping("/complete")
    @ResponseBody
    public String completeOnT(@RequestParam("todoNum") Long todoId, @RequestParam("userNo") Long userId) {
        String result = service.completeTodo(todoId,userId);
        return result;
    }

    @GetMapping("/delete")
    @ResponseBody
    public String deleteOnT(@RequestParam("todoNum") Long todoId, @RequestParam("userNo") Long userId) {
        String result = service.deleteTodo(todoId,userId);
        return result;
    }


}
