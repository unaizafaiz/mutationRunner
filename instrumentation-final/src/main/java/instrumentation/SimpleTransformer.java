package instrumentation;

import javassist.*;
import mutations.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class SimpleTransformer implements ClassFileTransformer {

    private String mutationClass = "";
    private String mutation = "";

    public SimpleTransformer(String agentArguments) {
        super();
        String[] agentArgsSplit = agentArguments.split(":");
        if(agentArgsSplit.length == 2) {
            mutationClass = agentArgsSplit[0];
            mutation = agentArgsSplit[1];
        }
    }

    public byte[] transform(ClassLoader loader, String className, Class redefiningClass, ProtectionDomain domain, byte[] bytes) throws IllegalClassFormatException {
        if (!className.contains(mutationClass)) {
            return bytes;
        }
        return transformClass(className,bytes);
    }

    private byte[] transformClass(String className, byte[] b) {
        try {
            ClassPool pool = ClassPool.getDefault();
            CtClass cl = null;
            try {
                cl = pool.makeClass(new java.io.ByteArrayInputStream(b));
                if (mutation.equals("ModifyPublicAccessMutation")) {
                    ModifyPublicAccessMutation mutation = new ModifyPublicAccessMutation(cl);
                    mutation.mutate();
                } else if (mutation.equals("ModifyProtectedAccessMutation")) {
                    ModifyProtectedAccessMutation mutation = new ModifyProtectedAccessMutation(cl);
                    mutation.mutate();
                    return  cl.getSuperclass().toBytecode();
                } else if (mutation.equals("ModifyStaticMutation")) {
                    ModifyStaticMutation staticMutation = new ModifyStaticMutation(cl);
                    staticMutation.mutate();
                    return cl.getSuperclass().toBytecode();
                } else if (mutation.equals("ModifyNonStaticMutation")) {
                    ModifyNonStaticMutation staticMutation = new ModifyNonStaticMutation(cl);
                    staticMutation.mutate();
                } else if (mutation.equals("InsertHidingVarMutation")) {
                    InsertHidingVarMutation insertMutation = new InsertHidingVarMutation(cl);
                    insertMutation.mutate();
                }
                b = cl.toBytecode();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cl != null) {
                    cl.detach();
                }
            }
        } catch (Exception e) {
        System.out.println(e.getMessage());
        }
        return b;
    }
}
