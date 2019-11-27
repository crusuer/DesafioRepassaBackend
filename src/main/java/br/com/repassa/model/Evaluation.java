package br.com.repassa.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Evaluation {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @NotNull(message = "Rated may not be null")
    private Employee rated;

    @ManyToOne
    @JoinColumn(name = "employee_ids", referencedColumnName = "id")
    @NotNull(message = "Rater may not be null")
    private Employee rater;

    @Column
    @Min(0)
    @Max(5)
    private Integer helpful;

    @Column
    @Min(0)
    @Max(5)
    private Integer focused;

    @Column
    @Min(0)
    @Max(5)
    private Integer responsible;

    @Column
    @Min(0)
    @Max(5)
    private Integer creative;

    @Column
    @Min(0)
    @Max(5)
    private Integer leadership;
}
