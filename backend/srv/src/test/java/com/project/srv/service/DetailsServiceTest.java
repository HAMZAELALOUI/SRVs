package com.project.srv.service;

import com.project.srv.bean.Details;
import com.project.srv.dao.DetailsDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DetailsServiceTest {

    @Mock
    private DetailsDao detailsDao;

    @InjectMocks
    private DetailsService detailsService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindByNombreParticipants() {
        int nombreParticipants = 5;
        Details details = new Details();
        details.setNombreParticipants(nombreParticipants);
        List<Details> detailsList = Arrays.asList(details);

        when(detailsDao.findByNombreParticipants(nombreParticipants)).thenReturn(detailsList);

        List<Details> result = detailsService.findByNombreParticipants(nombreParticipants);

        assertNotNull(result);
        assertEquals(detailsList, result);
        verify(detailsDao, times(1)).findByNombreParticipants(nombreParticipants);
    }

    @Test
    public void testFindByPrixTotalActivite() {
        double prixTotalActivite = 100.0;
        Details details = new Details();
        details.setPrixTotalActivite(prixTotalActivite);
        List<Details> detailsList = Arrays.asList(details);

        when(detailsDao.findByPrixTotalActivite(prixTotalActivite)).thenReturn(detailsList);

        List<Details> result = detailsService.findByPrixTotalActivite(prixTotalActivite);

        assertNotNull(result);
        assertEquals(detailsList, result);
        verify(detailsDao, times(1)).findByPrixTotalActivite(prixTotalActivite);
    }

    @Test
    public void testDeleteByNombreParticipants() {
        int nombreParticipants = 5;
        doNothing().when(detailsDao).deleteByNombreParticipants(nombreParticipants);

        detailsService.deleteByNombreParticipants(nombreParticipants);

        verify(detailsDao, times(1)).deleteByNombreParticipants(nombreParticipants);
    }

    @Test
    public void testDeleteByPrixTotalActivite() {
        double prixTotalActivite = 100.0;
        doNothing().when(detailsDao).deleteByPrixTotalActivite(prixTotalActivite);

        detailsService.deleteByPrixTotalActivite(prixTotalActivite);

        verify(detailsDao, times(1)).deleteByPrixTotalActivite(prixTotalActivite);
    }
}
