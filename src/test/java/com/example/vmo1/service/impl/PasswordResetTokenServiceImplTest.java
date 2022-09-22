package com.example.vmo1.service.impl;

import com.example.vmo1.commons.exceptions.ResourceNotFoundException;
import com.example.vmo1.model.entity.Account;
import com.example.vmo1.model.entity.PasswordResetToken;
import com.example.vmo1.model.entity.Role;
import com.example.vmo1.model.request.PasswordResetRequest;
import com.example.vmo1.repository.PasswordResetTokenRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PasswordResetTokenServiceImplTest {

    @Mock
    private PasswordResetTokenRepository mockPasswordResetTokenRepository;

    @InjectMocks
    private PasswordResetTokenServiceImpl passwordResetTokenServiceImplUnderTest;

    @Test
    public void testGetValidToken() {
        // Setup
        final PasswordResetRequest request = new PasswordResetRequest("email", "password", "confirmPassword",
                "token");
        final Account account = new Account();
        account.setId(0L);
        account.setFullname("fullname");
        account.setUsername("username");
        account.setPassword("password");
        account.setEmail("email");
        account.setPhone("phone");
        account.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account.setIs_deleted(false);
        account.setEnable(false);
        final Role role = new Role();
        role.setId(0L);
        role.setName("name");
        role.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        role.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account.setRoles(new HashSet<>(Arrays.asList(role)));
        final PasswordResetToken expectedResult = new PasswordResetToken(0L, "token",
                LocalDateTime.now().plusMinutes(15), LocalDateTime.now(), false,
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), account);

        // Configure PasswordResetTokenRepository.findByToken(...).
        final Account account1 = new Account();
        account1.setId(0L);
        account1.setFullname("fullname");
        account1.setUsername("username");
        account1.setPassword("password");
        account1.setEmail("email");
        account1.setPhone("phone");
        account1.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account1.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account1.setIs_deleted(false);
        account1.setEnable(false);
        final Role role1 = new Role();
        role1.setId(0L);
        role1.setName("name");
        role1.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        role1.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account1.setRoles(new HashSet<>(Arrays.asList(role1)));
        final Optional<PasswordResetToken> passwordResetToken = Optional.of(
                new PasswordResetToken(0L, "token", LocalDateTime.now().plusMinutes(15),
                        LocalDateTime.now(), false,
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), account1));
        when(mockPasswordResetTokenRepository.findByToken("token")).thenReturn(passwordResetToken);

        // Run the test
        final PasswordResetToken result = passwordResetTokenServiceImplUnderTest.getValidToken(request);
        System.out.println("haha" + result);
        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetValidToken_PasswordResetTokenRepositoryReturnsAbsent() {
        // Setup
        final PasswordResetRequest request = new PasswordResetRequest("email", "password", "confirmPassword",
                "fieldValue");
        when(mockPasswordResetTokenRepository.findByToken("fieldValue")).thenReturn(Optional.empty());

        // Run the test
        assertThrows(ResourceNotFoundException.class,
                () -> passwordResetTokenServiceImplUnderTest.getValidToken(request));
    }

    @Test
    public void testVerifyExpiration() {
        // Setup
        final Account account = new Account();
        account.setId(0L);
        account.setFullname("fullname");
        account.setUsername("username");
        account.setPassword("password");
        account.setEmail("email");
        account.setPhone("phone");
        account.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account.setIs_deleted(false);
        account.setEnable(false);
        final Role role = new Role();
        role.setId(0L);
        role.setName("name");
        role.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        role.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account.setRoles(new HashSet<>(Arrays.asList(role)));
        final PasswordResetToken request = new PasswordResetToken(0L, "token", LocalDateTime.now().plusMinutes(15),
                LocalDateTime.now(), true,
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), account);

        // Run the test
        passwordResetTokenServiceImplUnderTest.verifyExpiration(request);

        // Verify the results
    }

    @Test
    public void testInIsActiveToken() {
        // Setup
        final PasswordResetRequest request = new PasswordResetRequest("email", "password", "confirmPassword",
                "fieldValue");

        // Run the test
        passwordResetTokenServiceImplUnderTest.deleteToken(request);

        // Verify the results
        verify(mockPasswordResetTokenRepository).deleteToken("fieldValue");
    }

    @Test
    public void testMatchEmail() {
        // Setup
        final Account account = new Account();
        account.setId(0L);
        account.setFullname("fullname");
        account.setUsername("username");
        account.setPassword("password");
        account.setEmail("email");
        account.setPhone("phone");
        account.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account.setIs_deleted(false);
        account.setEnable(false);
        final Role role = new Role();
        role.setId(0L);
        role.setName("name");
        role.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        role.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        account.setRoles(new HashSet<>(Arrays.asList(role)));
        final PasswordResetToken token = new PasswordResetToken(0L, "token", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), false,
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), account);

        // Run the test
        passwordResetTokenServiceImplUnderTest.matchEmail(token, "eee");

        // Verify the results
    }
}
