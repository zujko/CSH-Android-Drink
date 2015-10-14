package edu.csh.cshdrink.fragments;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.csh.androiddrink.R;
import edu.csh.cshdrink.DividerItemDecoration;
import edu.csh.cshdrink.DrinkApplication;
import edu.csh.cshdrink.ItemClickSupport;
import edu.csh.cshdrink.adapters.ItemAdapter;
import edu.csh.cshdrink.models.BulkMachineData;
import edu.csh.cshdrink.models.Item;
import edu.csh.cshdrink.models.Test;
import edu.csh.cshdrink.models.UserData;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MachineFragment extends Fragment {
    public static final String MACHINE_ID = "MACHINE_ID";
    private int machine;
    ItemAdapter adapter;
    @Bind(R.id.machine_item_list) RecyclerView mRecyclerView;
    @Bind(R.id.swipe_container) SwipeRefreshLayout mSwipeRefreshLayout;
    SharedPreferences mPrefs;
    String apiKey;

    public static MachineFragment newInstance(int machineId) {
        Bundle args = new Bundle();
        args.putInt(MACHINE_ID, machineId);
        MachineFragment fragment = new MachineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.machine = getArguments().getInt(MACHINE_ID);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        apiKey = mPrefs.getString("key","");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_machine_layout,container,false);
        ButterKnife.bind(this, rootView);

        mSwipeRefreshLayout.setDistanceToTriggerSync(240);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.cshPink, R.color.cshPinkDark, R.color.cshPurple, R.color.cshPurplePressed);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadItems();
            }
        });

        adapter = new ItemAdapter(null);
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Item item = adapter.mItems.get(position);
                if (item.isEnabled() && item.isAvailable()) {
                    createDropDialog(item);
                } else {
                    Toast.makeText(getContext(), "Item is not available", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST);
        mRecyclerView.addItemDecoration(itemDecoration);
        loadItems();
        return rootView;
    }

    /**
     * Creates the dialog to ask the user if they want to drop a drink
     * @param item
     */
    private void createDropDialog(final Item item) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Drop Drink?");
        alert.setMessage("Drop a " + item.item_name + "?\nEnter seconds until drop");
        final AppCompatEditText text = new AppCompatEditText(getActivity());
        text.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setView(text);
        alert.setPositiveButton("DROP", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String delay = text.getText().toString();
                if (delay.equals("") || delay.equals(" ")) {
                    delay = "0";
                }
                Call<Test> call = DrinkApplication.API.dropDrink(item.machine_id, item.slot_num, delay, apiKey);
                final String finalDelay = delay;
                Toast.makeText(getContext(), "Drink dropping in " + finalDelay + " seconds", Toast.LENGTH_SHORT).show();
                call.enqueue(new Callback<Test>() {
                    @Override
                    public void onResponse(Response<Test> response, Retrofit retrofit) {
                        Test body = response.body();
                        if (body.data) {
                            Toast.makeText(getContext(), "Drink Dropped!", Toast.LENGTH_SHORT).show();
                            updateCredits();
                        } else {
                            Toast.makeText(getContext(), "Failed to drop drink: " + body.message, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getContext(), "FAILED: " + t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.create().show();
    }

    private void loadItems() {
        Call<BulkMachineData> bulkMachineDataCall = DrinkApplication.API.getSpecificMachineData(String.valueOf(machine));
        bulkMachineDataCall.enqueue(new Callback<BulkMachineData>() {
            @Override
            public void onResponse(Response<BulkMachineData> response, Retrofit retrofit) {
                BulkMachineData.MachineData data = response.body().data;
                adapter.mItems.clear();
                switch (machine) {
                    case 1:
                        adapter.addItems(data.littleDrink);
                        break;
                    case 2:
                        adapter.addItems(data.bigDrink);
                        break;
                    case 3:
                        adapter.addItems(data.snack);
                        break;
                }
                adapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void updateCredits() {
        Call<UserData> userDataCall = DrinkApplication.API.getUserInfo(apiKey);
        userDataCall.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Response<UserData> response, Retrofit retrofit) {
                UserData userData = response.body();
                if(userData.status.equals("true")) {
                    UserData.User user = userData.data;
                    String data = String.format("UID: %s CREDITS: %s ADMIN: %s",user.uid,user.credits,user.admin);
                    Log.d("USER DATA", data);
                    mPrefs.edit().putString("uid",user.uid).commit();
                    mPrefs.edit().putString("credits",user.credits).commit();
                    mPrefs.edit().putString("admin",user.admin).commit();
                    AppCompatActivity activity = (AppCompatActivity) getActivity();
                    activity.getSupportActionBar().setSubtitle("Credits: " +mPrefs.getString("credits",""));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getActivity(), "Could not get user data", Toast.LENGTH_SHORT).show();
                String errorMessage = String.format("Error: %s\nMessage: %s\nCause: %s",t.toString(),t.getMessage(),t.getCause().getMessage());
                Log.e("LOGIN", errorMessage);
            }
        });
    }
}
