package exercise.controller;

import java.util.List;
import java.util.stream.Collectors;

import exercise.dto.TaskCreateDTO;
import exercise.dto.TaskDTO;
import exercise.dto.TaskUpdateDTO;
import exercise.mapper.TaskMapper;
import exercise.model.Task;
import exercise.model.User;
import exercise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import exercise.exception.ResourceNotFoundException;
import exercise.repository.TaskRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TasksController {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired TaskMapper taskMapper;
    // BEGIN
    @GetMapping
    public List<TaskDTO> getAll() {
        List<Task> taskList = taskRepository.findAll();
        return taskList.stream().map(t -> taskMapper.map(t)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TaskDTO getById(@PathVariable long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found"));
        return taskMapper.map(task);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO create(@Valid @RequestBody TaskCreateDTO taskCreateDTO) {
        Task task = taskMapper.map(taskCreateDTO);
        return taskMapper.map(taskRepository.save(task));
    }

    @PutMapping("/{id}")
    public TaskDTO updateById(@PathVariable long id, @Valid @RequestBody TaskUpdateDTO taskUpdateDTO) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found"));
        if (taskUpdateDTO.getAssigneeId() != null) {
            User user = userRepository.findById(taskUpdateDTO.getAssigneeId()).orElseThrow(
                    () -> new ResourceNotFoundException("Not found"));
            task.setAssignee(user);
        }
        taskMapper.update(taskUpdateDTO, task);
        taskRepository.save(task);
        return taskMapper.map(task);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable long id) {
        taskRepository.deleteById(id);
    }
    // END
}
