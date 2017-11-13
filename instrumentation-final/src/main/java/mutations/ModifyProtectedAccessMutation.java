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
          System.out.println("Superclass name - "+cl.getSuperclass().getName());
          for (CtField field:classFields) {
              int modifiers = field.getModifiers();
              System.out.println("Variable "+field.getName()+" "+modifiers);
              if ((modifiers & AccessFlag.PROTECTED) == AccessFlag.PROTECTED){
                  System.out.println("Modifier is protected");
                  field.setModifiers((modifiers & AccessFlag.PRIVATE) | AccessFlag.PRIVATE);
                  System.out.println(field.getName()+" changed to "+field.getModifiers());
              }
          }
      } catch (NotFoundException e) {
          e.printStackTrace();
      }

  }

}
