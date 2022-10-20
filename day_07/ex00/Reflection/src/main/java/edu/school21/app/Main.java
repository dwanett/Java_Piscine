package edu.school21.app;

import edu.school21.classes.Car;
import edu.school21.classes.User;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		Object[] classes = loadClasses();
		Object clasz = null;
		Object createdObject = null;

		printNameClasses(classes);
		while (clasz == null) {
			clasz = choiceClass(classes, scanner);
		}
		printInfoClass(clasz.getClass());

		try {
			createdObject = createObject(clasz);
			updateObject(createdObject);
			selectMethod(createdObject);
		} catch (IllegalAccessException | InvocationTargetException
				 | InstantiationException | NoSuchFieldException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
	}

	private static void selectMethod(Object object) throws InvocationTargetException, IllegalAccessException {
		Method[] methods = object.getClass().getDeclaredMethods();
		Scanner scanner = new Scanner(System.in);
		Method method = null;

		while (method == null) {
			System.out.print("Enter name of the method for call:\n-> ");
			String nameMethod = scanner.nextLine();
			for (int i = 0; i < methods.length; i++) {
				String nameTmp = methods[i].toString().replaceAll("([\\w\\s]+[.][\\w\\s]+[.])+", "");
				if (nameTmp.equals(nameMethod)) {
					boolean flag = true;
					for (Method methodSuper : Object.class.getDeclaredMethods()) {
						if (methodSuper.getName().equals(methods[i].getName())) {
							flag = false;
							break;
						}
					}
					if (flag) {
						method = methods[i];
						break;
					}
				}
			}
			if (method == null) {
				System.err.println("Method was not found! ");
			}
		}
		method.setAccessible(true);
		List<Object> param = getValueParametrs(method);
		Object result = method.invoke(object, param.toArray());
		if (result != null) {
			System.out.printf("Method returned:\n%s\n", result);
		}
		scanner.close();
	}

	private static List<Object> getValueParametrs(Method method) {
		List<Object> result = new ArrayList<>();
		Scanner scanner = new Scanner(System.in);
		Parameter[] parameters = method.getParameters();

		for (int i = 0; i < parameters.length; i++) {
			System.out.printf("Enter %s value:\n-> ", parameters[i].getType().getSimpleName());
			String nameType = parameters[i].getType().getSimpleName();
			if (nameType.equals("Integer") || nameType.equals("int")) {
				result.add(scanner.nextInt());
			} else if (nameType.equals("Long") || nameType.equals("long")) {
				result.add(scanner.nextLong());
			} else if (nameType.equals("Boolean") || nameType.equals("boolean")) {
				result.add(scanner.nextBoolean());
			} else if (nameType.equals("Double") || nameType.equals("double")){
				result.add(scanner.nextDouble());
			} else if (nameType.equals("String")) {
				result.add(scanner.nextLine());
			}
		}
		return result;
	}

	private static void updateObject(Object object) throws NoSuchFieldException, IllegalAccessException {
		System.out.print("Enter name of the field for changing:\n-> ");
		Scanner scanner = new Scanner(System.in);
		Object value = null;
		String nameField = scanner.nextLine();
		Field field = object.getClass().getDeclaredField(nameField);
		field.setAccessible(true);

		System.out.printf("Enter %s value:\n-> ", field.getType().getSimpleName());
		String nameType = field.getType().getSimpleName();
		if (nameType.equals("Integer") || nameType.equals("int")) {
			value = scanner.nextInt();
		} else if (nameType.equals("Long") || nameType.equals("long")) {
			value = scanner.nextLong();
		} else if (nameType.equals("Boolean") || nameType.equals("boolean")) {
			value = scanner.nextBoolean();
		} else if (nameType.equals("Double") || nameType.equals("double")){
			value = scanner.nextDouble();
		} else if (nameType.equals("String")) {
			value = scanner.nextLine();
		}
		field.set(object, value);
		System.out.printf("Object updated: %s\n", object);
		System.out.println("---------------------");
	}

	private static Object createObject(Object clasz) throws InstantiationException, IllegalAccessException, InvocationTargetException {
		System.out.println("---------------------");
		System.out.println("Let's create an object.");
		Constructor[] constructors = clasz.getClass().getConstructors();
		Constructor constructor = constructors[0];
		for (int i = 0; i < constructors.length; i++) {
			if(constructors[i].getParameterCount() > 0) {
				constructor = constructors[i];
				break;
			}
		}
		Object object = null;
		List<Object> constructorParam = getValueParametrsConstructor(constructor);
		object = constructor.newInstance(constructorParam.toArray());
		System.out.println("Object created: " + object);
		System.out.println("---------------------");
		return object;
	}

	private static List<Object> getValueParametrsConstructor(Constructor constructor) {
		List<Object> result = new ArrayList<>();
		Scanner scanner = new Scanner(System.in);
		Parameter[] parameters = constructor.getParameters();

		for (int i = 0; i < parameters.length; i++) {
			System.out.printf("%s:\n-> ", parameters[i].getName());
			String nameType = parameters[i].getType().getSimpleName();
			if (nameType.equals("Integer") || nameType.equals("int")) {
				result.add(scanner.nextInt());
			} else if (nameType.equals("Long") || nameType.equals("long")) {
				result.add(scanner.nextLong());
			} else if (nameType.equals("Boolean") || nameType.equals("boolean")) {
				result.add(scanner.nextBoolean());
			} else if (nameType.equals("Double") || nameType.equals("double")){
				result.add(scanner.nextDouble());
			} else if (nameType.equals("String")) {
				result.add(scanner.nextLine());
			}
		}
		return result;
	}

	private static void printInfoClass(Class clasz) {
		System.out.println("fields:");
		Field[] fields = clasz.getDeclaredFields();
		Method[] methods = clasz.getDeclaredMethods();

		for (Field field : fields) {
			System.out.printf("\t%s %s\n", field.getType().getSimpleName(), field.getName());
		}

		System.out.println("methods:");
		for (Method method : methods) {
			boolean flag = true;
			for (Method methodSuper : Object.class.getDeclaredMethods()) {
				if (methodSuper.getName().equals(method.getName())) {
					flag = false;
					break;
				}
			}
			if (flag) {
				System.out.printf("\t%s %s\n", method.getReturnType().getSimpleName()
						, method.toString().replaceAll("([\\w\\s]+[.][\\w\\s]+[.])+", ""));
			}
		}
	}

	private static void printNameClasses(Object[] classes) {
		System.out.println("Classes:");
		for (Object o : classes) {
			System.out.println(o.getClass().getSimpleName());
		}
	}

	private static Object choiceClass(Object[] classes, Scanner scanner) {
		System.out.println("---------------------");
		System.out.println("Enter class name:");
		System.out.print("-> ");
		String line = scanner.nextLine();
		Object clasz = null;
		for (Object o : classes) {
			if (o.getClass().getSimpleName().equals(line)) {
				clasz = o;
				break;
			}
		}
		System.out.println("---------------------");
		if (clasz == null) {
			System.err.println("Error: Select class from existing!");
		}
		return clasz;
	}
	private static Object[] loadClasses() {
		Object[] ret = new Object[2];
		ret[0] = new Car();
		ret[1] = new User();
		return (ret);
	}
}
