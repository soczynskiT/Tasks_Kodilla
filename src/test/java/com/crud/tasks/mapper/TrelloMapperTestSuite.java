package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TrelloMapperTestSuite {
    @Autowired
    TrelloMapper mapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloMapperTestSuite.class);

    @Test
    public void mapToBoards() {
        //Given
        final TrelloBoardDto trelloBoardDto1 = new TrelloBoardDto("testId1", "testName1", new ArrayList<>());
        final TrelloListDto trelloListDto1 = new TrelloListDto("1", "name1", true);
        trelloBoardDto1.getLists().add(trelloListDto1);
        final TrelloBoardDto trelloBoardDto2 = new TrelloBoardDto("testId2", "testName2", new ArrayList<>());
        final TrelloListDto trelloListDto2 = new TrelloListDto("2", "name2", false);
        trelloBoardDto2.getLists().add(trelloListDto2);
        final List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>(Arrays.asList(trelloBoardDto1, trelloBoardDto2));
        //When
        final List<TrelloBoard> trelloBoards = mapper.mapToBoards(trelloBoardDtos);
        //Then
        assertEquals(2, trelloBoards.size());

        LOGGER.info("Checking elements of  Trello boards list (mapped to domain)...");
        for (TrelloBoard board : trelloBoards) {
            System.out.println("Board ID: " + board.getId() +
                    "\nBoard name: " + board.getName() +
                    "\nLists no: " + board.getLists().size());
        }
    }

    @Test
    public void mapToBoardsDto() {
        //Given
        final TrelloBoard trelloBoard1 = new TrelloBoard("testId1", "testName1", new ArrayList<>());
        final TrelloList trelloList1 = new TrelloList("1", "name1", true);
        trelloBoard1.getLists().add(trelloList1);
        final TrelloBoard trelloBoard2 = new TrelloBoard("testId2", "testName2", new ArrayList<>());
        final TrelloList trelloList2 = new TrelloList("2", "name2", false);
        trelloBoard2.getLists().add(trelloList2);
        final List<TrelloBoard> trelloBoards = new ArrayList<>(Arrays.asList(trelloBoard1, trelloBoard2));
        //When
        final List<TrelloBoardDto> trelloBoardDtos = mapper.mapToBoardsDto(trelloBoards);
        //Then
        assertEquals(2, trelloBoardDtos.size());

        LOGGER.info("Checking elements of Trello boards list (mapped to DTO)...");
        for (TrelloBoardDto boardDto : trelloBoardDtos) {
            System.out.println("Board ID: " + boardDto.getId() +
                    "\nBoard name: " + boardDto.getName() +
                    "\nLists no: " + boardDto.getLists().size());
        }
    }

    @Test
    public void mapToList() {
        //Given
        final TrelloListDto trelloListDto1 = new TrelloListDto("1", "name1", true);
        final TrelloListDto trelloListDto2 = new TrelloListDto("2", "name2", false);
        final List<TrelloListDto> trelloListDtos = new ArrayList<>(Arrays.asList(trelloListDto1, trelloListDto2));
        //When
        final List<TrelloList> trelloLists = mapper.mapToList(trelloListDtos);
        //Then
        assertEquals(2, trelloLists.size());

        LOGGER.info("Checking elements of  Trello list collection (mapped to domain)...");
        for (TrelloList list : trelloLists) {
            System.out.println("List ID: " + list.getId() +
                    "\nList name: " + list.getName() +
                    "\nIs closed: " + list.isClosed());
        }

    }

    @Test
    public void mapToListDto() {
        //Given
        final TrelloList trelloList1 = new TrelloList("1", "name1", true);
        final TrelloList trelloList2 = new TrelloList("2", "name2", false);
        final List<TrelloList> trelloLists = new ArrayList<>(Arrays.asList(trelloList1, trelloList2));
        //When
        final List<TrelloListDto> trelloListDtos = mapper.mapToListDto(trelloLists);
        //Then
        assertEquals(2, trelloListDtos.size());

        LOGGER.info("Checking elements of  Trello list collection (mapped to DTO)...");
        for (TrelloListDto listDto : trelloListDtos) {
            System.out.println("List ID: " + listDto.getId() +
                    "\nList name: " + listDto.getName() +
                    "\nIs closed: " + listDto.isClosed());
        }
    }

    @Test
    public void mapToCardDto() {
        //Given
        final TrelloCard trelloCard = new TrelloCard("cardName", "cardDesc", "cardPos", "cardListId");
        //When
        final TrelloCardDto trelloCardDto = mapper.mapToCardDto(trelloCard);
        //Then
        assertEquals("cardName", trelloCardDto.getName());
        assertEquals("cardDesc", trelloCardDto.getDescription());
        assertEquals("cardPos", trelloCardDto.getPos());
        assertEquals("cardListId", trelloCardDto.getListId());
    }

    @Test
    public void mapToCard() {
        //Given
        final TrelloCardDto trelloCardDto = new TrelloCardDto("cardName", "cardDesc", "cardPos", "cardListId");
        //When
        final TrelloCard trelloCard = mapper.mapToCard(trelloCardDto);
        //Then
        assertEquals("cardName", trelloCard.getName());
        assertEquals("cardDesc", trelloCard.getDescription());
        assertEquals("cardPos", trelloCard.getPos());
        assertEquals("cardListId", trelloCard.getListId());
    }
}