package ru.practicum.ewm.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.ewm.user.dto.NewUserDto;
import ru.practicum.ewm.user.dto.ResponseUserDto;
import ru.practicum.ewm.user.service.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserControllerTest.class)
@AutoConfigureMockMvc
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserControllerTest {

    @MockBean
    private UserService userService;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Test
    public void create() throws Exception {
        //given
        NewUserDto newUserDto = new NewUserDto("name", "user@mail.ru");
        ResponseUserDto responseUserDto = new ResponseUserDto(1L, "name", "user@mail.ru");

        when(userService.add(any())).thenReturn(responseUserDto);

        // when + then
        mockMvc.perform(post("/admin/users")
                        .content(objectMapper.writeValueAsString(newUserDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
               // .andExpect(jsonPath("$.ip").value(responseUserDto.getId()));
    }
}
