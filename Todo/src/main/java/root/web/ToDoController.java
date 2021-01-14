package root.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import root.data.TaskRepository;
import root.domain.Task;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping
public class ToDoController {

    @Autowired
    private TaskRepository taskRepo;

    public ToDoController(TaskRepository taskRepo) {
        this.taskRepo = taskRepo;
    }

    @ModelAttribute(name = "newTaskAttr")
    public Task taskForm() {
        return new Task();
    }

    @GetMapping("/todolist")
    public String showTodolist(Model model){
        Iterable<Task> taskList = taskRepo.findAll();
                model.addAttribute("taskListAttr", taskList);
        return "todolist";
    }

    @GetMapping("/todolist/newTask")
    public String showNewTaskPage(){
        return "newTask";
    }

    @PostMapping("/todolist/newTask")
    public String createNewTask(Model model, @ModelAttribute("newTaskAttr") @Valid Task task, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "/todolist/new";
        }else
            taskRepo.save(task);
        System.out.println(taskRepo.findAll());
        return "redirect:/todolist";
    }
}
