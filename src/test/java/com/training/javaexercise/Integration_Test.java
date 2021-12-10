package com.training.javaexercise;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.javaexercise.Controller.TelevisionController;
import com.training.javaexercise.Model.Content;
import com.training.javaexercise.Model.News;
import com.training.javaexercise.Service.NewsService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import javax.servlet.ServletContext;

import java.util.Date;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
//@RunWith(SpringRunner.class)
//@WebMvcTest(controllers = TelevisionController.class)
public class Integration_Test {

    @Autowired
    private WebApplicationContext webApplicationContext;

    // Controller couldn't get found if it isn't imported
    // Which is dumb because it is already a bean because
    // of the @Controller annotation, yet it still throws error
    @Autowired
    private TelevisionController televisionController;
    @Autowired //THIS IS BEAN
    private MockMvc mockMvc;

    @BeforeAll
    static void initializeOnce() {
    }

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void verifyTestConfiguration() {
        ServletContext servletContext = webApplicationContext.getServletContext();
        Assertions.assertNotNull(servletContext);
        Assertions.assertTrue(servletContext instanceof MockServletContext);
        Assertions.assertNotNull(webApplicationContext.getBean("televisionController"));
    }

    @Test
    public void testLoadJspWelcomePage() throws Exception {
        this.mockMvc.perform(get("/api/mvc/welcome"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.view().name("welcome"));
    }

    @Test
    public void testPost() throws Exception {
        Content contentDummy = new Content(
                null,
                "Release AMD",
                "Bla bla bla....",
                null,
                new Date());

        News news = new News(
                null,
                "Berita Hari ini",
                null,
                null);

        mockMvc.perform(post("/api/mvc/crud/news/post")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(news))
            .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.newsTitle").exists())
                .andReturn();
    }

    @Test
    public void testVerifyResponseFromController() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/api/mvc/greetings"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello World!")))
                .andReturn();

        Assertions.assertEquals("text/plain;charset=UTF-8",
                mvcResult.getResponse().getContentType());
    }

    // For converting model to json
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
