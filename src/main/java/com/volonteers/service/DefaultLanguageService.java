package com.volonteers.service;

import com.volonteers.model.Language;
import com.volonteers.repository.LanguageRepository;
import org.springframework.stereotype.Service;

@Service
public class DefaultLanguageService implements LanguageService {

    private final LanguageRepository languageRepository;
    public DefaultLanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Override
    public Iterable<Language> getLanguages() {
        return  this.languageRepository.findAll();
    }
}
