package com.project.srv.service;

import com.project.srv.bean.Administration;
import com.project.srv.dao.AdministartionDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AdministartionServiceTest {

    @Mock
    private AdministartionDao administartionDao;

    @InjectMocks
    private AdministartionService administartionService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindByName() {
        String name = "admin";
        Administration admin = new Administration();
        admin.setName(name);

        when(administartionDao.findByName(name)).thenReturn(admin);

        Administration result = administartionService.findByName(name);

        assertNotNull(result);
        assertEquals(admin, result);
        verify(administartionDao, times(1)).findByName(name);
    }

    @Test
    public void testFindById() {
        Long id = 1L;
        Administration admin = new Administration();

        when(administartionDao.findById(id)).thenReturn(Optional.of(admin));

        Optional<Administration> result = administartionService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(admin, result.get());
        verify(administartionDao, times(1)).findById(id);
    }

    @Test
    public void testDeleteById() {
        Long id = 1L;
        doNothing().when(administartionDao).deleteById(id);

        administartionService.deleteById(id);

        verify(administartionDao, times(1)).deleteById(id);
    }
}
