package com.volonteers.restcontroller;

import com.volonteers.model.Language;
import com.volonteers.service.LanguageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/language-api")
public class AdminLanguagesController {

    private final LanguageService languageService;
    public AdminLanguagesController(LanguageService languageService) {
        this.languageService = languageService;
    }
    @GetMapping("/all-languages")
    public Iterable<Language> getAllLanguages() {
        return this.languageService.getLanguages();
    }
}
