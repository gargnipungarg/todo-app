package org.opensource.todo.testUtils;

import org.apache.commons.lang3.time.DateUtils;
import org.opensource.todo.mappers.TodoAddItemMapper;
import org.opensource.todo.model.TODOEntity;
import org.opensource.todo.model.TodoAddItemRequest;
import org.opensource.todo.model.TodoUpdateDescRequest;
import org.opensource.todo.testConsts.TestConstants;

import java.util.Date;

public class TestUtil {

    public static TodoAddItemRequest getTestTODOItem() {
        return TodoAddItemRequest.builder()
                .dueDate(DateUtils.addDays(new Date(),1))
                .description(TestConstants.TEST_DESC)
                .build();
    }

    public static TodoAddItemRequest getPastTestTODOItem() {
        return TodoAddItemRequest.builder()
                .dueDate(DateUtils.addDays(new Date(),-1))
                .description(TestConstants.TEST_DESC)
                .build();
    }

    public static TODOEntity getTestTODOEntity() {
        TodoAddItemRequest task = getTestTODOItem();
        TODOEntity todoEntity = TodoAddItemMapper.INSTANCE.sourceToDestination(task);
        todoEntity.setStatus(TestConstants.TEST_NOT_DONE_STATUS);
        todoEntity.setId(TestConstants.ONE_LONG);
        todoEntity.setCreationDate(DateUtils.addDays(task.getDueDate(), -1));
        return todoEntity;
    }

    public static TODOEntity getPastTestTODOEntity() {
        TodoAddItemRequest task = getPastTestTODOItem();
        TODOEntity todoEntity = TodoAddItemMapper.INSTANCE.sourceToDestination(task);
        todoEntity.setStatus(TestConstants.TEST_DUE_STATUS);
        todoEntity.setId(TestConstants.ONE_LONG);
        todoEntity.setCreationDate(DateUtils.addDays(task.getDueDate(), -1));
        return todoEntity;
    }

    public static TodoUpdateDescRequest getTodoUpdateDescRequest() {
        return TodoUpdateDescRequest.builder()
                .id(TestConstants.ONE_LONG)
                .description(TestConstants.TEST_DESC)
                .build();
    }

}
