package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TrelloValidatorTestSuite {
    @InjectMocks
    private TrelloValidator trelloValidator;

    @Test
    public void shouldValidateTrelloBoards() {
        //Given
        final TrelloBoard boardToApproveByValidator = new TrelloBoard("1", "name", new ArrayList<>());
        final TrelloBoard boardToRejectByValidator = new TrelloBoard("2", "TeSt 1", new ArrayList<>());
        final List<TrelloBoard> trelloBoards = new ArrayList<>(Arrays.asList(boardToApproveByValidator, boardToRejectByValidator));

        //When
        final List<TrelloBoard> filteredBoards = trelloValidator.validateTrelloBoards(trelloBoards);

        //Then
        assertEquals(1, filteredBoards.size());

        filteredBoards.forEach(trelloBoard -> {
            assertEquals("1", trelloBoard.getId());
            assertEquals("name", trelloBoard.getName());
            assertNotNull(trelloBoard.getLists());
        });
    }

    @Test
    public void ShouldReturnEmptyListBecauseOfValidating() {
        //Given
        final TrelloBoard boardToRejectByValidator = new TrelloBoard("1", "TeSt 1", new ArrayList<>());
        final TrelloBoard boardToRejectByValidator2 = new TrelloBoard("2", "TeSt 2", new ArrayList<>());
        final List<TrelloBoard> trelloBoards = new ArrayList<>(Arrays.asList(boardToRejectByValidator, boardToRejectByValidator2));

        //When
        final List<TrelloBoard> filteredBoards = trelloValidator.validateTrelloBoards(trelloBoards);

        //Then
        assertNotNull(filteredBoards);
        assertEquals(0, filteredBoards.size());
    }

}