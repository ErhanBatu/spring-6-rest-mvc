package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.model.Beer;
import guru.springframework.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@RestController
//this is my base mapping
@RequestMapping("/api/v1/beer")
public class BeerController {

    //normally i have to create a const for this but i am using @AllArgsConstructor
    private final BeerService beerService;

    @DeleteMapping("{beerId}")
    public ResponseEntity deleteBuId(@PathVariable("beerId") UUID beerId){

        beerService.deleteById(beerId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    //it will search for by Id and update it
    @PutMapping("{beerId}")
    public ResponseEntity updateById(@PathVariable("beerId") UUID beerId, @RequestBody Beer beer){

        beerService.updateByBeerId(beerId, beer);

        //NO-CONTENT 204 status
        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }

    //RequestBody BIND THE JSON BODY BEER OBJECT
    @PostMapping
    //@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity handlePost(@RequestBody  Beer beer){

        Beer savedBeer = beerService.saveNewBeer(beer);

        //I will have a location value in header, you can see it in postman
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location","/api/v1/beer/" + savedBeer.getId().toString());

        //IT RETURNS 201 STATUS
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    //first you have to run Spring6RestMvcApplication and after that in postman "http://localhost:8080/api/v1/beer" you will see your data
    //requestmapping will convert it json format
    //I am getting requestmapping from above
    @RequestMapping(method = RequestMethod.GET)
    public List<Beer> listBeers(){
        return beerService.listBeers();
    }

    //this is my get url
    //@PathVariable explicitly say my beerId in requestMapping and beerId in getBeerById they are the same
    //method = RequestMethod.GET I am saying this is gonna be only get not post
    @RequestMapping(value = "/{beerId}", method = RequestMethod.GET)
    public Beer getBeerById(@PathVariable("beerId") UUID beerId){

        log.debug("Get Beer Id - in controller -1234 abc");

        return beerService.getBeerById(beerId);
    }

}
