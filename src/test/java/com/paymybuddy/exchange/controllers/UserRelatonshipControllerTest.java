package com.paymybuddy.exchange.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.exchange.models.UserRelationship;
import com.paymybuddy.exchange.services.UserRelationshipService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserRelationshipController.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserRelatonshipControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserRelationshipService userRelationshipService;

    @Test
    public void testCreateRelationship() throws Exception {
        String TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
        HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
        //GIVEN
        UserRelationship userRelationship;

        ObjectMapper mapper = new ObjectMapper();
        String relationshipJSON = "{\n" +
                "        \"idUserRelated\": \"13\",\n" +
                "        \"idUserRelating\": \"2\",\n" +
                "        \"timestampOfCreation\": \"123456789\"\n"+
                "    }";
        userRelationship = mapper.readValue(relationshipJSON,UserRelationship.class);

        //WHEN
        when(userRelationshipService.create(any(UserRelationship.class))).thenReturn(true);
        when(userRelationshipService.read(any(Integer.class))).thenReturn(userRelationship);
        when(userRelationshipService.verifyRelationship(any(Integer.class),any(Integer.class))).thenReturn(null).thenReturn(userRelationship);

        //THEN
        mockMvc.perform(post("/relationships")
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(relationshipJSON))
                .andExpect(status().is(201));
    }

    @Test
    public void testGetUserRelationshipById() throws Exception {
        //GIVEN
        UserRelationship userRelationship = new UserRelationship(2,7);
        userRelationship.setId(2);


        //WHEN
        when(userRelationshipService.read(2)).thenReturn(userRelationship);

        //THEN
        mockMvc.perform(get("/relationships/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userRelationship.getId())))
                .andExpect(jsonPath("$.idUserRelated", is(userRelationship.getIdUserRelated())))
                .andExpect(jsonPath("$.idUserRelating", is(userRelationship.getIdUserRelating())));
    }

    @Test
    public void testUpdateRelationships() throws Exception {
        String TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
        HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
        //GIVEN
        UserRelationship userRelationship = new UserRelationship(2,7);
        userRelationship.setId(2);


        ObjectMapper mapper = new ObjectMapper();
        String relationshipJSON = "{\n" +
                "        \"idUserRelated\": \"13\",\n" +
                "        \"idUserRelating\": \"2\",\n" +
                "        \"timestampOfCreation\": \"123456789\"\n"+
                "    }";

        //WHEN
        when(userRelationshipService.read(2)).thenReturn(userRelationship);
        when(userRelationshipService.update(userRelationship)).thenReturn(true);

        //THEN
        mockMvc.perform(put("/relationships/2")
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(relationshipJSON))
                .andExpect(status().is(200));

        verify(userRelationshipService,times(1)).read(userRelationship.getId());
        verify(userRelationshipService,times(1)).update(userRelationship);
    }

    @Test
    public void testDeleteRelationships() throws Exception {
        String TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
        HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
        //GIVEN
        UserRelationship userRelationship = new UserRelationship(2,7);
        userRelationship.setId(2);

        //WHEN
        when(userRelationshipService.delete(2)).thenReturn(true);

        //THEN
        mockMvc.perform(delete("/relationships/2")
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));

        verify(userRelationshipService,times(1)).delete(userRelationship.getId());
    }

    @Test
    public void testGetAllRelationships() throws Exception {
        //GIVEN
        UserRelationship userRelationship = new UserRelationship(2,7);
        userRelationship.setId(2);
        UserRelationship userRelationship1 = new UserRelationship(9,47);
        userRelationship.setId(1);

        //WHEN
        when(userRelationshipService.listAll()).thenReturn(Arrays.asList(userRelationship,userRelationship1));

        //THEN
        mockMvc.perform(get("/relationships")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(userRelationship.getId())))
                .andExpect(jsonPath("$[0].idUserRelated", is(userRelationship.getIdUserRelated())))
                .andExpect(jsonPath("$[0].idUserRelating", is(userRelationship.getIdUserRelating())))
                .andExpect(jsonPath("$[1].id", is(userRelationship1.getId())))
                .andExpect(jsonPath("$[1].idUserRelated", is(userRelationship1.getIdUserRelated())))
                .andExpect(jsonPath("$[1].idUserRelating", is(userRelationship1.getIdUserRelating())));
    }

}
