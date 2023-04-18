package guru.springframework.spring6restmvc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6restmvc.model.Beer;
import guru.springframework.spring6restmvc.services.BeerService;
import guru.springframework.spring6restmvc.services.BeerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest
//this test specifically for beer controller, it is gonna invoke beer controller
@WebMvcTest(BeerController.class)
class BeerControllerTest {

    //in the test best practice to use public
//    @Autowired
//    BeerController beerController;

    @Autowired
    MockMvc mockMvc;

    //
    @Autowired
    ObjectMapper objectMapper;

    //provide mock it into the spring content, if you don't put it it will give error
    //mockito will create a mock instance and autowired into the beer controller
    @MockBean
    BeerService beerService;

    BeerServiceImpl beerServiceImpl = new BeerServiceImpl();

    @Test
    void testListBeers() throws Exception {
        given(beerService.listBeers()).willReturn(beerServiceImpl.listBeers());

        mockMvc.perform(get("/api/v1/beer")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    //I convert into JSON
    @Test
    void testCreateNewBeer() throws JsonProcessingException {
        //For convertion you have to use Objectmapper from jakson
        //Above i used autowired and spring will do auto config, I don't need to add this line and also line below
        //Because I used autowired and spring will do auto config and convert the date format
//        ObjectMapper objectMapper = new ObjectMapper();
        //this is for date format otherwise it will give error
//        objectMapper.findAndRegisterModules();

        Beer beer = beerServiceImpl.listBeers().get(0);

        //it produce JSON here
        System.out.println(objectMapper.writeValueAsString(beer));
    }

    @Test
    void getBeerById() throws Exception {

        Beer testBeer = beerServiceImpl.listBeers().get(0);

        //tell mockito return back testbeer object
        given(beerService.getBeerById(testBeer.getId())).willReturn(testBeer);

        mockMvc.perform(get("/api/v1/beer/" + testBeer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())));

//        System.out.println(beerController.getBeerById(UUID.randomUUID()));

    }
}