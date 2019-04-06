package reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;

public class ReflectionTest {
	public static void main(String[] args){
		printFields(BigDecimal.class);
		printConstructors(Double.class);
		printMethods(Integer.class);
	}
	
	/**
	 * 
	 * @param cl
	 */
	public static void printConstructors(Class cl){
		
		Constructor[] constructors = cl.getDeclaredConstructors();
		
		for(Constructor c: constructors){
			String name = c.getName();
			System.out.print("      ");
			String modifiers = Modifier.toString(c.getModifiers());
			
			if(modifiers.length()>0){
				System.out.print(modifiers + "　");
			}
			System.out.print(name + "(");
			
			Class[] paramType = c.getParameterTypes();
			for(int j=0;j<paramType.length;j++){
				if(j>0){
					System.out.print(",");
				}
				System.out.print(paramType[j].getName());
			}
			System.out.println(");");
		}
	}
	
	public static void printMethods(Class cl){
		Method[] methods = cl.getMethods();
		for(Method m:methods){
			Class retType = m.getReturnType();
			String name = m.getName();
			System.out.print(" ");
			String modifiers = Modifier.toString(m.getModifiers());
			if(modifiers.length()>0){
				System.out.print(modifiers + "　");
			}
			System.out.print(retType.getName() + " "+name + "(");
			
			Class[] paramType = m.getParameterTypes();
			for(int j=0;j<paramType.length;j++){
				if(j>0){
					System.out.print(",");
				}
				System.out.print(paramType[j].getName());
			}
			System.out.println(");");
		}
	}
	public static void printFields(Class cl){
		Field[] fields = cl.getDeclaredFields();
		for(Field f:fields){
			Class type = f.getType();
			String name = f.getName();
			System.out.print("  ");
			String modifiers = Modifier.toString(f.getModifiers());
			if(modifiers.length()>0){
				System.out.print(modifiers + "　");
			}
			System.out.println(type.getName() + " "+name + "");
 		}
	}
}
