package Main;

import java.io.FileInputStream;

import Database.ToolHandler;
import Library.MyImageView;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class ToolBar extends VBox {
	private ToolHandler toolHandler;
	private ColorPicker color;
	private DropShadow shape;

	// UseCase Image View
	MyImageView actorV;
	MyImageView actionV;
	MyImageView boxV;
	MyImageView processV;
	MyImageView extendV;
	MyImageView includeV;
	MyImageView typeV;
	///////////////////////

	// Object Image View
	MyImageView objectV;
	MyImageView linkV;
	//////////////////////

	// Sequence Image View
	MyImageView roleV;
	MyImageView actV;
	MyImageView actNV;
	MyImageView actDV;
	MyImageView actSV;
	//////////////////////

	// Class Image View
	MyImageView classV;
	MyImageView aclassV;
	MyImageView iclassV;
	MyImageView depenV;
	MyImageView assoV;
	MyImageView aggV;
	MyImageView comV;
	MyImageView ihnV;
	//////////////////////

	// StateChart Image View
	MyImageView fstateV;
	MyImageView istateV;
	MyImageView hstateV;
	MyImageView stateV;
	MyImageView sstateV;
	MyImageView tranV;
	////////////////////////

	// Activity Image View
	MyImageView initNodeV;
	MyImageView endNodeV;
	MyImageView astateV;
	MyImageView edgeV;
	MyImageView mergeV;
	MyImageView timeV;
	MyImageView regionV;
	///////////////////////

	// Component Image View
	MyImageView compV;
	MyImageView depV;
	MyImageView souceV;
	MyImageView sCompV;
	MyImageView packageV;
	MyImageView libV;
	///////////////////////

	// Development Image View
	MyImageView hardV;
	MyImageView softV;
	MyImageView dbV;
	MyImageView proV;
	MyImageView fileV;
	MyImageView cV;
	MyImageView sysV;
	//////////////////////////

	public ToolBar(int type) {
		shape = new DropShadow();
		shape.setOffsetX(5);
		shape.setOffsetY(5);
		shape.setRadius(5);

		toolHandler = new ToolHandler();
		toolHandler.setTool("");
		setVisible(false);
		setPrefHeight(0);
		setMinHeight(0);
		color = new ColorPicker();
		color.setPrefWidth(50);
		Color defaultColor = Color.web(toolHandler.getColor());
		color.setValue(defaultColor);
		color.setOnAction(e -> {
			toolHandler.setColor(color.getValue().toString());
		});

		switch (type) {

		case 1: // Use_Case
			getChildren().addAll(UseCaseToolBox());
			break;
		case 2:// Object
			getChildren().addAll(ObjectToolBox());
			break;
		case 3:// Sequence
			getChildren().addAll(SequenceToolBox());
			break;
		case 4:// Collaboration
			getChildren().addAll(CollaborationToolBox());
			break;
		case 5:// Class
			getChildren().addAll(ClassToolBox());
			break;
		case 6:// State Chart
			getChildren().addAll(StateChartToolBox());
			break;
		case 7:// Activity
			getChildren().addAll(ActivityToolBox());
			break;
		case 8:// Component
			getChildren().addAll(ComponentToolBox());
			break;
		case 9:// Deployment
			getChildren().addAll(DeploymentToolBox());
			break;
		}

	}

	public void slideHide() {

		// Create an animation to hide the panel.
		final Animation hidePanel = new Transition() {
			{
				setCycleDuration(Duration.millis(250));
			}

			@Override
			protected void interpolate(double frac) {
				final double size = 200 * (1.0 - frac);
				setPrefHeight(size);
			}
		};

		hidePanel.onFinishedProperty().set(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				setVisible(false);
			}
		});
		if (isVisible()) {
			hidePanel.play();
		}

	}

	public void slideShow() {
		// Create an animation to show the panel.
		final Animation showPanel = new Transition() {
			{
				setCycleDuration(Duration.millis(250));
			}

			@Override
			protected void interpolate(double frac) {
				final double size = 200 * frac;
				setPrefHeight(size);
			}
		};

		showPanel.onFinishedProperty().set(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
			}
		});
		if (!isVisible()) {
			setVisible(true);
			showPanel.play();
		}
	}

	public VBox UseCaseToolBox() {
		VBox btnP = new VBox();
		btnP.setSpacing(10);
		btnP.setAlignment(Pos.CENTER_LEFT);
		Label head = new Label("UseCase Tools");
		head.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		head.setStyle("-fx-padding:0 0 20 0;");

		Image actor, action, box, process, extend, include, type;
		actorV = new MyImageView();
		actionV = new MyImageView();
		boxV = new MyImageView();
		processV = new MyImageView();
		extendV = new MyImageView();
		includeV = new MyImageView();
		typeV = new MyImageView();

		Button colorB = new Button("Color");
		colorB.setPrefHeight(50);
		colorB.setPrefWidth(50);

		try {
			actor = new Image(new FileInputStream("Resources/Icons/UseCaseIcon/Actor.png"));
			action = new Image(new FileInputStream("Resources/Icons/UseCaseIcon/Action.png"));
			box = new Image(new FileInputStream("Resources/Icons/UseCaseIcon/Box.png"));
			process = new Image(new FileInputStream("Resources/Icons/UseCaseIcon/Process.png"));
			extend = new Image(new FileInputStream("Resources/Icons/UseCaseIcon/Extend.png"));
			include = new Image(new FileInputStream("Resources/Icons/UseCaseIcon/Include.png"));
			type = new Image(new FileInputStream("Resources/Icons/UseCaseIcon/Type.png"));
			actorV.setImage(actor);
			actionV.setImage(action);
			boxV.setImage(box);
			processV.setImage(process);
			extendV.setImage(extend);
			includeV.setImage(include);
			typeV.setImage(type);
		} catch (Exception e) {
			e.printStackTrace();
		}

		actorV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("UseCase_Actor");
				ClearUseCaseToolShape();
				actorV.setEffect(shape);
			}
		});
		actionV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("UseCase_Action");
				ClearUseCaseToolShape();
				actionV.setEffect(shape);
			}
		});
		boxV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("UseCase_Box");
				ClearUseCaseToolShape();
				boxV.setEffect(shape);
			}
		});
		processV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("UseCase_Process");
				ClearUseCaseToolShape();
				processV.setEffect(shape);
			}
		});
		extendV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("UseCase_Extend");
				ClearUseCaseToolShape();
				extendV.setEffect(shape);
			}
		});

		includeV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("UseCase_Include");
				ClearUseCaseToolShape();
				includeV.setEffect(shape);
			}
		});
		typeV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("UseCase_Type");
				ClearUseCaseToolShape();
				typeV.setEffect(shape);
			}
		});

		btnP.getChildren().addAll(actorV, actionV, boxV, processV, extendV, includeV, typeV, color);
		return btnP;
	}

	public VBox ObjectToolBox() {
		VBox btnP = new VBox();
		btnP.setSpacing(10);
		btnP.setAlignment(Pos.CENTER_LEFT);

		Image object, link;
		objectV = new MyImageView();
		linkV = new MyImageView();
		try {
			object = new Image(new FileInputStream("Resources/Icons/Object/Object.png"));
			link = new Image(new FileInputStream("Resources/Icons/Object/Link.png"));
			objectV.setImage(object);
			linkV.setImage(link);
		} catch (Exception e) {
			e.printStackTrace();
		}

		objectV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("ObjectD_Object");
				ClearObjectToolShape();
				objectV.setEffect(shape);
			}
		});

		linkV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("ObjectD_link");
				ClearObjectToolShape();
				linkV.setEffect(shape);
			}
		});

		btnP.getChildren().addAll(objectV, linkV, color);
		return btnP;
	}

	public VBox SequenceToolBox() {
		VBox btnP = new VBox();
		btnP.setSpacing(10);
		btnP.setAlignment(Pos.CENTER_LEFT);
		Image crole, act, actN, actD, actS;
		roleV = new MyImageView();
		actV = new MyImageView();
		actNV = new MyImageView();
		actDV = new MyImageView();
		actSV = new MyImageView();
		try {
			crole = new Image(new FileInputStream("Resources/Icons/Sequence/Role.png"));
			act = new Image(new FileInputStream("Resources/Icons/Sequence/Activation.png"));
			actN = new Image(new FileInputStream("Resources/Icons/Sequence/NActivation.png"));
			actD = new Image(new FileInputStream("Resources/Icons/Sequence/DActivation.png"));
			actS = new Image(new FileInputStream("Resources/Icons/Sequence/SActivation.png"));
			roleV.setImage(crole);
			actV.setImage(act);
			actNV.setImage(actN);
			actDV.setImage(actD);
			actSV.setImage(actS);
		} catch (Exception e) {
			e.printStackTrace();
		}

		roleV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("Sequence_Role");
				ClearSequenceToolShape();
				roleV.setEffect(shape);
			}
		});
		actV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("Sequence_ANormal");
				ClearSequenceToolShape();
				actV.setEffect(shape);
			}
		});
		actNV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("Sequence_ANObject");
				ClearSequenceToolShape();
				actNV.setEffect(shape);
			}
		});

		actDV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("Sequence_ADObject");
				ClearSequenceToolShape();
				actDV.setEffect(shape);
			}
		});

		actSV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("Sequence_ASLoop");
				ClearSequenceToolShape();
				actSV.setEffect(shape);
			}
		});

		btnP.getChildren().addAll(roleV, actV, actNV, actDV, actSV, color);
		return btnP;
	}

	public VBox CollaborationToolBox() {
		VBox btnP = new VBox();
		btnP.setSpacing(10);
		btnP.setAlignment(Pos.CENTER_LEFT);
		ToggleButton object = new ToggleButton("Object");
		ToggleButton link = new ToggleButton("Link");

		ToggleGroup group = new ToggleGroup();
		object.setToggleGroup(group);
		link.setToggleGroup(group);

		object.setOnAction(e -> {
			toolHandler.setTool("Collaboration_Object");
		});
		link.setOnAction(e -> {
			toolHandler.setTool("Collaboration_Link");
		});

		btnP.getChildren().addAll(object, link);
		return btnP;
	}

	public VBox ClassToolBox() {
		VBox btnP = new VBox();
		btnP.setSpacing(10);
		btnP.setAlignment(Pos.CENTER_LEFT);

		Image cls, acls, icls, asso, agg, com, inh, depen;
		classV = new MyImageView();
		aclassV = new MyImageView();
		iclassV = new MyImageView();
		depenV = new MyImageView();
		assoV = new MyImageView();
		aggV = new MyImageView();
		comV = new MyImageView();
		ihnV = new MyImageView();
		try {
			cls = new Image(new FileInputStream("Resources/Icons/Class/Class.png"));
			acls = new Image(new FileInputStream("Resources/Icons/Class/AClass.png"));
			icls = new Image(new FileInputStream("Resources/Icons/Class/IClass.png"));
			asso = new Image(new FileInputStream("Resources/Icons/Class/Association.png"));
			agg = new Image(new FileInputStream("Resources/Icons/Class/Aggregation.png"));
			com = new Image(new FileInputStream("Resources/Icons/Class/Composition.png"));
			inh = new Image(new FileInputStream("Resources/Icons/Class/Inheritance.png"));
			depen = new Image(new FileInputStream("Resources/Icons/Class/Dependency.png"));
			classV.setImage(cls);
			aclassV.setImage(acls);
			iclassV.setImage(icls);
			assoV.setImage(asso);
			aggV.setImage(agg);
			comV.setImage(com);
			ihnV.setImage(inh);
			depenV.setImage(depen);
		} catch (Exception e) {
			e.printStackTrace();
		}

		classV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("Class_Class");
				ClearClassToolShape();
				classV.setEffect(shape);
			}
		});
		aclassV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("Class_AbstractClass");
				ClearClassToolShape();
				aclassV.setEffect(shape);
			}
		});
		iclassV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("Class_InterfaceClass");
				ClearClassToolShape();
				iclassV.setEffect(shape);
			}
		});

		assoV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("Class_Association");
				ClearClassToolShape();
				assoV.setEffect(shape);
			}
		});

		aggV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("Class_Aggregation");
				ClearClassToolShape();
				aggV.setEffect(shape);
			}
		});

		comV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("Class_Composition");
				ClearClassToolShape();
				comV.setEffect(shape);
			}
		});

		ihnV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("Class_Inheritance");
				ClearClassToolShape();
				ihnV.setEffect(shape);
			}
		});

		depenV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("Class_Dependency");
				ClearClassToolShape();
				depenV.setEffect(shape);
			}
		});

		btnP.getChildren().addAll(classV, aclassV, iclassV, assoV, aggV, comV, ihnV, depenV, color);
		return btnP;
	}

	public VBox StateChartToolBox() {
		VBox btnP = new VBox();
		btnP.setSpacing(10);
		btnP.setAlignment(Pos.CENTER_LEFT);

		Image fstate, istate, hstate, state, sstate, tran;
		fstateV = new MyImageView();
		istateV = new MyImageView();
		hstateV = new MyImageView();
		stateV = new MyImageView();
		sstateV = new MyImageView();
		tranV = new MyImageView();
		try {
			fstate = new Image(new FileInputStream("Resources/Icons/Statechart/FinalState.png"));
			istate = new Image(new FileInputStream("Resources/Icons/Statechart/InitState.png"));
			hstate = new Image(new FileInputStream("Resources/Icons/Statechart/HState.png"));
			sstate = new Image(new FileInputStream("Resources/Icons/Statechart/SubState.png"));
			state = new Image(new FileInputStream("Resources/Icons/Statechart/State.png"));
			tran = new Image(new FileInputStream("Resources/Icons/Statechart/Transition.png"));
			fstateV.setImage(fstate);
			istateV.setImage(istate);
			hstateV.setImage(hstate);
			stateV.setImage(state);
			sstateV.setImage(sstate);
			tranV.setImage(tran);
		} catch (Exception e) {
			e.printStackTrace();
		}

		istateV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("Statechart_InitState");
				ClearStateToolShape();
				istateV.setEffect(shape);
			}
		});
		fstateV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("Statechart_FinalState");
				ClearStateToolShape();
				fstateV.setEffect(shape);
			}
		});
		sstateV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("Statechart_Substate");
				ClearStateToolShape();
				sstateV.setEffect(shape);
			}
		});
		hstateV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("Statechart_HistoryState");
				ClearStateToolShape();
				hstateV.setEffect(shape);
			}
		});
		stateV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("Statechart_State");
				ClearStateToolShape();
				stateV.setEffect(shape);
			}
		});
		tranV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("Statechart_Transition");
				ClearStateToolShape();
				tranV.setEffect(shape);
			}
		});

		btnP.getChildren().addAll(istateV, fstateV, stateV, sstateV, hstateV, tranV, color);
		return btnP;
	}

	public VBox ActivityToolBox() {
		VBox btnP = new VBox();
		btnP.setSpacing(10);
		btnP.setAlignment(Pos.CENTER_LEFT);

		Image init, end, astate, edge, merge, time, region;
		initNodeV = new MyImageView();
		endNodeV = new MyImageView();
		astateV = new MyImageView();
		edgeV = new MyImageView();
		mergeV = new MyImageView();
		timeV = new MyImageView();
		regionV = new MyImageView();
		try {
			init = new Image(new FileInputStream("Resources/Icons/Activity/Start.png"));
			end = new Image(new FileInputStream("Resources/Icons/Activity/End.png"));
			astate = new Image(new FileInputStream("Resources/Icons/Activity/Action.png"));
			edge = new Image(new FileInputStream("Resources/Icons/Activity/Edge.png"));
			merge = new Image(new FileInputStream("Resources/Icons/Activity/Merge.png"));
			time = new Image(new FileInputStream("Resources/Icons/Activity/Time.png"));
			region = new Image(new FileInputStream("Resources/Icons/Activity/Region.png"));
			initNodeV.setImage(init);
			endNodeV.setImage(end);
			astateV.setImage(astate);
			edgeV.setImage(edge);
			mergeV.setImage(merge);
			timeV.setImage(time);
			regionV.setImage(region);
		} catch (Exception e) {
			e.printStackTrace();
		}
		initNodeV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("Activity_InitNode");
				ClearActivityToolShape();
				initNodeV.setEffect(shape);
			}
		});
		endNodeV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("Activity_EndNode");
				ClearActivityToolShape();
				endNodeV.setEffect(shape);
			}
		});
		astateV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("Activity_Action");
				ClearActivityToolShape();
				astateV.setEffect(shape);
			}
		});

		edgeV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("Activity_Edge");
				ClearActivityToolShape();
				edgeV.setEffect(shape);
			}
		});
		mergeV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("Activity_Merge");
				ClearActivityToolShape();
				mergeV.setEffect(shape);
			}
		});
		timeV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("Activity_Time");
				ClearActivityToolShape();
				timeV.setEffect(shape);
			}
		});
		regionV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("Activity_Region");
				ClearActivityToolShape();
				regionV.setEffect(shape);
			}
		});

		btnP.getChildren().addAll(initNodeV, endNodeV, astateV, edgeV, mergeV, timeV, regionV, color);
		return btnP;
	}

	public VBox ComponentToolBox() {
		VBox btnP = new VBox();
		btnP.setSpacing(10);
		btnP.setAlignment(Pos.CENTER_LEFT);

		Image comp, dep, souce, sComp, pack, lib;
		compV = new MyImageView();
		depV = new MyImageView();
		souceV = new MyImageView();
		sCompV = new MyImageView();
		packageV = new MyImageView();
		libV = new MyImageView();
		try {
			comp = new Image(new FileInputStream("Resources/Icons/Component/Component.png"));
			dep = new Image(new FileInputStream("Resources/Icons/Component/Depen.png"));
			souce = new Image(new FileInputStream("Resources/Icons/Component/Source.png"));
			sComp = new Image(new FileInputStream("Resources/Icons/Component/SComponent.png"));
			pack = new Image(new FileInputStream("Resources/Icons/Component/Package.png"));
			lib = new Image(new FileInputStream("Resources/Icons/Component/Library.png"));
			compV.setImage(comp);
			depV.setImage(dep);
			souceV.setImage(souce);
			sCompV.setImage(sComp);
			packageV.setImage(pack);
			libV.setImage(lib);
		} catch (Exception e) {
			e.printStackTrace();
		}

		compV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("Component_Component");
				ClearComponentToolShape();
				compV.setEffect(shape);
			}
		});

		depV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("Component_Dependence");
				ClearComponentToolShape();
				depV.setEffect(shape);
			}
		});

		souceV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("Component_SArtefact");
				ClearComponentToolShape();
				souceV.setEffect(shape);
			}
		});

		sCompV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("Component_SComponent");
				ClearComponentToolShape();
				sCompV.setEffect(shape);
			}
		});
		packageV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("Component_Package");
				ClearComponentToolShape();
				packageV.setEffect(shape);
			}
		});

		libV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("Component_Library");
				ClearComponentToolShape();
				libV.setEffect(shape);
			}
		});

		btnP.getChildren().addAll(compV, depV, souceV, sCompV, packageV, libV, color);
		return btnP;
	}

	public VBox DeploymentToolBox() {
		VBox btnP = new VBox();
		btnP.setSpacing(10);
		btnP.setAlignment(Pos.CENTER_LEFT);

		Image hard, soft, db, pro, file, c, sys;
		hardV = new MyImageView();
		softV = new MyImageView();
		dbV = new MyImageView();
		proV = new MyImageView();
		fileV = new MyImageView();
		cV = new MyImageView();
		sysV = new MyImageView();
		try {
			hard = new Image(new FileInputStream("Resources/Icons/Development/Hardware.png"));
			soft = new Image(new FileInputStream("Resources/Icons/Development/Software.png"));
			db = new Image(new FileInputStream("Resources/Icons/Development/Database.png"));
			pro = new Image(new FileInputStream("Resources/Icons/Development/Protocol.png"));
			file = new Image(new FileInputStream("Resources/Icons/Development/File.png"));
			c = new Image(new FileInputStream("Resources/Icons/Development/Component.png"));
			sys = new Image(new FileInputStream("Resources/Icons/Development/System.png"));
			hardV.setImage(hard);
			softV.setImage(soft);
			dbV.setImage(db);
			proV.setImage(pro);
			fileV.setImage(file);
			cV.setImage(c);
			sysV.setImage(sys);
		} catch (Exception e) {
			e.printStackTrace();
		}

		hardV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("Deployment_Hardware");
				ClearDevelopmentToolShape();
				hardV.setEffect(shape);
			}
		});
		softV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("Deployment_Software");
				ClearDevelopmentToolShape();
				softV.setEffect(shape);
			}
		});
		dbV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("Deployment_Database");
				ClearDevelopmentToolShape();
				dbV.setEffect(shape);
			}
		});
		proV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("Deployment_Protocol");
				ClearDevelopmentToolShape();
				proV.setEffect(shape);
			}
		});
		fileV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("Deployment_File");
				ClearDevelopmentToolShape();
				fileV.setEffect(shape);
			}
		});
		cV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("Deployment_Component");
				ClearDevelopmentToolShape();
				cV.setEffect(shape);
			}
		});
		sysV.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler.setTool("Deployment_System");
				ClearDevelopmentToolShape();
				sysV.setEffect(shape);
			}
		});

		btnP.getChildren().addAll(hardV, softV, dbV, proV, fileV, cV, sysV, color);
		return btnP;
	}

	public void ClearUseCaseToolShape() {
		actorV.setEffect(null);
		actionV.setEffect(null);
		boxV.setEffect(null);
		processV.setEffect(null);
		extendV.setEffect(null);
		includeV.setEffect(null);
		typeV.setEffect(null);
	}

	public void ClearObjectToolShape() {
		objectV.setEffect(null);
		linkV.setEffect(null);
	}

	public void ClearSequenceToolShape() {
		roleV.setEffect(null);
		actV.setEffect(null);
		actNV.setEffect(null);
		actDV.setEffect(null);
		actSV.setEffect(null);
	}

	public void ClearClassToolShape() {
		classV.setEffect(null);
		aclassV.setEffect(null);
		iclassV.setEffect(null);
		assoV.setEffect(null);
		aggV.setEffect(null);
		comV.setEffect(null);
		ihnV.setEffect(null);
		depenV.setEffect(null);
	}

	public void ClearStateToolShape() {
		fstateV.setEffect(null);
		istateV.setEffect(null);
		hstateV.setEffect(null);
		stateV.setEffect(null);
		sstateV.setEffect(null);
		tranV.setEffect(null);
	}

	public void ClearActivityToolShape() {
		initNodeV.setEffect(null);
		endNodeV.setEffect(null);
		edgeV.setEffect(null);
		mergeV.setEffect(null);
		timeV.setEffect(null);
		regionV.setEffect(null);
	}

	public void ClearComponentToolShape() {
		compV.setEffect(null);
		depV.setEffect(null);
		souceV.setEffect(null);
		sCompV.setEffect(null);
		packageV.setEffect(null);
		libV.setEffect(null);
	}

	public void ClearDevelopmentToolShape() {
		hardV.setEffect(null);
		softV.setEffect(null);
		dbV.setEffect(null);
		proV.setEffect(null);
		fileV.setEffect(null);
		cV.setEffect(null);
		sysV.setEffect(null);
	}
}
