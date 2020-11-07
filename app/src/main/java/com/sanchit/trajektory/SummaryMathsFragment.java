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

public class SummaryMathsFragment extends Fragment {

    List<Subtopics> summary5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_summary_maths, container, false);


        summary5 = new ArrayList<>();
        summary5.add(new Subtopics("Theory Of Equations","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/M%20THEORY%20OF%20EQUATIONS.pdf?alt=media&token=82a4f7ed-18f9-460f-be46-2827389d79d6"));
        summary5.add(new Subtopics("Trigonometry","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/M%20TRIGONOMETRY%20(ALL).pdf?alt=media&token=65c34944-905b-48ac-b93c-a805ec1378a6"));
        summary5.add(new Subtopics("Sequence and Series","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/M%20SEQUENCE%20AND%20SERIES.pdf?alt=media&token=1c315e02-f064-49a3-85f9-e08dddc55a97"));
        summary5.add(new Subtopics("Set Theory","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/M%20SET%20THEORY.pdf?alt=media&token=68e5e198-3de4-4510-87b7-070725823c57"));
        summary5.add(new Subtopics("Permutation and Combination","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/M%20COMBINATORICS-%20PERMUTATIONS%20_%20COMBINATIONS.pdf?alt=media&token=0552f92a-425b-4fec-9a6a-ccd373fe14a3"));
        summary5.add(new Subtopics("Binomial Theorem","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/M%20BINOMIAL%20THEOREM.pdf?alt=media&token=2858e87e-fa8f-42cd-a134-1abc182f9786"));
        summary5.add(new Subtopics("Complex Numbers","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/M%20COMPLEX%20NUMBERS.pdf?alt=media&token=9247c023-da79-4aff-be3b-67e55d1e919f"));
        summary5.add(new Subtopics("Functions","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/M%20FUNCTIONS.pdf?alt=media&token=4e63fee9-5b3a-4296-986e-01c4dbef83aa"));
        summary5.add(new Subtopics("Limits , Continuity and Differentiability","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/M%20LIMITS%2C%20CONTINUITY%20_%20DIFFERENTIABILITY.pdf?alt=media&token=c5947477-6ae5-40cf-94a6-1842f2eb9789"));
        summary5.add(new Subtopics("Differential Equations","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/M%20DIFFERENTIAL%20EQUATIONS.pdf?alt=media&token=177ef23a-cf64-4ac8-9a07-1e73d9f6ea95"));
        summary5.add(new Subtopics("Application of Derivatives","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/M%20APPLICATION%20OF%20DERIVATIVES.pdf?alt=media&token=706047ba-7049-4f34-a9f9-7901c7553f6f"));
        summary5.add(new Subtopics("Area ","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/M%20AREA%20MEASUREMENT%20BY%20DEFINITE%20INTEGRATION.pdf?alt=media&token=f9c75514-2c74-44c0-ac12-d8acb982a7ec"));
        summary5.add(new Subtopics("Inverse Trigonometric Functions","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/M%20INVERSE%20TRIGONOMETRIC%20FUNCTIONS.pdf?alt=media&token=131ada66-cd43-4a0c-8ece-23b47159e05e"));
        summary5.add(new Subtopics("Indefinite Integration","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/M%20INDEFINITE%20INTEGRATION.pdf?alt=media&token=4a4a2c7d-5e0d-4a4d-be7f-6baa62684ebb"));
        summary5.add(new Subtopics("Definite Integration","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/M%20DEFINITE%20INTEGRATION.pdf?alt=media&token=d0841042-2782-4ffd-a8bd-7e73d3ef1417"));
        summary5.add(new Subtopics("Straight Lines","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/M%20STRAIGHT%20LINE.pdf?alt=media&token=9f14f0df-fc2e-4bbe-9e10-eb5b98a25aa4"));
        summary5.add(new Subtopics("Parabola","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/M%20PARABOLA%20(WITH%20INTRODUCTION%20TO%20CONICS).pdf?alt=media&token=55d944f9-34e7-4bb1-a4f2-d19bc0211bde"));
        summary5.add(new Subtopics("Circle","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/M%20CIRCLE.pdf?alt=media&token=38755ae1-3332-4153-b020-97e2e7db50c1"));
        summary5.add(new Subtopics("Ellipse","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/M%20ELLIPSE.pdf?alt=media&token=d381dc96-0ec2-47a7-b5c2-322c1ec0e342"));
        summary5.add(new Subtopics("Hyperbola","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/M%20HYPERBOLA.pdf?alt=media&token=132e527e-0e6c-482a-ab17-c3ba3285ec52"));
        summary5.add(new Subtopics("Vectors","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/M%20VECTORS.pdf?alt=media&token=61728bdc-6ee8-476f-8bb9-ff302e8874d5"));
        summary5.add(new Subtopics("3D Geometry - 1 ","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/M%203D%20GEOMETRY%20(1).pdf?alt=media&token=da2ffc67-05e3-4349-b36f-0880e8c652fa"));
        summary5.add(new Subtopics("3D Geometry - 2","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/M%203D%20GEOMETRY%20(2).pdf?alt=media&token=824ac116-1d19-4cac-bbb5-789e4b9e530e"));
        summary5.add(new Subtopics("Statistics","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/M%20STATISTICS.pdf?alt=media&token=7cc02572-c367-4803-8fac-66a60dc27c2b"));
        summary5.add(new Subtopics("Probability","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/M%20PROBABILITY.pdf?alt=media&token=49e180bf-ef61-49a8-80fa-270475af10d0"));
        summary5.add(new Subtopics("Matrices","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/M%20MATRICES.pdf?alt=media&token=46c7eebd-8204-4078-be94-d8d40462415a"));
        summary5.add(new Subtopics("Determinants","https://firebasestorage.googleapis.com/v0/b/test-4bac3.appspot.com/o/M%20DETERMINANTS.pdf?alt=media&token=5b2b5f2e-af45-49e8-b6f4-2ee79279be7c"));






        RecyclerView myrv = (RecyclerView) view.findViewById(R.id.recyclerview9);
        RecyclerViewAdapter1 myAdapter = new RecyclerViewAdapter1(getContext(),summary5);
        myrv.setLayoutManager(new GridLayoutManager(getContext(),1));
        myrv.setAdapter(myAdapter);
        return view;
    }
}