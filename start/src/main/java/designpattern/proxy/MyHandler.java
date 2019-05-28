package designpattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyHandler implements InvocationHandler{

	private DemoProxy demoProxy;
	
	
	
	public MyHandler(DemoProxy demoProxy) {
		super();
		this.demoProxy = demoProxy;
	}



	@Override
	public Object invoke(Object proxy, Method method, Object[] args){
		try {
			System.out.println(method.getName());
			method.invoke(demoProxy, args);
			System.out.println("hello over");
			return null;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		DemoProxy demoProxy = new DemoProxyImpl();
		MyHandler handler = new MyHandler(demoProxy);
		Class<?> proxyClass = Proxy.getProxyClass(DemoProxy.class.getClassLoader(), DemoProxy.class);
		try {
			DemoProxy f = (DemoProxy) proxyClass.getConstructor(InvocationHandler.class).
			                     newInstance(handler);
			f.hello();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DemoProxy f = (DemoProxy) Proxy.newProxyInstance(DemoProxy.class.getClassLoader(),
                new Class<?>[] { DemoProxy.class },
                handler);
		
		f.hello();
	}

}
