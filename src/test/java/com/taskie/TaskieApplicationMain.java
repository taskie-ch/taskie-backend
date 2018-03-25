package com.taskie;

/**
 * Local run for development
 */
public class TaskieApplicationMain {

    /**
     * Start taskie app server using config.yml
     */
    public static void main(final String[] args) throws Exception {
        new TaskieApplication().run("server", "config.yml");
    }
}
