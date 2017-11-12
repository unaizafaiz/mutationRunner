package mutations;

import javassist.CtClass;
import javassist.CtField;
import javassist.Modifier;
import javassist.bytecode.AccessFlag;

public class ModifyStaticMutation {
    CtClass cl;
    
    public ModifyStaticMutation(CtClass cl) {
        this.cl=cl;
    }

    public void mutate() {
        for(CtField ctField: cl.getDeclaredFields()){
            if(!Modifier.isStatic(ctField.getModifiers())){
                ctField.setModifiers(ctField.getModifiers() | AccessFlag.STATIC);
                System.out.println("Added static "+ctField.getName());
            }
            else {
                ctField.setModifiers(ctField.getModifiers() & ~Modifier.STATIC);
            }
        }
    }
}
