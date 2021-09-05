package org.opensource.todo.testUtils;

import org.opensource.todo.mappers.TodoMapper;
import org.opensource.todo.model.TODOEntity;
import org.opensource.todo.model.TodoTask;
import org.opensource.todo.testConsts.TestConstants;

import java.util.Date;

public class TestUtil {

    public static TodoTask getTestTODOItem() {
        return TodoTask.builder()
                .id(String.valueOf(TestConstants.ONE_INT))
                .creationDate(new Date())
                .dueDate(new Date())
                .completionDate(new Date())
                .description(TestConstants.TEST_DESC)
                .status(TestConstants.TEST_STATUS)
                .build();
    }

    public static TODOEntity getTestTODOEntity() {
        TodoTask task = getTestTODOItem();
        return TodoMapper.INSTANCE.sourceToDestination(task);
    }
}
