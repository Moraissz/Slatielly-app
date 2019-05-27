package com.example.slatielly;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.slatielly.Model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import info.androidhive.fontawesome.FontDrawable;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnSuccessListener<DocumentSnapshot> {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FragmentManager fragmentManager;
    private TextView txtNavHeader;
    private int[] icons = {
            R.string.fa_female_solid, R.string.fa_calendar_alt_solid, R.string.fa_calendar_check_solid,
            R.string.fa_plus_solid, R.string.fa_tasks_solid
    };
    private MenuItem menuItemChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initNavView();
        this.getUser();
        this.initDrawerLayout();

        this.fragmentManager = this.getSupportFragmentManager();

        MenuItem menuItem = this.navigationView.getMenu().getItem(0);
        FontDrawable icon = (FontDrawable) menuItem.getIcon();
        icon.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        menuItem.setChecked(true);
        this.menuItemChecked = menuItem;

        this.setNavigationFragment(new DressesFragment(), R.string.all_dresses);
    }

    private void initDrawerLayout() {
        this.toolbar = this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);

        this.drawerLayout = this.findViewById(R.id.drawerLayout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, this.drawerLayout, this.toolbar,
                R.string.open_drawer, R.string.close_drawer);

        this.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void getUser() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .document(firebaseUser.getUid())
                .get()
                .addOnSuccessListener(this);
    }

    private void initNavView() {
        this.navigationView = this.findViewById(R.id.navView);
        this.navigationView.setNavigationItemSelectedListener(this);

        ImageView iconHeader = this.navigationView.getHeaderView(0).findViewById(R.id.ivNavHeader);
        FontDrawable drawable = new FontDrawable(this, R.string.fa_user_circle_solid, true, true);
        drawable.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        drawable.setTextSize(35);
        iconHeader.setImageDrawable(drawable);
        this.txtNavHeader = this.navigationView.getHeaderView(0).findViewById(R.id.txtNavHeader);

        this.renderMenuIcons(this.navigationView.getMenu());
    }

    private void renderMenuIcons(Menu menu) {
        for (int i = 0; i < menu.size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            if (!menuItem.hasSubMenu()) {
                FontDrawable drawable = new FontDrawable(this, this.icons[i], true, false);
                drawable.setTextColor(ContextCompat.getColor(this, R.color.colorGray600));
                drawable.setTextSize(25);
                menu.getItem(i).setIcon(drawable);
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        this.unCheckMenuItem();

        menuItem.setChecked(true);
        FontDrawable icon = (FontDrawable) menuItem.getIcon();
        icon.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        this.menuItemChecked = menuItem;

        switch (menuItem.getItemId()) {
            case R.id.nav_item_all_dresses: {
                this.setNavigationFragment(new DressesFragment(), R.string.all_dresses);
                break;
            }

            case R.id.nav_item_all_rents: {
                this.setNavigationFragment(new RentsFragment(), R.string.allRents);
                break;
            }

            case R.id.nav_item_rent_requests: {
                this.setNavigationFragment(new RentRequestsFragment(), R.string.rentsRequests);
                break;
            }

            case R.id.nav_item_register_dress: {
                this.setNavigationFragment(new RegisterDressFragment(), R.string.wRegisterDress);
                break;
            }

            case R.id.nav_item_list_orders: {
                this.setNavigationFragment(new ListOrdersFragment(), R.string.wOrders);
                break;
            }

            default: {
                this.setNavigationFragment(new DressesFragment(), R.string.all_dresses);
                break;
            }
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void unCheckMenuItem() {
        this.menuItemChecked.setChecked(false);
        FontDrawable icon = (FontDrawable) this.menuItemChecked.getIcon();
        icon.setTextColor(ContextCompat.getColor(this, R.color.colorGray600));
        this.menuItemChecked = null;
    }

    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setNavigationFragment(Fragment fragment, int title) {
        this.fragmentManager.beginTransaction()
                .replace(R.id.fragment_content, fragment)
                .commit();

        this.toolbar.setTitle(title);
    }

    @Override
    public void onSuccess(DocumentSnapshot documentSnapshot) {
        User user = documentSnapshot.toObject(User.class);
        this.txtNavHeader.setText(user.getName());
    }
}
