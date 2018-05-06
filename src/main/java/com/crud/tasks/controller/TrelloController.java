package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/v1/trello")
public class TrelloController {

    @Autowired
    private TrelloClient trelloClient;

    @GetMapping("getTrelloBoards")
    public void getTrelloBoards() {

        List<TrelloBoardDto> trelloBoards = trelloClient.getTrelloBoards();

        trelloBoards.stream()
                .filter(tb -> Objects.nonNull(tb.getId()) && Objects.nonNull(tb.getName()))
                .filter(tb -> tb.getName().toUpperCase().contains("KODILLA"))
                .forEach(tb -> {

                    System.out.println(tb.getName() + " - " + tb.getId());
                    System.out.println("This board contains lists: ");

                    tb.getLists().forEach(trelloList ->
                            System.out.println(trelloList.getName() + " - " + trelloList.getId() + " - " + trelloList.isClosed()));
                });

    }

    @PostMapping("createTrelloCard")
    public CreatedTrelloCard createTrelloCard(@RequestBody TrelloCardDto trelloCardDto) {
        return trelloClient.createNewCard(trelloCardDto);
    }
}

