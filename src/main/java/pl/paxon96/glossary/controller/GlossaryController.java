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
import pl.paxon96.glossary.service.GlossaryService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("glossary")
public class GlossaryController {

    @Autowired
    private GlossaryService glossaryService;

    @RequestMapping(method = RequestMethod.GET)
    public String getGlossaryPage(){
        return "glossaryPage";
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String getAllWords(Model model){
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

}
