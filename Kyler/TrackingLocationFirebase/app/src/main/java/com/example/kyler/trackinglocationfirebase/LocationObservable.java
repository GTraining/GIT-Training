package com.example.kyler.trackinglocationfirebase;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by kyler on 22/07/2016.
 */
public abstract class LocationObservable<T> extends Observable {
    private Query mRef;
    private Class<T> mModelClass;
    private List<T> mModels;
    private List<String> mKeys;
    private ChildEventListener mListener;

    public LocationObservable(Query mRef, Class<T> mModelsClass){
        this.mRef = mRef;
        this.mModelClass = mModelsClass;
        mModels = new ArrayList<T>();
        mKeys = new ArrayList<String>();
        mListener = this.mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                T model = dataSnapshot.getValue(LocationObservable.this.mModelClass);
                String key = dataSnapshot.getKey();

                // Insert into the correct location, based on previousChildName
                if (previousChildName == null) {
                    mModels.add(0, model);
                    mKeys.add(0, key);
                } else {
                    int previousIndex = mKeys.indexOf(previousChildName);
                    int nextIndex = previousIndex + 1;
                    if (nextIndex == mModels.size()) {
                        mModels.add(model);
                        mKeys.add(key);
                    } else {
                        mModels.add(nextIndex, model);
                        mKeys.add(nextIndex, key);
                    }
                }
                notifyObservers();
                myNearBy(mModels);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                String key = dataSnapshot.getKey();
                T newModel = dataSnapshot.getValue(LocationObservable.this.mModelClass);
                int index = mKeys.indexOf(key);

                mModels.set(index, newModel);

                notifyObservers(mModels);
                myNearBy(mModels);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                int index = mKeys.indexOf(key);

                mKeys.remove(index);
                mModels.remove(index);

                notifyObservers(mModels);
                myNearBy(mModels);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                String key = dataSnapshot.getKey();
                T newModel = dataSnapshot.getValue(LocationObservable.this.mModelClass);
                int index = mKeys.indexOf(key);
                mModels.remove(index);
                mKeys.remove(index);
                if (previousChildName == null) {
                    mModels.add(0, newModel);
                    mKeys.add(0, key);
                } else {
                    int previousIndex = mKeys.indexOf(previousChildName);
                    int nextIndex = previousIndex + 1;
                    if (nextIndex == mModels.size()) {
                        mModels.add(newModel);
                        mKeys.add(key);
                    } else {
                        mModels.add(nextIndex, newModel);
                        mKeys.add(nextIndex, key);
                    }
                }
                notifyObservers(mModels);
                myNearBy(mModels);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("FirebaseListAdapter", "Listen was cancelled, no more updates will occur");
            }
        });
    }

    public void cleanup() {
        // We're being destroyed, let go of our mListener and forget about all of the mModels
        mRef.removeEventListener(mListener);
        mModels.clear();
        mKeys.clear();
    }

    protected abstract void myNearBy(List<T> models);
}
