package com.simasforum.SimasForum.controller;

import com.simasforum.SimasForum.model.Thread;
import com.simasforum.SimasForum.service.ThreadService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ThreadController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ThreadControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ThreadService threadService;

    @Test
    void addThread_withSampleData_ok() throws Exception {
        Thread mockThread = new Thread(1L, "Thread about this", "The content of the thread is", 15, 6, null);
        when(threadService.addThread(mockThread)).thenReturn(mockThread);

        mockMvc.perform(post("/thread").param("item_text", "text")).andExpectAll(
                status().is3xxRedirection()
        );
    }

    /*test*/
    @Test
    @DisplayName("HTTP GET '/thread/add' call add_thread view")
    void showAddThreadPage_ok() throws Exception {
        mockMvc.perform(get("/thread/add")).andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith(TEXT_HTML),
                content().encoding(UTF_8),
                view().name("add_thread")
        );
    }

    @Test
    @DisplayName("HTTP GET '/thread/add' show add_thread.html and other tag")
    void showAddThreadPage_html() throws Exception {
        mockMvc.perform(get("/thread/add")).andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith(TEXT_HTML),
                content().encoding(UTF_8),
                content().string(containsString("</html>")),
                content().string(containsString("<form")),
                content().string(containsString("<input")),
                content().string(containsString("<textarea")),
                content().string(containsString("<button"))
        );
    }
}
