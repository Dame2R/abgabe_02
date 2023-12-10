package de.unistuttgart.iste.ese.api.todos;

import com.fasterxml.jackson.annotation.JsonFormat;
import de.unistuttgart.iste.ese.api.assignees.Assignee;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(name = "todos")

public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotNull
    @Size(min = 1, max = 40)
    private String title;


    @NotNull

    private String description;

    private boolean finished;

    @ManyToMany (targetEntity = de.unistuttgart.iste.ese.api.assignees.Assignee.class)
    private List<Assignee> assigneeList ;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT) 
    private Date createdDate;
    
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private Date dueDate;
    
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private Date finishedDate;

    public ToDo() {
        this.finished = false;
        this.assigneeList = new ArrayList<>();
        this.createdDate = new Date();
    }

    public ToDo(String title, String description, boolean finished, Set<Assignee> assigneeList, Date createdDate, Date dueDate, Date finishedDate) {
        this.title = title;
        this.description = description;
        this.finished = false;
        this.assigneeList = new ArrayList<>();
        this.createdDate = new Date();
        this.dueDate = dueDate;
        this.finishedDate = finishedDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public List<Assignee> getAssigneeList() {
        return assigneeList;
    }

    public void setAssigneeList(List<Assignee> assigneeList) {
        this.assigneeList = assigneeList;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(Date finishedDate) {
        this.finishedDate = finishedDate;
    }
}
