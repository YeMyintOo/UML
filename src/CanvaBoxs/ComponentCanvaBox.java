package CanvaBoxs;

import java.util.ArrayList;

import Canvas.CO_Artefact;
import Canvas.CO_Component;
import Canvas.CO_Depend;
import Canvas.CO_Library;
import Canvas.CO_Package;
import Canvas.CO_SComponent;
import Database.ToolHandler;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class ComponentCanvaBox extends Pane {
	// Only Components Can Draw
	private ToolHandler toolHandler;
	private Color color;

	// Component
	private ArrayList<CO_Component> components;
	private CO_Component component;
	private boolean isComponent;

	// Dependence
	private ArrayList<CO_Depend> depens;
	private CO_Depend depen;
	private boolean isDependence;

	// Artefact
	private ArrayList<CO_Artefact> artefacts;
	private CO_Artefact artefact;
	private boolean isArtefact;

	private ArrayList<CO_SComponent> scomponents;
	private CO_SComponent scomponent;
	private boolean isSComponent;

	// Package
	private ArrayList<CO_Package> packs;
	private CO_Package pack;
	private boolean isPackage;

	private ArrayList<CO_Library> librarys;
	private CO_Library library;
	private boolean isLibrary;

	public ComponentCanvaBox() {
		init();
		setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler = new ToolHandler();
				String colorS = toolHandler.getColor();
				String tool = toolHandler.getTool();// Check Tool From
													// ToolHandler.xml
				color = Color.web(colorS); // Dynamic color from ToolHander.xml

				switch (tool) {
				case "Component_Component":
					component = new CO_Component(e.getX(), e.getY(), color);
					isComponent = true;
					getChildren().addAll(component, component.getNode1(), component.getNode2());
					break;
				case "Component_Dependence":
					depen = new CO_Depend(e.getX(), e.getY(), e.getX(), e.getY(), color);
					isDependence = true;
					getChildren().addAll(depen);
					break;
				case "Component_SArtefact":
					artefact = new CO_Artefact(e.getX(), e.getY(), color);
					isArtefact = true;
					getChildren().addAll(artefact, artefact.getLabel());
					break;
				case "Component_SComponent":
					scomponent = new CO_SComponent(e.getX(), e.getY(), color);
					isSComponent = true;
					getChildren().addAll(scomponent, scomponent.getLabel(), scomponent.getNode1(),
							scomponent.getNode2());
					break;
				case "Component_Package":
					pack = new CO_Package(e.getX(), e.getY(), color);
					isPackage = true;
					getChildren().addAll(pack, pack.getLabel(), pack.getNode1());
					break;
				case "Component_Library":
					library = new CO_Library(e.getX(), e.getY(), 400, 200, color, Color.LIGHTGRAY);
					isLibrary = true;
					getChildren().addAll(library, library.getLabel(),library.getNode1(),library.getNode2());
					break;
				}

			}
		});

		setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (isComponent) {
					component.setX(e.getX());
					component.setY(e.getY());
				}
				if (isArtefact) {
					artefact.setX(e.getX());
					artefact.setY(e.getY());
				}
				if (isSComponent) {
					scomponent.setX(e.getX());
					scomponent.setY(e.getY());
				}
				if (isPackage) {
					pack.setX(e.getX());
					pack.setY(e.getY());
				}
				if (isLibrary) {
					library.setX(e.getX());
					library.setY(e.getY());
				}
				if (isDependence) {
					depen.setEndX(e.getX());
					depen.setEndY(e.getY());
				}
			}
		});

		setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (isComponent) {
					components.add(component);
					isComponent = false;
				}
				if (isArtefact) {
					artefacts.add(artefact);
					isArtefact = false;
				}
				if (isSComponent) {
					scomponents.add(scomponent);
					isSComponent = false;
				}
				if (isPackage) {
					packs.add(pack);
					isPackage = false;
				}
				if (isLibrary) {
					librarys.add(library);
					isLibrary = false;
				}
				if (isDependence) {
					depen.recalculatePoint();
					getChildren().addAll(depen.getTop(), depen.getBot());
					depens.add(depen);
					isDependence = false;
				}
			}
		});

	}

	public void init() {
		components = new ArrayList<CO_Component>();
		depens = new ArrayList<CO_Depend>();
		artefacts = new ArrayList<CO_Artefact>();
		scomponents = new ArrayList<CO_SComponent>();
		packs = new ArrayList<CO_Package>();
		librarys = new ArrayList<CO_Library>();
	}
}
