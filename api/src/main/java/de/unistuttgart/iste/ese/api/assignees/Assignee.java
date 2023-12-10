package de.unistuttgart.iste.ese.api.assignees;

import javax.persistence.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="assignees")
public class Assignee {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    @Size(min=1)
    private String prename;

    
    @Size(min=1)
    private String name;

    @Email(regexp="^.*uni-stuttgart.de$")
    @NotNull
    private String email;
    
    public Assignee() {}

    public Assignee(String prename, String name, String email) {
        
        this.name = name;
        this.prename = prename;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPrename() {
        return prename;
    }

    public void setPrename(String prename) {
        this.prename = prename;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
