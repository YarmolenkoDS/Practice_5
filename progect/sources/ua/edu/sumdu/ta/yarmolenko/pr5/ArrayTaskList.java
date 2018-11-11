/*
 * Classname: ArrayTaskList
 *
 * Date: 2018/10/22
 *
 * Author: Dmitrij Yarmolenko
 * E-mail: dsyarmolenko@gmail.com
 *
 */

package ua.edu.sumdu.ta.yarmolenko.pr5;

import ua.edu.sumdu.ta.yarmolenko.pr5.*;

/**
 * Class ArrayTaskList describes the ArrayTaskList data type
 */
public class ArrayTaskList extends AbstractTaskList {

    private static final int RESIZE_ARRAY = 6;

    private Task[] taskList;

    /**
     * Constructor for creating a task list object
     */
    public ArrayTaskList() {
        super();
        this.taskList = new Task[RESIZE_ARRAY];       
    }

    /**
     * Method for adding non-unique tasks
     *
     * @param task is an object of type task added to task list
     */	
    public void add(Task task) throws NullPointerException {
        if (task != null) {   
            if (counterOfTasksInList == taskList.length) {
                int newlength = taskList.length + RESIZE_ARRAY;
                Task[] tempAddList = new Task[newlength];
                for (int i = 0; i < taskList.length; i++) {
                    tempAddList[i] = taskList[i];
                }
                taskList = tempAddList;
            }
            task.setTitle(START_OF_TASK_TITLE + task.getTitle());
            taskList[counterOfTasksInList] = task;
            counterOfTasksInList++;
        } else {
            throw new NullPointerException();
        }
    }

    /**
     * Method to delete all tasks equal input
     *
     * @param task is an object of type task to be deleted in the task list
     */	
    public void remove(Task task) throws NullPointerException {
        if (task != null) {   
            Task[] tempRemoveList = new Task[taskList.length];
            Task[] tempResizeRemoveList;
            int tempIndex = 0;
            int newlength = 0;
            for (int i = 0; i < taskList.length; i++) {
                if ((taskList[i] != null) && !(taskList[i].equals(task))) {
                    tempRemoveList[tempIndex] = taskList[i];
                    tempIndex++;
                }
            }
            counterOfTasksInList = tempIndex;
            if ((taskList.length - counterOfTasksInList) > RESIZE_ARRAY) {
                newlength = taskList.length - ((taskList.length - counterOfTasksInList)
                    / RESIZE_ARRAY) * RESIZE_ARRAY;
                tempResizeRemoveList = new Task[newlength];
                for (int i = 0; i < tempResizeRemoveList.length; i++) {
                    tempResizeRemoveList[i] = tempRemoveList[i];
                }
                taskList = tempResizeRemoveList;
            } else {
                taskList = tempRemoveList;
            }
            taskList = tempRemoveList;
        } else {
            throw new NullPointerException(); 
        }
    }

    /**
     * Method to get the task with the specified number
     *
     * @param index is the task number in the list which should be returned (starting from zero)
     * @return the task whose index in the list is equal to the input
     */	
    public Task getTask(int index) throws IndexOutOfBoundsException {
        if ((index >= size()) || (index < 0) || (size() == 0)) {
            throw new IndexOutOfBoundsException();
        }
        return taskList[index];
    }

    /**
     * The method returns an array of tasks from the list whose notification time 
     *  is between from (exclusively) and to (inclusive)
     *
     * @param from the beginning of the time span
     * @param to the ending of the time span
     * @return an array of tasks from the list whose notification time 
     *  is between from (exclusively) and to (inclusive)
     */	
    public Task[] incoming(int from, int to) {
        Task[] taskListFromTo = new Task[taskList.length];
        int indexFromTo = 0;
        for (int i = 0; i < taskList.length; i++) {
            if ((taskList[i] != null) && (taskList[i].isActive() == true) 
                    && (taskList[i].nextTimeAfter(from) <= to)
                    && (taskList[i].nextTimeAfter(from) != -1)) {
                taskListFromTo[indexFromTo] = taskList[i];
                indexFromTo ++;
            }
        }
        Task[] tempTaskListFromTo = new Task[indexFromTo];
        for (int i = 0; i < indexFromTo; i++) {
            tempTaskListFromTo[i] = taskListFromTo[i];
        }
        taskListFromTo = tempTaskListFromTo;
        return taskListFromTo;
    }
}