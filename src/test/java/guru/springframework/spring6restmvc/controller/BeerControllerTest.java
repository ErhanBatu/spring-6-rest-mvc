package guru.springframework.spring6restmvc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6restmvc.model.Beer;
import guru.springframework.spring6restmvc.services.BeerService;
import guru.springframework.spring6restmvc.services.BeerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    BeerServiceImpl beerServiceImpl;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<Beer> beerArgumentCaptor;

    @BeforeEach
    void setUp(){
        //it's gonna create new beerserviceimpl for each test
        beerServiceImpl = new BeerServiceImpl();
    }

    @Test
    void getBeerByIdNotFound() throws Exception {

        //it will throw an error
        given(beerService.getBeerById(any(UUID.class))).willThrow(NotFoundException.class);

        mockMvc.perform(get(BeerController.BEER_PATH_ID,UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void testPatchBeer() throws Exception {

        Beer beer = beerServiceImpl.listBeers().get(0);

        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", "New Name");

        mockMvc.perform(patch(BeerController.BEER_PATH_ID, beer.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isNoContent());

        verify(beerService).patchBeerById(uuidArgumentCaptor.capture(), beerArgumentCaptor.capture());

        assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(beerMap.get("beerName")).isEqualTo(beerArgumentCaptor.getValue().getBeerName());

    }

    @Test
    void testDeleteBeer() throws Exception {

        Beer beer = beerServiceImpl.listBeers().get(0);

        mockMvc.perform(delete(BeerController.BEER_PATH_ID, beer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        //this is mockito class
//        ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(beerService).deleteById(uuidArgumentCaptor.capture());

        assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void testListBeers() throws Exception {
        given(beerService.listBeers()).willReturn(beerServiceImpl.listBeers());

        mockMvc.perform(get(BeerController.BEER_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    //I convert into JSON
    @Test
    void testCreateNewBeer() throws Exception {
        //For convertion you have to use Objectmapper from jakson
        //Above i used autowired and spring will do auto config, I don't need to add this line and also line below
        //Because I used autowired and spring will do auto config and convert the date format
//        ObjectMapper objectMapper = new ObjectMapper();
        //this is for date format otherwise it will give error
//        objectMapper.findAndRegisterModules();

        Beer beer = beerServiceImpl.listBeers().get(0);
        beer.setVersion(null);
        beer.setId(null);

        //it will return first beer
        given(beerService.saveNewBeer(any(Beer.class))).willReturn(beerServiceImpl.listBeers().get(1));

        mockMvc.perform(post(BeerController.BEER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                //here I inject JSON format here
                        .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));

        //it produce JSON here
//        System.out.println(objectMapper.writeValueAsString(beer));
    }

    @Test
    void testUpdateBeer() throws Exception {
        Beer beer = beerServiceImpl.listBeers().get(0);

        mockMvc.perform(put(BeerController.BEER_PATH_ID, beer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isNoContent());

        verify(beerService).updateByBeerId(any(UUID.class), any(Beer.class));
    }

    @Test
    void getBeerById() throws Exception {

        Beer testBeer = beerServiceImpl.listBeers().get(0);

        //tell mockito return back testbeer object
        given(beerService.getBeerById(testBeer.getId())).willReturn(testBeer);

        mockMvc.perform(get(BeerController.BEER_PATH_ID, testBeer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())));

//        System.out.println(beerController.getBeerById(UUID.randomUUID()));

    }
}