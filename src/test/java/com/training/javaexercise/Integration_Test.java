package com.training.javaexercise;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.javaexercise.Controller.GreetingController;
import com.training.javaexercise.Model.Content;
import com.training.javaexercise.Model.News;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import javax.servlet.ServletContext;

import java.util.Date;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class Integration_Test {

    @Autowired
    private WebApplicationContext webApplicationContext;

    // Controller couldn't get found if it isn't imported
    // Which is dumb because it is already a bean because
    // of the @Controller annotation, yet it still throws error
    @Autowired
    private GreetingController greetingController;
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
        Assertions.assertNotNull(webApplicationContext.getBean("greetingController"));
    }

    @Test
    public void testLoadJspWelcomePage() throws Exception {
        this.mockMvc.perform(get("/welcome"))
                .andDo(print())
                .andExpect(view().name("welcome"));
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
                contentDummy,
                null);

        this.mockMvc.perform(post("/post/news"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().json(asJsonString(news)))
                .andReturn();
    }

    @Test
    public void testVerifyResponseFromController() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/greetings"))
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
