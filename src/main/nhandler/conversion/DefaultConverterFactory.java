package nhandler.conversion;

import java.util.HashMap;
import java.util.Map;

import nhandler.conversion.jpf2jvm.JPF2JVMConverter;
import nhandler.conversion.jpf2jvm.JPF2JVMGenericConverter;
import nhandler.conversion.jvm2jpf.JVM2JPFConverter;
import nhandler.conversion.jvm2jpf.JVM2JPFGenericConverter;

/**
 * Default factory to create type specific converter
 * 
 * @author Nastaran Shafiei
 */
public class DefaultConverterFactory implements ConverterFactory {

  // So that it never clashes with a class name:
  private static final String GENERIC = "^^generic^^";

  private static Map<String, JPF2JVMConverter> jpf2jvmconverters = new HashMap<String, JPF2JVMConverter>();

  private static Map<String, JVM2JPFConverter> jvm2jpfconverters = new HashMap<String, JVM2JPFConverter>();

  private static final ConformanceChecker checker = SimpleListConformanceChecker.getInstance();

  @Override
  public JPF2JVMConverter getJPF2JVMConverter (String clsName) throws NoSuitableConverterFoundException {
    if (!checker.isConformant(clsName)) {
      if (jpf2jvmconverters.containsKey(clsName)) return jpf2jvmconverters.get(clsName);

      String name = "nhandler.conversion.jpf2jvm." + "JPF2JVM" + clsName.replace(".", "_") + "Converter";
      try {
        Class<?> converter = Class.forName(name);
        JPF2JVMConverter converterObj = (JPF2JVMConverter) converter.newInstance();
        jpf2jvmconverters.put(clsName, converterObj);
        return converterObj;
      } catch (ClassNotFoundException e) {
        throw new NoSuitableConverterFoundException("No suitable converter found for class " + clsName);
      } catch (InstantiationException e) {
        e.printStackTrace();
        System.exit(1);
      } catch (IllegalAccessException e) {
        e.printStackTrace();
        System.exit(1);
      }
      return null; // will never reach here
    } else {
      if (!jpf2jvmconverters.containsKey(GENERIC)) jpf2jvmconverters.put(GENERIC, new JPF2JVMGenericConverter());
      return jpf2jvmconverters.get(GENERIC);
    }
  }

  @Override
  public JVM2JPFConverter getJVM2JPFConverter (String clsName) throws NoSuitableConverterFoundException {
    if (!checker.isConformant(clsName)) {
      if (jvm2jpfconverters.containsKey(clsName)) return jvm2jpfconverters.get(clsName);

      String name = "nhandler.conversion.jvm2jpf." + "JVM2JPF" + clsName.replace(".", "_") + "Converter";

      try {
        Class<?> converter = Class.forName(name);
        JVM2JPFConverter converterObj = (JVM2JPFConverter) converter.newInstance();
        jvm2jpfconverters.put(clsName, converterObj);
        return converterObj;
      } catch (ClassNotFoundException e) {
        throw new NoSuitableConverterFoundException("No suitable converter found for class " + clsName);
      } catch (InstantiationException e) {
        e.printStackTrace();
        System.exit(1);
      } catch (IllegalAccessException e) {
        e.printStackTrace();
        System.exit(1);
      }
      return null; // will never reach here
    } else {
      if (!jvm2jpfconverters.containsKey(GENERIC)) jvm2jpfconverters.put(GENERIC, new JVM2JPFGenericConverter());
      return jvm2jpfconverters.get(GENERIC);
    }
  }
}
