package template;

public class Template {

    public synchronized static void instrum(int line, String methodName, Pair... pairs){
        System.out.print(line + "\t\t" + methodName);
        for (Pair p : pairs)
        {
            System.out.print("\t\t" + p.getName() + "\t\t" + p.getValue());
        }
        System.out.println();
    }
}
