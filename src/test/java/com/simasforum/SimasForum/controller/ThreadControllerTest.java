package com.simasforum.SimasForum.controller;

import com.simasforum.SimasForum.model.Thread;
import com.simasforum.SimasForum.service.ThreadService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ThreadController.class)
public class ThreadControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ThreadService threadService;

    @Test
    void addThread_withSampleData_ok() throws Exception {
        Thread mockThread = new Thread(1l, "Thread about this", "The content of the thread is", 15, 6, null);
        when(threadService.addThread(mockThread)).thenReturn(mockThread);

        mockMvc.perform(post("/thread").param("item_text", "text")).andExpectAll(
                status().is3xxRedirection()
        );
    }
}
