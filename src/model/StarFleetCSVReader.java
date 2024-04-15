package model;

import treeADT.ITree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Predicate;

public class StarFleetCSVReader {
    File inputFile;
    Scanner fileReader;

    public StarFleetCSVReader() {
    }

    public ITree<ICrewMember> loadRootFromFile(String filename) throws FileNotFoundException {
        StarFleetCrew starFleetCrew = new StarFleetCrew();
        try {
            inputFile = new File(filename);
            fileReader = new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Unable to open file");
        }

        ArrayList<String> fileLines = readInAllLines();
        fileLines.remove(0); //remove the header line

        //create the commanding officer and add them to the crew first
        String commandingOfficerInfo = fileLines.get(0);
        StarFleetOfficer commandingOfficer = createOfficerFromTokens(commandingOfficerInfo.split(","));
        starFleetCrew.setRoot(commandingOfficer); //TODO: exception handling for if commanding officer isn't elligible to be root
        fileLines.remove(0); //remove info for commanding officer since we're finished with it

        //add the rest of the officers to the crew
        for (String officerInfo : fileLines) {
            addOfficerFromLine(officerInfo, starFleetCrew);
        }

        return starFleetCrew.getRoot();
    }

    private ArrayList<String> readInAllLines() {
        ArrayList<String> lines = new ArrayList<>();
        while (fileReader.hasNextLine()) {
            lines.add(fileReader.nextLine().strip());
        }
        return lines;
    }

    private void addOfficerFromLine(String line, StarFleetCrew crew) {
        String[] tokens = line.split(",");

        //write predicate that will find the superior
        String supervisorName = tokens[3].strip();
        Predicate<ICrewMember> findSuperior = (officer) -> officer.getName().equalsIgnoreCase(supervisorName);

        //create the officer from the information in the line
        StarFleetOfficer newOfficer = createOfficerFromTokens(tokens);
        //only add to our crew if we were successfully able to create a new officer
        if (newOfficer != null) {
            crew.addCrewMember(newOfficer, findSuperior);
        }
    }

    private StarFleetOfficer createOfficerFromTokens(String[] tokens) {
        String name = tokens[0].strip();

        String rankName = tokens[1].strip();
        Rank rank = (Rank) convertStrToEnum(Rank.values(), rankName);

        String departmentName = tokens[2].strip();
        Department department = (Department) convertStrToEnum(Department.values(), departmentName);

        String[] speciesNames = tokens[4].strip().split(" ");
        ArrayList<Species> speciesList = new ArrayList<>();
        for (String speciesName : speciesNames) {
            Species species = (Species) convertStrToEnum(Species.values(), speciesName);
            speciesList.add(species);
        }

        //first see if we can make a crew member with information given
        StarFleetOfficer newOfficer = null;
        try {
            newOfficer = new StarFleetOfficer(name, rank, department, Rotation.ALPHA, speciesList);
        } catch (IllegalArgumentException e) {
            System.out.printf("WARNING: unable to parse line\n");
        }

        return newOfficer;
    }

    private Object convertStrToEnum(Object[] enumList, String str) {
        str = str.strip();
        for (Object enumVal : enumList) {
            //see if string given matches the enum
            if (str.equalsIgnoreCase(enumVal.toString())) {
                return enumVal;
            }
        }
        return null; //if we were given a value not in our enum list
    }


}
