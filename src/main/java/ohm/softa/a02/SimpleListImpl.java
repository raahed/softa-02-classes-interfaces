package ohm.softa.a02;

import sun.swing.plaf.synth.SynthFileChooserUIImpl;

import javax.swing.*;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * @author Peter Kurfer
 * Created on 10/6/17.
 */
public class SimpleListImpl implements SimpleList, Iterable {

    private Element head = null;

     private static class Element {

        private Object item;
        private Element next;

        Element(Object item, Element next) {
            this.item = item;
            this.next = next;
        }
    }

    public class SimpleIteratorImpl implements Iterator {

        private Element next;

        public SimpleIteratorImpl(Element list) {
            next = list;
        }

        @Override
        public boolean hasNext() {
            return (next != null);
        }

        @Override
        public Object next() {
            Object item = next.item;
            this.next = next.next;
            return item;
        }
    }

    @Override
    public Iterator iterator() {
        return new SimpleIteratorImpl(head);
    }

    @Override
    public void add(Object o) {
        if (head == null) {
            head = new Element(o, null);
            return;
        }

        Element current = head;

        while(current.next != null)
            current = current.next;

        current.next = new Element(o, null);
    }

    @Override
    public int size() {
        int count = 0;

        for(Object o : this) count++;

        return count;
    }

    @Override
    public SimpleList filter(SimpleFilter filter) {
        SimpleList filteredList = new SimpleListImpl();

        for (Object o : this)
            if(filter.include(o))
                filteredList.add(o);

        return filteredList;
    }
}
