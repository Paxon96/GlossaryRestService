package pl.paxon96.glossary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.paxon96.glossary.entity.GlossaryWord;

import java.util.List;

public interface GlossaryWordRepository extends JpaRepository<GlossaryWord, Long> {

    GlossaryWord findGlossaryWordById(int wordId);

    List<GlossaryWord> findGlossaryWordByIsLearned(boolean isLearned);
}
