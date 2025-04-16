package Application;

import Application.*;
import Boundaries.*;
import Controller.*;
import Entities.*;
import Enum.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ProjectFlatsDetails {
	private Map<Integer, FlatType> flatIDxflatType;
    private Map<Integer, Boolean> flatIDxavail;
    private Map<FlatType, Integer> flatTypexcount;
    private Project project;

	public ProjectFlatsDetails(Project project) {
		this.project = project;
	    this.flatIDxflatType = new HashMap<>();
	    this.flatIDxavail = new HashMap<>();
	    this.flatTypexcount = new HashMap<>();
	    for (FlatType type : FlatType.values()) {
	    	flatTypexcount.put(type, 0);
		}
	}

	public void initialiseFlatDetails(Map<FlatType, Integer> flatDetails) {
		int flatID = 1;
		flatDetails.forEach((type, count) -> {
        	flatTypexcount.put(type, count);
            for (int i = 0; i < count; i++) {
            	flatIDxflatType.put(flatID, type);
            	flatIDxavail.put(flatID, true);
                flatID++;
            }
        });
		
	}
	
	public int bookFlat(FlatType type) {
        return flatIDxflatType.entrySet().stream()
        		//Find first entry of flatID of specified FlatType and see whether its avail
                .filter(entry -> entry.getValue() == type && flatIDxavail.get(entry.getKey()))
                .findFirst()
                .map(entry -> {
                    int flatId = entry.getKey();
                    flatIDxavail.put(flatId, false);  // Mark flat as booked
                    flatTypexcount.put(type, flatTypexcount.get(type) - 1);  // Decrease available count
                    project.updateFlatTypeCount(type, flatTypexcount.get(type));  // Update project
                    return flatId;
                })
                .orElseThrow(null);
    }
	
	public int getAvailableFlatCount(FlatType type) {
        return flatTypexcount.getOrDefault(type, 0);
    }
	
	public int getTotalFlatCount(FlatType type) {
        return (int) flatIDxflatType.values().stream()
                .filter(flatType -> flatType == type)
                .count();  // Count how many flats are of the specified type
    }

	 public FlatType getFlatType(int flatId) {
	        return flatIDxflatType.get(flatId);
	    }
	 
	 public boolean isFlatAvailable(int flatId) {
	        return flatIDxavail.getOrDefault(flatId, false);
	    }
	 
	 public Map<FlatType, Integer> getAvailableFlatCounts() {
	        return new HashMap<>(flatTypexcount);
	    }
}
