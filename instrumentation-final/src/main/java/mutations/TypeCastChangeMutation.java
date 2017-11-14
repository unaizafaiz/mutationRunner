package mutations;

import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;

/*
 * This class implements Polymorphism type mutation -
 * PCC: Type Cast Change
 * Changing the Type of a Sub Class field to its corresponding Super Class field
 */

public class TypeCastChangeMutation {

    CtClass cl;
    public TypeCastChangeMutation(CtClass cl) {
        this.cl = cl;
    }

    public void mutate() throws NotFoundException {
        CtField[] ctFields = cl.getDeclaredFields();
        CtClass superClass = cl.getSuperclass();

        for (CtField ctField : ctFields) {
            if (ctField.getType().getName().equals(cl.getName()) &&
                    !superClass.getName().equals("java.lang.Object"))
                System.out.println("Changing Type " + ctField.getName() + " from " + ctField.getType().getName() + " to"
                        + " super class Type >" + superClass.getName());
            ctField.setType(superClass);
        }
    }
}
