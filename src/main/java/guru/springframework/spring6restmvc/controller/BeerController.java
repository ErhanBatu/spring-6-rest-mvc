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

//To use Slf4j you put this in application.properties: logging.level.guru.springframework=debug
@Slf4j
@AllArgsConstructor
//Restcontroller retuns you response body as json
@RestController
public class BeerController {

    //Normally I have to create an const for that but I use AllArgsConst
    private final BeerService beerService;

    @RequestMapping("/api/v1/beer")
    public List<Beer> listBeers(){

        return beerService.listBeers();

    }

    public Beer getBeerById(UUID id){

        log.debug("Get Beer by id in controller, thanks to Slf4j I can write this log");

        return beerService.getBeerById(id);
    }

}
