package com.example.jason.jason_workshop_3.Presenter.Presenter_Feature_Main.PWeeklyFitness;

/**
 * Created by jason on 24/06/2016.
 */
public class Exercise {
    public String id;
    public String Name;
    public String AmountofStep;
    public String weigh;

    public Exercise(String id, String name, String amountofStep, String weigh) {
        this.id = id;
        Name = name;
        AmountofStep = amountofStep;
        this.weigh = weigh;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAmountofStep() {
        return AmountofStep;
    }

    public void setAmountofStep(String amountofStep) {
        AmountofStep = amountofStep;
    }

    public String getWeigh() {
        return weigh;
    }

    public void setWeigh(String weigh) {
        this.weigh = weigh;
    }
}
