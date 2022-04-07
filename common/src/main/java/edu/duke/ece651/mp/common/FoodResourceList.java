package edu.duke.ece651.mp.common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;

public class FoodResourceList implements Serializable {
    public HashMap<String, FoodResource> resource_list;

    public FoodResourceList() {
        resource_list = new HashMap<String, FoodResource>();
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeObject(resource_list);
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

    public void addResource(String color, FoodResource foodResource) {
        for (Map.Entry<String, FoodResource> set : resource_list.entrySet()) {
            if (set.getKey().equals(color)) {
                int old_num = set.getValue().getResourceAmount();
                int new_num = old_num + foodResource.getResourceAmount();
                resource_list.put(color, new FoodResource(new_num));
                break;
            }
        }
    }

    public void addResource(String color, int foodResource) {
        for (Map.Entry<String, FoodResource> set : resource_list.entrySet()) {
            if (set.getKey().equals(color)) {
                int old_num = set.getValue().getResourceAmount();
                int new_num = old_num + foodResource;
                resource_list.put(color, new FoodResource(new_num));
                break;
            }
        }
    }
}
