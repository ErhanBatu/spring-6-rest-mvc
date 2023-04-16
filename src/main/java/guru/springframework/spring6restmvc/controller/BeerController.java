package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.model.Beer;
import guru.springframework.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
