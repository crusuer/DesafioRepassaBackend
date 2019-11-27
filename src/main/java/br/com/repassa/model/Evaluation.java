package br.com.repassa.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Employee rater;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Employee rated;

    @Column
    private Integer helpful;

    @Column
    private Integer focused;

    @Column
    private Integer responsible;

    @Column
    private Integer creative;

    @Column
    private Integer leadership;
}
