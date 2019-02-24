package pl.paxon96.glossary.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Builder
@Table(name = "words")
@NoArgsConstructor
@AllArgsConstructor
public class GlossaryWord {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "pl_word")
    private String polishWorld;

    @Column(name = "en_word")
    private String englishWorld;

    @Column(name = "add_date")
    private Timestamp addDate;

}
