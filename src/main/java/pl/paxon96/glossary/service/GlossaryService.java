package pl.paxon96.glossary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.paxon96.glossary.entity.GlossaryWord;
import pl.paxon96.glossary.repository.GlossaryWordRepository;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GlossaryService {

    @Autowired
    private GlossaryWordRepository glossaryWordRepository;


    public List<GlossaryWord> getAllWordsFromDatabase(){
        List<GlossaryWord> glossaryWords = glossaryWordRepository.findAll();
        return glossaryWords.stream().sorted(Comparator.comparing(GlossaryWord::getPolishWorld)).collect(Collectors.toList());
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

    public void editWord(GlossaryWord wordToEdit){
        GlossaryWord glossaryWord = glossaryWordRepository.findGlossaryWordById(wordToEdit.getId());

        if(!glossaryWord.getEnglishWorld().equalsIgnoreCase(wordToEdit.getEnglishWorld()) || !glossaryWord.getPolishWorld().equalsIgnoreCase(wordToEdit.getPolishWorld())){
            glossaryWord.setPolishWorld(wordToEdit.getPolishWorld());
            glossaryWord.setEnglishWorld(wordToEdit.getEnglishWorld());

            glossaryWordRepository.save(glossaryWord);
        }
    }

    public void deleteWord(int idWordToDelete){
        glossaryWordRepository.delete(glossaryWordRepository.findGlossaryWordById(idWordToDelete));
    }
}
