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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
//WebMvcTest this is for testing with mock mvc
@WebMvcTest(BeerController.class)
class BeerControllerTest {

    //in test you should use public property
//    @Autowired
//    BeerController beerController;

    @Autowired
    MockMvc mockMvc;

    //You don't have to create autowired because mockbean will already do that and also you can use mockito properties
    @MockBean
    BeerService beerService;

    @Test
    void getBeerById() throws Exception {

        mockMvc.perform(get("/api/v1/beer/" + UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }
}