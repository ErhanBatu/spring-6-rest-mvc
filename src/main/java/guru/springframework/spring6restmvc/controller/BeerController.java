package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.model.Beer;
import guru.springframework.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.UUID;

//To use Slf4j you put this in application.properties: logging.level.guru.springframework=debug
@Slf4j
@AllArgsConstructor
@Controller
public class BeerController {

    //Normally I have to create an const for that but I use AllArgsConst
    private final BeerService beerService;

    public Beer getBeerById(UUID id){

        log.debug("Get Beer by id in controller, thanks to Slf4j I can write this log");

        return beerService.getBeerById(id);
    }

}
