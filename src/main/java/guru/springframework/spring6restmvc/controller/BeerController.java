package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.model.Beer;
import guru.springframework.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

//To use Slf4j you put this in application.properties: logging.level.guru.springframework=debug
@Slf4j
@RequiredArgsConstructor
//Restcontroller retuns you response body as json, this is gonna convert to JSON
@RestController
public class BeerController {

    public static final String BEER_PATH = "/api/v1/beer";

    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";

    //Normally I have to create an const for that but I use AllArgsConst
    private final BeerService beerService;

    //POST
    @PostMapping(BEER_PATH)
    //RequestBody it is gonna bind the JSON with the beer object
    public ResponseEntity handlePost(@RequestBody Beer beer){

        Beer savedBeer = beerService.saveNewBeer(beer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", BEER_PATH + "/" + savedBeer.getId().toString());

        //we create headers and adding response entity, you have to put headres here otherwise you cannot see location
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    //GET
    @GetMapping(BEER_PATH)
    public List<Beer> listBeers(){

        return beerService.listBeers();

    }

    //GET
    @GetMapping(BEER_PATH_ID)
    public Beer getBeerById(@PathVariable("beerId") UUID beerId){

        log.debug("Get Beer by id in controller, thanks to Slf4j I can write this log");

        return beerService.getBeerById(beerId);
    }

}
