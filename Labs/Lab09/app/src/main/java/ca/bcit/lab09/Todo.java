package ca.bcit.lab09;

public class Todo {
    String todoId;
    String task;
    String who;
    String dueDate;
    boolean done;

    public Todo() {}

    public Todo(String todoId, String task, String who, String dueDate, boolean done) {
        this.todoId = todoId;
        this.task = task;
        this.who = who;
        this.dueDate = dueDate;
        this.done = done;
    }

    public String getTodoId() {
        return this.todoId;
    }

    public String getTask() {
        return this.task;
    }

    public String getWho() {
        return this.who;
    }

    public String getDueDate() {
        return this.dueDate;
    }

    public Boolean getDone() {
        return this.done;
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
