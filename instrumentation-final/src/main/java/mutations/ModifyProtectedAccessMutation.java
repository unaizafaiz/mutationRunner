package mutations;

import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;
import javassist.bytecode.AccessFlag;


/*
 * Class implements Encapsulation Level Mutation
 * AMC - Access Modifier Change Operator
 * Modifying protected access modifer in superclass to private
 */

public class ModifyProtectedAccessMutation {

    private final CtClass cl;

    public ModifyProtectedAccessMutation(CtClass cl) {
        this.cl = cl;
    }

  public void mutate(){

      try {
          CtField[] classFields = cl.getSuperclass().getDeclaredFields();

          for (CtField field:classFields) {
              int modifiers = field.getModifiers();
              if ((modifiers & AccessFlag.PROTECTED) == AccessFlag.PROTECTED){
                  field.setModifiers((modifiers & AccessFlag.PRIVATE) | AccessFlag.PRIVATE);
                  System.out.println(field.getName()+" changed to PRIVATE");
              }
          }
      } catch (NotFoundException e) {
          e.printStackTrace();
      }

  }

}
