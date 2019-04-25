package mobilize.mx.testflatmap.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import mobilize.mx.genericadapter.ItemListener;
import mobilize.mx.genericadapter.MobilizeAdapter;
import mobilize.mx.restcore.model.User;
import mobilize.mx.testflatmap.App;
import mobilize.mx.testflatmap.BR;
import mobilize.mx.testflatmap.R;
import mobilize.mx.testflatmap.databinding.ActivityMainBinding;
import mobilize.mx.testflatmap.viewmodel.MainVMFactory;
import mobilize.mx.testflatmap.viewmodel.MainViewModel;

/**
 * <pre>
 *     author: Efra Morales - emoralest@gmail.com
 *     time  : 2019/04/25
 * </pre>
 */
public class MainActivity extends AppCompatActivity implements ItemListener {
    private MainViewModel viewModel;
    private ActivityMainBinding binding;
    private List<User> userList = new ArrayList<>();
    private MobilizeAdapter adapter;

    @Inject
    MainVMFactory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getApplication()).getAppComponent().inject(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);
        binding.setViewModel(viewModel);


        init();
    }

    public void init() {
        setupRecycler();

        initData();
    }

    private void initData() {
        viewModel.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                userList.clear();
                userList.addAll(users);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void setupRecycler() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvUsers.setLayoutManager(layoutManager);
        adapter = new MobilizeAdapter<>(userList, R.layout.item_user, this, BR.user);
        binding.rvUsers.setAdapter(adapter);
    }

    @Override
    public void onClick(Object model, View v, int position) {

    }
}
