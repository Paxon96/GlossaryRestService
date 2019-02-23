package pl.paxon96.glossary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.paxon96.glossary.entity.GlossaryWord;
import pl.paxon96.glossary.repository.GlossaryWordRepository;

import java.util.List;

@Service
public class GlossaryService {

    @Autowired
    private GlossaryWordRepository glossaryWordRepository;


    public List<GlossaryWord> getAllWordsFromDatabase(){
        return glossaryWordRepository.findAll();
    }
}
