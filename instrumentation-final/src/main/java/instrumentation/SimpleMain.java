package instrumentation;

import java.lang.instrument.Instrumentation;

public class SimpleMain {
    public static void premain(String agentArguments, Instrumentation instrumentation) {
        try {
            instrumentation.addTransformer(new SimpleTransformer(agentArguments));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
