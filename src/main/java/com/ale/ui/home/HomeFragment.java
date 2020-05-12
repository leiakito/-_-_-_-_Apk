package com.ale.ui.home;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ale.R;

import java.io.IOException;
import java.lang.ref.WeakReference;

import util.HttpFlowerFire;

public class HomeFragment extends Fragment {
    public String reuslt;
    private Handler handler = new Myhandler(this);
    private Boolean flag = false;

    static class Myhandler extends Handler {

        WeakReference<HomeFragment> mac;
        private ClipboardManager cm;
        private ClipData data;

        public Myhandler(HomeFragment homeFragment) {
            mac = new WeakReference<HomeFragment>(homeFragment);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            HomeFragment homeFragment = mac.get();
            switch (msg.what) {
                case 1:
                    String result = (String) msg.obj;
                    TextView textView = homeFragment.getActivity().findViewById(R.id.result);
                    textView.setText(result);
                    Toast.makeText(homeFragment.getContext(), "已复制到粘贴板", Toast.LENGTH_SHORT).show();
                    cm = (ClipboardManager) homeFragment.getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    data = ClipData.newPlainText("Label", result);
                    cm.setPrimaryClip(data);

            }
            super.handleMessage(msg);

        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Switch aSwitch = getActivity().findViewById(R.id.witherhkorzh);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    flag = true;
                    Toast.makeText(getContext(), "开启繁体", Toast.LENGTH_SHORT).show();
                } else {
                    flag = false;
                    Toast.makeText(getContext(), "关闭繁体", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button button = getActivity().findViewById(R.id.get);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            reuslt = HttpFlowerFire.get(flag);
                            Message message = new Message();
                            message.what = 1;
                            message.obj = reuslt;
                            handler.sendMessage(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        super.onActivityCreated(savedInstanceState);
    }
}