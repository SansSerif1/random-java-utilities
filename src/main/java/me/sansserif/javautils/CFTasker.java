package me.sansserif.javautils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CFTasker {
    private final List<CompletableFuture<Void>> tasks = new ArrayList<>();

    public void addTask(CompletableFuture<Void> task) {
        tasks.add(task);
    }

    public void removeTask(CompletableFuture<Void> task) {
        tasks.remove(task);
    }

    public void waitForTasks() {
        tasks.forEach(CompletableFuture::join);
    }

    public void cancelTasks() {
        tasks.forEach(task -> task.complete(null));
    }
}
