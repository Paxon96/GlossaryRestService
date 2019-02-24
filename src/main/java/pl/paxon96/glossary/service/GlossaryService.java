package pl.paxon96.glossary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.paxon96.glossary.entity.GlossaryWord;
import pl.paxon96.glossary.repository.GlossaryWordRepository;

import java.sql.Timestamp;
import java.util.List;

@Service
public class GlossaryService {

    @Autowired
    private GlossaryWordRepository glossaryWordRepository;


    public List<GlossaryWord> getAllWordsFromDatabase(){
        return glossaryWordRepository.findAll();
    }

    public void createAndSaveNewWord(String polishWord, String englishWord){
        GlossaryWord glossaryWord = GlossaryWord
                .builder()
                .polishWorld(polishWord)
                .englishWorld(englishWord)
                .addDate(new Timestamp(System.currentTimeMillis()))
                .build();

        glossaryWordRepository.save(glossaryWord);
    }
}
