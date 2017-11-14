package mutations;

import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import java.util.HashSet;
import java.util.Set;

/*
 * OMD - overloading method deletion
 * Implements Mutation Operator to delete an overloading method
 */
public class DeletingOverloadingMethodMutation {
    CtClass cl;
    public DeletingOverloadingMethodMutation(CtClass cl) {
        this.cl=cl;
    }

    public void mutate() {
        try{
            CtMethod[] methods = cl.getDeclaredMethods();
            Set<String> methodSet = new HashSet<>();
                for (CtMethod ctMethod : methods) {
                        if (!methodSet.add(ctMethod.getName())) {
                            System.out.println("Deleting method " + ctMethod.getName()
                                    + " from class " + cl.getName());
                            cl.removeMethod(ctMethod);
                        }
                    }
        } catch ( NotFoundException e) {
            e.printStackTrace();
        }
    }
}
