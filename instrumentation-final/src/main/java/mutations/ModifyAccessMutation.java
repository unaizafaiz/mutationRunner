package mutations;

import javassist.CtBehavior;
import javassist.CtClass;
import javassist.bytecode.AccessFlag;

public class ModifyAccessMutation {

    private final CtClass cl;

    public ModifyAccessMutation(CtClass cl) {
        this.cl = cl;
    }

    public void mutate() {
        CtBehavior[] methods = cl.getDeclaredBehaviors();
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].isEmpty() == false) {
                changeMethod(methods[i]);
                //changeModifier(methods[i]);
            }
        }

    }

    private void changeMethod(CtBehavior method) {
        int modifiers = method.getModifiers();
        if((modifiers & AccessFlag.PUBLIC) == AccessFlag.PUBLIC){
            System.out.println("Method "+method.getName()+" is Public");
            method.setModifiers((modifiers & ~AccessFlag.PUBLIC) | AccessFlag.PRIVATE);
        } else if ((modifiers & AccessFlag.PRIVATE) == AccessFlag.PRIVATE)
            System.out.println("Method "+method.getName()+" is Private");
        else if ((modifiers & AccessFlag.PROTECTED) == AccessFlag.PROTECTED)
            System.out.println("Method "+method.getName()+" is Protected");
        else
            System.out.println("Method "+method.getName()+" is Default");
    }
}
