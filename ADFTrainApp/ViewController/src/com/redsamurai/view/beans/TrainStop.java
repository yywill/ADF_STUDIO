package com.redsamurai.view.beans;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import oracle.adf.controller.ControllerContext;
import oracle.adf.controller.TaskFlowContext;
import oracle.adf.controller.TaskFlowTrainModel;
import oracle.adf.controller.TaskFlowTrainStopModel;
import oracle.adf.controller.ViewPortContext;


public class TrainStop {
    private String trainStopImage;
    
    public TrainStop() {
        super();
    }
    
    public boolean isCurrentTab() {
        //get access to the JSF context classes
        FacesContext fctx = FacesContext.getCurrentInstance();
        ELContext elctx = fctx.getELContext();
        Application app = fctx.getApplication();
        ExpressionFactory expressionFactory = app.getExpressionFactory();
        //trainNode is the name of the variable attribute defined in af:navigationPane
        ValueExpression ve = expressionFactory.createValueExpression(elctx,"#{trainNode}", Object.class);
        //get the rendered stop's viewActivity
        TaskFlowTrainStopModel renderedTrainNode = (TaskFlowTrainStopModel)ve.getValue(elctx);
        //get current train stop to cpmpare it with the current "rendered" train stop
        ControllerContext controllerContext = ControllerContext.getInstance();
        ViewPortContext currentViewPortCtx = controllerContext.getCurrentViewPort();
        TaskFlowContext taskFlowCtx = currentViewPortCtx.getTaskFlowContext();
        TaskFlowTrainModel taskFlowTrainModel = taskFlowCtx.getTaskFlowTrainModel();
        //the train stop that is rendered in the train bar
        String renderedActivityId = renderedTrainNode.getLocalActivityId();
        //the train's current stop: the state
        TaskFlowTrainStopModel currentStop = taskFlowTrainModel.getCurrentStop();
        
        if (renderedActivityId.equalsIgnoreCase(currentStop.getLocalActivityId())) {
            return true;
        }
        return false;
    }

    public void setTrainStopImage(String trainStopImage) {
        this.trainStopImage = trainStopImage;
    }

    public String getTrainStopImage() {
        FacesContext fctx = FacesContext.getCurrentInstance();
        ELContext elctx = fctx.getELContext();
        Application app = fctx.getApplication();
        ExpressionFactory expressionFactory = app.getExpressionFactory();
        ValueExpression ve = expressionFactory.createValueExpression(elctx,"#{trainNode}", Object.class);
        TaskFlowTrainStopModel renderedTrainNode = (TaskFlowTrainStopModel)ve.getValue(elctx);
        String renderedActivityId = renderedTrainNode.getLocalActivityId();
        
        trainStopImage = "/images/" + renderedActivityId + ".png";
        
        return trainStopImage;
    }
}
