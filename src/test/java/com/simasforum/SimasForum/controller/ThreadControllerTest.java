package com.simasforum.SimasForum.controller;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.simasforum.SimasForum.model.Thread;
import com.simasforum.SimasForum.service.ThreadService;

@WebMvcTest(ThreadController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ThreadControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private ThreadService threadService;

    @Test
    @DisplayName("ShowDetailByid")
    void showDetail_byId() throws Exception{
        Thread mockThread = (new Thread(1L, 0, "Thread about this", "The content of the thread is", 15, 6, null));
        when(threadService.getThreadDetail(anyLong())).thenReturn(Optional.of(mockThread));
        mockMvc.perform(get("/thread/1")).andExpectAll(
                status().isOk(),
                content().string(containsString("Thread about this"))
                
        );
    }
    @Test
    public void whenIdNotFound() throws Exception {
        Long id = 1L;
        when(threadService.getThreadDetail(id))
                .thenReturn(Optional.empty());
//        mockMvc.perform(get("/thread/1"))
//                .andExpectAll(status().isNotFound());
//                .andExpect(status().isNotFound()).andDo(print());
//                .andExpectAll(status().isNotFound(),content().string(containsString("")));
    }
    
    @Test
    @Disabled
    void addThread_withSampleData_ok() throws Exception {
    	Thread mockThread = new Thread(1L, 1, "ss", "sss", 15, 6, LocalDate.now());
        when(threadService.addThread(any(Thread.class))).thenReturn(mockThread);
        
        mockMvc.perform(post("/thread/add").param("title", "ss").param("content", "sss")).andExpectAll(
                status().isOk()
//                content().string(containsString("Thread about this"))
        );
    }
    @Test
    void showDashboardByDate() throws Exception {
    	List<Thread> mockThread = List.of(new Thread(1L, 1, "Thread about this", "The content of the thread is", 15, 6, LocalDate.now()));

    	when(threadService.sortByDate()).thenReturn(mockThread);
        mockMvc.perform(get("/dashboard")).andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith(TEXT_HTML),
                content().encoding(UTF_8),
                view().name("dashboard")
        );
    }
    
    @Test
    void showDashboardByUpvote() throws Exception {
    	List<Thread> mockThread = List.of(new Thread(1L, 1, "Thread about this", "The content of the thread is", 15, 6, LocalDate.now()));

    	when(threadService.sortByUpVote()).thenReturn(mockThread);
        mockMvc.perform(get("/dashboard")).andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith(TEXT_HTML),
                content().encoding(UTF_8),
                view().name("dashboard")
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
