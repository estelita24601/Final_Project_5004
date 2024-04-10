/**
 * this is the front facing class of the model
 * the only class allowed to communicate with the controller
 * will parse and translate commands from the controller to fit whatever the model wants
 * will translate output from the model into whatever the controller wants
 */
package model;

import java.util.ArrayList;

import model.personnel.ICrewMember;
import model.scheduling.Shift;

public interface IModel {
    

    ArrayList<String> getRankOptions();

    ArrayList<String> getSpeciesOptions();

    ArrayList<String> getDepartmentOptions();

    ArrayList<String> getShiftOptions();

    ICrewMember creatCrewMember();

    Shift createShift();
}
