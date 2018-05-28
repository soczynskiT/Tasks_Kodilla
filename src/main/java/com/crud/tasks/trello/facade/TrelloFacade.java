package com.crud.tasks.trello.facade;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TrelloFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloFacade.class);

    @Autowired
    TrelloService service;

    @Autowired
    TrelloMapper mapper;

    @Autowired
    TrelloValidator validator;

    public List<TrelloBoardDto> fetchTrelloBoards() {
        List<TrelloBoard> trelloBoards = mapper.mapToBoards(service.fetchTrelloBoards());
        List<TrelloBoard> filteredBoards = validator.validateTrelloBoards(trelloBoards);
        return mapper.mapToBoardsDto(filteredBoards);
    }
    public CreatedTrelloCardDto createCard(final TrelloCardDto trelloCardDto) {
        TrelloCard trelloCard = mapper.mapToCard(trelloCardDto);
        validator.validateCard(trelloCard);
        return service.createTrelloCard(mapper.mapToCardDto(trelloCard));
    }
}
