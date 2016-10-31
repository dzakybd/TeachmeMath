package id.ac.its.teachmemath.View;

import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

import id.ac.its.teachmemath.Adapter.AdapterLaporan;
import id.ac.its.teachmemath.Adapter.AdapterMateri;
import id.ac.its.teachmemath.Database.Laporan;
import id.ac.its.teachmemath.Database.Materi;
import id.ac.its.teachmemath.Method.GravitySnapHelper;
import id.ac.its.teachmemath.Method.ItemClickSupport;
import id.ac.its.teachmemath.R;

public class Page1 extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page1);
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName("teachmemath")
                .setUseDefaultSharedPreference(true)
                .build();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String type;
        if(Prefs.getInt("type",0)==1)type="Murid";
        else if(Prefs.getInt("type",0)==2)type="Guru";
        else type="Ortu";

        setTitle("Hai "+Prefs.getString("nama","")+" - "+type);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if(Prefs.getInt("type",0)!=2)fab.hide();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Page1.this, Tambahmateri.class);
                startActivity(i);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            Prefs.clear();
            Intent i = new Intent(this, Login.class);
            startActivity(i);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView=null;
            if(getArguments().getInt(ARG_SECTION_NUMBER)==1){
                final List<Materi> materis = new Select()
                        .from ( Materi.class )
                        .execute();
                 rootView = inflater.inflate(R.layout.menumateri, container, false);
                RecyclerView cardView = (RecyclerView) rootView.findViewById(R.id.materiview);
                SnapHelper snapHelper = new GravitySnapHelper(Gravity.TOP);
                snapHelper.attachToRecyclerView(cardView);
                cardView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                cardView.setHasFixedSize(true);
                AdapterMateri adapter = new AdapterMateri(getActivity(), materis);
                cardView.setAdapter(adapter);
                if(Prefs.getInt("type",0)!=3){
                    ItemClickSupport.addTo(cardView).setOnItemClickListener(
                            new ItemClickSupport.OnItemClickListener() {
                                @Override
                                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                                    Intent i = new Intent(getActivity(), Page2.class);
                                    i.putExtra("materinama",materis.get(position).nama);
                                    i.putExtra("materiid",materis.get(position).getId());
                                    startActivity(i);
                                }
                            }
                    );
                }else {
                    Toast.makeText(getActivity(),"Anda hanya bisa melihat laporan",Toast.LENGTH_SHORT).show();
                }
            }else{
                final List<Laporan> laporans = new Select()
                        .from ( Laporan.class )
                        .execute();

                rootView = inflater.inflate(R.layout.menulaporan, container, false);
                RecyclerView cardView = (RecyclerView) rootView.findViewById(R.id.laporanview);
                SnapHelper snapHelper = new GravitySnapHelper(Gravity.TOP);
                snapHelper.attachToRecyclerView(cardView);
                cardView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                cardView.setHasFixedSize(true);
                AdapterLaporan adapter = new AdapterLaporan(getActivity(), laporans);
                cardView.setAdapter(adapter);
            }

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Materi";
                case 1:
                    return "Laporan";
            }
            return null;
        }
    }

}
