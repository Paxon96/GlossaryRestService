package pl.paxon96.glossary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.paxon96.glossary.entity.GlossaryWord;
import pl.paxon96.glossary.entity.GlossaryWordDTO;
import pl.paxon96.glossary.repository.GlossaryWordRepository;
import pl.paxon96.glossary.service.GlossaryService;

import javax.validation.Valid;

@Controller
@RequestMapping("glossary")
public class GlossaryController {

    @Autowired
    private GlossaryService glossaryService;
    @Autowired
    private GlossaryWordRepository glossaryWordRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String getGlossaryPage(){
        return "glossaryPage";
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String getAllWords(Model model){
        model.asMap().clear();
        model.addAttribute("wordsList", glossaryService.getAllWordsFromDatabase());
        return "glossaryAllWords";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String getAddWord(Model model){
        model.asMap().clear();
        model.addAttribute("glossaryWordDTO", new GlossaryWordDTO());
        return "glossaryAddWord";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String postAddWord(Model model,
                              @ModelAttribute("glossaryWordDTO") @Valid GlossaryWordDTO glossaryWordDTO){
        glossaryService.createAndSaveNewWord(glossaryWordDTO.getPolishWord(), glossaryWordDTO.getEnglishWord());
        return getAddWord(model);
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String getEditWord(Model model, @RequestParam("wordId") int wordId){
        model.asMap().clear();
        model.addAttribute("wordToEdit",glossaryWordRepository.findGlossaryWordById(wordId));
        return "glossaryEditWord";
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String postEditWord(Model model,
                               @RequestParam("wordId") int wordId,
                               @RequestParam("polishWord") String polishWord,
                               @RequestParam("englishWord") String englishWord){


        glossaryService.editWord(GlossaryWord
                .builder()
                .id(wordId)
                .polishWorld(polishWord)
                .englishWorld(englishWord)
                .build());

        return getAllWords(model);
    }


    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public String getDeleteWord(Model model,
                                @RequestParam("wordId") int wordId){

        model.addAttribute("wordToDelete", glossaryWordRepository.findGlossaryWordById(wordId));

        return "glossaryDeleteWord";
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String postDeleteWord(Model model,
                                @RequestParam("wordId") int wordId,
                                @RequestParam("delete") String delete){

        if(delete.equalsIgnoreCase("yes"))
            glossaryService.deleteWord(wordId);

        return getAllWords(model);
    }

    @RequestMapping(value = "relearn", method = RequestMethod.POST)
    public String postRelearnWord(Model model,
                                 @RequestParam("wordId") int wordId){

        GlossaryWord glossaryWord = glossaryWordRepository.findGlossaryWordById(wordId);
        glossaryWord.setIsLearned(false);
        glossaryWord.setCorrectRepetitionAmount(0);

        glossaryWordRepository.save(glossaryWord);

        return getAllWords(model);
    }
}
