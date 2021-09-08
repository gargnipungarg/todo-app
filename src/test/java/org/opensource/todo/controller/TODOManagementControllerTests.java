package org.opensource.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.opensource.todo.model.TodoAddItemRequest;
import org.opensource.todo.model.TodoUpdateDescRequest;
import org.opensource.todo.service.TODOManagementService;
import org.opensource.todo.testConsts.TestConstants;
import org.opensource.todo.testUtils.TestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TODOManagementController.class)
class TODOManagementControllerTests {

    @MockBean
    TODOManagementService todoManagementService;

    @Autowired
    MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void testGetAllItemsOfStatus() throws Exception {
        Mockito.when(todoManagementService.findAllByStatus(Optional.empty()))
                .thenReturn(Arrays.asList(TestUtil.getTestTODOEntity()));

        mockMvc.perform(get("/todoservicev1/todos/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].status", Matchers.is(TestConstants.TEST_NOT_DONE_STATUS)));
    }

    @Test
    void testGetTaskDetails() throws Exception {
        Mockito.when(todoManagementService.findByTaskId(TestConstants.ONE_LONG))
                .thenReturn(TestUtil.getTestTODOEntity());

        mockMvc.perform(get("/todoservicev1/todos").param("id","1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", Matchers.is(TestConstants.TEST_NOT_DONE_STATUS)));
    }

    @Test
    void testAddTodo() throws Exception {

        TodoAddItemRequest testTODOItem = TestUtil.getTestTODOItem();
        Mockito.when(todoManagementService.addTodoItem(testTODOItem))
                .thenReturn(TestConstants.TEST_ITEM_CREATED_MSG);

        mockMvc.perform(post("/todoservicev1/todos/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(testTODOItem)))
                .andExpect(status().isAccepted())
                .andExpect(content().string(TestConstants.TEST_ITEM_CREATED_MSG));
    }

    @Test
    void testUpdateDesc() throws Exception {

        TodoUpdateDescRequest testTODOItem = TestUtil.getTodoUpdateDescRequest();
        Mockito.when(todoManagementService.changeDesc(testTODOItem))
                .thenReturn(TestConstants.TEST_ITEM_UPDATED_MSG);

        mockMvc.perform(post("/todoservicev1/todos/updateDesc")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(testTODOItem)))
                .andExpect(status().isOk())
                .andExpect(content().string(TestConstants.TEST_ITEM_UPDATED_MSG));
    }

    @Test
    void testMarkDone() throws Exception {

        Mockito.when(todoManagementService.changeStatus(TestConstants.ONE_LONG, TestConstants.TEST_DONE_STATUS))
                .thenReturn(TestConstants.TEST_ITEM_UPDATED_MSG);

        mockMvc.perform(post("/todoservicev1/todos/markDone").param("id","1"))
                .andExpect(status().isOk())
                .andExpect(content().string(TestConstants.TEST_ITEM_UPDATED_MSG));
    }

    @Test
    void testMarkNotDone() throws Exception {

        Mockito.when(todoManagementService.changeStatus(TestConstants.ONE_LONG, TestConstants.TEST_NOT_DONE_STATUS))
                .thenReturn(TestConstants.TEST_ITEM_UPDATED_MSG);

        mockMvc.perform(post("/todoservicev1/todos/markNotDone").param("id","1"))
                .andExpect(status().isOk())
                .andExpect(content().string(TestConstants.TEST_ITEM_UPDATED_MSG));
    }
}
