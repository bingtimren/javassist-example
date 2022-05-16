package example;

import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class App {
    public static void main(String[] argv) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setSuperclass(Dog.class);
        proxyFactory.setFilter(
                m -> {
                    System.out.println("isHandled: "+m);
                    return m.getName().equals("bark");
                }
        );

        MethodHandler handler = (self, thisMethod, proceed, args) -> {
            System.out.println("Handler "+thisMethod+" of "+self.toString());
            proceed.invoke(self);
            return null;
        };

        System.out.println("================================================================");
        Dog dog = (Dog) proxyFactory.create(new Class<?>[0], new Object[0], handler);
        System.out.println("================================================================");
        dog.bark();
    }
}


