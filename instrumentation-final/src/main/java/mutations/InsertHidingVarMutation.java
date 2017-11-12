package mutations;


import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;

public class InsertHidingVarMutation {
    CtClass cl;

    public InsertHidingVarMutation(CtClass cl) {
        this.cl=cl;
    }

    public void mutate() {
        try {

            CtField[] superClassFields = cl.getSuperclass().getDeclaredFields();
            CtField[] classFields = cl.getDeclaredFields();
            for(CtField superCF: superClassFields){
                Boolean hasField = false;
                for(CtField classF: classFields){
                    if(classF.getName().equals(superCF.getName())){
                        hasField=true;
                        break;
                    }
                }
                if(!hasField){
                    CtField newField = new CtField(superCF.getType(), superCF.getName(),cl);
                    cl.addField(newField);
                    System.out.println("Added field "+superCF.getName()+" to subclass "+cl.getName());
                }
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }

    }
}
