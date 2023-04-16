package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.model.Beer;
import guru.springframework.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

//my api http://api.springframework.guru/api/v1/beer
//rest controller will get the result from api

@Slf4j
@AllArgsConstructor
@RestController
public class BeerController {

    //normally i have to create a const for this but i am using @AllArgsConstructor
    private final BeerService beerService;

    //first you have to run Spring6RestMvcApplication and after that in postman "http://localhost:8080/api/v1/beer" you will see your data
    //requestmapping will convert it json format
    @RequestMapping("/api/v1/beer")
    public List<Beer> listBeers(){
        return beerService.listBeers();
    }

    public Beer getBeerById(UUID id){

        log.debug("Get Beer Id - in controller");

        return beerService.getBeerById(id);
    }

}
