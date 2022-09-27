package no.hiof.janaathm.model;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Airport{
    protected int id;
    protected String name;
    Boolean runwayInUse;
    Queue<Plane> landingQue = new LinkedList<Plane>();
    Queue<Plane> takeoffQue = new LinkedList<Plane>();
    public Airport(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Boolean getRunwayInUse() {
        return runwayInUse;
    }

    public void setRunwayInUse(Boolean runwayInUse) {
        this.runwayInUse = runwayInUse;
    }

    public Queue<Plane> getLandingQue() {
        return landingQue;
    }

    public void setLandingQue(Queue<Plane> landingQue) {
        this.landingQue = landingQue;
    }

    public Queue<Plane> getTakeoffQue() {
        return takeoffQue;
    }

    public void setTakeoffQue(Queue<Plane> takeoffQue) {
        this.takeoffQue = takeoffQue;
    }

    public void addLandingQue(Plane plane) {
        this.landingQue.add(plane);
    }

    public void addTakeoffQue(Plane plane) {
        this.takeoffQue.add(plane);
    }



}
