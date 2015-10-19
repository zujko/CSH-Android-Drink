package edu.csh.drink.events;

public class UserDataEvent {
    public boolean isSuccessful;

    public UserDataEvent(boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }
}
