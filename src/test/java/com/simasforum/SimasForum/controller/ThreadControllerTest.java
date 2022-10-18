package com.simasforum.SimasForum.controller;

import com.simasforum.SimasForum.model.Reply;
import com.simasforum.SimasForum.model.Role;
import com.simasforum.SimasForum.model.Thread;
import com.simasforum.SimasForum.model.User;
import com.simasforum.SimasForum.service.ReplyService;
import com.simasforum.SimasForum.service.ThreadService;
import com.simasforum.SimasForum.service.UserService;
import com.simasforum.SimasForum.service.VoteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
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
    @MockBean
    private UserService userService;
    @MockBean
    private ReplyService replyService;

    @MockBean
    private VoteService voteService;

    @Test
    @DisplayName("ShowDetailByid")
    void showDetail_byId() throws Exception {
        LocalDate date = LocalDate.of(2020, 1, 8);
        User andre = new User("andre", "andre@gmail.com", "123", new Role("user"));
        andre.setId(1L);
        Thread mockThread = new Thread(andre, "Thread about this", "The content of the thread is", 15, null);
        mockThread.setReply(List.of(new Reply("Lorem opsum", "Lorem ipsum", andre, mockThread)));
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
    void addThread_withSampleData_ok() throws Exception {
        LocalDate date = LocalDate.of(2020, 1, 8);
        User andre = new User("andre", "andre@gmail.com", "123", new Role("user"));
        andre.setId(1L);
        Thread mockThread = new Thread(andre, "ss", "sss", 15, LocalDate.now());
        HashMap<String, Object> sessionAttr = new HashMap<String, Object>();
        sessionAttr.put("USER_LOGIN_ID", 1L);
        mockMvc.perform(post("/thread/add").param("title", "ss").param("content", "sss").sessionAttrs(sessionAttr)).andExpectAll(
                status().is3xxRedirection()
        );
        verify(threadService, times(1)).addThread(any(Thread.class));
    }

    @Test
    void showDashboardByDate() throws Exception {
        LocalDate date = LocalDate.of(2020, 1, 8);
        User andre = new User("andre", "andre@gmail.com", "123", new Role("user"));
        andre.setId(1L);
        List<Thread> mockThread = List.of(new Thread(andre, "Thread about this", "The content of the thread is", 15, LocalDate.now()));

        when(threadService.sortByDate()).thenReturn(mockThread);
        mockMvc.perform(get("/dashboard")).andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith(TEXT_HTML),
                content().encoding(UTF_8),
                view().name("dashboard")
        );
    }

    @Test
    void getEditPage_ok() throws Exception {
        LocalDate date = LocalDate.of(2020, 1, 8);
        User fadhlul = new User("fadhlul", "andre@gmail.com", "123", new Role("user"));
        Thread thread = new Thread(fadhlul, "title", "content" ,0, date);
        when(threadService.getThreadDetail(anyLong())).thenReturn(Optional.of(thread));
        mockMvc.perform(get("/thread/edit/1")).andExpectAll(
                status().isOk(),
                view().name("edit_thread")
        );

    }

    @Test
    void editThread_ok() throws Exception {
        LocalDate date = LocalDate.of(2020, 1, 8);
        User fadhlul = new User("fadhlul", "andre@gmail.com", "123", new Role("user"));
        Thread thread = new Thread(fadhlul, "title", "content" ,0, date);
        when(threadService.getThreadDetail(anyLong())).thenReturn(Optional.of(thread));
        mockMvc.perform(post("/thread/edit/1")
                .param("title", "change title")
                .param("content", "change content")
        ).andExpectAll(
                status().is3xxRedirection()
        );
        assertEquals("change title", thread.getTitle());
    }

    @Test
    void showDashboardByUpvote() throws Exception {
        LocalDate date = LocalDate.of(2020, 1, 8);
        User andre = new User("andre", "andre@gmail.com", "123", new Role("user"));
        andre.setId(1L);
        List<Thread> mockThread = List.of(new Thread(andre, "Thread about this", "The content of the thread is", 15, LocalDate.now()));

        when(threadService.sortByVoteScore()).thenReturn(mockThread);
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

    @Test
    void inputSearchThreadPage_html() throws Exception {
        mockMvc.perform(post("/thread/search").param("title", "ss")).andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith(TEXT_HTML),
                content().encoding(UTF_8),
                content().string(containsString("</html>"))
        );
    }

    @Test
    void showSearchThreadPage_html() throws Exception {
        mockMvc.perform(get("/thread/search").param("title", "ss")).andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith(TEXT_HTML),
                content().encoding(UTF_8),
                content().string(containsString("</html>"))
        );
    }

    @Test
    void showAllMyThreads() throws Exception {
        HashMap<String, Object> sessionAttr = new HashMap<String, Object>();
        sessionAttr.put("USER_LOGIN_ID", 1L);

        mockMvc.perform(get("/mythread").sessionAttrs(sessionAttr)).andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith(TEXT_HTML),
                content().encoding(UTF_8)
        );
    }

    @Test
    void defaultRedirect() throws Exception {
        mockMvc.perform(get("/")).andExpectAll(
                status().is3xxRedirection()
        );
    }

    @Test
    void deleteReply_ok() throws Exception {
        mockMvc.perform(post("/thread/1/reply/delete/1")).andExpectAll(
                status().is3xxRedirection()
        );
        verify(replyService,times(1)).deleteReplyById(anyLong());
    }
}
