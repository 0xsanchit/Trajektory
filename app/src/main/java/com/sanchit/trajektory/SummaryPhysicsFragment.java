package com.sanchit.trajektory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class SummaryPhysicsFragment extends Fragment {

    List<Subtopics> summary1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_summary_physics, container, false);

        summary1 = new ArrayList<>();
        summary1.add(new Subtopics("Units , Dimensions and Errors","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/P%20UNITS%2C%20DIMENSIONS%20_%20MEASUREMENT.pdf?alt=media&token=6965f2aa-9ed1-4402-870c-a90e51db25d6"));
        summary1.add(new Subtopics("Kinematics","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/P%20KINEMATICS.pdf?alt=media&token=1c4277a9-a5a7-4b3d-8e6a-772fb78cd170"));
        summary1.add(new Subtopics("Laws Of Motion","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/P%20NEWTON_S%20LAWS%20OF%20MOTION.pdf?alt=media&token=5306d7fb-5fac-4516-a735-6db72a771d7c"));
        summary1.add(new Subtopics("Work , Energy and Power ","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/P%20WORK%2C%20ENERGY%2C%20POWER.pdf?alt=media&token=c3e4ef44-3c9a-4013-a4a5-5643cbd5ed2a"));
        summary1.add(new Subtopics("Rotational Motion","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/P%20DYNAMICS%20OF%20UNIFORM%20CIRCULAR%20MOTION.pdf?alt=media&token=208220d5-2354-4cea-858a-46999472bdf0"));
        summary1.add(new Subtopics("Gravitation","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/P%20GRAVITATION.pdf?alt=media&token=192fd4a3-7441-4f19-8f30-bc740406f195"));
        summary1.add(new Subtopics("Thermodynamics","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/P%20THERMODYNAMICS.pdf?alt=media&token=29c07fb3-3b9b-4b39-9bcd-79f030df4dc2"));
        summary1.add(new Subtopics("Kinetic Theory Of Gases","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/P%20KINETIC%20THEORY%20OF%20GASES.pdf?alt=media&token=a54025db-5583-475d-b370-919b6b0c5b53"));
        summary1.add(new Subtopics("Elasticity","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/P%20ELASTICITY.pdf?alt=media&token=719dc5df-545f-46a5-a63c-1753cc21c8f8"));
        summary1.add(new Subtopics("Calorimetry","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/P%20CALORIMETRY%20AND%20CHANGE%20OF%20STATE.pdf?alt=media&token=0ca6b4e2-6668-4d6a-8d03-924a03205739"));
        summary1.add(new Subtopics("Heat Transfer","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/P%20HEAT%20TRANSFER.pdf?alt=media&token=a285c401-3f4e-4ad9-aa5f-591e3ccd45a3"));
        summary1.add(new Subtopics("Heat And Temperature","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/P%20HEAT%20AND%20TEMPERATURE.pdf?alt=media&token=b12e5a1e-c302-48da-9b29-5b0e534a1680"));
        summary1.add(new Subtopics("Thermal Expansion","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/P%20THERMAL%20EXPANSION.pdf?alt=media&token=7f9ca48b-c962-4141-8a69-707bb7125a88"));
        summary1.add(new Subtopics("Fluids","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/P%20FLUIDS.pdf?alt=media&token=ef712fb9-7322-4ed9-a15a-dccd2c0bcdab"));
        summary1.add(new Subtopics("Oscillations , Simple Harmonic Motion","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/P%20OSCILLATION%2C%20SIMPLE%20HARMONIC%20MOTION.pdf?alt=media&token=f7e88751-b0fb-477d-aaed-080f18a0166e"));
        summary1.add(new Subtopics("Waves - Wave Motion","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/P%20WAVES-WAVE%20MOTION.pdf?alt=media&token=63722598-c2d0-4b07-a1f1-24be150b7f20"));
        summary1.add(new Subtopics("Superposition of Waves","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/P%20WAVES-SUPERPOSITION%20OF%20WAVES.pdf?alt=media&token=669ddec4-a2a9-40cd-be19-a7bc1c31fdfe"));
        summary1.add(new Subtopics("Optics","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/P%20OPTICS-GEOMETRICAL%20_%20PHYSICAL.pdf?alt=media&token=451ec779-7784-4425-9a1a-e32654309fa0"));
        summary1.add(new Subtopics("Electrostatics","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/P%20ELECTROSTATICS.pdf?alt=media&token=49d78dcb-25ff-484f-9230-7a61815ba48a"));
        summary1.add(new Subtopics("Current Electricity","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/P%20CURRENT%20ELECTRICITY.pdf?alt=media&token=72382f80-fa88-42bc-80fe-a8f6b7783736"));
        summary1.add(new Subtopics("Magnetism","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/P%20MAGNETICS.pdf?alt=media&token=b0ef836f-0567-4694-a43c-7bee85619d69"));
        summary1.add(new Subtopics("Electromagnetic Induction","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/P%20ELECTROMAGNETIC%20INDUCTION.pdf?alt=media&token=7bd785e8-f8d2-4f80-b70c-70c1967e8286"));
        summary1.add(new Subtopics("Alternating Current","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/P%20ALTERNATING%20CURRENT.pdf?alt=media&token=81f1f4cb-fcf1-49b4-8afb-d90f0fa6e6e2"));
        summary1.add(new Subtopics("Electromagnetic Waves","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/P%20ELECTROMAGNETIC%20WAVE.pdf?alt=media&token=f411aaed-9cf2-40a6-9748-d7d582ecd129"));


        RecyclerView myrv = (RecyclerView) view.findViewById(R.id.recyclerview3);
        RecyclerViewAdapter1 myAdapter = new RecyclerViewAdapter1(getContext(),summary1);
        myrv.setLayoutManager(new GridLayoutManager(getContext(),1));
        myrv.setAdapter(myAdapter);


        return view;
    }
}