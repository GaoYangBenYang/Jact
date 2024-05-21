package com.gaoyang.jact.component;

import com.gaoyang.jact.controller.WorkflowController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.io.File;

@Component
public class JactCommandLine implements CommandLineRunner {

    private static final String FLAG_FILE = System.getProperty("user.home") + "/.jact/installed.flag";

    private final WorkflowController workflowController;

    public JactCommandLine(WorkflowController workflowController) {
        this.workflowController = workflowController;
    }

    @Override
    public void run(String... args) {
        new CommandLine(new JactCommand()).execute(args);
    }

    @CommandLine.Command(name = "jact", mixinStandardHelpOptions = true, version = "jact 1.0", description = "Jact CLI application")
    private class JactCommand implements Runnable {

        @CommandLine.Command(name = "run", description = "Run the application")
        private void run(@CommandLine.Parameters(paramLabel = "<params>", description = "The parameters to run with") String[] params) {
            System.out.println("Running with parameters: " + String.join(", ", params));
        }

        @CommandLine.Command(name = "version", description = "Show the application version")
        private void version() {
            System.out.println("Jact version 1.0");
        }

        @CommandLine.Command(name = "ping", description = "Show the application version")
        private void ping() {
            workflowController.runWorkflow();
        }

        @Override
        public void run() {
            if (!new File(FLAG_FILE).exists()) {
                CommandLine.usage(this, System.out);
            }
        }
    }
}