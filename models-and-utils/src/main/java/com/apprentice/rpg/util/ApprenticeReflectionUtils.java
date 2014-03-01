package com.apprentice.rpg.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.log4j.Logger;

import com.apprentice.rpg.model.ApprenticeEx;
import com.google.common.collect.Lists;

/**
 * Helper methods related to reflection
 * 
 * @author theoklitos
 * 
 */
public final class ApprenticeReflectionUtils {

	private static Logger LOG = Logger.getLogger(ApprenticeReflectionUtils.class);

	/**
	 * Calls a method on a given object.
	 * 
	 * @throws ApprenticeEx
	 *             encapsulates all possible exceptions, so take care
	 */
	public static void callMethodOnObject(final String methodName, final Object target, final Object... parameters)
			throws ApprenticeEx {
		LOG.debug("Calling method " + methodName + "()");
		Method openFrameMethod;
		try {
			if (parameters.length == 0 || parameters == null || (parameters.length == 1 && parameters[0] == null)) {
				openFrameMethod = target.getClass().getMethod(methodName);
				openFrameMethod.invoke(target);
			} else {
				final List<Class<?>> methodSignatureTypes = Lists.newArrayList();
				for (final Object paramter : parameters) {
					methodSignatureTypes.add(paramter.getClass());
				}
				final Class<?>[] parameterArray =
					methodSignatureTypes.toArray(new Class<?>[methodSignatureTypes.size()]);
				openFrameMethod = target.getClass().getMethod(methodName, parameterArray);
				openFrameMethod.invoke(target, parameters);
			}
		} catch (final IllegalAccessException e) {
			throw new ApprenticeEx(e);
		} catch (final IllegalArgumentException e) {
			throw new ApprenticeEx(e);
		} catch (final InvocationTargetException e) {
			throw new ApprenticeEx(e);
		} catch (final SecurityException e) {
			throw new ApprenticeEx(e);
		} catch (final NoSuchMethodException e) {
			throw new ApprenticeEx(e);
		}
	}
}
