package me.sansserif.javautils;

import java.util.*;

public class CLI implements Runnable {
    private final List<Module> modules = new ArrayList<>();
    private final Console console;
    private final Thread thread;
    private boolean run = true;
    public void register(Module module) throws Exception {
        if (modules.stream().anyMatch(cmd -> module.getCommandName().equals(cmd.getCommandName())))
            throw new Exception("Duplicate command.");
        modules.add(module);
    }

    public CLI(String name) {
        console = new Console(name + "CLI");
        thread = new Thread(this);
        thread.start();
        Runtime.getRuntime().addShutdownHook(new Thread(this::terminate));

        try {
            this.register(new Module() {
                @Override
                public String getCommandName() {
                    return "help";
                }
                @Override
                public String getCommandDescription() {
                    return "list commands and their descriptions";
                }
                @Override
                public Boolean execute(Args command) {
                    modules.forEach(cmd -> console.log("> " + cmd.getCommandName() + " => " + cmd.getCommandDescription()));
                    return true;
                }
            });
        } catch (Exception ignored) {}
        try {
            this.register(new Module() {
                @Override
                public String getCommandName() {
                    return "start";
                }
                @Override
                public String getCommandDescription() {
                    return "Start modules";
                }
                @Override
                public Boolean execute(Args command) {
                    if (command.length() <= 1) {
                        console.warning("Supply module name.");
                        return false;
                    }
                    List<String> modules = Arrays.stream(command.getAsString().substring(6).split("\\s+")).toList();
                    CLI.this.modules.stream().filter(cmd -> modules.contains(cmd.getCommandName())).forEach(Module::start);
                    return true;
                }
            });
        } catch (Exception ignored) {}
        try {
            this.register(new Module() {
                @Override
                public String getCommandName() {
                    return "stop";
                }
                @Override
                public String getCommandDescription() {
                    return "Stop modules";
                }
                @Override
                public Boolean execute(Args command) {
                    if (command.length() <= 1) {
                        console.warning("Supply module name.");
                        return false;
                    }
                    List<String> modules = Arrays.stream(command.getAsString().substring(6).split("\\s+")).toList();
                    CLI.this.modules.stream().filter(cmd -> modules.contains(cmd.getCommandName())).forEach(Module::stop);
                    return true;
                }
            });
        } catch (Exception ignored) {}
    }
    public void terminate() {
        run = false;
        thread.interrupt();
    }

    @Override
    public void run() {
        Scanner input = new Scanner(System.in);
        console.started();
        while (run) {
            Args args = new Args(input.nextLine().split("\\s+"));
            if (args.getAsString().isEmpty()) {
                console.warning("Empty command.");
                continue;
            }
            Optional<Module> command = modules.stream().filter(cmd -> cmd.getCommandName().equals(args.getString(0))).findAny();
            if (command.isEmpty()) {
                console.warning("Invalid command.");
                continue;
            }
            try {
                Boolean result = command.get().execute(args);
                if (result == null) {
                    console.warning("This module does not support commands.");
                    continue;
                }
                if (result)
                    console.success("Command finished.");
                else
                    console.warning("Command finished.");
            } catch (Exception err) {
                console.warning("Command threw an exception: " + err.getMessage());
            }
        }
    }

    public interface Module {
        String getCommandName();
        String getCommandDescription();
        default void start() {}
        default void stop() {}
        default Boolean execute(Args command) {
            return null;
        }
    }
}
