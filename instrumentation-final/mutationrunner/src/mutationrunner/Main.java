package mutationrunner;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    private static String agentJarPath;

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
        File agentJar = new File(args[1]);
        if(!agentJar.exists()) {
            help("Agent Jar "+args[1]+" doesn't exist!");
            System.exit(1);
        }
        agentJarPath = agentJar.getCanonicalPath();
        ArrayList<String> command = new ArrayList<>();
        command.add("java");
        for(int i = 2;i<args.length;i++) {
            command.add(args[i]);
        }
        FileReader fileReader = new FileReader(args[0]);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        String[] lineValues;
        ExecutorService threadPool = Executors.newFixedThreadPool(4);
        int fileIndex = 0;
        Set<String> classExists = new HashSet<>();
        ArrayList<String> noMutationFileList = new ArrayList<>();
        ArrayList<String> mutationFileList = new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null) {
            if(line.isEmpty())
                continue;
            lineValues = line.split(":");
            String classToMutate = lineValues[0];
            String mutation = lineValues[1];
            String testToRun = lineValues[2];
            int finalIndex = fileIndex++;
            if(classExists.add(classToMutate)) {
                threadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            runMutation(command, classToMutate, mutation, testToRun, finalIndex, false);
                            noMutationFileList.add("mutation-"+testToRun+"-"+classToMutate+"-none-" + finalIndex +
                                    ".log");

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        runMutation(command, classToMutate, mutation, testToRun, finalIndex, true);
                        mutationFileList.add("mutation-"+testToRun+"-"+classToMutate+"-"+mutation+"-" + finalIndex +
                                ".log");
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
        fileIndex=0;
        for (String noMutateFile:noMutationFileList) {
            String[] temp = noMutateFile.split("-");
            String classtoTest = temp[2];
            for(String mutateFile:mutationFileList){
                if(mutateFile.contains(classtoTest)){
                    compareFiles(noMutateFile, mutateFile, fileIndex++, classtoTest);

                }
            }
        }

}

    private static void compareFiles(String noMutationOutput, String mutationOutput, int fileIndex, String classtoTest) throws IOException, InterruptedException {

        ArrayList<String> command = new ArrayList<>();
        command.add("diff");
        command.add(noMutationOutput);
        command.add(mutationOutput);
        ProcessBuilder pb = new ProcessBuilder(command);
        File file = new File("Comparing" + "-" + classtoTest + "-" + fileIndex + ".log");
        PrintWriter pw = new PrintWriter(new FileWriter(file));
        pw.println("Comparing Files without and with mutation for Class: "+classtoTest);
        pw.close();
        pb.redirectOutput(ProcessBuilder.Redirect.appendTo(file));
        Process process = pb.start();
        process.waitFor();

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
                    agentJarPath +
                    "=" +
                    classToMutate +
                    ":" +
                    mutation);
        }

        Process process = pb.start();
        process.waitFor();
    }

    private static void help(String s) {
        System.out.println("Help"+s);
    }
}
