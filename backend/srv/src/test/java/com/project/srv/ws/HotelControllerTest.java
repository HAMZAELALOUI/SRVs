package com.project.srv.ws;

import com.project.srv.bean.Hotel;
import com.project.srv.bean.Ville;
import com.project.srv.service.HotelService;
import com.project.srv.service.VilleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HotelController.class)
public class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HotelService hotelService;

    @MockBean
    private VilleService villeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByEmplacement() throws Exception {
        List<Hotel> hotelList = new ArrayList<>();
        hotelList.add(new Hotel());

        when(hotelService.findByEmplacement(anyString())).thenReturn(hotelList);

        mockMvc.perform(get("/Gestion-Vol/Hotel/emplacement/testEmplacement")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindByNombreEtoiles() throws Exception {
        List<Hotel> hotelList = new ArrayList<>();
        hotelList.add(new Hotel());

        when(hotelService.findByNombreEtoiles(anyInt())).thenReturn(hotelList);

        mockMvc.perform(get("/Gestion-Vol/Hotel/etoiles/5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindActiviteWithVilleById() throws Exception {
        Optional<Hotel> hotel = Optional.of(new Hotel());

        when(hotelService.findActiviteWithVilleById(anyLong())).thenReturn(hotel);

        mockMvc.perform(get("/Gestion-Vol/Hotel/hotel/HotelDetails/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }



    @Test
    public void testGetAllHotels() throws Exception {
        List<Hotel> hotelList = new ArrayList<>();
        hotelList.add(new Hotel());

        when(hotelService.findAll()).thenReturn(hotelList);

        mockMvc.perform(get("/Gestion-Vol/Hotel")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteById() throws Exception {
        mockMvc.perform(delete("/Gestion-Vol/Hotel/id/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteByEmplacement() throws Exception {
        mockMvc.perform(delete("/Gestion-Vol/Hotel/emplacement/testEmplacement")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteByNombreEtoiles() throws Exception {
        mockMvc.perform(delete("/Gestion-Vol/Hotel/etoiles/5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testSaveHotel() throws Exception {
        Hotel hotel = new Hotel();
        mockMvc.perform(post("/Gestion-Vol/Hotel/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"nom\": \"testHotel\" }"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateHotel() throws Exception {
        Hotel hotel = new Hotel();
        when(hotelService.updateHotel(anyLong(), any(Hotel.class))).thenReturn(1);

        mockMvc.perform(put("/Gestion-Vol/Hotel/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"nom\": \"testHotelUpdated\" }"))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindByPrixChambresLessThan() throws Exception {
        List<Hotel> hotelList = new ArrayList<>();
        hotelList.add(new Hotel());

        when(hotelService.findByPrixChambresLessThan(anyDouble())).thenReturn(hotelList);

        mockMvc.perform(get("/Gestion-Vol/Hotel/prixChambres/lessThan/100.0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindByPrixChambresBetween() throws Exception {
        List<Hotel> hotelList = new ArrayList<>();
        hotelList.add(new Hotel());

        when(hotelService.findByPrixChambresBetween(anyDouble(), anyDouble())).thenReturn(hotelList);

        mockMvc.perform(get("/Gestion-Vol/Hotel/prixChambres/between/100.0/200.0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindByNomVille() throws Exception {
        List<Hotel> hotelList = new ArrayList<>();
        hotelList.add(new Hotel());

        when(hotelService.findByNomVille(anyString())).thenReturn(hotelList);

        mockMvc.perform(get("/Gestion-Vol/Hotel/ville/nom/testVille")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindByNomVilleAndDateAAndDateD() throws Exception {
        List<Hotel> hotelList = new ArrayList<>();
        hotelList.add(new Hotel());

        when(hotelService.rechercherParNomVilleEtDateAetDateD(anyString(), anyString(), anyString())).thenReturn(hotelList);

        mockMvc.perform(get("/Gestion-Vol/Hotel/ville/testVille/date/2022-01-01/2022-01-10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindByDateAAndDateD() throws Exception {
        List<Hotel> hotelList = new ArrayList<>();
        hotelList.add(new Hotel());

        when(hotelService.findByDateAAndDateD(anyString(), anyString())).thenReturn(hotelList);

        mockMvc.perform(get("/Gestion-Vol/Hotel/date/2022-01-01/2022-01-10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
