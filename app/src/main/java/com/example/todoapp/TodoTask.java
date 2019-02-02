package com.example.todoapp;

public class TodoTask {

    public String toDoTitle;
    public String toDoDescription;
    public String toDoDate;
    public String toDoTime;



//    public TodoTask(String toDoTitle, String toDoDescription, String toDoDate, String toDoTime) {
//        this.toDoTitle = toDoTitle;
//        this.toDoDescription = toDoDescription;
//        this.toDoDate = toDoDate;
//        this.toDoTime = toDoTime;
//    }

    public String getToDoTitle() {
        return toDoTitle;
    }

    public void setToDoTitle(String toDoTitle) {
        this.toDoTitle = toDoTitle;
    }

    public String getToDoDescription() {
        return toDoDescription;
    }

    public void setToDoDescription(String toDoDescription) {
        this.toDoDescription = toDoDescription;
    }

    public String getToDoDate() {
        return toDoDate;
    }

    public void setToDoDate(String toDoDate) {
        this.toDoDate = toDoDate;
    }

    public String getToDoTime() {
        return toDoTime;
    }

    public void setToDoTime(String toDoTime) {
        this.toDoTime = toDoTime;
    }
}
