package com.paymybuddy.exchange.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.exchange.models.Description;
import com.paymybuddy.exchange.services.DescriptionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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


@WebMvcTest(DescriptionController.class)
@RunWith(SpringRunner.class)
public class DescriptionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DescriptionService descriptionService;

    @Test
    public void testCreateDescription() throws Exception {
        //GIVEN
        Description description;

        ObjectMapper mapper = new ObjectMapper();
        String bankAccountJSON = "{\n" +
                "        \"content\": \"mon contenu\"\n" +
                "    }";
        description = mapper.readValue(bankAccountJSON,Description.class);

        //WHEN
        when(descriptionService.create(any(Description.class))).thenReturn(true);
        when(descriptionService.read(any(Integer.class))).thenReturn(description);

        //THEN
        mockMvc.perform(post("/descriptions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bankAccountJSON))
                .andExpect(status().is(201));
    }

    @Test
    public void testGetDescriptionsById() throws Exception {
        //GIVEN
        Description description = new Description("mon contenu");
        description.setId(2);

        //WHEN
        when(descriptionService.read(2)).thenReturn(description);

        //THEN
        mockMvc.perform(get("/descriptions/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(description.getId())))
                .andExpect(jsonPath("$.content", is(description.getContent())));
    }

    @Test
    public void testUpdateDescription() throws Exception {
        //GIVEN
        Description description = new Description("mon contenu");
        description.setId(2);

        String userJSON = "{\n" +
                "        \"content\": \"mon autre contenu \"\n" +
                "    }";

        //WHEN
        when(descriptionService.read(2)).thenReturn(description);
        when(descriptionService.update(description)).thenReturn(true);

        //THEN
        mockMvc.perform(put("/descriptions/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJSON))
                .andExpect(status().is(200));

        verify(descriptionService,times(1)).read(description.getId());
        verify(descriptionService,times(1)).update(description);
    }

    @Test
    public void testDeleteDescription() throws Exception {
        //GIVEN
        Description description = new Description("mon contenu");
        description.setId(2);

        //WHEN
        when(descriptionService.delete(2)).thenReturn(true);
        //THEN
        mockMvc.perform(delete("/descriptions/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));

        verify(descriptionService,times(1)).delete(description.getId());
    }

    @Test
    public void testGetAllDescriptions() throws Exception {
        //GIVEN
        Description description = new Description("le contenu du 0");
        description.setId(2);
        Description description1 = new Description("le contenu du 1");
        description1.setId(3);

        //WHEN
        when(descriptionService.listAll()).thenReturn(Arrays.asList(description,description1));

        //THEN
        mockMvc.perform(get("/descriptions")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].content", is(description.getContent())))
                .andExpect(jsonPath("$[1].content", is(description1.getContent())));
    }
}
