package mutations;

import javassist.CtClass;
import javassist.CtField;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.AccessFlag;

/*
 * This class implements Java Specific features -
 * java static deletion
 * Mutate static variable in superClass to non-static variables
 */
public class ModifyStaticMutation {
    CtClass cl;
    
    public ModifyStaticMutation(CtClass cl) {
        this.cl=cl;
    }

    public void mutate() {
        try {
            for(CtField ctField: cl.getSuperclass().getDeclaredFields()){
                //JSI
                if(Modifier.isStatic(ctField.getModifiers())) {
                    System.out.println(ctField.getName()+" is static "+ctField.getModifiers());
                    ctField.setModifiers(ctField.getModifiers() & ~Modifier.STATIC);
                    System.out.println(ctField.getName()+" changed to "+ctField.getModifiers());
                }else{
                    System.out.println(ctField.getName()+" is not static");

                }
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }
}
