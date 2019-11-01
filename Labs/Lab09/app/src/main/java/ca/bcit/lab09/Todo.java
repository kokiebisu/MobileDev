package ca.bcit.lab09;

public class Todo {
    String todoId;
    String task;
    String who;
    String dueDate;
    Boolean done;

    public Todo() {}

    public Todo(String todoId, String task, String who, String dueDate, Boolean done) {
        this.todoId = todoId;
        this.task = task;
        this.who = who;
        this.dueDate = dueDate;
        this.done = done;
    }

    public String getTodoId() {
        return todoId;
    }

    public String getTask() {
        return task;
    }

    public String getWho() {
        return who;
    }

    public String getDueDate() {
        return dueDate;
    }

    public Boolean getDone() {
        return done;
    }

    public void setTodoId(String todoId) {
        this.todoId = todoId;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }


}
