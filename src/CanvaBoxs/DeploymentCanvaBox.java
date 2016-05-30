package CanvaBoxs;

import java.util.ArrayList;

import Canvas.D_Component;
import Canvas.D_Database;
import Canvas.D_Device;
import Canvas.D_File;
import Canvas.D_Protocal;
import Canvas.D_Software;
import Canvas.D_System;
import Database.ToolHandler;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class DeploymentCanvaBox extends CanvasPane {
	// Hardware
	private ArrayList<D_Device> devices;
	private D_Device device;
	private boolean isDevice;

	// Software
	private ArrayList<D_Software> softwares;
	private D_Software software;
	private boolean isSoftware;

	// Database
	private ArrayList<D_Database> databases;
	private D_Database database;
	private boolean isDatabase;

	// Protocol
	private ArrayList<D_Protocal> protocols;
	private D_Protocal protocol;
	private boolean isProtocol;

	// File
	private ArrayList<D_File> files;
	private D_File file;
	private boolean isFile;

	// Component
	private ArrayList<D_Component> components;
	private D_Component component;
	private boolean isComponent;

	// System
	private ArrayList<D_System> systems;
	private D_System system;
	private boolean isSystem;

	public DeploymentCanvaBox() {
		devices = new ArrayList<D_Device>();
		softwares = new ArrayList<D_Software>();
		databases = new ArrayList<D_Database>();
		protocols = new ArrayList<D_Protocal>();
		files = new ArrayList<D_File>();
		components = new ArrayList<D_Component>();
		systems = new ArrayList<D_System>();
		setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler = new ToolHandler();
				String colorS = toolHandler.getColor();
				String tool = toolHandler.getTool();// Check Tool From
													// ToolHandler.xml
				color = Color.web(colorS); // Dynamic color from ToolHander.xml

				switch (tool) {
				case "Deployment_Hardware":
					device = new D_Device(e.getX(), e.getY(), color);
					isDevice = true;
					getChildren().addAll(device.getShape(), device, device.getLabel(), device.getData(),
							device.getText(false));
					break;
				case "Deployment_Software":
					software = new D_Software(e.getX(), e.getY(), color);
					isSoftware = true;
					getChildren().addAll(software.getShape(), software, software.getLabel(), software.getData(),
							software.getText(false));
					break;
				case "Deployment_Database":
					database = new D_Database(e.getX(), e.getY(), color);
					isDatabase = true;
					getChildren().addAll(database.getShape(), database, database.getLabel(), database.getData(),
							database.getText(false));
					break;
				case "Deployment_Protocol":
					protocol = new D_Protocal(e.getX(), e.getY(), e.getX(), e.getY(), color);
					isProtocol = true;
					getChildren().addAll(protocol);
					break;
				case "Deployment_File":
					file = new D_File(e.getX(), e.getY(), color);
					isFile = true;
					getChildren().addAll(file, file.getLabel(), file.getData(),file.getText(false));
					break;
				case "Deployment_Component":
					component = new D_Component(e.getX(), e.getY(), color);
					isComponent = true;
					getChildren().addAll(component, component.getLabel(), component.getData(),
							component.getText(false));
					break;
				case "Deployment_System":
					system = new D_System(e.getX(), e.getY(), color);
					isSystem = true;
					getChildren().addAll(system, system.getLabel(),system.getText(false));
					break;
				}

			}
		});

		setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (isDevice) {
					device.setX(e.getX());
					device.setY(e.getY());
				}
				if (isSoftware) {
					software.setX(e.getX());
					software.setY(e.getY());
				}
				if (isDatabase) {
					database.setX(e.getX());
					database.setY(e.getY());
				}
				if (isProtocol) {
					protocol.setEndX(e.getX());
					protocol.setEndY(e.getY());
				}
				if (isFile) {
					file.setX(e.getX());
					file.setY(e.getY());
				}
				if (isComponent) {
					component.setX(e.getX());
					component.setY(e.getY());
				}
				if (isSystem) {
					system.setX(e.getX());
					system.setY(e.getY());
				}
			}
		});

		setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (isDevice) {
					devices.add(device);
					isDevice = false;
				}
				if (isSoftware) {
					softwares.add(software);
					isSoftware = false;
				}
				if (isDatabase) {
					databases.add(database);
					isDatabase = false;
				}
				if (isProtocol) {
					getChildren().remove(protocol);
					if (protocol.filterLine()) {
						getChildren().addAll(protocol.getL1(), protocol.getL2(), protocol.getL3(), protocol.getNode1(),
								protocol.getNode2(), protocol.getStartNode(), protocol.getEndNode());
						protocols.add(protocol);
					}
					isProtocol = false;
				}
				if (isFile) {
					files.add(file);
					isFile = false;
				}
				if (isComponent) {
					components.add(component);
					isComponent = false;
				}
				if (isSystem) {
					systems.add(system);
					isSystem = false;
				}
				toolHandler.setTool("");
			}
		});

	}

}
