package mutations;

import javassist.CtBehavior;
import javassist.CtClass;
import javassist.bytecode.AccessFlag;


/*
 * Class implements Encapsulation Level Mutation
 * AMC - Access Modifier Change Operator
 * Modifying public access modifer to private
 */

public class ModifyPublicAccessMutation {

    private final CtClass cl;

    public ModifyPublicAccessMutation(CtClass cl) {
        this.cl = cl;
    }

    public void mutate() {
        CtBehavior[] methods = cl.getDeclaredBehaviors();
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].isEmpty() == false) {
                changeMethod(methods[i]);
            }
        }

    }

    private void changeMethod(CtBehavior method) {
        int modifiers = method.getModifiers();
        if((modifiers & AccessFlag.PUBLIC) == AccessFlag.PUBLIC){
            method.setModifiers((modifiers & ~AccessFlag.PUBLIC) | AccessFlag.PRIVATE);
        }
    }
}
