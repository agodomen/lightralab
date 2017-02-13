/**
 *
 */
package edu.muc.model.list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import edu.muc.model.Type;

/**
 * @author 龚文东
 *         <p>
 *         <p>
 *         2015年5月31日 下午6:24:28
 */
@XmlRootElement(name = "Types")
public class TypesList {
    @XmlElement(name = "Type")
    private List<Type> types;

    public TypesList() {
        types = new ArrayList<>();
    }

    /**
     * @return the titles
     */
    public List<Type> toGetTypes() {
        return types;
    }

    /**
     * @param titles the titles to set
     */
    public void toSetTypes(List<Type> types) {
        this.types = types;
    }

    public void addType(Type type) {
        types.add(type);
    }

    public Iterator<Type> iterator() {
        return types.iterator();
    }

}
