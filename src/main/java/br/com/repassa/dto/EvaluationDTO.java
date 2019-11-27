package br.com.repassa.dto;

import lombok.Data;

@Data
public class EvaluationDTO {
    private Integer helpful;
    private Integer focused;
    private Integer responsible;
    private Integer creative;
    private Integer leadership;
}
