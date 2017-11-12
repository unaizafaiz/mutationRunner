package mutationrunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        if(args.length == 0 || args[0].equals("-h") || args[0].equals("-?")) {
            help(null);
            System.exit(0);
        }
        File configFile = new File(args[0]);
        if(!configFile.exists()) {
            help("Config File "+args[0]+" doesn't exist!");
            System.exit(1);
        }
        ArrayList<String> command = new ArrayList<>();
        command.add("java");
        for(int i = 1;i<args.length;i++) {
            command.add(args[i]);
        }
        FileReader fileReader = new FileReader(args[0]);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        String[] lineValues;
        ExecutorService threadPool = Executors.newFixedThreadPool(4);
        int fileIndex = 0;
        while ((line = bufferedReader.readLine()) != null) {
            if(line.isEmpty())
                continue;
            lineValues = line.split(":");
            String classToMutate = lineValues[0];
            String mutation = lineValues[1];
            String testToRun = lineValues[2];
            int finalIndex = fileIndex++;
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        runMutation(command, classToMutate, mutation, testToRun, finalIndex, false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        runMutation(command, classToMutate, mutation, testToRun, finalIndex, true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        threadPool.shutdown();
        try {
            // Wait a while for existing tasks to terminate
            if (!threadPool.awaitTermination(120, TimeUnit.SECONDS)) {
                threadPool.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!threadPool.awaitTermination(120, TimeUnit.SECONDS))
                    System.err.println("Pool did not terminate");
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            threadPool.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }

}

    private static void runMutation(ArrayList<String> command, String classToMutate, String mutation, String testToRun, int finalIndex, boolean runMutate) throws IOException, InterruptedException {
        command = (ArrayList<String>) command.clone();
        command.add(testToRun);
        ProcessBuilder pb = new ProcessBuilder(command);
        //pb.inheritIO();
        pb.redirectOutput(new File("mutation-"+testToRun+"-"+classToMutate+"-"+(runMutate?mutation:"none")+"-" + finalIndex +
                ".log"));
        if(runMutate) {
            Map<String, String> env = pb.environment();
            env.put("JAVA_TOOL_OPTIONS", "-javaagent:" +
                    "/Users/manaswikarra/Projects/instrumentation-final/build/libs/myAgent.jar=" +
                    classToMutate +
                    ":" +
                    mutation);
        }
        Process process = pb.start();
        process.waitFor();
    }

    private static void help(String s) {
    }
}
