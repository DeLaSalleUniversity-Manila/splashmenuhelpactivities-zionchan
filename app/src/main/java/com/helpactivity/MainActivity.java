package com.helpactivity;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends IntroActivity implements ExpandableListView.OnChildClickListener {

    private DrawerLayout drawer;
    private ExpandableListView drawerList;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ArrayList<String> groupItem;
    private HashMap<String, List<String>> childItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        centerActionBarTitle();
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        setContentView(R.layout.activity_main);

        setGroupData();
        setChildGroupData();

        initDrawer();
    }



    private void initDrawer() {

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerList = (ExpandableListView) findViewById(R.id.left_drawer);

        drawerList.setAdapter(new MyExpandableListAdapter(this, groupItem, childItem));

        drawerList.setOnChildClickListener(this);

        drawerList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {

                playSound("Bird.mp3");

                if (groupPosition == 3){
                    startActivity(new Intent(MainActivity.this,
                            HelpActivity.class));
                    return true;
                }


                return false;
            }


        });



    }

            public void setGroupData() {

                groupItem = new ArrayList<String>();

                groupItem.add("LEARN");
                groupItem.add("EXAM");
                groupItem.add("RESULT");
                groupItem.add("HELP");
            }


            public void setChildGroupData() {

                childItem = new HashMap<String, List<String>>();

                ArrayList<String> child = new ArrayList<String>();
                child.add("Child 0-0");
                child.add("Child 0-1");
                childItem.put(groupItem.get(0), child);

                child = new ArrayList<String>();
                child.add("child 1-0");
                childItem.put(groupItem.get(1), child);

                child = new ArrayList<String>();
                child.add("child 2-0");
                child.add("child 2-1");
                child.add("child 2-2");
                child.add("child 2-3");
                childItem.put(groupItem.get(2), child);

                child = new ArrayList<String>();
                child.add(" ");
                childItem.put(groupItem.get(3), child);
            }




            private void playSound(String fileName) {
                final MediaPlayer mp = new MediaPlayer();

                if (mp.isPlaying()) {
                    mp.stop();
                    mp.reset();
                }
                try {

                    AssetFileDescriptor afd;
                    afd = getAssets().openFd(fileName);
                    mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                    mp.prepare();
                    mp.start();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(this, "Clicked On Child " + groupPosition + "-" + childPosition,
                        Toast.LENGTH_SHORT).show();
                return true;
            }


            @Override
            public boolean onCreateOptionsMenu(Menu menu) {
                getMenuInflater().inflate(R.menu.menu_main, menu);
                return true;
            }

            @Override
            public boolean onOptionsItemSelected(MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.action_settings) {
                    return true;
                }

                return super.onOptionsItemSelected(item);
            }


            @Override
            protected void onDestroy() {
                super.onDestroy();
                stopService(new Intent(this, BackgroundSound.class));
            }

            @Override
            protected void onPause() {
                super.onPause();
                stopService(new Intent(this, BackgroundSound.class));
            }

            @Override
            protected void onStop() {
                super.onStop();
                stopService(new Intent(this, BackgroundSound.class));
            }
        }