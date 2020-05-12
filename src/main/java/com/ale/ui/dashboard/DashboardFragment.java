package com.ale.ui.dashboard;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ale.R;

import java.io.IOException;
import java.lang.ref.WeakReference;

import util.HttpDogRaiBow;

public class DashboardFragment extends Fragment {
    private String reuslt;
    private Handler handler = new Myhandler1(this);

    static class Myhandler1 extends Handler {
        WeakReference<DashboardFragment> dhf;
        private ClipboardManager cm;
        private ClipData data;

        public Myhandler1(DashboardFragment dashboardFragment) {
            dhf = new WeakReference<DashboardFragment>(dashboardFragment);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            DashboardFragment dashboardFragment = dhf.get();
            switch (msg.what) {
                case 1:
                    String result = (String) msg.obj;
                    TextView dog = dashboardFragment.getActivity().findViewById(R.id.result_dog);
                    dog.setText(result);
                    Toast.makeText(dashboardFragment.getContext(), "已复制到粘贴板", Toast.LENGTH_SHORT).show();
                    cm= (ClipboardManager) dashboardFragment.getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    data=ClipData.newPlainText("Label",result);
                    cm.setPrimaryClip(data);
                    break;
            }

            super.handleMessage(msg);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Button button = getActivity().findViewById(R.id.get_dog);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            reuslt = HttpDogRaiBow.getDogRainBOw();
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