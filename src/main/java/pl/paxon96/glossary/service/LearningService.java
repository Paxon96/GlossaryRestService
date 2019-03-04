package pl.paxon96.glossary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.paxon96.glossary.entity.GlossaryWord;
import pl.paxon96.glossary.repository.GlossaryWordRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class LearningService {

    @Autowired
    private GlossaryWordRepository glossaryWordRepository;

    @Value("${glossary.repeat}")
    private int repeatTimes;

    public List<GlossaryWord> getRandomWordsToLearn(int n){
        List<GlossaryWord> glossaryWords = glossaryWordRepository.findGlossaryWordByIsLearned(false);
        Collections.shuffle(glossaryWords);
        if(glossaryWords.size() < n )
            return glossaryWords.subList(0, glossaryWords.size());
        else
            return glossaryWords.subList(0, n);
    }


    public List<GlossaryWord> setWordLearnead(List<GlossaryWord> glossaryWords, List<GlossaryWord> glossaryWordsLearned){
        glossaryWords.forEach(word ->{
            GlossaryWord glossaryWord = glossaryWordRepository.findGlossaryWordById(word.getId());
            if(glossaryWord.getIsLearned())
                word.setIsLearned(true);
        });

        Optional<GlossaryWord> learnedWord;
        learnedWord = glossaryWords.stream().filter(GlossaryWord::getIsLearned).findFirst();

        if(learnedWord.isPresent()) {
            GlossaryWord wordToSetLearned = learnedWord.get();
            wordToSetLearned.setIsLearned(true);
            wordToSetLearned.setCorrectRepetitionAmount(repeatTimes);
            glossaryWordRepository.save(wordToSetLearned);
            glossaryWordsLearned.add(wordToSetLearned);
            glossaryWords.remove(wordToSetLearned);
        }

        return glossaryWords;
    }

    public void manageWordAfterCorrectAnswer(GlossaryWord glossaryWord) {
        glossaryWord.setCorrectRepetitionAmount(glossaryWord.getCorrectRepetitionAmount() + 1);
        if(glossaryWord.getCorrectRepetitionAmount() == repeatTimes)
            glossaryWord.setIsLearned(true);
        glossaryWordRepository.save(glossaryWord);
    }

    public void manageWordAfterWrongAnswer(GlossaryWord glossaryWord){
        if(glossaryWord.getCorrectRepetitionAmount() >0){
            glossaryWord.setCorrectRepetitionAmount(glossaryWord.getCorrectRepetitionAmount() - 1);
            glossaryWordRepository.save(glossaryWord);
        }
    }
}
