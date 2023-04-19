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

//my api http://api.springframework.guru/api/v1/beer
//rest controller will get the result from api

@Slf4j
@RequiredArgsConstructor
@RestController
//this is my base mapping
//you don't need to use it because i defined below public static final
//@RequestMapping("/api/v1/beer")
public class BeerController {

    //I define the path and make them static I can reach anywhere
    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";

    //normally i have to create a const for this but i am using @AllArgsConstructor
    private final BeerService beerService;

    @PatchMapping(BEER_PATH_ID)
    public ResponseEntity updateBeerPatchById(@PathVariable("beerId") UUID beerId, @RequestBody Beer beer){

        beerService.patchBeerById(beerId, beer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }

    @DeleteMapping(BEER_PATH_ID)
    public ResponseEntity deleteBuId(@PathVariable("beerId") UUID beerId){

        beerService.deleteById(beerId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    //it will search for by Id and update it
    @PutMapping(BEER_PATH_ID)
    public ResponseEntity updateById(@PathVariable("beerId") UUID beerId, @RequestBody Beer beer){

        beerService.updateByBeerId(beerId, beer);

        //NO-CONTENT 204 status
        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }

    //RequestBody BIND THE JSON BODY BEER OBJECT
    @PostMapping(BEER_PATH)
    //@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity handlePost(@RequestBody  Beer beer){

        Beer savedBeer = beerService.saveNewBeer(beer);

        //I will have a location value in header, you can see it in postman
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location",BEER_PATH + savedBeer.getId().toString());

        //IT RETURNS 201 STATUS
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    //first you have to run Spring6RestMvcApplication and after that in postman "http://localhost:8080/api/v1/beer" you will see your data
    //requestmapping will convert it json format
    //I am getting requestmapping from above
    @GetMapping(value = BEER_PATH)
    public List<Beer> listBeers(){
        return beerService.listBeers();
    }

    //it will be handled by this method if anything throw a not found exception inside this class
    //I will make it globall and carry it to the exceptioncontroller class look at there
//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity handleNotFoundException(){
//
//        System.out.println("in exception handler");
//
//        return ResponseEntity.notFound().build();
//    }

    //this is my get url
    //@PathVariable explicitly say my beerId in requestMapping and beerId in getBeerById they are the same
    //method = RequestMethod.GET I am saying this is gonna be only get not post
    @RequestMapping(value = BEER_PATH_ID, method = RequestMethod.GET)
    public Beer getBeerById(@PathVariable("beerId") UUID beerId){

        log.debug("Get Beer Id - in controller -1234 abc");

        //I made it optinal in the service so that i can define here
        return beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);
    }

}
