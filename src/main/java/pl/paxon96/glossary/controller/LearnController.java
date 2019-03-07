package pl.paxon96.glossary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.paxon96.glossary.entity.GlossaryWord;
import pl.paxon96.glossary.repository.GlossaryWordRepository;
import pl.paxon96.glossary.service.LearningService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller @RequestMapping("learning") public class LearnController {

    @Value("${glossary.repeat}") private int repeatTimes;

    @Value("${glossary.wordAmountPerSession}") private int wordsInSession;

    @Autowired private LearningService learningService;

    @Autowired private GlossaryWordRepository glossaryWordRepository;

    private List<GlossaryWord> glossaryWordsToLearn;
    private List<GlossaryWord> glossaryWordsLearnded = new ArrayList<>();
    private Random random = new Random();

    @RequestMapping(method = RequestMethod.GET) public String getLearningPage(Model model) {
        glossaryWordsToLearn = learningService.getRandomWordsToLearn(wordsInSession);
        model.addAttribute("repeat", repeatTimes);
        model.addAttribute("words", wordsInSession);

        glossaryWordsLearnded.clear();

        return "learningPage";
    }

    @RequestMapping(value = "session", method = RequestMethod.GET) public String getSession(Model model) {
        model.asMap().clear();

        if (glossaryWordsToLearn.size() == 0)
            return "redirect:end";

        glossaryWordsToLearn = learningService.setWordLearnead(glossaryWordsToLearn, glossaryWordsLearnded);

        if (glossaryWordsToLearn.size() == 0)
            return "redirect:end";

        int wordIndex = random.nextInt(glossaryWordsToLearn.size());

        model.addAttribute("wordId", glossaryWordsToLearn.get(wordIndex).getId());

        int plOrEn = random.nextInt(2) + 1;

        model.addAttribute("translation", plOrEn);

        if (plOrEn == 1)
            model.addAttribute("wordToTranslate", glossaryWordsToLearn.get(wordIndex).getPolishWorld());
        else
            model.addAttribute("wordToTranslate", glossaryWordsToLearn.get(wordIndex).getEnglishWorld());

        return "learningSessionPage";
    }

    @RequestMapping(value = "check", method = RequestMethod.POST)
    public String postCheckWord(Model model, @RequestParam("wordId") int wordId, @RequestParam("translation") int plOrEn,
            @RequestParam("wordToCheck") String wordToCheck) {

        GlossaryWord glossaryWord = glossaryWordRepository.findGlossaryWordById(wordId);

        if (plOrEn == 1) {
            if (!glossaryWord.getEnglishWorld().equalsIgnoreCase(wordToCheck)) {
                model.addAttribute("wordTranslatorError", "Złe tłumaczenie");
                model.addAttribute("correctWord", glossaryWord.getEnglishWorld());
                learningService.manageWordAfterWrongAnswer(glossaryWord);
                return "learninSessionPageError";
            } else {
                learningService.manageWordAfterCorrectAnswer(glossaryWord);
            }

        } else if (!glossaryWord.getPolishWorld().equalsIgnoreCase(wordToCheck)) {
            model.addAttribute("wordTranslatorError", "Złe tłumaczenie");
            model.addAttribute("correctWord", glossaryWord.getPolishWorld());
            learningService.manageWordAfterWrongAnswer(glossaryWord);
            return "learninSessionPageError";
        } else {
            learningService.manageWordAfterCorrectAnswer(glossaryWord);
        }

        return "redirect:session";
    }

    @RequestMapping(value = "end", method = RequestMethod.GET) public String getEndSession(Model model) {
        model.asMap().clear();
        model.addAttribute("learndedWords", glossaryWordsLearnded);
        return "learningSessionFinalPage";
    }
}
