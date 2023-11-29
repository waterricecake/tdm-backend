package tdm.tdmbackend.post.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import tdm.tdmbackend.global.ControllerTest;
import tdm.tdmbackend.global.DtoCreater;
import tdm.tdmbackend.post.dto.request.PostCreateRequest;
import tdm.tdmbackend.post.service.PostService;

class PostControllerTest extends ControllerTest {

    @MockBean
    private PostService postService;

    @Test
    void created() throws Exception {
        // given
        PostCreateRequest request = DtoCreater.create(
                PostCreateRequest.class,
                "testTitle",
                "testContent",
                List.of(1L, 2L, 3L),
                List.of("test.jpeg", "test.jpg")
        );
        when(postService.create(any(), any()))
                .thenReturn(1L);

        // when & then
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(header().string(LOCATION, "/posts/1"));
    }
}
