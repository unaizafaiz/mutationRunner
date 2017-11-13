package mutations;


import javassist.CtClass;
import javassist.CtField;
import javassist.Modifier;
import javassist.bytecode.AccessFlag;

/*
 * This class implements Java Specific features -
 * java static insertion JSI
 * Mutating a non-static variable in the class to static variable
 *
 */
public class ModifyNonStaticMutation {
    CtClass cl;

    public ModifyNonStaticMutation(CtClass cl) {
        this.cl=cl;
    }

    public void mutate() {
        for(CtField ctField: cl.getDeclaredFields()){
            //JSI
            if(!Modifier.isStatic(ctField.getModifiers())){
                ctField.setModifiers(ctField.getModifiers() | AccessFlag.STATIC);
            }
        }
    }
}
