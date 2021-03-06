package edu.duke.ece651.mp.common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

public class Unit implements Serializable {
  private String type;
  private int bonus;
  private int number;

  // key is unit type/level and bvalue is the bonus
  public HashMap<String, Integer> AllUnitTypesBonus;
    /**
   * method to initialize all unit types and their bonus
   */
  private void initializeUnitTypes() {
    AllUnitTypesBonus = new HashMap<>();
    AllUnitTypesBonus.put("Guards", 0);
    AllUnitTypesBonus.put("Infantry", 1);
    AllUnitTypesBonus.put("Archer", 3);
    AllUnitTypesBonus.put("Cavalry", 5);
    AllUnitTypesBonus.put("Dwarves", 8);
    AllUnitTypesBonus.put("Orcs", 11);
    AllUnitTypesBonus.put("Elves", 15);
    AllUnitTypesBonus.put("SPY",17);
  }


  public Unit(String type, int number) {
    initializeUnitTypes();
    this.type = type;
    this.bonus = AllUnitTypesBonus.get(type);
    this.number = number;
  }

  private void writeObject(ObjectOutputStream s) throws IOException {
    s.defaultWriteObject();
    // s.writeObject(unit);
  }

  private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
    s.defaultReadObject();
    // unit = (String) s.readObject();
  }

  public String getUnitType() {
    return this.type;
  }

  public int getBonus() {
    return this.bonus;
  }

  public int getUnitNum() {
    return this.number;
  }

  /**
   * Method to update the number of units
   */
  public void updateUnit(int new_unit) {
    this.number = new_unit;
  }
}

// unit upgrade path: Alevel, Blevel, Clevel, Dlevel, Elevel, Flevel
