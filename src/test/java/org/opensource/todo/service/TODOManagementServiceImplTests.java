package org.opensource.todo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opensource.todo.exception.InvalidTODORequestException;
import org.opensource.todo.exception.InvalidTODOTaskStatusException;
import org.opensource.todo.exception.NoTODOTaskFoundException;
import org.opensource.todo.exception.TODODescriptionInvalidException;
import org.opensource.todo.exception.TODOPastDueException;
import org.opensource.todo.exception.TODOTaskMappingException;
import org.opensource.todo.model.TODOEntity;
import org.opensource.todo.model.TodoAddItemRequest;
import org.opensource.todo.model.TodoUpdateDescRequest;
import org.opensource.todo.respository.TODOManagementRepository;
import org.opensource.todo.testConsts.TestConstants;
import org.opensource.todo.testUtils.TestUtil;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class TODOManagementServiceImplTests {

    @Mock
    private TODOManagementRepository mockRepository;

    @InjectMocks
    private TODOManagementServiceImpl serviceUnderTest;

    @Test
    void testFindByTaskId() throws Exception{
        Mockito.when(mockRepository.findById(anyLong())).thenReturn(Optional.of(TestUtil.getTestTODOEntity()));
        TODOEntity testResponse = serviceUnderTest.findByTaskId(anyLong());
        Mockito.verify(mockRepository, Mockito.times(TestConstants.ONE_INT)).findById(anyLong());
        assertEquals(TestConstants.ONE_INT, testResponse.getId());
    }

    @Test
    void testFindByTaskIdForEmptyTODO(){
        Mockito.when(mockRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NoTODOTaskFoundException.class, () -> serviceUnderTest.findByTaskId(anyLong()));
        Mockito.verify(mockRepository, Mockito.times(TestConstants.ONE_INT)).findById(anyLong());
    }

    @Test
    void testAddTodoItem() throws Exception{
        TodoAddItemRequest testTODOItem = TestUtil.getTestTODOItem();
        serviceUnderTest.addTodoItem(testTODOItem);
        Mockito.verify(mockRepository, Mockito.times(TestConstants.ONE_INT)).save(any());
    }

    @Test
    void testInvalidDateForAddTodoItem() {
        TodoAddItemRequest testTODOItem = TestUtil.getPastTestTODOItem();
        assertThrows(TODOPastDueException.class, () -> serviceUnderTest.addTodoItem(testTODOItem));
        Mockito.verify(mockRepository, Mockito.times(TestConstants.ZERO_INT)).save(any());
    }

    @Test
    void testMappingExceptionForAddTodoItem(){
        TodoAddItemRequest testTODOItem = TestUtil.getTestTODOItem();
        Mockito.when(mockRepository.save(any())).thenThrow(new RuntimeException(TestConstants.MAPPING_EXCEPTION));
        assertThrows(TODOTaskMappingException.class, () -> serviceUnderTest.addTodoItem(testTODOItem));
        Mockito.verify(mockRepository, Mockito.times(TestConstants.ONE_INT)).save(any());
    }

    @Test
    void testChangeDesc() throws Exception {
        TodoUpdateDescRequest request = TestUtil.getTodoUpdateDescRequest();
        Mockito.when(mockRepository.getById(any())).thenReturn(TestUtil.getTestTODOEntity());
        assertEquals(serviceUnderTest.changeDesc(request), TestConstants.TEST_ITEM_UPDATED_MSG);
        Mockito.verify(mockRepository, Mockito.times(TestConstants.ONE_INT)).getById(anyLong());
        Mockito.verify(mockRepository, Mockito.times(TestConstants.ONE_INT)).save(any());
    }

    @Test
    void testChangeDescEmpty() {
        TodoUpdateDescRequest request = TestUtil.getTodoUpdateDescRequest();
        request.setDescription("");
        Mockito.when(mockRepository.getById(any())).thenReturn(TestUtil.getTestTODOEntity());
        assertThrows(TODODescriptionInvalidException.class, () -> serviceUnderTest.changeDesc(request));
    }

    @Test
    void testChangeDescPastItem() {
        TodoUpdateDescRequest request = TestUtil.getTodoUpdateDescRequest();
        Mockito.when(mockRepository.getById(any())).thenReturn(TestUtil.getPastTestTODOEntity());
        assertThrows(TODOPastDueException.class, () -> serviceUnderTest.changeDesc(request));
    }

    @Test
    void testChangeDescIdInvalid(){
        TodoUpdateDescRequest request = TestUtil.getTodoUpdateDescRequest();
        request.setDescription(TestConstants.TEST_NEW_DESC);
        Mockito.when(mockRepository.getById(any())).thenThrow(javax.persistence.EntityNotFoundException.class);
        assertThrows(InvalidTODORequestException.class, () -> serviceUnderTest.changeDesc(request));
    }

    @Test
    void testChangeNotDoneStatus() throws Exception {
        Mockito.when(mockRepository.getById(any())).thenReturn(TestUtil.getTestTODOEntity());
        assertEquals(serviceUnderTest.changeStatus(TestConstants.ONE_LONG, TestConstants.TEST_NOT_DONE_STATUS), TestConstants.TEST_ITEM_UPDATED_MSG);
        Mockito.verify(mockRepository, Mockito.times(TestConstants.ONE_INT)).getById(anyLong());
    }

    @Test
    void testChangeDoneStatus() throws Exception {
        Mockito.when(mockRepository.getById(any())).thenReturn(TestUtil.getTestTODOEntity());
        assertEquals(serviceUnderTest.changeStatus(TestConstants.ONE_LONG, TestConstants.TEST_DONE_STATUS), TestConstants.TEST_ITEM_UPDATED_MSG);
        Mockito.verify(mockRepository, Mockito.times(TestConstants.ONE_INT)).getById(anyLong());
    }

    @Test
    void testChangeStatusInvalidId() {
        assertThrows(InvalidTODORequestException.class, () -> serviceUnderTest.changeStatus(0l, TestConstants.TEST_NOT_DONE_STATUS));
    }

    @Test
    void testChangeStatusOfDueItem() {
        TODOEntity testTODOEntity = TestUtil.getPastTestTODOEntity();
        Mockito.when(mockRepository.getById(any())).thenReturn(testTODOEntity);
        assertThrows(TODOPastDueException.class, () -> serviceUnderTest.changeStatus(TestConstants.ONE_LONG, TestConstants.TEST_NOT_DONE_STATUS));
        Mockito.verify(mockRepository, Mockito.times(TestConstants.ONE_INT)).getById(anyLong());
    }

    @Test
    void testFindAllByStatus() throws InvalidTODOTaskStatusException {
        TODOEntity item = TestUtil.getTestTODOEntity();
        Mockito.when(mockRepository.findAll()).thenReturn(Collections.singletonList(item));
        final List<TODOEntity> allByStatus = serviceUnderTest.findAllByStatus(Optional.of(Boolean.TRUE));
        assertEquals(1, allByStatus.size());
        Mockito.verify(mockRepository, Mockito.times(TestConstants.ZERO_INT)).getById(anyLong());
    }

}
