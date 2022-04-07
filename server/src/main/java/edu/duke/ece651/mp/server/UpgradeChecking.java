package edu.duke.ece651.mp.server;

import java.util.ArrayList;

import edu.duke.ece651.mp.common.Map;
import edu.duke.ece651.mp.common.Territory;
import edu.duke.ece651.mp.common.UpgradeTurn;
import edu.duke.ece651.mp.common.TechResourceList;

public class UpgradeChecking<T> {
    String upgradeStatus;
    public int upgrade_cost;

    public ArrayList<UpgradeTurn> checkMyRule(Map<T> map, ArrayList<UpgradeTurn> upgradeOrder,
            TechResourceList tech_list) {
        ArrayList<UpgradeTurn> ans = new ArrayList<UpgradeTurn>();
        for (UpgradeTurn t : upgradeOrder) {
            boolean b = checkMyRule(map, t, tech_list);
            if (b) {
                ans.add(t);
            }
        }
        return ans;
    }

    public boolean checkMyRule(Map<T> map, UpgradeTurn upgradeOrder, TechResourceList tech_list) {
        String source = upgradeOrder.getFromTerritory();
        String old_unit_type = upgradeOrder.getOldUnitType();
        String new_unit_type = upgradeOrder.getNewUniType();
        int upgradeunits = upgradeOrder.getNumber();
        String player_color = upgradeOrder.getPlayerColor();
        int available_num = tech_list.resource_list.get(player_color).getResourceAmount();
        Territory<T> source_terr = map.getAllTerritories().get(source);

        upgradeStatus = player_color + ": Upgrade order in "
                + source + " from " + old_unit_type + " to " + new_unit_type + "(num:"
                + upgradeunits + ") units was ";

        // check if the source belongs to the attacker
        if (!source_terr.getColor().equals(player_color)) {
            upgradeStatus += "invalid as the source is not owned by the player";
            return false;
        }

        // check if the source has enough old unit type
        if (source_terr.getUnit(old_unit_type) < upgradeunits) {
            upgradeStatus += "invalid as the source don't have enough amount of " + old_unit_type;
            return false;
        }

        int base = 0;
        int top = 0;
        switch (old_unit_type) {
            case "ALEVEL":
                base = 0;
                break;
            case "BLEVEL":
                base = 50;
                break;
            case "CLEVEL":
                base = 125;
                break;
            case "DLEVEL":
                base = 250;
                break;
            case "ELEVEL":
                base = 450;
                break;
            case "FLEVEL":
                base = 750;
                break;
        }
        switch (new_unit_type) {
            case "BLEVEL":
                top = 50;
                break;
            case "CLEVEL":
                top = 125;
                break;
            case "DLEVEL":
                top = 250;
                break;
            case "ELEVEL":
                top = 450;
                break;
            case "FLEVEL":
                top = 750;
                break;
        }
        int upgrade_price = top - base;
        // check if the new unit level is higher than the old one
        if (upgrade_price <= 0) {
            upgradeStatus += "invalid as the " + new_unit_type + " is lower than " + old_unit_type;
            return false;
        }

        // check if the Player has enough tech resources to upgrade
        int need_amount = upgrade_price * upgradeunits;
        if (need_amount > available_num) {
            upgradeStatus += "invalid as the available tech resource's amount is not enough";
            return false;
        }

        // passed all rules!
        upgradeStatus += "valid - ";
        return true;
    }

}
