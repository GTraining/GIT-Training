package com.example.jason.jason_workshop_3.DialogLibrary;

import android.widget.BaseAdapter;

public interface HolderAdapter extends Holder {

  void setAdapter(BaseAdapter adapter);

  void setOnItemClickListener(OnHolderListener listener);
}
