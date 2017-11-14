package mutations;

import javassist.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/*
 * IOD - Overriding method deletion
 * Implements Mutation Operator to delete an overriding method in subclass
 */
public class DeletingOverridingMethodMutation {
    CtClass cl;
    public DeletingOverridingMethodMutation(CtClass cl) {
        this.cl=cl;
    }

    public void mutate() {
        try{
                CtMethod[] superMethods = cl.getSuperclass().getDeclaredMethods();
                CtMethod[] methods = cl.getDeclaredMethods();
                for(CtMethod ctSuperMethod: superMethods) {
                    Set<String> methodSet = new HashSet<>();
                    for (CtMethod ctMethod : methods) {
                        if(ctSuperMethod.getName().equals(ctMethod.getName())) {
                            if (methodSet.add(ctMethod.getName())) {
                                System.out.println("Deleting method " + ctMethod.getName()
                                        + " from class " + cl.getName());
                                cl.removeMethod(ctMethod);
                            }
                        }
                    }
                }
            } catch ( NotFoundException e) {
                e.printStackTrace();
            }
    }
}
