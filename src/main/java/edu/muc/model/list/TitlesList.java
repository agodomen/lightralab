/**
 *
 */
package edu.muc.model.list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import edu.muc.model.Title;

/**
 * @author 龚文东
 *         <p>
 *         <p>
 *         2015年5月1日 下午5:53:35
 */
@XmlRootElement(name = "Titles")
public class TitlesList {

    @XmlElement(name = "title")
    private List<Title> titles;

    /**
     *
     */
    public TitlesList() {
        // TODO Auto-generated constructor stub
        titles = new ArrayList<>();
    }

    public TitlesList(List<Title> titles) {
        this.titles = titles;
    }

    public Iterator<Title> iterator() {
        return titles.iterator();
    }

    public void add(Title e) {
        titles.add(e);
    }


    public List<Title> toGetTitles() {
        return titles;
    }

    public void toSetTitles(List<Title> titles) {
        this.titles = titles;
    }

}
