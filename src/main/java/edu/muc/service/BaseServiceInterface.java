/**
 *
 */
package edu.muc.service;

import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author 龚文东
 *         <p>
 *         <p>
 *         2015年5月7日 下午4:38:34
 */
public interface BaseServiceInterface<T> {

    /*
     * this is for the find service by the class entity's id
     */
    public T findById(String id);

    /*
     * this is the get all the default directory file data
     */
    public List<T> getAll();

    /*
     * this is for find the list index by the class entity's id
     */
    public int findIndexById(String id);

    /*
     * this is for finding the index by the class entity's name
     */
    public int findIndexByName(String name);

    /*
     * this is the anvation of valueName to find the id
     */
    public int findIndexByValue(String valueName, String value);


    public boolean save();

    /*
     * this is for save the data
     */
    public boolean save(T entity);

    /*
     * this is for the update service
     */
    public boolean update(T entity);

    public boolean update(String id, String value);

    /*
     * this is for the delet the sevice
     */
    public boolean delete(String id);

    public boolean checkValue(String valueName, String value);

}
