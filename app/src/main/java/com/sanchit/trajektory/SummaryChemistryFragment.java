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

public class SummaryChemistryFragment extends Fragment {

    List<Subtopics> summary2;
    List<Subtopics> summary3;
    List<Subtopics> summary4;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_summary_chemistry, container, false);


        summary2=new ArrayList<>();

        summary2.add(new Subtopics("Chemical Bonding","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/C%20CHEMICAL%20BONDING.pdf?alt=media&token=a31ce8c8-41ba-424e-95e1-53f264e59268"));
        summary2.add(new Subtopics("Coordination Chemistry","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/C%20COORDINATION%20CHEMISTRY.pdf?alt=media&token=22c20541-54e2-4d1e-9826-837e3bc590ea"));
        summary2.add(new Subtopics("D and F Block Elements","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/C%20D-F%20BLOCK%20ELEMENTS.pdf?alt=media&token=0fe2a5e2-249b-4129-842d-678378396f7b"));
        summary2.add(new Subtopics("Hydrogen and Hydrides","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/C%20HYDROGEN%20AND%20HYDRIDES.pdf?alt=media&token=c27d7a1d-036f-4188-9c53-238d4dfd436b"));
        summary2.add(new Subtopics("P Block Elements","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/C%20P%20BLOCK%20ELEMENTS.pdf?alt=media&token=94617b34-3e2c-456a-9267-d47b4018b199"));
        summary2.add(new Subtopics("Periodic Table","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/C%20PERIODIC%20TABLE.pdf?alt=media&token=98c9bd75-b87d-4eff-b659-93df1d991bb2"));
        summary2.add(new Subtopics("Redox Reactions","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/C%20REDOX%20REACTIONS.pdf?alt=media&token=3365d7eb-806b-4e56-a9f7-412c0e93d946"));

        RecyclerView myrv = (RecyclerView) view.findViewById(R.id.recyclerview6);
        RecyclerViewAdapter1 myAdapter = new RecyclerViewAdapter1(getContext(),summary2);
        myrv.setLayoutManager(new GridLayoutManager(getContext(),1));
        myrv.setAdapter(myAdapter);



        summary3=new ArrayList<>();

        summary3.add(new Subtopics("General Organic Chemistry","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/ORGANIC%20CHEMISTRY-%E2%9C%B3%20GENERAL%20ORGANIC%20CHEMISTRY.pdf?alt=media&token=4fbe9c43-5f1d-4893-956b-a338c229d07f"));
        summary3.add(new Subtopics("Reaction Mechanism","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/ORGANIC%20CHEMISTRY-ALL%20REACTION%20MECHANISMS.pdf?alt=media&token=92f1d57b-94fe-4e4a-a849-0db4e3da7c5a"));
        summary3.add(new Subtopics("Common Distinction Tests","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/ORGANIC%20CHEMISTRY-COMMON%20DISTINCTION%20TESTS.pdf?alt=media&token=c9b0c9e7-05a3-4dc1-a47f-753139bafe52"));
        summary3.add(new Subtopics("Conversion Schemes","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/ORGANIC%20CHEMISTRY-CONVERSION%20SCHEMES.pdf?alt=media&token=beeae38e-943d-4965-b7bf-e0e6a3fdb206"));
        summary3.add(new Subtopics("Named Reactions","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/ORGANIC%20CHEMISTRY-NAMED%20REACTIONS.pdf?alt=media&token=db378c86-8614-406a-a45f-f5fcfaf68533"));
        summary3.add(new Subtopics("Reagents","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/ORGANIC%20CHEMISTRY-REAGENTS.pdf?alt=media&token=4330c799-0d49-493c-bce2-f4efd564960f"));

        RecyclerView myrv2 = (RecyclerView) view.findViewById(R.id.recyclerview7);
        RecyclerViewAdapter1 myAdapter2 = new RecyclerViewAdapter1(getContext(),summary3);
        myrv2.setLayoutManager(new GridLayoutManager(getContext(),1));
        myrv2.setAdapter(myAdapter2);


        summary4=new ArrayList<>();

        summary4.add(new Subtopics("Atomic Structure","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/C%20ATOMIC%20STRUCTURE.pdf?alt=media&token=20f5c3fa-3799-4a4b-ab56-7c9b86a5a359"));
        summary4.add(new Subtopics("Chemical Equilibrium","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/C%20CHEMICAL%20EQUILIBRIUM.pdf?alt=media&token=4c786a7f-882f-460d-8e85-6349bb3914ff"));
        summary4.add(new Subtopics("Chemical Kinetics and Nuclear Chemistry","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/C%20CHEMICAL%20KINETICS%20AND%20NUCLEAR%20CHEMISTRY.pdf?alt=media&token=35a54bc5-750e-4daa-8c63-48940dd046cf"));
        summary4.add(new Subtopics("Chemical Thermodynamics","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/C%20CHEMICAL%20THERMODYNAMICS.pdf?alt=media&token=96306fba-4b2a-47e6-a5e2-ccb9a4ce10f9"));
        summary4.add(new Subtopics("Dilute Solution and Colligative Properties","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/C%20DILUTE%20SOLUTION%20AND%20COLLIGATIVE%20PROPERTIES%20(1).pdf?alt=media&token=639303c8-6e8b-4a08-8eb2-b72fbacec246"));
        summary4.add(new Subtopics("Electrochemistry","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/C%20ELECTROCHEMISTRY.pdf?alt=media&token=5e2110c9-4676-43b1-8a80-80aeb09898ce"));
        summary4.add(new Subtopics("Gaseous State ","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/C%20GASEOUS%20STATE.pdf?alt=media&token=bb81ae43-54b0-48da-b8dd-c1d84d9d4d9f"));
        summary4.add(new Subtopics("General Chemistry","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/C%20GENERAL%20CHEMISTRY.pdf?alt=media&token=963305c3-6685-41d4-9a46-eda74c0402ce"));
        summary4.add(new Subtopics("Ionic Equilibrium","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/C%20IONIC%20EQUILIBRIUM.pdf?alt=media&token=1691fa47-bc95-46b8-8182-179fa9e67746"));
        summary4.add(new Subtopics("Mole Concept and Stoichiometry","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/C%20MOLE%20CONCEPT%20AND%20STOICHIOMETRY.pdf?alt=media&token=72d29a77-500e-4f9a-96ea-75ee22c0b8c4"));
        summary4.add(new Subtopics("Solid State","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/C%20SOLID%20STATE.pdf?alt=media&token=19695fdc-1aa7-4a04-9492-8e8298ef2948"));
        summary4.add(new Subtopics("Surface Chemistry","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/C%20SURFACE%20CHEMISTRY.pdf?alt=media&token=7975fa19-2029-4a08-99d9-ad0106dd3e36"));

        RecyclerView myrv3 = (RecyclerView) view.findViewById(R.id.recyclerview8);
        RecyclerViewAdapter1 myAdapter3 = new RecyclerViewAdapter1(getContext(),summary4);
        myrv3.setLayoutManager(new GridLayoutManager(getContext(),1));
        myrv3.setAdapter(myAdapter3);

        return view;
    }
}