/**
 *
 */
package edu.muc.model;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;


/**
 * @author 龚文东
 *         <p>
 *         <p>
 *         2015年5月11日 下午7:46:55
 */
public abstract class BaseModel<M extends BaseModel<M>> implements Serializable {
    /**
     * the serialVersionUID for the implements the serialable interface.
     */
    private static final long serialVersionUID = 4810999122759684161L;

    @SuppressWarnings("finally")
    public Object toGetObject(String valueName) {
        Object object = null;
        try {
            object = this.getClass()
                    .getMethod("get" + valueName, new Class[]{})
                    .invoke(this, new Object[]{});
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
        } finally {
            return object;
        }
    }
}
