package CSVHandling;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Application.*;
import Enum.ApplicationStatus;
import Interfaces.CSVHandler;

class ProjectCSVHandler implements CSVHandler<Project> {
    @Override
    public List<Project> loadFromCSV(String filePath) {
        List<Project> projects = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length == 13) {
                    String projectName = columns[0];
                    String neighborhood = columns[1];
                    String region = columns[2];
                    int numUnitsType1 = Integer.parseInt(columns[3]);
                    int sellingPriceType1 = Integer.parseInt(columns[4]);
                    int numUnitsType2 = Integer.parseInt(columns[5]);
                    int sellingPriceType2 = Integer.parseInt(columns[6]);
                    String openingDate = columns[7];
                    String closingDate = columns[8];
                    String manager = columns[9];
                    int officerSlot = Integer.parseInt(columns[10]);
                    String[] officerList = columns[11].split(",");
                    projects.add(new Project(projectName, neighborhood, region, numUnitsType1, sellingPriceType1,
                            numUnitsType2, sellingPriceType2, openingDate, closingDate, manager, officerSlot, Arrays.asList(officerList)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return projects;
    }

    @Override
    public void saveToCSV(List<Project> projects, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("projectName,neighborhood,region,numUnitsType1,sellingPriceType1,numUnitsType2,sellingPriceType2,applicationOpeningDate,applicationClosingDate,manager,officerSlot,officers\n");
            for (Project p : projects) {
                writer.write(String.format("\"%s\",\"%s\",\"%s\",%d,%d,%d,%d,\"%s\",\"%s\",\"%s\",%d,\"%s\"\n",
                        p.getProjectName(), p.getNeighborhood(), p.getRegion(),
                        p.getNumUnitsType1(), p.getSellingPriceType1(),
                        p.getNumUnitsType2(), p.getSellingPriceType2(),
                        p.getOpeningDate(), p.getClosingDate(),
                        p.getManager(), p.getOfficerSlot(), String.join(",", p.getOfficers())));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
