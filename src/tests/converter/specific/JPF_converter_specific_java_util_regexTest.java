package converter.specific;

import gov.nasa.jpf.annotation.MJI;
import gov.nasa.jpf.util.test.TestJPF;
import gov.nasa.jpf.vm.MJIEnv;
import gov.nasa.jpf.vm.NativePeer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nhandler.conversion.ConversionException;
import nhandler.conversion.ConverterBase;
import nhandler.conversion.jpf2jvm.JPF2JVMConverter;
import nhandler.conversion.jvm2jpf.JVM2JPFConverter;

public class JPF_converter_specific_java_util_regexTest extends NativePeer {
  
  @MJI
  public static int convertPatternTest__Ljava_util_regex_Pattern_2__Ljava_util_regex_Pattern_2
  (MJIEnv env, int objRef, int rPattern) {
    
    ConverterBase.reset(env);
    
    Pattern pattern = null;
    
    try {
      pattern = (Pattern) JPF2JVMConverter.obtainJVMObj(rPattern, env);
    } catch (ConversionException e) {
      e.printStackTrace();
    }
    
    TestJPF.assertEquals(java_util_regexTest.PATTERN1, pattern.toString());
    
    int JPFRet = MJIEnv.NULL;
    try {
      JPFRet = JVM2JPFConverter.obtainJPFObj(Pattern.compile(java_util_regexTest.PATTERN2), env);
    } catch (ConversionException e) {
      e.printStackTrace();
    }
    return JPFRet;
  }
  
  @MJI
  public static int convertMatcherTest__Ljava_util_regex_Matcher_2__Ljava_util_regex_Matcher_2
  (MJIEnv env, int objRef, int rMatcher) {
    
    ConverterBase.reset(env);
    
    Matcher matcher = null;
    
    try {
      matcher = (Matcher) JPF2JVMConverter.obtainJVMObj(rMatcher, env);
    } catch (ConversionException e) {
      e.printStackTrace();
    }
    
    TestJPF.assertTrue(java_util_regexTest
                       .verifyMatcher(matcher,
                                      java_util_regexTest.PATTERN1,
                                      "text",
                                      java_util_regexTest.SEQUENCE1));
    
    int JPFRet = MJIEnv.NULL;
    try {
      JPFRet = JVM2JPFConverter.obtainJPFObj(Pattern.compile(java_util_regexTest.PATTERN2)
                                             .matcher(java_util_regexTest.SEQUENCE2)
                                             , env);
    } catch (ConversionException e) {
      e.printStackTrace();
    }
    return JPFRet;
  }
}
