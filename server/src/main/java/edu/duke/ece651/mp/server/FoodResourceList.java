package edu.duke.ece651.mp.server;

import edu.duke.ece651.mp.common.FoodResource;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.String;
import java.util.HashMap;

import edu.duke.ece651.mp.common.Resource;
import java.util.HashMap;

public class FoodResourceList implements Serializable {
    private HashMap<String, FoodResource> resource_list;

    public FoodResourceList() {
        resource_list = new HashMap<String, FoodResource>();
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        /*
         * Call even if there is no default serializable fields.
         * Enables default serializable fields to be added in future versions
         * and skipped by this version which has no default serializable fields.
         */
        s.defaultReadObject();
        // restore
        resource_list = (HashMap<String, FoodResource>) s.readObject();
    }

}
