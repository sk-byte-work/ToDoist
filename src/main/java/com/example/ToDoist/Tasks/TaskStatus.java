package com.example.ToDoist.Tasks;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Optional;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum TaskStatus {
    PENDING(0, "pending"),
    IN_PROGRESS(1, "in_progress"),
    COMPLETED(2, "completed"),
    YET_TO_START(3, "yet_to_start");

    private final int code;
    private final String name;

    TaskStatus(int code, String name)
    {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    @JsonCreator
    public static TaskStatus findByName(String name) throws Exception{
        Optional<TaskStatus> taskStatus = Arrays.stream(values())
                .filter(sts -> sts.getName().equals(name))
                .findFirst();

        if(taskStatus.isPresent()){
            return taskStatus.get();
        }
        throw new Exception("Task Status not available :(");
    }

    @JsonValue
    public String toJson(){
        return this.getName();
    }
}
