package com.crud.tasks.trello.client;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class TrelloClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloClient.class);

    @Value("${trello.api.endpoint.prod}")
    private String ApiEndpoint;

    @Value("${trello.app.key}")
    private String AppKey;

    @Value("${trello.app.token}")
    private String Token;

    @Value("${trello.app.username}")
    private String Username;

    @Autowired
    private RestTemplate restTemplate;

    private URI buildUrlForGetTrelloBoards() {
        return UriComponentsBuilder.fromHttpUrl(ApiEndpoint + "/members/" + Username + "/boards")
                .queryParam("key", AppKey)
                .queryParam("token", Token)
                .queryParam("fields", "name,id")
                .queryParam("lists", "all")
                .build().encode().toUri();
    }

    private URI buildUrlForCreateNewCard(TrelloCardDto trelloCardDto) {
        return UriComponentsBuilder.fromHttpUrl(ApiEndpoint + "/cards")
                .queryParam("key", AppKey)
                .queryParam("token", Token)
                .queryParam("name", trelloCardDto.getName())
                .queryParam("desc", trelloCardDto.getDescription())
                .queryParam("pos", trelloCardDto.getPos())
                .queryParam("idList", trelloCardDto.getListId())
                .build().encode().toUri();

    }

    public List<TrelloBoardDto> getTrelloBoards() {

        try {
            TrelloBoardDto[] boardsResponse = restTemplate.getForObject(buildUrlForGetTrelloBoards(), TrelloBoardDto[].class);
            return Optional.ofNullable(boardsResponse).map(Arrays::asList).orElseGet(ArrayList::new);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public CreatedTrelloCard createNewCard(TrelloCardDto trelloCardDto) {
        return restTemplate.postForObject(buildUrlForCreateNewCard(trelloCardDto), null, CreatedTrelloCard.class);
    }
}
