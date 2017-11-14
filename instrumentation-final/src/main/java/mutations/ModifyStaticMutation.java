package mutations;

import javassist.CtClass;
import javassist.CtField;
import javassist.Modifier;
import javassist.NotFoundException;

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
                if(Modifier.isStatic(ctField.getModifiers())) {
                    ctField.setModifiers(ctField.getModifiers() & ~Modifier.STATIC);
                    System.out.println(ctField.getName()+" changed to non static");
                }else{
                    System.out.println(ctField.getName()+" is not static");
                }
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }
}
