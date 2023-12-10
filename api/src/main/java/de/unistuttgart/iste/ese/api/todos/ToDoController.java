package de.unistuttgart.iste.ese.api.todos;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    
    
    //List all ToDos
    @GetMapping("/todos")
    public List<ToDo> getToDos() {
        List<ToDo> allToDos = (List<ToDo>) todoRepository.findAll();
        return allToDos;
    }

    // List a single ToDo by ID
    @GetMapping("/todos/{id}")
    public ToDo getToDo(@PathVariable("id") long id) {

        ToDo searchedToDo = todoRepository.findById(id);
        if (searchedToDo != null) {
            return searchedToDo;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
            String.format("Todo with ID %s not found!", id));
    }

    // Create a new ToDo
    @PostMapping("/todos")
    @ResponseStatus(HttpStatus.CREATED)
    public ToDo createToDo(@Valid @RequestBody String requestBody) {
        
        ToDo toDo = new ToDo( );
        ObjectMapper objectMapper = new ObjectMapper();
        
               
        try{
            JsonNode inputToDo = objectMapper.readTree(requestBody);
            
            if(!inputToDo.has("title") || inputToDo.get("title").asText().isBlank()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            else{
                String title = inputToDo.get("title").asText();
                toDo.setTitle(title);
                System.out.println("title: " + title);
            }
            if (inputToDo.has("description")){
                String description = inputToDo.get("description").asText();
                System.out.println("description: " + description);
                toDo.setDescription(description);
            }
            // ...

            // Assignee-ID-Liste validieren
            if (inputToDo.has("assigneeIdList")){
                JsonNode assignedIdList = inputToDo.get("assigneeIdList");
                if(assignedIdList.isArray()){
                    ArrayNode assignedIdArray= (ArrayNode) assignedIdList;
                    Set<Assignee> assigneeSet = new HashSet<>();
                    for(int index=0; index<assignedIdArray.size(); index++){
                        Assignee assignee = assigneeRepository.findById(assignedIdArray.get(index).asInt());
                        if(assignee == null || !assigneeSet.add(assignee)){
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid assigneeId");
                        }
                    }
                    List<Assignee> assigneeList = new ArrayList<>(assigneeSet);
                    toDo.setAssigneeList(assigneeList);
                }
            }

            // ...

            if (inputToDo.has("dueDate")){
                
                if (!inputToDo.get("dueDate").asText().matches("[0-9]+")){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
                Long date= inputToDo.get("dueDate").asLong();
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

    // Update an existing ToDo by ID
    @PutMapping("/todos/{id}")
    public ToDo updateToDo(@PathVariable("id") long id, @Valid @RequestBody String requestBody) {
        
        ObjectMapper objectMapper = new ObjectMapper();
        ToDo toDoToUpdate = todoRepository.findById(id);
        if (toDoToUpdate != null) {
            try{
                ToDo toDo = new ToDo( );
                toDo.setId(id);
                
                JsonNode inputToDo = objectMapper.readTree(requestBody);
                if(!inputToDo.has("title") || inputToDo.get("title").asText().isBlank()){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
                else{
               
                    String title = inputToDo.get("title").asText();
                    toDo.setTitle(title);
                    System.out.println("title: " + title);
                }
                if (inputToDo.has("description")){
                    String description = inputToDo.get("description").asText();
                    System.out.println("description: " + description);
                    toDo.setDescription(description);
                }
                if (inputToDo.has("assigneeIdList")){
                    JsonNode assignedIdList = inputToDo.get("assigneeIdList");
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
                if (inputToDo.has("dueDate")){

                    if (!inputToDo.get("dueDate").asText().matches("[0-9]+")){
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                    }
                    Long date= inputToDo.get("dueDate").asLong();
                    Date dueDate= new Date(date);
                    System.out.println("dueDate: " + dueDate);
                    toDo.setDueDate(dueDate);

                }

                if (inputToDo.has("finished")){
                    boolean finished = inputToDo.get("finished").asBoolean();
                    toDo.setFinished(finished);
                    if(finished) {
                        toDo.setFinishedDate(new Date()); // Setzt das Finished-Datum, wenn ToDo als abgeschlossen markiert wird.
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

    // Delete existing ToDo by ID
    @DeleteMapping("/todos/{id}")
    public ToDo deleteToDo(@PathVariable("id") long id) {

        ToDo deleteToDo = todoRepository.findById(id);
        if (deleteToDo != null) {
            
            deleteToDo.setAssigneeList(null);
            todoRepository.save(deleteToDo);
            todoRepository.deleteById(id);
             
            return deleteToDo;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
            String.format("Requested ToDo with ID %s was not found!", id));
    }
    
    
}
