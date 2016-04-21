/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.interfaces;

import mygame.javaclasses.MyArrayList;

/**
 *
 * @author GAMEOVER
 */
public interface IObservable {
    void addObserver(IObserver o);
    void removeObserver(IObserver o);
    void notifyAllObservers(String update);
}
