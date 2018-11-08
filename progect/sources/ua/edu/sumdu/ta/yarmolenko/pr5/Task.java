/*
 * Classname: Task
 *
 * Date: 2018/10/14
 *
 * Author: Dmitrij Yarmolenko
 * E-mail: dsyarmolenko@gmail.com
 *
 */

package ua.edu.sumdu.ta.yarmolenko.pr5;

/**
 * Class Task describes the Task data type
 */
public class Task {

    private String taskTitle;
    private boolean taskActive;
    private boolean taskRepeated;
    private int taskTime;
    private int taskStart; 
    private int taskEnd; 
    private int taskRepeatInterval;

    /**
     * Constructor to create an object a one-time task
     *
     * @param title is a string containing task title
     * @param time is a number defining the task's starting moment
     */
    public Task(String title, int time) {
        setTitle(title);
        setTime(time);
        setActive(false);
    }
    
    /**
     * Constructor for creating an object recurring task
     *
     * @param title is a string containing task title
     * @param start is a number defining the starting moment of the alerting about task
     * @param end is a number defining the ending moment of the alerting about task
     * @param repeat is a number defining the time interval for repeating the alert about the task
     */
    public Task(String title, int start, int end, int repeat) {
        setTitle(title);
        setTime(start, end, repeat);
        setActive(false);		
    }

    /**
     * Method to comparing objects
     *
     * @return boolean value containing the result of comparing objects
     */	
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Task tosk = (Task) obj;
        return (taskTitle != null && taskTitle.equals(tosk.getTitle()))
            && (taskActive == tosk.isActive()) && (taskRepeated == tosk.isRepeated())
            && (taskTime == tosk.getTime()) && (taskStart == tosk.getStartTime())
            && (taskEnd == tosk.getEndTime()) && (taskRepeatInterval == tosk.getRepeatInterval());
    }

    /**
     * Method to generate an integer object code
     *
     * @return an integer object code
     */	
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((taskTitle == null) ? 0 : taskTitle.hashCode());
        result = prime * result + (taskActive ? 1 : 0);
        result = prime * result + (taskRepeated ? 1 : 0);
        result = prime * result + taskTime;
        result = prime * result + taskStart;
        result = prime * result + taskEnd;
        result = prime * result + taskRepeatInterval;
        return result;
    }

    /**
     * Method for obtaining task title
     *
     * @return String value containing task's title
     */	
    public String getTitle() {
        return taskTitle;
    }

    /**
     * Method for setting a task title
     *
     * @param title is a string to assign to task's title
     */	
    public void setTitle(String title) throws RuntimeException {
        if ((title == null) || (title.length() == 0)) {
            throw new RuntimeException();
        }
        taskTitle = title;
    }

    /**
     * Method for obtaining task activity status
     *
     * @return boolean value (false for inactive task, and true otherwise)
     */	
    public boolean isActive() {
        return taskActive;
    }
    
    /**
     * Method of setting task activity status
     *
     * @param active is a boolean switch 
     */	
    public void setActive(boolean active) {
        this.taskActive = active;
    }

    /**
     * Method of getting time to alert about the task
     *
     * @return task's time or task's beginning moment for repetitive task's
     */	
    public int getTime() {
        return (isRepeated() ? taskStart : taskTime);
    }

    /**
     * Methods for setting task notification time for a one-time task
     *
     * @param time is a number defining the task's starting moment
     */	
    public void setTime(int time) throws RuntimeException {
        if (time < 0) {
            throw new RuntimeException();
        }
            this.taskTime = time;
            this.taskStart = 0;
            this.taskEnd = 0;
            this.taskRepeatInterval = 0;
            this.taskRepeated = false;
    }

    /**
     * Method of getting time to alert about the task
     *
     * @return task's time or task's beginning moment for repetitive task's
     */	
    public int getStartTime() {
        return (isRepeated() ? taskStart : taskTime);
    }

    /**
     * Method of getting time to alert about the task
     *
     * @return ending moment for repetitive tasks or task's time for not repetitive tasks
     */	
    public int getEndTime() {
        return (isRepeated() ? taskEnd : taskTime);
    }

    /**
     * Method of obtaining the time interval through which the task alert occurs
     *
     * @return repetition interval or 0 for not repetitive tasks
     */	
    public int getRepeatInterval() {
        return (isRepeated() ? taskRepeatInterval : 0);
    }

    /**
     * Methods for setting task alert time for a recurring task
     *
     * @param start is a number defining the starting moment of the alerting about task
     * @param end is a number defining the ending moment of the alerting about task
     * @param repeat is a number defining the time interval for repeating the alert about the task
     */
    public void setTime(int start, int end, int repeat) throws RuntimeException {
        if (start >= 0) {
            if (end > start) {
                if (repeat > 0) {
                    this.taskTime = 0;
                    this.taskStart = start;
                    this.taskEnd = end;
                    this.taskRepeatInterval = repeat;
                    this.taskRepeated = true;
                } else {
                    throw new RuntimeException();
                }
            } else {
                throw new RuntimeException();
            }
        } else {
            throw new RuntimeException();
        }
    }

    /**
     * Method for obtaining information about the repeatability of the task
     *
     * @return boolean value for repetition status of the task
     */	
    public boolean isRepeated() {
        return taskRepeated;
    }

    /**
     * Method that returns a description of this task.
     *
     * @return string with a description of the task
     */
    public String toString() {
        if (isActive()) {
            if (isRepeated()) {
                return ("Task \"" + getTitle() + "\" from " + getStartTime() 
                    + " to " + getEndTime() + " every " + getRepeatInterval() + " seconds");
            } else {
                return ("Task \"" + getTitle() + "\" at " + getTime());
            }

        } else {
            return ("Task \"" + getTitle() + "\" is inactive");
        }
    }

    /**
     * Method that returns the time of the next alert after the specified time
     *
     * @param time is a number containing a moment of time
     * @return time of the next alert after the specified time or -1 if it can't happen
     */	
    public int nextTimeAfter(int time) throws RuntimeException {
        if (time < 0) {
            throw new RuntimeException();
        }
        if (isActive()) {
            if (isRepeated()) {
                if (time < getEndTime()) {
                    if (time < getStartTime()){
                        return getStartTime();
                    } else {
                        int timeOfNextNotification = getStartTime() + getRepeatInterval();
                        if (timeOfNextNotification > getEndTime()) {
                            return getEndTime();
                        } else {
                            while ((time >= timeOfNextNotification)
                                    && (timeOfNextNotification < getEndTime())) {
                                timeOfNextNotification = timeOfNextNotification + getRepeatInterval();
                            }
                            return (timeOfNextNotification > getEndTime()) ? -1 : timeOfNextNotification;
                        }
                    }
                } else {
                    return -1;
                }
            } else {
                return (time < getTime()) ? getTime() : -1;
            }
        } else {
            return -1;
        }
    }
}