package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.services.BeerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
//this test specifically for beer controller, it is gonna invoke beer controller
@WebMvcTest(BeerController.class)
class BeerControllerTest {

    //in the test best practice to use public
//    @Autowired
//    BeerController beerController;

    @Autowired
    MockMvc mockMvc;

    //provide mock it into the spring content, if you don't put it it will give error
    //mockito will create a mock instance and autowired into the beer controller
    @MockBean
    BeerService beerService;

    @Test
    void getBeerById() throws Exception {

        mockMvc.perform(get("/api/v1/beer/" + UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

//        System.out.println(beerController.getBeerById(UUID.randomUUID()));

    }
}