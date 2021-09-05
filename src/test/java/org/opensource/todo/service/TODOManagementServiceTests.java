package org.opensource.todo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opensource.todo.constants.TaskStatuses;
import org.opensource.todo.exception.*;
import org.opensource.todo.model.TODOEntity;
import org.opensource.todo.model.TodoTask;
import org.opensource.todo.respository.TODOManagementRepository;
import org.opensource.todo.testConsts.TestConstants;
import org.opensource.todo.testUtils.TestUtil;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class TODOManagementServiceTests {

    @Mock
    private TODOManagementRepository mockRepository;

    @InjectMocks
    private TODOManagementService serviceUnderTest;

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
        TodoTask testTODOItem = TestUtil.getTestTODOItem();
        final String testResponse = serviceUnderTest.addTodoItem(testTODOItem);
        Mockito.verify(mockRepository, Mockito.times(TestConstants.ONE_INT)).save(any());
        assertEquals(testResponse, TestConstants.TEST_ITEM_CREATED_MSG);
    }

    @Test
    void testInvalidStatusForAddTodoItem() {
        TodoTask testTODOItem = TestUtil.getTestTODOItem();
        testTODOItem.setStatus(TestConstants.INVALID_STATUS);
        assertThrows(InvalidTODOTaskStatusException.class, () -> serviceUnderTest.addTodoItem(testTODOItem));
        Mockito.verify(mockRepository, Mockito.times(TestConstants.ZERO_INT)).save(any());
    }

    @Test
    void testMappingExceptionForAddTodoItem(){
        TodoTask testTODOItem = TestUtil.getTestTODOItem();
        Mockito.when(mockRepository.save(any())).thenThrow(new RuntimeException(TestConstants.MAPPING_EXCEPTION));
        assertThrows(TODOTaskMappingException.class, () -> serviceUnderTest.addTodoItem(testTODOItem));
        Mockito.verify(mockRepository, Mockito.times(TestConstants.ONE_INT)).save(any());
    }

    @Test
    void testChangeDesc() throws Exception {
        TodoTask testTODOItem = TestUtil.getTestTODOItem();
        testTODOItem.setDescription(TestConstants.TEST_NEW_DESC);
        Mockito.when(mockRepository.getById(any())).thenReturn(TestUtil.getTestTODOEntity());
        assertEquals(serviceUnderTest.changeDesc(testTODOItem), TestConstants.TEST_ITEM_UPDATED_MSG);
        Mockito.verify(mockRepository, Mockito.times(TestConstants.ONE_INT)).getById(anyLong());
        Mockito.verify(mockRepository, Mockito.times(TestConstants.ONE_INT)).save(any());
    }

    @Test
    void testChangeDescPastItem() throws Exception {
        TodoTask testTODOItem = TestUtil.getTestTODOPastItem();
        testTODOItem.setDescription(TestConstants.TEST_NEW_DESC);
        Mockito.when(mockRepository.getById(any())).thenReturn(TestUtil.getTestPastTODOEntity());
        assertThrows(TODOPastDueException.class, () -> serviceUnderTest.changeDesc(testTODOItem));
    }

    @Test
    void testChangeDescSameDesc() {
        TodoTask testTODOItem = TestUtil.getTestTODOItem();
        Mockito.when(mockRepository.getById(any())).thenReturn(TestUtil.getTestTODOEntity());
        testTODOItem.setStatus(TestConstants.TEST_NEW_DESC);
        assertThrows(TODODescriptionInvalidException.class, () -> serviceUnderTest.changeDesc(testTODOItem));
        Mockito.verify(mockRepository, Mockito.times(TestConstants.ONE_INT)).getById(anyLong());
        Mockito.verify(mockRepository, Mockito.times(TestConstants.ZERO_INT)).save(any());
    }

    @Test
    void testChangeDescIdInvalid(){
        TodoTask testTODOItem = new TodoTask();
        testTODOItem.setDescription(TestConstants.TEST_NEW_DESC);
        assertThrows(InvalidTODORequestException.class, () -> serviceUnderTest.changeDesc(testTODOItem));
    }

    @Test
    void testChangeDescIdInvalidZero() {
        TodoTask testTODOItem = new TodoTask();
        testTODOItem.setId(String.valueOf(TestConstants.ZERO_INT));
        assertThrows(InvalidTODORequestException.class, () -> serviceUnderTest.changeDesc(testTODOItem));
    }

    @Test
    void testChangeStatus() throws Exception {
        TodoTask testTODOItem = TestUtil.getTestTODOItem();
        testTODOItem.setStatus(TaskStatuses.NOT_DONE.getTaskStatus());
        Mockito.when(mockRepository.getById(any())).thenReturn(TestUtil.getTestTODOEntity());
        assertEquals(serviceUnderTest.changeStatus(testTODOItem), TestConstants.TEST_ITEM_UPDATED_MSG);
        Mockito.verify(mockRepository, Mockito.times(TestConstants.ONE_INT)).getById(anyLong());
    }

    @Test
    void testChangeStatusInvalidId() {
        TodoTask testTODOItem = new TodoTask();
        testTODOItem.setStatus(TaskStatuses.NOT_DONE.getTaskStatus());
        assertThrows(InvalidTODORequestException.class, () -> serviceUnderTest.changeStatus(testTODOItem));
    }

    @Test
    void testChangeStatusInvalidIdVal() {
        TodoTask testTODOItem = TestUtil.getTestTODOItem();
        testTODOItem.setStatus(TestConstants.INVALID_STATUS);
        Mockito.when(mockRepository.getById(any())).thenReturn(TestUtil.getTestTODOEntity());
        assertThrows(InvalidTODOTaskStatusException.class, () -> serviceUnderTest.changeStatus(testTODOItem));
        Mockito.verify(mockRepository, Mockito.times(TestConstants.ONE_INT)).getById(anyLong());
    }

    @Test
    void testChangeStatusOfDueItem() {
        TodoTask testTODOItem = TestUtil.getTestTODOItem();
        TODOEntity testTODOEntity = TestUtil.getTestTODOEntity();
        testTODOEntity.setStatus(TaskStatuses.PAST_DUE.getTaskStatus());
        Mockito.when(mockRepository.getById(any())).thenReturn(testTODOEntity);
        assertThrows(TODOPastDueException.class, () -> serviceUnderTest.changeStatus(testTODOItem));
        Mockito.verify(mockRepository, Mockito.times(TestConstants.ONE_INT)).getById(anyLong());
    }

    @Test
    void testChangeStatusOfIdZero() {
        TodoTask testTODOItem = TestUtil.getTestTODOItem();
        testTODOItem.setId(String.valueOf(TestConstants.ZERO_INT));
        assertThrows(InvalidTODORequestException.class, () -> serviceUnderTest.changeStatus(testTODOItem));
        Mockito.verify(mockRepository, Mockito.times(TestConstants.ZERO_INT)).getById(anyLong());
    }

    @Test
    void testFindAllByStatus() throws InvalidTODOTaskStatusException {
        TODOEntity item = TestUtil.getTestTODOEntity();
        Mockito.when(mockRepository.findAll()).thenReturn(Collections.singletonList(item));
        final List<TODOEntity> allByStatus = serviceUnderTest.findAllByStatus(TestConstants.TEST_STATUS);
        assertEquals(1, allByStatus.size());
        Mockito.verify(mockRepository, Mockito.times(TestConstants.ZERO_INT)).getById(anyLong());
    }

    @Test
    void testFindAllByStatusException() {
        TODOEntity item = TestUtil.getTestTODOEntity();
        Mockito.when(mockRepository.findAll()).thenReturn(Collections.singletonList(item));
        assertThrows(InvalidTODOTaskStatusException.class, () -> serviceUnderTest.findAllByStatus(TestConstants.INVALID_STATUS));
    }

}
