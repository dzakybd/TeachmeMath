package id.ac.its.teachmemath.View;

import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
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

import com.activeandroid.query.Select;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

import id.ac.its.teachmemath.Adapter.AdapterSoal;
import id.ac.its.teachmemath.Adapter.AdapterTanyajawab;
import id.ac.its.teachmemath.Database.Materi;
import id.ac.its.teachmemath.Database.Soal;
import id.ac.its.teachmemath.Database.Tanyajawab;
import id.ac.its.teachmemath.Method.GravitySnapHelper;
import id.ac.its.teachmemath.Method.ItemClickSupport;
import id.ac.its.teachmemath.R;

import static id.ac.its.teachmemath.R.layout.page2;

public class Page2 extends AppCompatActivity {

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
    private Long b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(page2);
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName("teachmemath")
                .setUseDefaultSharedPreference(true)
                .build();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String a = getIntent().getExtras().getString("materinama","Soal");
        b = getIntent().getExtras().getLong("materiid",0);
        Prefs.putLong("idmateri",b);
        setTitle(a);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Prefs.getInt("type",0)==1){
                    Intent i = new Intent(Page2.this, Tambahtanyajawab.class);
                    i.putExtra("materiid",b);
                    startActivity(i);
                }else{
                    open(view);
                }
            }
        });

    }

    public void open(View view){
        AlertDialog.Builder pilihan = new AlertDialog.Builder(this);
        pilihan.setMessage("Tambahkan apa?");
        pilihan.setPositiveButton("Soal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent move = new Intent(Page2.this, Tambahsoal.class);
                move.putExtra("materiid",b);
                startActivity(move);
            }
        });

        pilihan.setNegativeButton("Tanya Jawab", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent move = new Intent(Page2.this, Tambahtanyajawab.class);
                move.putExtra("materiid",b);
                startActivity(move);
            }
        });
        AlertDialog alert = pilihan.create();
        alert.show();
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
            final Materi materi = new Select()
                    .from ( Materi.class )
                    .where("id = ?", Prefs.getLong("idmateri",0))
                    .executeSingle();
            if(getArguments().getInt(ARG_SECTION_NUMBER)==1){
                final List<Soal> soals = new Select()
                        .from ( Soal.class )
                        .where("materi = ?", materi.getId())
                        .execute();
                rootView = inflater.inflate(R.layout.menusoal, container, false);
                RecyclerView cardView = (RecyclerView) rootView.findViewById(R.id.soalview);
                SnapHelper snapHelper = new GravitySnapHelper(Gravity.TOP);
                snapHelper.attachToRecyclerView(cardView);
                cardView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                cardView.setHasFixedSize(true);
                AdapterSoal adapter = new AdapterSoal(getActivity(), soals);
                cardView.setAdapter(adapter);
                ItemClickSupport.addTo(cardView).setOnItemClickListener(
                        new ItemClickSupport.OnItemClickListener() {
                            @Override
                            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                                Intent i = new Intent(getActivity(), Jawabsoal.class);
                                i.putExtra("soal",soals.get(position).getId());
                                startActivity(i);
                            }
                        }
                );
            }else {
                List<Tanyajawab> tanyajawabs = new Select()
                        .from(Tanyajawab.class)
                        .where("materi = ?", materi.getId())
                        .execute();
                rootView = inflater.inflate(R.layout.menutanyajawab, container, false);
                RecyclerView cardView = (RecyclerView) rootView.findViewById(R.id.tanyajwabview);
                SnapHelper snapHelper = new GravitySnapHelper(Gravity.TOP);
                snapHelper.attachToRecyclerView(cardView);
                cardView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                cardView.setHasFixedSize(true);
                AdapterTanyajawab adapter = new AdapterTanyajawab(getActivity(), tanyajawabs);
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
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Soal";
                case 1:
                    return "Tanya Jawab";
            }
            return null;
        }
    }
}
