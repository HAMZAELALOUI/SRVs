package com.project.srv.service;

import com.project.srv.bean.Utilisateur;
import com.project.srv.dao.UtilisateurDao;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UtilisateurServiceTest {

    @Mock
    private UtilisateurDao utilisateurDao;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private UtilisateurSevice utilisateurSevice;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Ignore
    @Test
    public void testSave() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail("test@example.com");
        utilisateur.setPassword("password");

        when(utilisateurDao.findByEmail(utilisateur.getEmail())).thenReturn(null);
        when(bCryptPasswordEncoder.encode(utilisateur.getPassword())).thenReturn("encryptedPassword");
        when(utilisateurDao.save(utilisateur)).thenReturn(utilisateur);

        int result = utilisateurSevice.save(utilisateur);

        assertEquals(1, result);
        verify(utilisateurDao, times(1)).findByEmail(utilisateur.getEmail());
        verify(bCryptPasswordEncoder, times(1)).encode(utilisateur.getPassword());
        verify(utilisateurDao, times(1)).save(utilisateur);
    }

    @Test
    public void testSave_UserExists() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail("test@example.com");

        when(utilisateurDao.findByEmail(utilisateur.getEmail())).thenReturn(utilisateur);

        int result = utilisateurSevice.save(utilisateur);

        assertEquals(-1, result);
        verify(utilisateurDao, times(1)).findByEmail(utilisateur.getEmail());
        verify(bCryptPasswordEncoder, never()).encode(anyString());
        verify(utilisateurDao, never()).save(any(Utilisateur.class));
    }

    @Test
    public void testUpdate() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(1L);
        utilisateur.setEmail("newemail@example.com");
        utilisateur.setPassword("newpassword");

        Utilisateur existingUser = new Utilisateur();
        existingUser.setId(1L);
        existingUser.setPassword("encryptedPassword");

        when(utilisateurDao.findById(utilisateur.getId())).thenReturn(Optional.of(existingUser));
        when(bCryptPasswordEncoder.encode(utilisateur.getPassword())).thenReturn("newEncryptedPassword");
        when(bCryptPasswordEncoder.matches(utilisateur.getPassword(), existingUser.getPassword())).thenReturn(false);
        when(utilisateurDao.save(existingUser)).thenReturn(existingUser);

        int result = utilisateurSevice.update(utilisateur);

        assertEquals(1, result);
        assertEquals("newEncryptedPassword", existingUser.getPassword());
        verify(utilisateurDao, times(1)).findById(utilisateur.getId());
        verify(bCryptPasswordEncoder, times(1)).encode(utilisateur.getPassword());
        verify(utilisateurDao, times(1)).save(existingUser);
    }

    @Test
    public void testUpdate_UserNotFound() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(1L);

        when(utilisateurDao.findById(utilisateur.getId())).thenReturn(Optional.empty());

        int result = utilisateurSevice.update(utilisateur);

        assertEquals(-1, result);
        verify(utilisateurDao, times(1)).findById(utilisateur.getId());
        verify(bCryptPasswordEncoder, never()).encode(anyString());
        verify(utilisateurDao, never()).save(any(Utilisateur.class));
    }

    @Test
    public void testLoginUser_Success() {
        String email = "test@example.com";
        String password = "password";
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail(email);
        utilisateur.setPassword("encryptedPassword");

        when(utilisateurDao.findByEmail(email)).thenReturn(utilisateur);
        when(bCryptPasswordEncoder.matches(password, utilisateur.getPassword())).thenReturn(true);

        int result = utilisateurSevice.loginUser(email, password);

        assertEquals(2, result);
        verify(utilisateurDao, times(1)).findByEmail(email);
        verify(bCryptPasswordEncoder, times(1)).matches(password, utilisateur.getPassword());
    }

    @Test
    public void testLoginUser_Admin() {
        String email = "admin@gmail.com";
        String password = "password";
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail(email);
        utilisateur.setPassword("encryptedPassword");

        when(utilisateurDao.findByEmail(email)).thenReturn(utilisateur);
        when(bCryptPasswordEncoder.matches(password, utilisateur.getPassword())).thenReturn(true);

        int result = utilisateurSevice.loginUser(email, password);

        assertEquals(1, result);
        verify(utilisateurDao, times(1)).findByEmail(email);
        verify(bCryptPasswordEncoder, times(1)).matches(password, utilisateur.getPassword());
    }

    @Test
    public void testLoginUser_UserNotFound() {
        String email = "test@example.com";
        String password = "password";

        when(utilisateurDao.findByEmail(email)).thenReturn(null);

        int result = utilisateurSevice.loginUser(email, password);

        assertEquals(-1, result);
        verify(utilisateurDao, times(1)).findByEmail(email);
        verify(bCryptPasswordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    public void testLoginUser_InvalidPassword() {
        String email = "test@example.com";
        String password = "password";
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail(email);
        utilisateur.setPassword("encryptedPassword");

        when(utilisateurDao.findByEmail(email)).thenReturn(utilisateur);
        when(bCryptPasswordEncoder.matches(password, utilisateur.getPassword())).thenReturn(false);

        int result = utilisateurSevice.loginUser(email, password);

        assertEquals(-2, result);
        verify(utilisateurDao, times(1)).findByEmail(email);
        verify(bCryptPasswordEncoder, times(1)).matches(password, utilisateur.getPassword());
    }

    @Test
    public void testFindByEmail() {
        String email = "test@example.com";
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail(email);

        when(utilisateurDao.findByEmail(email)).thenReturn(utilisateur);

        Utilisateur result = utilisateurSevice.findByEmail(email);

        assertNotNull(result);
        assertEquals(utilisateur, result);
        verify(utilisateurDao, times(1)).findByEmail(email);
    }

    @Test
    public void testFindByName() {
        String name = "John Doe";
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setName(name);

        when(utilisateurDao.findByName(name)).thenReturn(utilisateur);

        Utilisateur result = utilisateurSevice.findByName(name);

        assertNotNull(result);
        assertEquals(utilisateur, result);
        verify(utilisateurDao, times(1)).findByName(name);
    }

    @Test
    public void testFindByAddress() {
        String address = "123 Main St";
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setAddress(address);

        when(utilisateurDao.findByAddress(address)).thenReturn(utilisateur);

        Utilisateur result = utilisateurSevice.findByAddress(address);

        assertNotNull(result);
        assertEquals(utilisateur, result);
        verify(utilisateurDao, times(1)).findByAddress(address);
    }

    @Test
    public void testFindByPhone() {
        String phone = "1234567890";
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setPhone(phone);

        when(utilisateurDao.findByPhone(phone)).thenReturn(utilisateur);

        Utilisateur result = utilisateurSevice.findByPhone(phone);

        assertNotNull(result);
        assertEquals(utilisateur, result);
        verify(utilisateurDao, times(1)).findByPhone(phone);
    }

    @Test
    public void testFindByAge() {
        int age = 30;
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setAge(age);
        List<Utilisateur> utilisateurs = Arrays.asList(utilisateur);

        when(utilisateurDao.findByAge(age)).thenReturn(utilisateurs);

        List<Utilisateur> result = utilisateurSevice.findByAge(age);

        assertNotNull(result);
        assertEquals(utilisateurs, result);
        verify(utilisateurDao, times(1)).findByAge(age);
    }

    @Test
    public void testDeleteByEmail() {
        String email = "test@example.com";
        doNothing().when(utilisateurDao).deleteByEmail(email);

        utilisateurSevice.deleteByEmail(email);

        verify(utilisateurDao, times(1)).deleteByEmail(email);
    }

    @Test
    public void testDeleteByName() {
        String name = "John Doe";
        doNothing().when(utilisateurDao).deleteByName(name);

        utilisateurSevice.deleteByName(name);

        verify(utilisateurDao, times(1)).deleteByName(name);
    }

    @Test
    public void testDeleteByAddress() {
        String address = "123 Main St";
        doNothing().when(utilisateurDao).deleteByAddress(address);

        utilisateurSevice.deleteByAddress(address);

        verify(utilisateurDao, times(1)).deleteByAddress(address);
    }

    @Test
    public void testDeleteByPhone() {
        String phone = "1234567890";
        doNothing().when(utilisateurDao).deleteByPhone(phone);

        utilisateurSevice.deleteByPhone(phone);

        verify(utilisateurDao, times(1)).deleteByPhone(phone);
    }

    @Test
    public void testDeleteByAge() {
        int age = 30;
        doNothing().when(utilisateurDao).deleteByAge(age);

        utilisateurSevice.deleteByAge(age);

        verify(utilisateurDao, times(1)).deleteByAge(age);
    }

    @Test
    public void testFindAll() {
        Utilisateur utilisateur = new Utilisateur();
        List<Utilisateur> utilisateurs = Arrays.asList(utilisateur);

        when(utilisateurDao.findAll()).thenReturn(utilisateurs);

        List<Utilisateur> result = utilisateurSevice.findAll();

        assertNotNull(result);
        assertEquals(utilisateurs, result);
        verify(utilisateurDao, times(1)).findAll();
    }

    @Test
    public void testCount() {
        long count = 10L;

        when(utilisateurDao.count()).thenReturn(count);

        long result = utilisateurSevice.count();

        assertEquals(count, result);
        verify(utilisateurDao, times(1)).count();
    }

    @Test
    public void testDeleteById() {
        Long id = 1L;
        doNothing().when(utilisateurDao).deleteById(id);

        utilisateurSevice.deleteById(id);

        verify(utilisateurDao, times(1)).deleteById(id);
    }

    @Test
    public void testFindAllSorted() {
        Sort sort = Sort.by(Sort.Order.asc("name"));
        Utilisateur utilisateur = new Utilisateur();
        List<Utilisateur> utilisateurs = Arrays.asList(utilisateur);

        when(utilisateurDao.findAll(sort)).thenReturn(utilisateurs);

        List<Utilisateur> result = utilisateurSevice.findAll(sort);

        assertNotNull(result);
        assertEquals(utilisateurs, result);
        verify(utilisateurDao, times(1)).findAll(sort);
    }

    @Test
    public void testFindById() {
        Long id = 1L;
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(id);

        when(utilisateurDao.findById(id)).thenReturn(Optional.of(utilisateur));

        Optional<Utilisateur> result = utilisateurSevice.findById(id);

        assertTrue(result.isPresent());
        assertEquals(utilisateur, result.get());
        verify(utilisateurDao, times(1)).findById(id);
    }

    @Test
    public void testCheckPassword() {
        String rawPassword = "password";
        String encodedPassword = "encodedPassword";

        when(bCryptPasswordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);

        boolean result = utilisateurSevice.checkPassword(rawPassword, encodedPassword);

        assertTrue(result);
        verify(bCryptPasswordEncoder, times(1)).matches(rawPassword, encodedPassword);
    }
}
