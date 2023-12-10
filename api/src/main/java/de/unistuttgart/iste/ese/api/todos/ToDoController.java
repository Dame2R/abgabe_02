package de.unistuttgart.iste.ese.api.todos;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ArrayNode;
import de.unistuttgart.iste.ese.api.assignees.Assignee;
import de.unistuttgart.iste.ese.api.assignees.AssigneeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
@RestController
public class ToDoController {

    @Autowired
    private ToDoRepository todoRepository;
    @Autowired
    private AssigneeRepository assigneeRepository;
    
    
    //get all ToDos
    @GetMapping("/todos")
    public List<ToDo> getToDos() {
        List<ToDo> allToDos = (List<ToDo>) todoRepository.findAll();
        return allToDos;
    }

    // get a single ToDo
    @GetMapping("/todos/{id}")
    public ToDo getToDo(@PathVariable("id") long id) {

        ToDo searchedToDo = todoRepository.findById(id);
        if (searchedToDo != null) {
            return searchedToDo;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
            String.format("Todo with ID %s not found!", id));
    }

    // create a ToDo
    @PostMapping("/todos")
    @ResponseStatus(HttpStatus.CREATED)
    public ToDo createToDo(@Valid @RequestBody String requestBody) {
        
        ToDo toDo = new ToDo( );
        ObjectMapper objectMapper = new ObjectMapper();
        
               
        try{
            JsonNode savedToDo = objectMapper.readTree(requestBody);
            
            if(!savedToDo.has("title") || savedToDo.get("title").asText().isBlank()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            else{
                
            
                String title = savedToDo.get("title").asText();
                toDo.setTitle(title);
                System.out.println("title: " + title);
            }
            if (savedToDo.has("description")){
                String description = savedToDo.get("description").asText();
                System.out.println("description: " + description);
                toDo.setDescription(description);
            }
            if (savedToDo.has("assigneeIdList")){
                JsonNode assignedIdList = savedToDo.get("assigneeIdList");
                if(assignedIdList.isArray()){
                    ArrayNode assignedIdArray= (ArrayNode) assignedIdList;
                    Set<Assignee> assigneeList = new HashSet<>();
                    for(int index=0; index<assignedIdArray.size(); index++){
                    
                        if(
                           assigneeRepository.findById(assignedIdArray.get(index).asInt())==null||
                            !assigneeList.add(assigneeRepository.findById(assignedIdArray.get(index).asInt()))){
                            
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                        }
                    }
                    toDo.setAssigneeList(assigneeList.stream().toList());
                }
                
            }
            if (savedToDo.has("dueDate")){
                
                if (!savedToDo.get("dueDate").asText().matches("[0-9]+")){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
                Long date= savedToDo.get("dueDate").asLong();
                Date dueDate= new Date(date);
                System.out.println("dueDate: " + dueDate);
                toDo.setDueDate(dueDate);
                
            }
            todoRepository.save(toDo);
            return  toDo;
        }
        
        catch (JsonProcessingException e){

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        
    }

    // update an ToDo
    @PutMapping("/todos/{id}")
    public ToDo updateToDo(@PathVariable("id") long id, @Valid @RequestBody String requestBody) {
        
        
        ObjectMapper objectMapper = new ObjectMapper();
        ToDo toDoToUpdate = todoRepository.findById(id);
        if (toDoToUpdate != null) {
            try{
                ToDo toDo = new ToDo( );
                toDo.setId(id);
                
                JsonNode savedToDo = objectMapper.readTree(requestBody);
                if(!savedToDo.has("title") || savedToDo.get("title").asText().isBlank()){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
                else{
               
                    String title = savedToDo.get("title").asText();
                    toDo.setTitle(title);
                    System.out.println("title: " + title);
                }
                if (savedToDo.has("description")){
                    String description = savedToDo.get("description").asText();
                    System.out.println("description: " + description);
                    toDo.setDescription(description);
                }
                if (savedToDo.has("assigneeIdList")){
                    JsonNode assignedIdList = savedToDo.get("assigneeIdList");
                    if(assignedIdList.isArray()){
                        ArrayNode assignedIdArray= (ArrayNode) assignedIdList;
                        Set<Assignee> assigneeList = new HashSet<>();
                        for(int index=0; index<assignedIdArray.size(); index++){

                            if(
                                assigneeRepository.findById(assignedIdArray.get(index).asInt())==null||
                                    !assigneeList.add(assigneeRepository.findById(assignedIdArray.get(index).asInt()))){

                                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                            }
                        }
                        toDo.setAssigneeList(assigneeList.stream().toList());
                    }

                }
                if (savedToDo.has("dueDate")){

                    if (!savedToDo.get("dueDate").asText().matches("[0-9]+")){
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                    }
                    Long date= savedToDo.get("dueDate").asLong();
                    Date dueDate= new Date(date);
                    System.out.println("dueDate: " + dueDate);
                    toDo.setDueDate(dueDate);

                }

                if (savedToDo.has("finished")){

                    if(savedToDo.get("finished").asBoolean()){
                        toDo.setFinished(true);
                        toDo.setFinishedDate(new Date());
                    }

                }
                
                todoRepository.save(toDo);
                return  toDo;
            }
            catch (JsonProcessingException e){

                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            
            
                    }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
            String.format("ToDo with ID %s not found!", id));
    }

    @DeleteMapping("/todos/{id}")
    public ToDo deleteToDo(@PathVariable("id") long id) {

        ToDo toDoToDelete = todoRepository.findById(id);
        if (toDoToDelete != null) {
            
            toDoToDelete.setAssigneeList(null);
            todoRepository.save(toDoToDelete);
            todoRepository.deleteById(id);
             
            return toDoToDelete;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
            String.format("ToDO with ID %s not found!", id));
    }
    
    
}
