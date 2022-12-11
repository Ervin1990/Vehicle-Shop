package org.makerminds.jcoaching.finalexam.console;

import java.util.List;
import java.util.Scanner;

import org.makerminds.jcoaching.finalexam.controller.VehicleFileManager;
import org.makerminds.jcoaching.finalexam.controller.VehicleShopPrinter;
import org.makerminds.jcoaching.finalexam.controller.VehicleShopProcessor;
import org.makerminds.jcoaching.finalexam.controller.VehicleTransformer;
import org.makerminds.jcoaching.finalexam.model.Vehicle;

/**
 * providing main method to start the application.
 * 
 * @author <Ervin Selishta>
 *
 */
public class VehicleShop {

	private static final String VEHICLE_LIST_PATH = "/vehicle-list.txt";

	public static void main(String[] args) {

		
		executeVehicleShopProcess();
	}

	private static void executeVehicleShopProcess() {
		// File reading
		VehicleFileManager vehicleFileManager = new VehicleFileManager(VEHICLE_LIST_PATH);
		List<String> vehicleDataAsStringList = vehicleFileManager.importVehiclesFromFile();

		// Tranformation into Vehicle Java Objects
		VehicleTransformer vehicleTransformer = new VehicleTransformer();
		List<Vehicle> vehicleList = vehicleTransformer.transformDataArrayToVehicleObjects(vehicleDataAsStringList);

		// Printing available vehicles
		VehicleShopPrinter vehicleShopPrinter = new VehicleShopPrinter();
		vehicleShopPrinter.printAvailableVehicles(vehicleList);

		// User interaction - selecting a vehicle to sell
		Scanner scanner = new Scanner(System.in);
		vehicleShopPrinter.printVehicleIdToSellRequest();
		int vehicleChosenId = scanner.nextInt();

		// Selling a vehicle
		VehicleShopProcessor vehicleShopProcessor = new VehicleShopProcessor();
		vehicleShopProcessor.sellVehicle(vehicleList, vehicleChosenId);

		vehicleFileManager.rewriteFile(vehicleList);

		vehicleShopPrinter.printVehicleSoldMessage(vehicleChosenId);
		vehicleShopPrinter.printAvailableVehicles(vehicleList);
		scanner.close();
		executeVehicleShopProcess();
	}
}
