package root.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import lombok.*;

@Entity
@Data
public class Task {

    public Task() {}

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @NonNull
    @Size(min = 1, max = 20, message = "Task name must be from 1 to 20 symbols.")
    private String taskName;
    private String taskDescription;
}
