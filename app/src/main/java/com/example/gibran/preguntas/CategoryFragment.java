package com.example.gibran.preguntas;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ResourceCursorAdapter;
import android.widget.Toast;

import com.example.gibran.preguntas.Interface.ItemClickListener;
import com.example.gibran.preguntas.ViewHolder.CategoryViewHolder;
import com.example.gibran.preguntas.modelo.Category;
import com.example.gibran.preguntas.modelo.Questions;
import com.example.gibran.preguntas.variables.Variables;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Collections;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.paperdb.Paper;


public class CategoryFragment extends Fragment {

  View myFragment;

  RecyclerView listCategory;
  RecyclerView.LayoutManager layoutManager;
  FirebaseRecyclerAdapter<Category,CategoryViewHolder> adapter;

  FirebaseDatabase database;
  DatabaseReference categories;
  FirebaseAuth auth;

  FloatingActionButton button,btnInfo,btnLogout;
  Animation fabOpen,fabClose,fabClock,fabAntiClock;
  boolean isOpen = false;



  public static CategoryFragment newInstance(){
    CategoryFragment categoryFragment = new CategoryFragment();
    return categoryFragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);


    database = FirebaseDatabase.getInstance();
    categories = database.getReference("Category");

    auth = FirebaseAuth.getInstance();



  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    myFragment =  inflater.inflate(R.layout.fragment_category,container,false);

    listCategory = (RecyclerView)myFragment.findViewById(R.id.listCategory);
    listCategory.setHasFixedSize(true);
    layoutManager = new LinearLayoutManager(container.getContext());
    listCategory.setLayoutManager(layoutManager);

    button = (FloatingActionButton)myFragment.findViewById(R.id.fab);
    btnInfo = (FloatingActionButton)myFragment.findViewById(R.id.fab_info);
    btnLogout = (FloatingActionButton)myFragment.findViewById(R.id.fab_logout);
    fabOpen = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.fab_open);
    fabClose = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.fab_close);
    fabClock= AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.rotate_clockwise);
    fabAntiClock = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.rotate_anticlock);

    btnLogout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        LogoutUser();
      }
    });

    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(isOpen){

          btnLogout.startAnimation(fabClose);
          btnInfo.startAnimation(fabClose);
          button.startAnimation(fabAntiClock);
          btnLogout.setClickable(false);
          btnInfo.setClickable(false);
          isOpen = false;

        }else {
          btnLogout.startAnimation(fabOpen);
          btnInfo.startAnimation(fabOpen);
          button.startAnimation(fabClock);
          btnLogout.setClickable(true);
          btnInfo.setClickable(true);
          isOpen = true;
        }
      }
    });

    loadCategories();

    return myFragment;
  }

  private void LogoutUser() {
    auth.signOut();
    if (auth.getCurrentUser() ==  null){
      startActivity( new Intent(getActivity(),MainActivity.class));
    }
    Paper.book().destroy();
  }


  private void loadCategories() {

    adapter = new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(
      Category.class,
      R.layout.category_layout,
      CategoryViewHolder.class,
      categories
    ) {
      @Override
      protected void populateViewHolder(CategoryViewHolder viewHolder, final Category model, int position) {

        viewHolder.category_name.setText(model.getName());
        Picasso.with(getActivity()).load(model.getImage()).into(viewHolder.category_image);

        viewHolder.setItemClickListener(new ItemClickListener() {
          @Override
          public void onClick(View view, int position, boolean isLongClick) {
            //Toast.makeText(getActivity(), String.format("%s|%s",adapter.getRef(position).getKey(),model.getName()), Toast.LENGTH_SHORT).show();
            Intent starGame = new Intent(getActivity(),Start.class);
            Variables.categoryId = adapter.getRef(position).getKey();
            Variables.categoryName = model.getName();
            startActivity(starGame);

           }
        });
      }
    };

    adapter.notifyDataSetChanged();
    listCategory.setAdapter(adapter);
  }
}
