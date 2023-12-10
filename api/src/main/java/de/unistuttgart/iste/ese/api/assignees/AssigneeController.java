package de.unistuttgart.iste.ese.api.assignees;


import de.unistuttgart.iste.ese.api.todos.ToDo;
import de.unistuttgart.iste.ese.api.todos.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import javax.validation.Valid;
import java.util.List;

@RestController
public class AssigneeController {

    @Autowired
    private AssigneeRepository assigneeRepository;
    @Autowired
    private ToDoRepository todoRepository;




    @GetMapping("/assignees")
    public List<Assignee> getAssignees() {
        List<Assignee> allAssignees = (List<Assignee>) assigneeRepository.findAll();
        return allAssignees;
    }

    // get a single Assignee
    @GetMapping("/assignees/{id}")
    public Assignee getAssignee(@PathVariable("id") long id) {

        Assignee searchedAssignee = assigneeRepository.findById(id);
        if (searchedAssignee != null) {
            return searchedAssignee;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
            String.format("Assignee with ID %s not found!", id));
    }

    // create an Assignee
    @PostMapping("/assignees")
    @ResponseStatus(HttpStatus.CREATED)
    public Assignee createAssignee(@Valid @RequestBody Assignee requestBody) {
        Assignee assignee = new Assignee(
            requestBody.getPrename(),
            requestBody.getName(),
            requestBody.getEmail()
        );
        Assignee savedAssignee = assigneeRepository.save(assignee);
        return savedAssignee;
    }

    // update an Assignee
    @PutMapping("/assignees/{id}")
    public Assignee updateAssignee(@PathVariable("id") long id, @Valid @RequestBody Assignee requestBody) {

        requestBody.setId(id);
        Assignee assigneeToUpdate = assigneeRepository.findById(id);


        if (assigneeToUpdate != null) {
            Assignee savedAssignee= assigneeRepository.save(requestBody);
            return savedAssignee;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
            String.format("Assignee with ID %s not found!", id));
    }

    @DeleteMapping("/assignees/{id}")
    public Assignee deleteAssignee(@PathVariable("id") long id) {

        Assignee assigneeToDelete = assigneeRepository.findById(id);
        if (assigneeToDelete != null) {
            List<ToDo> allToDos= (List<ToDo>) todoRepository.findAll();
            for(ToDo toDo : allToDos){
                toDo.getAssigneeList().remove(assigneeToDelete);
                todoRepository.save(toDo);
            }
            assigneeRepository.deleteById(id);
            return assigneeToDelete;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
            String.format("Assignee with ID %s not found!", id));
    }
}
