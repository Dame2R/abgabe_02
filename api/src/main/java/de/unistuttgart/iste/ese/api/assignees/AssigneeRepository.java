package de.unistuttgart.iste.ese.api.assignees;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AssigneeRepository extends CrudRepository<Assignee, Long> {

    Assignee findByName(String name);

    Assignee findById(long id);
    Assignee deleteById(long id);

    List<Assignee> findAll();
}
